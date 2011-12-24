package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "cmpadminhkblog", id = "sysid")
public class CmpAdminHkbLog {
	public static final byte ADDFLG_MONEYBUY = 0;

	public static final byte ADDFLG_PRESENT = 1;

	private long sysId;

	private long companyId;

	private double money;

	private byte addflg;

	private long opuserId;

	private Date createTime;

	private String remark;

	private int addCount;

	public int getAddCount() {
		return addCount;
	}

	public void setAddCount(int addCount) {
		this.addCount = addCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public byte getAddflg() {
		return addflg;
	}

	public void setAddflg(byte addflg) {
		this.addflg = addflg;
	}

	public long getOpuserId() {
		return opuserId;
	}

	public void setOpuserId(long opuserId) {
		this.opuserId = opuserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int validate() {
		if (this.companyId == 0) {
			return Err.HKOBJID_ERROR;
		}
		if (this.opuserId == 0) {
			return Err.USERID_ERROR;
		}
		if (this.addCount == 0) {
			return Err.CMPADMINHKBLOG_ADDCOUNT_ERROR;
		}
		if (this.addflg == ADDFLG_MONEYBUY) {
			if (money <= 0) {
				return Err.ADMINHKB_MONEY_ERROR;
			}
		}
		String s = DataUtil.toTextRow(this.remark);
		if (!DataUtil.isEmpty(s)) {
			if (s.length() > 200) {
				return Err.ADMINHKB_CONTENT_LENGTH_TOO_LONG;
			}
		}
		return Err.SUCCESS;
	}
}