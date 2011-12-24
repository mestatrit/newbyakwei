package com.hk.web.hk4.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.processor.UserLoginResult;
import com.hk.svr.processor.UserProcessor;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

@Component("/h4/login")
public class LoginAction extends BaseAction {

	@Autowired
	private UserProcessor userProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			return "r:/user/" + loginUser.getUserId();
		}
		int ch = req.getInt("ch");
		if (ch == 1) {
			return this.processloginforweb4(req, resp);
		}
		String input = this.getInput(req);
		req.setAttribute("input", input);
		return this.getWeb4Jsp("login.jsp");
	}

	public String processloginforweb4(HkRequest req, HkResponse resp) {
		String input = req.getString("input", "");
		input = input.replaceAll("　", "").replaceAll("＠", "@");
		String password = req.getString("password", null);
		UserLoginResult userLoginResult = this.userProcessor.login(input,
				password);
		if (userLoginResult == null) {
			return this.onError(req, Err.USER_LOGIN_INPUT_EMPTY, "loginerror",
					null);
		}
		if (userLoginResult.getError() != Err.SUCCESS) {
			return this.onError(req, userLoginResult.getError(), "loginerror",
					null);
		}
		long userId = userLoginResult.getUserId();
		req.setSessionValue("login_userId", userId);
		this.processLoginAndReg(req, resp, input, null, false, true);
		return this.onSuccess2(req, "loginok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String checkip(HkRequest req, HkResponse resp) {
		// String ip = req.getRemoteAddr();
		// ip = "58.024.104.000";
		// if (ip != null) {
		// IpCity ipCity = this.ipCityService.getIpCityByIp(ip);
		// if (ipCity != null) {
		// String name = DataUtil.filterZoneName(ipCity.getName());
		// City city = this.zoneService.getCityLike(name);
		// if (city != null) {
		// if (this.getPcityId(req) != city.getCityId()) {
		// req.setAttribute("city", ZoneUtil.getCity(this
		// .getPcityId(req)));
		// req.setAttribute("ip_city", city);
		// return this.getWeb4Jsp("pub/changecity.jsp");
		// }
		// }
		// }
		// }
		if (DataUtil.isNotEmpty(req.getReturnUrl())) {
			return "r:" + req.getReturnUrl();
		}
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			return "r:/user/" + loginUser.getUserId();
		}
		return null;
	}
}