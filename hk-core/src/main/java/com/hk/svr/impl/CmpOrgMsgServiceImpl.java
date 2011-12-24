package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpOrgMsg;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpOrgMsgService;

public class CmpOrgMsgServiceImpl implements CmpOrgMsgService {

	@Autowired
	private QueryManager manager;

	public void createCmpOrgMsg(CmpOrgMsg cmpOrgMsg) {
		Query query = this.manager.createQuery();
		long oid = query.insertObject(cmpOrgMsg).longValue();
		cmpOrgMsg.setOid(oid);
	}

	public void deleteCmpOrgMsg(long companyId, long oid) {
		Query query = this.manager.createQuery();
		query.delete(CmpOrgMsg.class, "companyid=? and oid=?", new Object[] {
				companyId, oid });
	}

	public CmpOrgMsg getCmpOrgMsg(long companyId, long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpOrgMsg.class, "companyid=? and oid=?",
				new Object[] { companyId, oid });
	}

	public int countCmpOrgMsgByCompanyIdAndOrgId(long companyId, long orgId) {
		Query query = this.manager.createQuery();
		return query.count(CmpOrgMsg.class, "companyid=? and orgid=?",
				new Object[] { companyId, orgId });
	}

	public List<CmpOrgMsg> getCmpOrgMsgListByCompanyIdAndOrgId(long companyId,
			long orgId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpOrgMsg.class, "companyid=? and orgid=?",
				new Object[] { companyId, orgId }, "oid desc", begin, size);
	}
}