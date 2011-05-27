package svr;

import iwant.bean.User;
import iwant.bean.enumtype.GenderType;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.dev3g.cactus.util.DateUtil;

public class UserSvrTest extends BaseSvrTest {

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

	@Test
	public void createUser() {
		User user = new User();
		user.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
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
		User user = this.userSvr.getUserByUserid(this.user0.getUserid());
		Assert.assertNotNull(user);
		user.setName("akweiwei");
		user.setEmail("a@1.com");
		user.setGender(GenderType.FEMALE.getValue());
		user.setMobile("111");
		user.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
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
		User user = this.userSvr.getUserByUserid(this.user0.getUserid());
		this.assertData(this.user0, user);
	}
}
