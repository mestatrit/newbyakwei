package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "welprouser", id = "oid")
public class WelProUser {
	private long oid;

	private long prouserId;

	private long userId;

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getProuserId() {
		return prouserId;
	}

	public void setProuserId(long prouserId) {
		this.prouserId = prouserId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}