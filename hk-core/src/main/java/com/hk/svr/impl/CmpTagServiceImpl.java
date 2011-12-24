package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpTag;
import com.hk.bean.CmpTagRef;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpTagService;

public class CmpTagServiceImpl implements CmpTagService {
	@Autowired
	private QueryManager manager;

	public boolean createCmpTag(CmpTag cmpTag, long companyId, long userId,
			int pcityId) {
		Query query = manager.createQuery();
		CmpTag tag = query.getObjectEx(CmpTag.class, "name=?",
				new Object[] { cmpTag.getName() });
		long tagId = 0;
		if (tag == null) {
			tagId = query.insertObject(cmpTag).longValue();
			cmpTag.setTagId(tagId);
			tag = cmpTag;
		}
		else {
			tagId = tag.getTagId();
		}
		if (query.count(CmpTagRef.class, "companyid=? and tagid=?",
				new Object[] { companyId, tagId }) == 0) {
			CmpTagRef ref = new CmpTagRef();
			ref.setCompanyId(companyId);
			ref.setTagId(tagId);
			ref.setUserId(userId);
			ref.setName(tag.getName());
			ref.setPcityId(pcityId);
			query.insertObject(ref);
			return true;
		}
		return false;
	}

	public void deleteCmpTagRef(long oid) {
		Query query = manager.createQuery();
		query.deleteById(CmpTagRef.class, oid);
	}

	public void deleteCmpTagRefByCompanyIdAndTagId(long companyId, long tagId) {
		Query query = manager.createQuery();
		query.delete(CmpTagRef.class, "companyid=? and tagid=?", new Object[] {
				companyId, tagId });
	}

	public CmpTagRef getCmpTagRefByCompanyIdAndTagId(long companyId, long tagId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpTagRef.class, "companyid=? and tagid=?",
				new Object[] { companyId, tagId });
	}

	public List<CmpTagRef> getCmpTagRefListByCompanyId(long companyId,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpTagRef.class, "companyid=?",
				new Object[] { companyId }, "oid desc", begin, size);
	}

	public CmpTagRef getCmpTagRef(long oid) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpTagRef.class, oid);
	}

	public CmpTag getCmpTagByName(String name) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpTag.class, "name=?", new Object[] { name });
	}

	public List<CmpTagRef> getCmpTagRefListByTagId(long tagId, int pcityId,
			int begin, int size) {
		Query query = manager.createQuery();
		if (pcityId > 0) {
			return query.listEx(CmpTagRef.class, "tagid=? and pcityid=?",
					new Object[] { tagId, pcityId }, "oid desc", begin, size);
		}
		return query.listEx(CmpTagRef.class, "tagid=?", new Object[] { tagId },
				"oid desc", begin, size);
	}

	public CmpTag getCmpTag(long tagId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpTag.class, tagId);
	}

	public List<CmpTagRef> getCmpTagRefListByPcityIdAndNameLike(int pcityId,
			String name, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpTagRef.class, "pcityid=? and name like ?",
				new Object[] { pcityId, "%" + name + "%" }, "oid desc", begin,
				size);
	}
}