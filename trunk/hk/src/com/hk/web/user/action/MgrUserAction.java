package com.hk.web.user.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.UserService;
import com.hk.web.pub.action.BaseAction;

@Component("/admin/mgruser")
public class MgrUserAction extends BaseAction {
	@Autowired
	private UserService userService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		String key = req.getString("key", "");
		key = key.replaceAll("＠", "@");
		String mobile = null;
		String email = null;
		String nickName = null;
		User user = null;
		UserOtherInfo info = null;
		boolean locked = false;
		if (!this.isEmpty(key)) {
			if (key.indexOf('@') != -1) {
				email = key;
				info = this.userService.getUserOtherInfoByeEmail(email);
				if (info != null) {
					user = this.userService.getUser(info.getUserId());
				}
			}
			else if (key.length() == 11) {
				mobile = key;
				info = this.userService.getUserOtherInfoByMobile(mobile);
				if (info != null) {
					user = this.userService.getUser(info.getUserId());
				}
			}
			else {
				nickName = key;
				user = this.userService.getUserByNickName(nickName);
				if (user != null) {
					info = this.userService.getUserOtherInfo(user.getUserId());
				}
			}
		}
		if (info != null
				&& info.getUserStatus() == UserOtherInfo.USERSTATUS_STOP) {
			locked = true;
		}
		req.setAttribute("user", user);
		req.setAttribute("search", true);
		req.setAttribute("locked", locked);
		req.setEncodeAttribute("key", key);
		return "/WEB-INF/page/user/mgr/search.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String lock(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		this.userService.setUserStop(userId);
		req.setSessionMessage("封锁成功");
		return this.backString(req);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String unlock(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		this.userService.setUserNormal(userId);
		req.setSessionMessage("解除封锁成功");
		return this.backString(req);
	}

	private String backString(HkRequest req) {
		return "r:/admin/mgruser.do?key=" + req.getEncodeString("key");
	}
}