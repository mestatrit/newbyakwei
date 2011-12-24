package com.hk.web.hk4.venue.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpActor;
import com.hk.bean.CmpActorSvrRef;
import com.hk.bean.CmpPhotoSet;
import com.hk.bean.CmpPhotoSetRef;
import com.hk.bean.CmpSvr;
import com.hk.bean.Company;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpSvrService;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.CompanyService;
import com.hk.svr.processor.CmpActorProcessor;
import com.hk.svr.processor.CmpSvrProcessor;
import com.hk.svr.processor.CompanyPhotoProcessor;
import com.hk.web.pub.action.BaseAction;

/**
 * 工作人员action
 * 
 * @author akwei
 */
@Component("/h4/venue/svr")
public class SvrAction extends BaseAction {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CmpActorProcessor cmpActorProcessor;

	@Autowired
	private CmpSvrService cmpSvrService;

	@Autowired
	private CompanyPhotoService companyPhotoService;

	@Autowired
	private CompanyPhotoProcessor companyPhotoProcessor;

	@Autowired
	private CmpSvrProcessor cmpSvrProcessor;

	public String execute(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		long svrId = req.getLongAndSetAttr("svrId");
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
		if (cmpSvr.getPhotosetId() > 0) {
			CmpPhotoSet cmpPhotoSet = this.companyPhotoService.getCmpPhotoSet(
					companyId, cmpSvr.getPhotosetId());
			if (cmpPhotoSet != null) {
				List<CmpPhotoSetRef> photoreflist = this.companyPhotoProcessor
						.getCmpPhotoSetRefListByCompanyIdAndSetId(companyId,
								cmpPhotoSet.getSetId(), true, 0, 300);
				req.setAttribute("photoreflist", photoreflist);
			}
		}
		// 其他产品服务
		List<CmpSvr> svrlist = this.cmpSvrService.getCmpSvrListByCompanyId(
				companyId, null, 0, 7);
		for (CmpSvr o : svrlist) {
			if (o.getSvrId() == svrId) {
				svrlist.remove(o);
				break;
			}
		}
		// 提供此服务的人
		List<CmpActorSvrRef> actorsvrreflist = this.cmpSvrProcessor
				.getCmpActorSvrRefListByCompanyIdAndSvrId(companyId, svrId,
						true, true, 0, 20);
		req.setAttribute("actorsvrreflist", actorsvrreflist);
		req.setAttribute("svrlist", svrlist);
		return this.getWeb4Jsp("venue/meifa/svr.jsp");
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
		List<CmpSvr> svrlist = this.cmpSvrService.getCmpSvrListByCompanyId(
				companyId, null, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, svrlist);
		req.setAttribute("svrlist", svrlist);
		List<CmpActor> actorlist = this.cmpActorProcessor
				.getCmpActorListByCompanyIdForCanReserve(companyId, true, 0, 6);
		req.setAttribute("actorlist", actorlist);
		return this.getWeb4Jsp("venue/meifa/svrlist.jsp");
	}
}