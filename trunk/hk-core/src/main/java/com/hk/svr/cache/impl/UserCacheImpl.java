package com.hk.svr.cache.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hk.bean.User;
import com.hk.frame.dao.query.Cache;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public class UserCacheImpl implements Cache {
	private GeneralCacheAdministrator userCacheMgr;

	private Log log = LogFactory.getLog(UserCacheImpl.class);

	private boolean loginfo;

	public void setLoginfo(boolean loginfo) {
		this.loginfo = loginfo;
	}

	public void setUserCacheMgr(GeneralCacheAdministrator userCacheMgr) {
		this.userCacheMgr = userCacheMgr;
	}

	public void clearAll() {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("cast")
	public Object get(Object key) {
		if (loginfo) {
			log.info("[cache] getUser ...");
		}
		try {
			return (User) userCacheMgr.getFromCache(key.toString());
		}
		catch (NeedsRefreshException e) {
			userCacheMgr.cancelUpdate(key.toString());
			return null;
		}
	}

	public void put(Object key, Object obj) {
		if (obj == null) {
			return;
		}
		if (loginfo) {
			log.info("[cache] putUser ...");
		}
		try {
			this.userCacheMgr.putInCache(key.toString(), obj);
		}
		catch (Exception e) {
			userCacheMgr.cancelUpdate(key.toString());
		}
	}

	public void remove(Object key) {
		if (loginfo) {
			log.info("[cache] removeUser ...");
		}
		this.userCacheMgr.removeEntry(key.toString());
	}
}