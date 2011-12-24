package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 企业语言关联
 * 
 * @author akwei
 */
@Table(name = "cmplanguageref")
public class CmpLanguageRef {

	public CmpLanguageRef() {
	}

	public CmpLanguageRef(long companyId, long refCompanyId) {
		this.companyId = companyId;
		this.refCompanyId = refCompanyId;
	}

	@Id
	private long oid;

	@Column
	private long companyId;

	@Column
	private long refCompanyId;

	private CmpInfo refCmpInfo;

	public void setRefCmpInfo(CmpInfo refCmpInfo) {
		this.refCmpInfo = refCmpInfo;
	}

	public CmpInfo getRefCmpInfo() {
		return refCmpInfo;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getRefCompanyId() {
		return refCompanyId;
	}

	public void setRefCompanyId(long refCompanyId) {
		this.refCompanyId = refCompanyId;
	}
}