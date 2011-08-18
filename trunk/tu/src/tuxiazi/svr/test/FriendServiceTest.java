package tuxiazi.svr.test;

import javax.annotation.Resource;

import org.aspectj.lang.annotation.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tuxiazi.bean.Friend;
import tuxiazi.svr.iface.FriendService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml" })
@Transactional
public class FriendServiceTest {

	@Resource
	private FriendService friendService;

	private long userid = 1;

	private long friendid = 2;

	@Test
	public void createFriend1() {
		Friend friend = new Friend();
		friend.setUserid(2);
		friend.setFriendid(1);
		this.friendService.createFriend(friend, false, true);
	}

	@After
	public void destory() {
		Friend friend = new Friend();
		friend.setUserid(userid);
		friend.setFriendid(friendid);
		this.friendService.deleteFriend(userid, friendid, true);
		friend = new Friend();
		friend.setUserid(friendid);
		friend.setFriendid(userid);
		this.friendService.deleteFriend(friendid, userid, true);
	}
}