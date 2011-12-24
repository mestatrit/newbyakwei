package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "cmptagref")
public class CmpTagRef {
	@Id
	private long tagId;

	@Column
	private long companyId;

	@Column
	private long userId;

	@Column
	private String name;

	@Column
	private int pcityId;

	private Company company;

	public void setCompany(Company company) {
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public int getPcityId() {
		return pcityId;
	}

	public void setPcityId(int pcityId) {
		this.pcityId = pcityId;
	}
}