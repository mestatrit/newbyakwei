package com.hk.web.cmpunion.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpProduct;
import com.hk.bean.CmpUnionKind;
import com.hk.bean.User;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpProductService;
import com.hk.svr.CmpUnionService;

@Component("/union/product")
public class ProductAction extends CmpUnionBaseAction {
	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private CmpUnionService cmpUnionService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		int pid = req.getIntAndSetAttr("pid");
		User loginUser = this.getLoginUser(req);
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(pid);
		if (cmpProduct == null) {
			return this.getNotFoundForward(resp);
		}
		boolean fav = false;
		if (loginUser != null) {
			fav = this.cmpProductService.isFavProduct(loginUser.getUserId(),
					pid);
		}
		req.setAttribute("fav", fav);
		req.setAttribute("cmpProduct", cmpProduct);
		long kindId = cmpProduct.getCmpUnionKindId();
		if (kindId > 0) {
			CmpUnionKind cmpUnionKind = this.cmpUnionService
					.getCmpUnionKind(kindId);
			req.setAttribute("cmpUnionKind", cmpUnionKind);
			List<CmpUnionKind> list2 = new ArrayList<CmpUnionKind>();
			this.loadCmpUnionKindList(list2, kindId);
			req.setAttribute("list2", list2);
		}
		return this.getUnionWapJsp("product/product.jsp");
	}
}