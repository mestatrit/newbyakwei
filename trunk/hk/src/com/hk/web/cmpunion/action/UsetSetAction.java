package com.hk.web.cmpunion.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.User;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.UserService;
import com.hk.svr.pub.Err;

@Component("/union/op/userset")
public class UsetSetAction extends CmpUnionBaseAction {
	@Autowired
	private UserService userService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("uid");
		return this.getUnionWapJsp("set/set.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toupdatenickname(HkRequest req, HkResponse resp)
			throws Exception {
		long userId = this.getLoginUser(req).getUserId();
		User user = this.userService.getUser(userId);
		req.setAttribute("user", user);
		return this.getUnionWapJsp("set/nickname.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updatenickname(HkRequest req, HkResponse resp)
			throws Exception {
		long uid = req.getLong("uid");
		long userId = this.getLoginUser(req).getUserId();
		String nickName = req.getString("nickName");
		int code = User.validateNickName(nickName);
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/union/op/userset_toupdatenickname.do";
		}
		if (!this.userService.updateNickName(userId, nickName)) {
			req.setText(Err.NICKNAME_DUPLICATE + "");
			return "/union/op/userset_toupdatenickname.do";
		}
		req.setSessionText("func.user.updatenicknameok");
		return "r:/union/home.do?uid=" + uid + "&userId=" + userId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toupdatepwd(HkRequest req, HkResponse resp) {
		return this.getUnionWapJsp("set/pwd.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updatepwd(HkRequest req, HkResponse resp) {
		long uid = req.getLong("uid");
		long userId = this.getLoginUser(req).getUserId();
		String oldPwd = req.getString("oldPwd");
		String newPwd = req.getString("newPwd");
		int code = User.validatePassword(newPwd);
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/union/op/userset_toupdatepwd.do";
		}
		if (!this.userService.updatePwd(userId, oldPwd, newPwd)) {
			req.setText(Err.USER_OLDPASSWORD_ERROR + "");
			return "/union/op/userset_toupdatepwd.do";
		}
		req.setSessionText("func.user.updatepwdok");
		return "r:/union/home.do?uid=" + uid + "&userId=" + userId;
	}
}