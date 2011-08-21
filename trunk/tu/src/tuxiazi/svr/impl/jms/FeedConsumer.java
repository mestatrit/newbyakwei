package tuxiazi.svr.impl.jms;

import halo.util.JsonUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import tuxiazi.bean.Friend_photo_feed;
import tuxiazi.bean.User;
import tuxiazi.dao.FansDao;
import tuxiazi.svr.iface.FeedService;
import tuxiazi.svr.iface.UserService;

public class FeedConsumer {

	private final Log log = LogFactory.getLog(FeedConsumer.class);

	@Autowired
	private FeedService feedService;

	@Autowired
	private UserService userService;

	@Autowired
	private FansDao fansDao;

	public void processMessage(String value) {
		JmsMsg jmsMsg = new JmsMsg(value);
		if (jmsMsg.getHead().equals(JmsMsg.HEAD_PHOTO_CREATEPHOTO)) {
			this.proccessCreatePhoto(jmsMsg.getBody());
			return;
		}
		log.error("unknown message type [ " + value + " ]");
	}

	private void proccessCreatePhoto(String body) {
		Map<String, String> map = JsonUtil.getMapFromJson(body);
		long userid = Long.valueOf(map.get(JsonKey.userid));
		User user = this.userService.getUser(userid);
		if (user == null) {
			return;
		}
		String photos = map.get(JsonKey.photos);
		List<Long> fansidList = this.fansDao.getFansidListByUserid(userid);
		String[] photoarr = photos.split(",");
		Friend_photo_feed friendPhotoFeed = null;
		Date date = new Date();
		List<Friend_photo_feed> list = null;
		for (Long fansid : fansidList) {
			list = new ArrayList<Friend_photo_feed>();
			for (String photo_v : photoarr) {
				if (photo_v.equals("")) {
					continue;
				}
				String[] tmp = photo_v.split(":");
				String id = tmp[0];
				String photo_userid = tmp[1];
				friendPhotoFeed = new Friend_photo_feed();
				friendPhotoFeed.setUserid(fansid);
				friendPhotoFeed.setPhotoid(Long.valueOf(id));
				friendPhotoFeed.setCreate_time(date);
				friendPhotoFeed.setPhoto_userid(Long.valueOf(photo_userid));
				list.add(friendPhotoFeed);
			}
			this.feedService.createFriend_photo_feed(list);
		}
	}
}