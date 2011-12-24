package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "companyuserscore")
public class CompanyUserScore {
	private long userId;

	private long companyId;

	private int score;

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

	public static int validateScore(int score) {
		if (!DataUtil.isInElements(score, new Object[] { 3, 2, 1, -1, -2 })) {
			return Err.COMPANY_SCORE_ERROR;
		}
		return Err.SUCCESS;
	}
}