package tuxiazi.dao;

import tuxiazi.bean.User;

import com.hk.frame.dao.query2.BaseDao;

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