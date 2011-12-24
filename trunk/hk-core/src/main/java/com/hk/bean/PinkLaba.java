package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.LabaService;

@Table(name = "pinklaba", id = "labaid")
public class PinkLaba {
	private long labaId;

	private long pinkUserId;

	private Date createTime;

	private Laba laba;

	public long getLabaId() {
		return labaId;
	}

	public void setLabaId(long labaId) {
		this.labaId = labaId;
	}

	public long getPinkUserId() {
		return pinkUserId;
	}

	public void setPinkUserId(long pinkUserId) {
		this.pinkUserId = pinkUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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