package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import tuxiazi.bean.Api_user;
import tuxiazi.dao.Api_userDao;

public class Api_userDaoImpl extends BaseDao<Api_user> implements Api_userDao {

	@Override
	public Class<Api_user> getClazz() {
		return Api_user.class;
	}

	public Api_user getByUseridAndApi_type(long userid, int apiType) {
		return this.getObject("userid=? and api_type=?", new Object[] { userid,
				apiType });
	}
}
