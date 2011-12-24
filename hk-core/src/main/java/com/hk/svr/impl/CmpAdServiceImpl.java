package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpAd;
import com.hk.bean.CmpAdGroup;
import com.hk.bean.CmpAdRef;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpAdService;

public class CmpAdServiceImpl implements CmpAdService {

	@Autowired
	private QueryManager manager;

	public void createCmpAd(CmpAd cmpAd) {
		Query query = this.manager.createQuery();
		long adid = query.insertObject(cmpAd).longValue();
		cmpAd.setAdid(adid);
	}

	public void deleteCmpAd(long companyId, long adid) {
		Query query = this.manager.createQuery();
		query.delete(CmpAd.class, "companyid=? and adid=?", new Object[] {
				companyId, adid });
		query.delete(CmpAdRef.class, "companyid=? and adid=?", new Object[] {
				companyId, adid });
	}

	public CmpAd getCmpAd(long adid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpAd.class, adid);
	}

	public List<CmpAd> getCmpAdListByCompanyId(long companyId, long groupId,
			int begin, int size) {
		if (groupId > 0) {
			return manager.createQuery().listEx(CmpAd.class,
					"companyid=? and groupid=?",
					new Object[] { companyId, groupId }, "adid asc", begin,
					size);
		}
		return manager.createQuery().listEx(CmpAd.class, "companyid=?",
				new Object[] { companyId }, "adid asc", begin, size);
	}

	public List<CmpAd> getCmpAdListByCompanyIdAndGroupId(long companyId,
			long groupId) {
		return manager.createQuery().listEx(CmpAd.class,
				"companyid=? and groupid=?",
				new Object[] { companyId, groupId }, "adid asc");
	}

	public void updateCmpAd(CmpAd cmpAd) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpAd);
	}

	public Map<Long, CmpAd> getCmpAdMaByCompanyIdAndInId(long companyId,
			List<Long> idList) {
		Map<Long, CmpAd> map = new HashMap<Long, CmpAd>();
		Query query = this.manager.createQuery();
		List<CmpAd> list = query.listInField(CmpAd.class, "companyid=?",
				new Object[] { companyId }, "adid", idList, null);
		for (CmpAd o : list) {
			map.put(o.getAdid(), o);
		}
		return map;
	}

	public boolean createCmpAdGroup(CmpAdGroup cmpAdGroup) {
		Query query = this.manager.createQuery();
		if (query
				.count(CmpAdGroup.class, "companyid=? and name=?",
						new Object[] { cmpAdGroup.getCompanyId(),
								cmpAdGroup.getName() }) > 0) {
			return false;
		}
		long groupId = query.insertObject(cmpAdGroup).longValue();
		cmpAdGroup.setGroupId(groupId);
		return true;
	}

	public void deleteCmpAdGroup(long companyId, long groupId) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpAdGroup.class, groupId);
		query.addField("groupid", 0);
		query.update(CmpAd.class, "companyid=? and groupid=?", new Object[] {
				companyId, groupId });
	}

	public CmpAdGroup getCmpAdGroup(long companyId, long groupId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpAdGroup.class, "companyid=? and groupid=?",
				new Object[] { companyId, groupId });
	}

	public List<CmpAdGroup> getCmpAdGroupListByCompanyId(long companyId,
			String name, int begin, int size) {
		List<Object> olist = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(
				"select * from cmpadgroup where companyid=?");
		olist.add(companyId);
		if (!DataUtil.isEmpty(name)) {
			sb.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		sb.append(" order by groupid desc");
		Query query = this.manager.createQuery();
		return query.listBySqlParamList("ds1", sb.toString(), begin, size,
				CmpAdGroup.class, olist);
	}

	public boolean updateCmpAdGroup(CmpAdGroup cmpAdGroup) {
		Query query = this.manager.createQuery();
		if (query.count(CmpAdGroup.class,
				"companyid=? and groupid!=? and name=?", new Object[] {
						cmpAdGroup.getCompanyId(), cmpAdGroup.getGroupId(),
						cmpAdGroup.getName() }) > 0) {
			return false;
		}
		query.updateObject(cmpAdGroup);
		return true;
	}

	public boolean createCmpAdRef(CmpAdRef cmpAdRef) {
		Query query = this.manager.createQuery();
		if (query.count(CmpAdRef.class, "companyid=? and adid=?", new Object[] {
				cmpAdRef.getCompanyId(), cmpAdRef.getAdid() }) > 0) {
			return false;
		}
		query.insertObject(cmpAdRef);
		return true;
	}

	public void deleteCmpAdRef(long companyId, long oid) {
		Query query = this.manager.createQuery();
		query.delete(CmpAdRef.class, "companyid=? and oid=?", new Object[] {
				companyId, oid });
	}

	public List<CmpAdRef> getCmpAdRefListByCompanyId(long companyId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpAdRef.class, "companyid=?",
				new Object[] { companyId }, "oid desc", begin, size);
	}

	public CmpAdRef getCmpAdRefByCompanyIdAndAdid(long companyId, long adid) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpAdRef.class, "companyid=? and adid=?",
				new Object[] { companyId, adid });
	}

	public CmpAdRef getCmpAdRefByCompanyIdAndOid(long companyId, long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpAdRef.class, "companyid=? and oid=?",
				new Object[] { companyId, oid });
	}
}