package com.hk.web.pub.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.bean.AdminUser;
import com.hk.bean.Bomber;
import com.hk.bean.City;
import com.hk.bean.Company;
import com.hk.bean.IpCity;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.web.action.PathProcesser;
import com.hk.frame.web.http.HkFilter;
import com.hk.svr.BombService;
import com.hk.svr.CompanyService;
import com.hk.svr.IpCityService;
import com.hk.svr.UserService;
import com.hk.svr.ZoneService;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.cmpunion.action.CmpUnionUtil;
import com.hk.web.util.HkStatus;
import com.hk.web.util.HkWebConfig;
import com.hk.web.util.HkWebUtil;
import com.hk.web.util.HttpShoppingCard;

public class LoginUserFilter extends HkFilter {

	/**
	 * 需要登录才能操作的uri
	 */
	private Set<String> loginSet = new HashSet<String>();

	/**
	 * 管理员的uri
	 */
	private Set<String> adminSet = new HashSet<String>();

	/**
	 * 足迹管理权限
	 */
	private Set<String> opCmpSet = new HashSet<String>();

	/**
	 * 足迹管理权限
	 */
	private Set<String> opAuthCmpSet = new HashSet<String>();

	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String uri = request.getRequestURI();
		if (uri.indexOf("api/") != -1 || uri.startsWith("/epp/")
				|| uri.startsWith("/sys/")) {
			chain.doFilter(request, response);
			return;
		}
		// 其他网站的逻辑
		if (uri.startsWith("/union/")) {
			this.filterOtherSite(request, response, chain);
		}
		else {// 火酷网逻辑
			this.filterHuoku(request, response, chain);
		}
	}

	private void filterHuoku(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String uri = request.getRequestURI();
		if (uri.startsWith("/logout") || uri.startsWith("/login")) {
			chain.doFilter(request, response);
			return;
		}
		this.loadShoppingCard(request);
		// boolean pcbrowse = ServletUtil.isPc(request);
		// pcbrowse = false;
		// int wapflg = ServletUtil.getInt(request, "wapflg");
		// if (wapflg == 1) {
		// pcbrowse = false;
		// }
		// int pcflg = ServletUtil.getInt(request, "pcflg");
		// if (pcflg == 1) {
		// pcbrowse = true;
		// }
		// request.setAttribute("pcbrowse", pcbrowse);
		boolean userLogin = false;
		User loginUser = null;
		HkStatus hkStatus = HkWebUtil.getHkStatus(request);
		if (hkStatus != null) {
			request.setAttribute(HkWebUtil.HK_STATUS, hkStatus);
			if (hkStatus.getUserId() > 0) {
				loginUser = (User) request.getSession().getAttribute(
						HkWebUtil.LOGIN_USER);
				if (loginUser == null) {
					UserService userService = (UserService) HkUtil
							.getBean("userService");
					loginUser = userService.getUser(hkStatus.getUserId());
				}
			}
		}
		if (loginUser != null) {
			// 检查用户是否被禁止
			// if (this.isUserStop(loginUser, request)) {
			// HkWebUtil.removeLoginUser(request, response);
			// }
			userLogin = true;
			request.getSession().setAttribute(HkWebUtil.LOGIN_USER, loginUser);
			request.setAttribute(HkWebUtil.LOGIN_USER, loginUser);
			request.setAttribute("login", true);
			// HkWebUtil.initAdminUser(request, loginUser.getUserId());
		}
		request.setAttribute("userLogin", userLogin);
		this.getPcityId(loginUser, request, response);
		if (uri.indexOf("/op/") != -1 || uri.indexOf("/admin/") != -1
				|| this.mustLogin(uri) || uri.indexOf("_op") != -1) {
			// 必须登录才能通过
			if (loginUser == null) {
				this.tologin(request, response);
				return;
			}
			// 具有地区管理员操作，必须验证是否是地区管理员
			// if (uri.indexOf("/op/cmd/") != -1) {
			// ZoneAdminService zoneAdminService = (ZoneAdminService) HkUtil
			// .getBean("zoneAdminService");
			// ZoneAdmin zoneAdmin = zoneAdminService.getZoneAdmin(loginUser
			// .getUserId());
			// request.setAttribute("zoneAdmin", zoneAdmin);
			// }
			if (!this.fileerHuokuAdmin(uri, loginUser)) {
				this.tologin(request, response);
				return;
			}
			if (!this.bombFilter(loginUser, request)) {
				this.tologin(request, response);
				return;
			}
			if (!this.doOpCmpFilter(uri, request)) {// 更新足迹权限
				return;
			}
			if (!this.doMgrCmpFilter(uri, request)) {// 足迹认领后的权限
				return;
			}
		}
		// 其他管理
		chain.doFilter(request, response);
	}

	private void tologin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (uri.startsWith("/h4/")) {
			// PathProcesser.proccessResult("r:/login", request, response);
			PathProcesser.proccessResult("r:/h4/login.do?return_url="
					+ DataUtil.urlEncoder(ServletUtil.getReturnUrl(request)),
					request, response);
			return;
		}
		String path = "http://" + HkWebConfig.getWebDomain()
				+ "/tologin.do?return_url="
				+ DataUtil.urlEncoder(ServletUtil.getReturnUrl(request));
		PathProcesser.proccessResult(path, request, response);
		return;
	}

	private void filterOtherSite(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String uri = request.getRequestURI();
		this.loadShoppingCard(request);
		boolean pcbrowse = ServletUtil.isPc(request);
		pcbrowse = false;
		int wapflg = ServletUtil.getInt(request, "wapflg");
		if (wapflg == 1) {
			pcbrowse = false;
		}
		int pcflg = ServletUtil.getInt(request, "pcflg");
		if (pcflg == 1) {
			pcbrowse = true;
		}
		request.setAttribute("pcbrowse", pcbrowse);
		User loginUser = CmpUnionUtil.getLoginUser(request);
		if (uri.indexOf("/op/") != -1) {
			// 必须登录才能通过
			if (loginUser == null) {
				String path = "r:/union/login_tologin.do?uid="
						+ ServletUtil.getLong(request, "uid")
						+ "&return_url="
						+ DataUtil
								.urlEncoder(ServletUtil.getReturnUrl(request));
				PathProcesser.proccessResult(path, request, response);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private void loadShoppingCard(HttpServletRequest request) {
		HttpShoppingCard httpShoppingCard = new HttpShoppingCard(request);
		request.setAttribute(HkWebUtil.HTTPSHOPPINGCARDATTR, httpShoppingCard);
	}

	private void getPcityId(User loginUser, HttpServletRequest req,
			HttpServletResponse resp) {
		int pcityId = HkWebUtil.getPcityId(req);
		if (pcityId < 1 && loginUser != null) {
			pcityId = loginUser.getPcityId();
		}
		if (pcityId == 0) {// 根据ip查找
			IpCityService ipCityService = (IpCityService) HkUtil
					.getBean("ipCityService");
			IpCity ipCity = ipCityService.getIpCityByIp(req.getRemoteAddr());
			if (ipCity != null) {
				ZoneService zoneService = (ZoneService) HkUtil
						.getBean("zoneService");
				String name = DataUtil.filterZoneName(ipCity.getName());
				City city = zoneService.getCityLike(name);
				if (city != null) {
					pcityId = city.getCityId();
				}
			}
		}
		if (pcityId == 0) {// 查不到，就设置为-1，下次不再查找
			HkWebUtil.setPcityId(req, resp, -1);
		}
		if (pcityId > 0) {
			if (loginUser != null && loginUser.getPcityId() <= 0) {
				UserService userService = (UserService) HkUtil
						.getBean("userService");
				userService.updateUserPcityId(loginUser.getUserId(), pcityId);
				loginUser.setPcityId(pcityId);
				HkWebUtil.setPcityId(req, resp, pcityId);
			}
			else {// 如果session中的pcityid不符当前pcityid，就以当前pcityid为准，更新数据库中用户pcityid
				if (loginUser != null) {
					if (pcityId != loginUser.getPcityId()) {
						loginUser.setPcityId(pcityId);
						UserService userService = (UserService) HkUtil
								.getBean("userService");
						userService.updateUserPcityId(loginUser.getUserId(),
								pcityId);
					}
				}
			}
			req.setAttribute(HkWebUtil.SYS_ZONE_PCITYID_ATTR_KEY, pcityId);
			req.setAttribute(HkWebUtil.SYS_ZONE_PCITY_ATTR_KEY, ZoneUtil
					.getPcity(pcityId));
		}
	}

	private boolean mustLogin(String uri) {
		for (String s : loginSet) {
			if (uri.startsWith(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 如果uri是管理员的操作，则进行管理员审核，当用户为管理员是返回true,可以通过，不是管理员时返回false，不能通过。
	 * 如果uri不是管理员操作时，返回true
	 * 
	 * @param uri
	 * @param loginUser
	 * @return
	 *         2010-8-12
	 */
	private boolean fileerHuokuAdmin(String uri, User loginUser) {
		if (loginUser == null) {
			return false;
		}
		for (String s : adminSet) {
			if (uri.startsWith(s)) {
				UserService userService = (UserService) HkUtil
						.getBean("userService");
				AdminUser adminUser = userService.getAdminUser(loginUser
						.getUserId());
				if (adminUser != null) {
					return true;
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否可以更新足迹
	 * 
	 * @param uri
	 * @param request
	 * @return
	 *         2010-8-12
	 */
	private boolean doOpCmpFilter(String uri, HttpServletRequest request) {
		for (String s : opCmpSet) {
			if (uri.startsWith(s)) {
				return this.opCmpFilter(uri, request);
			}
		}
		return true;
	}

	/**
	 * 是否是被认领的足迹可以使用
	 * 
	 * @param uri
	 * @param request
	 * @return
	 *         2010-8-12
	 */
	private boolean doMgrCmpFilter(String uri, HttpServletRequest request) {
		for (String s : this.opAuthCmpSet) {
			if (uri.startsWith(s)) {
				return this.mgrCmpFilter(request);
			}
		}
		return true;
	}

	private boolean mgrCmpFilter(HttpServletRequest request) {
		User loginUser = HkWebUtil.getLoginUser(request);
		long companyId = ServletUtil.getLong(request, "companyId");
		if (companyId > 0) {
			CompanyService companyService = (CompanyService) HkUtil
					.getBean("companyService");
			Company o = companyService.getCompany(companyId);
			if (o == null) {
				return false;
			}
			// 如果企业没有被认领 ,不合法进入
			if (o.getUserId() > 0) {
				if (loginUser.getUserId() != o.getUserId()) {
					if (!HkWebUtil.isAdminUser(request)) {// 不合法进入
						ServletUtil.setSessionText(request,
								"func.when_authed_company_nopower_edit");
						return false;
					}
				}
			}
			else {
				ServletUtil.setSessionText(request,
						"func.when_authed_company_nopower_edit");
				return false;
			}
		}
		return true;
	}

	private boolean opCmpFilter(String uri, HttpServletRequest request) {
		if (uri.indexOf("/review/op/op") != -1) {
			return true;
		}
		long companyId = ServletUtil.getLong(request, "companyId");
		request.setAttribute("companyId", companyId);
		User loginUser = HkWebUtil.getLoginUser(request);
		if (companyId > 0) {
			CompanyService companyService = (CompanyService) HkUtil
					.getBean("companyService");
			Company o = companyService.getCompany(companyId);
			if (o == null) {
				return false;
			}
			if (o.isFreeze()) {
				ServletUtil.setSessionText(request,
						"func.company.cannotopforfreeze");
				return false;
			}
			// 如果企业已经被认领或者试用
			if (o.getUserId() > 0) {
				if (loginUser.getUserId() != o.getUserId()) {// 不合法进入
					if (!HkWebUtil.isAdminUser(request)) {// 不合法进入
						ServletUtil.setSessionText(request,
								"func.when_authed_company_nopower_edit");
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 是否是炸弹管理员
	 * 
	 * @param loginUser
	 * @param req
	 * @return
	 *         2010-8-12
	 */
	private boolean bombFilter(User loginUser, HttpServletRequest req) {
		if (!req.getRequestURI().startsWith("/bomb/")
				&& !req.getRequestURI().startsWith("/adminbomb/")) {
			return true;
		}
		BombService bombService = (BombService) HkUtil.getBean("bombService");
		Bomber bomber = bombService.getBomber(loginUser.getUserId());
		if (bomber == null) {
			return false;
		}
		if (req.getRequestURI().startsWith("/adminbomb/")) {
			if (bomber.getUserLevel() == Bomber.USERLEVEL_NORMAL) {
				return false;
			}
		}
		boolean superAdmin = false;
		boolean admin = false;
		if (bomber.getUserLevel() == Bomber.USERLEVEL_ADMIN) {
			admin = true;
		}
		if (bomber.getUserLevel() == Bomber.USERLEVEL_SUPERADMIN) {
			superAdmin = true;
		}
		req.setAttribute("superAdmin", superAdmin);
		req.setAttribute("admin", admin);
		return true;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String v = filterConfig.getInitParameter("login_conf");
		if (v != null) {
			String[] vs = v.split(",");
			if (vs != null) {
				for (int i = 0; i < vs.length; i++) {
					loginSet.add(vs[i]);
				}
			}
		}
		v = filterConfig.getInitParameter("admin_conf");
		if (v != null) {
			String[] vs = v.split(",");
			if (vs != null) {
				for (int i = 0; i < vs.length; i++) {
					adminSet.add(vs[i]);
				}
			}
		}
		v = filterConfig.getInitParameter("opcmp_conf");
		if (v != null) {
			String[] vs = v.split(",");
			if (vs != null) {
				for (int i = 0; i < vs.length; i++) {
					opCmpSet.add(vs[i]);
				}
			}
		}
		v = filterConfig.getInitParameter("opauthcmp_conf");
		if (v != null) {
			String[] vs = v.split(",");
			if (vs != null) {
				for (int i = 0; i < vs.length; i++) {
					opAuthCmpSet.add(vs[i]);
				}
			}
		}
		super.init(filterConfig);
	}

	public boolean isUserStop(User loginUser, HttpServletRequest req) {
		Boolean status = (Boolean) req.getSession().getAttribute(
				HkWebUtil.USERSTOPSTATUS_ATTR_KEY);
		if (status == null) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			UserOtherInfo info = userService.getUserOtherInfo(loginUser
					.getUserId());
			if (info != null) {
				status = new Boolean(info.isStop());
			}
			else {
				status = false;
			}
			req.getSession().setAttribute(HkWebUtil.USERSTOPSTATUS_ATTR_KEY,
					status);
		}
		return status;
	}
}