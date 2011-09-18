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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.Lasted_photo;
import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoLikeUser;
import tuxiazi.bean.PhotoUserLike;
import tuxiazi.bean.SinaUserFromAPI;
import tuxiazi.bean.UploadPhoto;
import tuxiazi.bean.User;
import tuxiazi.bean.User_photo;
import tuxiazi.dao.Api_user_sinaDao;
import tuxiazi.dao.Lasted_photoDao;
import tuxiazi.dao.PhotoLikeUserDao;
import tuxiazi.dao.PhotoUserLikeDao;
import tuxiazi.dao.User_photoDao;
import tuxiazi.svr.exception.ImageSizeOutOfLimitException;
import tuxiazi.svr.exception.UserAlreadyExistException;
import tuxiazi.svr.iface.PhotoService;
import tuxiazi.svr.iface.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml" })
@Transactional
public class PhotoServiceTest {

	@Resource
	private UserService userService;

	private User user0;

	private User user1;

	private Photo photo;

	@Resource
	private PhotoService photoService;

	@Resource
	private User_photoDao user_photoDao;

	@Resource
	private Api_user_sinaDao api_user_sinaDao;

	@Resource
	private Lasted_photoDao lasted_photoDao;

	@Resource
	private PhotoUserLikeDao photoUserLikeDao;

	@Resource
	private PhotoLikeUserDao photoLikeUserDao;

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
//		UploadPhoto up = new UploadPhoto();
//		up.setName("aaa");
//		up.setCreate_time(new Date());
//		up.setFile(new File("/Users/fire9/test/test.png"));
//		Api_user_sina apiUserSina = this.api_user_sinaDao
//				.getByUserid(this.user0.getUserid());
//		try {
//			photo = this.photoService.createPhoto(up, false, this.user0,
//					apiUserSina);
//		}
//		catch (ImageSizeOutOfLimitException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (ImageException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Test
	public void createPhoto() {
		UploadPhoto up = new UploadPhoto();
		up.setName("aaa");
		up.setCreate_time(new Date());
		up.setFile(new File("d:/test/test0.jpg"));
		Api_user_sina apiUserSina = this.api_user_sinaDao
				.getByUserid(this.user0.getUserid());
		try {
			Photo photo = this.photoService.createPhoto(up, false, this.user0,
					apiUserSina);
			Assert.assertNotNull(photo);
			Assert.assertEquals(up.getName(), photo.getName());
			Assert.assertEquals(this.user0.getUserid(), photo.getUserid());
			User_photo user_photo = this.user_photoDao.getByUseridAndPhotoid(
					this.user0.getUserid(), photo.getPhotoid());
			Assert.assertNotNull(user_photo);
			Lasted_photo lasted_photo = this.lasted_photoDao.getById(photo
					.getPhotoid());
			Assert.assertNotNull(lasted_photo);
		}
		catch (ImageSizeOutOfLimitException e) {
			Assert.fail(e.getMessage());
		}
		catch (ImageException e) {
			Assert.fail(e.getMessage());
		}
		catch (IOException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void createPhotoUserLike() {
		PhotoUserLike photoUserLike = this.photoService.createPhotoUserLike(
				this.user1, this.photo);
		Assert.assertNotNull(photoUserLike);
		Assert.assertEquals(this.user1.getUserid(), photoUserLike.getUserid());
		Assert.assertEquals(this.photo.getPhotoid(), photoUserLike.getPhotoid());
		PhotoLikeUser photoLikeUser = this.photoLikeUserDao
				.getByUseridAndPhotoid(this.user1.getUserid(),
						this.photo.getPhotoid());
		Assert.assertNotNull(photoLikeUser);
		Assert.assertEquals(this.user1.getUserid(), photoLikeUser.getUserid());
		Assert.assertEquals(this.photo.getPhotoid(), photoLikeUser.getPhotoid());
	}

	@Test
	public void deletePhotoUserLike() {
		PhotoUserLike photoUserLike = this.photoService.createPhotoUserLike(
				this.user1, this.photo);
		Assert.assertNotNull(photoUserLike);
		Assert.assertEquals(this.user1.getUserid(), photoUserLike.getUserid());
		Assert.assertEquals(this.photo.getPhotoid(), photoUserLike.getPhotoid());
		PhotoLikeUser photoLikeUser = this.photoLikeUserDao
				.getByUseridAndPhotoid(this.user1.getUserid(),
						this.photo.getPhotoid());
		Assert.assertNotNull(photoLikeUser);
		Assert.assertEquals(this.user1.getUserid(), photoLikeUser.getUserid());
		Assert.assertEquals(this.photo.getPhotoid(), photoLikeUser.getPhotoid());
		this.photoService.deletePhotoUserLike(this.user1, this.photo);
		photoUserLike = this.photoUserLikeDao.getByUseridAndPhotoid(
				this.user1.getUserid(), this.photo.getPhotoid());
		Assert.assertNull(photoUserLike);
		photoLikeUser = this.photoLikeUserDao.getByUseridAndPhotoid(
				this.user1.getUserid(), this.photo.getPhotoid());
		Assert.assertNull(photoLikeUser);
	}
}