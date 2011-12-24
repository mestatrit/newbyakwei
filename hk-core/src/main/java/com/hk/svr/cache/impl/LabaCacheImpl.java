package com.hk.svr.cache.impl;

import com.hk.bean.Laba;
import com.hk.bean.UserRecentLaba;
import com.hk.svr.cache.LabaCache;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public class LabaCacheImpl implements LabaCache {
	private GeneralCacheAdministrator labaCacheMgr;

	private GeneralCacheAdministrator userRecentLabaCacheMgr;

	public void setUserRecentLabaCacheMgr(
			GeneralCacheAdministrator userRecentLabaCacheMgr) {
		this.userRecentLabaCacheMgr = userRecentLabaCacheMgr;
	}

	public void setLabaCacheMgr(GeneralCacheAdministrator labaCacheMgr) {
		this.labaCacheMgr = labaCacheMgr;
	}

	public Laba getLaba(long labaId) {
		try {
			Laba laba = (Laba) labaCacheMgr.getFromCache(labaId + "");
			if (laba == null) {
				return null;
			}
			return laba;
		}
		catch (NeedsRefreshException e) {
			this.labaCacheMgr.cancelUpdate(labaId + "");
			return null;
		}
	}

	public void putLaba(Laba laba) {
		if (laba == null) {
			return;
		}
		try {
			labaCacheMgr.putInCache(laba.getLabaId() + "", laba);
		}
		catch (Exception e) {
			this.labaCacheMgr.cancelUpdate(laba.getLabaId() + "");
		}
	}

	public void removeLaba(long labaId) {
		labaCacheMgr.removeEntry(labaId + "");
	}

	public UserRecentLaba getUserRecentLaba(long userId) {
		try {
			return (UserRecentLaba) userRecentLabaCacheMgr.getFromCache(userId
					+ "");
		}
		catch (Exception e) {
			this.userRecentLabaCacheMgr.cancelUpdate(userId + "");
			return null;
		}
	}

	public void removeUserRecentLaba(long userId) {
		userRecentLabaCacheMgr.removeEntry(userId + "");
	}
}