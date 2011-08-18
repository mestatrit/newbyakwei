package tuxiazi.svr.impl2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import tuxiazi.bean.Friend_photo_feed;
import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoUserLike;
import tuxiazi.bean.User;
import tuxiazi.dao.Friend_photo_feedDao;
import tuxiazi.svr.iface.FeedService;
import tuxiazi.svr.iface.PhotoService;
import tuxiazi.svr.iface.UserService;

public class FeedServiceImpl implements FeedService {

	private PhotoService photoService;

	private UserService userService;

	private Friend_photo_feedDao friend_photo_feedDao;

	public void setFriend_photo_feedDao(Friend_photo_feedDao friendPhotoFeedDao) {
		friend_photo_feedDao = friendPhotoFeedDao;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}

	/**
	 * 每人最多动态数据量
	 */
	private int maxSize;

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	@Override
	public void createFriend_photo_feed(List<Friend_photo_feed> friendPhotoFeeds) {
		if (friendPhotoFeeds.isEmpty()) {
			return;
		}
		long userid = friendPhotoFeeds.get(0).getUserid();
		for (Friend_photo_feed o : friendPhotoFeeds) {
			this.friend_photo_feedDao.save(null, o);
		}
		int count = this.friend_photo_feedDao.count(null, "userid=?",
				new Object[] { userid });
		if (count > maxSize) {
			List<Friend_photo_feed> list = this.friend_photo_feedDao.getList(
					null, "userid=?", new Object[] { userid }, "feedid asc",
					maxSize - 1, count - maxSize);
			for (Friend_photo_feed o : list) {
				this.friend_photo_feedDao.deleteById(o.getFeedid());
			}
		}
	}

	@Override
	public void deleteFriend_photo_feedByPhotoid(long photoid) {
		this.friend_photo_feedDao.delete(null, "photoid=?",
				new Object[] { photoid });
	}

	@Override
	public void deleteFriend_photo_feedByUseridAndPhoto_userid(long userid,
			long photo_userid) {
		this.friend_photo_feedDao.delete(null, "userid=? and photo_userid=?",
				new Object[] { userid, photo_userid });
	}

	@Override
	public List<Friend_photo_feed> getFriend_photo_feedListByUserid(
			long userid, boolean buildPhoto, boolean buildPhotoUser,
			long favUserid, int begin, int size) {
		List<Friend_photo_feed> list = this.friend_photo_feedDao.getList(null,
				"userid=?", new Object[] { userid }, "photoid desc", begin,
				size);
		if (buildPhoto) {
			List<Long> idList = new ArrayList<Long>();
			for (Friend_photo_feed o : list) {
				idList.add(o.getPhotoid());
			}
			Map<Long, Photo> map = this.photoService.getPhotoMapInId(idList);
			for (Friend_photo_feed o : list) {
				o.setPhoto(map.get(o.getPhotoid()));
			}
			if (buildPhotoUser) {
				idList = new ArrayList<Long>();
				for (Entry<Long, Photo> e : map.entrySet()) {
					idList.add(e.getValue().getUserid());
				}
				Map<Long, User> usermap = this.userService
						.getUserMapInId(idList);
				for (Friend_photo_feed o : list) {
					if (o.getPhoto() != null) {
						o.getPhoto().setUser(
								usermap.get(o.getPhoto().getUserid()));
					}
				}
			}
			if (favUserid > 0) {
				List<PhotoUserLike> photoUserLikes = this.photoService
						.getPhotoUserLikeListByUserid(favUserid, false, begin,
								-1);
				Set<Long> photoidset = new HashSet<Long>();
				for (PhotoUserLike o : photoUserLikes) {
					photoidset.add(o.getPhotoid());
				}
				for (Friend_photo_feed o : list) {
					if (photoidset.contains(o.getPhotoid())) {
						if (o.getPhoto() != null) {
							o.getPhoto().setOpliked(true);
						}
					}
				}
			}
		}
		return list;
	}
}