package com.hk.web.user.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.CompanyReview;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.UserService;
import com.hk.web.company.action.CompanyReviewVo;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.HkWebUtil;

/**
 * 用户的足迹点评，显示用户的所有点评
 * 
 * @author akwei
 */
@Component("/usercmpreview")
public class UserCmpReviewAction extends BaseAction {
	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		req.setAttribute("userId", userId);
		SimplePage page = req.getSimplePage(20);
		req.setAttribute("page", page.getPage());
		List<CompanyReview> reviewlist = this.companyService
				.getCompanyReviewListByUserId(userId, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, reviewlist);
		List<CompanyReviewVo> rvolist = CompanyReviewVo.createVoList(
				reviewlist, HkWebUtil.getLabaUrlInfoWeb(req.getContextPath()));
		List<UserCmpReviewVo> usercmpreviewvolist = UserCmpReviewVo
				.makeList(rvolist);
		req.setAttribute("usercmpreviewvolist", usercmpreviewvolist);
		User user = this.userService.getUser(userId);
		req.setAttribute("user", user);
		return this.getWeb3Jsp("user/usercmpreviewlist.jsp");
	}
}