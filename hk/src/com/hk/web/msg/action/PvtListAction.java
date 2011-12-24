package com.hk.web.msg.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.PvtChatMain;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.MsgService;
import com.hk.web.pub.action.BaseAction;

@Component("/msg/pvtlist")
public class PvtListAction extends BaseAction {
	@Autowired
	private MsgService msgService;

	private int size = 10;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		SimplePage page = req.getSimplePage(size);
		List<PvtChatMain> list = this.msgService.getPvtChatMainList(this
				.getLoginUser(req).getUserId(), page.getBegin(), size);
		page.setListSize(list.size());
		req.setAttribute("list", list);
		return "/WEB-INF/page/msg/pvtlist.jsp";
	}

	/**
	 * pc查看
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String web(HkRequest req, HkResponse resp) {
		long userId = this.getLoginUser(req).getUserId();
		SimplePage page = req.getSimplePage(20);
		List<PvtChatMain> list = this.msgService.getPvtChatMainList(userId,
				page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWeb3Jsp("msg/pvtlist.jsp");
	}
}