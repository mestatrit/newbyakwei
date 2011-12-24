package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpActorCmt;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpActorCmtService;

public class CmpActorCmtServiceImpl implements CmpActorCmtService {

	@Autowired
	private QueryManager manager;

	public void createCmpActorCmt(CmpActorCmt cmpActorCmt) {
		Query query = this.manager.createQuery();
		long cmtId = query.insertObject(cmpActorCmt).longValue();
		cmpActorCmt.setCmtId(cmtId);
	}

	public void deleteCmpActorCmt(long cmtId) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpActorCmt.class, cmtId);
	}

	public CmpActorCmt getCmpActorCmt(long cmtId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpActorCmt.class, cmtId);
	}

	public List<CmpActorCmt> getCmpActorCmtList(int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpActorCmt.class, "cmtid desc", begin, size);
	}

	public List<CmpActorCmt> getCmpActorCmtListByActorId(long actorId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpActorCmt.class, "actorid=?",
				new Object[] { actorId }, "cmtid desc", begin, size);
	}

	public List<CmpActorCmt> getCmpActorCmtListByCompanyId(long companyId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpActorCmt.class, "companyid=?",
				new Object[] { companyId }, "cmtid desc", begin, size);
	}

	public void updateCmpActorCmt(CmpActorCmt cmpActorCmt) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpActorCmt);
	}

	public void updateCmpActorCmtScore(long actorId, long userId, int score) {
		Query query = this.manager.createQuery();
		query.addField("score", score);
		query.update(CmpActorCmt.class, "actorid=? and userid=?", new Object[] {
				actorId, userId });
	}
}