package com.hk.frame.web.http;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.util.TokenUtil;
import com.hk.frame.util.i18n.HkI18n;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.util.upload.FileUpload;

public class HkRequestImpl extends HttpServletRequestWrapper implements
		HkRequest {

	private FileUpload fileUpload;

	private HttpServletRequest request;

	public HkRequestImpl(HttpServletRequest request) throws IOException {
		super(request);
		this.initMultipart();
	}

	public HttpServletRequest getHttpServletRequest() {
		if (this.request == null) {
			return (HttpServletRequest) this.getRequest();
		}
		return this.request;
	}

	public boolean getBoolean(String key) {
		String s = this.getString(key);
		if (s == null) {
			return false;
		}
		return Boolean.parseBoolean(s);
	}

	public byte getByte(String key) {
		return this.getNumber(key).byteValue();
	}

	public byte getByte(String key, byte num) {
		return this.getNumber(key, num).byteValue();
	}

	public Cookie getCookie(String name) {
		return ServletUtil.getCookie(getHttpServletRequest(), name);
	}

	public double getDouble(String key) {
		return this.getNumber(key).doubleValue();
	}

	public double getDoubleAndSetAttr(String key) {
		double v = this.getDouble(key);
		this.getHttpServletRequest().setAttribute(key, v);
		return v;
	}

	public double getDouble(String key, double num) {
		return this.getNumber(key, num).doubleValue();
	}

	public File getFile(String name) {
		if (fileUpload != null) {
			return this.fileUpload.getFile(name);
		}
		return null;
	}

	public File[] getFiles() {
		if (fileUpload != null) {
			return this.fileUpload.getFiles();
		}
		return null;
	}

	public String getOriginalFileName(String name) {
		if (fileUpload != null) {
			return this.fileUpload.getOriginalFileName(name);
		}
		return null;
	}

	public float getFloat(String key) {
		return this.getNumber(key).floatValue();
	}

	public float getFloat(String key, float num) {
		return this.getNumber(key, num).floatValue();
	}

	public int getInt(String key) {
		return this.getNumber(key).intValue();
	}

	public int getInt(String key, int num) {
		return this.getNumber(key, num).intValue();
	}

	public long getLong(String key) {
		return this.getNumber(key).longValue();
	}

	public long getLong(String key, long num) {
		return this.getNumber(key, num).longValue();
	}

	public Number getNumber(String key) {
		return ServletUtil.getNumber(getHttpServletRequest(), key);
	}

	public Number getNumber(String key, Number num) {
		return ServletUtil.getNumber(getHttpServletRequest(), key, num);
	}

	public Number[] getNumbers(String key) {
		return ServletUtil.getNumbers(getHttpServletRequest(), key);
	}

	public int[] getInts(String key) {
		Number[] n = getNumbers(key);
		if (n == null) {
			return null;
		}
		int[] ii = new int[n.length];
		for (int i = 0; i < n.length; i++) {
			ii[i] = n[i].intValue();
		}
		return ii;
	}

	public long[] getLongs(String key) {
		Number[] n = getNumbers(key);
		if (n == null) {
			return null;
		}
		long[] ii = new long[n.length];
		for (int i = 0; i < n.length; i++) {
			ii[i] = n[i].longValue();
		}
		return ii;
	}

	public Object getSessionValue(String name) {
		return ServletUtil.getSessionValue(getHttpServletRequest(), name);
	}

	public String getString(String key) {
		return ServletUtil.getString(getHttpServletRequest(), key);
	}

	public String getStringWithoutBeginTrim(String key) {
		return ServletUtil.getStringWithoutBeginTrim(getHttpServletRequest(),
				key);
	}

	public String getHtmlWithoutBeginTrim(String key) {
		String v = this.getStringWithoutBeginTrim(key);
		return DataUtil.toHtmlEx(v);
	}

	public String getHtml(String key) {
		String v = this.getString(key);
		return DataUtil.toHtmlEx(v);
	}

	public String getHtmlRow(String key) {
		String v = this.getString(key);
		return DataUtil.toHtmlRow(v);
	}

	public String getHtmlAndSetAttr(String key) {
		String v = getHtml(key);
		this.setAttribute(key, v);
		return v;
	}

	public String getHtmlRowAndSetAttr(String key) {
		String v = getHtmlRow(key);
		this.setAttribute(key, v);
		return v;
	}

	public String getStringAndSetAttr(String key) {
		String s = ServletUtil.getString(getHttpServletRequest(), key);
		this.setAttribute(key, s);
		return s;
	}

	@Override
	public String getParameter(String name) {
		return ServletUtil.getString(getHttpServletRequest(), name);
	}

	public String getEncodeString(String key) {
		String s = this.getString(key, "");
		return DataUtil.urlEncoder(s);
	}

	public String getString(String key, String str) {
		String t = this.getString(key);
		if (t == null) {
			return str;
		}
		return t;
	}

	public String[] getStrings(String key) {
		return this.getHttpServletRequest().getParameterValues(key);
	}

	public void setSessionValue(String name, Object value) {
		ServletUtil.setSessionValue(getHttpServletRequest(), name, value);
	}

	public int getPage() {
		return ServletUtil.getPage(getHttpServletRequest());
	}

	public PageSupport getPageSupport(int page, int size) {
		PageSupport pageSupport = PageSupport.getInstance(this.getPage(), size);
		getHttpServletRequest().setAttribute(HkUtil.PAGESUPPORT_ATTRIBUTE,
				pageSupport);
		return pageSupport;
	}

	public PageSupport getPageSupport(int size) {
		return this.getPageSupport(this.getPage(), size);
	}

	private void initMultipart() throws IOException {
		if (ServletUtil.isMultipart(this.getHttpServletRequest())) {
			fileUpload = new FileUpload(this.getHttpServletRequest());
			this.request = fileUpload.getHkMultiRequest();
		}
	}

	public String getServerInfo() {
		return ServletUtil.getServerInfo(getHttpServletRequest());
	}

	public void setMessage(String msg) {
		this.setAttribute(HkUtil.MESSAGE_NAME, msg);
	}

	public void setSessionMessage(String msg) {
		ServletUtil.setSessionMessage(getHttpServletRequest(), msg);
	}

	public void removeSessionvalue(String name) {
		this.getHttpServletRequest().getSession().removeAttribute(name);
	}

	public void invalidateSession() {
		this.getHttpServletRequest().getSession().invalidate();
	}

	public int getPageBegin(int size) {
		int page = this.getPage();
		getHttpServletRequest().setAttribute("page", page);
		return (page - 1) * size;
	}

	public SimplePage getSimplePage(int size) {
		return ServletUtil.getSimplePage(getHttpServletRequest(), size);
	}

	public void reSetAttribute(String name) {
		String o = this.getString(name);
		if (o != null) {
			getHttpServletRequest().setAttribute(name, o);
		}
	}

	public void setEncodeAttribute(String name, Object value) {
		getHttpServletRequest().setAttribute(name, value);
		if (value != null) {
			getHttpServletRequest().setAttribute("enc_" + name,
					DataUtil.urlEncoder(value.toString()));
		}
	}

	public void reSetEncodeAttribute(String name) {
		String value = this.getString(name, "");
		getHttpServletRequest().setAttribute(name, value);
		this.setEncodeAttribute("enc_" + name, value);
	}

	@Override
	public String getRemoteAddr() {
		return ServletUtil.getRemoteAddr(getHttpServletRequest());
	}

	public void setBackString(String s) {
		this.setAttribute("backString", s);
	}

	public void reSetBackString() {
		this.reSetAttribute("backString");
	}

	public String getBackString() {
		String s = (String) this.getHttpServletRequest().getAttribute(
				"backString");
		if (s == null) {
			return "";
		}
		return s;
	}

	public void setReturnUrl(String url) {
		this.setAttribute(HkUtil.RETURN_URL, DataUtil.urlEncoder(url));
		this.setAttribute(HkUtil.DENC_RETURN_URL, url);
	}

	public void reSetReturnUrl() {
		String s = this.getReturnUrl();
		this.setReturnUrl(s);
	}

	public String getReturnUrl() {
		return this.getString(HkUtil.RETURN_URL, "");
	}

	public String getEncodeReturnUrl() {
		return DataUtil.urlEncoder(this.getReturnUrl());
	}

	public String getText(String key, Object... args) {
		Locale locale = (Locale) this.getAttribute(HkI18n.I18N_KEY);
		if (locale == null) {
			locale = Locale.SIMPLIFIED_CHINESE;
			this.setAttribute(HkI18n.I18N_KEY, locale);
		}
		return ResourceConfig.getText(locale, key, args);
	}

	public void setText(String key, Object... args) {
		this.setMessage(this.getText(key, args));
	}

	public void setSessionText(String key, Object... args) {
		this.setSessionMessage(this.getText(key, args));
		// ServletUtil.setSessionText(getHttpServletRequest(), key, args);
	}

	public byte getByteAndSetAttr(String key) {
		byte v = this.getByte(key);
		this.getHttpServletRequest().setAttribute(key, v);
		return v;
	}

	public byte getByteAndSetAttr(String key, byte num) {
		byte v = this.getByte(key, num);
		this.getHttpServletRequest().setAttribute(key, v);
		return v;
	}

	public int getIntAndSetAttr(String key) {
		int v = this.getInt(key);
		this.getHttpServletRequest().setAttribute(key, v);
		return v;
	}

	public long getLongAndSetAttr(String key) {
		long v = this.getLong(key);
		this.getHttpServletRequest().setAttribute(key, v);
		return v;
	}

	public String saveToken() {
		return TokenUtil.saveToken(this.getHttpServletRequest());
	}

	public boolean isTokenValid() {
		return TokenUtil.isTokenValid(this.getHttpServletRequest());
	}

	public void clearToken() {
		TokenUtil.clearToken(this.getHttpServletRequest());
	}
}