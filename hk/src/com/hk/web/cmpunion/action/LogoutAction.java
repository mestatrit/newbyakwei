package com.hk.web.cmpunion.action;

import org.springframework.stereotype.Component;

import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.web.util.HkWebUtil;

@Component("/union/logout")
public class LogoutAction extends CmpUnionBaseAction {
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		HkWebUtil.setWapCookieConfig(resp);
		this.removeLoginUser(req);
		CookieInfo cookieInfo = CmpUnionUtil.getCookieInfo(req);
		if (cookieInfo != null) {
			cookieInfo.setId("0");
			CmpUnionUtil.setCookieInfo(resp, cookieInfo, 1);
		}
		return "r:/union/union.do?uid=" + req.getLong("uid");
	}
}