package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpActor;
import com.hk.bean.CmpActorPink;
import com.hk.bean.CmpActorRole;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpActorService;

public class CmpActorServiceImpl implements CmpActorService {

	@Autowired
	private QueryManager manager;

	public void createCmpActor(CmpActor cmpActor) {
		Query query = manager.createQuery();
		cmpActor.setActorStatus(CmpActor.ACTORSTATUS_RUN);
		long actorId = query.insertObject(cmpActor).longValue();
		cmpActor.setActorId(actorId);
	}

	public void createCmpActorRole(CmpActorRole cmpActorRole) {
		Query query = manager.createQuery();
		long roleId = query.insertObject(cmpActorRole).longValue();
		cmpActorRole.setRoleId(roleId);
	}

	public void deleteCmpActor(long actorId) {
		Query query = manager.createQuery();
		query.deleteById(CmpActor.class, actorId);
	}

	public void deleteCmpActorRole(long roleId) {
		Query query = manager.createQuery();
		query.deleteById(CmpActorRole.class, roleId);
		query.addField("roleid", 0);
		query.update(CmpActor.class, "roleid=?", new Object[] { roleId });
	}

	public CmpActor getCmpActor(long actorId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpActor.class, actorId);
	}

	public List<CmpActor> getCmpActorListByCompanyId(long companyId,
			String name, int begin, int size) {
		Query query = manager.createQuery();
		if (!DataUtil.isEmpty(name)) {
			return query.listEx(CmpActor.class, "companyid=? and name like ?",
					new Object[] { companyId, "%" + name + "%" },
					"actorid desc", begin, size);
		}
		return query.listEx(CmpActor.class, "companyid=?",
				new Object[] { companyId }, "actorid desc", begin, size);
	}

	public List<CmpActor> getCmpActorListByCompanyId(long companyId,
			long roleId, String name, int begin, int size) {
		Query query = manager.createQuery();
		StringBuilder sb = new StringBuilder("companyid=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(companyId);
		if (roleId > 0) {
			sb.append(" and roleid=?");
			olist.add(roleId);
		}
		if (!DataUtil.isEmpty(name)) {
			sb.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		return query.listExParamList(CmpActor.class, sb.toString(), olist,
				"actorid desc", begin, size);
	}

	public CmpActorRole getCmpActorRole(long roleId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpActorRole.class, roleId);
	}

	public List<CmpActorRole> getCmpActorRoleListByCompanyId(long companyId) {
		Query query = manager.createQuery();
		return query.listEx(CmpActorRole.class, "companyid=?",
				new Object[] { companyId }, "roleid desc");
	}

	public void updateCmpActor(CmpActor cmpActor) {
		Query query = manager.createQuery();
		query.updateObject(cmpActor);
	}

	public void updateCmpActorRole(CmpActorRole cmpActorRole) {
		Query query = manager.createQuery();
		query.updateObject(cmpActorRole);
	}

	public Map<Long, CmpActor> getCmpActorMapInId(List<Long> idList) {
		Query query = manager.createQuery();
		List<CmpActor> list = query.listInField(CmpActor.class, null, null,
				"actorid", idList, null);
		Map<Long, CmpActor> map = new HashMap<Long, CmpActor>();
		for (CmpActor o : list) {
			map.put(o.getActorId(), o);
		}
		return map;
	}

	public CmpActor getCmpActorByName(long companyId, String name) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpActor.class, "companyid=? and name=?",
				new Object[] { companyId, name });
	}

	public List<CmpActor> getCmpActorListByCompanyIdForCanReserve(
			long companyId, int begin, int size) {
		Query query = manager.createQuery();
		if (begin < 0 || size < 0) {
			return query.listEx(CmpActor.class, "companyid=? and reserveflg=?",
					new Object[] { companyId, CmpActor.RESERVEFLG_Y },
					"actorid asc");
		}
		return query.listEx(CmpActor.class, "companyid=? and reserveflg=?",
				new Object[] { companyId, CmpActor.RESERVEFLG_Y },
				"actorid asc", begin, size);
	}

	public Map<Long, CmpActorRole> getCmpActorRoleMapByCompanyIdAndInId(
			List<Long> idList) {
		Query query = manager.createQuery();
		List<CmpActorRole> list = query.listInField(CmpActorRole.class, null,
				null, "roleid", idList, null);
		Map<Long, CmpActorRole> map = new HashMap<Long, CmpActorRole>();
		for (CmpActorRole o : list) {
			map.put(o.getRoleId(), o);
		}
		return map;
	}

	public void updateWorkCountByActorId(long actorId, int workCount) {
		Query query = manager.createQuery();
		query.addField("workcount", workCount);
		query.updateById(CmpActor.class, actorId);
	}

	public void createCmpActorPink(CmpActorPink cmpActorPink) {
		Query query = manager.createQuery();
		if (query.count(CmpActorPink.class, "actorid=?",
				new Object[] { cmpActorPink.getActorId() }) == 0) {
			long oid = query.insertObject(cmpActorPink).longValue();
			cmpActorPink.setOid(oid);
		}
	}

	public void deleteCmpActorPink(long oid) {
		Query query = manager.createQuery();
		query.deleteById(CmpActorPink.class, oid);
	}

	public List<CmpActorPink> getCmpActorPinkList(int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpActorPink.class, "oid desc", begin, size);
	}

	public List<CmpActor> getCmpActorListForWorkCount(int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpActor.class, "workcount desc", begin, size);
	}

	public CmpActorPink getCmpActorPinkByActorId(long actorId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpActorPink.class, "actorid=?",
				new Object[] { actorId });
	}

	public int sumCmpActorScoreByCompanyId(long companyId) {
		Query query = manager.createQuery();
		return query.sum("score", CmpActor.class, "companyid=?",
				new Object[] { companyId }).intValue();
	}

	public int sumCmpActorScoreUserNumByCompanyId(long companyId) {
		Query query = manager.createQuery();
		return query.sum("scoreusernum", CmpActor.class, "companyid=?",
				new Object[] { companyId }).intValue();
	}
}