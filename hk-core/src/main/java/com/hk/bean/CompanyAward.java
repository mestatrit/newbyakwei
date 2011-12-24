package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "companyaward", id = "companyid")
public class CompanyAward {
	public static final byte AWARDSTATUS_N = 0;

	public static final byte AWARDSTATUS_Y = 1;

	private long companyId;

	private long createrId;

	private byte awardStatus;

	private int money;

	private int awardhkb;

	private User creater;

	private Company company;

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(long createrId) {
		this.createrId = createrId;
	}

	public byte getAwardStatus() {
		return awardStatus;
	}

	public void setAwardStatus(byte awardStatus) {
		this.awardStatus = awardStatus;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getAwardhkb() {
		return awardhkb;
	}

	public void setAwardhkb(int awardhkb) {
		this.awardhkb = awardhkb;
	}

	public boolean isAwarded() {
		if (this.awardStatus == AWARDSTATUS_Y) {
			return true;
		}
		return false;
	}
}