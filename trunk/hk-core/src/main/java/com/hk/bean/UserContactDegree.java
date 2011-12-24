package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "usercontactdegree")
public class UserContactDegree {
	public UserContactDegree() {//
	}

	public UserContactDegree(long userId, long contactUserId) {
		this.userId = userId;
		this.contactUserId = contactUserId;
		this.degree = 1;
	}

	private long userId;

	private long contactUserId;

	private int degree;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getContactUserId() {
		return contactUserId;
	}

	public void setContactUserId(long contactUserId) {
		this.contactUserId = contactUserId;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}
}