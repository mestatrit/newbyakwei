package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "userprotect", id = "userid")
public class UserProtect {
	private long userId;

	private int pconfig;

	private String pvalue;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getPconfig() {
		return pconfig;
	}

	public void setPconfig(int pconfig) {
		this.pconfig = pconfig;
	}

	public String getPvalue() {
		return pvalue;
	}

	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}
}