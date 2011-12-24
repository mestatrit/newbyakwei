package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "buildingtag", id = "tagid")
public class BuildingTag {
	private int tagId;

	private int cityId;

	private String name;

	private int provinceId;

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
}