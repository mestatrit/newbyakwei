package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 分期费用说明
 * 
 * @author akwei
 */
@Table(name = "cmpactcost", id = "costid")
public class CmpActCost {
	@Id
	private long costId;

	@Column
	private long actId;

	@Column
	private String name;

	@Column
	private double actCost;

	@Column
	private String intro;

	@Column
	private long companyId;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getActCost() {
		return actCost;
	}

	public void setActCost(double actCost) {
		this.actCost = actCost;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int validate() {
		String s = DataUtil.toTextRow(name);
		if (DataUtil.isEmpty(s) || s.length() > 20) {
			return Err.CMPACTCOST_NAME_ERROR;
		}
		s = DataUtil.toTextRow(intro);
		if (s != null && s.length() > 100) {
			return Err.CMPACTCOST_INTRO_ERROR;
		}
		if (this.actCost <= 0) {
			return Err.CMPACTCOST_ACTCOST_ERROR;
		}
		return Err.SUCCESS;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}