package tuxiazi.svr.impl2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tuxiazi.bean.Fans;
import tuxiazi.bean.Friend;
import tuxiazi.bean.Friend_photo_feed;
import tuxiazi.bean.User;
import tuxiazi.bean.User_photo;
import tuxiazi.bean.helper.noticedata.FollowNoticeCreater;
import tuxiazi.dao.FansDao;
import tuxiazi.dao.FriendDao;
import tuxiazi.svr.iface.FeedService;
import tuxiazi.svr.iface.FriendService;
import tuxiazi.svr.iface.NoticeService;
import tuxiazi.svr.iface.PhotoService;
import tuxiazi.svr.iface.UserService;

import com.hk.frame.util.NumberUtil;

public class FriendServiceImpl implements FriendService {

	private UserService userService;

	private NoticeService noticeService;

	private PhotoService photoService;

	private FeedService feedService;

	private FriendDao friendDao;

	private FansDao fansDao;

	public void setFansDao(FansDao fansDao) {
		this.fansDao = fansDao;
	}

	public void setFriendDao(FriendDao friendDao) {
		this.friendDao = friendDao;
	}

	public void setFeedService(FeedService feedService) {
		this.feedService = feedService;
	}

	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}

	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private Comparator<User_photo> comparator = new Comparator<User_photo>() {

		@Override
		public int compare(User_photo o1, User_photo o2) {
			if (o1.getPhotoid() > o2.getPhotoid()) {
				return 1;
			}
			return -1;
		}
	};

	@Override
	public void createFriend(Friend friend, boolean sendNotice, boolean getPhoto) {
		if (friend.getFriendid() == friend.getUserid()) {
			return;
		}
		if (this.friendDao.getObject(null, "userid=? and friendid=?",
				new Object[] { friend.getUserid(), friend.getFriendid() }) != null) {
			return;
		}
		// 查看对方是否已经加用户为好友
		Friend friend2 = this.friendDao.getObject(null,
				"userid=? and friendid=?", new Object[] { friend.getFriendid(),
						friend.getUserid() });
		// 如果已经加用户为好友
		if (friend2 != null) {
			friend2.setFlg(Friend.FLG_BOTH);
			friend.setFlg(Friend.FLG_BOTH);
			this.friendDao.update(null, friend2);
		}
		else {
			friend.setFlg(Friend.FLG_NOBOTH);
		}
		// 创建好友关系
		friend.setOid(NumberUtil.getLong(this.friendDao.save(null, friend)));
		User user = this.userService.getUser(friend.getUserid());
		if (sendNotice) {
			// 发送follow的通知到对方
			FollowNoticeCreater followNoticeCreater = new FollowNoticeCreater();
			followNoticeCreater.setUserid(friend.getFriendid());
			followNoticeCreater.setSenderid(friend.getUserid());
			followNoticeCreater.setSender_nick(user.getNick());
			followNoticeCreater.setSender_head(user.getHead_path());
			this.noticeService.createNotice(followNoticeCreater.buildNotice());
		}
		// 更新好友数量
		user.setFriend_num(user.getFriend_num() + 1);
		this.userService.update(user);
		// 创建对方粉丝数据
		Fans fans2 = this.fansDao.getObject(null, "userid=? and fansid=?",
				new Object[] { friend.getFriendid(), friend.getUserid() });
		if (fans2 == null) {
			fans2 = new Fans();
			fans2.setUserid(friend.getFriendid());
			fans2.setFansid(friend.getUserid());
			fans2.setFlg(friend.getFlg());
			fans2.setOid(NumberUtil.getLong(this.fansDao.save(null, fans2)));
			// 更新对方粉丝数量
			User friendUser = this.userService.getUser(friend.getFriendid());
			friendUser.setFans_num(friendUser.getFans_num() + 1);
			this.userService.update(friendUser);
		}
		else {
			fans2.setFlg(friend.getFlg());
			this.fansDao.update(null, fans2);
		}
		if (getPhoto) {
			// 获取被关注人的10张图片
			List<User_photo> photolist = this.photoService
					.getUser_photoListByUserid(friend.getFriendid(), false, 0,
							0, 10);
			// 按照photid排序(正序)从小到大
			Collections.sort(photolist, comparator);
			List<Friend_photo_feed> friendPhotoFeeds = new ArrayList<Friend_photo_feed>();
			Friend_photo_feed friendPhotoFeed = null;
			Date date = new Date();
			for (User_photo o : photolist) {
				friendPhotoFeed = new Friend_photo_feed();
				friendPhotoFeed.setCreate_time(date);
				friendPhotoFeed.setPhotoid(o.getPhotoid());
				friendPhotoFeed.setUserid(friend.getUserid());
				friendPhotoFeed.setPhoto_userid(o.getUserid());
				friendPhotoFeeds.add(friendPhotoFeed);
			}
			this.feedService.createFriend_photo_feed(friendPhotoFeeds);
		}
	}

	@Override
	public void deleteFriend(long userid, long friendid, boolean delPhoto) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteFriend(User user, User friendUser, boolean delPhoto) {
		long userid = user.getUserid();
		long friendid = friendUser.getUserid();
		int result = this.fansDao.delete(null, "userid=? and fansid=?",
				new Object[] { friendid, userid });
		if (result > 0) {
			user.setFans_num(user.getFans_num() - 1);
			if (user.getFans_num() < 0) {
				user.setFans_num(0);
			}
			this.userService.update(friendUser);
		}
		this.friendDao.updateBySQL(null, "flg=?", "userid=? and friendid=?",
				new Object[] { Friend.FLG_NOBOTH, friendid, userid });
		this.fansDao.updateBySQL(null, "flg=?", "userid=? and friendid=?",
				new Object[] { Friend.FLG_NOBOTH, userid, friendid });
		result = this.friendDao.delete(null, "userid=? and friendid=?",
				new Object[] { userid, friendid });
		if (result > 0) {
			user.setFriend_num(user.getFriend_num() - 1);
			if (user.getFriend_num() < 0) {
				user.setFriend_num(0);
			}
			this.userService.update(user);
		}
		if (delPhoto) {
			this.feedService.deleteFriend_photo_feedByUseridAndPhoto_userid(
					userid, friendid);
		}
	}

	@Override
	public Fans getFansByUseridAndFansid(long userid, long fansid) {
		return this.fansDao.getObject(null, "userid=? and fansid=?",
				new Object[] { userid, fansid });
	}

	@Override
	public List<Long> getFansidListByUserid(long userid) {
		List<Fans> list = this.fansDao.getList(null, "userid=?",
				new Object[] { userid }, null, 0, 0);
		List<Long> idList = new ArrayList<Long>();
		for (Fans o : list) {
			idList.add(o.getFansid());
		}
		return idList;
	}

	@Override
	public Friend getFriendByUseridAndFriendid(long userid, long friendid) {
		return this.friendDao.getObject(null, "userid=? and friendid=?",
				new Object[] { userid, friendid });
	}

	@Override
	public List<Fans> getFansListByUserid(long userid, boolean buildUser,
			long relationUserid, int begin, int size) {
		List<Fans> list = this.fansDao.getList(null, "userid=?",
				new Object[] { userid }, "oid desc", begin, size);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (Fans o : list) {
				idList.add(o.getFansid());
			}
			Map<Long, User> usermap = this.userService.getUserMapInId(idList);
			for (Fans o : list) {
				o.setFansUser(usermap.get(o.getFansid()));
			}
		}
		if (relationUserid > 0) {
			List<Long> friendidList = this
					.getFriendUseridListByUserid(relationUserid);
			Set<Long> set = new HashSet<Long>();
			set.addAll(friendidList);
			for (Fans o : list) {
				if (set.contains(o.getFansid())) {
					o.setFriendRef(true);
				}
			}
		}
		return list;
	}

	@Override
	public List<Friend> getFriendListByUserid(long userid, boolean buildUser,
			long relationUserid, int begin, int size) {
		List<Friend> list = this.friendDao.getList(null, "userid=?",
				new Object[] { userid }, "oid desc", begin, size);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (Friend o : list) {
				idList.add(o.getFriendid());
			}
			Map<Long, User> usermap = this.userService.getUserMapInId(idList);
			for (Friend o : list) {
				o.setFriendUser(usermap.get(o.getFriendid()));
			}
		}
		if (relationUserid == userid) {
			for (Friend o : list) {
				o.setFriendRef(true);
			}
		}
		else {
			if (relationUserid > 0) {
				List<Long> friendidList = this
						.getFriendUseridListByUserid(relationUserid);
				Set<Long> set = new HashSet<Long>();
				set.addAll(friendidList);
				for (Friend o : list) {
					if (set.contains(o.getFriendid())) {
						o.setFriendRef(true);
					}
				}
			}
		}
		return list;
	}

	@Override
	public List<Long> getFriendUseridListByUserid(long userid) {
		List<Friend> list = this.friendDao.getList(null, "userid=?",
				new Object[] { userid }, null, 0, -1);
		List<Long> idList = new ArrayList<Long>();
		for (Friend o : list) {
			idList.add(o.getFriendid());
		}
		return idList;
	}

	@Override
	public Set<Long> getFriendUseridSetByUserid(long userid) {
		return new HashSet<Long>(this.getFriendUseridListByUserid(userid));
	}
}