package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;

/**
 * 用于个人信息台,活动
 * 
 * @author yuanwei
 */
@Table(name = "infosmsport", id = "portid")
public class InfoSmsPort {
	public static final byte USETYPE_INFO = 0;

	public static final byte USETYPE_ACT = 1;

	public static final byte LEVEL_NORMAL = 0;

	public static final byte LEVEL_GOOD = 1;

	private long portId;

	private String portNumber;

	private long userId;

	private Date overTime;

	private byte level;

	private byte usetype;// 使用类型0:信息台1:活动

	private long actId;// 活动id

	public long getActId() {
		return actId;
	}

	public void setActId(long actId) {
		this.actId = actId;
	}

	public byte getUsetype() {
		return usetype;
	}

	public void setUsetype(byte usetype) {
		this.usetype = usetype;
	}

	public boolean isOver() {
		if (this.overTime.getTime() < System.currentTimeMillis()) {
			return true;
		}
		return false;
	}

	public boolean isUse() {
		if (userId == 0) {
			return false;
		}
		return true;
	}

	public byte getLevel() {
		return level;
	}

	public void setLevel(byte level) {
		this.level = level;
	}

	public byte getLEVEL_NORMAL() {
		return LEVEL_NORMAL;
	}

	public byte getLEVEL_GOOD() {
		return LEVEL_GOOD;
	}

	public long getPortId() {
		return portId;
	}

	public void setPortId(long portId) {
		this.portId = portId;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}
}