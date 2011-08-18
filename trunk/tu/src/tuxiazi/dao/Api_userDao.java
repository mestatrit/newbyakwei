package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.Api_user;

public class Api_userDao extends BaseDao<Api_user> {

	@Override
	public Class<Api_user> getClazz() {
		return Api_user.class;
	}
}
