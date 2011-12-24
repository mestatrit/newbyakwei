package com.hk.web.pub.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.AdminUser;
import com.hk.bean.AuthorApply;
import com.hk.bean.Bomber;
import com.hk.bean.User;
import com.hk.bean.UserCmpFunc;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.AuthorTagService;
import com.hk.svr.BombService;
import com.hk.svr.CompanyService;
import com.hk.svr.UserCmpFuncService;
import com.hk.svr.UserService;

/**
 * 更多功能页面
 * 
 * @author akwei
 */
@Component("/more")
public class MoreAction extends BaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private BombService bombService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private AuthorTagService authorTagService;

	@Autowired
	private UserCmpFuncService userCmpFuncService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		boolean mobileBind = false;
		boolean superAdmin = false;
		boolean bomb = false;
		boolean bombadmin = false;
		boolean userauth = false;
		if (loginUser != null) {
			AuthorApply apply = this.authorTagService
					.getAuthorApplyByUserId(loginUser.getUserId());
			if (apply != null) {
				req.setAttribute("hasauthorapply", true);
			}
			Bomber bomber = this.bombService.getBomber(loginUser.getUserId());
			if (bomber != null) {
				bomb = true;
				if (bomber.getUserLevel() == Bomber.USERLEVEL_ADMIN
						|| bomber.getUserLevel() == Bomber.USERLEVEL_SUPERADMIN) {
					bombadmin = true;
				}
			}
			AdminUser adminUser = this.userService.getAdminUser(loginUser
					.getUserId());
			if (adminUser != null) {
				if (adminUser.getAdminLevel() == 1) {
					superAdmin = true;
				}
				req.setAttribute("superAdmin", superAdmin);
				req.setAttribute("adminUser", adminUser);
			}
			UserOtherInfo userOtherInfo = this.userService
					.getUserOtherInfo(loginUser.getUserId());
			if (userOtherInfo.getMobileBind() == UserOtherInfo.MOBILE_BIND) {
				mobileBind = true;
			}
			if (this.companyService.getCompanyListByUserId(
					loginUser.getUserId(), 0, 1).size() > 0) {
				userauth = true;
			}
		}
		if (loginUser != null) {
			UserCmpFunc userCmpFunc = this.userCmpFuncService
					.getUserCmpFunc(loginUser.getUserId());
			req.setAttribute("userCmpFunc", userCmpFunc);
			if (userCmpFunc != null && userCmpFunc.isBoxOpen()) {
				req.setAttribute("cancreatebox", true);
			}
		}
		req.setAttribute("userauth", userauth);
		req.setAttribute("bombadmin", bombadmin);
		req.setAttribute("bomb", bomb);
		req.setAttribute("mobileBind", mobileBind);
		return "/WEB-INF/page/more.jsp";
	}
}