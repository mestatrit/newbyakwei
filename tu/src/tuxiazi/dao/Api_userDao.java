package tuxiazi.dao;

import tuxiazi.bean.Api_user;

import com.hk.frame.dao.query2.BaseDao;

public class Api_userDao extends BaseDao<Api_user> {

	@Override
	public Class<Api_user> getClazz() {
		return Api_user.class;
	}
}
