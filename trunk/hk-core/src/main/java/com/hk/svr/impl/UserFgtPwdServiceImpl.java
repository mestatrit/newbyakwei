package com.hk.svr.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.UserFgtSms;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;

public class UserFgtPwdServiceImpl implements com.hk.svr.UserFgtPwdService {

	@Autowired
	private QueryManager manager;

	public void createUserFgtSms(UserFgtSms userFgtSms) {
		UserFgtSms o = this.getUserFgtSms(userFgtSms.getUserId());
		Query query = this.manager.createQuery();
		if (o != null) {
			query.updateObject(userFgtSms);
		}
		else {
			query.insertObject(userFgtSms);
		}
	}

	public void deleteUserFgtSms(long userId) {
		Query query = this.manager.createQuery();
		query.deleteById(UserFgtSms.class, userId);
	}

	public UserFgtSms getUserFgtSms(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserFgtSms.class, userId);
	}
}