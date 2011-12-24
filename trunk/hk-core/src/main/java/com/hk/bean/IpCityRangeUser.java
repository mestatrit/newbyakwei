package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;

@Table(name = "ipcityrangeuser", id = "userid")
public class IpCityRangeUser {
	private int rangeId;

	private long userId;

	public int getRangeId() {
		return rangeId;
	}

	public void setRangeId(int rangeId) {
		this.rangeId = rangeId;
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