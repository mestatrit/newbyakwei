package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;

@Table(name = "userfgtmail", id = "userid")
public class UserFgtMail {
	private long userId;

	private int sencCount;

	private String desValue;

	private Date createTime;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getSencCount() {
		return sencCount;
	}

	public void setSencCount(int sencCount) {
		this.sencCount = sencCount;
	}

	public String getDesValue() {
		return desValue;
	}

	public void setDesValue(String desValue) {
		this.desValue = desValue;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}