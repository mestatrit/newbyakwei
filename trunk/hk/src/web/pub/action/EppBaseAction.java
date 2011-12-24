package web.pub.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import web.pub.util.WebUtil;

import com.hk.bean.CmpAdminUser;
import com.hk.bean.CmpInfo;
import com.hk.bean.CmpNav;
import com.hk.bean.CmpOrgNav;
import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.action.Action;
import com.hk.frame.web.http.HkRequest;
import com.hk.svr.CmpAdminUserService;
import com.hk.svr.CmpNavService;
import com.hk.svr.CmpOrgService;
import com.hk.svr.pub.Err;

public abstract class EppBaseAction implements Action {

	protected com.hk.bean.User getLoginUser2(HkRequest req) {
		return WebUtil.getLoginUser2(req);
	}

	/**
	 * @param req
	 * @param jspFileName
	 * @return
	 */
	protected String getWapPath(HkRequest req, String jspFileName) {
		return "/WEB-INF/wap/" + jspFileName;
	}

	protected String getWapPath(String jspFileName) {
		return "/WEB-INF/cmpwap/" + jspFileName;
	}

	protected String getWebPath(String jspFileName) {
		return "/WEB-INF/cmpweb/" + jspFileName;
	}

	/**
	 * @param req
	 * @param jspFileName
	 * @return
	 */
	protected String getMgrJspPath(HkRequest req, String jspFileName) {
		return "/WEB-INF/wap/mgr/" + jspFileName;
	}

	protected boolean isLogin(HkRequest req) {
		if (this.getLoginUser2(req) != null) {
			return true;
		}
		return false;
	}

	protected boolean isNotLogin(HkRequest req) {
		if (this.getLoginUser2(req) == null) {
			return true;
		}
		return false;
	}

	protected String getLoginPath(HkRequest req) {
		String r = DataUtil.urlEncoder(req.getRequestURL().toString() + "?"
				+ req.getQueryString());
		return "r:/epp/login.do?return_url=" + r;
	}

	protected void processListForPage(SimplePage page, List<?> list) {
		if (list.size() == page.getSize() + 1) {
			page.setHasNext(true);
			list.remove(page.getSize());
		}
	}

	protected void setOpFuncSuccessMsg(HkRequest req) {
		req.setSessionText("op.submitinfook");
	}

	protected void setSuccessMsg(HkRequest req) {
		req.setSessionText("op.setting.op.success");
	}

	protected void setDelSuccessMsg(HkRequest req) {
		req.setSessionText("view2.data.delete.ok");
	}

	protected void setPinkSuccessMsg(HkRequest req) {
		req.setSessionText("view2.data.pink.ok");
	}

	protected void setDelPinkSuccessMsg(HkRequest req) {
		req.setSessionText("view2.data.delpink.ok");
	}

	protected String onError(HkRequest req, int code, String functionName,
			Object respValue) {
		req.setAttribute("error", code);
		req.setAttribute("error_msg", req.getText(String.valueOf(code)));
		req.setAttribute("functionName", functionName);
		if (respValue != null) {
			req.setAttribute("respValue", respValue);
		}
		return this.getWebPath("pub/onerror.jsp");
	}

	protected String onError(HkRequest req, int code, Object[] args,
			String functionName, Object respValue) {
		req.setAttribute("error", code);
		req.setAttribute("error_msg", req.getText(String.valueOf(code), args));
		req.setAttribute("functionName", functionName);
		if (respValue != null) {
			req.setAttribute("respValue", respValue);
		}
		return this.getWebPath("pub/onerror.jsp");
	}

