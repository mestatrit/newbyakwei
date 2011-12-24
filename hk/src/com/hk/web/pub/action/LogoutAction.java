package com.hk.web.pub.action;

import org.springframework.stereotype.Component;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.web.util.HkWebUtil;

@Component("/logout")
public class LogoutAction extends BaseAction {

	public String execute(HkRequest req, HkResponse resp) {
		HkWebUtil.removeLoginUser(req, resp);
		HkWebUtil.setWapCookieConfig(resp);
		return "r:/m";
	}

	public String web(HkRequest req, HkResponse resp) {
		HkWebUtil.removeLoginUser(req, resp);
		HkWebUtil.setWapCookieConfig(resp);
		return "r:/reg_toregweb.do";
	}

	public String web4(HkRequest req, HkResponse resp) {
		HkWebUtil.removeLoginUser(req, resp);
		HkWebUtil.setWapCookieConfig(resp);
		return "r:/login";
	}
}