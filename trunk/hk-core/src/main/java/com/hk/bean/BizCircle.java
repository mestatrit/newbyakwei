package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.ZoneUtil;

@Table(name = "bizcircle", id = "circleid")
public class BizCircle {
	private int circleId;

	private int cityId;

	private String name;

	private int provinceId;

	private int cmpCount;

	public int getCmpCount() {
		return cmpCount;
	}

	public void setCmpCount(int cmpCount) {
		this.cmpCount = cmpCount;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public int getCircleId() {
		return circleId;
	}

	public void setCircleId(int circleId) {
		this.circleId = circleId;
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

	public City getCity() {
		return ZoneUtil.getCity(cityId);
	}
}