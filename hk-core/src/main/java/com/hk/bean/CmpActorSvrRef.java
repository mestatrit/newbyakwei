package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "cmpactorsvrref")
public class CmpActorSvrRef {

	@Id
	private long oid;

	@Column
	private long actorId;

	@Column
	private long svrId;

	@Column
	private long companyId;

	private CmpSvr cmpSvr;

	private CmpActor cmpActor;

	public void setCmpActor(CmpActor cmpActor) {
		this.cmpActor = cmpActor;
	}

	public CmpActor getCmpActor() {
		return cmpActor;
	}

	public void setCmpSvr(CmpSvr cmpSvr) {
		this.cmpSvr = cmpSvr;
	}

	public CmpSvr getCmpSvr() {
		return cmpSvr;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getActorId() {
		return actorId;
	}

	public void setActorId(long actorId) {
		this.actorId = actorId;
	}

	public long getSvrId() {
		return svrId;
	}

	public void setSvrId(long svrId) {
		this.svrId = svrId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}