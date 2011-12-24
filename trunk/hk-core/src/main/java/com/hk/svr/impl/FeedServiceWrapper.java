package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.hk.bean.Feed;
import com.hk.bean.Follow;
import com.hk.bean.UserRecentFeed;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ListWrapper;
import com.hk.svr.FeedService;
import com.hk.svr.FollowService;

public class FeedServiceWrapper extends FeedServiceImpl {
	private FeedService feedService;

	private FollowService followService;

	private FeedComparator feedComparator = new FeedComparator();

	public FeedServiceWrapper() {
		this.feedService = (FeedService) HkUtil.getBean("feedService");
		this.followService = (FollowService) HkUtil.getBean("followService");
	}

	@Override
	public ListWrapper<Feed> getFeedList(int begin, int size) {
		ListWrapper<Feed> list = feedService.getFeedList(begin, size);
		return list;
	}

	@Override
	public ListWrapper<Feed> getIpCityFeedList(int cityId, int begin, int size) {
		ListWrapper<Feed> list = this.feedService.getIpCityFeedList(cityId,
				begin, size);
		return list;
	}

	@Override
	public ListWrapper<Feed> getIpCityRangeFeedList(int rangeId, int begin,
			int size) {
		ListWrapper<Feed> list = this.feedService.getIpCityRangeFeedList(
				rangeId, begin, size);
		return list;
	}

	@Override
	public ListWrapper<Feed> getIpFeedList(String ip, int begin, int size) {
		ListWrapper<Feed> list = this.feedService
				.getIpFeedList(ip, begin, size);
		return list;
	}

	@Override
	public List<Feed> getUserFeedList(long userId, int begin, int size) {
		List<Feed> list = this.feedService.getUserFeedList(userId, begin, size);
		return list;
	}

	@Override
	public List<Feed> getFriendFeedList(long userId, int begin, int size) {
		List<Follow> flist = this.followService.getFollowList(userId);
		List<Long> idList = new ArrayList<Long>();
		for (Follow o : flist) {
			UserRecentFeed userRecentFeed = this.feedService
					.getUserRecentFeed(o.getFriendId());
			if (userRecentFeed != null) {
				userRecentFeed.makeFeedIdList();
				idList.addAll(userRecentFeed.getFeedIdList());
			}
		}
		Collections.sort(idList, feedComparator);
		int end = DataUtil.getSelectedListEnd(idList, begin, size);
		if (end == -1) {
			return new ArrayList<Feed>();
		}
		idList = idList.subList(begin, end);
		return this.feedService.getFeedListInIdList(idList);
	}

	final class FeedComparator implements Comparator<Long> {
		public int compare(Long o1, Long o2) {
			if (o1.longValue() > o2.longValue()) {
				return -1;
			}
			if (o1.longValue() == o2.longValue()) {
				return 0;
			}
			return 1;
		}
	}
}