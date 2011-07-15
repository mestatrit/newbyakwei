package svr;

import halo.util.DateUtil;
import iwant.bean.Notice;
import iwant.bean.UserNotice;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class NoticeSvrTest extends BaseSvrTest {

	private void assertNoticeData(Notice expected, Notice actual) {
		Assert.assertEquals(expected.getNoticeid(), actual.getNoticeid());
		Assert.assertEquals(expected.getContent(), actual.getContent());
		Assert.assertEquals(expected.getProjectid(), actual.getProjectid());
		Assert.assertEquals(expected.getCreatetime().getTime(), actual
				.getCreatetime().getTime());
	}

	@Test
	public void createNotice() {
		Notice notice = new Notice();
		notice.setContent("notice 5");
		notice.setProjectid(this.project0.getProjectid());
		notice.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
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
		UserNotice userNotice = this.noticeSvr.createUserNotice(this.notice0
				.getNoticeid(), this.user0.getUserid());
		Assert.assertNull(userNotice);
		userNotice = this.noticeSvr.createUserNotice(
				this.notice0.getNoticeid(), this.user1.getUserid());
		Assert.assertNotNull(userNotice);
	}

	@Test
	public void deleteUserNotice() {
		this.noticeSvr.deleteUserNotice(this.userNotice0);
		UserNotice userNotice = this.noticeSvr
				.getUserNoticeByUseridAndNoticeid(this.userNotice0.getUserid(),
						this.userNotice0.getNoticeid());
		Assert.assertNull(userNotice);
	}

	@Test
	public void getNoticeList() {
		List<Notice> list = this.noticeSvr.getNoticeList(0, 100);
		Assert.assertNotNull(list);
		Assert.assertEquals(2, list.size());
	}

	@Test
	public void getUserNoticeList() {
		List<UserNotice> list = this.noticeSvr.getUserNoticeList(false, 0, 100);
		Assert.assertNotNull(list);
		Assert.assertEquals(2, list.size());
	}
}