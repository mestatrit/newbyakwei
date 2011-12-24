package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "cmpactuser", id = "oid")
public class CmpActUser {
	/**
	 * 未审核
	 */
	public static final byte CHECKFLG_UNCHECKED = 0;

	/**
	 * 审核不通过
	 */
	public static final byte CHECKFLG_N = 1;

	/**
	 * 候补
	 */
	public static final byte CHECKFLG_CANDIDATE = 2;

	/**
	 * 通过
	 */
	public static final byte CHECKFLG_Y = 3;

	@Id
	private long oid;

	@Column
	private long actId;

	@Column
	private long userId;

	@Column
	private byte checkflg;

	@Column
	private long companyId;

	private User user;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
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

	public byte getCheckflg() {
		return checkflg;
	}

	public void setCheckflg(byte checkflg) {
		this.checkflg = checkflg;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isCheckOk() {
		if (this.checkflg == CHECKFLG_Y) {
			return true;
		}
		return false;
	}

	public boolean isCheckNo() {
		if (this.checkflg == CHECKFLG_N) {
			return true;
		}
		return false;
	}

	public boolean isCandidate() {
		if (this.checkflg == CHECKFLG_CANDIDATE) {
			return true;
		}
		return false;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}