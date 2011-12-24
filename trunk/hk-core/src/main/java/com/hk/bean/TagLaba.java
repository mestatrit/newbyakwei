package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.LabaService;

@Table(name = "taglaba")
public class TagLaba {
	@Column
	private long tagId;

	@Column
	private long labaId;

	@Column
	private long userId;// 发表人

	private Laba laba;

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}