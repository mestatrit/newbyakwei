package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.User;

public class UserDao extends BaseDao<User> {

	@Override
	public Class<User> getClazz() {
		return User.class;
	}

	public int addPi_num(Object keyValue, long userid, int add) {
		return this.updateBySQL(keyValue, "pic_num=pic_num+?", "userid=?",
				new Object[] { userid, add });
	}
}