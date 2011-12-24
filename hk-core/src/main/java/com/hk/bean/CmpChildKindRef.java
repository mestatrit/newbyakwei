package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "cmpchildkindref")
public class CmpChildKindRef {
	private int oid;

	private long companyId;

	private int cityId;

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}