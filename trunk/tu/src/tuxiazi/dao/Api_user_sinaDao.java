package tuxiazi.dao;

import tuxiazi.bean.Api_user_sina;

import com.hk.frame.dao.query2.BaseDao;

public class Api_user_sinaDao extends BaseDao<Api_user_sina> {

	@Override
	public Class<Api_user_sina> getClazz() {
		return Api_user_sina.class;
	}
}
