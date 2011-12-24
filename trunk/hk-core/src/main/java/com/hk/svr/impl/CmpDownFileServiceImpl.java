package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpDownFile;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpDownFileService;

public class CmpDownFileServiceImpl implements CmpDownFileService {

	@Autowired
	private QueryManager manager;

	public void createCmpDownFile(CmpDownFile cmpDownFile) {
		Query query = this.manager.createQuery();
		query.insertObject(cmpDownFile);
	}

	public void deleteCmpDownFile(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpDownFile.class, oid);
	}

	public List<CmpDownFile> getCmpDownFileListByCompanyIdAndCmpNavOid(
			long companyId, long cmpNavOid, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpDownFile.class, "companyid=? and cmpnavoid=?",
				new Object[] { companyId, cmpNavOid }, "oid desc", begin, size);
	}

	public void updateCmpDownFile(CmpDownFile cmpDownFile) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpDownFile);
	}

	public CmpDownFile getCmpDownFile(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpDownFile.class, oid);
	}

	public void addCmpDownFileDcount(long oid, int add) {
		Query query = this.manager.createQuery();
		query.addField("dcount", "add", add);
		query.updateById(CmpDownFile.class, oid);
	}
}