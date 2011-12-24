package com.hk.svr.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpArticleTag;
import com.hk.bean.CmpArticleTagRef;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpArticleTagService;

public class CmpArticleTagServiceImpl implements CmpArticleTagService {

	@Autowired
	private QueryManager manager;

	public boolean createCmpArticleTag(CmpArticleTag cmpArticleTag) {
		if (cmpArticleTag.getPinktime() == null) {
			cmpArticleTag.setPinktime(new Date());
		}
		Query query = this.manager.createQuery();
		if (query.count(CmpArticleTag.class, "companyid=? and name=?",
				new Object[] { cmpArticleTag.getCompanyId(),
						cmpArticleTag.getName() }) > 0) {
			return false;
		}
		long tagId = query.insertObject(cmpArticleTag).longValue();
		cmpArticleTag.setTagId(tagId);
		return true;
	}

	public void deleteCmpArticleTag(long companyId, long tagId) {
		Query query = this.manager.createQuery();
		query.delete(CmpArticleTagRef.class, "companyid=? and tagid=?",
				new Object[] { companyId, tagId });
		query.delete(CmpArticleTag.class, "companyid=? and tagid=?",
				new Object[] { companyId, tagId });
	}

	public CmpArticleTag getCmpArticleTag(long companyId, long tagId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpArticleTag.class,
				"companyid=? and tagid=?", new Object[] { companyId, tagId });
	}

	public List<CmpArticleTagRef> getCmpArticleTagRefListByCompanyIdAndTagId(
			long companyId, long tagId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpArticleTagRef.class, "companyid=? and tagid=?",
				new Object[] { companyId, tagId }, "articleid desc", begin,
				size);
	}

	public int countCmpArticleTagRefByCompanyIdAndTagId(long companyId,
			long tagId) {
		Query query = this.manager.createQuery();
		return query.count(CmpArticleTagRef.class, "companyid=? and tagid=?",
				new Object[] { companyId, tagId });
	}

	public Map<Long, CmpArticleTag> getCmpArticleTagMapByCompanyIdInId(
			long companyId, List<Long> idList) {
		Query query = this.manager.createQuery();
		List<CmpArticleTag> list = query.listInField(CmpArticleTag.class,
				"companyid=?", new Object[] { companyId }, "tagid", idList,
				null);
		Map<Long, CmpArticleTag> map = new HashMap<Long, CmpArticleTag>();
		for (CmpArticleTag o : list) {
			map.put(o.getTagId(), o);
		}
		return map;
	}

	public List<CmpArticleTagRef> getCmpArticleTagRefListByCompanyIdAndArticleId(
			long companyId, long articleId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpArticleTagRef.class,
				"companyid=? and articleid=?", new Object[] { companyId,
						articleId }, "oid asc", begin, size);
	}

	public void deleteCmpArticleTagRefByCompanyIdAndArticleId(long companyId,
			long articleId) {
		Query query = this.manager.createQuery();
		query.delete(CmpArticleTagRef.class, "companyid=? and articleid=?",
				new Object[] { companyId, articleId });
	}

	public void deleteCmpArticleTagRefByCompanyIdAndOid(long companyId, long oid) {
		Query query = this.manager.createQuery();
		query.delete(CmpArticleTagRef.class, "companyid=? and oid=?",
				new Object[] { companyId, oid });
	}

	public boolean createCmpArticleTagRef(CmpArticleTagRef cmpArticleTagRef) {
		Query query = this.manager.createQuery();
		if (query.count(CmpArticleTagRef.class,
				"companyid=? and tagid=? and articleid=?", new Object[] {
						cmpArticleTagRef.getCompanyId(),
						cmpArticleTagRef.getTagId(),
						cmpArticleTagRef.getArticleId() }) > 0) {
			return false;
		}
		long oid = query.insertObject(cmpArticleTagRef).longValue();
		cmpArticleTagRef.setOid(oid);
		return true;
	}

	public CmpArticleTag getCmpArticleTagByCompanyIdAndName(long companyId,
			String name) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpArticleTag.class, "companyid=? and name=?",
				new Object[] { companyId, name });
	}

	public List<CmpArticleTag> getCmpArticleTagListByCompanyId(long companyId,
			String name, int begin, int size) {
		Query query = this.manager.createQuery();
		if (DataUtil.isEmpty(name)) {
			return query.listEx(CmpArticleTag.class, "companyid=?",
					new Object[] { companyId }, "tagid desc", begin, size);
		}
		return query.listEx(CmpArticleTag.class, "companyid=? and name like ?",
				new Object[] { companyId, "%" + name + "%" }, "tagid desc",
				begin, size);
	}

	public void updateCmpArticleTagPinkflg(long companyId, long tagId,
			byte pinkflg) {
		Query query = this.manager.createQuery();
		query.addField("pinkflg", pinkflg);
		query.addField("pinktime", new Date());
		query.update(CmpArticleTag.class, "companyid=? and tagid=?",
				new Object[] { companyId, tagId });
	}

	public List<CmpArticleTag> getCmpArticleTagListByCompanyIdForPink(
			long companyId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpArticleTag.class, "companyid=? and pinkflg=?",
				new Object[] { companyId, CmpArticleTag.PINKFLG_Y },
				"pinktime desc", begin, size);
	}
}