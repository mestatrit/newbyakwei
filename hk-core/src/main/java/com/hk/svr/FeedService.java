package com.hk.svr;

import java.util.List;

import com.hk.bean.Feed;
import com.hk.bean.FeedInfo;
import com.hk.bean.UserRecentFeed;
import com.hk.frame.util.ListWrapper;

public interface FeedService {

	/**
	 * @param feed @see {@link Feed}
	 * @param feedInfo @see {@link FeedInfo}
	 */
	void createFeed(Feed feed, FeedInfo feedInfo);

	UserRecentFeed getUserRecentFeed(long userId);

	List<Feed> getFriendFeedList(long userId, int begin, int size);

	List<Feed> getUserFeedList(long userId, int begin, int size);

	ListWrapper<Feed> getFeedList(int begin, int size);

	ListWrapper<Feed> getIpFeedList(String ip, int begin, int size);

	ListWrapper<Feed> getIpCityFeedList(int cityId, int begin, int size);

	ListWrapper<Feed> getIpCityRangeFeedList(int rangeId, int begin, int size);

	int countFeed(long userId);

	List<Feed> getFeedListInIdList(List<Long> idList);

	List<Feed> getFeedListForIndex(int cityId, int begin, int size);

	List<Feed> getFeedListForIndex(int begin, int size);

	void updateFeed(Feed feed);

	List<Long> getFeedIdListFromFeedInfoByObjIdAndFeedType(long objId,
			byte feedType);

	List<Long> getFeedIdListFromFeedInfoByObj2IdAndFeedType(long obj2Id,
			byte feedType);

	void deleteFeed(long feedId);

	List<Feed> getFeedListInId(List<Long> idList);

	List<Feed> getFeedListByFeedType(byte feedType, int begin, int size);

	List<Feed> getFeedListByFeedTypeAndCityid(byte feedType, int cityId,
			int begin, int size);
}