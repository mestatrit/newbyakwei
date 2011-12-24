package com.hk.svr.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpSvrCnf;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpSvrCnfService;

public class CmpSvrCnfServiceImpl implements CmpSvrCnfService {

	@Autowired
	private QueryManager manager;

	public void createCmpSvrCnf(CmpSvrCnf cmpSvrCnf) {
		Query query = manager.createQuery();
		query.insertObject(cmpSvrCnf);
	}

	public CmpSvrCnf getCmpSvrCnf(long companyId) {
		return manager.createQuery().getObjectById(CmpSvrCnf.class, companyId);
	}

	public void updateCmpSvrCnf(CmpSvrCnf cmpSvrCnf) {
		Query query = manager.createQuery();
		query.updateObject(cmpSvrCnf);
	}
}