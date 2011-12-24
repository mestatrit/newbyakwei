package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpContact;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpContactService;

public class CmpContactServiceImpl implements CmpContactService {

	@Autowired
	private QueryManager manager;

	public void createCmpContact(CmpContact cmpContact) {
		Query query = this.manager.createQuery();
		long oid = query.insertObject(cmpContact).longValue();
		cmpContact.setOid(oid);
	}

	public void deleteCmpContact(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpContact.class, oid);
	}

	public CmpContact getCmpContact(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpContact.class, oid);
	}

	public List<CmpContact> getCmpContactListByCompanyId(long companyId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpContact.class, "companyid=?",
				new Object[] { companyId }, "oid desc");
	}

	public void updateCmpContact(CmpContact cmpContact) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpContact);
	}
}