package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;

@Table(name = "companyfeed", id = "feedid")
public class CompanyFeed {
	private long feedId;

	private long userId;

	private long companyId;

	private int cityId;

	private Date createTime;

	private int rangeId;

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		if (user == null) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			user = userService.getUser(userId);
		}
		return user;
	}

	public int getRangeId() {
		return rangeId;
	}

	public void setRangeId(int rangeId) {
		this.rangeId = rangeId;
	}

	public long getFeedId() {
		return feedId;
	}

	public void setFeedId(long feedId) {
		this.feedId = feedId;
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

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}