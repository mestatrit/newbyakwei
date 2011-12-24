package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "hkgroup", id = "groupid")
public class HkGroup {
	private int groupId;

	private String name;

	private int ucount;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUcount() {
		return ucount;
	}

	public void setUcount(int ucount) {
		this.ucount = ucount;
	}
}