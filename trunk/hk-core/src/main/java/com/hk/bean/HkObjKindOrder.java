package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.Err;

@Table(name = "hkobjkindorder", id = "oid")
public class HkObjKindOrder extends HkOrder {
	private long oid;

	private int kindId;

	private CompanyKind companyKind;

	public void setCompanyKind(CompanyKind companyKind) {
		this.companyKind = companyKind;
	}

	public CompanyKind getCompanyKind() {
		return companyKind;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public int getKindId() {
		return kindId;
	}

	public void setKindId(int kindId) {
		this.kindId = kindId;
	}

	public int validate() {
		if (this.kindId < 1) {
			return Err.COMPANYKINDID_ERROR;
		}
		if (this.money < 1) {
			return Err.HKOBJKINDORDER_MONEY_ERROR;
		}
		if (this.pday < 1) {
			return Err.HKOBJKINDORDER_PDAY_ERROR;
		}
		if (this.stopflg != STOPFLG_Y && this.stopflg != STOPFLG_N) {
			return Err.HKOBJKINDORDER_STOPFLG_ERROR;
		}
		if (this.cityId < 0) {
			return Err.ZONE_ERROR;
		}
		return Err.SUCCESS;
	}
}