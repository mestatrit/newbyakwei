package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.HkObj;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.HkObjService;

public class HkObjServiceImpl implements HkObjService {
	@Autowired
	private QueryManager manager;

	public HkObj getHkObj(long objId) {
		Query query = manager.createQuery();
		return query.getObjectById(HkObj.class, objId);
	}

	public List<HkObj> getHkObjListInId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<HkObj>();
		}
		StringBuilder sql = new StringBuilder(
				"select * from hkobj where objid in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		Query query = manager.createQuery();
		return query.listBySqlEx("ds1", sql.toString(), HkObj.class);
	}

	public Map<Long, HkObj> getHkObjMapInId(List<Long> idList) {
		List<HkObj> list = this.getHkObjListInId(idList);
		Map<Long, HkObj> map = new HashMap<Long, HkObj>();
		for (HkObj o : list) {
			map.put(o.getObjId(), o);
		}
		return map;
	}
}