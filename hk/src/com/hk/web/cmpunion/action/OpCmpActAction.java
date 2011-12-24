package com.hk.web.cmpunion.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpAct;
import com.hk.bean.CmpActUser;
import com.hk.bean.User;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpActService;

@Component("/union/op/cmpact")
public class OpCmpActAction extends CmpUnionBaseAction {
	@Autowired
	private CmpActService cmpActService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String join(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLongAndSetAttr("uid");
		long actId = req.getLongAndSetAttr("actId");
		CmpAct act = this.cmpActService.getCmpAct(actId);
		if (act == null) {
			return this.getNotFoundForward(resp);
		}
		User loginUser = this.getLoginUser(req);
		if (!act.isRun()) {// 活动不在运行，不能 报名
			req.setSessionText("func.nocmpactorcmpactnotbegin");
			return "r:/union/cmpact.do?uid=" + uid + "&actId=" + actId;
		}
		if (act.isEnd()) {// 活动已经停止，不能报名
			req.setSessionText("func.cmpact.expired");
			return "r:/union/cmpact.do?uid=" + uid + "&actId=" + actId;
		}
		if (act.isOnlyMemberJoin()) {// 活动不惜会员，才能报名
			if (this.cmpActService.getCmpActUserByActIdAndUserId(actId,
					loginUser.getUserId()) == null) {
				req.setSessionText("view.cmpact.onlymemberjoin");
				return "r:/union/cmpact.do?uid=" + uid + "&actId=" + actId;
			}
		}
		CmpActUser cmpActUser = new CmpActUser();
		cmpActUser.setActId(actId);
		cmpActUser.setUserId(loginUser.getUserId());
		cmpActUser.setCompanyId(act.getCompanyId());
		if (act.isUserNeedCheck()) {
			cmpActUser.setCheckflg(CmpActUser.CHECKFLG_UNCHECKED);
		}
		else {
			cmpActUser.setCheckflg(CmpActUser.CHECKFLG_Y);
		}
		this.cmpActService.createCmpActUser(cmpActUser);
		if (act.isUserNeedCheck()) {
			req.setSessionText("func.cmpact.cmpactuseraddokandwaitcheck");
		}
		else {
			req.setSessionText("func.cmpact.cmpactuseraddok");
		}
		return "r:/union/cmpact.do?uid=" + uid + "&actId=" + actId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String unjoin(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLongAndSetAttr("uid");
		long actId = req.getLongAndSetAttr("actId");
		User loginUser = this.getLoginUser(req);
		this.cmpActService.deleteCmpActUser(actId, loginUser.getUserId());
		return "r:/union/cmpact.do?uid=" + uid + "&actId=" + actId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String checky(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLongAndSetAttr("actId");
		long uid = req.getLongAndSetAttr("uid");
		CmpAct act = this.cmpActService.getCmpAct(actId);
		if (act.getUserId() != this.getLoginUser(req).getUserId()) {
			return this.getNotFoundForward(resp);
		}
		long userId = req.getLong("userId");
		this.cmpActService.updateCmpActUserCheckflg(actId, userId,
				CmpActUser.CHECKFLG_Y);
		return "r:/union/cmpact_user.do?uid=" + uid + "&actId=" + actId
				+ "&page=" + req.getInt("repage");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String checkn(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLongAndSetAttr("uid");
		long actId = req.getLongAndSetAttr("actId");
		CmpAct act = this.cmpActService.getCmpAct(actId);
		if (act.getUserId() != this.getLoginUser(req).getUserId()) {
			return this.getNotFoundForward(resp);
		}
		long userId = req.getLong("userId");
		this.cmpActService.updateCmpActUserCheckflg(actId, userId,
				CmpActUser.CHECKFLG_N);
		return "r:/union/cmpact_user.do?uid=" + uid + "&actId=" + actId
				+ "&page=" + req.getInt("repage");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String checkcandidate(HkRequest req, HkResponse resp)
			throws Exception {
		long uid = req.getLongAndSetAttr("uid");
		long actId = req.getLongAndSetAttr("actId");
		CmpAct act = this.cmpActService.getCmpAct(actId);
		if (act.getUserId() != this.getLoginUser(req).getUserId()) {
			return this.getNotFoundForward(resp);
		}
		long userId = req.getLong("userId");
		this.cmpActService.updateCmpActUserCheckflg(actId, userId,
				CmpActUser.CHECKFLG_CANDIDATE);
		return "r:/union/cmpact_user.do?uid=" + uid + "&actId=" + actId
				+ "&page=" + req.getInt("repage");
	}
}