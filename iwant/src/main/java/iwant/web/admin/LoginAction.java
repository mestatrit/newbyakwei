package iwant.web.admin;

import iwant.bean.City;
import iwant.svr.ZoneSvr;
import iwant.web.BaseAction;
import iwant.web.admin.util.Admin;
import iwant.web.admin.util.AdminUtil;
import iwant.web.admin.util.Err;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.dev3g.cactus.util.DataUtil;
import com.dev3g.cactus.web.action.HkRequest;
import com.dev3g.cactus.web.action.HkResponse;

@Lazy
@Component("/sitemgrlogin")
public class LoginAction extends BaseAction {

	@Autowired
	private ZoneSvr zoneSvr;

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
		City city = this.zoneSvr.getCity(req.getInt("cityid"));
		if (city == null) {
			return this
					.onError(req, Err.ADMIN_LOGIN_CITY_ERR, "loginerr", null);
		}
		Admin admin = new Admin();
		admin.setUsername(req.getHtmlRow("username"));
		admin.setPwd(req.getHtmlRow("pwd"));
		AdminUtil.setLoginAdmin(resp);
		AdminUtil.setLoginCity(resp, city.getCityid());
		return this.onSuccess(req, "loginok", null);
	}
}