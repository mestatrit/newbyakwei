package com.hk.svr.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmdData;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmdDataService;

public class CmdDataServiceImpl implements CmdDataService {
	@Autowired
	private QueryManager manager;

	public synchronized boolean createCmdData(CmdData cmdData) {
		Query query = manager.createQuery();
		if (query.count(CmdData.class, "name=? and endtime>=?", new Object[] {
				cmdData.getName(), new Date() }) > 0) {
			return false;
		}
		query.addField("name", cmdData.getName());
		query.addField("oid", cmdData.getOid());
		query.addField("otype", cmdData.getOtype());
		query.addField("endtime", cmdData.getEndTime());
		query.addField("endflg", cmdData.getEndflg());
		long cmdId = query.insert(CmdData.class).longValue();
		cmdData.setCmdId(cmdId);
		return true;
	}

	public void deleteCmdData(long cmdId) {
		Query query = manager.createQuery();
		query.deleteById(CmdData.class, cmdId);
	}

	public CmdData getCmdDataByName(String name) {
		Query query = manager.createQuery();
		return query
				.getObjectEx(CmdData.class, "name=?", new Object[] { name });
	}

	public synchronized boolean updateCmdData(CmdData cmdData) {
		Query query = manager.createQuery();
		CmdData o = null;
		if (cmdData.getEndflg() == CmdData.ENDFLG_Y) {
			o = query.getObjectEx(CmdData.class, "name=? and endtime>=?",
					new Object[] { cmdData.getName(), cmdData.getEndTime() });
		}
		else {
			o = query.getObjectEx(CmdData.class, "name=?",
					new Object[] { cmdData.getName() });
		}
		if (o != null) {
			if (o.getCmdId() == cmdData.getCmdId()) {
				updateData(cmdData);
				return true;
			}
			return false;
		}
		updateData(cmdData);
		return true;
	}

	private void updateData(CmdData cmdData) {
		Query query = manager.createQuery();
		query.addField("name", cmdData.getName());
		query.addField("oid", cmdData.getOid());
		query.addField("otype", cmdData.getOtype());
		query.addField("endtime", cmdData.getEndTime());
		query.addField("endflg", cmdData.getEndflg());
		query.updateById(CmdData.class, cmdData.getCmdId());
	}

	public CmdData getCmdDataByOidAndOtype(long oid, int otype) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmdData.class, "oid=? and otype=?",
				new Object[] { oid, otype });
	}

	public void deleteCmdDataByOidAndOtype(long oid, int otype) {
		Query query = manager.createQuery();
		query.delete(CmdData.class, "oid=? and otype=?", new Object[] { oid,
				otype });
	}
}