package web.pub.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.frame.util.DesUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ServletUtil;
import com.hk.svr.CompanyService;
import com.hk.svr.UserService;

public class WebUtil {

	public static final String CMPINFO = "cmpInfo";

	public static final String CMPUNION = "cmpUnion";

	public static final String CMPREQUESTINFO = "cmpRequestInfo";

	public static final String LOGINUSER = "loginUser";

	public static final String LOGINUSER_COOKIE = "cookieepploginUser";

	public static String defDomain;

	public static final String URLINFO_ATTR = "urlInfo";

	public static final String SYS_RETURN_INFO = "sys_return_info";

	public static int cookie_maxAge = 14 * 24 * 60 * 60;

	private static DesUtil desUtil = new DesUtil("apiloginuser");

	public static void loadEppInfo(HttpServletRequest request, long companyId) {
		WebUtil.loadCompany(request, companyId);
		initLoginUser2(request);
	}

	public static UrlInfo getUrlInfo(HttpServletRequest request) {
		UrlInfo urlInfo = (UrlInfo) request.getAttribute(URLINFO_ATTR);
		// String ctx = request.getContextPath();
		if (urlInfo == null) {
			urlInfo = new UrlInfo();
			// urlInfo.setUserUrl(ctx + "/home.do?v={0}");
			// urlInfo.setCompanyUrl("#");
			// urlInfo.setTagUrl("#");
			String tadd = (String) request.getAttribute(SYS_RETURN_INFO);
			if (tadd == null) {
				tadd = "";
			}
			urlInfo
					.setUserUrl("http://www.huoku.com/home_viewbynickname.do?v={0}"
							+ tadd);
			urlInfo
					.setCompanyUrl("http://www.huoku.com/e/cmp_viewbyname.do?v={0}"
							+ tadd);
			urlInfo
					.setTagUrl("http://www.huoku.com/laba/taglabalist_viewbyname.do?v={0}"
							+ tadd);
			request.setAttribute(URLINFO_ATTR, urlInfo);
		}
		return urlInfo;
	}

	public static long getCompanyId(HttpServletRequest request) {
		long companyId = ServletUtil.getLong(request, "companyId");
		if (companyId == 0) {
			companyId = (Long) request.getAttribute("companyId");
		}
		return companyId;
	}

	public static Company loadCompany(HttpServletRequest request, long companyId) {
		Company company = (Company) request.getAttribute("o");
		if (company == null) {
			CompanyService companyService = (CompanyService) HkUtil
					.getBean("companyService");
			company = companyService.getCompany(companyId);
		}
		request.setAttribute("o", company);
		request.setAttribute("companyId", companyId);
		return company;
	}

	public static com.hk.bean.User getLoginUser2(HttpServletRequest request) {
		return (com.hk.bean.User) request.getAttribute(LOGINUSER);
	}

	public static com.hk.bean.User initLoginUser2(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(LOGINUSER);
		if (user == null) {
			Cookie uc = getCookie(request, LOGINUSER_COOKIE);
			if (uc != null) {
				try {
					String s = desUtil.decrypt(uc.getValue());
					String[] arr = s.split(":");
					if (arr.length == 3) {
						long userId = Long.parseLong(arr[1]);
						UserService userService = (UserService) HkUtil
								.getBean("userService");
						user = userService.getUser(userId);
					}
				}
				catch (Exception e) {//
				}
			}
		}
		if (user != null) {
			session.setAttribute(LOGINUSER, user);
			request.setAttribute(LOGINUSER, user);
		}
		return user;
	}

	public static void setLoginUser2(HttpServletRequest request,
			HttpServletResponse response, com.hk.bean.User user,
			String login_input, boolean autologin) {
		request.setAttribute(LOGINUSER, user);
		request.getSession().setAttribute(LOGINUSER, user);
		StringBuilder cookie_value = new StringBuilder();
		cookie_value.append(login_input).append(":");
		cookie_value.append(user.getUserId()).append(":");
		cookie_value.append(autologin);
		Cookie cookie = new Cookie(LOGINUSER_COOKIE, desUtil
				.encrypt(cookie_value.toString()));
		cookie.setPath("/");
		cookie.setDomain(request.getServerName());
		cookie.setMaxAge(cookie_maxAge);
		// if (autologin) {
		// }
		// else {
		// cookie.setMaxAge(-1);
		// }
		response.addCookie(cookie);
	}

	public static void userLogout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute(LOGINUSER);
		request.removeAttribute(LOGINUSER);
		Cookie cookie = new Cookie(LOGINUSER_COOKIE, "");
		cookie.setPath("/");
		cookie.setDomain(getFormatServerName(request.getServerName()));
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	private static String getFormatServerName(String serverName) {
		return serverName;
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cs = request.getCookies();
		if (cs == null) {
			return null;
		}
		for (Cookie cookie : cs) {
			if (cookie.getName().equals(name)) {
				return cookie;
			}
		}
		return null;
	}

	public static String getDefDomain() {
		return defDomain;
	}

	public void setDefDomain(String defDomain) {
		WebUtil.defDomain = defDomain;
	}
}