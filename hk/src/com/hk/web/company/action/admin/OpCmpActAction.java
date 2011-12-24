package com.hk.web.company.action.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmdData;
import com.hk.bean.CmpAct;
import com.hk.bean.CmpActCost;
import com.hk.bean.CmpActIdData;
import com.hk.bean.CmpActKind;
import com.hk.bean.CmpActStepCost;
import com.hk.bean.Pcity;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmdDataService;
import com.hk.svr.CmpActService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.pub.action.BaseAction;

@Component("/e/op/auth/act")
public class OpCmpActAction extends BaseAction {
	@Autowired
	private CmpActService cmpActService;

	@Autowired
	private CmdDataService cmdDataService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLongAndSetAttr("actId");
		CmpAct act = this.cmpActService.getCmpAct(actId);
		if (act == null) {
			return this.getNotFoundForward(resp);
		}
		List<CmpActCost> cmpActCostList = this.cmpActService
				.getCmpActCostListByActId(actId);
		List<CmpActStepCost> cmpActStepCostList = this.cmpActService
				.getCmpActStepCostListByActId(actId);
		if (act.getPcityId() > 0) {
			Pcity pcity = ZoneUtil.getPcity(act.getPcityId());
			req.setAttribute("pcity", pcity);
		}
		CmpActKind cmpActKind = this.cmpActService.getCmpActKind(act
				.getKindId());
		req.setAttribute("cmpActKind", cmpActKind);
		req.setAttribute("cmpActCostList", cmpActCostList);
		req.setAttribute("cmpActStepCostList", cmpActStepCostList);
		req.setAttribute("act", act);
		req.reSetAttribute("companyId");
		return this.getWeb3Jsp("e/act/act.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		PageSupport page = req.getPageSupport(20);
		page
				.setTotalCount(this.cmpActService
						.countCmpActByCompanyId(companyId));
		List<CmpAct> list = this.cmpActService.getCmpActListByCompanyId(
				companyId, page.getBegin(), page.getSize());
		req.setAttribute("list", list);
		req.setAttribute("op_func", 22);
		return this.getWeb3Jsp("e/act/actlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tocreate(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("companyId");
		req.setAttribute("op_func", 21);
		return this.getWeb3Jsp("e/act/create.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toupdate(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLongAndSetAttr("actId");
		CmpAct act = this.cmpActService.getCmpAct(actId);
		if (act == null) {
			return this.getNotFoundForward(resp);
		}
		req.setAttribute("act", act);
		req.reSetAttribute("companyId");
		req.setAttribute("pcityId", act.getPcityId());
		return this.getWeb3Jsp("e/act/update.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long uid = this.getUidFromCompany(companyId);
		String name = req.getString("name");
		String intro = req.getString("intro");
		String spintro = req.getString("spintro");
		String addr = req.getString("addr");
		String actKey = req.getString("actKey");
		byte userNeedCheckflg = req.getByte("userNeedCheckflg");
		double actCost = req.getDouble("actCost");
		int pcityId = req.getInt("pcityId");
		int userLimitCount = req.getInt("userLimitCount");
		long kindId = req.getLong("kindId");
		String bd = req.getString("bd");
		String bt = req.getString("bt");
		String ed = req.getString("ed");
		String et = req.getString("et");
		Date beginTime = DataUtil.parseTime(bd + " " + bt, "yyyy-MM-dd HH:mm");
		Date endTime = DataUtil.parseTime(ed + " " + et, "yyyy-MM-dd HH:mm");
		CmpAct cmpAct = new CmpAct();
		cmpAct.setUserId(this.getLoginUser(req).getUserId());
		cmpAct.setCompanyId(companyId);
		cmpAct.setUid(uid);
		cmpAct.setActCost(actCost);
		cmpAct.setName(DataUtil.toHtmlRow(name));
		cmpAct.setIntro(DataUtil.toHtml(intro));
		cmpAct.setSpintro(DataUtil.toHtml(spintro));
		cmpAct.setActKey(DataUtil.toHtmlRow(actKey));
		cmpAct.setAddr(DataUtil.toHtml(addr));
		cmpAct.setUserNeedCheckflg(userNeedCheckflg);
		cmpAct.setPcityId(pcityId);
		cmpAct.setKindId(kindId);
		cmpAct.setBeginTime(beginTime);
		cmpAct.setEndTime(endTime);
		cmpAct.setUserLimitCount(userLimitCount);
		String[] cmpActCost_name = req.getStrings("cmpActCost_name");
		Number[] cmpActCost_cost = req.getNumbers("cmpActCost_cost");
		String[] cmpActCost_intro = req.getStrings("cmpActCost_intro");
		Number[] cmpActSetp_userCount = req.getNumbers("cmpActSetp_userCount");
		Number[] cmpActSetp_cost = req.getNumbers("cmpActSetp_cost");
		List<CmpActCost> cmpActCostList = new ArrayList<CmpActCost>();
		if (cmpActCost_name != null && cmpActCost_intro != null) {
			for (int i = 0; i < cmpActCost_name.length; i++) {
				CmpActCost cost = new CmpActCost();
				cost.setActCost(cmpActCost_cost[i].doubleValue());
				cost.setName(DataUtil.toHtmlRow(cmpActCost_name[i]));
				cost.setIntro(DataUtil.toHtml(cmpActCost_intro[i]));
				cost.setCompanyId(companyId);
				cmpActCostList.add(cost);
			}
		}
		List<CmpActStepCost> cmpActStepCostList = new ArrayList<CmpActStepCost>();
		if (cmpActSetp_userCount != null && cmpActSetp_cost != null) {
			for (int i = 0; i < cmpActSetp_userCount.length; i++) {
				CmpActStepCost cost = new CmpActStepCost();
				cost.setActCost(cmpActSetp_cost[i].doubleValue());
				cost.setUserCount(cmpActSetp_userCount[i].intValue());
				cost.setCompanyId(companyId);
				cmpActStepCostList.add(cost);
			}
		}
		int code = cmpAct.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "acterror", null);
		}
		CmpActIdData data = this.cmpActService.createCmpActIdData();
		cmpAct.setActId(data.getActId());
		if (actKey != null) {
			CmdData cmdData = new CmdData();
			cmdData.setEndTime(endTime);
			cmdData.setName(DataUtil.toHtmlRow(actKey));
			cmdData.setEndflg(CmdData.ENDFLG_Y);
			cmdData.setOtype(CmdData.OTYPE_CMPACT);
			cmdData.setOid(cmpAct.getActId());
			code = cmdData.validate();
			if (code != Err.SUCCESS) {
				this.cmdDataService.deleteCmdDataByOidAndOtype(cmpAct
						.getActId(), CmdData.OTYPE_CMPACT);
				return this.onError(req, code, "acterror", null);
			}
			if (!this.cmdDataService.createCmdData(cmdData)) {
				this.cmdDataService.deleteCmdDataByOidAndOtype(cmpAct
						.getActId(), CmdData.OTYPE_CMPACT);
				return this.onError(req, Err.CMDDATA_NAME_DUPLICATE,
						"acterror", null);
			}
		}
		this.cmpActService.createCmpAct(cmpAct, cmpActCostList,
				cmpActStepCostList);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "actok", cmpAct.getActId());
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLong("actId");
		long companyId = req.getLong("companyId");
		CmpAct cmpAct = this.cmpActService.getCmpAct(actId);
		if (cmpAct == null || cmpAct.getCompanyId() != companyId) {
			return null;
		}
		String name = req.getString("name");
		String intro = req.getString("intro");
		String spintro = req.getString("spintro");
		String addr = req.getString("addr");
		String actKey = req.getString("actKey");
		byte userNeedCheckflg = req.getByte("userNeedCheckflg");
		double actCost = req.getDouble("actCost");
		int pcityId = req.getInt("pcityId");
		int userLimitCount = req.getInt("userLimitCount");
		long kindId = req.getLong("kindId");
		String bd = req.getString("bd");
		String bt = req.getString("bt");
		String ed = req.getString("ed");
		String et = req.getString("et");
		Date beginTime = DataUtil.parseTime(bd + " " + bt, "yyyy-MM-dd HH:mm");
		Date endTime = DataUtil.parseTime(ed + " " + et, "yyyy-MM-dd HH:mm");
		cmpAct.setActCost(actCost);
		cmpAct.setName(DataUtil.toHtmlRow(name));
		cmpAct.setIntro(DataUtil.toHtml(intro));
		cmpAct.setSpintro(DataUtil.toHtml(spintro));
		cmpAct.setActKey(DataUtil.toHtmlRow(actKey));
		cmpAct.setAddr(DataUtil.toHtml(addr));
		cmpAct.setUserNeedCheckflg(userNeedCheckflg);
		cmpAct.setPcityId(pcityId);
		cmpAct.setKindId(kindId);
		cmpAct.setBeginTime(beginTime);
		cmpAct.setEndTime(endTime);
		cmpAct.setUserLimitCount(userLimitCount);
		int code = cmpAct.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "acterror", null);
		}
		if (actKey != null) {
			CmdData cmdData = this.cmdDataService.getCmdDataByOidAndOtype(
					actId, CmdData.OTYPE_CMPACT);
			if (cmdData != null) {
				cmdData.setEndTime(endTime);
				cmdData.setName(DataUtil.toHtmlRow(actKey));
				if (code != Err.SUCCESS) {
					return this.onError(req, code, "acterror", null);
				}
				if (!this.cmdDataService.updateCmdData(cmdData)) {
					return this.onError(req, Err.CMDDATA_NAME_DUPLICATE,
							"acterror", null);
				}
			}
			else {
				cmdData = new CmdData();
				cmdData.setOid(actId);
				cmdData.setEndTime(endTime);
				cmdData.setName(DataUtil.toHtmlRow(actKey));
				cmdData.setEndflg(CmdData.ENDFLG_Y);
				cmdData.setOtype(CmdData.OTYPE_CMPACT);
				cmdData.setOid(cmpAct.getActId());
				code = cmdData.validate();
				if (code != Err.SUCCESS) {
					return this.onError(req, code, "acterror", null);
				}
				if (!this.cmdDataService.createCmdData(cmdData)) {
					return this.onError(req, Err.CMDDATA_NAME_DUPLICATE,
							"acterror", null);
				}
			}
		}
		else {
			// 删除指令库中的关键字
			this.cmdDataService.deleteCmdDataByOidAndOtype(actId,
					CmdData.OTYPE_CMPACT);
		}
		this.cmpActService.updateCmpAct(cmpAct);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "actok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setrun(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLong("actId");
		long companyId = req.getLong("companyId");
		CmpAct act = this.cmpActService.getCmpAct(actId);
		if (act == null || act.getCompanyId() != companyId) {
			return null;
		}
		act.setActStatus(CmpAct.ACTSTATUS_RUN);
		this.cmpActService.updateCmpAct(act);
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setpause(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLong("actId");
		long companyId = req.getLong("companyId");
		CmpAct act = this.cmpActService.getCmpAct(actId);
		if (act == null || act.getCompanyId() != companyId) {
			return null;
		}
		act.setActStatus(CmpAct.ACTSTATUS_PAUSE);
		this.cmpActService.updateCmpAct(act);
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setinvalid(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLong("actId");
		long companyId = req.getLong("companyId");
		CmpAct act = this.cmpActService.getCmpAct(actId);
		if (act == null || act.getCompanyId() != companyId) {
			return null;
		}
		act.setActStatus(CmpAct.ACTSTATUS_INVALID);
		this.cmpActService.updateCmpAct(act);
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String loadstep(HkRequest req, HkResponse resp) throws Exception {
		long costId = req.getLongAndSetAttr("costId");
		CmpActStepCost o = this.cmpActService.getCmpActStepCost(costId);
		req.setAttribute("o", o);
		return this.getWeb3Jsp("e/act/step_inc.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String loadcost(HkRequest req, HkResponse resp) throws Exception {
		long costId = req.getLongAndSetAttr("costId");
		CmpActCost o = this.cmpActService.getActCost(costId);
		req.setAttribute("o", o);
		return this.getWeb3Jsp("e/act/cost_inc.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createcost(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLong("actId");
		String name = req.getString("name");
		String intro = req.getString("intro");
		CmpAct act = this.cmpActService.getCmpAct(actId);
		double actCost = req.getDouble("actCost");
		CmpActCost cost = new CmpActCost();
		cost.setActCost(actCost);
		cost.setActId(actId);
		cost.setName(DataUtil.toHtmlRow(name));
		cost.setIntro(DataUtil.toHtml(intro));
		cost.setCompanyId(act.getCompanyId());
		int code = cost.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "costerror", null);
		}
		this.cmpActService.createCmpActCost(cost);
		return this.onSuccess2(req, "costok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updatecost(HkRequest req, HkResponse resp) throws Exception {
		long costId = req.getLong("costId");
		String name = req.getString("name");
		String intro = req.getString("intro");
		double actCost = req.getDouble("actCost");
		CmpActCost cost = this.cmpActService.getCmpActCost(costId);
		cost.setActCost(actCost);
		cost.setName(DataUtil.toHtmlRow(name));
		cost.setIntro(DataUtil.toHtml(intro));
		int code = cost.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "costerror", null);
		}
		this.cmpActService.updateCmpActCost(cost);
		return this.onSuccess2(req, "costok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createstep(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLong("actId");
		int userCount = req.getInt("userCount");
		double actCost = req.getDouble("actCost");
		CmpAct act = this.cmpActService.getCmpAct(actId);
		CmpActStepCost cost = new CmpActStepCost();
		cost.setUserCount(userCount);
		cost.setActCost(actCost);
		cost.setActId(actId);
		cost.setCompanyId(act.getCompanyId());
		int code = cost.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "steperror", null);
		}
		this.cmpActService.createCmpActStepCost(cost);
		return this.onSuccess2(req, "stepok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updatestep(HkRequest req, HkResponse resp) throws Exception {
		long costId = req.getLong("costId");
		int userCount = req.getInt("userCount");
		double actCost = req.getDouble("actCost");
		CmpActStepCost cost = this.cmpActService.getCmpActStepCost(costId);
		cost.setUserCount(userCount);
		cost.setActCost(actCost);
		int code = cost.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "steperror", null);
		}
		this.cmpActService.updateCmpActStepCost(cost);
		return this.onSuccess2(req, "stepok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delcost(HkRequest req, HkResponse resp) throws Exception {
		long costId = req.getLong("costId");
		long companyId = req.getLong("companyId");
		CmpActCost cost = this.cmpActService.getCmpActCost(costId);
		if (cost == null) {
			return null;
		}
		CmpAct act = this.cmpActService.getCmpAct(cost.getActId());
		if (act.getCompanyId() == companyId) {
			this.cmpActService.deleteCmpActCost(costId);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delstep(HkRequest req, HkResponse resp) throws Exception {
		long costId = req.getLong("costId");
		long companyId = req.getLong("companyId");
		CmpActStepCost cost = this.cmpActService.getCmpActStepCost(costId);
		if (cost == null) {
			return null;
		}
		CmpAct act = this.cmpActService.getCmpAct(cost.getActId());
		if (act.getCompanyId() == companyId) {
			this.cmpActService.deleteCmpActStepCost(costId);
		}
		return null;
	}
}