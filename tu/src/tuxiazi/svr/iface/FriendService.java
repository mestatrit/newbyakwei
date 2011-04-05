package tuxiazi.svr.iface;

import java.util.List;
import java.util.Set;

import tuxiazi.bean.Fans;
import tuxiazi.bean.Friend;
import tuxiazi.bean.User;

public interface FriendService {

	/**
	 * 添加关注
	 * 
	 * @param friend
	 * @param sendNotice
	 *            true:向对方发送关注的通知,false:不发送通知
	 * @param getPhoto
	 *            true:获取关注人的图片
	 */
	void createFriend(Friend friend, boolean sendNotice, boolean getPhoto);

	/**
	 * @param userid
	 * @param friendid
	 * @param delPhoto
	 *            是否把此人的图片从关注图片中移除 true:移除
	 */
	void deleteFriend(long userid, long friendid, boolean delPhoto);

	/**
	 * @param user
	 * @param friendUser
	 * @param delPhoto
	 *            是否把此人的图片从关注图片中移除 true:移除
	 */
	void deleteFriend(User user, User friendUser, boolean delPhoto);

	Friend getFriendByUseridAndFriendid(long userid, long friendid);

	Fans getFansByUseridAndFansid(long userid, long fansid);

	List<Long> getFansidListByUserid(long userid);

	List<Long> getFriendUseridListByUserid(long userid);

	Set<Long> getFriendUseridSetByUserid(long userid);

	/**
	 * @param userid
	 * @param buildUser
	 *            true:组装friend中的friendUser对象
	 * @param relationUserid
	 *            如果需要获得关注关系，就赋值为当前访问用户的userid
	 * @param begin
	 * @param size
	 * @return 2010-12-5
	 */
	List<Friend> getFriendListByUserid(long userid, boolean buildUser,
			long relationUserid, int begin, int size);

	/**
	 * @param userid
	 * @param buildUser
	 *            true:组装fans中的fansUser对象
	 * @param relationUserid
	 *            如果需要获得关注关系，就赋值为当前访问用户的userid
	 * @param begin
	 * @param size
	 * @return 2010-12-5
	 */
	List<Fans> getFansListByUserid(long userid, boolean buildUser,
			long relationUserid, int begin, int size);
}