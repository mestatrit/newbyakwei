package tuxiazi.dao;

import halo.dao.query.IDao;

import java.util.List;

import tuxiazi.bean.Friend;

public interface FriendDao extends IDao<Friend> {

	public Friend getByUseridAndFriendid(long userid, long friendid);

	public int deleteByUseridAndFriendid(long userid, long friendid);

	public int countByUserid(long userid);

	public List<Friend> getListByUserid(long userid, int begin, int size);

	public List<Long> getFriendidListByUserid(long userid);

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
	List<Friend> getListByUserid(long userid, boolean buildUser,
			long relationUserid, int begin, int size);
}
