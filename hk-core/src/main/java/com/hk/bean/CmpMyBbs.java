package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "cmpmybbs")
public class CmpMyBbs {

	public static final byte BBSFLG_CREATE = 0;

	public static final byte BBSFLG_REPLY = 1;

	@Id
	private long oid;

	@Column
	private long userId;

	@Column
	private long companyId;

	@Column
	private long bbsId;

	/**
	 * 发表的，还是回复的话题
	 */
	@Column
	private byte bbsflg;

	/**
	 * 最后更新时间
	 */
	@Column
	private Date uptime;

	private CmpBbs cmpBbs;

	public void setCmpBbs(CmpBbs cmpBbs) {
		this.cmpBbs = cmpBbs;
	}

	public CmpBbs getCmpBbs() {
		return cmpBbs;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
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

	public long getBbsId() {
		return bbsId;
	}

	public void setBbsId(long bbsId) {
		this.bbsId = bbsId;
	}

	public byte getBbsflg() {
		return bbsflg;
	}

	public void setBbsflg(byte bbsflg) {
		this.bbsflg = bbsflg;
	}

	public Date getUptime() {
		return uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}
}