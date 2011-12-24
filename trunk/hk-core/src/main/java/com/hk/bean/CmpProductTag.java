package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "cmpproducttag", id = "tagid")
public class CmpProductTag {
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