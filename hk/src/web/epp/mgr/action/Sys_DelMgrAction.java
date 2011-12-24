package web.epp.mgr.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import web.pub.action.EppBaseAction;

import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.TemplateService;
import com.hk.svr.pub.Err;

@Deprecated
// @Component("/epp/mgr/mgr")
public class Sys_DelMgrAction extends EppBaseAction {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private TemplateService templateService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		List<com.hk.bean.CmpModule> cmpmoduleList = this.templateService
				.getCmpModuleList(companyId);
		req.setAttribute("cmpmoduleList", cmpmoduleList);
		return this.getMgrJspPath(req, "mgr.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toupdatelogo(HkRequest req, HkResponse resp) throws Exception {
		return this.getMgrJspPath(req, "cmp/updatelogo.jsp");
	}

//	/**
//	 * @param req
//	 * @param resp
//	 * @return
//	 * @throws Exception
//	 */
//	public String updatelogo(HkRequest req, HkResponse resp) throws Exception {
//		File file = req.getFile("f");
//		long companyId = req.getLong("companyId");
//		if (file == null) {
//			return "r:/epp/mgr/mgr_toupdatelogo.do?companyId=" + companyId;
//		}
//		try {
//			this.companyService.updateLogo(companyId, file);
//			req.setSessionText("func.mgrsite.cmp.updatelogo_ok");
//			return "r:/epp/mgr/mgr_toupdatelogo.do?companyId=" + companyId;
//		}
//		catch (IOException e) {
//			req.setSessionText(String.valueOf(Err.IMG_UPLOAD_ERROR));
//			return "r:/epp/mgr/mgr_toupdatelogo.do?companyId=" + companyId;
//		}
//	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toedit(HkRequest req, HkResponse resp) throws Exception {
		return this.getMgrJspPath(req, "cmp/edit.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String edit(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		String name = req.getString("name");
		String addr = req.getString("addr");
		String traffic = req.getString("traffic");
		String tel = req.getString("tel");
		String intro = req.getString("intro");
		com.hk.bean.Company company = this.companyService.getCompany(companyId);
		company.setName(DataUtil.toHtmlRow(name));
		company.setAddr(DataUtil.toHtmlRow(addr));
		company.setTraffic(DataUtil.toHtmlRow(traffic));
		company.setTel(DataUtil.toHtmlRow(tel));
		company.setIntro(DataUtil.toHtml(intro));
		int code = company.validate(true);
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/epp/mgr/mgr_toedit.do";
		}
		this.companyService.updateCompany(company);
		req.setSessionText("func.mgrsite.cmp.update_ok");
		return "r:/epp/mgr/mgr_toedit.do?companyId=" + companyId;
	}
}