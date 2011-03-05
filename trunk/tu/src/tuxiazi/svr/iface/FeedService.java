package tuxiazi.svr.iface;

import java.util.List;

import tuxiazi.bean.Friend_photo_feed;

public interface FeedService {

	void createFriend_photo_feed(List<Friend_photo_feed> friendPhotoFeeds);

	void deleteFriend_photo_feedByUseridAndPhoto_userid(long userid,
			long photo_userid);

	/**
	 * 获得用户关注的人的图片动态
	 * 
	 * @param userid
	 * @param buildPhoto true:组装图片
	 * @param buildPhotoUser true:组装图片中的user对象
	 * @param favUserid 查看此用户是否有收藏此集合的图片
	 * @param begin
	 * @param size
	 * @return
	 *         2010-11-27
	 */
	List<Friend_photo_feed> getFriend_photo_feedListByUserid(long userid,
			boolean buildPhoto, boolean buildPhotoUser, long favUserid,
			int begin, int size);

	void deleteFriend_photo_feedByPhotoid(long photoid);
}