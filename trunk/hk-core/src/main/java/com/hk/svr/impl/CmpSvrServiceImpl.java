package com.hk.svr.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpActorSvrRef;
import com.hk.bean.CmpSvr;
import com.hk.bean.CmpSvrKind;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpSvrService;

public class CmpSvrServiceImpl implements CmpSvrService {

	@Autowired
	private QueryManager manager;

	public void createCmpActorSvrRef(CmpActorSvrRef cmpActorSvrRef) {
		Query query = this.manager.createQuery();
		if (query
				.count(CmpActorSvrRef.class,
						"companyid=? and actorid=? and svrid=?", new Object[] {
								cmpActorSvrRef.getCompanyId(),
								cmpActorSvrRef.getActorId(),
								cmpActorSvrRef.getSvrId() }) > 0) {
			return;
		}
		long oid = query.insertObject(cmpActorSvrRef).longValue();
		cmpActorSvrRef.setOid(oid);
	}

	public void createCmpSvr(CmpSvr cmpSvr) {
		Query query = this.manager.createQuery();
		query.insertObject(cmpSvr);
	}

	public void deleteCmpActorSvrRef(long companyId, long oid) {
		Query query = this.manager.createQuery();
		query.delete(CmpActorSvrRef.class, "companyid=? and oid=?",
				new Object[] { companyId, oid });
	}

	public void deleteCmpSvr(long companyId, long svrId) {
		Query query = this.manager.createQuery();
		query.delete(CmpActorSvrRef.class, "companyid=? and svrid=?",
				new Object[] { companyId, svrId });
		query.delete(CmpSvr.class, "companyid=? and svrid=?", new Object[] {
				companyId, svrId });
	}

	public CmpActorSvrRef getCmpActorSvrRef(long companyId, long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpActorSvrRef.class, "companyid=? and oid=?",
				new Object[] { companyId, oid });
	}

	public List<CmpActorSvrRef> getCmpActorSvrRefListByCompanyIdAndActorId(
			long companyId, long actorId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpActorSvrRef.class, "companyid=? and actorid=?",
				new Object[] { companyId, actorId }, "oid desc");
	}

	public CmpSvr getCmpSvr(long companyId, long svrId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpSvr.class, "companyid=? and svrid=?",
				new Object[] { companyId, svrId });
	}

	public List<CmpSvr> getCmpSvrListByCompanyId(long companyId, String name,
			int begin, int size) {
		Query query = this.manager.createQuery();
		if (!DataUtil.isEmpty(name)) {
			return query.listEx(CmpSvr.class, "companyid=? and name like ?",
					new Object[] { companyId, "%" + name + "%" }, "svrid desc",
					begin, size);
		}
		return query.listEx(CmpSvr.class, "companyid=?",
				new Object[] { companyId }, "svrid desc", begin, size);
	}

	public void updateCmpSvr(CmpSvr cmpSvr) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpSvr);
	}

	public Map<Long, CmpSvr> getCmpSvrMapInId(List<Long> idList) {
		List<CmpSvr> list = this.getCmpSvrListInId(idList);
		Map<Long, CmpSvr> map = new HashMap<Long, CmpSvr>();
		for (CmpSvr o : list) {
			map.put(o.getSvrId(), o);
		}
		return map;
	}

	public List<CmpSvr> getCmpSvrListInId(List<Long> idList) {
		Query query = this.manager.createQuery();
		return query.listInField(CmpSvr.class, null, null, "svrid", idList,
				null);
	}

	public void createCmpSvrKind(CmpSvrKind cmpSvrKind) {
		Query query = this.manager.createQuery();
		long kindId = query.insertObject(cmpSvrKind).longValue();
		cmpSvrKind.setKindId(kindId);
	}

	public void deleteCmpSvrKind(long companyId, long kindId) {
		Query query = this.manager.createQuery();
		query.addField("kindid", 0);
		query.update(CmpSvr.class, "companyid=? and kindid=?", new Object[] {
				companyId, kindId });
		query.delete(CmpSvrKind.class, "companyid=? and kindid=?",
				new Object[] { companyId, kindId });
	}

	public CmpSvrKind getCmpSvrKind(long companyId, long kindId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpSvrKind.class, "companyid=? and kindid=?",
				new Object[] { companyId, kindId });
	}

	public List<CmpSvrKind> getCmpSvrKindListByCompanyId(long companyId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpSvrKind.class, "companyid=?",
				new Object[] { companyId }, "kindid desc");
	}

	public void updateCmpSvrKind(CmpSvrKind cmpSvrKind) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpSvrKind);
	}

	public List<CmpActorSvrRef> getCmpActorSvrRefListByCompanyIdAndSvrId(
			long companyId, long svrId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpActorSvrRef.class, "companyid=? and svrid=?",
				new Object[] { companyId, svrId }, "oid desc", begin, size);
	}
}