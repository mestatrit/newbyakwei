package tuxiazi.dao;

import halo.dao.query.IDao;

import java.util.List;

import tuxiazi.bean.Friend_photo_feed;

public interface Friend_photo_feedDao extends IDao<Friend_photo_feed> {

	public int countByUserid(long userid);

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
	public List<Friend_photo_feed> getListByUserid(long userid,
			boolean buildPhoto, boolean buildPhotoUser, long favUserid,
			int begin, int size);

	public void deleteByPhotoid(long photoid);

	public void deleteByUseridAndPhotoUserid(long userid, long photoUserid);
}
