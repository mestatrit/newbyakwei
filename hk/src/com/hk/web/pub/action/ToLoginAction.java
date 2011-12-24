package com.hk.web.pub.action;

import org.springframework.stereotype.Component;
import com.hk.bean.User;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/tologin")
public class ToLoginAction extends BaseAction {
	public String execute(HkRequest req, HkResponse resp) {
		boolean login = false;
		User user = this.getLoginUser(req);
		if (user != null) {
			return "r:/square.do";
		}
		String input = this.getInput(req);
		req.setAttribute("input", input);
		req.setAttribute("login", login);
		req.reSetAttribute("wapflg");
		return "/WEB-INF/page/login.jsp";
	}
}