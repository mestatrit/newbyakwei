package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "cmpproducttagref")
public class CmpProductTagRef {
	private long tagId;

	private long companyId;

	private Company company;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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
}