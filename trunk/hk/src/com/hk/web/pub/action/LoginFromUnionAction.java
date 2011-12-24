package com.hk.web.pub.action;

import org.springframework.stereotype.Component;

import com.hk.bean.User;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.web.cmpunion.action.CmpUnionUtil;
import com.hk.web.util.HkStatus;
import com.hk.web.util.HkWebUtil;

@Component("/loginfromunion")
public class LoginFromUnionAction extends BaseAction {
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		User hkLoginUser = this.getLoginUser(req);
		if (hkLoginUser != null) {
			return "r:" + req.getReturnUrl();
		}
		User loginUser = CmpUnionUtil.getLoginUser(req);
		if (loginUser != null) {
			HkStatus hkStatus = HkWebUtil.getHkStatus(req);
			if (hkStatus == null) {
				hkStatus = new HkStatus();
			}
			hkStatus.setUserId(loginUser.getUserId());
			hkStatus.setInput("");
			HkWebUtil.setHkCookie(req,resp, hkStatus);
		}
		return "r:" + req.getReturnUrl();
	}
}