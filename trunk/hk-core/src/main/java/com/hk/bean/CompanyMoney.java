package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.Err;

@Table(name = "companymoney", id = "sysid")
public class CompanyMoney {
	public static final byte FIRSTFLG_N = 0;

	public static final byte FIRSTFLG_Y = 1;

	private long sysId;

	private long companyId;

	private long opuserId;

	private int money;

	private Date createTime;

	private Date endTime;

	private byte firstflg;

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getOpuserId() {
		return opuserId;
	}

	public void setOpuserId(long opuserId) {
		this.opuserId = opuserId;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public byte getFirstflg() {
		return firstflg;
	}

	public void setFirstflg(byte firstflg) {
		this.firstflg = firstflg;
	}

	public int validate() {
		if (this.companyId == 0) {
			return Err.HKOBJID_ERROR;
		}
		if (this.opuserId == 0) {
			return Err.USERID_ERROR;
		}
		if (this.money <= 0) {
			return Err.COMPANYMONEY_MONEY_ERROR;
		}
		if (this.endTime == null) {
			return Err.COMPANYMONEY_ENDTIME_ERROR;
		}
		return Err.SUCCESS;
	}
}