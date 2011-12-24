package com.hk.svr.wrapper;

import java.util.List;

import com.hk.bean.CmpAct;
import com.hk.bean.CmpActCmt;
import com.hk.bean.CmpActCost;
import com.hk.bean.CmpActIdData;
import com.hk.bean.CmpActKind;
import com.hk.bean.CmpActStepCost;
import com.hk.bean.CmpActUser;
import com.hk.listener.act.CmpActEventListener;
import com.hk.svr.CmpActService;

public class CmpActServiceWrapper implements CmpActService {
	private CmpActService cmpActService;

	private List<CmpActEventListener> cmpActEventListenerList;

	public void setCmpActEventListenerList(
			List<CmpActEventListener> cmpActEventListenerList) {
		this.cmpActEventListenerList = cmpActEventListenerList;
	}

	public CmpActServiceWrapper(CmpActService cmpActService) {
		this.cmpActService = cmpActService;
	}

	public void createCmpAct(CmpAct cmpAct, List<CmpActCost> cmpActCostList,
			List<CmpActStepCost> cmpActStepCostList) {
		this.cmpActService.createCmpAct(cmpAct, cmpActCostList,
				cmpActStepCostList);
		if (cmpActEventListenerList != null) {
			for (CmpActEventListener listener : cmpActEventListenerList) {
				listener.cmpActCreated(cmpAct);
			}
		}
	}

	public void createCmpActCmt(CmpActCmt cmpActCmt) {
		this.cmpActService.createCmpActCmt(cmpActCmt);
	}

	public void createCmpActCost(CmpActCost cmpActCost) {
		this.cmpActService.createCmpActCost(cmpActCost);
	}

	public void createCmpActStepCost(CmpActStepCost cmpActStepCost) {
		this.cmpActService.createCmpActStepCost(cmpActStepCost);
	}

	public void createCmpActUser(CmpActUser cmpActUser) {
		this.cmpActService.createCmpActUser(cmpActUser);
	}

	public void deleteCmpActCmt(long cmtId) {
		this.cmpActService.deleteCmpActCmt(cmtId);
	}

	public void deleteCmpActCost(long costId) {
		this.cmpActService.deleteCmpActCost(costId);
	}

	public void deleteCmpActStepCost(long costId) {
		this.cmpActService.deleteCmpActStepCost(costId);
	}

	public void deleteCmpActUser(long actId, long userId) {
		this.cmpActService.deleteCmpActUser(actId, userId);
	}

	public CmpActCost getActCost(long costId) {
		return this.cmpActService.getActCost(costId);
	}

	public CmpAct getCmpAct(long actId) {
		return this.cmpActService.getCmpAct(actId);
	}

	public CmpActCmt getCmpActCmt(long cmtId) {
		return this.cmpActService.getCmpActCmt(cmtId);
	}

	public List<CmpActCmt> getCmpActCmtListByActId(long actId, int begin,
			int size) {
		return this.cmpActService.getCmpActCmtListByActId(actId, begin, size);
	}

	public CmpActStepCost getCmpActStepCost(long costId) {
		return this.cmpActService.getCmpActStepCost(costId);
	}

	public void updateCmpAct(CmpAct cmpAct) {
		this.cmpActService.updateCmpAct(cmpAct);
	}

	public void updateCmpActCost(CmpActCost cmpActCost) {
		this.cmpActService.updateCmpActCost(cmpActCost);
	}

	public void updateCmpActStepCost(CmpActStepCost cmpActStepCost) {
		this.cmpActService.updateCmpActStepCost(cmpActStepCost);
	}

	public void updateCmpActUserCheckflg(long actId, long userId, byte checkflg) {
		this.cmpActService.updateCmpActUserCheckflg(actId, userId, checkflg);
	}

	public List<CmpActKind> getCmpActKindList() {
		return this.cmpActService.getCmpActKindList();
	}

	public boolean createCmpActKind(CmpActKind cmpActKind) {
		return this.cmpActService.createCmpActKind(cmpActKind);
	}

	public boolean updateCmpActKind(CmpActKind cmpActKind) {
		return this.cmpActService.updateCmpActKind(cmpActKind);
	}

	public void deleteCmpActKind(long kindId) {
		this.cmpActService.deleteCmpActKind(kindId);
	}

	public CmpActKind getCmpActKind(long kindId) {
		return this.cmpActService.getCmpActKind(kindId);
	}

	public List<CmpActCost> getCmpActCostListByActId(long actId) {
		return this.cmpActService.getCmpActCostListByActId(actId);
	}

	public List<CmpActStepCost> getCmpActStepCostListByActId(long actId) {
		return this.cmpActService.getCmpActStepCostListByActId(actId);
	}

	public int countCmpActByCompanyId(long companyId) {
		return this.cmpActService.countCmpActByCompanyId(companyId);
	}

	public List<CmpAct> getCmpActListByCompanyId(long companyId, int begin,
			int size) {
		return this.cmpActService.getCmpActListByCompanyId(companyId, begin,
				size);
	}

	public CmpActIdData createCmpActIdData() {
		return this.cmpActService.createCmpActIdData();
	}

	public List<CmpAct> getCmpActListByCdn(long uid, String name,
			byte actStatus, int begin, int size) {
		return this.cmpActService.getCmpActListByCdn(uid, name, actStatus,
				begin, size);
	}

	public List<CmpAct> getCmpActListForRun(long uid, int begin, int size) {
		return this.cmpActService.getCmpActListForRun(uid, begin, size);
	}

	public int countCmpActByCdn(long uid, String name, byte actStatus) {
		return this.cmpActService.countCmpActByCdn(uid, name, actStatus);
	}

	public List<CmpActUser> getCmpActUserListByActId(long actId, byte checkflg,
			int begin, int size) {
		return this.cmpActService.getCmpActUserListByActId(actId, checkflg,
				begin, size);
	}

	public int countCmpActUserByActId(long actId) {
		return this.cmpActService.countCmpActUserByActId(actId);
	}

	public CmpActUser getCmpActUserByActIdAndUserId(long actId, long userId) {
		return this.cmpActService.getCmpActUserByActIdAndUserId(actId, userId);
	}

	public CmpActCost getCmpActCost(long costId) {
		return this.cmpActService.getCmpActCost(costId);
	}

	public void updatepcityiddata() {
		this.cmpActService.updatepcityiddata();
	}
}