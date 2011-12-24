package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "usercmpreview")
public class UserCmpReview {
	private long userId;

	private long labaId;

	private long companyId;

	private Company company;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

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