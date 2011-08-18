package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.Userid;

public class UseridDao extends BaseDao<Userid> {

	@Override
	public Class<Userid> getClazz() {
		return Userid.class;
	}
}
