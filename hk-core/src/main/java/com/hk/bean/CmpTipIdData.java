package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "cmptipiddata")
public class CmpTipIdData {

	@Id
	private long tipId;

	@Column
	private Date createTime;

	public void setTipId(long tipId) {
		this.tipId = tipId;
	}

	public long getTipId() {
		return tipId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}