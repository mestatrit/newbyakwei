package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "tagclick")
public class TagClick {
	private long tagId;

	private long userId;

	private int pcount;

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

	public int getPcount() {
		return pcount;
	}

	public void setPcount(int pcount) {
		this.pcount = pcount;
	}
}