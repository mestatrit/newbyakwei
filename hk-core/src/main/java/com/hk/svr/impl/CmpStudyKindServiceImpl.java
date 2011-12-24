package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpStudyKind;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpStudyKindService;

public class CmpStudyKindServiceImpl implements CmpStudyKindService {

	@Autowired
	private QueryManager manager;

	public void createCmpStudyKind(CmpStudyKind cmpOrgStudyKind) {
		Query query = this.manager.createQuery();
		if (cmpOrgStudyKind.getParentId() > 0) {
			CmpStudyKind parent = this.getCmpStudyKind(cmpOrgStudyKind
					.getCompanyId(), cmpOrgStudyKind.getParentId());
			if (parent == null) {
				return;
			}
			if (parent.getKlevel() > 3) {
				return;
			}
			parent.setChildflg(CmpStudyKind.CHILDFLG_Y);
			this.updateCmpStudyKind(parent);
			cmpOrgStudyKind.setKlevel(parent.getKlevel() + 1);
		}
		else {
			cmpOrgStudyKind.setKlevel(1);
		}
		long kindId = query.insertObject(cmpOrgStudyKind).longValue();
		cmpOrgStudyKind.setKindId(kindId);
	}

	public void deleteCmpStudyKind(long companyId, long kindId) {
		Query query = this.manager.createQuery();
		// 要删除的数据
		CmpStudyKind cmpOrgStudyKind = this.getCmpStudyKind(companyId, kindId);
		if (cmpOrgStudyKind == null) {
			return;
		}
		// 查看是否有子类
		int count = query.count(CmpStudyKind.class,
				"companyid=? and parentid=?",
				new Object[] { companyId, kindId });
		if (count > 0) {// 存在子类，不能删除
			return;
		}
		// 删除数据
		query.delete(CmpStudyKind.class, "companyid=? and kindid=?",
				new Object[] { companyId, kindId });
		if (cmpOrgStudyKind.getParentId() > 0) {
			// 获取父类
			CmpStudyKind parent = this.getCmpStudyKind(companyId,
					cmpOrgStudyKind.getParentId());
			if (parent == null) {
				return;
			}
			count = query.count(CmpStudyKind.class,
					"companyid=? and parentid=?", new Object[] { companyId,
							parent.getKindId() });
			// 如果父类还有子类
			if (count > 0) {
				parent.setChildflg(CmpStudyKind.CHILDFLG_Y);
			}
			else {
				parent.setChildflg(CmpStudyKind.CHILDFLG_N);
			}
			this.updateCmpStudyKind(parent);
		}
	}

	public CmpStudyKind getCmpStudyKind(long companyId, long kindId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpStudyKind.class,
				"companyid=? and kindid=?", new Object[] { companyId, kindId });
	}

	public List<CmpStudyKind> getCmpStudyKindListByCompanyIdAndParentIdEx(
			long companyId, long parentId, String name, int begin, int size) {
		StringBuilder sb = new StringBuilder("companyid=? and parentid=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(companyId);
		olist.add(parentId);
		if (!DataUtil.isEmpty(name)) {
			sb.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		Query query = this.manager.createQuery();
		return query.listExParamList(CmpStudyKind.class, sb.toString(), olist,
				"kindid asc", begin, size);
	}

	public int countCmpStudyKindByCompanyIdAndParentIdEx(long companyId,
			long parentId, String name) {
		StringBuilder sb = new StringBuilder("companyid=? and parentid=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(companyId);
		olist.add(parentId);
		if (!DataUtil.isEmpty(name)) {
			sb.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		Query query = this.manager.createQuery();
		return query.countExParamList(CmpStudyKind.class, sb.toString(), olist);
	}

	public void updateCmpStudyKind(CmpStudyKind cmpOrgStudyKind) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpOrgStudyKind);
	}

	public Map<Long, CmpStudyKind> getCmpStudyKindByCompanyIdInId(
			long companyId, List<Long> idList) {
		Query query = this.manager.createQuery();
		List<CmpStudyKind> list = query.listInField(CmpStudyKind.class,
				"companyid=?", new Object[] { companyId }, "kindid", idList,
				null);
		Map<Long, CmpStudyKind> map = new HashMap<Long, CmpStudyKind>();
		for (CmpStudyKind o : list) {
			map.put(o.getKindId(), o);
		}
		return map;
	}
}