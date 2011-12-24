package com.hk.svr.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpActorScore;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpActorScoreService;

public class CmpActorScoreServiceImpl implements CmpActorScoreService {

	@Autowired
	private QueryManager manager;

	public int countCmpActorScoreByActorId(long actorId) {
		Query query = this.manager.createQuery();
		return query.count(CmpActorScore.class, "actorid=?",
				new Object[] { actorId });
	}

	public void saveCmpActorScore(CmpActorScore cmpActorScore) {
		Query query = this.manager.createQuery();
		CmpActorScore o = this.getActorScoreByActorIdAndUserId(cmpActorScore
				.getActorId(), cmpActorScore.getUserId());
		if (o == null) {
			query.insertObject(cmpActorScore);
		}
		else {
			o.setScore(cmpActorScore.getScore());
			query.updateObject(o);
			cmpActorScore.setCompanyId(o.getCompanyId());
		}
	}

	public CmpActorScore getActorScoreByActorIdAndUserId(long actorId,
			long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpActorScore.class, "actorid=? and userid=?",
				new Object[] { actorId, userId });
	}

	public int sumScoreByActorId(long actorId) {
		Query query = this.manager.createQuery();
		return query.sum("score", CmpActorScore.class, "actorid=?",
				new Object[] { actorId }).intValue();
	}

	public void deleteCmpActorScoreByActorIdAndUserId(long actorId, long userId) {
		Query query = this.manager.createQuery();
		query.delete(CmpActorScore.class, "actorid=? and userid=?",
				new Object[] { actorId, userId });
	}
}