package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "cmpproductuserscore", id = "oid")
public class CmpProductUserScore {
	private long oid;

	private long userId;

	private long productId;

	private long companyId;

	private User user;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	private int score;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}