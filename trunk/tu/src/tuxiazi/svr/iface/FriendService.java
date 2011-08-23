package tuxiazi.svr.iface;

import tuxiazi.bean.User;

public interface FriendService {

	boolean createFriend(User user, User friendUser, boolean sendNotice,
			boolean getPhoto);

	/**
	 * @param user
	 * @param friendUser
	 * @param delPhoto
	 *            是否把此人的图片从关注图片中移除 true:移除
	 */
	void deleteFriend(User user, User friendUser, boolean delPhoto);
}