package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "companyreflaba", id = "labaid")
public class CompanyRefLaba {
	private long labaId;

	private long companyId;

	public long getLabaId() {
		return labaId;
	}

	public void setLabaId(long labaId) {
		this.labaId = labaId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}