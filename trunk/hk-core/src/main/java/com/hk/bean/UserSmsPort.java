package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "usersmsport", id = "sysid")
public class UserSmsPort {
	private long sysId;

	private String port;

	private long userId;

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}