package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;

import org.springframework.stereotype.Component;

import tuxiazi.bean.Invitelog;
import tuxiazi.dao.InvitelogDao;

@Component("invitelogDao")
public class InvitelogDaoImpl extends BaseDao<Invitelog> implements
		InvitelogDao {

	@Override
	public Class<Invitelog> getClazz() {
		return Invitelog.class;
	}
}
