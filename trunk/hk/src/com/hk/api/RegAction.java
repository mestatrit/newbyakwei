package com.hk.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.DefFollowUser;
import com.hk.bean.RegFrom;
import com.hk.bean.User;
import com.hk.bean.UserWebBind;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.FollowService;
import com.hk.svr.UserService;
import com.hk.svr.friend.exception.AlreadyBlockException;
import com.hk.svr.pub.Err;
import com.hk.svr.user.exception.EmailDuplicateException;
import com.hk.svr.user.exception.MobileDuplicateException;
import com.hk.web.pub.action.BaseAction;

@Component("/api/reg")
public class RegAction extends BaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private FollowService followService;

	public String execute(HkRequest req, HkResponse resp) {
		String input = req.getString("input");
		String password = req.getString("pwd");
		String ip = req.getString("ip");
		String boseeId = req.getString("boseeid");
		int code = User.validateReg(input, password);
		if (code != Err.SUCCESS) {
			if (code == Err.EMAIL_ERROR) {
				resp.sendHtml(-1);
			}
			else if (code == Err.MOBILE_ERROR) {
				resp.sendHtml(-3);
			}
			else if (code == Err.PASSWORD_DATA_ERROR) {
				resp.sendHtml(-2);
			}
			return null;
		}
		try {
			long userId = this.userService.createUser(input, password, ip);
			// 添加默认关注
			List<DefFollowUser> list = this.userService.getDefFollowUserList(0,
					20);
			for (DefFollowUser o : list) {
				try {
					this.followService.addFollow(userId, o.getUserId(), ip,
							false);
				}
				catch (AlreadyBlockException e) {
					// TODO Auto-generated catch block
				}
			}
			UserWebBind userWebBind = new UserWebBind();
			userWebBind.setUserId(userId);
			userWebBind.setBoseeId(boseeId);
			this.userService.createUserWebBind(userWebBind);
			this.userService.createRegfromUser(userId, RegFrom.BOSEE, 0);
			resp.sendHtml(userId);
			return null;
		}
		catch (EmailDuplicateException e) {
			resp.sendHtml(-4);
			return null;
		}
		catch (MobileDuplicateException e) {
			resp.sendHtml(-5);
			return null;
		}
	}
}