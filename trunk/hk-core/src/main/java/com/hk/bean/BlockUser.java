package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "blockuser")
public class BlockUser {
	private long userId;

	private long blockUserId;

	private User blockUser;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getBlockUserId() {
		return blockUserId;
	}

	public void setBlockUserId(long blockUserId) {
		this.blockUserId = blockUserId;
	}

	public User getBlockUser() {
		return blockUser;
	}

	public void setBlockUser(User blockUser) {
		this.blockUser = blockUser;
	}
}