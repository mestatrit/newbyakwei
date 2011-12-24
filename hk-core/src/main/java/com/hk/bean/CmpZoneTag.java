package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "cmpzonetag", id = "oid")
public class CmpZoneTag {
	private long oid;

	private long tagId;

	private int cityId;

	private int kindId;

	private int cmpCount;

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

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getCmpCount() {
		return cmpCount;
	}

	public void setCmpCount(int cmpCount) {
		this.cmpCount = cmpCount;
	}

	public int getKindId() {
		return kindId;
	}

	public void setKindId(int kindId) {
		this.kindId = kindId;
	}
}