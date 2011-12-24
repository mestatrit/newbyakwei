package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;

@Table(name = "regcodeuser", id = "sysid")
public class RegCodeUser {
	private long sysId;

	private long userId;

	private long codeId;

	private Date createTime;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCodeId() {
		return codeId;
	}

	public void setCodeId(long codeId) {
		this.codeId = codeId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}
}