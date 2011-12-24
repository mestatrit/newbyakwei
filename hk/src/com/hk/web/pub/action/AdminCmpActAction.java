package com.hk.web.pub.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpAct;
import com.hk.bean.CmpActCost;
import com.hk.bean.CmpActKind;
import com.hk.bean.CmpActStepCost;
import com.hk.bean.Pcity;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpActService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ZoneUtil;

@Component("/e/admin/cmpact")
public class AdminCmpActAction extends BaseAction {
	@Autowired
	private CmpActService cmpActService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLongAndSetAttr("uid");
		String name = req.getString("name");
		byte actStatus = req.getByte("actStatus", (byte) -1);
		SimplePage page = req.getSimplePage(20);
		List<CmpAct> list = this.cmpActService.getCmpActListByCdn(uid, name,
				actStatus, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.setEncodeAttribute("name", name);
		return this.getWapJsp("admin/actlist.jsp");
	}

	public String act(HkRequest req, HkResponse resp) throws Exception {
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
		return this.getWapJsp("admin/act.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setactadminpause(HkRequest req, HkResponse resp)
			throws Exception {
		long actId = req.getLongAndSetAttr("actId");
		CmpAct act = this.cmpActService.getCmpAct(actId);
		if (act != null) {
			act.setActStatus(CmpAct.ACTSTATUS_ADMINPAUSE);
			this.cmpActService.updateCmpAct(act);
			this.setOpFuncSuccessMsg(req);
		}
		return "r:/e/admin/cmpact.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setactrun(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLongAndSetAttr("actId");
		CmpAct act = this.cmpActService.getCmpAct(actId);
		if (act != null) {
			act.setActStatus(CmpAct.ACTSTATUS_RUN);
			this.cmpActService.updateCmpAct(act);
			this.setOpFuncSuccessMsg(req);
		}
		return "r:/e/admin/cmpact.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String kindlist(HkRequest req, HkResponse resp) throws Exception {
		List<CmpActKind> kindlist = this.cmpActService.getCmpActKindList();
		req.setAttribute("kindlist", kindlist);
		return this.getWapJsp("admin/actkindlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createkind(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("name");
		CmpActKind o = new CmpActKind();
		o.setName(DataUtil.toHtmlRow(name));
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setAttribute("o", o);
			req.setText(String.valueOf(code));
			return "/e/admin/cmpact_kindlist.do";
		}
		if (!this.cmpActService.createCmpActKind(o)) {
			req.setText(String.valueOf(Err.CMPACTKIND_NAME_DUPLICATE));
			return "/e/admin/cmpact_kindlist.do";
		}
		this.setOpFuncSuccessMsg(req);
		return "r:/e/admin/cmpact_kindlist.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updatekind(HkRequest req, HkResponse resp) throws Exception {
		long kindId = req.getLongAndSetAttr("kindId");
		String name = req.getString("name");
		CmpActKind o = this.cmpActService.getCmpActKind(kindId);
		o.setName(DataUtil.toHtmlRow(name));
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setAttribute("o", o);
			req.setText(String.valueOf(code));
			return "/e/admin/cmpact_kindlist.do";
		}
		if (!this.cmpActService.updateCmpActKind(o)) {
			req.setText(String.valueOf(Err.CMPACTKIND_NAME_DUPLICATE));
			return "/e/admin/cmpact_kindlist.do";
		}
		this.setOpFuncSuccessMsg(req);
		return "r:/e/admin/cmpact_kindlist.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toupdatekind(HkRequest req, HkResponse resp) throws Exception {
		long kindId = req.getLongAndSetAttr("kindId");
		CmpActKind o = (CmpActKind) req.getAttribute("o");
		if (o == null) {
			o = this.cmpActService.getCmpActKind(kindId);
		}
		req.setAttribute("o", o);
		return this.getWapJsp("admin/updateactkind.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String cfmdelkind(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("kindId");
		return this.getWapJsp("admin/cfmdelkind.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delkind(HkRequest req, HkResponse resp) throws Exception {
		long kindId = req.getLongAndSetAttr("kindId");
		if (req.getString("ok") != null) {
			this.cmpActService.deleteCmpActKind(kindId);
			this.setOpFuncSuccessMsg(req);
		}
		return "r:/e/admin/cmpact_kindlist.do";
	}
}