package web.epp.mgr.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpPhotoSet;
import com.hk.bean.CmpSvr;
import com.hk.bean.CmpSvrKind;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpSvrService;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.pub.Err;

@Component("/epp/web/op/webadmin/svr")
public class SvrAction extends EppBaseAction {

	@Autowired
	private CmpSvrService cmpSvrService;

	@Autowired
	private CompanyPhotoService companyPhotoService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_37", 1);
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		String name = req.getHtmlRow("name");
		req.setEncodeAttribute("name", name);
		List<CmpSvr> list = this.cmpSvrService.getCmpSvrListByCompanyId(
				companyId, name, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebPath("admin/svr/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-23
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_37", 1);
		if (this.isForwardPage(req)) {
			return this.getWebPath("admin/svr/create.jsp");
		}
		long companyId = req.getLong("companyId");
		CmpSvr cmpSvr = new CmpSvr();
		cmpSvr.setCompanyId(companyId);
		cmpSvr.setName(req.getHtmlRow("name"));
		cmpSvr.setIntro(req.getHtml("intro"));
		cmpSvr.setPrice(req.getDouble("price"));
		cmpSvr.setSvrmin(req.getInt("svrmin"));
		cmpSvr.setKindId(req.getLong("kindId"));
		int code = cmpSvr.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		this.cmpSvrService.createCmpSvr(cmpSvr);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-23
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_37", 1);
		long companyId = req.getLong("companyId");
		long svrId = req.getLongAndSetAttr("svrId");
		CmpSvr cmpSvr = this.cmpSvrService.getCmpSvr(companyId, svrId);
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpSvr", cmpSvr);
			if (cmpSvr.getPhotosetId() > 0) {
				CmpPhotoSet cmpPhotoSet = this.companyPhotoService
						.getCmpPhotoSet(companyId, cmpSvr.getPhotosetId());
				req.setAttribute("cmpPhotoSet", cmpPhotoSet);
			}
			return this.getWebPath("admin/svr/update.jsp");
		}
		cmpSvr.setCompanyId(companyId);
		cmpSvr.setName(req.getHtmlRow("name"));
		cmpSvr.setIntro(req.getHtml("intro"));
		cmpSvr.setPrice(req.getDouble("price"));
		cmpSvr.setSvrmin(req.getInt("svrmin"));
		cmpSvr.setKindId(req.getLong("kindId"));
		int code = cmpSvr.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		this.cmpSvrService.updateCmpSvr(cmpSvr);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-23
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long svrId = req.getLongAndSetAttr("svrId");
		CmpSvr cmpSvr = this.cmpSvrService.getCmpSvr(companyId, svrId);
		if (cmpSvr == null || cmpSvr.getCompanyId() != companyId) {
			return null;
		}
		this.cmpSvrService.deleteCmpSvr(companyId, svrId);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-23
	 */
	public String createkind(HkRequest req, HkResponse resp) {
		if (this.isForwardPage(req)) {
			return this.getWebPath("admin/svr/createkind.jsp");
		}
		long companyId = req.getLong("companyId");
		CmpSvrKind cmpSvrKind = new CmpSvrKind();
		cmpSvrKind.setCompanyId(companyId);
		cmpSvrKind.setName(req.getHtmlRow("name"));
		int code = cmpSvrKind.valiate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		this.cmpSvrService.createCmpSvrKind(cmpSvrKind);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-23
	 */
	public String updatekind(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long kindId = req.getLongAndSetAttr("kindId");
		CmpSvrKind cmpSvrKind = this.cmpSvrService.getCmpSvrKind(companyId,
				kindId);
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpSvrKind", cmpSvrKind);
			return this.getWebPath("admin/svr/updatekind.jsp");
		}
		cmpSvrKind.setName(req.getHtmlRow("name"));
		int code = cmpSvrKind.valiate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		this.cmpSvrService.updateCmpSvrKind(cmpSvrKind);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-23
	 */
	public String delkind(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long kindId = req.getLong("kindId");
		CmpSvrKind cmpSvrKind = this.cmpSvrService.getCmpSvrKind(companyId,
				kindId);
		if (cmpSvrKind == null || cmpSvrKind.getCompanyId() != companyId) {
			return null;
		}
		this.cmpSvrService.deleteCmpSvrKind(companyId, kindId);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-23
	 */
	public String kindlist(HkRequest req, HkResponse resp) {
		req.setAttribute("active_38", 1);
		long companyId = req.getLong("companyId");
		List<CmpSvrKind> list = this.cmpSvrService
				.getCmpSvrKindListByCompanyId(companyId);
		req.setAttribute("list", list);
		return this.getWebPath("admin/svr/kindlist.jsp");
	}
}