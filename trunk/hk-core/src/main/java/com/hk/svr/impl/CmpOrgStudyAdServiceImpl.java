package com.hk.svr.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpOrgStudyAd;
import com.hk.bean.CmpOrgStudyAdContent;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpOrgStudyAdService;

public class CmpOrgStudyAdServiceImpl implements CmpOrgStudyAdService {

	@Autowired
	private QueryManager manager;

	public void createCmpOrgStudyAd(CmpOrgStudyAd cmpOrgStudyAd,
			CmpOrgStudyAdContent cmpOrgStudyAdContent) {
		Query query = manager.createQuery();
		long adid = query.insertObject(cmpOrgStudyAd).longValue();
		cmpOrgStudyAd.setAdid(adid);
		cmpOrgStudyAdContent.setAdid(adid);
		query.insertObject(cmpOrgStudyAdContent);
	}

	public void deleteCmpOrgStudyAd(long companyId, long adid) {
		Query query = manager.createQuery();
		query.delete(CmpOrgStudyAd.class, "companyid=? and adid=?",
				new Object[] { companyId, adid });
		query.delete(CmpOrgStudyAdContent.class, "companyid=? and adid=?",
				new Object[] { companyId, adid });
	}

	public CmpOrgStudyAd getCmpOrgStudyAd(long companyId, long adid) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpOrgStudyAd.class, "companyid=? and adid=?",
				new Object[] { companyId, adid });
	}

	public CmpOrgStudyAdContent getCmpOrgStudyAdContent(long companyId,
			long adid) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpOrgStudyAdContent.class,
				"companyid=? and adid=?", new Object[] { companyId, adid });
	}

	public List<CmpOrgStudyAd> getCmpOrgStudyAdListByCompanyIdAndOrgId(
			long companyId, long orgId, String title, int begin, int size) {
		Query query = manager.createQuery();
		if (!DataUtil.isEmpty(title)) {
			return query.listEx(CmpOrgStudyAd.class,
					"companyid=? and orgid=? and title like ?", new Object[] {
							companyId, orgId, "%" + title + "%" }, "adid desc",
					begin, size);
		}
		return query.listEx(CmpOrgStudyAd.class, "companyid=? and orgid=?",
				new Object[] { companyId, orgId }, "adid desc", begin, size);
	}

	public List<CmpOrgStudyAd> getCmpOrgStudyAdListByCompanyIdAndKindId(
			long companyId, long kindId, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpOrgStudyAd.class, "companyid=? and kindid=?",
				new Object[] { companyId, kindId }, "adid desc", begin, size);
	}

	public List<CmpOrgStudyAd> getCmpOrgStudyAdListByCompanyIdAndKindId2(
			long companyId, long kindId2, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpOrgStudyAd.class, "companyid=? and kindid2=?",
				new Object[] { companyId, kindId2 }, "adid desc", begin, size);
	}

	public List<CmpOrgStudyAd> getCmpOrgStudyAdListByCompanyIdAndKindId3(
			long companyId, long kindId3, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpOrgStudyAd.class, "companyid=? and kindid3=?",
				new Object[] { companyId, kindId3 }, "adid desc", begin, size);
	}

	public int countCmpOrgStudyAdByCompanyIdAndKindId(long companyId,
			long kindId) {
		Query query = manager.createQuery();
		return query.count(CmpOrgStudyAd.class, "companyid=? and kindid=?",
				new Object[] { companyId, kindId });
	}

	public int countCmpOrgStudyAdByCompanyIdAndKindId2(long companyId,
			long kindId2) {
		Query query = manager.createQuery();
		return query.count(CmpOrgStudyAd.class, "companyid=? and kindid2=?",
				new Object[] { companyId, kindId2 });
	}

	public int countCmpOrgStudyAdByCompanyIdAndKindId3(long companyId,
			long kindId3) {
		Query query = manager.createQuery();
		return query.count(CmpOrgStudyAd.class, "companyid=? and kindid3=?",
				new Object[] { companyId, kindId3 });
	}

	public List<CmpOrgStudyAd> getCmpOrgStudyAdListByCompanyIdAndOrgIdForNext(
			long companyId, long orgId, long currentAdid, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpOrgStudyAd.class,
				"companyid=? and orgid=? and adid<?", new Object[] { companyId,
						orgId, currentAdid }, "adid desc", 0, size);
	}

	public void updateCmpOrgStudyAd(CmpOrgStudyAd cmpOrgStudyAd,
			CmpOrgStudyAdContent cmpOrgStudyAdContent) {
		Query query = manager.createQuery();
		query.updateObject(cmpOrgStudyAd);
		if (cmpOrgStudyAdContent != null) {
			query.updateObject(cmpOrgStudyAdContent);
		}
	}

	public int countCmpOrgStudyAdByCompanyIdAndOrgId(long companyId, long orgId) {
		Query query = manager.createQuery();
		return query.count(CmpOrgStudyAd.class, "companyid=? and orgid=?",
				new Object[] { companyId, orgId });
	}

	public Map<Long, CmpOrgStudyAd> getCmpOrgStudyAdMapByCompanyIdInId(
			long companyId, List<Long> idList) {
		Query query = manager.createQuery();
		List<CmpOrgStudyAd> list = query
				.listInField(CmpOrgStudyAd.class, "companyid=?",
						new Object[] { companyId }, "adid", idList, null);
		Map<Long, CmpOrgStudyAd> map = new HashMap<Long, CmpOrgStudyAd>();
		for (CmpOrgStudyAd o : list) {
			map.put(o.getAdid(), o);
		}
		return map;
	}
}