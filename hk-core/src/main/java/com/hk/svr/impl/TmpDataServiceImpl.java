package com.hk.svr.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.TmpData;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.TmpDataService;

public class TmpDataServiceImpl implements TmpDataService {

	@Autowired
	private QueryManager manager;

	public void createTmpData(TmpData tmpData) {
		if (tmpData.getCreateTime() == null) {
			tmpData.setCreateTime(new Date());
		}
		Query query = this.manager.createQuery();
		tmpData.setOid(query.insertObject(tmpData).longValue());
	}

	public void deleteTmpData(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(TmpData.class, oid);
	}

	public TmpData getLastTmpDataByUserIdAndDataType(long userId, byte dataType) {
		Query query = this.manager.createQuery();
		return query.getObject(TmpData.class, "userid=? and datatype=?",
				new Object[] { userId, dataType }, "oid desc");
	}

	public void deleteTmpDataByUserIdAndDataType(long userId, byte dataType) {
		Query query = this.manager.createQuery();
		query.delete(TmpData.class, "userid=? and datatype=?", new Object[] {
				userId, dataType });
	}

	public TmpData getTmpData(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(TmpData.class, oid);
	}

	public void updateTmpData(TmpData tmpData) {
		Query query = this.manager.createQuery();
		query.updateObject(tmpData);
	}
}