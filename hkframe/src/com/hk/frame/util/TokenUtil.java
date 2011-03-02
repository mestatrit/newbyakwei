package com.hk.frame.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TokenUtil {

	private static final Log log = LogFactory.getLog(TokenUtil.class);

	public static final String REQ_TOKEN_KEY = "req_com_hk_form_token_key";

	public static final String SESSION_TOKEN_KEY = "session_com_hk_form_token_key";

	public static boolean isTokenValid(HttpServletRequest request) {
		String param_value = ServletUtil.getString(request, REQ_TOKEN_KEY);
		String session_value = (String) ServletUtil.getSessionValue(request,
				SESSION_TOKEN_KEY);
		log.info("param_value token [ " + param_value + " ]");
		log.info("session_value token [ " + session_value + " ]");
		if (DataUtil.isEmpty(session_value)) {// 长时间不操作session会丢失
			return true;
		}
		if (DataUtil.isEmpty(param_value)) {// 页面参数不能为空
			return false;
		}
		if (param_value.equals(session_value)) {
			return true;
		}
		return false;
	}

	public static void clearToken(HttpServletRequest request) {
		ServletUtil.removeSessionValue(request, SESSION_TOKEN_KEY);
	}

	public static String saveToken(HttpServletRequest request) {
		String randomvalue = request.getSession().getId()
				+ String.valueOf(System.currentTimeMillis())
				+ String.valueOf(System.nanoTime());
		request.getSession().setAttribute(SESSION_TOKEN_KEY, randomvalue);
		return randomvalue;
	}
}
