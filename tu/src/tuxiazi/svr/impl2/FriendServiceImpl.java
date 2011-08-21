package tuxiazi.svr.impl2;

import halo.util.NumberUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

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

public class FriendServiceImpl implements FriendService {

	private UserService userService;

	private NoticeService noticeService;

	private PhotoService photoService;

	private FeedService feedService;

	@Autowired
	private FriendDao friendDao;

	@Autowired
	private FansDao fansDao;

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
		if (this.friendDao.getByUseridAndFriendid(friend.getUserid(),
				friend.getFriendid()) != null) {
			return;
		}
		// 查看对方是否已经加用户为好友
		Friend friend2 = this.friendDao.getByUseridAndFriendid(
				friend.getFriendid(), friend.getUserid());
		// 如果已经加用户为好友
		if (friend2 != null) {
			friend2.setFlg(Friend.FLG_BOTH);
			friend.setFlg(Friend.FLG_BOTH);
			friend2.update();
		}
		else {
			friend.setFlg(Friend.FLG_NOBOTH);
		}
		// 创建好友关系
		friend.save();
		User user = this.userService.getUser(friend.getUserid());
		if (sendNotice) {
			// 发送follow的通知到对方
			FollowNoticeCreater followNoticeCreater = new FollowNoticeCreater();
			followNoticeCreater.setUserid(friend.getFriendid());
			followNoticeCreater.setSenderid(friend.getUserid());
			this.noticeService.createNotice(followNoticeCreater.buildNotice());
		}
		// 更新好友数量
		user.setFriend_num(user.getFriend_num() + 1);
		user.update();
		// 创建对方粉丝数据
		Fans fans2 = this.fansDao.getByUseridAndFansid(friend.getFriendid(),
				friend.getUserid());
		if (fans2 == null) {
			fans2 = new Fans();
			fans2.setUserid(friend.getFriendid());
			fans2.setFansid(friend.getUserid());
			fans2.setFlg(friend.getFlg());
			fans2.setOid(NumberUtil.getLong(this.fansDao.save(fans2)));
			// 更新对方粉丝数量
			User friendUser = this.userService.getUser(friend.getFriendid());
			friendUser.setFans_num(friendUser.getFans_num() + 1);
			this.userService.update(friendUser);
		}
		else {
			fans2.setFlg(friend.getFlg());
			this.fansDao.update(fans2);
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
	public void deleteFriend(User user, User friendUser, boolean delPhoto) {
		long userid = user.getUserid();
		long friendid = friendUser.getUserid();
		// 从自己的好友列表中删除好友
		this.friendDao.deleteByUseridAndFriendid(userid, friendid);
		// 从对方的粉丝列表中删除粉丝
		this.fansDao.deleteByUseridAndFansid(friendid, userid);
		// 统计自己的好友数量
		int friend_num = this.friendDao.countByUserid(userid);
		// 统计对方的粉丝数量
		int fans_num = this.fansDao.countByUserid(friendid);
		user.setFriend_num(friend_num);
		this.userService.update(user);
		friendUser.setFans_num(fans_num);
		this.userService.update(friendUser);
		// 如果对方加我为好友，则更新取消相互关注的标志
		Friend friend = this.friendDao.getByUseridAndFriendid(friendid, userid);
		if (friend != null) {
			friend.setFlg(Friend.FLG_NOBOTH);
		}
		Fans fans = this.fansDao.getByUseridAndFansid(userid, friendid);
		if (fans != null) {
			fans.setFlg(Friend.FLG_NOBOTH);
		}
		this.friendDao.update(friend);
		this.fansDao.update(fans);
		if (delPhoto) {
			this.feedService.deleteFriend_photo_feedByUseridAndPhoto_userid(
					userid, friendid);
		}
	}

	@Override
	public Fans getFansByUseridAndFansid(long userid, long fansid) {
		return this.fansDao.getByUseridAndFansid(userid, fansid);
	}

	@Override
	public List<Long> getFansidListByUserid(long userid) {
		return this.fansDao.getFansidListByUserid(userid);
	}

	@Override
	public Friend getFriendByUseridAndFriendid(long userid, long friendid) {
		return this.friendDao.getByUseridAndFriendid(userid, friendid);
	}

	@Override
	public List<Fans> getFansListByUserid(long userid, boolean buildUser,
			long relationUserid, int begin, int size) {
		List<Fans> list = this.fansDao.getListByUserid(userid, begin, size);
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
		List<Friend> list = this.friendDao.getListByUserid(userid, begin, size);
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
		return this.friendDao.getFriendidListByUserid(userid);
	}

	@Override
	public Set<Long> getFriendUseridSetByUserid(long userid) {
		return new HashSet<Long>(this.getFriendUseridListByUserid(userid));
	}
}