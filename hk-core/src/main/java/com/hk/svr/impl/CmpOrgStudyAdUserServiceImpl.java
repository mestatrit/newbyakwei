package com.hk.svr.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpOrgStudyAdUser;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpOrgStudyAdUserService;

public class CmpOrgStudyAdUserServiceImpl implements CmpOrgStudyAdUserService {

	@Autowired
	private QueryManager manager;

	public void createCmpOrgStudyAdUser(CmpOrgStudyAdUser cmpOrgStudyAdUser) {
		Query query = this.manager.createQuery();
		if (cmpOrgStudyAdUser.getCreateTime() == null) {
			cmpOrgStudyAdUser.setCreateTime(new Date());
		}
		long oid = query.insertObject(cmpOrgStudyAdUser).longValue();
		cmpOrgStudyAdUser.setOid(oid);
	}

	public void deleteCmpOrgStudyAdUser(long companyId, long oid) {
		Query query = this.manager.createQuery();
		query.delete(CmpOrgStudyAdUser.class, "companyid=? and oid=?",
				new Object[] { companyId, oid });
	}

	public CmpOrgStudyAdUser getCmpOrgStudyAdUser(long companyId, long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpOrgStudyAdUser.class,
				"companyid=? and oid=?", new Object[] { companyId, oid });
	}

	public List<CmpOrgStudyAdUser> getCmpOrgStudyAdUserListByCompanyIdAndOrgId(
			long companyId, long orgId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpOrgStudyAdUser.class, "companyid=? and orgid=?",
				new Object[] { companyId, orgId }, "oid asc", begin, size);
	}

	public List<CmpOrgStudyAdUser> getCmpOrgStudyAdUserListByCompanyIdAndOrgIdAndAdid(
			long companyId, long orgId, long adid, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpOrgStudyAdUser.class,
				"companyid=? and orgid=? and adid=?", new Object[] { companyId,
						orgId, adid }, "oid asc", begin, size);
	}

	public int countCmpOrgStudyAdUserByCompanyidAndUserId(long companyId,
			long userId) {
		Query query = manager.createQuery();
		return query.count(CmpOrgStudyAdUser.class, "companyid=? and userid=?",
				new Object[] { companyId, userId });
	}

	public List<CmpOrgStudyAdUser> getCmpOrgStudyAdUserListByCompanyidAndUserId(
			long companyId, long userId, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpOrgStudyAdUser.class,
				"companyid=? and userid=?", new Object[] { companyId, userId },
				"oid desc", begin, size);
	}

	public List<CmpOrgStudyAdUser> getCmpOrgStudyAdUserListByCompanyidAndAdidAndUserId(
			long companyId, long adid, long userId, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpOrgStudyAdUser.class,
				"companyid=? and adid=? and userid=?", new Object[] {
						companyId, adid, userId }, "oid desc", begin, size);
	}

	public void updateCmpOrgStudyAdUser(CmpOrgStudyAdUser cmpOrgStudyAdUser) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpOrgStudyAdUser);
	}
}