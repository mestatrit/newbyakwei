package com.hk.web.company.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpModule;
import com.hk.bean.CmpTemplate;
import com.hk.bean.Company;
import com.hk.bean.Template;
import com.hk.bean.TmlModule;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.TemplateService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.svr.pub.TemplateUtil;
import com.hk.web.pub.action.BaseAction;

/**
 * 此类不再使用
 * 
 * @author akwei
 */
@Deprecated
// @Component("/e/op/tml/op")
public class TmlAction extends BaseAction {

	@Autowired
	private TemplateService templateService;

	@Autowired
	private CompanyService companyService;

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
	public String toedit(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		CmpTemplate cmpTemplate = this.templateService
				.getCmpTemplate(companyId);
		Template template = TemplateUtil
				.geTemplate(cmpTemplate.getTemplateId());
		List<CmpModule> cmpmoduleList = this.templateService
				.getCmpModuleList(companyId);
		if (template.getTmlModuleList().size() > cmpmoduleList.size()) {
			req.setAttribute("can_add_new_module", true);
		}
		req.setAttribute("cmpmoduleList", cmpmoduleList);
		req.setAttribute("o", o);
		req.setAttribute("cmpTemplate", cmpTemplate);
		req.setAttribute("companyId", companyId);
		return "/WEB-INF/page/e/tml/edit.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toaddcmpmodule(HkRequest req, HkResponse resp)
			throws Exception {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		CmpTemplate cmpTemplate = this.templateService
				.getCmpTemplate(companyId);
		Template template = TemplateUtil
				.geTemplate(cmpTemplate.getTemplateId());
		// 模板中的模块
		List<TmlModule> moduleList = template.getTmlModuleList();
		// 企业使用的模块
		List<CmpModule> cmpmoduleList = this.templateService
				.getCmpModuleList(companyId);
		// 未使用的模块
		List<TmlModule> moduleList2 = new ArrayList<TmlModule>();
		moduleList2.addAll(moduleList);
		List<TmlModule> delList = new ArrayList<TmlModule>();
		for (CmpModule m : cmpmoduleList) {
			delList.add(TemplateUtil.geTmlModule(m.getModuleId()));
		}
		moduleList2.removeAll(delList);
		req.setAttribute("modules", moduleList2);
		req.setAttribute("o", o);
		req.setAttribute("templateId", template.getTemplateId());
		req.setAttribute("companyId", companyId);
		return "/WEB-INF/page/e/tml/addcmpmodule.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addcmpmodule(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		int templateId = req.getInt("templateId");
		int moduleId = req.getInt("moduleId");
		String title = req.getString("title");
		String intro = req.getString("intro");
		byte showflg = req.getByte("showflg");
		CmpModule cmpModule = new CmpModule();
		cmpModule.setCompanyId(companyId);
		cmpModule.setTemplateId(templateId);
		cmpModule.setModuleId(moduleId);
		cmpModule.setTitle(title);
		cmpModule.setIntro(intro);
		cmpModule.setShowflg(showflg);
		int code = cmpModule.validate();
		if (code != Err.SUCCESS) {
			req.setAttribute("cmpModule", cmpModule);
			req.setText(code + "");
			return "/e/op/tml/op_toaddcmpmodule.do";
		}
		this.templateService.createCmpModule(cmpModule);
		req.setSessionText("op.exeok");
		return "r:/e/op/tml/op_toedit.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String index(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		CmpTemplate cmpTemplate = this.templateService
				.getCmpTemplate(companyId);
		List<CmpModule> list = this.templateService
				.getCmpModuleListIgnoreHide(companyId);
		req.setAttribute("o", o);
		req.setAttribute("list", list);
		req.setAttribute("companyId", companyId);
		req.setAttribute("cmpTemplate", cmpTemplate);
		return "/WEB-INF/page/e/tml/index.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toeditcmpmodule(HkRequest req, HkResponse resp)
			throws Exception {
		long companyId = req.getLong("companyId");
		long sysId = req.getLong("sysId");
		CmpModule cmpModule = this.templateService.getCmpModule(sysId);
		Template template = TemplateUtil.geTemplate(cmpModule.getTemplateId());
		Company o = this.companyService.getCompany(companyId);
		CmpTemplate cmpTemplate = this.templateService
				.getCmpTemplate(companyId);
		req.setAttribute("o", o);
		req.setAttribute("sysId", sysId);
		req.setAttribute("template", template);
		req.setAttribute("cmpModule", cmpModule);
		req.setAttribute("companyId", companyId);
		req.setAttribute("cmpTemplate", cmpTemplate);
		return "/WEB-INF/page/e/tml/editcmpmodule.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editcmpmodule(HkRequest req, HkResponse resp)
			throws Exception {
		long companyId = req.getLong("companyId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		long sysId = req.getLong("sysId");
		int moduleId = req.getInt("moduleId");
		String title = req.getString("title");
		String intro = req.getString("intro");
		byte showflg = req.getByte("showflg");
		CmpModule cmpModule = this.templateService.getCmpModule(sysId);
		cmpModule.setModuleId(moduleId);
		cmpModule.setTitle(DataUtil.toHtmlRow(title));
		cmpModule.setIntro(DataUtil.toHtml(intro));
		cmpModule.setShowflg(showflg);
		int code = cmpModule.validate();
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/e/op/tml/op_toeditcmpmodule?companyId=" + companyId
					+ "&sysId=" + sysId;
		}
		this.templateService.updateCmpModule(cmpModule);
		req.setSessionText("op.exeok");
		return "r:/e/op/tml/op_toedit.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toadd(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		CmpTemplate cmpTemplate = this.templateService
				.getCmpTemplate(companyId);
		List<Template> list = TemplateUtil.getTemplateListByKindId(o
				.getKindId());
		if (cmpTemplate != null) {
			Template template = TemplateUtil.geTemplate(cmpTemplate
					.getTemplateId());
			req.setAttribute("template", template);
		}
		req.setAttribute("o", o);
		req.setAttribute("list", list);
		req.setAttribute("companyId", companyId);
		req.setAttribute("cmpTemplate", cmpTemplate);
		return "/WEB-INF/page/e/tml/add.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String add(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		int templateId = req.getInt("templateId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		Template template = TemplateUtil.geTemplate(templateId);
		if (template == null) {
			req.setText("func.template.select_template");
			return "/e/op/tml/op_toadd.do?companyId=" + companyId;
		}
		templateService.createCmpTemplate(companyId, templateId);
		req.setSessionText("op.exeok");
		return "r:/e/op/tml/op_toedit.do?companyId=" + companyId;
	}
}