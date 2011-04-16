package iwant.web.admin;

import iwant.web.BaseAction;
import iwant.web.admin.util.Admin;
import iwant.web.admin.util.AdminUtil;
import iwant.web.admin.util.Err;

import org.springframework.stereotype.Component;

import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/sitemgrlogin")
public class LoginAction extends BaseAction {

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
		if (!AdminUtil.isLogined(username, pwd)) {
			return this.onError(req, Err.ADMIN_LOGIN_ERR, "loginerr", null);
		}
		Admin admin = new Admin();
		admin.setUsername(req.getHtmlRow("username"));
		admin.setPwd(req.getHtmlRow("pwd"));
		AdminUtil.setLoginAdmin(req, resp, admin);
		return this.onSuccess(req, "loginok", null);
	}
}