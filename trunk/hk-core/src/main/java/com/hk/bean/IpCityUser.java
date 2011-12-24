package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;

@Table(name = "ipcityuser", id = "userid")
public class IpCityUser {
	private int cityId;

	private long userId;

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
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