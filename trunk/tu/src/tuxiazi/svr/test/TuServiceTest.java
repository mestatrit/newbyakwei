package tuxiazi.svr.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tuxiazi.bean.Api_user;
import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.User;
import tuxiazi.svr.iface.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
// 指定测试用例的运行器 这里是指定了Junit4
@ContextConfiguration( { "/applicationContext.xml" })
// 指定Spring的配置文件 /为classpath下
@Transactional
// 对所有的测试方法都使用事务，并在测试完成后回滚事务
public class TuServiceTest {

	@Resource
	private UserService userService;

	@Test
	public void test_createApi_user_sina() {
		Api_user_sina apiUserSina = new Api_user_sina();
		apiUserSina.setSina_userid(120394);
		apiUserSina.setAccess_token("accesstoken");
		apiUserSina.setToken_secret("tokenSecret");
		this.userService.createApi_user_sina(apiUserSina, "akwei", null);
		User user = this.userService.getUser(apiUserSina.getUserid());
		Assert.assertNotNull(user);
		Api_user apiUser = this.userService.getApi_userByUseridAndApi_type(
				apiUserSina.getUserid(), Api_user.API_TYPE_SINA);
		Assert.assertNotNull(apiUser);
		Assert.assertEquals(apiUserSina.getUserid(), user.getUserid());
		Assert.assertEquals(apiUserSina.getUserid(), apiUser.getUserid());
		Assert.assertEquals(Api_user.API_TYPE_SINA, apiUser.getApi_type());
	}
}