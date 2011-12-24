package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;

@Table(name = "employee")
public class Employee {
	public static final byte LEVEL_SUPERADMIN = 1;

	public static final byte LEVEL_ADMIN = 2;

	public static final byte LEVEL_NORMAL = 3;

	private int companyId;

	private long userId;

	private byte level;

	private User user;

	public boolean isAdmin() {
		if (level == LEVEL_ADMIN || level == LEVEL_SUPERADMIN) {
			return true;
		}
		return false;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		if (user == null) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			user = userService.getUser(userId);
		}
		return user;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public byte getLevel() {
		return level;
	}

	public void setLevel(byte level) {
		this.level = level;
	}
}