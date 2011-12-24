package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;

@Table(name = "hkgroupuser", id = "sysid")
public class HkGroupUser {
	private long sysId;

	private int groupId;

	private long userId;

	private User user;

	public User getUser() {
		if (user == null) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			user = userService.getUser(userId);
		}
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}