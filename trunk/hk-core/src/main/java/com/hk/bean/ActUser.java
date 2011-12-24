package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

/**
 * 活动参加者
 * 
 * @author akwei
 */
@Table(name = "actuser", id = "sysid")
public class ActUser {

	public static final byte CHECKFLG_N = 0;

	public static final byte CHECKFLG_Y = 1;

	private long sysId;

	private long actId;

	private long userId;

	private byte checkflg;// 审核标志0:未审核1:通过审核

	private User user;

	public boolean isPassCheck() {
		if (this.checkflg == CHECKFLG_Y) {
			return true;
		}
		return false;
	}

	public byte getCheckflg() {
		return checkflg;
	}

	public void setCheckflg(byte checkflg) {
		this.checkflg = checkflg;
	}

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public long getActId() {
		return actId;
	}

	public void setActId(long actId) {
		this.actId = actId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}