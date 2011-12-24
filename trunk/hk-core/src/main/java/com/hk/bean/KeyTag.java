package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "keytag", id = "tagid")
public class KeyTag {
	private long tagId;

	private String name;

	private int searchCount;

	public int getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}

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