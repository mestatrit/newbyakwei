package com.hk.svr.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.UserCmpFunc;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.UserCmpFuncService;

public class UserCmpFuncServiceImpl implements UserCmpFuncService {

	@Autowired
	private QueryManager manager;

	public UserCmpFunc getUserCmpFunc(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserCmpFunc.class, userId);
	}

	public void saveUserCmpFunc(UserCmpFunc userCmpFunc) {
		UserCmpFunc o = this.getUserCmpFunc(userCmpFunc.getUserId());
		Query query = this.manager.createQuery();
		if (o == null) {
			query.insertObject(userCmpFunc);
		}
		else {
			query.updateObject(userCmpFunc);
		}
	}
}