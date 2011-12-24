package com.hk.web.cmpunion.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.DefFollowUser;
import com.hk.bean.RegFrom;
import com.hk.bean.User;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.FollowService;
import com.hk.svr.UserService;
import com.hk.svr.friend.exception.AlreadyBlockException;
import com.hk.svr.pub.Err;
import com.hk.svr.user.exception.EmailDuplicateException;
import com.hk.svr.user.exception.MobileDuplicateException;

@Component("/union/reg")
public class RegAction extends CmpUnionBaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private FollowService followService;

	public String execute(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		if (this.getLoginUser(req) != null) {
			return "r:/union/union.do?uid=" + uid;
		}
		String input = req.getString("input", "");
		input = input.replaceAll("　", "");
		String password = req.getString("password");
		String password1 = req.getString("password1");
		req.setAttribute("input", input);
		if (input == null || password == null) {
			return "/union/reg_toreg.do";
		}
		if (!password.equals(password1)) {
			req.setMessage(req.getText("func.reg.pwdnotequal"));
			return "/union/reg_toreg.do";
		}
		int vacode = User.validateReg(input, password);
		if (vacode != Err.SUCCESS) {
			req.setText(vacode + "");
			return "/union/reg_toreg.do";
		}
		long userId = 0;
		try {
			userId = this.userService.createUserWithRegCode(input, password, req
					.getRemoteAddr(), null);
			// 注册来源
			this.userService.createRegfromUser(userId, RegFrom.CMPUNION, uid);
			// 添加默认关注
			List<DefFollowUser> list = this.userService.getDefFollowUserList(0,
					20);
			for (DefFollowUser o : list) {
				if (this.userService.getUser(o.getUserId()) != null) {
					try {
						this.followService.addFollow(userId, o.getUserId(), req
								.getRemoteAddr(), false);
					}
					catch (AlreadyBlockException e) {
					}
				}
			}
		}
		catch (EmailDuplicateException e) {
			req.setMessage(req.getText("func.mailalreadyexist"));
			return "/union/reg_toreg.do";
		}
		catch (MobileDuplicateException e) {
			req.setMessage(req.getText("func.mobilealreadyexist"));
			return "/union/reg_toreg.do";
		}
		CookieInfo cookieInfo = new CookieInfo();
		cookieInfo.setId(userId + "");
		cookieInfo.setInput(input);
		CmpUnionUtil.setCookieInfo(resp, cookieInfo, 1);
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
	public String toreg(HkRequest req, HkResponse resp) throws Exception {
		if (this.getLoginUser(req) != null) {
			return "r:/union/union.do?uid=" + req.getLong("uid");
		}
		req.reSetAttribute("uid");
		if (req.getSessionValue("forcreatecmp") != null) {
			return this.getUnionWapJsp("reg_cmp.jsp");
		}
		return this.getUnionWapJsp("reg.jsp");
	}
}