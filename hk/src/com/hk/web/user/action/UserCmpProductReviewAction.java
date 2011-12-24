package com.hk.web.user.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpProductReview;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpProductService;
import com.hk.svr.UserService;
import com.hk.web.company.action.CmpProductReviewVo;
import com.hk.web.pub.action.BaseAction;

@Component("/usercmpproductreview")
public class UserCmpProductReviewAction extends BaseAction {
	@Autowired
	private UserService userService;

	@Autowired
	private CmpProductService cmpProductService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLongAndSetAttr("userId");
		User user = this.userService.getUser(userId);
		SimplePage page = req.getSimplePage(20);
		List<CmpProductReview> cmpproductreviewlist = this.cmpProductService
				.getCmpProductReviewListByUserId(userId, page.getBegin(), page
						.getSize() + 1);
		if (cmpproductreviewlist.size() == page.getSize() + 1) {
			req.setAttribute("hasmore", true);
			cmpproductreviewlist.remove(cmpproductreviewlist.size() - 1);
		}
		List<CmpProductReviewVo> cmpproductreviewvolist = CmpProductReviewVo
				.createVoList(cmpproductreviewlist, this.getUrlInfoWeb(req),
						true, true);
		req.setAttribute("cmpproductreviewvolist", cmpproductreviewvolist);
		req.setAttribute("user", user);
		return this.getWeb3Jsp("user/usercmpproductreviewlist.jsp");
	}
}