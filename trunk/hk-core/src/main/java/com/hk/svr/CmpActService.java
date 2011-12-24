package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpAct;
import com.hk.bean.CmpActCmt;
import com.hk.bean.CmpActCost;
import com.hk.bean.CmpActIdData;
import com.hk.bean.CmpActKind;
import com.hk.bean.CmpActStepCost;
import com.hk.bean.CmpActUser;

public interface CmpActService {
	/**
	 * 创建活动
	 * 
	 * @param cmpAct
	 * @param cmpActCostList 可为空
	 * @param cmpActStepCostList 可为空
	 */
	void createCmpAct(CmpAct cmpAct, List<CmpActCost> cmpActCostList,
			List<CmpActStepCost> cmpActStepCostList);

	void createCmpActCost(CmpActCost cmpActCost);

	void createCmpActStepCost(CmpActStepCost cmpActStepCost);

	void createCmpActUser(CmpActUser cmpActUser);

	void createCmpActCmt(CmpActCmt cmpActCmt);

	boolean createCmpActKind(CmpActKind cmpActKind);

	boolean updateCmpActKind(CmpActKind cmpActKind);

	void updateCmpAct(CmpAct cmpAct);

	void updateCmpActCost(CmpActCost cmpActCost);

	void updateCmpActStepCost(CmpActStepCost cmpActStepCost);

	void updateCmpActUserCheckflg(long actId, long userId, byte checkflg);

	void deleteCmpActUser(long actId, long userId);

	void deleteCmpActCost(long costId);

	void deleteCmpActStepCost(long costId);

	void deleteCmpActCmt(long cmtId);

	void deleteCmpActKind(long kindId);

	CmpAct getCmpAct(long actId);

	CmpActCost getActCost(long costId);

	CmpActStepCost getCmpActStepCost(long costId);

	CmpActCmt getCmpActCmt(long cmtId);

	List<CmpActCmt> getCmpActCmtListByActId(long actId, int begin, int size);

	List<CmpActKind> getCmpActKindList();

	CmpActKind getCmpActKind(long kindId);

	List<CmpActCost> getCmpActCostListByActId(long actId);

	List<CmpActStepCost> getCmpActStepCostListByActId(long actId);

	List<CmpAct> getCmpActListByCompanyId(long companyId, int begin, int size);

	List<CmpAct> getCmpActListByCdn(long uid, String name, byte actStatus,
			int begin, int size);

	int countCmpActByCdn(long uid, String name, byte actStatus);

	List<CmpAct> getCmpActListForRun(long uid, int begin, int size);

	int countCmpActByCompanyId(long companyId);

	CmpActIdData createCmpActIdData();

	List<CmpActUser> getCmpActUserListByActId(long actId, byte checkflg,
			int begin, int size);

	int countCmpActUserByActId(long actId);

	CmpActUser getCmpActUserByActIdAndUserId(long actId, long userId);

	CmpActCost getCmpActCost(long costId);

	void updatepcityiddata();
}