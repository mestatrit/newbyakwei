package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "ipcity", id = "cityid")
public class IpCity {
	private int cityId;

	private String name;

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}