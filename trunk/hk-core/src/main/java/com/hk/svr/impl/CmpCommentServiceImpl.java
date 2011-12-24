package com.hk.svr.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.CmpComment;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpCommentService;

public class CmpCommentServiceImpl implements CmpCommentService {
	@Autowired
	private QueryManager manager;

	public void createCmpComment(CmpComment cmpComment) {
		cmpComment.setCreateTime(new Date());
		Query query = manager.createQuery();
		query.addField("companyid", cmpComment.getCompanyId());
		query.addField("userid", cmpComment.getUserId());
		query.addField("content", cmpComment.getContent());
		query.addField("createtime", cmpComment.getCreateTime());
		query.addField("sendfrom", cmpComment.getSendfrom());
		query.addField("ip", cmpComment.getIp());
		long id = query.insert(CmpComment.class).longValue();
		cmpComment.setCmtId(id);
	}

	public void deleteCmpComment(long companyId, long cmtId) {
		Query query = manager.createQuery();
		query.delete(CmpComment.class, "companyid=? and cmtid=?", new Object[] {
				companyId, cmtId });
	}

	public CmpComment getCmpComment(long companyId, long cmtId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpComment.class, "companyid=? and cmtid=?",
				new Object[] { companyId, cmtId });
	}

	public List<CmpComment> getCmpCommentList(long companyId, int begin,
			int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpComment.class, "companyid=?",
				new Object[] { companyId }, "cmtid desc", begin, size);
	}

	public int countCmpComment(long companyId) {
		Query query = manager.createQuery();
		return query.count(CmpComment.class, "companyid=?",
				new Object[] { companyId });
	}
}