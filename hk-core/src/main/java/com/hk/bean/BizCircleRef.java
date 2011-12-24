package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "bizcircleref")
public class BizCircleRef {
	private int companyId;

	private int circleId;

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getCircleId() {
		return circleId;
	}

	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}
}