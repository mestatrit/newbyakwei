package com.hk.web.user.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpProduct;
import com.hk.bean.CmpProductFav;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpProductService;
import com.hk.svr.UserService;
import com.hk.web.company.action.ProductVo;
import com.hk.web.pub.action.BaseAction;

@Component("/userproductfav")
public class UserProductFavAction extends BaseAction {
	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private UserService userService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLongAndSetAttr("userId");
		User user = this.userService.getUser(userId);
		if (user == null) {
			this.getNotFoundForward(resp);
		}
		req.setAttribute("user", user);
		SimplePage page = req.getSimplePage(20);
		List<CmpProductFav> productfavlist = this.cmpProductService
				.getCmpProductFavListByUserId(userId, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, productfavlist);
		List<Long> idList = new ArrayList<Long>();
		for (CmpProductFav fav : productfavlist) {
			idList.add(fav.getProductId());
		}
		Map<Long, CmpProduct> promap = this.cmpProductService
				.getCmpProductMapInId(idList);
		List<CmpProduct> favproductlist = new ArrayList<CmpProduct>();
		for (CmpProductFav fav : productfavlist) {
			favproductlist.add(promap.get(fav.getProductId()));
		}
		List<ProductVo> productvolist = ProductVo.createVoList(favproductlist,
				this.getShoppingCard(req));
		req.setAttribute("productvolist", productvolist);
		return this.getWeb3Jsp("/user/userproductfavlist.jsp");
	}
}