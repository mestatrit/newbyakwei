package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;

@Table(name = "randnum", id = "sysid")
public class Randnum {
	public static final byte INUSE_Y = 1;

	public static final byte INUSE_N = 0;

	private int sysId;

	private int randvalue;

	private long userId;

	private byte inuse;

	private Date utime;

	public int getSysId() {
		return sysId;
	}

	public void setSysId(int sysId) {
		this.sysId = sysId;
	}

	public int getRandvalue() {
		return randvalue;
	}

	public void setRandvalue(int randvalue) {
		this.randvalue = randvalue;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public byte getInuse() {
		return inuse;
	}

	public void setInuse(byte inuse) {
		this.inuse = inuse;
	}

	public Date getUtime() {
		return utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}
}