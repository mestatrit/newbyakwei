package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.HkObjKeyTagOrderDef;
import com.hk.bean.HkObjOrderDef;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.OrderDefService;

public class OrderDefServiceImpl implements OrderDefService {
	@Autowired
	private QueryManager manager;

	public HkObjKeyTagOrderDef getHkObjKeyTagOrderDef(long tagId, int cityId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(HkObjKeyTagOrderDef.class,
				"tagid=? and cityid=?", new Object[] { tagId, cityId });
	}

	public HkObjOrderDef getHkObjOrderDef(byte kind, int kdinId, int cityId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(HkObjOrderDef.class,
				"kind=? and kindid=? and cityid=?", new Object[] { kind,
						kdinId, cityId });
	}

	public void updateHkObjOrderDef(HkObjOrderDef hkObjOrderDef) {
		HkObjOrderDef o = this.getHkObjOrderDef(hkObjOrderDef.getKind(),
				hkObjOrderDef.getKindId(), hkObjOrderDef.getCityId());
		Query query = manager.createQuery();
		if (o != null) { // update
			o.setMoney(hkObjOrderDef.getMoney());
			query.addField("kind", o.getKind());
			query.addField("kindid", o.getKindId());
			query.addField("cityid", o.getCityId());
			query.addField("money", o.getMoney());
			query.update(HkObjOrderDef.class, "oid=?", new Object[] { o
					.getOid() });
			return;
		}
		query.addField("kind", hkObjOrderDef.getKind());
		query.addField("kindid", hkObjOrderDef.getKindId());
		query.addField("cityid", hkObjOrderDef.getCityId());
		query.addField("money", hkObjOrderDef.getMoney());
		query.insert(HkObjOrderDef.class);
	}

	public void deleteHkObjKeyTagOrderDef(long oid) {
		Query query = manager.createQuery();
		query.deleteById(HkObjKeyTagOrderDef.class, oid);
	}

	public void deleteHkObjOrderDef(int oid) {
		Query query = manager.createQuery();
		query.deleteById(HkObjOrderDef.class, oid);
	}

	public List<HkObjKeyTagOrderDef> getHkObjKeyTagOrderDefList(long tagId,
			int cityId, int begin, int size) {
		StringBuilder sql = new StringBuilder(
				"select * from hkobjkeytagorderdef where 1=1");
		List<Object> olist = new ArrayList<Object>();
		if (tagId > 0) {
			sql.append(" and tagid=?");
			olist.add(tagId);
		}
		if (cityId > -1) {
			sql.append(" and cityid=?");
			olist.add(cityId);
		}
		sql.append(" order by oid desc");
		Query query = manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				HkObjKeyTagOrderDef.class, olist);
	}

	public List<HkObjOrderDef> getHkObjOrderDefList(int kind, int kindId,
			int cityId, int begin, int size) {
		StringBuilder sql = new StringBuilder(
				"select * from hkobjkeytagorderdef where 1=1");
		List<Object> olist = new ArrayList<Object>();
		if (kind > 0) {
			sql.append(" and kind=?");
			olist.add(kind);
		}
		if (kindId > 0) {
			sql.append(" and kindid=?");
			olist.add(kindId);
		}
		if (cityId > -1) {
			sql.append(" and cityid=?");
			olist.add(cityId);
		}
		sql.append(" order by oid desc");
		Query query = manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				HkObjOrderDef.class, olist);
	}

	public void updateHkObjKeyTagOrderDef(
			HkObjKeyTagOrderDef hkObjKeyTagOrderDef) {
		HkObjKeyTagOrderDef o = this.getHkObjKeyTagOrderDef(hkObjKeyTagOrderDef
				.getTagId(), hkObjKeyTagOrderDef.getCityId());
		Query query = manager.createQuery();
		if (o != null) {
			query.addField("tagid", o.getTagId());
			query.addField("cityid", o.getCityId());
			query.addField("money", hkObjKeyTagOrderDef.getMoney());
			query.update(HkObjKeyTagOrderDef.class, "oid=?", new Object[] { o
					.getOid() });
		}
		else {
			query.addField("tagid", hkObjKeyTagOrderDef.getTagId());
			query.addField("cityid", hkObjKeyTagOrderDef.getCityId());
			query.addField("money", hkObjKeyTagOrderDef.getMoney());
			query.insert(HkObjKeyTagOrderDef.class);
		}
	}
}