package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpOrgFile;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpOrgFileService;

public class CmpOrgFileServiceImpl implements CmpOrgFileService {

	@Autowired
	private QueryManager manager;

	public void createCmpOrgFile(CmpOrgFile cmpOrgFile) {
		Query query = this.manager.createQuery();
		long oid = query.insertObject(cmpOrgFile).longValue();
		cmpOrgFile.setOid(oid);
	}

	public void deleteCmpOrgFile(long companyId, long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpOrgFile.class, oid);
	}

	public CmpOrgFile getCmpOrgFile(long companyId, long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpOrgFile.class, "companyid=? and oid=?",
				new Object[] { companyId, oid });
	}

	public List<CmpOrgFile> getCmpOrgFileListByCompanyIdAndArticleOid(
			long companyId, long articleOid) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpOrgFile.class, "companyid=? and articleoid=?",
				new Object[] { companyId, articleOid }, "oid asc");
	}

	public void updateCmpOrgFile(CmpOrgFile cmpOrgFile) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpOrgFile);
	}
}