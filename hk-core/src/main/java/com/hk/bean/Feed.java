package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 动态数据
 * 
 * @author akwei
 */
@Table(name = "feed", id = "feedid")
public class Feed {

	public static final byte FEEDTYPE_FOLLOW = 3;

	public static final byte FEEDTYPE_OPENBOX = 4;

	public static final byte FEEDTYPE_GETBADGE = 5;

	public static final byte FEEDTYPE_BECOME_MAYOR = 6;

	public static final byte FEEDTYPE_WRITETIPS = 7;

	/**
	 * 用户获得了某个道具装备
	 */
	public static final byte FEEDTYPE_EQU = 8;

	public static final byte FEEDTYPE_CREATEVENUE = 9;

	public static final byte FEEDTYPE_INVITE = 10;

	@Id
	private long feedId;

	@Column
	private long userId;

	@Column
	private byte feedType;

	@Column
	private Date createTime;

	@Column
	private long ipNumber;

	@Column
	private int cityId;

	@Column
	private int rangeId;

	@Column
	private String data;

	private User user;

	private UserBadge userBadge;

	private long tipId;

	private long companyId;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getIpNumber() {
		return ipNumber;
	}

	public void setIpNumber(long ipNumber) {
		this.ipNumber = ipNumber;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
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

	public byte getFeedType() {
		return feedType;
	}

	public void setFeedType(byte feedType) {
		this.feedType = feedType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public UserBadge getUserBadge() {
		return userBadge;
	}

	public void setUserBadge(UserBadge userBadge) {
		this.userBadge = userBadge;
	}

	public boolean isWriteTips() {
		if (this.feedType == FEEDTYPE_WRITETIPS) {
			return true;
		}
		return false;
	}

	public boolean isGetBadge() {
		if (this.feedType == FEEDTYPE_GETBADGE) {
			return true;
		}
		return false;
	}

	public boolean isBecomeMayor() {
		if (this.feedType == FEEDTYPE_BECOME_MAYOR) {
			return true;
		}
		return false;
	}

	public boolean isCreateVenue() {
		if (this.feedType == FEEDTYPE_CREATEVENUE) {
			return true;
		}
		return false;
	}

	public long getTipId() {
		return tipId;
	}

	public void setTipId(long tipId) {
		this.tipId = tipId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public FeedInfo createFeedInfo() {
		FeedInfo feedInfo = new FeedInfo();
		feedInfo.setCreateTime(this.createTime);
		feedInfo.setFeedType(this.feedType);
		return feedInfo;
	}
}