package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "protectednickname", id = "userid")
public class ProtectedNickName {
	private long userId;

	private String name;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}