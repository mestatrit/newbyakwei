package com.hk.web.msg.action;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.Follow;
import com.hk.bean.PvtChat;
import com.hk.bean.PvtChatMain;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.FollowService;
import com.hk.svr.MsgService;
import com.hk.web.pub.action.BaseAction;

/**
 * 只查看前3条
 * 
 * @author akwei
 */
@Component("/msg/pvt")
public class PvtAction extends BaseAction {
	@Autowired
	private MsgService msgService;

	@Autowired
	private FollowService followService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long userId = this.getLoginUser(req).getUserId();
		long mainId = req.getLong("mainId");
		PvtChatMain main = this.msgService.getPvtChatMain(userId, mainId);
		if (main == null) {
			return "r:/msg/pvtlist.do";
		}
		List<PvtChat> list = this.msgService.getLastPvtChatList(userId, mainId,
				3);
		List<PvtChatVo> volist = PvtChatVo.createVoList(list);
		Collections.reverse(list);
		if (main.getReadflg() == PvtChatMain.READ_N) {
			this.msgService.setRead(userId, mainId);
		}
		Follow follow = this.followService.getFollow(userId, main.getUser2Id());
		boolean notFriend = false;
		if (follow == null) {
			notFriend = true;
		}
		req.setAttribute("notFriend", notFriend);
		req.setAttribute("volist", volist);
		req.setAttribute("list", list);
		req.setAttribute("main", main);
		req.setAttribute("mainId", mainId);
		req.setAttribute("canSms", this.isCanSms(req, main.getUser2Id()));
		return "/WEB-INF/page/msg/pvt.jsp";
	}

	/**
	 * 获取最后一条
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
			return "r:/msg/pvt.do?mainId=" + main.getMainId();
		}
		return "r:/msg/pvtlist.do";
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
		request.setSessionMessage("删除私信成功");
		return "r:/msg/pvtlist.do";
	}

	/**
	 * 删除与某人的整个对话
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String delweb(HkRequest request, HkResponse response)
			throws Exception {
		long userId = this.getLoginUser(request).getUserId();
		long mainId = request.getLong("mainId");
		PvtChatMain main = this.msgService.getPvtChatMain(userId, mainId);
		if (main != null && main.getUserId() == userId) {
			this.msgService.deletePvtChatMain(userId, mainId);
		}
		return null;
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
		long mainId = request.getLong("mainId");
		long chatId = request.getLong("chatId");
		PvtChat chat = this.msgService.getPvtChat(chatId);
		if (chat != null && chat.getUserId() == userId) {
			this.msgService.deleteChat(userId, chatId);
			request.setSessionMessage("删除成功");
		}
		String from = request.getString("from");
		if (from != null && from.equals("pvt")) {
			return "r:/msg/pvt.do?mainId=" + mainId;
		}
		return "r:/msg/chat.do?mainId=" + mainId + "&page="
				+ request.getInt("repage");
	}

	/**
	 * 删除单条私信
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String delchatweb(HkRequest request, HkResponse response)
			throws Exception {
		long userId = this.getLoginUser(request).getUserId();
		long chatId = request.getLong("chatId");
		PvtChat chat = this.msgService.getPvtChat(chatId);
		if (chat != null && chat.getUserId() == userId) {
			this.msgService.deleteChat(userId, chatId);
		}
		return null;
	}
}