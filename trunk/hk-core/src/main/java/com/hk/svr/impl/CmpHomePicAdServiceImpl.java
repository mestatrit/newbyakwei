package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpHomePicAd;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpHomePicAdService;

public class CmpHomePicAdServiceImpl implements CmpHomePicAdService {

	@Autowired
	private QueryManager manager;

	public void createCmpHomePicAd(CmpHomePicAd cmpHomePicAd) {
		Query query = this.manager.createQuery();
		cmpHomePicAd.setAdid(query.insertObject(cmpHomePicAd).longValue());
	}

	public void deleteCmpHomePicAd(long adid) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpHomePicAd.class, adid);
	}

	public List<CmpHomePicAd> getCmpHomePicAdListByCompanyId(long companyId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpHomePicAd.class, "companyid=?",
				new Object[] { companyId }, "adid asc", begin, size);
	}

	public void updateCmpHomePicAd(CmpHomePicAd cmpHomePicAd) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpHomePicAd);
	}

	public CmpHomePicAd getCmpHomePicAd(long adid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpHomePicAd.class, adid);
	}
}