package tuxiazi.svr.iface;

import java.util.List;

import tuxiazi.bean.Friend_photo_feed;

public interface FeedService {

	void createFriend_photo_feed(List<Friend_photo_feed> friendPhotoFeeds);

	void deleteFriend_photo_feedByUseridAndPhoto_userid(long userid,
			long photo_userid);

	void deleteFriend_photo_feedByPhotoid(long photoid);
}