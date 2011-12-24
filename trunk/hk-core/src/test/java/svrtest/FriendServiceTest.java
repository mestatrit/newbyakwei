package svrtest;

import java.util.List;

import com.hk.bean.Follow;
import com.hk.frame.util.P;
import com.hk.svr.FollowService;
import com.hk.svr.friend.exception.AlreadyBlockException;

public class FriendServiceTest extends HkServiceTest {

	private FollowService followService;

	public void setFollowService(FollowService followService) {
		this.followService = followService;
	}

	public void testGetFriendIdList() {
		long userId = 1;
		List<Long> list = this.followService
				.getFollowFriendIdListByUserId(userId);
		StringBuffer sb = new StringBuffer();
		for (Long l : list) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		P.println(sb.toString());
	}

	public void ttestGetFollow() {
		long userId = 3;
		long friendId = 999;
		Follow follow = this.followService.getFollow(userId, friendId);
		System.out.println(follow);
	}

	public void ttestAddFriend() {
		long userId = 4490;
		for (int i = 0; i < 20; i++) {
			long friendId = 900 + i;
			try {
				this.followService.addFollow(userId, friendId,
						"123.121.214.218", true);
			}
			catch (AlreadyBlockException e) {
				e.printStackTrace();
			}
		}
		this.commit();
	}

	public void ttestRemoveFriend() {
		long userId = 1;
		long friendId = 989;
		this.followService.removeFollow(userId, friendId);
		// this.commit();
	}

	public void ttestBlockFriend() {
		long userId = 3;
		long friendId = 4;
		try {
			this.followService.blockUser(userId, friendId);
		}
		catch (AlreadyBlockException e) {
			e.printStackTrace();
		}
	}
}