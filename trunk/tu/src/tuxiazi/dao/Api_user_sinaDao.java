package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.Api_user_sina;

public class Api_user_sinaDao extends BaseDao<Api_user_sina> {

	@Override
	public Class<Api_user_sina> getClazz() {
		return Api_user_sina.class;
	}
}
