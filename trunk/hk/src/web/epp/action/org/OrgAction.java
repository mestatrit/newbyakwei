package web.epp.action.org;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpNav;
import com.hk.bean.CmpOrg;
import com.hk.bean.CmpOrgNav;
import com.hk.bean.CmpOrgStyle;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpOrgService;
import com.hk.svr.processor.CmpOrgProcessor;
import com.hk.svr.pub.Err;

@Component("/epp/web/org/org")
public class OrgAction extends EppBaseAction {

	@Autowired
	private CmpOrgProcessor cmpOrgProcessor;

	@Autowired
	private CmpOrgService cmpOrgService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long orgId = req.getLongAndSetAttr("orgId");
		long companyId = req.getLong("companyId");
		List<CmpOrgNav> cmporgnavlist = this.loadOrgInfo(req);
		if (cmporgnavlist.size() == 0) {
			return null;// 到栏目管理
		}
		// 根据栏目性质，跳转到不同页面
		CmpOrgNav cmpOrgNav = cmporgnavlist.get(0);
		return "r:/edu/" + companyId + "/" + orgId + "/column/"
				+ cmpOrgNav.getNavId();
	}

	/**
	 * 栏目导航
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-8
	 */
	public String nav(HkRequest req, HkResponse resp) throws Exception {
		this.loadOrgInfo(req);
		long orgId = req.getLongAndSetAttr("orgId");
		long companyId = req.getLong("companyId");
		// 根据栏目性质，跳转到不同页面
		CmpOrgNav cmpOrgNav = (CmpOrgNav) req.getAttribute("cmpOrgNav");
		if (cmpOrgNav.getReffunc() == CmpNav.REFFUNC_SINGLECONTENT
				|| cmpOrgNav.getReffunc() == CmpNav.REFFUNC_LISTCONTENT) {
			return "r:/edu/" + companyId + "/" + orgId + "/articles/"
					+ cmpOrgNav.getNavId();
		}
		if (cmpOrgNav.getReffunc() == CmpNav.REFFUNC_MSG) {
			return "r:/edu/" + companyId + "/" + orgId + "/msg/"
					+ cmpOrgNav.getNavId();
		}
		if (cmpOrgNav.getReffunc() == CmpNav.REFFUNC_STUDYAD) {
			return "r:/edu/" + companyId + "/" + orgId + "/zhaosheng/"
					+ cmpOrgNav.getNavId();
		}
		return null;
	}

	/**
	 * 更改标题背景图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-11
	 */
	public String createbg(HkRequest req, HkResponse resp) {
		req.setAttribute("org_active_bg", 1);
		this.loadOrgInfo(req);
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		if (this.isForwardPage(req)) {
			return this.getWebPath("mod/2/0/org/set/createbg.jsp");
		}
		CmpOrg cmpOrg = (CmpOrg) req.getAttribute("cmpOrg");
		try {
			int code = this.cmpOrgProcessor.updateCmpOrgPath(cmpOrg, req
					.getFile("f"));
			if (code != Err.SUCCESS) {
				if (code == Err.IMG_OUTOFSIZE_ERROR) {
					return this.onError(req, code, new Object[] { "100K" },
							"uploaderror", null);
				}
				return this.onError(req, code, "uploaderror", null);
			}
		}
		catch (IOException e) {
			return this.onError(req, Err.UPLOAD_ERROR, "uploaderror", null);
		}
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "uploadok", null);
	}

	/**
	 * 更改机构配色
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-11
	 */
	public String updatestyle(HkRequest req, HkResponse resp) {
		req.setAttribute("org_active_style", 1);
		this.loadOrgInfo(req);
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		CmpOrg cmpOrg = (CmpOrg) req.getAttribute("cmpOrg");
		if (!cmpOrg.isOpenStyle()) {
			return null;
		}
		if (cmpOrg.getStyleData() != null) {
			CmpOrgStyle cmpOrgStyle = new CmpOrgStyle(DataUtil
					.getMapFromJson(cmpOrg.getStyleData()));
			req.setAttribute("cmpOrgStyle", cmpOrgStyle);
		}
		if (this.isForwardPage(req)) {
			return this.getWebPath("mod/2/0/org/set/updatestyle.jsp");
		}
		CmpOrgStyle cmpOrgStyle = new CmpOrgStyle();
		cmpOrgStyle.setTitleColor(req.getHtmlRow("titleColor"));
		cmpOrgStyle.setBgColor(req.getHtmlRow("bgColor"));
		cmpOrgStyle.setLinkColor(req.getHtmlRow("linkColor"));
		cmpOrgStyle.setLinkHoverColor(req.getHtmlRow("linkHoverColor"));
		cmpOrgStyle.setNavLinkHoverBgColor(req
				.getHtmlRow("navLinkHoverBgColor"));
		cmpOrg.setStyleData(cmpOrgStyle.toJsonValue());
		this.cmpOrgService.updateCmpOrg(cmpOrg);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 栏目列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-13
	 */
	public String navlist(HkRequest req, HkResponse resp) throws Exception {
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		this.loadOrgInfo(req);
		req.setAttribute("org_active_navlist", 1);
		return this.getWebPath("mod/2/0/org/nav/list.jsp");
	}

	/**
	 *修改栏目
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-13
	 */
	public String updatenav(HkRequest req, HkResponse resp) throws Exception {
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		long companyId = req.getLong("companyId");
		long oid = req.getLongAndSetAttr("oid");
		long orgId = req.getLong("orgId");
		this.loadOrgInfo(req);
		CmpOrgNav cmpOrgNav = this.cmpOrgService.getCmpOrgNav(companyId, oid);
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpOrgNav", cmpOrgNav);
			return this.getWebPath("mod/2/0/org/nav/update.jsp");
		}
		if (cmpOrgNav.getOrgId() != orgId) {
			return null;
		}
		cmpOrgNav.setName(req.getHtmlRow("name"));
		int code = cmpOrgNav.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		this.cmpOrgService.updateCmpOrgNav(cmpOrgNav);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 *修改栏目
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-13
	 */
	public String delnav(HkRequest req, HkResponse resp) throws Exception {
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		long companyId = req.getLong("companyId");
		long oid = req.getLongAndSetAttr("oid");
		long orgId = req.getLong("orgId");
		this.loadOrgInfo(req);
		CmpOrgNav cmpOrgNav = this.cmpOrgService.getCmpOrgNav(companyId, oid);
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpOrgNav", cmpOrgNav);
			return this.getWebPath("mod/2/0/org/nav/update.jsp");
		}
		if (cmpOrgNav.getOrgId() != orgId) {
			return null;
		}
		cmpOrgNav.setName(req.getHtmlRow("name"));
		int code = cmpOrgNav.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		this.cmpOrgService.updateCmpOrgNav(cmpOrgNav);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 *修改栏目显示顺序
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-13
	 */
	public String moveupnav(HkRequest req, HkResponse resp) throws Exception {
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		long companyId = req.getLong("companyId");
		long orgId = req.getLong("orgId");
		long oid = req.getLong("oid");
		CmpOrgNav cmpOrgNav = this.cmpOrgService.getCmpOrgNav(companyId, oid);
		if (cmpOrgNav.getOrgId() != orgId) {
			return null;
		}
		this.cmpOrgProcessor.moveUpCmpOrgOrder(companyId, orgId, oid);
		return null;
	}

	/**
	 *修改机构信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-13
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		this.loadOrgInfo(req);
		req.setAttribute("org_active_info", 1);
		if (this.isForwardPage(req)) {
			return this.getWebPath("mod/2/0/org/info/update.jsp");
		}
		CmpOrg cmpOrg = (CmpOrg) req.getAttribute("cmpOrg");
		cmpOrg.setName(req.getHtmlRow("name"));
		int code = cmpOrg.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		this.cmpOrgService.updateCmpOrg(cmpOrg);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}
}