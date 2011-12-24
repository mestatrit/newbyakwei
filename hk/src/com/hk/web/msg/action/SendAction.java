package com.hk.web.msg.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.Follow;
import com.hk.bean.HkbLog;
import com.hk.bean.PvtChat;
import com.hk.bean.PvtChatMain;
import com.hk.bean.SendInfo;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserSms;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.Sms;
import com.hk.sms.SmsClient;
import com.hk.svr.FollowService;
import com.hk.svr.MsgService;
import com.hk.svr.UserService;
import com.hk.svr.UserSmsService;
import com.hk.svr.msg.exception.MsgFormatErrorException;
import com.hk.svr.msg.validate.MsgValidate;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.FmtUrlContent;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.svr.pub.HkbConfig;
import com.hk.svr.user.exception.UserNotExistException;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.HkWebConfig;
import com.hk.web.util.HkWebUtil;

/**
 * 发送私信，也可以发送短信,如果对方没有火酷号，不能给对方发送。如果用户酷币不足，不能发送短信，私信可以发送。用户没有火酷号，不能给对方发送
 * 短信发送需要扣除用户酷币，记录用户短信发送日志，记录短信发送状态 (以后需要加入短信状态丢失的记录)
 * 
 * @author akwei
 */
@Component("/msg/send")
public class SendAction extends BaseAction {
	@Autowired
	private MsgService msgService;

	@Autowired
	private FollowService followService;

	@Autowired
	private UserService userService;

	@Autowired
	private SmsClient smsClient;

	@Autowired
	private UserSmsService userSmsService;

	public String execute(HkRequest req, HkResponse resp) {
		long receiverId = req.getLong("receiverId");
		long senderId = this.getLoginUser(req).getUserId();
		Follow follow = this.followService.getFollow(receiverId, senderId);
		if (follow == null) {// receiver 没有关注sender 就不能发送私信
			req.setSessionMessage(req.getText("func.usernofollow_cannotuse"));
			return "r:/square.do";
		}
		String lastUrl = req.getString("lastUrl");
		String msg = req.getString("msg");
		if (msg == null) {
			msg = req.getString("content", "");
		}
		int fromlaba = req.getInt("fromlaba");
		if (fromlaba == 1) {
			User user = this.userService.getUser(receiverId);
			String n = "@" + user.getNickName();
			if (msg.startsWith(n)) {
				msg = msg.replaceFirst(n, "");
				if (DataUtil.isEmpty(msg)) {
					req.setSessionMessage("私信内容不能为空或者不能超过140个字符");
					return lastUrl;
				}
			}
		}
		try {
			MsgValidate.validateCreateMsg(senderId, receiverId, msg);
		}
		catch (MsgFormatErrorException e) {
			req.setAttribute("msg", msg);
			req.setSessionMessage("私信内容不能为空或者不能超过140个字符");
			if (lastUrl != null) {
				return lastUrl;
			}
			return "/msg/send_tosend.do?userId=" + receiverId;
		}
		catch (UserNotExistException e) {
			req.setAttribute("msg", msg);
			req.setSessionMessage("请选择收信人");
			if (lastUrl != null) {
				return lastUrl;
			}
			return "/msg/send_tosend.do?receiverId=" + receiverId;
		}
		msg = DataUtil.toHtml(msg).replaceAll("，", ",").replaceAll("。", ".");
		boolean smssend = false;// 是否可以发送短信
		boolean enoughhkb = true;
		UserOtherInfo info = this.userService.getUserOtherInfo(receiverId);
		Sms sms = new Sms();
		String port = HkWebUtil.getUserPort(senderId);
		if (port != null) {// 如果用户没有短信端口无法发送信息
			if (req.getString("msgandsms_submit") != null) {
				sms.setContent(DataUtil.toTextRow(msg));
				sms.setPort(port);
				if (info.getMobile() != null) {
					if (!this.userService.hasEnoughHkb(senderId, -HkbConfig
							.getSendSms())) {// 查看酷币是否充足
						enoughhkb = false;
					}
					else {
						smssend = true;
					}
				}
			}
		}
		byte smsflg = PvtChat.SmsFLG_N;
		if (smssend) {
			smsflg = PvtChat.SmsFLG_Y;
		}
		FmtUrlContent fmtUrlContent = new FmtUrlContent(msg, true, HkWebConfig
				.getShortUrlDomain());
		msg = fmtUrlContent.getFmtContent();
		SendInfo sendInfo = this.msgService.sendMsg(receiverId, senderId, msg,
				smsflg);
		req.setSessionMessage(req.getText("func.msg_send_ok"));
		if (!enoughhkb && req.getString("msgandsms_submit") != null) {
			req.setSessionMessage(req.getText("func.msg_send_ok") + ","
					+ req.getText("func.noenoughhkb_sendsms"));
		}
		if (smssend && enoughhkb && req.getString("msgandsms_submit") != null) {
			sms.setContent(fmtUrlContent.getFmtContent());
			sms.setMobile(info.getMobile());
			HkbLog hkbLog = HkbLog.create(senderId, HkLog.SEND_SMS, receiverId,
					-HkbConfig.getSendSms());
			UserSms userSms = HkWebUtil.sendSms(sms, senderId, hkbLog);
			if (userSms != null) {
				this.msgService.updatePvtChatSmsmsgId(sendInfo
						.getReceiverPvtChat().getChatId(), userSms.getMsgId());
				this.msgService.updatePvtChatSmsmsgId(sendInfo
						.getSenderPvtChat().getChatId(), userSms.getMsgId());
			}
			req.setSessionMessage(req.getText("func.msg_send_ok"));
		}
		return "r:/msg/chat.do?mainId="
				+ sendInfo.getSenderPvtChat().getMainId() + "&f=1";
	}

