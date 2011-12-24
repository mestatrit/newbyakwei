package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpAdminGroup;
import com.hk.bean.CmpAdminGroupRef;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpAdminGroupService;

public class CmpAdminGroupServiceImpl implements CmpAdminGroupService {
	@Autowired
	private QueryManager manager;

	public boolean createCmpAdminGroup(CmpAdminGroup cmpAdminGroup) {
		Query query = this.manager.createQuery();
		if (query.count(CmpAdminGroup.class, "name=?",
				new Object[] { cmpAdminGroup.getName() }) > 0) {
			return false;
		}
		cmpAdminGroup.setGroupId(query.insertObject(cmpAdminGroup).longValue());
		return true;
	}

	public void createCmpAdminGroupRef(long groupId, long companyId) {
		Query query = this.manager.createQuery();
		if (query.count(CmpAdminGroupRef.class, "groupid=? and companyid=?",
				new Object[] { groupId, companyId }) == 0) {
			CmpAdminGroupRef ref = new CmpAdminGroupRef();
			ref.setCompanyId(companyId);
			ref.setGroupId(groupId);
			query.insertObject(ref);
		}
	}

	public void deleteCmpAdminGroup(long groupId) {
		Query query = this.manager.createQuery();
		query.delete(CmpAdminGroupRef.class, "groupid=?",
				new Object[] { groupId });
		query.deleteById(CmpAdminGroup.class, groupId);
	}

	public void deleteCmpAdminGroupRef(long groupId, long companyId) {
		Query query = this.manager.createQuery();
		query.delete(CmpAdminGroupRef.class, "groupid=? and companyid=?",
				new Object[] { groupId, companyId });
	}

	public List<CmpAdminGroup> getCmpAdminGroupList(String name, int begin,
			int size) {
		Query query = this.manager.createQuery();
		if (DataUtil.isEmpty(name)) {
			return query.listEx(CmpAdminGroup.class, "groupid desc", begin,
					size);
		}
		return query.listEx(CmpAdminGroup.class, "name like ?",
				new Object[] { "%" + name + "%" }, "groupid desc", begin, size);
	}

	public List<CmpAdminGroupRef> getCmpAdminGroupRefListByGroupId(
			long groupId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpAdminGroupRef.class, "groupid=?",
				new Object[] { groupId }, "oid desc", begin, size);
	}

	public CmpAdminGroup getCmpAdminGroup(long groupId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpAdminGroup.class, groupId);
	}

	public boolean updateCmpAdminGroup(CmpAdminGroup cmpAdminGroup) {
		Query query = this.manager.createQuery();
		CmpAdminGroup o = query.getObjectEx(CmpAdminGroup.class, "name=?",
				new Object[] { cmpAdminGroup.getName() });
		if (o != null && o.getGroupId() != cmpAdminGroup.getGroupId()) {
			return false;
		}
		query.updateObject(cmpAdminGroup);
		return true;
	}

	public List<CmpAdminGroup> getCmpAdminGroupListInIdList(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<CmpAdminGroup>();
		}
		StringBuilder sql = new StringBuilder(
				"select * from cmpadmingroup where groupid in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		Query query = this.manager.createQuery();
		return query.listBySqlEx("ds1", sql.toString(), CmpAdminGroup.class);
	}

	public Map<Long, CmpAdminGroup> getCmpAdminGroupMapInIdList(
			List<Long> idList) {
		List<CmpAdminGroup> list = getCmpAdminGroupListInIdList(idList);
		Map<Long, CmpAdminGroup> map = new HashMap<Long, CmpAdminGroup>();
		for (CmpAdminGroup o : list) {
			map.put(o.getGroupId(), o);
		}
		return map;
	}

	public List<CmpAdminGroupRef> getCmpAdminGroupRefListByCompanyId(
			long companyId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpAdminGroupRef.class, "companyid=?",
				new Object[] { companyId });
	}
}