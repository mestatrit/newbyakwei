package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpFile;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpFileService;

public class CmpFileServiceImpl implements CmpFileService {

	@Autowired
	private QueryManager manager;

	public void createCmpFile(CmpFile cmpFile) {
		Query query = this.manager.createQuery();
		long oid = query.insertObject(cmpFile).longValue();
		cmpFile.setOid(oid);
	}

	public void deleteCmpFile(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpFile.class, oid);
	}

	public CmpFile getCmpFile(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpFile.class, oid);
	}

	public List<CmpFile> getCmpFileListByCompanyIdAndArticleOid(long companyId,
			long articleOid) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpFile.class, "companyid=? and articleoid=?",
				new Object[] { companyId, articleOid }, "oid asc");
	}

	public void updateCmpFile(CmpFile cmpFile) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpFile);
	}
}