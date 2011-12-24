package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 推荐分类
 * 
 * @author akwei
 */
@Table(name = "cmpunioncmdkind", id = "oid")
public class CmpUnionCmdKind {
	@Id
	private long oid;

	@Column
	private long uid;

	@Column
	private long kindId;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getKindId() {
		return kindId;
	}

	public void setKindId(long kindId) {
		this.kindId = kindId;
	}
}