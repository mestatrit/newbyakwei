package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.LabaService;

@Table(name = "ipcityrangelaba")
public class IpCityRangeLaba {
	@Column
	private int rangeId;

	@Id
	private long labaId;

	private Laba laba;

	public int getRangeId() {
		return rangeId;
	}

	public void setRangeId(int rangeId) {
		this.rangeId = rangeId;
	}

	public long getLabaId() {
		return labaId;
	}

	public void setLabaId(long labaId) {
		this.labaId = labaId;
	}

	public Laba getLaba() {
		if (laba == null) {
			LabaService labaService = (LabaService) HkUtil
					.getBean("labaService");
			laba = labaService.getLaba(labaId);
		}
		return laba;
	}

	public void setLaba(Laba laba) {
		this.laba = laba;
	}
}