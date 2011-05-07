package iwant.dao.impl;

import iwant.bean.User;
import iwant.dao.UserDao;
import cactus.dao.query.BaseDao;

public class UserDaoImpl extends BaseDao<User> implements UserDao {

	@Override
	public Class<User> getClazz() {
		return User.class;
	}

	@Override
	public User getByDevice_tokenAnsNotUserid(String deviceToken, long userid) {
		return this.getObject(null, "device_token=? and userid!=?",
				new Object[] { deviceToken, userid });
	}

	@Override
	public boolean isExistByDevice_token(String deviceToken) {
		if (this.count(null, "device_token=?", new Object[] { deviceToken }) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public User getByDevice_token(String deviceToken) {
		return this.getObject(null, "device_token=?",
				new Object[] { deviceToken });
	}
}