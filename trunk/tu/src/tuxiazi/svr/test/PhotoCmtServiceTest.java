package tuxiazi.svr.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tuxiazi.dao.PhotoCmtDao;
import tuxiazi.dao.PhotoDao;
import tuxiazi.dao.UserDao;
import tuxiazi.svr.iface.PhotoCmtService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml" })
@Transactional
public class PhotoCmtServiceTest {

	@Resource
	private PhotoCmtService photoCmtService;

	@Autowired
	private PhotoDao photoDao;

	@Autowired
	private PhotoCmtDao photoCmtDao;

	@Autowired
	private UserDao userDao;

	@Test
	public void createPhotoCmt() {
	}
}