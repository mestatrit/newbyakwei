package com.hk.web.cmpunion.msg.action;

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
import com.hk.web.cmpunion.action.CmpUnionBaseAction;
import com.hk.web.msg.action.PvtChatVo;

/**
 * 只查看前3条
 * 
 * @author akwei
 */
@Component("/union/op/msg/pvt")
public class PvtAction extends CmpUnionBaseAction {
	@Autowired
	private MsgService msgService;

	@Autowired
	private FollowService followService;

	public String execute(HkRequest req, HkResponse resp) {
		long uid = req.getLong("uid");
		long userId = this.getLoginUser(req).getUserId();
		long mainId = req.getLong("mainId");
		PvtChatMain main = this.msgService.getPvtChatMain(userId, mainId);
		if (main == null) {
			return "r:/union/op/msg/pvtlist.do?uid=" + uid;
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
		return this.getUnionWapJsp("msg/pvt.jsp");
	}

	/**
	 * 获取最后一条
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String last(HkRequest req, HkResponse resp) {
		long uid = req.getLong("uid");
		long userId = this.getLoginUser(req).getUserId();
		PvtChatMain main = this.msgService.getLastNoReadPvtChatMain(userId);
		if (main != null) {
			return "r:/union/op/msg/pvt.do?mainId=" + main.getMainId() + "&uid="
					+ uid;
		}
		return "r:/union/op/msg/pvtlist.do" + "&uid=" + uid;
	}

	/**
	 * 删除与某人的整个对话
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest req, HkResponse resp) {
		long uid = req.getLong("uid");
		long userId = this.getLoginUser(req).getUserId();
		long mainId = req.getLong("mainId");
		PvtChatMain main = this.msgService.getPvtChatMain(userId, mainId);
		if (main != null && main.getUserId() == userId) {
			this.msgService.deletePvtChatMain(userId, mainId);
		}
		req.setSessionMessage("删除私信成功");
		return "r:/union/op/msg/pvtlist.do" + "&uid=" + uid;
	}

	/**
	 * 删除单条私信
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delchat(HkRequest req, HkResponse resp) {
		long uid = req.getLong("uid");
		long userId = this.getLoginUser(req).getUserId();
		long mainId = req.getLong("mainId");
		long chatId = req.getLong("chatId");
		PvtChat chat = this.msgService.getPvtChat(chatId);
		if (chat != null && chat.getUserId() == userId) {
			this.msgService.deleteChat(userId, chatId);
			req.setSessionMessage("删除成功");
		}
		String from = req.getString("from");
		if (from != null && from.equals("pvt")) {
			return "r:/union/op/msg/pvt.do?mainId=" + mainId + "&uid=" + uid;
		}
		return "r:/union/op/msg/chat.do?mainId=" + mainId + "&page="
				+ req.getInt("repage") + "&uid=" + uid;
	}
}