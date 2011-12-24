package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "pcity", id = "cityid")
public class Pcity {
	private City city;

	private int cityId;

	private String name;

	private int provinceId;

	private int countryId;

	private int ocityId;// 原来的cityid

	public void setCity(City city) {
		this.city = city;
	}

	public City getCity() {
		return city;
	}

	public int getOcityId() {
		return ocityId;
	}

	public void setOcityId(int ocityId) {
		this.ocityId = ocityId;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public int getCityId() {
		if (this.city != null) {
			return city.getCityId();
		}
		return 0;
	}

	public int getCityIdOld() {
		return this.cityId;
	}

	public String getNameOld() {
		return this.name;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getName() {
		// return name;
		if (city != null) {
			return this.city.getCity();
		}
		return null;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
}