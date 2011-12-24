package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpVideo;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpVideoService;

public class CmpVideoServiceImpl implements CmpVideoService {

	@Autowired
	private QueryManager manager;

	public void createCmpVideo(CmpVideo cmpVideo) {
		Query query = this.manager.createQuery();
		query.insertObject(cmpVideo);
	}

	public void deleteCmpVideo(long oid) {
		manager.createQuery().deleteById(CmpVideo.class, oid);
	}

	public CmpVideo getCmpVideo(long oid) {
		return manager.createQuery().getObjectById(CmpVideo.class, oid);
	}

	public List<CmpVideo> getCmpVideoListByCompanyIdAndCmpNavOid(
			long companyId, long cmpNavOid, int begin, int size) {
		return manager.createQuery().listEx(CmpVideo.class,
				"companyid=? and cmpnavoid=?",
				new Object[] { companyId, cmpNavOid }, "oid desc", begin, size);
	}

	public List<CmpVideo> getCmpVideoListByCompanyIdAndCmpNavOidForRange(
			long companyId, long cmpNavOid, long oid, int range, int size) {
		if (range >= 0) {
			return manager.createQuery().listEx(CmpVideo.class,
					"companyid=? and cmpnavoid=? and oid>?",
					new Object[] { companyId, cmpNavOid, oid }, "oid asc", 0,
					size);
		}
		return manager.createQuery()
				.listEx(CmpVideo.class,
						"companyid=? and cmpnavoid=? and oid<?",
						new Object[] { companyId, cmpNavOid, oid }, "oid desc",
						0, size);
	}

	public void updateCmpVideo(CmpVideo cmpVideo) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpVideo);
	}
}