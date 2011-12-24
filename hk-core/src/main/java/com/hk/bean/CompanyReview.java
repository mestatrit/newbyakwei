package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.Err;

@Table(name = "companyreview", id = "labaId")
public class CompanyReview {

	public static final byte CHECKFLG_NORMAL = 0;// 正常的

	public static final byte CHECKFLG_DISSENTIOUS = 1;// 有争议的

	private long labaId;

	private long userId;

	private long companyId;

	private int score;// 用户打分

	private String content;

	private String longContent;

	private Date createTime;

	private int sendFrom;

	private byte checkflg;

	public boolean isNormal() {
		if (this.checkflg == CHECKFLG_NORMAL) {
			return true;
		}
		return false;
	}

	public void setSendFrom(int sendFrom) {
		this.sendFrom = sendFrom;
	}

	public int getSendFrom() {
		return sendFrom;
	}

	public void setLongContent(String longContent) {
		this.longContent = longContent;
	}

	public String getLongContent() {
		return longContent;
	}

	public void setLabaId(long labaId) {
		this.labaId = labaId;
	}

	public long getLabaId() {
		return labaId;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int validate() {
		if (this.userId <= 0) {
			return Err.USERID_ERROR;
		}
		if (this.companyId <= 0) {
			return Err.HKOBJID_ERROR;
		}
		int i = CompanyUserScore.validateScore(this.score);
		if (i != Err.SUCCESS) {
			return i;
		}
		return Err.SUCCESS;
	}

	public byte getCheckflg() {
		return checkflg;
	}

	public void setCheckflg(byte checkflg) {
		this.checkflg = checkflg;
	}

	public long getTime() {
		return this.createTime.getTime();
	}
}