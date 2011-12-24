package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;

@Table(name = "usermailauth", id = "userid")
public class UserMailAuth {
	private long userId;

	private String authcode;

	private Date overTime;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAuthcode() {
		return authcode;
	}

	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}

	public boolean isOver() {
		if (this.overTime.getTime() < System.currentTimeMillis()) {
			return true;
		}
		return false;
	}
}