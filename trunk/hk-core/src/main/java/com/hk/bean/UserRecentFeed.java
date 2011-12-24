package com.hk.bean;

import java.util.ArrayList;
import java.util.List;

import com.hk.frame.dao.annotation.Table;

@Table(name = "userrecentfeed", id = "userid")
public class UserRecentFeed {
	private long userId;

	private String feedData;

	private List<Long> feedIdList = new ArrayList<Long>();

	public void makeFeedIdList() {
		if (feedData != null && !feedData.equals("")) {
			String[] tmp = feedData.split(",");
			if (tmp != null) {
				for (String t : tmp) {
					feedIdList.add(Long.valueOf(t));
				}
			}
		}
	}

	public void removeFeedId(long feedId) {
		if (feedIdList.contains(feedId)) {
			feedIdList.remove(feedId);
			this.feedData = this.getFeedIdValue();
		}
	}

	public void addFeedId(long feedId) {
		if (!feedIdList.contains(feedId)) {
			feedIdList.add(0, feedId);
		}
		if (feedIdList.size() > 20) {
			feedIdList = feedIdList.subList(0, 20);
		}
		this.feedData = this.getFeedIdValue();
	}

	private String getFeedIdValue() {
		StringBuilder sb = new StringBuilder();
		if (feedIdList.size() > 0) {
			for (Long l : feedIdList) {
				sb.append(l).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		}
		return null;
	}

	public List<Long> getFeedIdList() {
		return feedIdList;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFeedData() {
		return feedData;
	}

	public void setFeedData(String feedData) {
		this.feedData = feedData;
	}
}