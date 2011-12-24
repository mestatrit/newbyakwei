package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "hkobjkeytag", id = "oid")
public class HkObjKeyTag {
	private long oid;

	private long tagId;

	private long hkObjId;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public long getHkObjId() {
		return hkObjId;
	}

	public void setHkObjId(long hkObjId) {
		this.hkObjId = hkObjId;
	}
}
