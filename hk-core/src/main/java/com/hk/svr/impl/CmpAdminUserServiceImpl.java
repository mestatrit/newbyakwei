package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpAdminUser;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpAdminUserService;

public class CmpAdminUserServiceImpl implements CmpAdminUserService {

	@Autowired
	private QueryManager manager;

	public boolean createCmpAdminUser(CmpAdminUser cmpAdminUser) {
		Query query = this.manager.createQuery();
		if (query.count(CmpAdminUser.class, "companyid=? and userid=?",
				new Object[] { cmpAdminUser.getCompanyId(),
						cmpAdminUser.getUserId() }) > 0) {
			return false;
		}
		cmpAdminUser.setOid(query.insertObject(cmpAdminUser).longValue());
		return true;
	}

	public void deleteCmpAdminUser(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpAdminUser.class, oid);
	}

	public CmpAdminUser getCmpAdminUser(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpAdminUser.class, oid);
	}

	public List<CmpAdminUser> getCmpAdminUserByCompanyId(long companyId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpAdminUser.class, "companyid=?",
				new Object[] { companyId }, "oid desc");
	}

	public CmpAdminUser getCmpAdminUserByCompanyIdAndUserId(long companyId,
			long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpAdminUser.class,
				"companyid=? and userid=?", new Object[] { companyId, userId });
	}
}