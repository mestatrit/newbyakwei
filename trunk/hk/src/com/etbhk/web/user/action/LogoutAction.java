package com.etbhk.web.user.action;

import org.springframework.stereotype.Component;

import com.etbhk.util.BaseTaoBaoAction;
import com.etbhk.util.TbHkWebUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/tb/logout")
public class LogoutAction extends BaseTaoBaoAction {

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		TbHkWebUtil.clearLoginUser(req, resp);
		return "r:/tb/index";
	}
}