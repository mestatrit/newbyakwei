package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.Fans;

public class FansDao extends BaseDao<Fans> {

	@Override
	public Class<Fans> getClazz() {
		return Fans.class;
	}
}
