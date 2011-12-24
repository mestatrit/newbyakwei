package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "cmpfollow", id = "sysid")
public class CmpFollow {
	private long sysId;

	private long userId;

	private long companyId;

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}