package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.HkObjKeyTag;
import com.hk.bean.KeyTag;
import com.hk.bean.KeyTagSearchInfo;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.KeyTagService;

public class KeyTagServiceImpl implements KeyTagService {
	@Autowired
	private QueryManager manager;

	public boolean createHkObjKeyTag(long tagId, long objId) {
		Query query = manager.createQuery();
		if (query.count(HkObjKeyTag.class, "tagid=? and hkobjid=?",
				new Object[] { tagId, objId }) > 0) {
			return false;
		}
		query.addField("tagid", tagId);
		query.addField("hkobjid", objId);
		query.insert(HkObjKeyTag.class);
		return true;
	}

	public long createKeyTag(String name) {
		KeyTag o = this.getKeyTagByName(name);
		if (o != null) {
			return o.getTagId();
		}
		Query query = manager.createQuery();
		query.addField("name", name);
		query.addField("searchcount", 0);
		long id = query.insert(KeyTag.class).longValue();
		return id;
	}

	public KeyTag getKeyTag(long tagId) {
		Query query = manager.createQuery();
		return query.getObjectById(KeyTag.class, tagId);
	}

	public KeyTag getKeyTagByName(String name) {
		Query query = manager.createQuery();
		return query.getObjectEx(KeyTag.class, "name=?", new Object[] { name });
	}

	public KeyTagSearchInfo getKeyTagSearchInfoByYearAndMonth(long tagId,
			int year, int month) {
		Query query = manager.createQuery();
		return query.getObjectEx(KeyTagSearchInfo.class,
				"tagid=? and year=? and month=?", new Object[] { tagId, year,
						month });
	}

	public List<KeyTag> getKeyTagListInId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<KeyTag>();
		}
		StringBuilder sql = new StringBuilder(
				"select * from keytag where tagid in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		Query query = manager.createQuery();
		return query.listBySqlEx("ds1", sql.toString(), KeyTag.class);
	}

	public Map<Long, KeyTag> getKeyTagMapInId(List<Long> idList) {
		List<KeyTag> list = this.getKeyTagListInId(idList);
		Map<Long, KeyTag> map = new HashMap<Long, KeyTag>();
		for (KeyTag o : list) {
			map.put(o.getTagId(), o);
		}
		return map;
	}
}