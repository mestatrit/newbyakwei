package com.hk.web.hk4.venue.action;

import java.util.List;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpActor;
import com.hk.bean.CmpActorPink;
import com.hk.bean.CmpActorSvrRef;
import com.hk.bean.CmpSvr;
import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpActorService;
import com.hk.svr.CmpSvrService;
import com.hk.svr.CompanyService;
import com.hk.svr.processor.CmpActorProcessor;
import com.hk.svr.processor.CmpSvrProcessor;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.HkWebUtil;

/**
 * 工作人员action
 * 
 * @author akwei
 */
@Component("/h4/venue/actor")
public class ActorAction extends BaseAction {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CmpActorService cmpActorService;

	@Autowired
	private CmpSvrProcessor cmpSvrProcessor;

	@Autowired
	private CmpActorProcessor cmpActorProcessor;

	@Autowired
	private CmpSvrService cmpSvrService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		long actorId = req.getLongAndSetAttr("actorId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		CmpActor cmpActor = this.cmpActorService.getCmpActor(actorId);
		if (cmpActor == null) {
			return null;
		}
		List<CmpActorSvrRef> svrreflist = this.cmpSvrProcessor
				.getCmpActorSvrRefListByCompanyIdAndActorId(companyId, actorId,
						true);
		List<CmpActor> actorlist = this.cmpActorProcessor
				.getCmpActorListByCompanyIdForCanReserve(companyId, true, 0, 7);
		for (CmpActor o : actorlist) {
			if (o.getActorId() == actorId) {
				actorlist.remove(o);
				break;
			}
		}
		req.setAttribute("company", company);
		req.setAttribute("cmpActor", cmpActor);
		req.setAttribute("svrreflist", svrreflist);
		req.setAttribute("actorlist", actorlist);
		// 是否是管理员，管理员可以进行推荐美发师工作
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			req.setAttribute("admin", this.isAdminUser(req));
		}
		CmpActorPink cmpActorPink = this.cmpActorService
				.getCmpActorPinkByActorId(actorId);
		req.setAttribute("cmpActorPink", cmpActorPink);
		// // 点评
		// List<CmpActorCmt> cmtlist = this.cmpActorCmtProcessor
		// .getCmpActorCmtListByActorId(actorId, true, false, 0, 11);
		// if (cmtlist.size() == 11) {
		// req.setAttribute("more_cmt", true);
		// cmtlist.remove(10);
		// }
		// req.setAttribute("cmtlist", cmtlist);
		return this.getWeb4Jsp("venue/meifa/actor.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-16
	 */
	public String list(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		req.setAttribute("company", company);
		SimplePage page = req.getSimplePage(20);
		List<CmpActor> actorlist = this.cmpActorProcessor
				.getCmpActorListByCompanyIdForCanReserve(companyId, true, page
						.getBegin(), page.getSize() + 1);
		req.setAttribute("actorlist", actorlist);
		this.processListForPage(page, actorlist);
		List<CmpSvr> svrlist = this.cmpSvrService.getCmpSvrListByCompanyId(
				companyId, null, 0, 6);
		req.setAttribute("svrlist", svrlist);
		return this.getWeb4Jsp("venue/meifa/actorlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-16
	 */
	public String selsvrforactor(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		long svrId = req.getLongAndSetAttr("svrId");
		long actorId = req.getLong("actorId");
		if (svrId > 0) {
			ReserveInfo reserveInfo = new ReserveInfo();
			reserveInfo.addSvrId(svrId);
			this.saveReserveInfo(reserveInfo, req, resp);
		}
		return "r:/h4/op/reserve.do?companyId=" + companyId + "&actorId="
				+ actorId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-16
	 */
	public String listforsvr(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		long svrId = req.getLongAndSetAttr("svrId");
		if (svrId > 0) {
			ReserveInfo reserveInfo = new ReserveInfo();
			reserveInfo.addSvrId(svrId);
			this.saveReserveInfo(reserveInfo, req, resp);
		}
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		CmpSvr cmpSvr = this.cmpSvrService.getCmpSvr(companyId, svrId);
		if (cmpSvr == null) {
			return null;
		}
		req.setAttribute("company", company);
		req.setAttribute("cmpSvr", cmpSvr);
		SimplePage page = req.getSimplePage(20);
		List<CmpActorSvrRef> reflist = this.cmpSvrProcessor
				.getCmpActorSvrRefListByCompanyIdAndSvrId(companyId, svrId,
						true, true, page.getBegin(), page.getSize() + 1);
		req.setAttribute("reflist", reflist);
		return this.getWeb4Jsp("venue/meifa/actorlistforsvr.jsp");
	}

	private void saveReserveInfo(ReserveInfo reserveInfo, HkRequest req,
			HkResponse resp) {
		Cookie cookie = new Cookie("hk_reserve_cookie", reserveInfo.toString());
		cookie.setDomain(req.getServerName());
		cookie.setPath("/");
		cookie.setMaxAge(HkWebUtil.COOKIE_MAXAGE);
		resp.addCookie(cookie);
	}
}