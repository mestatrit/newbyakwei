package com.hk.web.msg.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.Follow;
import com.hk.bean.PvtChat;
import com.hk.bean.PvtChatMain;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.FollowService;
import com.hk.svr.MsgService;
import com.hk.web.pub.action.BaseAction;

/**
 * 私信单页,看过后置为已读
 * 
 * @author akwei
 */
@Component("/msg/chat")
public class ChatAction extends BaseAction {
	@Autowired
	private MsgService msgService;

	@Autowired
	private FollowService followService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long userId = this.getLoginUser(req).getUserId();
		long mainId = req.getLong("mainId");
		int sms = req.getInt("sms");
		PvtChatMain main = this.msgService.getPvtChatMain(userId, mainId);
		if (main == null) {
			return "r:/msg/pvtlist.do";
		}
		if (main.getReadflg() == PvtChatMain.READ_N) {
			this.msgService.setRead(userId, mainId);
		}
		int size = 10;
		SimplePage page = req.getSimplePage(size);
		List<PvtChat> list = null;
		if (sms == 1) {// 过滤查看短信发送的私信
			list = this.msgService.getPvtChatList(userId, mainId,
					PvtChat.SmsFLG_Y, page.getBegin(), size + 1);
		}
		else {
			list = this.msgService.getPvtChatList(userId, mainId, page
					.getBegin(), size + 1);
		}
		if (list.size() < size + 1) {
			// page.setListSize(list.size());
		}
		else {
			list.remove(list.size() - 1);
			page.setListSize(list.size());
		}
		List<PvtChatVo> volist = PvtChatVo.createVoList(list);
		Follow follow = this.followService.getFollow(userId, main.getUser2Id());
		boolean notFriend = false;
		if (follow == null) {
			notFriend = true;
		}
		req.setAttribute("notFriend", notFriend);
		req.setAttribute("volist", volist);
		req.setAttribute("mainId", mainId);
		req.setAttribute("main", main);
		req.setAttribute("list", list);
		req.setAttribute("sms", sms);
		req.setAttribute("canSms", this.isCanSms(req, main.getUser2Id()));// 是否可以发送短信
		req.reSetAttribute("simplestate");
		return "/WEB-INF/page/msg/chat.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String web(HkRequest req, HkResponse resp) throws Exception {
		long userId = this.getLoginUser(req).getUserId();
		long mainId = req.getLong("mainId");
		int sms = req.getInt("sms");
		PvtChatMain main = this.msgService.getPvtChatMain(userId, mainId);
		if (main == null) {
			return null;
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
		Follow follow = this.followService.getFollow(userId, main.getUser2Id());
		boolean notFriend = false;
		if (follow == null) {
			notFriend = true;
		}
		req.setAttribute("page", page.getPage());
		req.setAttribute("notFriend", notFriend);
		req.setAttribute("volist", volist);
		req.setAttribute("mainId", mainId);
		req.setAttribute("main", main);
		req.setAttribute("list", list);
		req.setAttribute("sms", sms);
		req.setAttribute("canSms", this.isCanSms(req, main.getUser2Id()));// 是否可以发送短信
		req.reSetAttribute("simplestate");
		return this.getWeb3Jsp("msg/chat.jsp");
	}
}