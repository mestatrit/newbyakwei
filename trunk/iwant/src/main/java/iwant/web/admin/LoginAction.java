package iwant.web.admin;

import halo.util.DataUtil;
import halo.web.action.HkRequest;
import halo.web.action.HkResponse;
import iwant.bean.AdminUser;
import iwant.svr.AdminUserSvr;
import iwant.web.BaseAction;
import iwant.web.admin.util.AdminUtil;
import iwant.web.admin.util.Err;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("/sitemgrlogin")
public class LoginAction extends BaseAction {

	@Autowired
	private AdminUserSvr adminUserSvr;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		if (this.isForwardPage(req)) {
			return this.getAdminPath("login.jsp");
		}
		String username = req.getHtmlRow("username");
		String pwd = req.getHtmlRow("pwd");
		if (DataUtil.isEmpty(username) || DataUtil.isEmpty(pwd)) {
			return this.onError(req, Err.ADMIN_LOGIN_ERR, "loginerr", null);
		}
		AdminUser adminUser = this.adminUserSvr.getAdminUserByName(username);
		if (adminUser == null) {
			return this.onError(req, Err.ADMIN_LOGIN_ERR, "loginerr", null);
		}
		if (!adminUser.isLogin(pwd)) {
			return this.onError(req, Err.ADMIN_LOGIN_ERR, "loginerr", null);
		}
		AdminUtil.setLoginAdminUser(resp, adminUser);
		return this.onSuccess(req, "loginok", null);
	}
}