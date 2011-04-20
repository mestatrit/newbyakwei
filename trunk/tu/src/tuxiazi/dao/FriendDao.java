package tuxiazi.dao;

import tuxiazi.bean.Friend;

import com.hk.frame.dao.query2.BaseDao;

public class FriendDao extends BaseDao<Friend> {

	@Override
	public Class<Friend> getClazz() {
		return Friend.class;
	}
}
