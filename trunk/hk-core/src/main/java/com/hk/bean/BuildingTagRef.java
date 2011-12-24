package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "buildingtagref")
public class BuildingTagRef {
	private int companyId;

	private int tagId;

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}
}