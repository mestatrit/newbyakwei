package com.hk.svr.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hk.bean.BlockUser;
import com.hk.bean.Follow;
import com.hk.bean.Followed;
import com.hk.bean.User;
import com.hk.listener.user.FollowEvent;
import com.hk.listener.user.FollowEventListener;
import com.hk.svr.FollowService;
import com.hk.svr.friend.exception.AlreadyBlockException;

public class FollowServiceWrapper implements FollowService {
	private FollowService followService;

	private List<FollowEventListener> followEventListenerList;

	public void setFollowService(FollowService followService) {
		this.followService = followService;
	}

	public void setFollowEventListenerList(
			List<FollowEventListener> followEventListenerList) {
		this.followEventListenerList = followEventListenerList;
	}

	public void addFollowEventListener(FollowEventListener listener) {
		if (followEventListenerList == null) {
			followEventListenerList = new ArrayList<FollowEventListener>();
		}
		followEventListenerList.add(listener);
	}

	public boolean addFollow(long userId, long friendId, String ip,
			boolean noticeAndFeed) throws AlreadyBlockException {
		boolean result = followService.addFollow(userId, friendId, ip,
				noticeAndFeed);
		FollowEvent event = new FollowEvent(userId);
		event.setFriendId(friendId);
		event.setResutlt(result);
		if (noticeAndFeed) {
			for (FollowEventListener listener : followEventListenerList) {
				listener.followCreated(event);
			}
		}
		return result;
	}

	public void removeFollow(long userId, long friendId) {
		followService.removeFollow(userId, friendId);
		FollowEvent event = new FollowEvent(userId);
		event.setFriendId(friendId);
		for (FollowEventListener listener : followEventListenerList) {
			listener.followDeleted(event);
		}
	}

	public void blockUser(long userId, long blockUserId)
			throws AlreadyBlockException {
		followService.blockUser(userId, blockUserId);
	}

	public int countFollow(long userId) {
		return followService.countFollow(userId);
	}

	public int countFollowed(long userId) {
		return followService.countFollowed(userId);
	}

	public BlockUser getBlockUser(long userId, long blockUserId) {
		return followService.getBlockUser(userId, blockUserId);
	}

	public List<Follow> getBothFollowList(long userId) {
		return followService.getBothFollowList(userId);
	}

	public Follow getFollow(long userId, long friendId) {
		return followService.getFollow(userId, friendId);
	}

	public List<Followed> getFollowedList(long userId, int begin, int size) {
		return followService.getFollowedList(userId, begin, size);
	}

	public List<Followed> getFollowedListByfollowingUserIdInUserId(long userId,
			List<Long> idList) {
		return followService.getFollowedListByfollowingUserIdInUserId(userId,
				idList);
	}

	public List<Followed> getFollowedListByNickName(long userId,
			String nickName, int begin, int size) {
		return followService.getFollowedListByNickName(userId, nickName, begin,
				size);
	}

	public Map<Long, Followed> getFollowedMapByfollowingUserIdInUserId(
			long userId, List<Long> idList) {
		return followService.getFollowedMapByfollowingUserIdInUserId(userId,
				idList);
	}

	public List<Follow> getFollowList(long userId, int begin, int size) {
		return followService.getFollowList(userId, begin, size);
	}

	public List<Follow> getFollowList(long userId) {
		return followService.getFollowList(userId);
	}

	public List<Follow> getFollowListByNickName(long userId, String nickName,
			int begin, int size) {
		return followService.getFollowListByNickName(userId, nickName, begin,
				size);
	}

	public List<Long> getUserIdList(long userId, List<Long> idList) {
		return followService.getUserIdList(userId, idList);
	}

	public List<User> getUserListByNickNameForSend(long userId,
			String nickName, int begin, int size) {
		return followService.getUserListByNickNameForSend(userId, nickName,
				begin, size);
	}

	public List<User> getUserListForSend(long userId, int begin, int size) {
		return followService.getUserListForSend(userId, begin, size);
	}

	public void removeBlockUser(long userId, long blockUserId) {
		followService.removeBlockUser(userId, blockUserId);
	}

	public void setBothFollow(long userId, long friendId, boolean both) {
		followService.setBothFollow(userId, friendId, both);
	}

	public List<Long> getFollowFriendIdListByUserId(long userId) {
		return followService.getFollowFriendIdListByUserId(userId);
	}

	public List<Long> getFansIdListByUserId(long userId, int begin, int size) {
		return this.followService.getFansIdListByUserId(userId, begin, size);
	}
}