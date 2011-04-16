package svr;

import iwant.bean.User;
import iwant.bean.enumtype.GenderType;
import iwant.svr.UserSvr;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hk.frame.util.DataUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/app-ds.xml", "/app-dao.xml", "/app-svr.xml" })
@Transactional
public class UserSvrTest {

	@Resource
	private UserSvr userSvr;

	private User user0;

	private User user1;

	private void assertData(User expected, User actual) {
		Assert.assertEquals(expected.getUserid(), actual.getUserid());
		Assert.assertEquals(expected.getDevice_token(), actual
				.getDevice_token());
		Assert.assertEquals(expected.getEmail(), actual.getEmail());
		Assert.assertEquals(expected.getMobile(), actual.getMobile());
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.getGender(), actual.getGender());
		Assert.assertEquals(expected.getCreatetime().getTime(), actual
				.getCreatetime().getTime());
	}

	@Before
	public void init() {
		this.user0 = new User();
		this.user0.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		this.user0.setDevice_token("a");
		this.user0.setEmail("");
		this.user0.setGender(GenderType.MALE.getValue());
		this.user0.setMobile("");
		this.user0.setName("");
		this.userSvr.createUser(this.user0);
		this.user1 = new User();
		this.user1.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		this.user1.setDevice_token("b");
		this.user1.setEmail("");
		this.user1.setGender(GenderType.MALE.getValue());
		this.user1.setMobile("");
		this.user1.setName("");
		this.userSvr.createUser(this.user1);
	}

	@Test
	public void createUser() {
		// Assert.fail();
		User user = new User();
		user.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		user.setDevice_token("c");
		user.setEmail("");
		user.setGender(GenderType.MALE.getValue());
		user.setMobile("");
		user.setName("");
		boolean result = this.userSvr.createUser(user);
		Assert.assertEquals(true, result);
	}

	@Test
	public void updateUser() {
		// Assert.fail();
		User user = this.userSvr.getUserByUserid(this.user0.getUserid());
		Assert.assertNotNull(user);
		user.setName("akweiwei");
		user.setEmail("a@1.com");
		user.setGender(GenderType.FEMALE.getValue());
		user.setMobile("111");
		user.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		user.setDevice_token("b");
		boolean result = this.userSvr.updateUser(user);
		Assert.assertEquals(false, result);
		user.setDevice_token("c");
		result = this.userSvr.updateUser(user);
		Assert.assertEquals(true, result);
		User dbUser = this.userSvr.getUserByUserid(this.user0.getUserid());
		Assert.assertNotNull(dbUser);
		this.assertData(user, dbUser);
	}

	@Test
	public void getUserByUserid() {
		// Assert.fail();
		User user = this.userSvr.getUserByUserid(this.user0.getUserid());
		this.assertData(this.user0, user);
	}
}
