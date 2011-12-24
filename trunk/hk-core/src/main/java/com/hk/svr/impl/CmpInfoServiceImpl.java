package com.hk.svr.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpInfo;
import com.hk.bean.CmpLanguageRef;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpInfoService;

public class CmpInfoServiceImpl implements CmpInfoService {

	@Autowired
	private QueryManager manager;

	public CmpInfo getCmpInfo(long companyId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpInfo.class, companyId);
	}

	public CmpInfo getCmpInfoByDomain(String domain) {
		String sdomain = null;
		if (domain.startsWith("www.")) {
			sdomain = domain.substring(4);
		}
		else {
			sdomain = domain;
		}
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpInfo.class, "domain=?",
				new Object[] { sdomain });
	}

	public boolean createCmpInfo(CmpInfo cmpInfo) {
		String v = this.getFilterDomain(cmpInfo.getDomain());
		CmpInfo o = this.getCmpInfoByDomain(v);
		if (o != null) {
			return false;
		}
		Query query = this.manager.createQuery();
		cmpInfo.setDomain(v);
		query.insertObject(cmpInfo);
		return true;
	}

	public boolean updateCmpInfo(CmpInfo cmpInfo) {
		String v = this.getFilterDomain(cmpInfo.getDomain());
		CmpInfo o = this.getCmpInfoByDomain(v);
		if (o != null && o.getCompanyId() != cmpInfo.getCompanyId()) {
			return false;
		}
		Query query = this.manager.createQuery();
		cmpInfo.setDomain(v);
		query.updateObject(cmpInfo);
		return true;
	}

	private String getFilterDomain(String domain) {
		String v = domain.toLowerCase().replaceFirst("http://", "");
		if (v.startsWith("www.")) {
			v = v.replaceAll("www\\.", "");
		}
		return v;
	}

	public void createCmpLanguageRef(long companyId, long refCompanyId) {
		if (companyId == refCompanyId) {
			return;
		}
		Query query = this.manager.createQuery();
		if (query.count(CmpLanguageRef.class, "companyid=? and refcompanyid=?",
				new Object[] { companyId, refCompanyId }) == 0) {
			CmpLanguageRef o = new CmpLanguageRef(companyId, refCompanyId);
			query.insertObject(o);
		}
		if (query.count(CmpLanguageRef.class, "companyid=? and refcompanyid=?",
				new Object[] { refCompanyId, companyId }) == 0) {
			CmpLanguageRef o = new CmpLanguageRef(refCompanyId, companyId);
			query.insertObject(o);
		}
	}

	public void deleteCmpLanguageRef(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpLanguageRef.class, oid);
	}

	public List<CmpLanguageRef> getCmpLanguageRefListByCompanyId(long companyId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpLanguageRef.class, "companyid=?",
				new Object[] { companyId }, "oid asc");
	}

	public List<CmpInfo> getCmpInfoListInId(List<Long> idList) {
		Query query = this.manager.createQuery();
		return query.listInField(CmpInfo.class, null, null, "companyid",
				idList, null);
	}

	public Map<Long, CmpInfo> getCmpInfoMapInId(List<Long> idList) {
		List<CmpInfo> list = this.getCmpInfoListInId(idList);
		Map<Long, CmpInfo> map = new HashMap<Long, CmpInfo>();
		for (CmpInfo o : list) {
			map.put(o.getCompanyId(), o);
		}
		return map;
	}

	public List<CmpInfo> getCmpInfoList(int begin, int size) {
		Query query = this.manager.createQuery();
		if (size < 0) {
			return query.listEx(CmpInfo.class);
		}
		return query.listEx(CmpInfo.class, begin, size);
	}
}