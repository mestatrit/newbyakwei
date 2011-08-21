package tuxiazi.dao;

import halo.dao.query.IDao;
import tuxiazi.bean.Api_user_sina;

public interface Api_user_sinaDao extends IDao<Api_user_sina> {

	Api_user_sina getByUserid(long userid);
}
