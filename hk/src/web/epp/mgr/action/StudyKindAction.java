package web.epp.mgr.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpStudyKind;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpStudyKindService;
import com.hk.svr.pub.Err;

@Component("/epp/web/op/webadmin/studykind")
public class StudyKindAction extends EppBaseAction {

	@Autowired
	private CmpStudyKindService cmpStudyKindService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_33", 1);
		long companyId = req.getLong("companyId");
		long parentId = req.getLongAndSetAttr("parentId");
		if (parentId > 0) {
			CmpStudyKind parent = this.cmpStudyKindService.getCmpStudyKind(
					companyId, parentId);
			req.setAttribute("parent", parent);
		}
		SimplePage page = req.getSimplePage(20);
		String name = req.getHtmlRow("name");
		List<CmpStudyKind> list = this.cmpStudyKindService
				.getCmpStudyKindListByCompanyIdAndParentIdEx(companyId,
						parentId, name, page.getBegin(), page.getSize() + 1);
		req.setAttribute("list", list);
		req.setEncodeAttribute("name", name);
		return this.getWebPath("admin/studykind/list.jsp");
	}

	/**
	 * 创建分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-12
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long parentId = req.getLongAndSetAttr("parentId");
		if (parentId > 0) {
			CmpStudyKind parent = this.cmpStudyKindService.getCmpStudyKind(
					companyId, parentId);
			req.setAttribute("parent", parent);
		}
		if (this.isForwardPage(req)) {
			return this.getWebPath("admin/studykind/create.jsp");
		}
		CmpStudyKind cmpStudyKind = new CmpStudyKind();
		cmpStudyKind.setCompanyId(companyId);
		cmpStudyKind.setParentId(parentId);
		cmpStudyKind.setName(req.getHtmlRow("name"));
		int code = cmpStudyKind.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		this.cmpStudyKindService.createCmpStudyKind(cmpStudyKind);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * 修改分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-12
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long kindId = req.getLongAndSetAttr("kindId");
		CmpStudyKind cmpStudyKind = this.cmpStudyKindService.getCmpStudyKind(
				companyId, kindId);
		long parentId = cmpStudyKind.getParentId();
		req.setAttribute("parentId", parentId);
		req.setAttribute("cmpStudyKind", cmpStudyKind);
		if (parentId > 0) {
			CmpStudyKind parent = this.cmpStudyKindService.getCmpStudyKind(
					companyId, parentId);
			req.setAttribute("parent", parent);
		}
		if (this.isForwardPage(req)) {
			return this.getWebPath("admin/studykind/update.jsp");
		}
		cmpStudyKind.setName(req.getHtmlRow("name"));
		int code = cmpStudyKind.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		this.cmpStudyKindService.updateCmpStudyKind(cmpStudyKind);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-12
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long kindId = req.getLongAndSetAttr("kindId");
		CmpStudyKind cmpOrgStudyKind = this.cmpStudyKindService
				.getCmpStudyKind(companyId, kindId);
		if (cmpOrgStudyKind == null
				|| cmpOrgStudyKind.getCompanyId() != companyId) {
			return null;
		}
		this.cmpStudyKindService.deleteCmpStudyKind(companyId, kindId);
		this.setDelSuccessMsg(req);
		return null;
	}
}