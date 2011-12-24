package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "hkusergroup")
public class HkUserGroup {
	private long sysId;

	private long userId;

	private int groupId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}
}