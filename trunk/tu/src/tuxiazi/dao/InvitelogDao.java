package tuxiazi.dao;

import tuxiazi.bean.Invitelog;

import com.hk.frame.dao.query2.BaseDao;

public class InvitelogDao extends BaseDao<Invitelog> {

	@Override
	public Class<Invitelog> getClazz() {
		return Invitelog.class;
	}
}
