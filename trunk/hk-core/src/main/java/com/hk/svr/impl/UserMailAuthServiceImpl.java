package com.hk.svr.impl;

import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.UserMailAuth;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.MD5Util;
import com.hk.svr.UserMailAuthService;

public class UserMailAuthServiceImpl implements UserMailAuthService {
	@Autowired
	private QueryManager manager;

	public UserMailAuth createUserMailAuth(long userId) {
		this.deleteUserMailAuth(userId);
		UserMailAuth o = new UserMailAuth();
		o.setUserId(userId);
		String code = userId + "" + System.currentTimeMillis() + ""
				+ Math.random();
		String md5 = MD5Util.md5Encode32(code);
		o.setAuthcode(md5);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		o.setOverTime(cal.getTime());
		Query query = this.manager.createQuery();
		query.addField("userid", userId);
		query.addField("authcode", o.getAuthcode());
		query.addField("overtime", o.getOverTime());
		query.insert(UserMailAuth.class);
		return o;
	}

	public void deleteUserMailAuth(long userId) {
		Query query = this.manager.createQuery();
		query.deleteById(UserMailAuth.class, userId);
	}

	public UserMailAuth getUserMailAuth(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserMailAuth.class, userId);
	}

	public UserMailAuth getUserMailAuthByAuthcode(String authcode) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(UserMailAuth.class, "authcode=?",
				new Object[] { authcode });
	}
}