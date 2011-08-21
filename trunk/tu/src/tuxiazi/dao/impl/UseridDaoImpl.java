package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;

import org.springframework.stereotype.Component;

import tuxiazi.bean.Userid;
import tuxiazi.dao.UseridDao;

@Component("useridDao")
public class UseridDaoImpl extends BaseDao<Userid> implements UseridDao {

	@Override
	public Class<Userid> getClazz() {
		return Userid.class;
	}
}
