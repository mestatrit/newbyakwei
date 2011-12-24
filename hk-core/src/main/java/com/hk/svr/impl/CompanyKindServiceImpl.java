package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.CmpChildKind;
import com.hk.bean.CmpChildKindRef;
import com.hk.bean.Company;
import com.hk.bean.CompanyKind;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CompanyKindService;

public class CompanyKindServiceImpl implements CompanyKindService {

	@Autowired
	private QueryManager manager;

	public boolean createCmpChildKind(CmpChildKind cmpChildKind) {
		Query query = manager.createQuery();
		if (query.getObjectEx(CmpChildKind.class, "name=?",
				new Object[] { cmpChildKind.getName() }) != null) {
			return false;
		}
		query.addField("name", cmpChildKind.getName());
		query.addField("kindid", cmpChildKind.getKindId());
		query.addField("cmpcount", cmpChildKind.getCmpCount());
		int oid = query.insert(CmpChildKind.class).intValue();
		cmpChildKind.setOid(oid);
		return true;
	}

	public void deleteCmpChildKind(int oid) {
		Query query = manager.createQuery();
		query.deleteById(CmpChildKind.class, oid);
		this.deleteCmpChildKindRefByOid(oid);
	}

	public List<CmpChildKind> getCmpChildKindList(int kindId, int order_type,
			int begin, int size) {
		StringBuilder sql = new StringBuilder(
				"select * from cmpchildkind where 1=1");
		List<Object> olist = new ArrayList<Object>();
		if (kindId > 0) {
			sql.append(" and kindid=?");
			olist.add(kindId);
		}
		if (order_type == 0) {
			sql.append(" order by oid desc");
		}
		else if (order_type == 1) {
			sql.append(" order by cmpcount desc");
		}
		Query query = manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				CmpChildKind.class, olist);
	}

	public boolean updateCmpChildKind(CmpChildKind cmpChildKind) {
		Query query = manager.createQuery();
		CmpChildKind o = query.getObjectEx(CmpChildKind.class, "name=?",
				new Object[] { cmpChildKind.getName() });
		if (o != null && o.getOid() != cmpChildKind.getOid()) {// 存在重名，不能修改数据
			return false;
		}
		query.addField("name", cmpChildKind.getName());
		query.addField("kindid", cmpChildKind.getKindId());
		query.addField("cmpcount", cmpChildKind.getCmpCount());
		query.updateById(CmpChildKind.class, cmpChildKind.getOid());
		return true;
	}

	public CmpChildKind getCmpChildKind(int oid) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpChildKind.class, oid);
	}

	public boolean createCmpChildKindRef(int oid, long companyId, int cityId) {
		Query query = manager.createQuery();
		query.delete(CmpChildKindRef.class, "companyid=?",
				new Object[] { companyId });
		query.addField("oid", oid);
		query.addField("companyid", companyId);
		query.addField("cityid", cityId);
		query.insert(CmpChildKindRef.class);
		// 更新小分类的足迹数量
		this.updateCmpChildKindCmpCount(oid);
		return true;
	}

	private void updateCmpChildKindCmpCount(int oid) {
		Query query = manager.createQuery();
		int count = query.count(CmpChildKindRef.class, "oid=?",
				new Object[] { oid });
		query.addField("cmpcount", count);
		query.updateById(CmpChildKind.class, oid);
	}

	public void deleteCmpChildKindRef(int oid, long companyId) {
		Query query = manager.createQuery();
		query.delete(CmpChildKindRef.class, "oid=? and companyid=?",
				new Object[] { oid, companyId });
		// 更新小分类的足迹数量
	}

	public void deleteCmpChildKindRefByOid(int oid) {
		Query query = manager.createQuery();
		query.delete(CmpChildKindRef.class, "oid=?", new Object[] { oid });
	}

	public List<CmpChildKindRef> getCmpChildKindRefListByOid(int oid,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpChildKindRef.class, "oid=?",
				new Object[] { oid }, "companyid desc", begin, size);
	}

	// public void updateCmpCount(int oid, int count) {
	// Query query = manager.createQuery();
	// query.addField("cmpcount", count);
	// query.updateById(CmpChildKind.class, oid);
	// }
	public void deleteCmpChildKindRefByCompanyId(long companyId) {
		Query query = manager.createQuery();
		query.delete(CmpChildKindRef.class, "companyid=?",
				new Object[] { companyId });
	}

	public boolean createCompanyKind(CompanyKind companyKind) {
		Query query = manager.createQuery();
		if (query
				.count(CompanyKind.class, "name=? and parentid=?",
						new Object[] { companyKind.getName(),
								companyKind.getParentId() }) > 0) {
			return false;
		}
		query.addField("kindid", companyKind.getKindId());
		query.addField("name", companyKind.getName());
		query.addField("pricetip", companyKind.getPriceTip());
		query.addField("parentid", companyKind.getParentId());
		query.addField("cmpcount", companyKind.getCmpCount());
		query.addField("orderflg", companyKind.getOrderflg());
		query.insert(CompanyKind.class);
		return true;
	}

	public void deleteCompanyKind(int kindId) {
		Query query = manager.createQuery();
		query.deleteById(CompanyKind.class, kindId);
	}

	public List<CompanyKind> getCompanyKindList(int parentId) {
		Query query = manager.createQuery();
		if (parentId > 0) {
			return query.listEx(CompanyKind.class, "parentid=?",
					new Object[] { parentId }, "orderflg asc,kindid asc");
		}
		return query.listEx(CompanyKind.class, "orderflg asc,kindid asc");
	}

	public List<CmpChildKind> getCmpChildKindList(int kindId) {
		Query query = manager.createQuery();
		if (kindId > 0) {
			return query.listEx(CmpChildKind.class, "kindid=?",
					new Object[] { kindId }, "oid desc");
		}
		return query.listEx(CmpChildKind.class, "oid desc");
	}

	public CompanyKind getCompanyKind(int kindId) {
		Query query = manager.createQuery();
		return query.getObjectById(CompanyKind.class, kindId);
	}

	public boolean updateCompanyKind(CompanyKind companyKind) {
		Query query = manager.createQuery();
		CompanyKind o = query.getObjectEx(CompanyKind.class,
				"name=? and parentid=?", new Object[] { companyKind.getName(),
						companyKind.getParentId() });
		if (o != null && o.getKindId() != companyKind.getKindId()) {
			return false;
		}
		query.addField("name", companyKind.getName());
		query.addField("pricetip", companyKind.getPriceTip());
		query.addField("parentid", companyKind.getParentId());
		query.addField("cmpcount", companyKind.getCmpCount());
		query.addField("orderflg", companyKind.getOrderflg());
		query.updateById(CompanyKind.class, companyKind.getKindId());
		return true;
	}

	public void updateCompanyKindCmpCount(int kindId) {
		Query query = manager.createQuery();
		int count = query.count(Company.class, "kindid=?",
				new Object[] { kindId });
		query.addField("cmpcount", count);
		query.updateById(CompanyKind.class, kindId);
	}

	public CmpChildKindRef getCmpChildKindRefByCompanyId(long companyId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpChildKindRef.class, "companyid=?",
				new Object[] { companyId });
	}

	public int countCmpChildKindRef(int oid, int cityId) {
		Query query = manager.createQuery();
		return query.count(CmpChildKindRef.class, "oid=? and cityid=?",
				new Object[] { oid, cityId });
	}

	public List<CmpChildKindRef> getCmpChildKindRefList(int oid, int cityId,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpChildKindRef.class, "oid=? and cityid=?",
				new Object[] { oid, cityId }, "companyid desc", begin, size);
	}
}