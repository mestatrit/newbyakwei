package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 推荐的美发师
 * 
 * @author akwei
 */
@Table(name = "cmpactorpink")
public class CmpActorPink {

	@Id
	private long oid;

	@Column
	private long actorId;

	private CmpActor cmpActor;

	public void setCmpActor(CmpActor cmpActor) {
		this.cmpActor = cmpActor;
	}

	public CmpActor getCmpActor() {
		return cmpActor;
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
}