package com.hk.web.cmpunion.action.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpAct;
import com.hk.bean.CmpActCost;
import com.hk.bean.CmpActKind;
import com.hk.bean.CmpActStepCost;
import com.hk.bean.Pcity;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpActService;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.cmpunion.action.CmpUnionBaseAction;

@Component("/cmpunion/op/cmpact")
public class OpCmpUnionActAction extends CmpUnionBaseAction {
	@Autowired
	private CmpActService cmpActService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLongAndSetAttr("uid");
		String name = req.getString("name");
		PageSupport page = req.getPageSupport(20);
		byte actStatus = (byte) -1;
		page.setTotalCount(this.cmpActService.countCmpActByCdn(uid, name,
				actStatus));
		List<CmpAct> list = this.cmpActService.getCmpActListByCdn(uid, name,
				actStatus, page.getBegin(), page.getSize());
		req.setAttribute("list", list);
		req.setEncodeAttribute("name", name);
		req.setAttribute("op_func", 9);
		return this.getWeb3Jsp("unionadmin/actlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String act(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("uid");
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
		return this.getWeb3Jsp("unionadmin/act.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setrun(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLong("actId");
		long uid = req.getLong("uid");
		CmpAct act = this.cmpActService.getCmpAct(actId);
		if (act == null || act.getUid() != uid) {
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
		long uid = req.getLong("uid");
		CmpAct act = this.cmpActService.getCmpAct(actId);
		if (act == null || act.getUid() != uid) {
			return null;
		}
		act.setActStatus(CmpAct.ACTSTATUS_ADMINPAUSE);
		this.cmpActService.updateCmpAct(act);
		this.setOpFuncSuccessMsg(req);
		return null;
	}
}