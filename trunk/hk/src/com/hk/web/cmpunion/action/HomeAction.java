package com.hk.web.cmpunion.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpProduct;
import com.hk.bean.CmpProductFav;
import com.hk.bean.Follow;
import com.hk.bean.User;
import com.hk.bean.UserProduct;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpProductService;
import com.hk.svr.FollowService;
import com.hk.svr.UserService;

@Component("/union/home")
public class HomeAction extends CmpUnionBaseAction {
	@Autowired
	private UserService userService;

	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private FollowService followService;

	public String execute(HkRequest req, HkResponse resp) {
		long userId = req.getLongAndSetAttr("userId");
		User user = this.userService.getUser(userId);
		User loginUser = this.getLoginUser(req);
		if (user == null) {
			return this.getNotFoundForward(resp);
		}
		boolean followed = false;
		if (loginUser != null && loginUser.getUserId() != userId) {
			Follow follow = this.followService.getFollow(userId, loginUser
					.getUserId());
			if (follow != null) {
				followed = true;
			}
		}
		req.setAttribute("followed", followed);
		req.setAttribute("user", user);
		// 我收藏的产品
		List<CmpProductFav> favlist = this.cmpProductService
				.getCmpProductFavListByUserId(userId, 0, 6);
		if (favlist.size() == 6) {
			req.setAttribute("morefav", true);
			favlist.remove(5);
		}
		List<Long> idList = new ArrayList<Long>();
		for (CmpProductFav fav : favlist) {
			idList.add(fav.getProductId());
		}
		List<CmpProduct> favproductlist = this.cmpProductService
				.getCmpProductListInId(idList);
		req.setAttribute("favproductlist", favproductlist);
		// 我买过的产品
		List<UserProduct> userplist = this.cmpProductService
				.getUserProductListByUserId(userId, 0, 6);
		if (userplist.size() == 6) {
			req.setAttribute("morebuy", true);
			userplist.remove(5);
		}
		idList = new ArrayList<Long>();
		for (UserProduct userProduct : userplist) {
			idList.add(userProduct.getProductId());
		}
		List<CmpProduct> userproductlist = this.cmpProductService
				.getCmpProductListInId(idList);
		req.setAttribute("userproductlist", userproductlist);
		return this.getUnionWapJsp("user/home.jsp");
	}
}