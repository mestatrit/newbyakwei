package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "companyreviewdel", id = "labaId")
public class CompanyReviewDel extends CompanyReview {
	public CompanyReviewDel() {//
	}

	public CompanyReviewDel(CompanyReview companyReview) {
		this.setCompanyId(companyReview.getCompanyId());
		this.setContent(companyReview.getContent());
		this.setCreateTime(companyReview.getCreateTime());
		this.setLabaId(companyReview.getLabaId());
		this.setLongContent(companyReview.getLongContent());
		this.setScore(companyReview.getScore());
		this.setSendFrom(companyReview.getSendFrom());
		this.setUserId(companyReview.getUserId());
		this.setCheckflg(companyReview.getCheckflg());
	}

	private long opuserId;

	private long optime;

	public long getOpuserId() {
		return opuserId;
	}

	public void setOpuserId(long opuserId) {
		this.opuserId = opuserId;
	}

	public long getOptime() {
		return optime;
	}

	public void setOptime(long optime) {
		this.optime = optime;
	}
}