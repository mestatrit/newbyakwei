package com.hk.svr.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.HkObjArticle;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.HkObjArticleService;

public class HkObjArticleServiceImpl implements HkObjArticleService {
	@Autowired
	private QueryManager manager;

	public void createHkObjArticle(HkObjArticle hkObjArticle) {
		hkObjArticle.setCreateTime(new Date());
		hkObjArticle.setCheckflg(HkObjArticle.CHECKFLG_N);
		Query query = manager.createQuery();
		query.addField("userid", hkObjArticle.getUserId());
		query.addField("hkobjid", hkObjArticle.getHkObjId());
		query.addField("title", hkObjArticle.getTitle());
		query.addField("url", hkObjArticle.getUrl());
		query.addField("authorflg", hkObjArticle.getAuthorflg());
		query.addField("checkflg", hkObjArticle.getCheckflg());
		query.addField("createtime", hkObjArticle.getCreateTime());
		query.addField("email", hkObjArticle.getEmail());
		query.addField("tel", hkObjArticle.getTel());
		query.addField("author", hkObjArticle.getAuthor());
		query.addField("blog", hkObjArticle.getBlog());
		long id = query.insert(HkObjArticle.class).longValue();
		hkObjArticle.setArticleId(id);
	}

	public void deleteHkObjArticle(long articleId) {
		Query query = manager.createQuery();
		query.deleteById(HkObjArticle.class, articleId);
	}

	public HkObjArticle getHkObjArticle(long articleId) {
		Query query = manager.createQuery();
		return query.getObjectById(HkObjArticle.class, articleId);
	}

	public List<HkObjArticle> getHkObjArticleList(byte checkflg, int begin,
			int size) {
		Query query = manager.createQuery();
		if (checkflg < 0) {
			return query.listEx(HkObjArticle.class, "articleid desc", begin,
					size);
		}
		return query.listEx(HkObjArticle.class, "checkflg=?",
				new Object[] { checkflg }, "articleid desc", begin, size);
	}

	public List<HkObjArticle> getHkObjArticleListByOid(long oid, int begin,
			int size) {
		Query query = manager.createQuery();
		return query.listEx(HkObjArticle.class, "hkobjid=? and checkflg=?",
				new Object[] { oid, HkObjArticle.CHECKFLG_Y },
				"articleid desc", begin, size);
	}

	public List<HkObjArticle> getHkObjArticleListByUserId(long userId,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(HkObjArticle.class, "userid=?",
				new Object[] { userId }, "articleid desc", begin, size);
	}

	public void updateHkObjArticle(HkObjArticle hkObjArticle) {
		Query query = manager.createQuery();
		query.addField("userid", hkObjArticle.getUserId());
		query.addField("hkobjid", hkObjArticle.getHkObjId());
		query.addField("title", hkObjArticle.getTitle());
		query.addField("url", hkObjArticle.getUrl());
		query.addField("authorflg", hkObjArticle.getAuthorflg());
		query.addField("checkflg", hkObjArticle.getCheckflg());
		query.addField("createtime", hkObjArticle.getCreateTime());
		query.addField("email", hkObjArticle.getEmail());
		query.addField("tel", hkObjArticle.getTel());
		query.addField("author", hkObjArticle.getAuthor());
		query.addField("blog", hkObjArticle.getBlog());
		query.update(HkObjArticle.class, "articleid=?",
				new Object[] { hkObjArticle.getArticleId() });
	}

	public void checkHkObjArticle(long articleId, byte checkflg) {
		Query query = manager.createQuery();
		query.addField("checkflg", checkflg);
		query.update(HkObjArticle.class, "articleid=?",
				new Object[] { articleId });
	}

	public HkObjArticle getUserLastHkObjArticle(long userId) {
		Query query = manager.createQuery();
		return query.getObject(HkObjArticle.class, "userid=?",
				new Object[] { userId }, "articleid desc");
	}
}