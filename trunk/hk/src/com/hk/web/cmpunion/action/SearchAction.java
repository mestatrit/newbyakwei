package com.hk.web.cmpunion.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpProduct;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpProductService;

@Component("/union/s")
public class SearchAction extends CmpUnionBaseAction {
	@Autowired
	private CmpProductService cmpProductService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		String w = req.getString("w");
		long uid = req.getLong("uid");
		SimplePage page = req.getSimplePage(20);
		List<CmpProduct> list = this.cmpProductService.getCmpProductListByUid(
				uid, w, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.setEncodeAttribute("w", w);
		return this.getUnionWapJsp("/search/productlist.jsp");
	}
}