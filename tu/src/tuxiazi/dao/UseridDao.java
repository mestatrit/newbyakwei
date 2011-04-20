package tuxiazi.dao;

import tuxiazi.bean.Userid;

import com.hk.frame.dao.query2.BaseDao;

public class UseridDao extends BaseDao<Userid> {

	@Override
	public Class<Userid> getClazz() {
		return Userid.class;
	}
}
