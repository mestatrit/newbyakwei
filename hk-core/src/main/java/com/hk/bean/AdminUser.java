package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "adminuser", id = "userid")
public class AdminUser {
	public static final byte LEVEL_NORMAL = 0;

	public static final byte LEVEL_SUPER = 1;

	private long userId;

	private byte adminLevel;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public byte getAdminLevel() {
		return adminLevel;
	}

	public void setAdminLevel(byte adminLevel) {
		this.adminLevel = adminLevel;
	}
}