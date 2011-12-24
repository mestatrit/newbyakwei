package com.hk.web.hk4.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Follow;
import com.hk.bean.PvtChat;
import com.hk.bean.PvtChatMain;
import com.hk.bean.SendInfo;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.FollowService;
import com.hk.svr.MsgService;
import com.hk.svr.UserService;
import com.hk.svr.msg.validate.MsgValidate;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.FmtUrlContent;
import com.hk.web.msg.action.PvtChatVo;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.HkWebConfig;

@Component("/h4/op/msg")
public class MsgAction extends BaseAction {
	@Autowired
	private MsgService msgService;

	@Autowired
	private FollowService followService;

	@Autowired
	private UserService userService;

	/**
	 * 私信列表
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		SimplePage page = req.getSimplePage(10);
		List<PvtChatMain> list = this.msgService.getPvtChatMainList(this
				.getLoginUser(req).getUserId(), page.getBegin(),
				page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWeb4Jsp("msg/list.jsp");
	}

	/**
	 * 与某人的私信对话
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String chat(HkRequest req, HkResponse resp) throws Exception {
		long userId = this.getLoginUser(req).getUserId();
		long mainId = req.getLongAndSetAttr("mainId");
		int sms = req.getIntAndSetAttr("sms");
		PvtChatMain main = this.msgService.getPvtChatMain(userId, mainId);
		if (main == null) {
			return "r:/h4/op/msg.do";
		}
		if (main.getReadflg() == PvtChatMain.READ_N) {
			this.msgService.setRead(userId, mainId);
		}
		SimplePage page = req.getSimplePage(20);
		List<PvtChat> list = null;
		if (sms == 1) {// 过滤查看短信发送的私信
			list = this.msgService.getPvtChatList(userId, mainId,
					PvtChat.SmsFLG_Y, page.getBegin(), page.getSize() + 1);
		}
		else {
			list = this.msgService.getPvtChatList(userId, mainId, page
					.getBegin(), page.getSize() + 1);
		}
		this.processListForPage(page, list);
		List<PvtChatVo> volist = PvtChatVo.createVoList(list);
		// 对方是否关注你
		Follow follow = this.followService.getFollow(main.getUser2Id(), userId);
		if (follow == null) {
			req.setAttribute("not_follow", true);
		}
		req.setAttribute("volist", volist);
		req.setAttribute("main", main);
		req.setAttribute("list", list);
		req.setAttribute("canSms", this.isCanSms(req, main.getUser2Id()));// 是否可以发送短信
		return this.getWeb4Jsp("msg/chat.jsp");
	}

	/**
	 * pc发送短消息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String send(HkRequest req, HkResponse resp) {
		String nickName = req.getString("nickName");
		User user = this.userService.getUserByNickName(nickName);
		if (user == null) {
			return this.onError(req, Err.USER_NOT_EXIST, "msgerror", null);
		}
		long receiverId = user.getUserId();
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
		SendInfo sendInfo = this.msgService.sendMsg(receiverId, senderId, msg,
				smsflg);// 私信已发送
		PvtChat chat = sendInfo.getSenderPvtChat();
		chat.setSender(this.getLoginUser(req));
		req.setAttribute("msg", chat);
		return this.getWeb4Jsp("msg/sendok_inc.jsp");
	}

	/**
	 * 删除单条私信
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String delchat(HkRequest request, HkResponse response)
			throws Exception {
		long userId = this.getLoginUser(request).getUserId();
		long chatId = request.getLong("chatId");
		PvtChat chat = this.msgService.getPvtChat(chatId);
		if (chat != null && chat.getUserId() == userId) {
			this.msgService.deleteChat(userId, chatId);
		}
		return null;
	}

	/**
	 * 删除与某人的整个对话
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest request, HkResponse response) throws Exception {
		long userId = this.getLoginUser(request).getUserId();
		long mainId = request.getLong("mainId");
		PvtChatMain main = this.msgService.getPvtChatMain(userId, mainId);
		if (main != null && main.getUserId() == userId) {
			this.msgService.deletePvtChatMain(userId, mainId);
		}
		return null;
	}

	/**
	 * 获取最新对话
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String last(HkRequest request, HkResponse response) throws Exception {
		long userId = this.getLoginUser(request).getUserId();
		PvtChatMain main = this.msgService.getLastNoReadPvtChatMain(userId);
		if (main != null) {
			return "r:/h4/op/msg_chat.do?mainId=" + main.getMainId();
		}
		return "r:/h4/op/msg.do";
	}

	/**
	 * 到发送信息页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String send2(HkRequest req, HkResponse resp) {
		String nickName = req.getString("nickName");
		User user = this.userService.getUserByNickName(nickName);
		if (user == null) {
			return this.onError(req, Err.USER_NOT_EXIST, "msgerror", null);
		}
		long receiverId = user.getUserId();
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
		SendInfo sendInfo = this.msgService.sendMsg(receiverId, senderId, msg,
				smsflg);// 私信已发送
		PvtChat chat = sendInfo.getSenderPvtChat();
		return this.onSuccess2(req, "msgok", chat.getMainId());
	}
}