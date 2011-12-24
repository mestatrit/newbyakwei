package com.hk.svr.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpNav;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpNavService;

public class CmpNavServiceImpl implements CmpNavService {

	@Autowired
	private QueryManager manager;

	public void createCmpNav(CmpNav cmpNav) {
		Query query = this.manager.createQuery();
		List<CmpNav> list = this.getCmpNavListByCompanyIdAndNlevel(cmpNav
				.getCompanyId(), cmpNav.getNlevel());
		int max = 0;
		for (CmpNav o : list) {
			if (o.getOrderflg() > max) {
				max = o.getOrderflg();
			}
		}
		cmpNav.setOrderflg(max + 1);
		long oid = query.insertObject(cmpNav).longValue();
		cmpNav.setOid(oid);
	}

	public void deleteCmpNav(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpNav.class, oid);
	}

	public CmpNav getCmpNav(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpNav.class, oid);
	}

	public List<CmpNav> getCmpNavListByCompanyIdAndNlevel(long companyId,
			int nlevel) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpNav.class, "companyId=? and nlevel=?",
				new Object[] { companyId, nlevel }, "orderflg asc,oid asc");
	}

	public List<CmpNav> getCmpNavListByCompanyIdAndParentId(long companyId,
			long parentId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpNav.class, "companyId=? and parentid=?",
				new Object[] { companyId, parentId }, "orderflg asc,oid asc");
	}

	public List<CmpNav> getCmpNavListByCompanyId(long companyId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpNav.class, "companyId=?",
				new Object[] { companyId }, "orderflg asc,oid asc");
	}

	public void updateCmpNav(CmpNav cmpNav) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpNav);
	}

	public void updateCmpNavOrderflg(long oid, int orderflg) {
		Query query = this.manager.createQuery();
		query.addField("orderflg", orderflg);
		query.updateById(CmpNav.class, oid);
	}

	public void setCmpNavShowInHome(long companyId, long oid, byte homepos) {
		List<CmpNav> list = this.getCmpNavListByCompanyId(companyId);
		Query query = this.manager.createQuery();
		int max = 0;
		// 取出所有
		for (CmpNav o : list) {
			if (o.getShowInHome() > max) {
				max = o.getShowInHome();
			}
		}
		query.addField("showinhome", max + 1);
		query.addField("homepos", homepos);
		query.updateById(CmpNav.class, oid);
	}

	public List<CmpNav> getCmpNavListByCompanyIdForHome(long companyId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpNav.class, "companyid=? and showinhome>?",
				new Object[] { companyId, 0 }, "showinhome asc");
	}

	public List<CmpNav> getCmpNavListByCompanyIdForHome(long companyId,
			byte homepos) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpNav.class,
				"companyid=? and showinhome>? and homepos=?", new Object[] {
						companyId, 0, homepos }, "showinhome asc");
	}

	public CmpNav getHomeCmpNav(long companyId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpNav.class, "companyid=? and reffunc=?",
				new Object[] { companyId, CmpNav.REFFUNC_HOME });
	}

	public CmpNav getCmpNavByCompanyIdAndReffunc(long companyId, int reffunc) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpNav.class, "companyid=? and reffunc=?",
				new Object[] { companyId, reffunc });
	}

	public void updateCmpNavShowInHome(long oid, int showInHome) {
		Query query = this.manager.createQuery();
		query.addField("showinhome", showInHome);
		query.updateById(CmpNav.class, oid);
	}

	public int countCmpNavByCompanyIdAndNlevel(long companyId, byte nlevel) {
		Query query = this.manager.createQuery();
		return query.count(CmpNav.class, "companyid=? and nlevel=?",
				new Object[] { companyId, nlevel });
	}

	public int countCmpNavByCompanyIdAndParentIdAndNlevel(long companyId,
			long parentId, byte nlevel) {
		Query query = this.manager.createQuery();
		return query.count(CmpNav.class,
				"companyid=? and parentid=? and nlevel=?", new Object[] {
						companyId, parentId, nlevel });
	}

	public Map<Long, CmpNav> getCmpNavMapByCompanyIdAndInId(long companyId,
			List<Long> idList) {
		Query query = this.manager.createQuery();
		List<CmpNav> list = query.listInField(CmpNav.class, "companyid=?",
				new Object[] { companyId }, "oid", idList, null);
		Map<Long, CmpNav> map = new HashMap<Long, CmpNav>();
		for (CmpNav o : list) {
			map.put(o.getOid(), o);
		}
		return map;
	}

	public List<CmpNav> getCmpNavListByCompanyIdAndInId(long companyId,
			List<Long> idList) {
		Query query = this.manager.createQuery();
		return query.listInField(CmpNav.class, "companyid=?",
				new Object[] { companyId }, "oid", idList, "orderflg desc");
	}
}