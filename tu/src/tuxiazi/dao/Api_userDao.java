package tuxiazi.dao;

import halo.dao.query.IDao;
import tuxiazi.bean.Api_user;

public interface Api_userDao extends IDao<Api_user> {

	public Api_user getByUseridAndApi_type(long userid, int apiType);
}
