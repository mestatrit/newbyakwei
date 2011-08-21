package tuxiazi.svr.iface;

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
	 * @param user
	 * @param friendUser
	 * @param delPhoto
	 *            是否把此人的图片从关注图片中移除 true:移除
	 */
	void deleteFriend(User user, User friendUser, boolean delPhoto);
}