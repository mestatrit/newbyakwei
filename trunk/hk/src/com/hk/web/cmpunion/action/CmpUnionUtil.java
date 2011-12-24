package com.hk.web.cmpunion.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.DesUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ServletUtil;
import com.hk.svr.UserService;

public class CmpUnionUtil {
	public static String CMPUNION_LOGINUSER_KEY = "com_hk_cmpunion_loginUser";

	public static String CMPUNION_BROWSETYPE_KEY = "com_hk_cmpunion_browsetype";

	public static String CMPUNION_BROWSETYPE_ATTR = "sys_pcbrowse";

	public static String CMPUNION_LOGINUSER_ATTR = "cmpUnion_loginUser";

	private static DesUtil desUtil = new DesUtil("com_hk_cmpunion_desutil_key");

	public static String COOKIE_VERSION = "v1";

	public static int COOKIE_MAXAGE = 1209600;

	public static int COOKIE_BROWSETYPE_MAXAGE = 60 * 60 * 24 * 365;

	private CmpUnionUtil() {
	}

	public static void setBrowseTypeCookie(HttpServletResponse response,
			boolean pcbrowse) {
		Cookie cookie = new Cookie(CMPUNION_BROWSETYPE_KEY, pcbrowse + "");
		cookie.setPath("/");
		cookie.setMaxAge(COOKIE_BROWSETYPE_MAXAGE);
		response.addCookie(cookie);
	}

	public static boolean isPcBrowse(HttpServletRequest request) {
		Boolean t = (Boolean) request.getAttribute(CMPUNION_BROWSETYPE_ATTR);
		if (t != null) {
			return t;
		}
		Cookie cookie = getBrowseTypeCookie(request);
		if (cookie != null) {
			try {
				t = Boolean.parseBoolean(cookie.getValue());
			}
			catch (NumberFormatException e) {
			}
		}
		if (t == null) {
			t = ServletUtil.isPc(request);
		}
		request.setAttribute(CMPUNION_BROWSETYPE_ATTR, t);
		return t;
	}

	public static Cookie getBrowseTypeCookie(HttpServletRequest request) {
		return ServletUtil.getCookie(request, CMPUNION_BROWSETYPE_KEY);
	}

	// public static void removeCookieInfo(HttpServletResponse response) {
	// Cookie cookie = new Cookie(CMPUNION_LOGINUSER_KEY, "");
	// cookie.setPath("/");
	// cookie.setMaxAge(0);
	// response.addCookie(cookie);
	// }
	public static void setCookieInfo(HttpServletResponse response,
			CookieInfo cookieInfo, int autologin) {
		StringBuilder v = new StringBuilder();
		cookieInfo.setVersion(COOKIE_VERSION);
		v.append(cookieInfo.getVersion()).append(";");
		v.append(cookieInfo.getId()).append(";");
		v.append(cookieInfo.getInput());
		String value = desUtil.encrypt(DataUtil.urlEncoder(v.toString()));
		Cookie cookie = new Cookie(CMPUNION_LOGINUSER_KEY, value);
		cookie.setPath("/");
		if (autologin != 0) {
			cookie.setMaxAge(COOKIE_MAXAGE);
		}
		else {
			cookie.setMaxAge(-1);
		}
		response.addCookie(cookie);
	}

	public static User getLoginUser(HttpServletRequest request) {
		User user = (User) request.getAttribute(CMPUNION_LOGINUSER_ATTR);
		if (user != null) {
			return user;
		}
		user = (User) request.getSession()
				.getAttribute(CMPUNION_LOGINUSER_ATTR);
		if (user != null) {
			request.setAttribute(CMPUNION_LOGINUSER_ATTR, user);
			return user;
		}
		user = getUserFromCookie(request);
		if (user != null) {
			request.setAttribute(CMPUNION_LOGINUSER_ATTR, user);
			ServletUtil.setSessionValue(request, CMPUNION_LOGINUSER_ATTR, user);
		}
		return user;
	}

	private static User getUserFromCookie(HttpServletRequest request) {
		CookieInfo cookieInfo = getCookieInfo(request);
		if (cookieInfo != null && !cookieInfo.getId().equals("0")) {
			long userId = Long.parseLong(cookieInfo.getId());
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			User user = userService.getUser(userId);
			return user;
		}
		return null;
	}

	public static CookieInfo getCookieInfo(HttpServletRequest request) {
		Cookie cookie = ServletUtil.getCookie(request, CMPUNION_LOGINUSER_KEY);
		if (cookie == null) {
			return null;
		}
		String v;
		try {
			v = DataUtil.urlDecoder(desUtil.decrypt(cookie.getValue()));
		}
		catch (Exception e) {
			return null;
		}
		String[] data = v.split(";");
		if (data.length < 2) {
			return null;
		}
		String version = data[0];
		if (!version.equals(COOKIE_VERSION)) {
			return null;
		}
		String id = data[1];
		String input = data[2];
		CookieInfo cookieInfo = new CookieInfo();
		cookieInfo.setVersion(version);
		cookieInfo.setId(id);
		cookieInfo.setInput(input);
		return cookieInfo;
	}
}