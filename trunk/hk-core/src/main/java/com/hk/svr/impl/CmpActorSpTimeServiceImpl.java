package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpActorSpTime;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpActorSpTimeService;

public class CmpActorSpTimeServiceImpl implements CmpActorSpTimeService {

	@Autowired
	private QueryManager manager;

	public void createCmpActorSpTime(CmpActorSpTime cmpActorSpTime) {
		Query query = this.manager.createQuery();
		long oid = query.insertObject(cmpActorSpTime).longValue();
		cmpActorSpTime.setOid(oid);
	}

	public void deleteCmpActorSpTime(long companyId, long oid) {
		Query query = this.manager.createQuery();
		query.delete(CmpActorSpTime.class, "companyid=? and oid=?",
				new Object[] { companyId, oid });
	}

	public void deleteCmpActorSpTimeByCompanyIdAndActorId(long companyId,
			long actorId) {
		Query query = this.manager.createQuery();
		query.delete(CmpActorSpTime.class, "companyid=? and actorid=?",
				new Object[] { companyId, actorId });
	}

	public CmpActorSpTime getCmpActorSpTime(long companyId, long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpActorSpTime.class, "companyid=? and oid=?",
				new Object[] { companyId, oid });
	}

	public List<CmpActorSpTime> getCmpActorSpTimeListByCompanyIdEx(
			long companyId, long actorId, byte spflg, Date beginTime,
			Date endTime, int begin, int size) {
		StringBuilder sb = new StringBuilder("companyid=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(companyId);
		if (beginTime != null) {
			olist.add(beginTime);
			sb.append(" and begintime>=?");
		}
		if (endTime != null) {
			olist.add(endTime);
			sb.append(" and endtime<=?");
		}
		if (actorId > 0) {
			sb.append(" and actorid=?");
			olist.add(actorId);
		}
		if (spflg > 0) {
			sb.append(" and  spflg=?");
			olist.add(spflg);
		}
		Query query = this.manager.createQuery();
		return query.listExParamList(CmpActorSpTime.class, sb.toString(),
				olist, "oid desc", begin, size);
	}

	public void updateCmpActorSpTime(CmpActorSpTime cmpActorSpTime) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpActorSpTime);
	}
}