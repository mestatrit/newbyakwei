package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Table;

@Table(name = "cmpmembermoneylog", id = "oid")
public class CmpMemberMoneyLog {
	public static final byte ADDFLG_CHARGE = 0;

	public static final byte ADDFLG_SPEND = 1;

	private long oid;

	private long companyId;

	private long memberId;

	private double money;

	private double oldMoney;

	private Date createTime;

	private byte addflg;// 账户变动类型 0:充值,1:用户消费

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public byte getAddflg() {
		return addflg;
	}

	public void setAddflg(byte addflg) {
		this.addflg = addflg;
	}

	public double getOldMoney() {
		return oldMoney;
	}

	public void setOldMoney(double oldMoney) {
		this.oldMoney = oldMoney;
	}
}