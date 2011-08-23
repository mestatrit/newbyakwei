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
import tuxiazi.bean.SinaUserFromAPI;
import tuxiazi.bean.User;
import tuxiazi.dao.Api_userDao;
import tuxiazi.dao.Api_user_sinaDao;
import tuxiazi.dao.UserDao;
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

	@Resource
	private UserDao userDao;

	@Resource
	private Api_userDao api_userDao;

	@Resource
	Api_user_sinaDao api_user_sinaDao;

	@Test
	public void createUser() {
		SinaUserFromAPI sinaUserFromAPI = new SinaUserFromAPI("accesstoken0",
				"tokenSecret0", 120395, "akwei0", "head0");
		try {
			User u = this.userService
					.createUserFromSina(sinaUserFromAPI, false);
			User user = this.userDao.getById(u.getUserid());
			Assert.assertNotNull(user);
			Api_user apiUser = this.api_userDao.getByUseridAndApi_type(
					u.getUserid(), Api_user.API_TYPE_SINA);
			Assert.assertNotNull(apiUser);
			Assert.assertEquals(u.getUserid(), user.getUserid());
			Assert.assertEquals(u.getUserid(), apiUser.getUserid());
			Assert.assertEquals(Api_user.API_TYPE_SINA, apiUser.getApi_type());
			Api_user_sina api_user_sina = this.api_user_sinaDao.getByUserid(u
					.getUserid());
			Assert.assertNotNull(api_user_sina);
		}
		catch (UserAlreadyExistException e) {
			Assert.fail(e.getMessage());
		}
	}
}