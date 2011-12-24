package com.hk.svr;

import com.hk.bean.CmpActorScore;

public interface CmpActorScoreService {

	/**
	 * 对于每个工作人员每个用户只有一条分数记录,如果存在记录就更新，如果不存在就创建
	 * 
	 * @param cmpActorScore
	 *            2010-8-23
	 */
	void saveCmpActorScore(CmpActorScore cmpActorScore);

	void deleteCmpActorScoreByActorIdAndUserId(long actorId, long userId);

	CmpActorScore getActorScoreByActorIdAndUserId(long actorId, long userId);

	int sumScoreByActorId(long actorId);

	int countCmpActorScoreByActorId(long actorId);
}