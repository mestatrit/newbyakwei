package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpProductSortFile;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpProductSortFileService;

public class CmpProductSortFileServiceImpl implements CmpProductSortFileService {

	@Autowired
	private QueryManager manager;

	public void createCmpProductSortFile(CmpProductSortFile cmpProductSortFile) {
		Query query = this.manager.createQuery();
		cmpProductSortFile.setOid(query.insertObject(cmpProductSortFile)
				.longValue());
	}

	public void deleteCmpProductSortFile(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpProductSortFile.class, oid);
	}

	public CmpProductSortFile getCmpProductSortFile(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpProductSortFile.class, oid);
	}

	public List<CmpProductSortFile> getCmpProductSortFileListByCompanyId(
			long companyId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpProductSortFile.class, "companyid=?",
				new Object[] { companyId }, "oid asc");
	}

	public List<CmpProductSortFile> getCmpProductSortFileListByCompanyIdAndSortId(
			long companyId, int sortId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpProductSortFile.class,
				"companyid=? and sortid=?", new Object[] { companyId, sortId },
				"oid asc", begin, size);
	}

	public void updateCmpProductSortFile(CmpProductSortFile cmpProductSortFile) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpProductSortFile);
	}
}