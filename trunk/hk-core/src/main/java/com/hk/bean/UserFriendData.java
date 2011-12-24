package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "userfrienddata", id = "userid")
public class UserFriendData {
	private long userId;

	private String friendData;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFriendData() {
		return friendData;
	}

	public void setFriendData(String friendData) {
		this.friendData = friendData;
	}
}