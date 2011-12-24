package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpOrg;
import com.hk.bean.CmpOrgApply;
import com.hk.bean.CmpOrgNav;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpOrgService;

public class CmpOrgServiceImpl implements CmpOrgService {

	@Autowired
	private QueryManager manager;

	public void createCmpOrg(CmpOrg cmpOrg) {
		Query query = manager.createQuery();
		long orgId = query.insertObject(cmpOrg).longValue();
		cmpOrg.setOrgId(orgId);
		// 创建默认机构的栏目
		this.createDefaultCmpOrgNavBatch(cmpOrg.getCompanyId(), orgId);
	}

	public CmpOrg getCmpOrg(long companyId, long orgId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpOrg.class, "companyid=? and orgid=?",
				new Object[] { companyId, orgId });
	}

	public CmpOrg getCmpOrgByCompanyIdAndUserId(long companyId, long userId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpOrg.class, "companyid=? and userid=?",
				new Object[] { companyId, userId });
	}

	public List<CmpOrg> getCmpOrgListByCompanyId(long companyId, int begin,
			int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpOrg.class, "companyid=?",
				new Object[] { companyId }, "orgid desc", begin, size);
	}

	public List<CmpOrg> getCmpOrgListByCompanyId(long companyId, String name,
			int begin, int size) {
		Query query = manager.createQuery();
		if (DataUtil.isEmpty(name)) {
			return query.listEx(CmpOrg.class, "companyid=?",
					new Object[] { companyId }, "orgid desc", begin, size);
		}
		return query.listEx(CmpOrg.class, "companyid=? and name like ?",
				new Object[] { companyId, "%" + name + "%" }, "orgid desc",
				begin, size);
	}

	public void updateCmpOrg(CmpOrg cmpOrg) {
		Query query = manager.createQuery();
		query.updateObject(cmpOrg);
	}

	public void createCmpOrgNav(CmpOrgNav cmpOrgNav) {
		Query query = manager.createQuery();
		int max = 0;
		List<CmpOrgNav> list = this.getCmpOrgNavListByCompanyIdAndOrgId(
				cmpOrgNav.getCompanyId(), cmpOrgNav.getOrgId());
		for (CmpOrgNav o : list) {
			if (o.getOrderflg() > max) {
				max = o.getOrderflg();
			}
		}
		cmpOrgNav.setOrderflg(max + 1);
		long navId = query.insertObject(cmpOrgNav).longValue();
		cmpOrgNav.setNavId(navId);
	}

	private void createDefaultCmpOrgNavBatch(long companyId, long orgId) {
		List<CmpOrgNav> list = CmpOrgNav.getDefCmpOrgNavs();
		for (CmpOrgNav o : list) {
			o.setCompanyId(companyId);
			o.setOrgId(orgId);
			this.createCmpOrgNav(o);
		}
	}

	public void deleteCmpOrgNav(long companyId, long navId) {
		Query query = manager.createQuery();
		query.delete(CmpOrgNav.class, "companyid=? and navid=?", new Object[] {
				companyId, navId });
	}

	public CmpOrgNav getCmpOrgNav(long companyId, long navId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpOrgNav.class, "companyid=? and navid=?",
				new Object[] { companyId, navId });
	}

	public List<CmpOrgNav> getCmpOrgNavListByCompanyIdAndOrgId(long companyId,
			long orgId) {
		Query query = manager.createQuery();
		return query.listEx(CmpOrgNav.class, "companyid=? and orgid=?",
				new Object[] { companyId, orgId }, "orderflg asc,navid asc");
	}

	public void updateCmpOrgNav(CmpOrgNav cmpOrgNav) {
		Query query = manager.createQuery();
		query.updateObject(cmpOrgNav);
	}

	public void createCmpOrgApply(CmpOrgApply cmpOrgApply) {
		if (cmpOrgApply.getCreateTime() == null) {
			cmpOrgApply.setCreateTime(new Date());
		}
		cmpOrgApply.setCheckflg(CmpOrgApply.CHECKFLG_UNCHECKED);
		Query query = manager.createQuery();
		long oid = query.insertObject(cmpOrgApply).longValue();
		cmpOrgApply.setOid(oid);
	}

	public void deleteCmpOrgApply(long companyId, long oid) {
		Query query = manager.createQuery();
		query.delete(CmpOrgApply.class, "companyid=? and oid=?", new Object[] {
				companyId, oid });
	}

	public List<CmpOrgApply> getCmpOrgApplyListByCompanyId(long companyId,
			String userName, String tel, String email, String orgName,
			byte checkflg, int begin, int size) {
		Query query = manager.createQuery();
		List<Object> olist = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder("companyid=?");
		olist.add(companyId);
		if (checkflg >= 0) {
			sb.append(" and checkflg=?");
			olist.add(checkflg);
		}
		if (!DataUtil.isEmpty(userName)) {
			sb.append(" and username like ?");
			olist.add("%" + userName + "%");
		}
		if (!DataUtil.isEmpty(tel)) {
			sb.append(" and tel like ?");
			olist.add("%" + tel + "%");
		}
		if (!DataUtil.isEmpty(email)) {
			sb.append(" and email like ?");
			olist.add("%" + email + "%");
		}
		if (!DataUtil.isEmpty(orgName)) {
			sb.append(" and orgname like ?");
			olist.add("%" + orgName + "%");
		}
		return query.listExParamList(CmpOrgApply.class, sb.toString(), olist,
				"oid asc", begin, size);
	}

	public void updateCmpOrgApply(CmpOrgApply cmpOrgApply) {
		Query query = manager.createQuery();
		query.updateObject(cmpOrgApply);
	}

	public CmpOrgApply getCmpOrgApply(long companyId, long oid) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpOrgApply.class, "companyid=? and oid=?",
				new Object[] { companyId, oid });
	}

	public CmpOrgApply getCmpOrgApplyByCompanyIdAndUserId(long companyId,
			long userId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpOrgApply.class, "companyid=? and userid=?",
				new Object[] { companyId, userId });
	}

	public CmpOrg checkOkCmpOrgApply(CmpOrgApply cmpOrgApply) {
		if (cmpOrgApply.getOrgId() > 0) {
			cmpOrgApply.setCheckflg(CmpOrgApply.CHECKFLG_YES);
			this.updateCmpOrgApply(cmpOrgApply);
			return this.getCmpOrg(cmpOrgApply.getCompanyId(), cmpOrgApply
					.getOrgId());
		}
		CmpOrg cmpOrg = new CmpOrg();
		cmpOrg.setName(cmpOrgApply.getOrgName());
		cmpOrg.setCompanyId(cmpOrgApply.getCompanyId());
		cmpOrg.setUserId(cmpOrgApply.getUserId());
		cmpOrg.setFlg(CmpOrg.FLG_Y);
		cmpOrg.setCityId(cmpOrgApply.getCityId());
		this.createCmpOrg(cmpOrg);
		cmpOrgApply.setCheckflg(CmpOrgApply.CHECKFLG_YES);
		cmpOrgApply.setOrgId(cmpOrg.getOrgId());
		this.updateCmpOrgApply(cmpOrgApply);
		return cmpOrg;
	}
}