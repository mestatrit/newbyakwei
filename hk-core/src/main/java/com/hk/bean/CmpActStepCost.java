package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.Err;

@Table(name = "cmpactstepcost", id = "costid")
public class CmpActStepCost {
	@Id
	private long costId;

	@Column
	private long actId;

	@Column
	private int userCount;

	@Column
	private double actCost;

	@Column
	private long companyId;

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public long getCostId() {
		return costId;
	}

	public void setCostId(long costId) {
		this.costId = costId;
	}

	public long getActId() {
		return actId;
	}

	public void setActId(long actId) {
		this.actId = actId;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public double getActCost() {
		return actCost;
	}

	public void setActCost(double actCost) {
		this.actCost = actCost;
	}

	public int validate() {
		if (this.userCount <= 0) {
			return Err.CMPACTSTEPCOST_USERCOUNT_ERROR;
		}
		if (this.actCost <= 0) {
			return Err.CMPACTSTEPCOST_ACTCOST_ERROR;
		}
		return Err.SUCCESS;
	}
}