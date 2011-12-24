package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "mayor")
public class Mayor {

	@Id
	private long mayorId;

	@Column
	private long userId;

	@Column
	private long companyId;

	/**
	 * 足迹的pcityid
	 */
	@Column
	private int pcityId;

	private User user;

	private Company company;

	private CmpCheckInUserLog lastCmpCheckInUserLog;

	public void setLastCmpCheckInUserLog(CmpCheckInUserLog lastCmpCheckInUserLog) {
		this.lastCmpCheckInUserLog = lastCmpCheckInUserLog;
	}

	public CmpCheckInUserLog getLastCmpCheckInUserLog() {
		return lastCmpCheckInUserLog;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setPcityId(int pcityId) {
		this.pcityId = pcityId;
	}

	public int getPcityId() {
		return pcityId;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}

	public long getMayorId() {
		return mayorId;
	}

	public void setMayorId(long mayorId) {
		this.mayorId = mayorId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}