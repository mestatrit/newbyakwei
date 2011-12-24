package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "userrecentupdate", id = "userid")
public class UserRecentUpdate {
	private long userId;

	private int last30LabaCount;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getLast30LabaCount() {
		return last30LabaCount;
	}

	public void setLast30LabaCount(int last30LabaCount) {
		this.last30LabaCount = last30LabaCount;
	}
}