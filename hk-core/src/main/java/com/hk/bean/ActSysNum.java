package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 活动使用的短信号码
 * 
 * @author akwei
 */
@Table(name = "actsysnum", id = "sysid")
public class ActSysNum {

	public static final byte USETYPE_CARD = 0;

	public static final byte USETYPE_ACT = 1;

	public static final byte SYSSTATUS_FREE = 0;// 空闲中

	public static final byte SYSSTATUS_INUSE = 1;// 使用中

	@Id
	private int sysId;

	@Column
	private String sysnum;

	@Column
	private byte sysstatus;

	@Column
	private Date overTime;

	@Column
	private long actId;

	@Column
	private byte usetype;// 使用类型0:信息台1:活动

	public byte getUsetype() {
		return usetype;
	}

	public void setUsetype(byte usetype) {
		this.usetype = usetype;
	}

	public boolean isOver() {
		if (overTime.getTime() < System.currentTimeMillis()) {
			return true;
		}
		return false;
	}

	public void setActId(long actId) {
		this.actId = actId;
	}

	public long getActId() {
		return actId;
	}

	public int getSysId() {
		return sysId;
	}

	public void setSysId(int sysId) {
		this.sysId = sysId;
	}

	public String getSysnum() {
		return sysnum;
	}

	public void setSysnum(String sysnum) {
		this.sysnum = sysnum;
	}

	public byte getSysstatus() {
		return sysstatus;
	}

	public void setSysstatus(byte sysstatus) {
		this.sysstatus = sysstatus;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}
}