package com.hk.web.cmpunion.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpAct;
import com.hk.bean.CmpActCost;
import com.hk.bean.CmpActKind;
import com.hk.bean.CmpActStepCost;
import com.hk.bean.CmpActUser;
import com.hk.bean.CmpMember;
import com.hk.bean.Pcity;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpActService;
import com.hk.svr.CmpMemberService;
import com.hk.svr.UserService;
import com.hk.svr.pub.ZoneUtil;

@Component("/union/cmpact")
public class CmpUnionCmpActAction extends CmpUnionBaseAction {
	@Autowired
	private CmpActService cmpActService;

	@Autowired
	private UserService userService;

	@Autowired
	private CmpMemberService cmpMemberService;

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
		int userCount = this.cmpActService.countCmpActUserByActId(actId);
		req.setAttribute("userCount", userCount);
		if (userCount > 0) {
			req.setAttribute("moreactuser", true);
		}
		User loginUser = this.getLoginUser(req);
		if (act.isOnlyMemberJoin()) {
			if (loginUser != null) {
				CmpMember cmpMember = this.cmpMemberService.getCmpMember(act
						.getCompanyId(), loginUser.getUserId());
				if (cmpMember != null) {
					req.setAttribute("usermember", true);
				}
			}
		}
		if (loginUser != null) {
			CmpActUser cmpActUser = this.cmpActService
					.getCmpActUserByActIdAndUserId(actId, loginUser.getUserId());
			req.setAttribute("cmpActUser", cmpActUser);
		}
		return this.getUnionWapJsp("act/act.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String user(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLongAndSetAttr("actId");
		CmpAct act = this.cmpActService.getCmpAct(actId);
		if (act == null) {
			return this.getNotFoundForward(resp);
		}
		req.setAttribute("act", act);
		if (act.isUserNeedCheck()) {
			req.setAttribute("needcheck", true);
		}
		SimplePage page = req.getSimplePage(20);
		byte checkflg = req.getByteAndSetAttr("checkflg", (byte) -1);
		List<CmpActUser> list = this.cmpActService.getCmpActUserListByActId(
				actId, checkflg, page.getBegin(), page.getSize() + 1);
		List<Long> idList = new ArrayList<Long>();
		for (CmpActUser o : list) {
			idList.add(o.getUserId());
		}
		Map<Long, User> map = this.userService.getUserMapInId(idList);
		for (CmpActUser o : list) {
			o.setUser(map.get(o.getUserId()));
		}
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			if (loginUser.getUserId() == act.getUserId()) {
				req.setAttribute("actadmin", true);
			}
		}
		int userCount = this.cmpActService.countCmpActUserByActId(actId);
		req.setAttribute("userCount", userCount);
		return this.getUnionWapJsp("act/userlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLong("uid");
		SimplePage page = req.getSimplePage(20);
		List<CmpAct> list = this.cmpActService.getCmpActListForRun(uid, page
				.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getUnionWapJsp("act/actlist.jsp");
	}
}