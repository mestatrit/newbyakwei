package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "regfromuser", id = "userid")
public class RegfromUser {
	private long userId;

	private int regfrom;

	private long fromId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getRegfrom() {
		return regfrom;
	}

	public void setRegfrom(int regfrom) {
		this.regfrom = regfrom;
	}

	public long getFromId() {
		return fromId;
	}

	public void setFromId(long fromId) {
		this.fromId = fromId;
	}
}