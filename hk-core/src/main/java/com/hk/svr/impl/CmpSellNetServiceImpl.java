package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpSellNet;
import com.hk.bean.CmpSellNetKind;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpSellNetService;

public class CmpSellNetServiceImpl implements CmpSellNetService {

	@Autowired
	private QueryManager manager;

	public void createCmpSellNet(CmpSellNet cmpSellNet) {
		Query query = this.manager.createQuery();
		long oid = query.insertObject(cmpSellNet).longValue();
		cmpSellNet.setOid(oid);
	}

	public void deleteCmpSellNet(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpSellNet.class, oid);
	}

	public CmpSellNet getCmpSellNet(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpSellNet.class, oid);
	}

	public List<CmpSellNet> getCmpSellNetListByCompanyId(long companyId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpSellNet.class, "companyid=?",
				new Object[] { companyId }, "orderflg desc", begin, size);
	}

	public List<CmpSellNet> getCmpSellNetListByCompanyId(long companyId,
			long kindId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpSellNet.class, "companyid=? and kindid=?",
				new Object[] { companyId, kindId }, "orderflg desc", begin,
				size);
	}

	public void updateCmpSellNet(CmpSellNet cmpSellNet) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpSellNet);
	}

	public void updateCmpSellNetOrderflg(long oid, int orderflg) {
		Query query = this.manager.createQuery();
		query.addField("orderflg", orderflg);
		query.updateById(CmpSellNet.class, oid);
	}

	public void createCmpSellNetKind(CmpSellNetKind cmpSellNetKind) {
		Query query = this.manager.createQuery();
		query.insertObject(cmpSellNetKind);
	}

	public void deleteCmpSellNetKind(long kindId) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpSellNetKind.class, kindId);
	}

	public List<CmpSellNetKind> getCmpSellNetKindListByCompanyId(long companyId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpSellNetKind.class, "kindid asc");
	}

	public void updateCmpSellNetKind(CmpSellNetKind cmpSellNetKind) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpSellNetKind);
	}

	public CmpSellNetKind getCmpSellNetKind(long kindId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpSellNetKind.class, kindId);
	}

	public Map<Long, CmpSellNetKind> getCmpSellNetKindMapInId(List<Long> idList) {
		Map<Long, CmpSellNetKind> map = new HashMap<Long, CmpSellNetKind>();
		List<CmpSellNetKind> list = this.getCmpSellNetKindListInId(idList);
		for (CmpSellNetKind o : list) {
			map.put(o.getKindId(), o);
		}
		return map;
	}

	private List<CmpSellNetKind> getCmpSellNetKindListInId(List<Long> idList) {
		Query query = this.manager.createQuery();
		return query.listInField(CmpSellNetKind.class, null, null, "kindid",
				idList, null);
	}

	public List<CmpSellNet> getCmpSellNetListByCompanyIdEx(long companyId,
			String name, long kindId, int begin, int size) {
		List<Object> olist = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(
				"select * from cmpsellnet where companyid=?");
		olist.add(companyId);
		if (!DataUtil.isEmpty(name)) {
			sql.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		if (kindId > 0) {
			sql.append(" and kindid=?");
			olist.add(kindId);
		}
		sql.append(" order by orderflg desc");
		Query query = this.manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				CmpSellNet.class, olist);
	}
}