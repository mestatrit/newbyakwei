package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "cmpwatch", id = "sysid")
public class CmpWatch {
	public static final byte DUTY_N = 0;

	public static final byte DUTY_Y = 1;

	private long sysId;

	private long companyId;

	private long userId;

	private byte duty;

	private User user;

	public User getUser() {
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

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public byte getDuty() {
		return duty;
	}

	public void setDuty(byte duty) {
		this.duty = duty;
	}
}