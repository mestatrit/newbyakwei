package tuxiazi.svr.test;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoCmt;
import tuxiazi.bean.User;
import tuxiazi.svr.iface.PhotoCmtService;
import tuxiazi.svr.iface.PhotoService;
import tuxiazi.svr.iface.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/applicationContext.xml" })
@Transactional
public class PhotoCmtServiceTest {

	@Resource
	private PhotoCmtService photoCmtService;

	@Resource
	private PhotoService photoService;

	@Resource
	private UserService userService;

	@Test
	public void createPhotoCmt() {
		long userid = 2;
		long photoid = 33;
		Photo photo = this.photoService.getPhoto(photoid);
		User user = this.userService.getUser(userid);
		PhotoCmt photoCmt = new PhotoCmt();
		photoCmt.setUserid(userid);
		photoCmt.setPhotoid(photoid);
		photoCmt.setCreate_time(new Date());
		photoCmt.setContent("测试评论");
		this.photoCmtService.createPhotoCmt(photo, photoCmt, user);
		PhotoCmt photoCmt2 = this.photoCmtService.getPhotoCmt(photoid, photoCmt
				.getCmtid());
		Assert.assertNotNull(photoCmt2);
		Assert.assertEquals(photoCmt.getCmtid(), photoCmt2.getCmtid());
	}
}