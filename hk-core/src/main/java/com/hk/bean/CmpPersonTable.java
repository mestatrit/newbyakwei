package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "cmppersontable", id = "oid")
public class CmpPersonTable {
	private long oid;

	private long companyId;

	/**
	 * 理想人数
	 */
	private int personNum;

	/**
	 * 空闲的数量
	 */
	private int freeCount;

	/**
	 * 总数量
	 */
	private int totalCount;

	private long sortId;

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

	public int getFreeCount() {
		return freeCount;
	}

	public void setFreeCount(int freeCount) {
		this.freeCount = freeCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPersonNum() {
		return personNum;
	}

	public void setPersonNum(int personNum) {
		this.personNum = personNum;
	}

	public long getSortId() {
		return sortId;
	}

	public void setSortId(long sortId) {
		this.sortId = sortId;
	}
}