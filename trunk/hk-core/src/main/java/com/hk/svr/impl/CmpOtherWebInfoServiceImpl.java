package com.hk.svr.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpOtherWebInfo;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpOtherWebInfoService;

public class CmpOtherWebInfoServiceImpl implements CmpOtherWebInfoService {

	@Autowired
	private QueryManager manager;

	public void createCmpOtherWebInfo(CmpOtherWebInfo cmpOtherWebInfo) {
		Query query = manager.createQuery();
		query.insertObject(cmpOtherWebInfo);
	}

	public CmpOtherWebInfo getCmpOtherWebInfo(long companyId) {
		return manager.createQuery().getObjectById(CmpOtherWebInfo.class,
				companyId);
	}

	public void updateCmpOtherWebInfo(CmpOtherWebInfo cmpOtherWebInfo) {
		manager.createQuery().updateObject(cmpOtherWebInfo);
	}
}