package com.hk.web.user.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.User;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.UserService;
import com.hk.web.pub.action.BaseAction;

@Component("/op/prouser")
public class OpProUserAction extends BaseAction {
	@Autowired
	private UserService userService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 加入欢迎某人的行列
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String add(HkRequest req, HkResponse resp) throws Exception {
		long prouserId = req.getLong("prouserId");
		User loginUser = this.getLoginUser(req);
		this.userService.createWelProUser(loginUser.getUserId(), prouserId);
		req.setSessionText("op.submitinfook");
		return "r:/prouser.do?prouserId=" + prouserId;
	}
}