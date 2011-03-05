package tuxiazi.svr.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tuxiazi.bean.Friend_photo_feed;
import tuxiazi.svr.iface.FeedService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/applicationContext.xml" })
@Transactional
public class FeedServiceTest {

	@Resource
	private FeedService feedService;

	private long userid = 1;

	@Test
	public void createFriend_photo_feed() {
		List<Friend_photo_feed> list = new ArrayList<Friend_photo_feed>();
		for (int i = 0; i < 100; i++) {
			Friend_photo_feed o = new Friend_photo_feed();
			o.setUserid(userid);
			o.setCreate_time(new Date());
			o.setPhotoid(1);
			o.setPhoto_userid(1);
			list.add(o);
		}
		this.feedService.createFriend_photo_feed(list);
		List<Friend_photo_feed> list2 = this.feedService
				.getFriend_photo_feedListByUserid(userid, false, false, 0, 0,
						100);
		Assert.assertEquals(40, list2.size());
	}
}