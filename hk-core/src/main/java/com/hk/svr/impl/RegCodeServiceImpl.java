package com.hk.svr.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.RegCode;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.RegCodeService;
import com.hk.svr.user.exception.RegCodeNameDuplicateException;

public class RegCodeServiceImpl implements RegCodeService {
	@Autowired
	private QueryManager manager;

	public synchronized void createRegCode(RegCode regCode)
			throws RegCodeNameDuplicateException {
		regCode.setName(regCode.getName().toLowerCase());
		Query query = manager.createQuery();
		RegCode o = query.getObjectEx(RegCode.class, "name=?",
				new Object[] { regCode.getName() });
		if (o != null && o.getObjId() > 0) {
			throw new RegCodeNameDuplicateException("name [ "
					+ regCode.getName() + " ] in use");
		}
		if (o == null) {// 如果没有就创建
			regCode.setCreateTime(new Date());
			query.addField("objid", regCode.getObjId());
			query.addField("name", regCode.getName());
			query.addField("createtime", regCode.getCreateTime());
			query.addField("objtype", regCode.getObjType());
			long id = query.insert(RegCode.class).longValue();
			regCode.setCodeId(id);
		}
		else {// 更新现有的regcode objid
			o.setObjId(regCode.getObjId());
			o.setObjType(regCode.getObjType());
			query.addField("objid", o.getObjId());
			query.addField("objtype", o.getObjType());
			query.update(RegCode.class, "codeid=?", new Object[] { o
					.getCodeId() });
		}
	}

	public RegCode getRegCodeByUserId(long userId) {
		Query query = manager.createQuery();
		return query.getObjectEx(RegCode.class, "objid=? and objtype=?",
				new Object[] { userId, RegCode.OBJTYPE_USER });
	}

	public RegCode getRegCode(long codeId) {
		Query query = manager.createQuery();
		return query.getObjectById(RegCode.class, codeId);
	}

	public List<RegCode> getRegCodeList(int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(RegCode.class, "objid=?", new Object[] { 0 },
				begin, size);
	}

	public int countNoUseRegCode() {
		Query query = manager.createQuery();
		query.setTable(RegCode.class);
		query.where("objid=?").setParam(0);
		return query.count();
	}

	public RegCode getRegCodeByName(String name) {
		Query query = manager.createQuery();
		return query
				.getObjectEx(RegCode.class, "name=?", new Object[] { name });
	}
}