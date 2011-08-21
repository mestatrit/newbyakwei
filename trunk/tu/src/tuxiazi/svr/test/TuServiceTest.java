package tuxiazi.svr.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tuxiazi.bean.Api_user;
import tuxiazi.bean.SinaUserFromAPI;
import tuxiazi.bean.User;
import tuxiazi.svr.exception.UserAlreadyExistException;
import tuxiazi.svr.iface.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
// 指定测试用例的运行器 这里是指定了Junit4
@ContextConfiguration({ "/applicationContext.xml" })
// 指定Spring的配置文件 /为classpath下
@Transactional
// 对所有的测试方法都使用事务，并在测试完成后回滚事务
public class TuServiceTest {

	@Resource
	private UserService userService;

	@Test
	public void test_createApi_user_sina() {
		SinaUserFromAPI sinaUserFromAPI = new SinaUserFromAPI("accesstoken",
				"tokenSecret", 120394, "akwei", "head");
		try {
			User u = this.userService.createUserFromSina(sinaUserFromAPI);
			User user = this.userService.getUser(u.getUserid());
			Assert.assertNotNull(user);
			Api_user apiUser = this.userService.getApi_userByUseridAndApi_type(
					u.getUserid(), Api_user.API_TYPE_SINA);
			Assert.assertNotNull(apiUser);
			Assert.assertEquals(u.getUserid(), user.getUserid());
			Assert.assertEquals(u.getUserid(), apiUser.getUserid());
			Assert.assertEquals(Api_user.API_TYPE_SINA, apiUser.getApi_type());
		}
		catch (UserAlreadyExistException e) {
			Assert.fail(e.getMessage());
		}
	}
}