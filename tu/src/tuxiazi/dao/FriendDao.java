package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.Friend;

public class FriendDao extends BaseDao<Friend> {

	@Override
	public Class<Friend> getClazz() {
		return Friend.class;
	}
}
