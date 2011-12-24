package com.hk.svr.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.ApiUser;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DesUtil;
import com.hk.frame.util.MD5Util;
import com.hk.svr.ApiUserService;

public class ApiUserServiceImpl implements ApiUserService {

	@Autowired
	private QueryManager manager;

	private DesUtil desUtil = new DesUtil("ak47flyshowhuoku");

	public synchronized ApiUser createApiUser(ApiUser apiUser) {
		ApiUser o = this.getApiUserByName(apiUser.getName());
		if (o != null) {
			return o;
		}
		String key = apiUser.getUserId() + "" + System.currentTimeMillis()
				+ Math.random();
		try {
			key = this.desUtil.encrypt(key);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		apiUser.setUserKey(MD5Util.md5Encode32(key));
		Query query = manager.createQuery();
		query.addField("userkey", apiUser.getUserKey());
		query.addField("name", apiUser.getName());
		query.addField("lockflg", apiUser.getLockflg());
		query.addField("createtime", new Date());
		query.addField("url", apiUser.getUrl());
		query.addField("userid", apiUser.getUserId());
		query.addField("checkflg", apiUser.getCheckflg());
		apiUser.setApiUserId(query.insert(ApiUser.class).intValue());
		return apiUser;
	}

	public void delete(int apiUserId) {
		Query query = manager.createQuery();
		query.deleteById(ApiUser.class, apiUserId);
	}

	public ApiUser getApiUserByUserKey(String userKey) {
		Query query = manager.createQuery();
		return query.getObjectEx(ApiUser.class, "userkey=?",
				new Object[] { userKey });
	}

	private ApiUser getApiUserByName(String name) {
		Query query = manager.createQuery();
		return query
				.getObjectEx(ApiUser.class, "name=?", new Object[] { name });
	}

	public void updateApiUser(ApiUser apiUser) {
		// TODO Auto-generated method stub
	}

	public ApiUser getApiUser(long apiUserId) {
		Query query = manager.createQuery();
		return query.getObjectById(ApiUser.class, apiUserId);
	}
}