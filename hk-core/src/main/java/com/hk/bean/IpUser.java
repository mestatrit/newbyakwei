package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;

@Table(name = "ipuser")
public class IpUser {
	private long ipnumber;

	private long userId;

	public long getIpnumber() {
		return ipnumber;
	}

	public void setIpnumber(long ipnumber) {
		this.ipnumber = ipnumber;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public User getUser() {
		UserService userService = (UserService) HkUtil.getBean("userService");
		return userService.getUser(userId);
	}
}