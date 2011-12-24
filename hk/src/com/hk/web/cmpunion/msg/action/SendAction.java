package com.hk.web.cmpunion.msg.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Follow;
import com.hk.bean.PvtChat;
import com.hk.bean.SendInfo;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.FollowService;
import com.hk.svr.MsgService;
import com.hk.svr.UserService;
import com.hk.svr.msg.exception.MsgFormatErrorException;
import com.hk.svr.msg.validate.MsgValidate;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.FmtUrlContent;
import com.hk.svr.user.exception.UserNotExistException;
import com.hk.web.cmpunion.action.CmpUnionBaseAction;
import com.hk.web.util.HkWebConfig;

@Component("/union/op/msg/send")
public class SendAction extends CmpUnionBaseAction {
	@Autowired
	private MsgService msgService;

	@Autowired
	private FollowService followService;

	@Autowired
	private UserService userService;

	public String execute(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		long receiverId = req.getLong("receiverId");
		long senderId = this.getLoginUser(req).getUserId();
		Follow follow = this.followService.getFollow(receiverId, senderId);
		if (follow == null) {// receiver 没有关注sender 就不能发送私信
			req.setSessionMessage(req.getText("func.usernofollow_cannotuse"));
			return "r:/union/union.do?uid=" + uid;
		}
		String msg = req.getString("msg");
		if (msg == null) {
			msg = req.getString("content", "");
		}
		try {
			MsgValidate.validateCreateMsg(senderId, receiverId, msg);
		}
		catch (MsgFormatErrorException e) {
			req.setAttribute("msg", msg);
			req.setText(Err.MSG_CONTENT_ERROR + "");
			return "/union/op/msg/send_tosend.do?userId=" + receiverId;
		}
		catch (UserNotExistException e) {
			req.setAttribute("msg", msg);
			req.setSessionMessage("请选择收信人");
			return "/union/op/msg/send_tosend.do?receiverId=" + receiverId;
		}
		msg = DataUtil.toHtml(msg).replaceAll("，", ",").replaceAll("。", ".");
		FmtUrlContent fmtUrlContent = new FmtUrlContent(msg, true, HkWebConfig
				.getShortUrlDomain());
		msg = fmtUrlContent.getFmtContent();
		SendInfo sendInfo = this.msgService.sendMsg(receiverId, senderId, msg,
				PvtChat.SmsFLG_N);
		req.setSessionMessage(req.getText("func.msg_send_ok"));
		return "r:/union/op/msg/chat.do?mainId="
				+ sendInfo.getSenderPvtChat().getMainId() + "&f=1&uid=" + uid;
	}

	/**
	 * 到发送信息页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tosend(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		int size = 15;
		long receiverId = req.getLong("receiverId");
		if (receiverId > 0) {
			return "r:/union/op/msg/send_tosend2.do?receiverId=" + receiverId
					+ "&uid=" + uid;
		}
		String nickName = req.getString("nickName");
		SimplePage page = req.getSimplePage(size);
		long userId = this.getLoginUser(req).getUserId();
		List<User> list = null;
		if (isEmpty(nickName)) {
			list = this.followService.getUserListForSend(userId, page
					.getBegin(), page.getSize() + 1);
		}
		else {
			list = this.followService.getUserListByNickNameForSend(userId,
					nickName, page.getBegin(), page.getSize() + 1);
		}
		if (list.size() == page.getSize() + 1) {
			page.setHasNext(true);
			list.remove(list.size() - 1);
		}
		req.setEncodeAttribute("nickName", nickName);
		req.setAttribute("list", list);
		req.setAttribute("receiverId", receiverId);
		return this.getUnionWapJsp("msg/send.jsp");
	}

	/**
	 * 到发送信息页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tosend2(HkRequest req, HkResponse resp) {
		long receiverId = req.getLong("receiverId");
		User receiver = this.userService.getUser(receiverId);
		if (receiver == null) {
			return null;
		}
		req.setAttribute("receiver", receiver);
		req.setAttribute("receiverId", receiverId);
		req.reSetAttribute("from");
		return this.getUnionWapJsp("msg/send2.jsp");
	}
}