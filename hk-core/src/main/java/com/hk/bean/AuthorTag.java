package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "authortag", id = "tagid")
public class AuthorTag {
	private long tagId;

	private String name;

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}