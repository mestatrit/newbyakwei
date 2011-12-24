package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "ipcityrange", id = "sysid")
public class IpCityRange {
	private int rangeId;

	private long beginIp;

	private long endIp;

	private int cityId;

	public int getRangeId() {
		return rangeId;
	}

	public void setRangeId(int rangeId) {
		this.rangeId = rangeId;
	}

	public long getBeginIp() {
		return beginIp;
	}

	public void setBeginIp(long beginIp) {
		this.beginIp = beginIp;
	}

	public long getEndIp() {
		return endIp;
	}

	public void setEndIp(long endIp) {
		this.endIp = endIp;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
}