package com.hk.svr.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpOrgArticle;
import com.hk.bean.CmpOrgArticleContent;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpOrgArticleService;

public class CmpOrgArticleServiceImpl implements CmpOrgArticleService {

	@Autowired
	private QueryManager manager;

	public int countCmpOrgArticleByCompanyIdAndNavId(long companyId,
			long navId, String title) {
		Query query = this.manager.createQuery();
		if (!DataUtil.isEmpty(title)) {
			return query.count(CmpOrgArticle.class,
					"companyId=? and navId=? and title like ?", new Object[] {
							companyId, navId, "%" + title + "%" });
		}
		return query.count(CmpOrgArticle.class, "companyId=? and navId=?",
				new Object[] { companyId, navId });
	}

	public void createCmpOrgArticle(CmpOrgArticle cmpOrgArticle,
			CmpOrgArticleContent cmpOrgArticleContent) {
		if (cmpOrgArticle.getCreateTime() == null) {
			cmpOrgArticle.setCreateTime(new Date());
		}
		Query query = this.manager.createQuery();
		long oid = query.insertObject(cmpOrgArticle).longValue();
		cmpOrgArticle.setOid(oid);
		cmpOrgArticleContent.setOid(oid);
		cmpOrgArticleContent.setCompanyId(cmpOrgArticle.getCompanyId());
		cmpOrgArticleContent.setOrgId(cmpOrgArticle.getOrgId());
		query.insertObject(cmpOrgArticleContent);
	}

	public void deleteCmpOrgArticle(long companyId, long oid) {
		Query query = this.manager.createQuery();
		query.delete(CmpOrgArticle.class, "companyid=? and oid=?",
				new Object[] { companyId, oid });
		query.delete(CmpOrgArticleContent.class, "companyid=? and oid=?",
				new Object[] { companyId, oid });
	}

	public CmpOrgArticle getCmpOrgArticle(long companyId, long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpOrgArticle.class, "companyid=? and oid=?",
				new Object[] { companyId, oid });
	}

	public CmpOrgArticleContent getCmpOrgArticleContent(long companyId, long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpOrgArticleContent.class,
				"companyid=? and oid=?", new Object[] { companyId, oid });
	}

	public List<CmpOrgArticle> getCmpOrgArticleListByCompanyIdAndNavId(
			long companyId, long navId, String title, int begin, int size) {
		Query query = this.manager.createQuery();
		if (!DataUtil.isEmpty(title)) {
			return query.listEx(CmpOrgArticle.class,
					"companyId=? and navid=? and title like ?", new Object[] {
							companyId, navId, "%" + title + "%" },
					"orderflg desc,oid desc", begin, size);
		}
		return query.listEx(CmpOrgArticle.class, "companyId=? and navid=?",
				new Object[] { companyId, navId }, "orderflg desc,oid desc",
				begin, size);
	}

	public void setCmpOrgArticleUnknown(long companyId, long navId) {
		Query query = this.manager.createQuery();
		query.addField("navid", 0);
		query.update(CmpOrgArticle.class, "companyid=? and navid=?",
				new Object[] { companyId, navId });
	}

	public void updateCmpOrgArticle(CmpOrgArticle cmpOrgArticle,
			CmpOrgArticleContent cmpOrgArticleContent) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpOrgArticle);
		if (cmpOrgArticleContent != null) {
			query.updateObject(cmpOrgArticleContent);
		}
	}

	public void updateCmpOrgArticleOrderflg(long companyId, long oid,
			int orderflg) {
		Query query = this.manager.createQuery();
		query.addField("orderflg", orderflg);
		query.update(CmpOrgArticle.class, "companyid=? and oid=?",
				new Object[] { companyId, oid });
	}

	public List<CmpOrgArticle> getCmpOrgArticleListByCompanyIdForNext(
			long companyId, long orgId, long navId, long oid, int orderflg,
			int size) {
		Query query = this.manager.createQuery();
		List<CmpOrgArticle> list = query.listEx(CmpOrgArticle.class,
				"companyid=? and orgid=? and navid=? and oid<? and orderflg=?",
				new Object[] { companyId, orgId, navId, oid, orderflg },
				"orderflg desc,oid desc", 0, size);
		list.addAll(query.listEx(CmpOrgArticle.class,
				"companyid=? and orgid=? and navid=? and orderflg<?",
				new Object[] { companyId, orgId, navId, orderflg },
				"orderflg desc,oid desc", 0, size));
		return DataUtil.subList(list, 0, size);
	}
}