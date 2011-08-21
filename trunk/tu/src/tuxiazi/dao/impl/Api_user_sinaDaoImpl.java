package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import tuxiazi.bean.Api_user_sina;
import tuxiazi.dao.Api_user_sinaDao;

public class Api_user_sinaDaoImpl extends BaseDao<Api_user_sina> implements
		Api_user_sinaDao {

	@Override
	public Class<Api_user_sina> getClazz() {
		return Api_user_sina.class;
	}

	public Api_user_sina getByUserid(long userid) {
		return this.getObject("userid=?", new Object[] { userid });
	}
}
