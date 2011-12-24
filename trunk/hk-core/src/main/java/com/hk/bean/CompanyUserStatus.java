package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "companyuserstatus", id = "sysid")
public class CompanyUserStatus {

	public static final byte USERSTATUS_WANT = 0;

	public static final byte USERSTATUS_DONE = 1;

	public static final byte OK_FLG = 1;

	public static final byte NONE_FLG = 0;

	@Id
	private long sysId;

	@Column
	private long companyId;

	@Column
	private long userId;

	@Column
	private byte userStatus;

	@Column
	private byte doneStatus;

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public byte getDoneStatus() {
		return doneStatus;
	}

	public void setDoneStatus(byte doneStatus) {
		this.doneStatus = doneStatus;
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

	public byte getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(byte userStatus) {
		this.userStatus = userStatus;
	}

	public boolean isDone() {
		if (this.doneStatus == 1) {
			return true;
		}
		return false;
	}

	public boolean isWant() {
		if (this.userStatus == 1) {
			return true;
		}
		return false;
	}
}