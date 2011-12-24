package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.LabaService;

@Table(name = "favlaba")
public class FavLaba {
	private long favId;

	private long labaId;

	private long userId;

	private Laba laba;

	public void setLaba(Laba laba) {
		this.laba = laba;
	}

	public Laba getLaba() {
		if (laba == null) {
			LabaService labaService = (LabaService) HkUtil
					.getBean("labaService");
			laba = labaService.getLaba(labaId);
		}
		return laba;
	}

	public long getFavId() {
		return favId;
	}

	public void setFavId(long favId) {
		this.favId = favId;
	}

	public long getLabaId() {
		return labaId;
	}

	public void setLabaId(long labaId) {
		this.labaId = labaId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}