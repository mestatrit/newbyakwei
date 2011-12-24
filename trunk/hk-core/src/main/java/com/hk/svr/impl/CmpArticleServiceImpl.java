package com.hk.svr.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpArticle;
import com.hk.bean.CmpArticleContent;
import com.hk.bean.CmpArticleGroup;
import com.hk.bean.CmpArticleNavPink;
import com.hk.bean.CmpUtil;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpArticleService;

public class CmpArticleServiceImpl implements CmpArticleService {

	@Autowired
	private QueryManager manager;

	public void createCmpArticle(CmpArticle cmpArticle,
			CmpArticleContent cmpArticleContent) {
		if (cmpArticle.getCreateTime() == null) {
			cmpArticle.setCreateTime(new Date());
		}
		cmpArticle.setCmppinkTime(cmpArticle.getCreateTime());
		Query query = this.manager.createQuery();
		long oid = query.insertObject(cmpArticle).longValue();
		cmpArticle.setOid(oid);
		cmpArticleContent.setOid(oid);
		query.insertObject(cmpArticleContent);
	}

	public void updateCmpArticle(CmpArticle cmpArticle,
			CmpArticleContent cmpArticleContent) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpArticle);
		if (cmpArticleContent != null) {
			query.updateObject(cmpArticleContent);
		}
	}

	public void deleteCmpArticle(long companyId, long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpArticle.class, oid);
		query.deleteById(CmpArticleContent.class, oid);
		query.delete(CmpArticleNavPink.class, "companyid=? and articleid=?",
				new Object[] { companyId, oid });
	}

	public CmpArticle getCmpArticle(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpArticle.class, oid);
	}

	public CmpArticleContent getCmpArticleContent(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpArticleContent.class, oid);
	}

	public List<CmpArticle> getCmpArticleListByCompanyIdAndCmpNavOid(
			long companyId, long cmpNavOid, String title, int begin, int size) {
		Query query = this.manager.createQuery();
		if (!DataUtil.isEmpty(title)) {
			return query.listEx(CmpArticle.class,
					"companyId=? and cmpnavoid=? and title like ?",
					new Object[] { companyId, cmpNavOid, "%" + title + "%" },
					"orderflg desc,oid desc", begin, size);
		}
		return query.listEx(CmpArticle.class, "companyId=? and cmpnavoid=?",
				new Object[] { companyId, cmpNavOid },
				"orderflg desc,oid desc", begin, size);
	}

	public int countCmpArticleListByCompanyIdAndCmpNavOid(long companyId,
			long cmpNavOid, String title) {
		Query query = this.manager.createQuery();
		if (!DataUtil.isEmpty(title)) {
			return query.count(CmpArticle.class,
					"companyId=? and cmpnavoid=? and title like ?",
					new Object[] { companyId, cmpNavOid, "%" + title + "%" });
		}
		return query.count(CmpArticle.class, "companyId=? and cmpnavoid=?",
				new Object[] { companyId, cmpNavOid });
	}

	public List<CmpArticle> getCmpArticleListByCompanyIdAndCmpNavOidAndGroupId(
			long companyId, long cmpNavOid, long groupId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpArticle.class,
				"companyId=? and cmpnavoid=? and groupid=?", new Object[] {
						companyId, cmpNavOid, groupId },
				"orderflg desc,oid desc", begin, size);
	}

	public List<CmpArticle> getCmpArticleListByCompanyIdAndGroupId(
			long companyId, long groupId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpArticle.class, "companyId=? and groupid=?",
				new Object[] { companyId, groupId }, "orderflg desc,oid desc",
				begin, size);
	}

	public List<CmpArticle> getCmpArticleListByCompanyId(long companyId,
			String title, int begin, int size) {
		Query query = this.manager.createQuery();
		if (!DataUtil.isEmpty(title)) {
			return query.listEx(CmpArticle.class,
					"companyId=? and title like ?", new Object[] { companyId,
							"%" + title + "%" }, "oid desc", begin, size);
		}
		return query.listEx(CmpArticle.class, "companyId=?",
				new Object[] { companyId }, "oid desc", begin, size);
	}

	public List<CmpArticle> getCmpArticleListByCompanyIdAndCmpNavOidForCmppink(
			long companyId, long cmpNavOid, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpArticle.class,
				"companyId=? and cmpnavoid=? and cmppink=?", new Object[] {
						companyId, cmpNavOid, CmpUtil.CMPPINK_Y },
				"cmppinktime desc", begin, size);
	}

	public void setCmpArticleCmppink(long oid, byte cmppink) {
		Query query = this.manager.createQuery();
		query.addField("cmppink", cmppink);
		query.addField("cmppinktime", new Date());
		query.updateById(CmpArticle.class, oid);
	}

	public void setCmpArticleUnknown(long companyId, long cmpNavOid) {
		Query query = this.manager.createQuery();
		query.addField("cmpnavoid", 0);
		query.addField("cmppink", 0);
		query.update(CmpArticle.class, "companyid=? and cmpnavoid=?",
				new Object[] { companyId, cmpNavOid });
	}

	public void setCmpArticleHomePink(long oid, byte homepink) {
		Query query = this.manager.createQuery();
		query.addField("homepink", homepink);
		query.updateById(CmpArticle.class, oid);
	}

	public CmpArticle getCmpArticleForHomepink(long companyId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpArticle.class,
				"companyid=? and homepink=?", new Object[] { companyId,
						CmpArticle.HOMEPINK_Y });
	}

	public List<CmpArticle> getCmpArticleListByCompanyIdForRange(
			long companyId, long cmpNavOid, long oid, int orderflg, int range,
			int size) {
		Query query = this.manager.createQuery();
		if (range <= 0) {
			List<CmpArticle> list = query.listEx(CmpArticle.class,
					"companyid=? and cmpnavoid=? and oid>? and orderflg=?",
					new Object[] { companyId, cmpNavOid, oid, orderflg },
					"orderflg asc,oid asc", 0, size);
			list.addAll(query.listEx(CmpArticle.class,
					"companyid=? and cmpnavoid=? and orderflg>?", new Object[] {
							companyId, cmpNavOid, orderflg },
					"orderflg asc,oid asc", 0, size));
			return DataUtil.subList(list, 0, 3);
		}
		List<CmpArticle> list = query.listEx(CmpArticle.class,
				"companyid=? and cmpnavoid=? and oid<? and orderflg=?",
				new Object[] { companyId, cmpNavOid, oid, orderflg },
				"orderflg desc,oid desc", 0, size);
		list.addAll(query.listEx(CmpArticle.class,
				"companyid=? and cmpnavoid=? and orderflg<?", new Object[] {
						companyId, cmpNavOid, orderflg },
				"orderflg desc,oid desc", 0, size));
		return DataUtil.subList(list, 0, 3);
	}

	public void updateCmpArticleOrderflg(long oid, int orderflg) {
		Query query = this.manager.createQuery();
		query.addField("orderflg", orderflg);
		query.updateById(CmpArticle.class, oid);
	}

	public List<CmpArticle> getCmpArticleListByCompanyIdAndSortIdForCmppink(
			long companyId, int sortId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpArticle.class,
				"companyid=? and sortid=? and cmppink=?", new Object[] {
						companyId, sortId, CmpUtil.CMPPINK_Y },
				"cmppinktime desc", begin, size);
	}

	public List<CmpArticle> getCmpArticleListByCompanyIdAndNotInIdForCmppink(
			long companyId, List<Long> idList, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listNotInField(CmpArticle.class,
				"companyid=? and cmppink=?", new Object[] { companyId,
						CmpUtil.CMPPINK_Y }, "oid", idList, "cmppinktime desc",
				begin, size);
	}

	public boolean createCmpArticleGroup(CmpArticleGroup cmpArticleGroup) {
		Query query = this.manager.createQuery();
		CmpArticleGroup o = query.getObjectEx(CmpArticleGroup.class,
				"cmpnavoid=? and name=?", new Object[] {
						cmpArticleGroup.getCmpNavOid(),
						cmpArticleGroup.getName() });
		if (o != null) {
			return false;
		}
		cmpArticleGroup.setGroupId(query.insertObject(cmpArticleGroup)
				.longValue());
		return true;
	}

	public List<CmpArticleGroup> getCmpArticleGroupListByCompanyIdAndCmpNavOid(
			long companyId, long cmpNavOid) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpArticleGroup.class,
				"companyid=? and cmpnavoid=?", new Object[] { companyId,
						cmpNavOid }, "groupid asc");
	}

	public void deleteCmpArticleGroup(long groupId) {
		Query query = this.manager.createQuery();
		CmpArticleGroup group = this.getCmpArticleGroup(groupId);
		if (group == null) {
			return;
		}
		query.addField("groupid", 0);
		query.update(CmpArticle.class, "companyid=? and groupid=?",
				new Object[] { group.getCompanyId(), group.getCmpNavOid() });
	}

	public boolean updateCmpArticleGroup(CmpArticleGroup cmpArticleGroup) {
		Query query = this.manager.createQuery();
		CmpArticleGroup o = query.getObjectEx(CmpArticleGroup.class,
				"cmpnavoid=? and name=?", new Object[] {
						cmpArticleGroup.getCmpNavOid(),
						cmpArticleGroup.getName() });
		if (o != null && o.getGroupId() != cmpArticleGroup.getGroupId()) {
			return false;
		}
		query.updateObject(cmpArticleGroup);
		return true;
	}

	public CmpArticleGroup getCmpArticleGroup(long groupId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpArticleGroup.class, groupId);
	}

	public Map<Long, CmpArticle> getCmpArticleMapByCompanyIdAndInId(
			long companyId, List<Long> idList) {
		Query query = this.manager.createQuery();
		List<CmpArticle> list = query.listInField(CmpArticle.class,
				"companyid=?", new Object[] { companyId }, "oid", idList, null);
		Map<Long, CmpArticle> map = new HashMap<Long, CmpArticle>();
		for (CmpArticle o : list) {
			map.put(o.getOid(), o);
		}
		return map;
	}

	public Map<Long, CmpArticleGroup> getCmpArticleGroupMapByCompanyIdInId(
			long companyId, List<Long> idList) {
		Query query = this.manager.createQuery();
		List<CmpArticleGroup> list = query.listInField(CmpArticleGroup.class,
				"companyid=?", new Object[] { companyId }, "groupid", idList,
				null);
		Map<Long, CmpArticleGroup> map = new HashMap<Long, CmpArticleGroup>();
		for (CmpArticleGroup o : list) {
			map.put(o.getGroupId(), o);
		}
		return map;
	}

	public Map<Long, CmpArticleContent> getCmpArticleContentMapInId(
			List<Long> idList) {
		Query query = this.manager.createQuery();
		List<CmpArticleContent> list = query.listInField(
				CmpArticleContent.class, null, null, "oid", idList, null);
		Map<Long, CmpArticleContent> map = new HashMap<Long, CmpArticleContent>();
		for (CmpArticleContent o : list) {
			map.put(o.getOid(), o);
		}
		return map;
	}

	public List<CmpArticle> getCmpArticleListByCompanyIdForIdx(long companyId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpArticle.class, "companyid=?",
				new Object[] { companyId }, "oid desc", begin, size);
	}

	public void createCmpArticleNavPink(CmpArticleNavPink cmpArticleNavPink) {
		Query query = this.manager.createQuery();
		if (query.count(CmpArticleNavPink.class,
				"companyid=? and navid=? and articleid=?", new Object[] {
						cmpArticleNavPink.getCompanyId(),
						cmpArticleNavPink.getNavId(),
						cmpArticleNavPink.getArticleId() }) > 0) {
			return;
		}
		long oid = query.insertObject(cmpArticleNavPink).longValue();
		cmpArticleNavPink.setOid(oid);
	}

	public void updateCmpArticleNavPink(CmpArticleNavPink cmpArticleNavPink) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpArticleNavPink);
	}

	public void deleteCmpArticleNavPink(long companyId, long oid) {
		Query query = this.manager.createQuery();
		query.delete(CmpArticleNavPink.class, "companyid=? and oid=?",
				new Object[] { companyId, oid });
	}

	public void deleteCmpArticleNavPinkByCompanyIdAndNavId(long companyId,
			long navId) {
		Query query = this.manager.createQuery();
		query.delete(CmpArticleNavPink.class, "companyid=? and navid=?",
				new Object[] { companyId, navId });
	}

	public CmpArticleNavPink getCmpArticleNavPink(long companyId, long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpArticleNavPink.class,
				"companyid=? and oid=?", new Object[] { companyId, oid });
	}

	public CmpArticleNavPink getCmpArticleNavPinkByCompanyIdAndArticleId(
			long companyId, long articleId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpArticleNavPink.class,
				"companyid=? and articleid=?", new Object[] { companyId,
						articleId });
	}

	public List<CmpArticleNavPink> getCmpArticleNavPinkByCompanyIdAndNavId(
			long companyId, long navId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpArticleNavPink.class, "companyid=? and navid=?",
				new Object[] { companyId, navId }, "orderflg desc,oid desc");
	}

	public List<CmpArticle> getCmpArticleListByCompanyIdAndCmpNavOidNotInId(
			long companyId, long cmpNavOid, List<Long> idList, int begin,
			int size) {
		Query query = this.manager.createQuery();
		return query.listNotInField(CmpArticle.class,
				"companyid=? and cmpnavoid=?", new Object[] { companyId,
						cmpNavOid }, "oid", idList, "orderflg desc,oid desc",
				begin, size);
	}
}