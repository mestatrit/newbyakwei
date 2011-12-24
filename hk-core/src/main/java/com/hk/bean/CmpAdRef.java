package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 广告推荐关联表
 * 
 * @author akwei
 */
@Table(name = "cmpadref")
public class CmpAdRef {

	@Id
	private long oid;

	@Column
	private long companyId;

	@Column
	private long adid;

	private CmpAd cmpAd;

	public void setCmpAd(CmpAd cmpAd) {
		this.cmpAd = cmpAd;
	}

	public CmpAd getCmpAd() {
		return cmpAd;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getAdid() {
		return adid;
	}

	public void setAdid(long adid) {
		this.adid = adid;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}