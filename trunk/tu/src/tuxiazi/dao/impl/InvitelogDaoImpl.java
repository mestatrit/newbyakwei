package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import tuxiazi.bean.Invitelog;
import tuxiazi.dao.InvitelogDao;

public class InvitelogDaoImpl extends BaseDao<Invitelog> implements
		InvitelogDao {

	@Override
	public Class<Invitelog> getClazz() {
		return Invitelog.class;
	}
}
