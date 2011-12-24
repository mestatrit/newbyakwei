package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "userauthortag")
public class UserAuthorTag {
	private long tagId;

	private long userId;

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}