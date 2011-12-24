package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "userwebbind", id = "userid")
public class UserWebBind {
	private long userId;

	private String boseeId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getBoseeId() {
		return boseeId;
	}

	public void setBoseeId(String boseeId) {
		this.boseeId = boseeId;
	}
}