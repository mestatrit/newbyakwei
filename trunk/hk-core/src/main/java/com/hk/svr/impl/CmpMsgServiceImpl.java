package com.hk.svr.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpMsg;
import com.hk.bean.CmpUtil;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpMsgService;

public class CmpMsgServiceImpl implements CmpMsgService {

	@Autowired
	private QueryManager manager;

	public void createCmpMsg(CmpMsg cmpMsg) {
		if (cmpMsg.getCreateTime() == null) {
			cmpMsg.setCreateTime(new Date());
		}
		cmpMsg.setCmppinkTime(cmpMsg.getCreateTime());
		Query query = this.manager.createQuery();
		long oid = query.insertObject(cmpMsg).longValue();
		cmpMsg.setOid(oid);
	}

	public void deleteCmpMsg(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpMsg.class, oid);
	}

	public CmpMsg getCmpMsg(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpMsg.class, oid);
	}

	public List<CmpMsg> getCmpMsgListByCompanyId(long companyId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpMsg.class, "companyid=?",
				new Object[] { companyId }, "oid desc", begin, size);
	}

	public List<CmpMsg> getCmpMsgListByCompanyIdForCmppink(long companyId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpMsg.class, "companyid=? and cmppink=?",
				new Object[] { companyId, CmpUtil.CMPPINK_Y },
				"cmppinktime desc", begin, size);
	}

	public void updateCmpMsgCmppink(long oid, byte cmppink) {
		Query query = this.manager.createQuery();
		query.addField("cmppink", cmppink);
		query.updateById(CmpMsg.class, oid);
	}
}