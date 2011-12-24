package com.hk.web.cmpunion.msg.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.PvtChatMain;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.MsgService;
import com.hk.web.cmpunion.action.CmpUnionBaseAction;

@Component("/union/op/msg/pvtlist")
public class PvtListAction extends CmpUnionBaseAction {
	@Autowired
	private MsgService msgService;

	private int size = 10;

	public String execute(HkRequest req, HkResponse resp) {
		SimplePage page = req.getSimplePage(size);
		List<PvtChatMain> list = this.msgService.getPvtChatMainList(this
				.getLoginUser(req).getUserId(), page.getBegin(), size);
		page.setListSize(list.size());
		req.setAttribute("list", list);
		return this.getUnionWapJsp("msg/pvtlist.jsp");
	}
}