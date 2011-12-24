package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "chgcardact", id = "actid")
public class ChgCardAct {
	public static final byte ACTSTATUS_INUSE = 0;

	public static final byte ACTSTATUS_PAUSE = 1;

	private long actId;

	private long userId;

	private String name;

	private int persistHour;

	private Date createTime;

	private Date endTime;// 活动结束时间

	private byte chgflg;// 交换方式同usercard.chgflg

	private byte actStatus;// 活动状态0:使用中 1:暂停 2:停止

	private String sysnum;// 系统分配的暗号

	public void setSysnum(String sysnum) {
		this.sysnum = sysnum;
	}

	public String getSysnum() {
		return sysnum;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public byte getActStatus() {
		return actStatus;
	}

	public void setActStatus(byte actStatus) {
		this.actStatus = actStatus;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPersistHour() {
		return persistHour;
	}

	public void setPersistHour(int persistHour) {
		this.persistHour = persistHour;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public byte getChgflg() {
		return chgflg;
	}

	public void setChgflg(byte chgflg) {
		this.chgflg = chgflg;
	}

	public boolean isPause() {
		if (this.actStatus == 1) {
			return true;
		}
		return false;
	}

	public boolean isStop() {
		if (this.actStatus == 2) {
			return true;
		}
		return false;
	}

	public boolean isProtectedChange() {
		if (this.chgflg == UserCard.CHGFLG_PROTECT) {
			return true;
		}
		return false;
	}

	public boolean isOver() {
		if (endTime.getTime() < System.currentTimeMillis()) {
			return true;
		}
		return false;
	}

	public int validate() {
		if (DataUtil.isEmpty(DataUtil.toTextRow(name))) {
			return Err.CHGCARDACT_NAME_ERROR;
		}
		if (DataUtil.toTextRow(name).length() > 30) {
			return Err.CHGCARDACT_NAME_LENGTH_TOO_LONG;
		}
		if (this.persistHour > 72) {
			return Err.CHGCARDACT_PERSISTHOUR_TOO_BIG;
		}
		return Err.SUCCESS;
	}
}