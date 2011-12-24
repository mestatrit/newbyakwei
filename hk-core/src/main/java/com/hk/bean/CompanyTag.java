package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "companytag", id = "tagid")
public class CompanyTag {
	private int tagId;

	private int kindId;

	private String name;

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public int getKindId() {
		return kindId;
	}

	public void setKindId(int kindId) {
		this.kindId = kindId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKindData() {
		CompanyKind k = CompanyKindUtil.getCompanyKind(kindId);
		if (k != null) {
			return k.getName();
		}
		return null;
	}
}