	protected String onErrorList(HkRequest req, List<Integer> list,
			String functionName) {
		Map<String, String> map = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		for (Integer i : list) {
			map.put(String.valueOf("error_" + i), req
					.getText(String.valueOf(i)));
			sb.append(i).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String json = DataUtil.toJson(map);
		req.setAttribute("json", json);
		req.setAttribute("errorlist", sb.toString());
		req.setAttribute("functionName", functionName);
		return this.getWebPath("pub/onerrorlist.jsp");
	}

	protected String onSuccess2(HkRequest req, String functionName,
			Object respValue) {
		return this.onError(req, Err.SUCCESS, functionName, respValue);
	}

	protected boolean isCmpAdminUser(HkRequest req) {
		long companyId = req.getLong("companyId");
		User loginUser = this.getLoginUser2(req);
		CmpAdminUserService cmpAdminUserService = (CmpAdminUserService) HkUtil
				.getBean("cmpAdminUserService");
		CmpAdminUser cmpAdminUser = cmpAdminUserService
				.getCmpAdminUserByCompanyIdAndUserId(companyId, loginUser
						.getUserId());
		if (cmpAdminUser != null) {
			return true;
		}
		if (this.isSuperAdmin(req)) {
			return true;
		}
		return false;
	}

	protected boolean isSuperAdmin(HkRequest req) {
		User loginUser = this.getLoginUser2(req);
		Company company = (Company) req.getAttribute("o");
		if (company.getUserId() == loginUser.getUserId()) {
			return true;
		}
		return false;
	}

	protected List<CmpNav> getMiddleList(List<CmpNav> list) {
		List<CmpNav> middle_navlist = new ArrayList<CmpNav>();
		for (CmpNav o : list) {
			if (o.getHomepos() == CmpNav.HOMEPOS_MIDDLE) {
				middle_navlist.add(o);
			}
		}
		return middle_navlist;
	}

	protected List<CmpNav> getRightList(List<CmpNav> list) {
		List<CmpNav> right_navlist = new ArrayList<CmpNav>();
		for (CmpNav o : list) {
			if (o.getHomepos() == CmpNav.HOMEPOS_RIGHT) {
				right_navlist.add(o);
			}
		}
		return right_navlist;
	}

	protected void setCmpNavInfo(HkRequest req) {
		long navId = req.getLongAndSetAttr("navId");
		long companyId = req.getLong("companyId");
		CmpNavService cmpNavService = (CmpNavService) HkUtil
				.getBean("cmpNavService");
		CmpNav cmpNav = cmpNavService.getCmpNav(navId);
		if (cmpNav == null) {
			return;
		}
		req.setAttribute("cmpNav", cmpNav);
		req.setAttribute("parent_cmpNav", cmpNavService.getCmpNav(cmpNav
				.getParentId()));
		if (cmpNav.getParentId() > 0) {
			List<CmpNav> children = cmpNavService
					.getCmpNavListByCompanyIdAndParentId(companyId, cmpNav
							.getParentId());
			req.setAttribute("children", children);
		}
	}

	protected boolean isForwardPage(HkRequest req) {
		if (req.getInt("ch") == 0) {
			return true;
		}
		return false;
	}

	protected List<CmpOrgNav> loadOrgInfo(HkRequest req) {
		CmpOrgService cmpOrgService = (CmpOrgService) HkUtil
				.getBean("cmpOrgService");
		long orgId = req.getLongAndSetAttr("orgId");
		long companyId = req.getLong("companyId");
		long navId = req.getLongAndSetAttr("orgnavId");
		List<CmpOrgNav> cmporgnavlist = cmpOrgService
				.getCmpOrgNavListByCompanyIdAndOrgId(companyId, orgId);
		if (navId > 0) {
			for (CmpOrgNav o : cmporgnavlist) {
				if (o.getNavId() == navId) {
					req.setAttribute("cmpOrgNav", o);
					break;
				}
			}
		}
		req.setAttribute("cmporgnavlist", cmporgnavlist);
		return cmporgnavlist;
	}

	protected boolean isCanAdminOrg(HkRequest req) {
		Boolean adminorg = (Boolean) req.getAttribute("adminorg");
		if (adminorg == null) {
			return false;
		}
		return adminorg;
	}

	public int getCmpflg(HkRequest req) {
		Company o = (Company) req.getAttribute("o");
		return o.getCmpflg();
	}

	public int getTmlflg(HkRequest req) {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		return cmpInfo.getTmlflg();
	}

	protected boolean isWap(HkRequest req) {
		if (ServletUtil.isWap(req) || req.getInt("wapflg") == 1) {
			return true;
		}
		return false;
	}
}