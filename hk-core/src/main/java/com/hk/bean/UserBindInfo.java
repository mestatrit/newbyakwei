package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "userbindinfo", id = "userid")
public class UserBindInfo {
	private long userId;

	private String msn;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}
}