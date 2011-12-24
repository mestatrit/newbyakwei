package com.hk.api.action.msg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.api.util.SessionKey;
import com.hk.bean.Follow;
import com.hk.bean.HkbLog;
import com.hk.bean.PvtChat;
import com.hk.bean.PvtChatMain;
import com.hk.bean.SendInfo;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserSms;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.Sms;
import com.hk.svr.FollowService;
import com.hk.svr.MsgService;
import com.hk.svr.UserService;
import com.hk.svr.msg.exception.MsgFormatErrorException;
import com.hk.svr.msg.validate.MsgValidate;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.FmtUrlContent;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.HkbConfig;
import com.hk.svr.user.exception.UserNotExistException;
import com.hk.web.util.HkWebConfig;
import com.hk.web.util.HkWebUtil;

// @Component("/pubapi/protect/msg")
public class ProtectMsgAction extends BaseApiAction {

	@Autowired
	private UserService userService;

	@Autowired
	private FollowService followService;

	@Autowired
	private MsgService msgService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String chatlist(HkRequest req, HkResponse resp) throws Exception {
		SessionKey o = this.getSessionKey(req);
		long userId = o.getUserId();
		long mainId = req.getLong("mainId");
		int sms = req.getInt("sms");
		int size = this.getSize(req);
		PvtChatMain main = this.msgService.getPvtChatMain(userId, mainId);
		if (main == null) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		if (main.getReadflg() == PvtChatMain.READ_N) {
			this.msgService.setRead(userId, mainId);
		}
		SimplePage page = req.getSimplePage(size);
		List<PvtChat> list = null;
		if (sms == 1) {
			list = this.msgService.getPvtChatList(userId, mainId,
					PvtChat.SmsFLG_Y, page.getBegin(), size);
		}
		else {
			list = this.msgService.getPvtChatList(userId, mainId, page
					.getBegin(), size);
		}
		PvtChat.initUserInList(list);
		VelocityContext context = new VelocityContext();
		context.put("chatlist", list);
		this.write(resp, "vm/msg/chatlist.vm", context);
		return null;
	}

	public String mainlist(HkRequest req, HkResponse resp) throws Exception {
		SessionKey o = this.getSessionKey(req);
		long userId = o.getUserId();
		int size = this.getSize(req);
		SimplePage page = req.getSimplePage(size);
		List<PvtChatMain> list = this.msgService.getPvtChatMainList(userId,
				page.getBegin(), size);
		VelocityContext context = new VelocityContext();
		context.put("mainlist", list);
		this.write(resp, "vm/msg/mainlist.vm", context);
		return null;
	}

	public String deletechat(HkRequest req, HkResponse resp) throws Exception {
		SessionKey o = this.getSessionKey(req);
		long userId = o.getUserId();
		long chatId = req.getLong("chatId");
		PvtChat chat = this.msgService.getPvtChat(chatId);
		if (chat != null && chat.getUserId() == userId) {
			this.msgService.deleteChat(userId, chatId);
			APIUtil.sendSuccessRespStatus(resp);
			return null;
		}
		APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
		return null;
	}

	public String deletemain(HkRequest req, HkResponse resp) throws Exception {
		SessionKey o = this.getSessionKey(req);
		long mainId = req.getLong("mainId");
		PvtChatMain main = this.msgService
				.getPvtChatMain(o.getUserId(), mainId);
		if (main != null && main.getUserId() == o.getUserId()) {
			this.msgService.deletePvtChatMain(o.getUserId(), mainId);
			APIUtil.sendSuccessRespStatus(resp);
			return null;
		}
		APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
		return null;
	}

	public String create(HkRequest req, HkResponse resp) throws Exception {
		long receiverId = req.getLong("receiverId");
		long senderId = this.getSessionKey(req).getUserId();
		String msg = req.getString("msg");
		boolean needsms = req.getBoolean("needsms");
		Follow follow = this.followService.getFollow(receiverId, senderId);
		if (follow == null) {// 对方没有关注你不能发送私信
			APIUtil.sendFailRespStatus(resp, Err.USER_NOT_FOLLOW);
			return null;
		}
		try {
			MsgValidate.validateCreateMsg(senderId, receiverId, msg);
		}
		catch (MsgFormatErrorException e) {
			APIUtil.sendFailRespStatus(resp, Err.MSG_CONTENT_ERROR);
			return null;
		}
		catch (UserNotExistException e) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		msg = DataUtil.toHtml(msg).replaceAll("，", ",").replaceAll("。", ".");
		FmtUrlContent fmtUrlContent = new FmtUrlContent(msg, true, HkWebConfig
				.getShortUrlDomain());
		msg = fmtUrlContent.getFmtContent();
		boolean smssend = false;
		SendInfo sendInfo = null;
		if (needsms) {// 需要发送短信
			String port = HkWebUtil.getUserPort(senderId);
			if (port != null) {// 用户有短信通道
				if (this.userService.hasEnoughHkb(senderId, -HkbConfig
						.getSendSms())) {// 查看酷币是否充足
					UserOtherInfo info = this.userService
							.getUserOtherInfo(receiverId);
					if (info.getMobile() != null) {
						sendInfo = this.msgService.sendMsg(receiverId,
								senderId, msg, PvtChat.SmsFLG_Y);
						Sms sms = new Sms();
						sms.setContent(DataUtil.toTextRow(msg));
						sms.setPort(port);
						sms.setMobile(info.getMobile());
						HkbLog hkbLog = HkbLog.create(senderId, HkLog.SEND_SMS,
								receiverId, -HkbConfig.getSendSms());
						UserSms userSms = HkWebUtil.sendSms(sms, senderId,
								hkbLog);
						smssend = true;
						if (userSms != null) {
							this.msgService.updatePvtChatSmsmsgId(sendInfo
									.getReceiverPvtChat().getChatId(), userSms
									.getMsgId());
							this.msgService.updatePvtChatSmsmsgId(sendInfo
									.getSenderPvtChat().getChatId(), userSms
									.getMsgId());
						}
					}
					else {// 对方没有手机号
						sendInfo = this.msgService.sendMsg(receiverId,
								senderId, msg, PvtChat.SmsFLG_N);
					}
				}
				else {// 酷币不够，只发送站内信
					sendInfo = this.msgService.sendMsg(receiverId, senderId,
							msg, PvtChat.SmsFLG_N);
				}
			}
			else {// 没有通道，只发送站内信
				sendInfo = this.msgService.sendMsg(receiverId, senderId, msg,
						PvtChat.SmsFLG_N);
			}
		}
		else {// 不需要发送短信
			sendInfo = this.msgService.sendMsg(receiverId, senderId, msg,
					PvtChat.SmsFLG_N);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("smssend", smssend);
		map.put("sendmsg", true);
		map.put("mainId", sendInfo.getSenderPvtChat().getMainId());
		APIUtil.sendSuccessRespStatus(resp, map);
		return null;
	}
}