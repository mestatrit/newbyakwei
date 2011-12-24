package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "cmpgroupuser", id = "oid")
public class CmpGroupUser {
	private long oid;

	private long userId;

	private long cmpgroupId;

	private String name;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCmpgroupId() {
		return cmpgroupId;
	}

	public void setCmpgroupId(long cmpgroupId) {
		this.cmpgroupId = cmpgroupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}