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
}
