package com.hk.frame.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import com.hk.frame.util.i18n.HkI18n;
import com.hk.frame.util.page.SimplePage;

public class ServletUtil {

	private ServletUtil() {
		// TODO Auto-generated constructor stub
	}

	public static String PAGE = "page";

	public static final String USER_AGENT = "user-agent";

	public static final String ACCEPT = "accept";

	public static final String WAP_12 = "12";

	public static final String WAP_20 = "20";

	/**
	 * 返回指定名字的header字段
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getHeader(HttpServletRequest request, String name) {
		String value = request.getHeader(name);
		if (value == null) {
			return "";
		}
		return value.trim().toLowerCase();
	}

	/**
	 * 返回当前请求的ua信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getUA(HttpServletRequest request) {
		return getHeader(request, USER_AGENT);
	}

	public static boolean isPc(HttpServletRequest request) {
		if (getUA(request).indexOf("android") != -1) {
			return false;
		}
		if (getUA(request).indexOf("iphone") != -1) {
			return false;
		}
		String accept = getAccept(request);
		if (accept == null || accept.indexOf("wap") != -1
				|| accept.indexOf("j2me") != -1
				|| accept.indexOf("text/vnd") != -1
				|| accept.indexOf("wml") != -1) {
			return false;
		}
		return true;
	}

	public static boolean isWap(HttpServletRequest request) {
		if (getUA(request).indexOf("android") != -1) {
			return true;
		}
		if (getUA(request).indexOf("iphone") != -1) {
			return true;
		}
		String accept = getAccept(request);
		if (accept.indexOf("wap") != -1 || accept.indexOf("j2me") != -1
				|| accept.indexOf("text/vnd") != -1
				|| accept.indexOf("wml") != -1) {
			return true;
		}
		return false;
	}

	/**
	 * 返回当前请求的accept信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getAccept(HttpServletRequest request) {
		return getHeader(request, ACCEPT);
	}

	public static void writeValue(HttpServletResponse resp, String vmpath,
			VelocityContext context) {
		try {
			sendXml2(resp, VelocityUtil.writeToString(vmpath, context));
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void alertConfirmJS(HttpServletResponse response, String s,
			String trueurl, String falseurl) {
		sendHtml(response, HkUtil.buildString("<script>if(window.confirm('", s,
				"')){window.location.href='", trueurl,
				"';}else{window.location.href='", falseurl, "';}</script>"));
	}

	public static void alertJS(HttpServletResponse response, String s) {
		sendHtml(response, HkUtil.buildString("<script>alert('", s,
				"');</script>"));
	}

	public static void alertJSAndClose(HttpServletResponse response, String s) {
		sendHtml(response, HkUtil.buildString("<script>alert('", s,
				"');window.close();</script>"));
	}

	public static void alertJSAndGoBack(HttpServletResponse response, String s) {
		sendHtml(response, HkUtil.buildString("<script>alert('", s,
				"');history.back();</script>"));
	}

	public static void alertJSAndRedirect(HttpServletResponse response,
			String s, String url) {
		sendHtml(response, HkUtil.buildString("<script>alert('", s,
				"');window.location.href='", url, "';</script>"));
	}

	public static void jsRedirect(HttpServletResponse response, String url) {
		sendHtml(response, HkUtil.buildString("<script>window.location.href='",
				url, "';</script>"));
	}

	public static void sendHtml(HttpServletResponse response, Object value) {
		response.setContentType("text/html;charset=UTF-8");
		write(response, value.toString());
	}

	public static void write(HttpServletResponse response, String value) {
		Writer writer = null;
		try {
			writer = response.getWriter();
			writer.write(value);
			writer.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (writer != null) {
				try {
					writer.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Number[] getNumbers(HttpServletRequest request, String key) {
		String[] v = request.getParameterValues(key);
		if (v == null) {
			return null;
		}
		Number[] t = new Number[v.length];
		for (int i = 0; i < v.length; i++) {
			if (v[i].trim().equals("")) {
				t[i] = 0;
			}
			else {
				t[i] = new BigDecimal(v[i]);
			}
		}
		return t;
	}

	public static Number getNumber(HttpServletRequest request, String key,
			Number num) {
		String t = request.getParameter(key);
		if (t == null) {
			return num;
		}
		try {
			return new BigDecimal(t);
		}
		catch (Exception e) {
			return 0;
		}
	}

	public static Number getNumber(HttpServletRequest request, String key) {
		try {
			String t = request.getParameter(key);
			if (t == null || t.equals("")) {
				return 0;
			}
			return new BigDecimal(t);
		}
		catch (Exception e) {
			return 0;
		}
	}

	static String ISO = "ISO-8859-1";

	static String UTF_8 = "UTF-8";

	public static String getServerInfo(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.getScheme());
		sb.append("://");
		sb.append(request.getServerName());
		int port = request.getServerPort();
		if (port != 80) {
			sb.append(":").append(port);
		}
		sb.append(request.getContextPath());
		return sb.toString();
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

	public static String getStringWithoutBeginTrim(HttpServletRequest request,
			String key) {
		String t = request.getParameter(key);
		if (t == null) {
			return null;
		}
		if (t.length() == 0) {
			return null;
		}
		if (request.getMethod().equalsIgnoreCase("post")) {
			return t;
		}
		try {
			return new String(t.getBytes(ISO), UTF_8);
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getString(HttpServletRequest request, String key) {
		String t = request.getParameter(key);
		if (t == null) {
			return null;
		}
		t = t.trim();
		if (t.length() == 0) {
			return null;
		}
		if (request.getMethod().equalsIgnoreCase("post")) {
			return t;
		}
		try {
			return new String(t.getBytes(ISO), UTF_8);
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static int getPage(HttpServletRequest request) {
		int page = getInt(request, PAGE);
		if (page < 1) {
			page = 1;
		}
		request.setAttribute("page", page);
		return page;
	}

	public static SimplePage getSimplePage(HttpServletRequest request, int size) {
		int page = getPage(request);
		SimplePage simplePage = new SimplePage(size, page);
		request.setAttribute(HkUtil.SIMPLEPAGE_ATTRIBUTE, simplePage);
		return simplePage;
	}

	public static long getLong(HttpServletRequest request, String key) {
		return getNumber(request, key).longValue();
	}

	public static long getLong(HttpServletRequest request, String key, long num) {
		return getNumber(request, key, num).longValue();
	}

	public static int getInt(HttpServletRequest request, String key) {
		return getNumber(request, key).intValue();
	}

	public static int getInt(HttpServletRequest request, String key, long num) {
		return getNumber(request, key, num).intValue();
	}

	public static byte getByte(HttpServletRequest request, String key) {
		return getNumber(request, key).byteValue();
	}

	public static byte getByte(HttpServletRequest request, String key, long num) {
		return getNumber(request, key, num).byteValue();
	}

	public static double getDouble(HttpServletRequest request, String key) {
		return getNumber(request, key).doubleValue();
	}

	public static double getDouble(HttpServletRequest request, String key,
			long num) {
		return getNumber(request, key, num).doubleValue();
	}

	public static void setSessionText(HttpServletRequest request, String key,
			Object... args) {
		Locale locale = (Locale) request.getAttribute(HkI18n.I18N_KEY);
		if (locale == null) {
			locale = Locale.SIMPLIFIED_CHINESE;
			request.setAttribute(HkI18n.I18N_KEY, locale);
		}
		setSessionMessage(request, ResourceConfig.getText(locale, key, args));
	}

	public static String getText(HttpServletRequest request, String key,
			Object... args) {
		Locale locale = (Locale) request.getAttribute(HkI18n.I18N_KEY);
		if (locale == null) {
			locale = Locale.SIMPLIFIED_CHINESE;
			request.setAttribute(HkI18n.I18N_KEY, locale);
		}
		return ResourceConfig.getText(locale, key, args);
	}

	public static void setSessionValue(HttpServletRequest request, String name,
			Object value) {
		request.getSession().setAttribute(name, value);
	}

	public static Object getSessionValue(HttpServletRequest request, String name) {
		return request.getSession().getAttribute(name);
	}

	public static Object getRequestValue(HttpServletRequest request, String name) {
		return request.getAttribute(name);
	}

	public static void removeSessionValue(HttpServletRequest request,
			String name) {
		request.getSession().removeAttribute(name);
	}

	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getRemoteAddr();
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.split(",")[0];
		}
		return ip;
	}

	public static void setSessionMessage(HttpServletRequest request, String msg) {
		setSessionValue(request, HkUtil.MESSAGE_NAME, msg);
	}

	public static void sendXml2(HttpServletResponse response, String value) {
		response.setContentType("text/xml;charset=UTF-8");
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append(value);
		sendValue(response, sb.toString());
	}

	public static void sendValue(HttpServletResponse response, String value) {
		Writer writer = null;
		try {
			writer = response.getWriter();
			writer.write(value);
			writer.flush();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (writer != null) {
				try {
					writer.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static boolean isMultipart(HttpServletRequest request) {
		String type = null;
		String type1 = request.getHeader("Content-Type");
		String type2 = request.getContentType();
		if (type1 == null && type2 != null) {
			type = type2;
		}
		else if (type2 == null && type1 != null) {
			type = type1;
		}
		else if (type1 != null && type2 != null) {
			type = (type1.length() > type2.length() ? type1 : type2);
		}
		if (type == null
				|| !type.toLowerCase().startsWith("multipart/form-data")) {
			return false;
		}
		return true;
	}

	public static String getReturnUrl(HttpServletRequest request) {
		String url = request.getRequestURL().append("?").append(
				request.getQueryString()).toString();
		return url;
	}
}