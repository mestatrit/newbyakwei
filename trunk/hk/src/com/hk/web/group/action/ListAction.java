package com.hk.web.group.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.HkGroup;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.GroupService;
import com.hk.web.pub.action.BaseAction;

@Component("/group/list")
public class ListAction extends BaseAction {
	@Autowired
	private GroupService groupService;

	private int size = 20;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		SimplePage page = req.getSimplePage(size);
		List<HkGroup> list = this.groupService.getGroupList(page.getBegin(),
				size);
		page.setListSize(list.size());
		req.setAttribute("list", list);
		return "/WEB-INF/page/group/list.jsp";
	}
}