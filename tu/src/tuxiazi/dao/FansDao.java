package tuxiazi.dao;

import tuxiazi.bean.Fans;

import com.hk.frame.dao.query2.BaseDao;

public class FansDao extends BaseDao<Fans> {

	@Override
	public Class<Fans> getClazz() {
		return Fans.class;
	}
}
