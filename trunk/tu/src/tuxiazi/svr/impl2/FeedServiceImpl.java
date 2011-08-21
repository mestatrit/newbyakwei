package tuxiazi.svr.impl2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import tuxiazi.bean.Friend_photo_feed;
import tuxiazi.dao.Friend_photo_feedDao;
import tuxiazi.svr.iface.FeedService;

public class FeedServiceImpl implements FeedService {

	@Autowired
	private Friend_photo_feedDao friend_photo_feedDao;

	/**
	 * 每人最多动态数据量
	 */
	private int maxSize = 200;

	@Override
	public void createFriend_photo_feed(List<Friend_photo_feed> friendPhotoFeeds) {
		if (friendPhotoFeeds.isEmpty()) {
			return;
		}
		long userid = friendPhotoFeeds.get(0).getUserid();
		for (Friend_photo_feed o : friendPhotoFeeds) {
			o.save();
		}
		int count = this.friend_photo_feedDao.countByUserid(userid);
		if (count > maxSize) {
			List<Friend_photo_feed> list = this.friend_photo_feedDao
					.getListByUserid(userid, false, false, 0, maxSize - 1,
							count - maxSize);
			for (Friend_photo_feed o : list) {
				o.delete();
			}
		}
	}

	@Override
	public void deleteFriend_photo_feedByPhotoid(long photoid) {
		this.friend_photo_feedDao.deleteByPhotoid(photoid);
	}

	@Override
	public void deleteFriend_photo_feedByUseridAndPhoto_userid(long userid,
			long photo_userid) {
		this.friend_photo_feedDao.deleteByUseridAndPhotoUserid(userid,
				photo_userid);
	}
}