package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.BlockUser;
import com.hk.bean.Follow;
import com.hk.bean.Followed;
import com.hk.bean.User;
import com.hk.svr.friend.exception.AlreadyBlockException;

public interface FollowService {
	/**
	 * 加好友，如果对方阻止你，将不能加好友
	 * 
	 * @param userId
	 * @param friendId
	 */
	boolean addFollow(long userId, long friendId, String ip,
			boolean noticeAndFeed) throws AlreadyBlockException;

	void setBothFollow(long userId, long friendId, boolean both);

	/**
	 * 按照最最近更新时间操作
	 * 
	 * @param userId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Long> getFansIdListByUserId(long userId, int begin, int size);

	/**
	 * 删除好友,单项解除关系
	 * 
	 * @param userId
	 * @param friendId
	 */
	void removeFollow(long userId, long friendId);

	/**
	 * 阻止某人,双向解除关系,并加入黑名单,直到阻止人主动加好友来解除封锁 .(被阻止人不能阻止再次阻止目标人)
	 * 
	 * @param userId
	 * @param friendId
	 */
	void blockUser(long userId, long blockUserId) throws AlreadyBlockException;

	/**
	 * 解除屏蔽
	 * 
	 * @param userId
	 * @param blockUserId
	 */
	void removeBlockUser(long userId, long blockUserId);

	/**
	 * 活得好友列表
	 * 
	 * @param userId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Follow> getFollowList(long userId, int begin, int size);

	List<Follow> getFollowListByNickName(long userId, String nickName,
			int begin, int size);

	List<Follow> getFollowList(long userId);

	/**
	 * 统计好友数量
	 * 
	 * @param userId
	 * @return
	 */
	int countFollow(long userId);

	int countFollowed(long userId);

	Follow getFollow(long userId, long friendId);

	BlockUser getBlockUser(long userId, long blockUserId);

	List<Followed> getFollowedList(long userId, int begin, int size);

	List<Follow> getBothFollowList(long userId);

	List<Followed> getFollowedListByNickName(long userId, String nickName,
			int begin, int size);

	List<User> getUserListForSend(long userId, int begin, int size);

	List<User> getUserListByNickNameForSend(long userId, String nickName,
			int begin, int size);

	List<Long> getUserIdList(long userId, List<Long> idList);

	List<Followed> getFollowedListByfollowingUserIdInUserId(long userId,
			List<Long> idList);

	Map<Long, Followed> getFollowedMapByfollowingUserIdInUserId(long userId,
			List<Long> idList);

	List<Long> getFollowFriendIdListByUserId(long userId);
}