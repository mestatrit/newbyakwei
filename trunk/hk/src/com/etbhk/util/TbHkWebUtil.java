package com.etbhk.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.bean.taobao.Tb_User;
import com.hk.frame.util.DesUtil;
import com.hk.frame.util.ServletUtil;

public class TbHkWebUtil {

	public static final String LOGIN_USER_ATTRKEY = "login_user";

	public static final String SESSION_LOGIN_USER_ATTRKEY = "session_login_user";

	public static final String CTXPATH = "ctx_path";

	public static final String LOGIN_COOKIE_USER_ATTRKEY = "tb_cookie_guest";

	public static final String YZM_CODE_ATTRKEY = "yzm_code_attrkey";

	private static final DesUtil desUtil = new DesUtil("akweiflyshowhuoku");

	public static int COOKIE_MAXAGE = 1209600;

	public static void setLoginUser(HttpServletRequest request,
			HttpServletResponse response, Tb_User tbUser, String input) {
		String _input = null;
		if (input.equals("")) {
			_input = " ";
		}
		else {
			_input = input;
		}
		String v = tbUser.getUserid() + ";" + tbUser.getShow_nick() + ";"
				+ _input;
		String encode_v = desUtil.encrypt(v);
		Cookie cookie = new Cookie(LOGIN_COOKIE_USER_ATTRKEY, encode_v);
		cookie.setPath("/");
		cookie.setDomain(request.getServerName());
		cookie.setMaxAge(COOKIE_MAXAGE);
		response.addCookie(cookie);
	}

	public static String[] getCookieValueArray(HttpServletRequest request) {
		Cookie cookie = ServletUtil.getCookie(request,
				LOGIN_COOKIE_USER_ATTRKEY);
		if (cookie == null) {
			return null;
		}
		String decode_v;
		try {
			decode_v = desUtil.decrypt(cookie.getValue());
			String[] arrv = decode_v.split(";");
			if (arrv.length < 3) {
				return null;
			}
			// 设置arrv.length - 1的原因为input可以为空
			for (int i = 0; i < arrv.length - 1; i++) {
				if (arrv[i].equals("")) {
					return null;
				}
			}
			return arrv;
		}
		catch (Exception e) {
			return null;
		}
	}

	public static void clearLoginUser(HttpServletRequest request,
			HttpServletResponse response) {
		ServletUtil.removeSessionValue(request, SESSION_LOGIN_USER_ATTRKEY);
		String v = "";
		Cookie cookie = new Cookie(LOGIN_COOKIE_USER_ATTRKEY, v);
		cookie.setPath("/");
		cookie.setDomain(request.getServerName());
		cookie.setMaxAge(COOKIE_MAXAGE);
		response.addCookie(cookie);
	}

	public static Tb_User getLoginUserFromCookie(HttpServletRequest request) {
		String[] arrv = getCookieValueArray(request);
		if (arrv == null) {
			return null;
		}
		Tb_User loginUser = new Tb_User();
		try {
			loginUser.setUserid(Long.valueOf(arrv[0]));
			loginUser.setNick(arrv[1]);
			return loginUser;
		}
		catch (Exception e) {
			return null;
		}
	}

	public static String getLogin_Input(HttpServletRequest request) {
		String[] arrv = getCookieValueArray(request);
		if (arrv == null) {
			return null;
		}
		return arrv[2];
	}
}