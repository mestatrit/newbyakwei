package svr;

import iwant.bean.Notice;
import iwant.bean.UserNotice;
import iwant.svr.NoticeSvr;

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
public class NoticeSvrTest {

	@Resource
	private NoticeSvr noticeSvr;

	Notice notice0;

	Notice notice1;

	UserNotice userNotice0;

	UserNotice userNotice1;

	private void assertNoticeData(Notice expected, Notice actual) {
		Assert.assertEquals(expected.getNoticeid(), actual.getNoticeid());
		Assert.assertEquals(expected.getContent(), actual.getContent());
		Assert.assertEquals(expected.getProjectid(), actual.getProjectid());
		Assert.assertEquals(expected.getCreatetime().getTime(), actual
				.getCreatetime().getTime());
	}

	@Before
	public void init() {
		// data 0
		this.notice0 = new Notice();
		this.notice0.setContent("notice 0");
		this.notice0.setProjectid(10);
		this.notice0
				.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		this.noticeSvr.createNotice(this.notice0);
		// data 1
		this.notice1 = new Notice();
		this.notice1.setContent("notice 1");
		this.notice1.setProjectid(10);
		this.notice1
				.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		this.noticeSvr.createNotice(this.notice1);
		// usernotice
		this.userNotice0 = this.noticeSvr.createUserNotice(1, 2);
		this.userNotice1 = this.noticeSvr.createUserNotice(2, 2);
	}

	@Test
	public void createNotice() {
		Notice notice = new Notice();
		notice.setContent("notice 5");
		notice.setProjectid(12);
		notice.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		this.noticeSvr.createNotice(notice);
		if (notice.getNoticeid() <= 0) {
			Assert.fail("no noticeid");
		}
		Notice dbNotice = this.noticeSvr.getNotice(notice.getNoticeid());
		Assert.assertNotNull(dbNotice);
		this.assertNoticeData(notice, dbNotice);
	}

	@Test
	public void deleteNotice() {
		this.noticeSvr.deleteNotice(this.notice0.getNoticeid());
		Notice dbNotice = this.noticeSvr.getNotice(this.notice0.getNoticeid());
		Assert.assertNull(dbNotice);
	}

	@Test
	public void createUserNotice() {
		UserNotice userNotice = this.noticeSvr.createUserNotice(1, 2);
		Assert.assertNull(userNotice);
		userNotice = this.noticeSvr.createUserNotice(5, 2);
		Assert.assertNotNull(userNotice);
	}
}