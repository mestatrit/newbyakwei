package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Feed;
import com.hk.bean.FeedInfo;
import com.hk.bean.UserRecentFeed;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ListWrapper;
import com.hk.svr.FeedService;

public class FeedServiceImpl implements FeedService {

	@Autowired
	private QueryManager manager;

	public void createFeed(Feed feed, FeedInfo feedInfo) {
		long feedId = insertFeed(feed, feedInfo);
		feed.setFeedId(feedId);
		this.createUserRecentFeed(feed);
	}

	private long insertFeed(Feed feed, FeedInfo feedInfo) {
		if (feed.getCreateTime() == null) {
			feed.setCreateTime(new Date());
		}
		Query query = this.manager.createQuery();
		long feedId = query.insertObject(feed).longValue();
		if (feedInfo != null) {
			feedInfo.setFeedId(feedId);
			feedInfo.setCreateTime(feed.getCreateTime());
			query.insertObject(feedInfo);
		}
		return feedId;
	}

	public void deleteFeed(long feedId) {
		Query query = this.manager.createQuery();
		Feed feed = query.getObjectById(Feed.class, feedId);
		if (feed == null) {
			return;
		}
		query.deleteById(Feed.class, feedId);
		query.deleteById(FeedInfo.class, feedId);
		UserRecentFeed userRecentFeed = this
				.getUserRecentFeed(feed.getUserId());
		userRecentFeed.removeFeedId(feedId);
		this.updateUserRecentFeed(userRecentFeed);
	}

	private void createUserRecentFeed(Feed feed) {
		UserRecentFeed userRecentFeed = this
				.getUserRecentFeed(feed.getUserId());
		if (userRecentFeed == null) {
			userRecentFeed = new UserRecentFeed();
			userRecentFeed.setUserId(feed.getUserId());
			userRecentFeed.addFeedId(feed.getFeedId());
			this.insertUserRecentFeed(userRecentFeed);
		}
		else {
			userRecentFeed.addFeedId(feed.getFeedId());
			this.updateUserRecentFeed(userRecentFeed);
		}
	}

	private void insertUserRecentFeed(UserRecentFeed userRecentFeed) {
		Query query = this.manager.createQuery();
		query.addField("userid", userRecentFeed.getUserId());
		query.addField("feeddata", userRecentFeed.getFeedData());
		query.insert(UserRecentFeed.class);
	}

	private void updateUserRecentFeed(UserRecentFeed userRecentFeed) {
		Query query = this.manager.createQuery();
		query.setTable(UserRecentFeed.class);
		query.addField("feeddata", userRecentFeed.getFeedData());
		query.where("userid=?").setParam(userRecentFeed.getUserId());
		query.update();
	}

	public UserRecentFeed getUserRecentFeed(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserRecentFeed.class, userId);
	}

	/**
	 * @see FeedServiceWrapper.getFriendFeedList
	 */
	public List<Feed> getFriendFeedList(long userId, int begin, int size) {
		return null;
	}

	public List<Feed> getUserFeedList(long userId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Feed.class, "userid=?", new Object[] { userId },
				"feedid desc", begin, size);
	}

	public ListWrapper<Feed> getFeedList(int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(Feed.class);
		query.orderByDesc("feedid");
		ListWrapper<Feed> o = new ListWrapper<Feed>();
		o.setList(query.list(begin, size, Feed.class));
		o.setBegin(begin);
		o.setSize(size);
		return o;
	}

	public ListWrapper<Feed> getIpCityFeedList(int cityId, int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(Feed.class);
		query.where("cityid=?").setParam(cityId);
		query.orderByDesc("feedid");
		ListWrapper<Feed> o = new ListWrapper<Feed>();
		o.setBegin(begin);
		o.setSize(size);
		o.setList(query.list(begin, size, Feed.class));
		return o;
	}

	public ListWrapper<Feed> getIpCityRangeFeedList(int rangeId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		query.setTable(Feed.class);
		query.where("rangeid=?").setParam(rangeId);
		query.orderByDesc("feedid");
		ListWrapper<Feed> o = new ListWrapper<Feed>();
		o.setBegin(begin);
		o.setSize(size);
		o.setList(query.list(begin, size, Feed.class));
		return o;
	}

	public ListWrapper<Feed> getIpFeedList(String ip, int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(Feed.class);
		query.where("ipnumber=?").setParam(DataUtil.parseIpNumber(ip));
		query.orderByDesc("feedid");
		ListWrapper<Feed> o = new ListWrapper<Feed>();
		o.setBegin(begin);
		o.setSize(size);
		o.setList(query.list(begin, size, Feed.class));
		return o;
	}

	public int countFeed(long userId) {
		Query query = this.manager.createQuery();
		return query.count(Feed.class, "userid=?", new Object[] { userId });
	}

	public List<Feed> getFeedListInIdList(List<Long> idList) {
		Query query = this.manager.createQuery();
		StringBuilder sb = new StringBuilder(
				"select * from feed where feedid in (");
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1).append(") order by feedid desc");
		return query.listBySqlEx("ds1", sb.toString(), Feed.class);
	}

	public List<Feed> getFeedListForIndex(int cityId, int begin, int size) {
		Query query = this.manager.createQuery();
		String sql = "select * from feed where cityid=? and feedtype in(?,?,?) order by feedid desc";
		return query.listBySql("ds1", sql, begin, size, Feed.class, cityId,
				Feed.FEEDTYPE_BECOME_MAYOR, Feed.FEEDTYPE_GETBADGE,
				Feed.FEEDTYPE_WRITETIPS);
	}

	public List<Feed> getFeedListForIndex(int begin, int size) {
		Query query = this.manager.createQuery();
		String sql = "select * from feed where feedtype in(?,?,?) order by feedid desc";
		return query.listBySql("ds1", sql, begin, size, Feed.class,
				Feed.FEEDTYPE_BECOME_MAYOR, Feed.FEEDTYPE_GETBADGE,
				Feed.FEEDTYPE_WRITETIPS);
	}

	public void updateFeed(Feed feed) {
		Query query = this.manager.createQuery();
		query.updateObject(feed);
	}

	public List<Long> getFeedIdListFromFeedInfoByObj2IdAndFeedType(long obj2Id,
			byte feedType) {
		Query query = this.manager.createQuery();
		String sql = "select feedid from feedinfo where obj2id=? and feedtype=?";
		return query.listBySqlEx("ds1", sql, Long.class, obj2Id, feedType);
	}

	public List<Long> getFeedIdListFromFeedInfoByObjIdAndFeedType(long objId,
			byte feedType) {
		Query query = this.manager.createQuery();
		String sql = "select feedid from feedinfo where objid=? and feedtype=?";
		return query.listBySqlEx("ds1", sql, Long.class, objId, feedType);
	}

	public List<Feed> getFeedListInId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<Feed>();
		}
		StringBuilder sql = new StringBuilder(
				"select * from feed where feedid in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		Query query = this.manager.createQuery();
		return query.listBySqlEx("ds1", sql.toString(), Feed.class);
	}

	public List<Feed> getFeedListByFeedType(byte feedType, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Feed.class, "feedtype=?",
				new Object[] { feedType }, "feedid desc", begin, size);
	}

	public List<Feed> getFeedListByFeedTypeAndCityid(byte feedType, int cityId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Feed.class, "feedtype=? and cityid=?",
				new Object[] { feedType, cityId }, "feedid desc", begin, size);
	}
}