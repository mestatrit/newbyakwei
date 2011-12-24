package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpRefUser;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpRefUserService;

public class CmpRefUserServiceImpl implements CmpRefUserService {

	@Autowired
	private QueryManager manager;

	public void createCmpRefUser(CmpRefUser cmpRefUser) {
		Query query = this.manager.createQuery();
		if (query.count(CmpRefUser.class, "companyid=? and userid=?",
				new Object[] { cmpRefUser.getCompanyId(),
						cmpRefUser.getUserId() }) == 0) {
			long oid = query.insertObject(cmpRefUser).longValue();
			cmpRefUser.setOid(oid);
		}
	}

	public void deleteCmpRefUserByCompanyIdAndUserId(long companyId, long userId) {
		Query query = this.manager.createQuery();
		query.delete(CmpRefUser.class, "companyid=? and userid=?",
				new Object[] { companyId, userId });
	}

	public List<CmpRefUser> getCmpRefUserListByCompanyId(long companyId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpRefUser.class, "companyid=?",
				new Object[] { companyId }, "oid desc", begin, size);
	}

	public CmpRefUser getCmpRefUserByCompanyIdAndUserId(long companyId,
			long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpRefUser.class, "companyid=? and userid=?",
				new Object[] { companyId, userId });
	}
}