package tuxiazi.svr.impl2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Fans;
import tuxiazi.bean.Friend;
import tuxiazi.bean.Friend_photo_feed;
import tuxiazi.bean.Notice;
import tuxiazi.bean.User;
import tuxiazi.bean.User_photo;
import tuxiazi.bean.benum.NoticeEnum;
import tuxiazi.bean.benum.NoticeReadEnum;
import tuxiazi.dao.FansDao;
import tuxiazi.dao.FriendDao;
import tuxiazi.dao.User_photoDao;
import tuxiazi.svr.iface.FeedService;
import tuxiazi.svr.iface.FriendService;

@Component("friendService")
public class FriendServiceImpl implements FriendService {

	@Autowired
	private FeedService feedService;

	@Autowired
	private FriendDao friendDao;

	@Autowired
	private FansDao fansDao;

	@Autowired
	private User_photoDao user_photoDao;

	private Comparator<User_photo> comparator = new Comparator<User_photo>() {

		@Override
		public int compare(User_photo o1, User_photo o2) {
			if (o1.getPhotoid() > o2.getPhotoid()) {
				return 1;
			}
			return -1;
		}
	};

	public boolean createFriend(User user, User friendUser, boolean sendNotice,
			boolean getPhoto) {
		if (user.getUserid() == friendUser.getUserid()) {
			throw new RuntimeException(
					"addFriend must be not only one user userid:friendUserid ["
							+ user.getUserid() + ":" + friendUser.getUserid()
							+ "]");
		}
		Friend friend = new Friend();
		Friend friend2 = this.friendDao.getByUseridAndFriendid(
				friendUser.getUserid(), user.getUserid());
		boolean both = false;
		if (friend2 != null) {
			both = true;
		}
		if (friend.saveFriend(user, friendUser, both)) {
			// 创建好友成功，添加粉丝
			Fans fans = new Fans();
			fans.saveFans(friendUser, user, both);
			// 如果对方也加user为好友，那么就为相互好友，更新这2个人的好友数据为相互关注，粉丝数据为相互关注
			if (both && friend2 != null) {
				friend2.setFlg(Friend.FLG_BOTH);
				friend2.update();
				Fans fans2 = this.fansDao.getByUseridAndFansid(
						user.getUserid(), friendUser.getUserid());
				if (fans2 == null) {
					fans2 = new Fans();
					fans2.saveFans(user, friendUser, both);
				}
				else {
					fans2.setFlg(Friend.FLG_BOTH);
					fans2.update();
				}
			}
			// 更新每个人的好友数量与粉丝数量
			user.setFriend_num(this.friendDao.countByUserid(user.getUserid()));
			friendUser.setFans_num(this.fansDao.countByUserid(friendUser
					.getUserid()));
			if (both) {
				user.setFans_num(this.fansDao.countByUserid(user.getUserid()));
				friendUser.setFriend_num(this.friendDao
						.countByUserid(friendUser.getUserid()));
			}
			user.update();
			friendUser.update();
			if (sendNotice) {
				// 发送follow的通知到对方
				Notice notice = new Notice();
				notice.setUserid(friend.getFriendid());
				notice.setCreatetime(new Date());
				notice.setNotice_flg(NoticeEnum.ADD_FOLLOW.getValue());
				notice.setReadflg(NoticeReadEnum.UNREAD.getValue());
				notice.setRefoid(notice.getUserid());
				notice.setSenderid(friend.getUserid());
				notice.setData("");
				notice.save();
			}
			if (getPhoto) {
				// 获取被关注人的10张图片
				List<User_photo> photolist = this.user_photoDao
						.getListByUserid(friend.getFriendid(), false, 0, 0, 10);
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
			return true;
		}
		return false;
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
		user.update();
		friendUser.setFans_num(fans_num);
		friendUser.update();
		// 如果对方加我为好友，则更新取消相互关注的标志
		Friend friend = this.friendDao.getByUseridAndFriendid(friendid, userid);
		if (friend != null) {
			friend.setFlg(Friend.FLG_NOBOTH);
			friend.update();
		}
		Fans fans = this.fansDao.getByUseridAndFansid(userid, friendid);
		if (fans != null) {
			fans.setFlg(Friend.FLG_NOBOTH);
			fans.update();
		}
		if (delPhoto) {
			this.feedService.deleteFriend_photo_feedByUseridAndPhoto_userid(
					userid, friendid);
		}
	}
}