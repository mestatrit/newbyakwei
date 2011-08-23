package tuxiazi.dao;

import halo.dao.query.IDao;

import java.util.List;

import tuxiazi.bean.User_photo;

public interface User_photoDao extends IDao<User_photo> {

	public int deleteByUseridAndPhotoid(long userid, long photoid);

	public int countByUserid(long userid);

	public List<User_photo> getListByUserid(long userid, int begin, int size);

	/**
	 * @param userid
	 * @param buildPhoto
	 * @param favUserid
	 *            查看该用户是否有收藏当前集合的图片
	 * @param begin
	 * @param size
	 * @return 2010-11-13
	 */
	List<User_photo> getListByUserid(long userid, boolean buildPhoto,
			long favUserid, int begin, int size);

	User_photo getByUseridAndPhotoid(long userid, long photoid);
}
