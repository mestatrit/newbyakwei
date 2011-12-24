package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;

@Table(name = "bomblaba", id = "sysid")
public class BombLaba {
	private long sysId;

	private long userId;

	private long labaId;

	private Date createTime;

	private long optime;

	private Laba laba;

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getLabaId() {
		return labaId;
	}

	public void setLabaId(long labaId) {
		this.labaId = labaId;
	}

	public Laba getLaba() {
		return laba;
	}

	public void setLaba(Laba laba) {
		this.laba = laba;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getOptime() {
		return optime;
	}

	public void setOptime(long optime) {
		this.optime = optime;
	}
}