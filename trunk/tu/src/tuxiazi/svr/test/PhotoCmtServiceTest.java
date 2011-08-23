package tuxiazi.svr.test;

import halo.util.image.ImageException;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoCmt;
import tuxiazi.bean.SinaUserFromAPI;
import tuxiazi.bean.UploadPhoto;
import tuxiazi.bean.User;
import tuxiazi.dao.Api_user_sinaDao;
import tuxiazi.dao.PhotoCmtDao;
import tuxiazi.svr.exception.ImageSizeOutOfLimitException;
import tuxiazi.svr.exception.UserAlreadyExistException;
import tuxiazi.svr.iface.PhotoCmtService;
import tuxiazi.svr.iface.PhotoService;
import tuxiazi.svr.iface.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml" })
@Transactional
public class PhotoCmtServiceTest {

	@Resource
	private PhotoCmtService photoCmtService;

	@Autowired
	private PhotoCmtDao photoCmtDao;

	@Resource
	private UserService userService;

	private User user0;

	private Photo photo;

	@Resource
	private PhotoService photoService;

	@Resource
	private Api_user_sinaDao api_user_sinaDao;

	@Before
	public void before() {
		SinaUserFromAPI sinaUserFromAPI = new SinaUserFromAPI("accesstoken0",
				"tokenSecret0", 120395, "akwei0", "head0");
		try {
			this.user0 = this.userService.createUserFromSina(sinaUserFromAPI,
					false);
		}
		catch (UserAlreadyExistException e) {
			Assert.fail(e.getMessage());
		}
		UploadPhoto up = new UploadPhoto();
		up.setName("aaa");
		up.setCreate_time(new Date());
		up.setFile(new File("/Users/fire9/test/test.jpg"));
		Api_user_sina apiUserSina = this.api_user_sinaDao
				.getByUserid(this.user0.getUserid());
		try {
			photo = this.photoService.createPhoto(up, false, this.user0,
					apiUserSina);
		}
		catch (ImageSizeOutOfLimitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ImageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void createPhotoCmt() {
		String content = "测试发送内容";
		Api_user_sina apiUserSina = this.api_user_sinaDao
				.getByUserid(this.user0.getUserid());
		PhotoCmt photoCmt = this.photoCmtService.createPhotoCmt(photo, content,
				this.user0, null, false, apiUserSina);
		PhotoCmt _photoCmt = this.photoCmtDao.getById(photoCmt.getCmtid());
		Assert.assertNotNull(photoCmt);
		Assert.assertNotNull(_photoCmt);
		Assert.assertEquals(photoCmt.getCmtid(), photoCmt.getCmtid());
		Assert.assertEquals(photoCmt.getContent(), _photoCmt.getContent());
		Assert.assertEquals(photoCmt.getUserid(), _photoCmt.getUserid());
		Assert.assertEquals(photoCmt.getPhotoid(), _photoCmt.getPhotoid());
		Assert.assertEquals(photoCmt.getReplyuserid(),
				_photoCmt.getReplyuserid());
	}
}