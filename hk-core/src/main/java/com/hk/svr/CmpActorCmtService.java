package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpActorCmt;

/**
 * 美发师点评
 * 
 * @author akwei
 */
public interface CmpActorCmtService {

	void createCmpActorCmt(CmpActorCmt cmpActorCmt);

	void updateCmpActorCmt(CmpActorCmt cmpActorCmt);

	void deleteCmpActorCmt(long cmtId);

	void updateCmpActorCmtScore(long actorId, long userId, int score);

	CmpActorCmt getCmpActorCmt(long cmtId);

	List<CmpActorCmt> getCmpActorCmtListByCompanyId(long companyId, int begin,
			int size);

	List<CmpActorCmt> getCmpActorCmtListByActorId(long actorId, int begin,
			int size);

	List<CmpActorCmt> getCmpActorCmtList(int begin, int size);
}