package com.hk.web.laba.action;

import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.web.pub.action.BaseAction;

/**
 * 喇叭的恢复列表,取消使用
 * 
 * @author akwei
 */
@Deprecated
public class ReListAction extends BaseAction {
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// long labaId = req.getLong("labaId");
		// int size = 21;
		// SimplePage page = req.getSimplePage(size);
		// List<LabaReply> list = labaService.getLabaReplyList(labaId, page
		// .getBegin(), size);
		// page.setListSize(list.size());
		// List<Laba> labalist = new ArrayList<Laba>();
		// for (LabaReply o : list) {
		// labalist.add(o.getReplyLaba());
		// }
		// List<LabaVo> labavolist = LabaVo.createVoList(labalist, this
		// .getLabaParserCfg(req));
		// req.setAttribute("labaId", labaId);
		// req.setAttribute("labavolist", labavolist);
		// String queryString = req.getQueryString();
		// req.setAttribute("queryString", queryString);
		return "/WEB-INF/page/laba/relist.jsp";
	}
}