package tuxiazi.svr.test;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tuxiazi.bean.SinaUserFromAPI;
import tuxiazi.bean.User;
import tuxiazi.svr.exception.UserAlreadyExistException;
import tuxiazi.svr.iface.FriendService;
import tuxiazi.svr.iface.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml" })
@Transactional
public class FriendServiceTest {

	@Resource
	private FriendService friendService;

	@Resource
	private UserService userService;

	private User user0;

	private User user1;

	@Before
	public void before() {
		SinaUserFromAPI sinaUserFromAPI = new SinaUserFromAPI("accesstoken0",
				"tokenSecret0", 120395, "akwei0", "head0");
		try {
			this.user0 = this.userService.createUserFromSina(sinaUserFromAPI,
					false);
			sinaUserFromAPI = new SinaUserFromAPI("accesstoken1",
					"tokenSecret1", 120394, "akwei1", "head1");
			this.user1 = this.userService.createUserFromSina(sinaUserFromAPI,
					false);
		}
		catch (UserAlreadyExistException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void createFriend() {
		User user = this.user0;
		User friendUser = this.user1;
		boolean result = this.friendService.createFriend(user, friendUser,
				false, false);
		Assert.assertEquals(true, result);
		Assert.assertEquals(1, user.getFriend_num());
		Assert.assertEquals(0, user.getFans_num());
		Assert.assertEquals(0, friendUser.getFriend_num());
		Assert.assertEquals(1, friendUser.getFans_num());
		result = this.friendService
				.createFriend(friendUser, user, false, false);
		Assert.assertEquals(true, result);
		Assert.assertEquals(1, user.getFriend_num());
		Assert.assertEquals(1, user.getFans_num());
		Assert.assertEquals(1, friendUser.getFriend_num());
		Assert.assertEquals(1, friendUser.getFans_num());
		result = this.friendService
				.createFriend(user, friendUser, false, false);
		Assert.assertEquals(false, result);
		result = this.friendService
				.createFriend(friendUser, user, false, false);
		Assert.assertEquals(false, result);
	}

	@Test
	public void deleteFriend() {
		User user = this.user0;
		User friendUser = this.user1;
		this.friendService.createFriend(user, friendUser, false, false);
		this.friendService.createFriend(friendUser, user, false, false);
		this.friendService.deleteFriend(user, friendUser, false);
		Assert.assertEquals(0, user.getFriend_num());
		Assert.assertEquals(0, friendUser.getFans_num());
		this.friendService.deleteFriend(friendUser, user, false);
		Assert.assertEquals(0, user.getFans_num());
		Assert.assertEquals(0, friendUser.getFriend_num());
	}

	@After
	public void destory() {
	}
}