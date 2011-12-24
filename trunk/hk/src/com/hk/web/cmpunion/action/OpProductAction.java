package com.hk.web.cmpunion.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpProduct;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpProductService;

@Component("/union/op/product")
public class OpProductAction extends CmpUnionBaseAction {
	@Autowired
	private CmpProductService cmpProductService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String fav(HkRequest req, HkResponse resp) throws Exception {
		long userId = this.getLoginUser(req).getUserId();
		long pid = req.getLong("pid");
		long uid = req.getLong("uid");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(pid);
		this.cmpProductService.favProduct(userId, pid, cmpProduct
				.getCompanyId());
		req.setSessionText("func.cmpproduct.favok");
		return "r:/union/product.do?uid=" + uid + "&pid=" + pid;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delfav(HkRequest req, HkResponse resp) throws Exception {
		long userId = this.getLoginUser(req).getUserId();
		long pid = req.getLong("pid");
		long uid = req.getLong("uid");
		this.cmpProductService.deleteFavProduct(userId, pid);
		req.setSessionText("func.cmpproduct.delfavok");
		return "r:/union/product.do?uid=" + uid + "&pid=" + pid;
	}
}