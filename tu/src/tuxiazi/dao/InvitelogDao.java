package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.Invitelog;

public class InvitelogDao extends BaseDao<Invitelog> {

	@Override
	public Class<Invitelog> getClazz() {
		return Invitelog.class;
	}
}
