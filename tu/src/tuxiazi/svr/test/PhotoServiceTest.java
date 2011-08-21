package tuxiazi.svr.test;

import halo.util.image.ImageException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.Photo;
import tuxiazi.bean.UploadPhoto;
import tuxiazi.bean.User;
import tuxiazi.svr.exception.ImageSizeOutOfLimitException;
import tuxiazi.svr.iface.PhotoService;
import tuxiazi.svr.iface.UploadPhotoResult;
import tuxiazi.svr.iface.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml" })
public class PhotoServiceTest {

	private long userid = 3;

	private long userid2 = 4;

	@Resource
	private PhotoService photoService;

	@Resource
	private UserService userService;

	private Log log = LogFactory.getLog(PhotoServiceTest.class);

	private UploadPhotoResult uploadPhotoResult;

	@Test
	public void createPhoto() {
		List<UploadPhoto> list = new ArrayList<UploadPhoto>();
		UploadPhoto up = new UploadPhoto();
		up.setName("aaa");
		up.setUserid(userid);
		up.setCreate_time(new Date());
		up.setFile(new File("c:/test/284DF4F7812EF3C96C69253C12B1775F.jpg"));
		list.add(up);
		User user = this.userService.getUser(userid);
		Api_user_sina apiUserSina = this.userService
				.getApi_user_sinaByUserid(userid);
		try {
			Photo photo = this.photoService.createPhoto(up, false, user,
					apiUserSina);
			Assert.assertNotNull(photo);
			Assert.assertNotNull(uploadPhotoResult);
			Assert.assertNotNull(uploadPhotoResult.getPhotos());
			Assert.assertEquals(2, uploadPhotoResult.getPhotos().size());
			Assert.assertEquals(true, uploadPhotoResult.isSuccess());
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
	public void getLasted() {
		// this.photoService.getLasted_photoList(true, false, 2, 0, 1);
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			this.photoService.getLasted_photoList(true, false, 0, 0, 1);
		}
		long end = System.currentTimeMillis();
		log.info("time [ " + (end - begin) + " ]");
	}

	@Test
	public void createPhotoUserLike() {
		this.createPhoto();
		if (uploadPhotoResult.isSuccess()) {
			User user2 = this.userService.getUser(userid2);
			for (Photo photo : uploadPhotoResult.getPhotos()) {
				this.photoService.createPhotoUserLike(user2, photo);
			}
		}
	}

	@Test
	public void deletePhotoUserLike() {
		Photo photo = this.photoService.getPhoto(31);
		this.photoService.deletePhotoUserLike(userid2, photo);
	}
}