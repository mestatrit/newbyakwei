package web.pub.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.pub.util.EppFuncTip;
import web.pub.util.WebUtil;

import com.hk.bean.CmpAdminUser;
import com.hk.bean.CmpBomber;
import com.hk.bean.CmpInfo;
import com.hk.bean.CmpOrg;
import com.hk.bean.CmpOrgStyle;
import com.hk.bean.CmpSvrCnf;
import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.util.i18n.HkI18n;
import com.hk.frame.web.action.PathProcesser;
import com.hk.frame.web.http.HkFilter;
import com.hk.svr.CmpAdminUserService;
import com.hk.svr.CmpBomberService;
import com.hk.svr.CmpInfoService;
import com.hk.svr.CmpOrgService;
import com.hk.svr.CmpSvrCnfService;

public class SysFilter extends HkFilter {

	/**
	 * uri中含有的关键字(web访问使用)
	 */
	private List<String> loginset = new ArrayList<String>();

	/**
	 * uri中含有的关键字(wap访问使用)
	 */
	private List<String> waploginset = new ArrayList<String>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		loginset.add("_create");
		loginset.add("_delete");
		loginset.add("_update");
		loginset.add("_del");
		loginset.add("_prv");
		waploginset.add("_wapprv");
		super.init(filterConfig);
	}

	/**
	 * 对于uri中出现的相应关键字进行判定，是否是登录用户才具有的权限(对于web访问)
	 * 
	 * @param request
	 * @return
	 *         2010-6-1
	 */
	private boolean mustHasLogin(HttpServletRequest request) {
		String uri = request.getRequestURI();
		for (String s : loginset) {
			if (uri.indexOf(s) != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 对于uri中出现的相应关键字进行判定，是否是登录用户才具有的权限(对于wap访问)
	 * 
	 * @param request
	 * @return
	 *         2010-6-1
	 */
	private boolean mustWapLogin(HttpServletRequest request) {
		String uri = request.getRequestURI();
		for (String s : waploginset) {
			if (uri.indexOf(s) != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 进行信息提示，当未登录用户点击登录用户才可以操作的uri时，设置的提醒信息
	 * 
	 * @param request
	 *            2010-6-1
	 */
	private void setSessionTip(HttpServletRequest request) {
		int func_tip = ServletUtil.getInt(request, "func_tip");
		if (func_tip == EppFuncTip.CHECKIN) {
			ServletUtil.setSessionText(request, "epp.please_login_forcheckin");
		}
	}

	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String uri = request.getRequestURI();
		if (uri.endsWith(".jsp")) {
			chain.doFilter(request, response);
			return;
		}
		CmpInfoService cmpInfoService = (CmpInfoService) HkUtil
				.getBean("cmpInfoService");
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpInfo cmpInfo = (CmpInfo) request.getAttribute("cmpInfo");
		if (cmpInfo == null) {
			cmpInfo = cmpInfoService.getCmpInfo(companyId);
		}
		if (cmpInfo == null) {
			return;
		}
		request.setAttribute("cmpInfo", cmpInfo);
		request.setAttribute(HkI18n.I18N_KEY, HkI18n.getLocale(cmpInfo
				.getLanguage(), true));
		Company company = WebUtil.loadCompany(request, companyId);
		User user = WebUtil.initLoginUser2(request);
		boolean sys_cmpadminuser = false;
		if (user != null) {
			CmpAdminUserService cmpAdminUserService = (CmpAdminUserService) HkUtil
					.getBean("cmpAdminUserService");
			CmpAdminUser cmpAdminUser = cmpAdminUserService
					.getCmpAdminUserByCompanyIdAndUserId(companyId, user
							.getUserId());
			if (cmpAdminUser != null) {
				sys_cmpadminuser = true;
				request.setAttribute("sys_cmpadminuser", true);
			}
			else if (company.getUserId() == user.getUserId()) {
				sys_cmpadminuser = true;
				request.setAttribute("sys_cmpadminuser", true);
			}
		}
		if (uri.indexOf("/epp/web/op/") != -1) {
			if (user == null) {
				this.setSessionTip(request);
				if (ServletUtil.getInt(request, "ajax") == 1) {
					return;
				}
				String r = ServletUtil.getReturnUrl(request);
				PathProcesser.doForward(true, "/epp/login_web.do?companyId="
						+ companyId + "&return_url=" + DataUtil.urlEncoder(r),
						true, request, response);
				return;
			}
		}
		else if (uri.startsWith("/epp/web/") && this.mustHasLogin(request)) {
			if (user == null) {
				this.setSessionTip(request);
				if (ServletUtil.getInt(request, "ajax") == 1) {
					return;
				}
				String r = ServletUtil.getReturnUrl(request);
				PathProcesser.doForward(true, "/epp/login_web.do?companyId="
						+ companyId + "&return_url=" + DataUtil.urlEncoder(r),
						true, request, response);
				return;
			}
		}
		if (uri.startsWith("/epp/mgr/web/")) {// 企业网站超级管理员可进入的uri
			request.setAttribute(HkI18n.I18N_KEY, HkI18n.getLocale(
					HkI18n.SIMPLIFIED_CHINESE, true));
			if (!this.mgrFilter(request)) {
				String r = ServletUtil.getReturnUrl(request);
				PathProcesser.doForward(true, "/epp/login_web.do?companyId="
						+ companyId + "&return_url=" + DataUtil.urlEncoder(r),
						true, request, response);
				return;
			}
		}
		// else if (uri.startsWith("/epp/mgr/")) {// 企业网站超级管理员可进入的uri
		// if (!this.mgrFilter(request)) {
		// String r = ServletUtil.getReturnUrl(request);
		// PathProcesser.doForward(true, "/epp/login_web.do?companyId="
		// + companyId + "&return_url=" + DataUtil.urlEncoder(r),
		// request, response);
		// return;
		// }
		// }
		if (uri.startsWith("/epp/web/op/webadmin/")) {// 企业网站管理员可进入的uri
			request.setAttribute(HkI18n.I18N_KEY, HkI18n.getLocale(
					HkI18n.SIMPLIFIED_CHINESE, true));
			if (!this.adminUserFilter(request)) {
				String r = ServletUtil.getReturnUrl(request);
				PathProcesser.doForward(true, "/epp/login_web.do?companyId="
						+ companyId + "&return_url=" + DataUtil.urlEncoder(r),
						true, request, response);
				return;
			}
			CmpSvrCnfService cmpSvrCnfService = (CmpSvrCnfService) HkUtil
					.getBean("cmpSvrCnfService");
			CmpSvrCnf cmpSvrCnf = cmpSvrCnfService.getCmpSvrCnf(companyId);
			request.setAttribute("cmpSvrCnf", cmpSvrCnf);
		}
		if (uri.startsWith("/epp/web/op/bomber/")) {// 网站内容管理员可进入的uri
			request.setAttribute(HkI18n.I18N_KEY, HkI18n.getLocale(
					HkI18n.SIMPLIFIED_CHINESE, true));
			if (!this.bomberFilter(request)) {
				String r = ServletUtil.getReturnUrl(request);
				PathProcesser.doForward(true, "/epp/login_web.do?companyId="
						+ companyId + "&return_url=" + DataUtil.urlEncoder(r),
						true, request, response);
				return;
			}
		}
		if (this.mustWapLogin(request)) {// wap网站的登录用户与可进入的uri
			if (user == null) {
				String r = ServletUtil.getReturnUrl(request);
				PathProcesser.doForward(true, "/epp/login.do?companyId="
						+ companyId + "&return_url=" + DataUtil.urlEncoder(r),
						true, request, response);
				return;
			}
		}
		if (uri.startsWith("/epp/web/org/")) {// 机构的权限以及数据
			long orgId = ServletUtil.getLong(request, "orgId");
			CmpOrgService cmpOrgService = (CmpOrgService) HkUtil
					.getBean("cmpOrgService");
			CmpOrg cmpOrg = cmpOrgService.getCmpOrg(companyId, orgId);
			if (cmpOrg == null) {
				return;
			}
			request.setAttribute("cmpOrg", cmpOrg);
			if (cmpOrg.getStyleData() != null) {
				CmpOrgStyle sys_CmpOrgStyle = new CmpOrgStyle(DataUtil
						.getMapFromJson(cmpOrg.getStyleData()));
				request.setAttribute("sys_CmpOrgStyle", sys_CmpOrgStyle);
			}
			if (user != null && user.getUserId() == cmpOrg.getUserId()
					&& cmpOrg.isAvailable()) {
				request.setAttribute("adminorg", true);
			}
			else if (sys_cmpadminuser) {
				request.setAttribute("adminorg", true);
			}
		}
		chain.doFilter(request, response);
	}

	private boolean bomberFilter(HttpServletRequest request) {
		Company o = (Company) request.getAttribute("o");
		if (o == null) {
			return false;
		}
		User user = WebUtil.getLoginUser2(request);
		if (user == null) {
			return false;
		}
		if (user.getUserId() == o.getUserId()) {
			return true;
		}
		CmpBomberService cmpBomberService = (CmpBomberService) HkUtil
				.getBean("cmpBomberService");
		CmpBomber cmpBomber = cmpBomberService
				.getCmpBomberByCompanyIdAndUserId(o.getCompanyId(), o
						.getUserId());
		if (cmpBomber != null) {
			return true;
		}
		Boolean sys_cmpadminuser = (Boolean) request
				.getAttribute("sys_cmpadminuser");
		if (sys_cmpadminuser != null && sys_cmpadminuser.booleanValue()) {
			return true;
		}
		CmpAdminUserService cmpAdminUserService = (CmpAdminUserService) HkUtil
				.getBean("cmpAdminUserService");
		CmpAdminUser cmpAdminUser = cmpAdminUserService
				.getCmpAdminUserByCompanyIdAndUserId(o.getCompanyId(), user
						.getUserId());
		if (cmpAdminUser != null) {
			return true;
		}
		return false;
	}

	/**
	 * 网站管理员的判定（包括超级管理员）
	 * 
	 * @param request
	 * @return
	 *         2010-6-1
	 */
	private boolean adminUserFilter(HttpServletRequest request) {
		Company o = (Company) request.getAttribute("o");
		if (o == null) {
			return false;
		}
		User user = WebUtil.getLoginUser2(request);
		if (user == null) {
			return false;
		}
		if (user.getUserId() == o.getUserId()) {
			return true;
		}
		Boolean sys_cmpadminuser = (Boolean) request
				.getAttribute("sys_cmpadminuser");
		if (sys_cmpadminuser != null && sys_cmpadminuser.booleanValue()) {
			return true;
		}
		CmpAdminUserService cmpAdminUserService = (CmpAdminUserService) HkUtil
				.getBean("cmpAdminUserService");
		CmpAdminUser cmpAdminUser = cmpAdminUserService
				.getCmpAdminUserByCompanyIdAndUserId(o.getCompanyId(), user
						.getUserId());
		if (cmpAdminUser != null) {
			return true;
		}
		return false;
	}

	/**
	 * 超级管理员的判定
	 * 
	 * @param request
	 * @return
	 *         2010-6-1
	 */
	private boolean mgrFilter(HttpServletRequest request) {
		Company o = (Company) request.getAttribute("o");
		if (o == null) {
			return false;
		}
		User user = WebUtil.getLoginUser2(request);
		if (user == null) {
			return false;
		}
		if (user.getUserId() == o.getUserId()) {
			return true;
		}
		return false;
	}
}