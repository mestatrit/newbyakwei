package com.hk.web.yuming;

import org.springframework.stereotype.Component;

import com.hk.frame.util.DataUtil;
import com.hk.frame.web.action.Action;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

/**
 * 用户名：root
 * 密码：akafly
 * 
 * @author User
 */
@Component("/yuming/login")
public class LoginAction implements Action {

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		if (this.isForwardPage(req)) {
			return this.getPath("login");
		}
		String name = req.getString("name");
		String pwd = req.getString("pwd");
		if (DataUtil.isEmpty(name) || DataUtil.isEmpty(pwd)) {
			return "r:/yuming/login.do";
		}
		if (!name.equals("root") || !pwd.equals("bosee.cn")) {
			req.setAttribute("nopermission", true);
			return this.getPath("login");
		}
		req.setSessionValue("rootlogin", true);
		return "r:/yuming/ymmgr/domain_list.do";
	}

	private String getPath(String name) {
		return "/WEB-INF/yuming/admin/" + name + ".jsp";
	}

	protected boolean isForwardPage(HkRequest req) {
		if (req.getInt("ch") == 0) {
			return true;
		}
		return false;
	}
}
