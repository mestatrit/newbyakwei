package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "companybizcircle")
public class CompanyBizCircle {
	private long companyId;

	private int circleId;

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public int getCircleId() {
		return circleId;
	}

	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}
}