	/**
	 * pc发送短消息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String sendweb(HkRequest req, HkResponse resp) {
		long receiverId = req.getLong("receiverId");
		long senderId = this.getLoginUser(req).getUserId();
		Follow follow = this.followService.getFollow(receiverId, senderId);
		if (follow == null) {// receiver 没有关注sender 就不能发送私信
			return this.initError(req, Err.MSG_CANNOT_SEND_FORNOTFOLLOW, "msg");
		}
		String msg = req.getString("msg");
		msg = DataUtil.toHtml(msg);
		int code = MsgValidate.validateMsg(msg);
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "msg");
		}
		UserOtherInfo info = this.userService.getUserOtherInfo(receiverId);
		if (info == null) {
			return null;
		}
		boolean smssend = false;// 是否可以发送短信
		boolean enoughhkb = true;
		Sms sms = new Sms();
		String port = HkWebUtil.getUserPort(senderId);
		if (port != null) {// 如果用户没有短信端口无法发送信息
			if (req.getString("msgandsms_submit") != null) {
				sms.setContent(DataUtil.toTextRow(msg));
				sms.setPort(port);
				if (info.getMobile() != null) {
					if (!this.userService.hasEnoughHkb(senderId, -HkbConfig
							.getSendSms())) {// 查看酷币是否充足
						enoughhkb = false;
					}
					else {
						smssend = true;
					}
				}
			}
		}
		byte smsflg = PvtChat.SmsFLG_N;
		if (smssend) {
			smsflg = PvtChat.SmsFLG_Y;
		}
		FmtUrlContent fmtUrlContent = new FmtUrlContent(msg, true, HkWebConfig
				.getShortUrlDomain());
		msg = fmtUrlContent.getFmtContent();
		SendInfo sendInfo = this.msgService.sendMsg(receiverId, senderId, msg,
				smsflg);// 私信已发送
		// 如果酷币不足，提示没有足够酷币发送短信
		if (!enoughhkb && req.getString("msgandsms_submit") != null) {
			// req.setSessionMessage(req.getText("func.msg_send_ok") + ","
			// + req.getText("func.noenoughhkb_sendsms"));
		}
		if (smssend && enoughhkb && req.getString("msgandsms_submit") != null) {
			sms.setContent(fmtUrlContent.getFmtContent());
			sms.setMobile(info.getMobile());
			HkbLog hkbLog = HkbLog.create(senderId, HkLog.SEND_SMS, receiverId,
					-HkbConfig.getSendSms());
			UserSms userSms = HkWebUtil.sendSms(sms, senderId, hkbLog);
			if (userSms != null) {
				this.msgService.updatePvtChatSmsmsgId(sendInfo
						.getReceiverPvtChat().getChatId(), userSms.getMsgId());
				this.msgService.updatePvtChatSmsmsgId(sendInfo
						.getSenderPvtChat().getChatId(), userSms.getMsgId());
			}
			// req.setSessionMessage(req.getText("func.msg_send_ok"));
		}
		int sendtype = req.getInt("sendtype");
		if (sendtype == 2 || sendtype == 3) {
			req.setSessionMessage(req.getText("func.msg_send_ok"));
			// long lastChatId = req.getLong("lastChatId");
			// long mainId = req.getLong("mainId");
			// List<PvtChat> list =
			// this.msgService.getPvtChatListByUserIdGt(this
			// .getLoginUser(req).getUserId(), mainId, lastChatId, 20);
			// req.setAttribute("list", list);
			// return this.getWeb3Jsp("msg/sendok.jsp");
		}
		if (sendtype == 3) {// 需要发送新的mainId到页面，用来返回
			return this.onSuccess(req, sendInfo.getSenderPvtChat().getMainId()
					+ "", "msg");
		}
		return this.initSuccess(req, "msg");
	}

	/**
	 * pc发送短消息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String sendweb2(HkRequest req, HkResponse resp) {
		long receiverId = req.getLong("receiverId");
		long senderId = this.getLoginUser(req).getUserId();
		Follow follow = this.followService.getFollow(receiverId, senderId);
		if (follow == null) {// receiver 没有关注sender 就不能发送私信
			return this.onError(req, Err.MSG_CANNOT_SEND_FORNOTFOLLOW,
					"msgerror", null);
		}
		String msg = req.getString("msg");
		msg = DataUtil.toHtml(msg);
		int code = MsgValidate.validateMsg(msg);
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "msgerror", null);
		}
		byte smsflg = PvtChat.SmsFLG_N;
		FmtUrlContent fmtUrlContent = new FmtUrlContent(msg, true, HkWebConfig
				.getShortUrlDomain());
		msg = fmtUrlContent.getFmtContent();
		this.msgService.sendMsg(receiverId, senderId, msg, smsflg);// 私信已发送
		return this.onSuccess2(req, "msgok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String sendtoservice(HkRequest req, HkResponse resp) {
		long receiverId = HkSvrUtil.getServiceUserId();
		long senderId = this.getLoginUser(req).getUserId();
		String msg = req.getString("msg");
		int code = PvtChat.validate(msg);
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "msg");
		}
		msg = DataUtil.toHtml(msg).replaceAll("，", ",").replaceAll("。", ".");
		FmtUrlContent fmtUrlContent = new FmtUrlContent(msg, true, HkWebConfig
				.getShortUrlDomain());
		msg = fmtUrlContent.getFmtContent();
		this.msgService.sendMsg(receiverId, senderId, msg, PvtChat.SmsFLG_N);
		return this.initSuccess(req, "msg");
	}

	/**
	 * 重新发送失败短信
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String resendsms(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		long smsmsgId = req.getLong("smsmsgId");
		long mainId = req.getLong("mainId");
		int sms_force = req.getInt("sms_force");// 测试使用
		UserSms userSms = this.userSmsService.getUserSms(smsmsgId);
		if (sms_force != 1) {
			if (userSms != null && !userSms.isSendFail()) {
				return "r:/msg/pvt.do?sms=1&mainId=" + mainId;
			}
		}
		PvtChatMain main = this.msgService.getPvtChatMain(
				loginUser.getUserId(), mainId);
		if (main != null) {
			long user2Id = main.getUser2Id();
			UserOtherInfo info = this.userService.getUserOtherInfo(user2Id);
			String port = HkWebUtil.getUserPort(loginUser.getUserId());
			if (port != null) {
				Sms sms = new Sms();
				sms.setPort(port);
				sms.setMobile(info.getMobile());
				sms.setExmsgid(smsmsgId + "");
				sms.setContent(DataUtil.toHtmlRow(main.getMsg()));
				this.smsClient.sendIgnoreError(sms);
				req.setSessionText("func.sms.resend.success");
			}
		}
		return "r:/msg/pvt.do?sms=1&mainId=" + mainId;
	}

	/**
	 * 到发送信息页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String tosend(HkRequest request, HkResponse response)
			throws Exception {
		int size = 15;
		long receiverId = request.getLong("receiverId");
		if (receiverId > 0) {
			return "r:/msg/send_tosend2.do?receiverId=" + receiverId;
		}
		String nickName = request.getString("nickName");
		SimplePage page = request.getSimplePage(size);
		long userId = this.getLoginUser(request).getUserId();
		List<User> list = null;
		if (isEmpty(nickName)) {
			list = this.followService.getUserListForSend(userId, page
					.getBegin(), size);
		}
		else {
			list = this.followService.getUserListByNickNameForSend(userId,
					nickName, page.getBegin(), size);
		}
		page.setListSize(list.size());
		request.setEncodeAttribute("nickName", nickName);
		request.setAttribute("list", list);
		request.setAttribute("receiverId", receiverId);
		return "/WEB-INF/page/msg/send.jsp";
	}

	/**
	 * 到发送信息页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String tosendweb(HkRequest request, HkResponse response)
			throws Exception {
		long userId = this.getLoginUser(request).getUserId();
		List<Follow> list = this.followService.getBothFollowList(userId);
		List<Long> idList = new ArrayList<Long>();
		for (Follow o : list) {
			idList.add(o.getFriendId());
		}
		// 进行中文排序(由于mysql utf-8,不能达到效果)
		Map<Long, User> map = this.userService.getUserMapInId(idList);
		Map<String, Follow> namemap = new HashMap<String, Follow>();
		String[] name = new String[list.size()];
		int i = 0;
		for (Follow o : list) {
			o.setFollowUser(map.get(o.getFriendId()));
			namemap.put(o.getFollowUser().getNickName(), o);
			name[i++] = o.getFollowUser().getNickName();
		}
		String[] new_name = DataUtil.getSortedByName(name);
		List<Follow> newlist = new ArrayList<Follow>();
		for (i = 0; i < new_name.length; i++) {
			newlist.add(namemap.get(new_name[i]));
		}
		request.setAttribute("list", newlist);
		return this.getWeb3Jsp("msg/send.jsp");
	}

	/**
	 * 到发送信息页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String tosend2(HkRequest request, HkResponse response)
			throws Exception {
		long receiverId = request.getLong("receiverId");
		User receiver = this.userService.getUser(receiverId);
		if (receiver == null) {
			return "r:/square.do";
		}
		request.setAttribute("receiver", receiver);
		request.setAttribute("receiverId", receiverId);
		request.reSetAttribute("from");
		return "/WEB-INF/page/msg/send2.jsp";
	}
}