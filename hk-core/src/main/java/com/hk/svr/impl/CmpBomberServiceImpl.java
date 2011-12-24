package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpBomber;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpBomberService;

public class CmpBomberServiceImpl implements CmpBomberService {

	@Autowired
	private QueryManager manager;

	public void deleteCmpBomber(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpBomber.class, oid);
	}

	public CmpBomber getCmpBomber(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpBomber.class, oid);
	}

	public List<CmpBomber> getCmpBomberListByCompanyId(long companyId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpBomber.class, "companyid=?",
				new Object[] { companyId }, "oid desc", begin, size);
	}

	public void saveCmpBomber(CmpBomber cmpBomber) {
		Query query = this.manager.createQuery();
		CmpBomber o = query.getObjectEx(CmpBomber.class,
				"companyid=? and userid=?", new Object[] {
						cmpBomber.getCompanyId(), cmpBomber.getUserId() });
		if (o != null) {
			cmpBomber.setOid(o.getOid());
			o.setBombcount(cmpBomber.getBombcount());
			query.updateObject(o);
		}
		else {
			cmpBomber.setOid(query.insertObject(cmpBomber).longValue());
		}
	}

	public void useBomb(long companyId, long userId, int add) {
		Query query = this.manager.createQuery();
		query.addField("bombcount", "add", -add);
		query.update(CmpBomber.class, "companyid=? and userid=?", new Object[] {
				companyId, userId });
	}

	public CmpBomber getCmpBomberByCompanyIdAndUserId(long companyId,
			long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpBomber.class, "companyid=? and userid=?",
				new Object[] { companyId, userId });
	}
}