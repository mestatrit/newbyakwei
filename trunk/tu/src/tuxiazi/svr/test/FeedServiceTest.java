package tuxiazi.svr.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tuxiazi.bean.Friend_photo_feed;
import tuxiazi.svr.iface.FeedService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml" })
@Transactional
public class FeedServiceTest {

	@Resource
	private FeedService feedService;

	@Test
	public void createFeed() {
		Friend_photo_feed feed = new Friend_photo_feed(1, 2, new Date(), 3);
		List<Friend_photo_feed> list = new ArrayList<Friend_photo_feed>();
		list.add(feed);
		this.feedService.createFriend_photo_feed(list);
	}
}