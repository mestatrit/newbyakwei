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

	@Override
	public boolean getBoolean(String key) {
		String s = this.getString(key);
		if (s == null) {
			return false;
		}
		return Boolean.parseBoolean(s);
	}

	@Override
	public byte getByte(String key) {
		return this.getNumber(key).byteValue();
	}

	@Override
	public byte getByte(String key, byte num) {
		return this.getNumber(key, num).byteValue();
	}

	@Override
	public Cookie getCookie(String name) {
		return ServletUtil.getCookie(getHttpServletRequest(), name);
	}

	@Override
	public double getDouble(String key) {
		return this.getNumber(key).doubleValue();
	}

	@Override
	public double getDoubleAndSetAttr(String key) {
		double v = this.getDouble(key);
		this.getHttpServletRequest().setAttribute(key, v);
		return v;
	}

	@Override
	public double getDouble(String key, double num) {
		return this.getNumber(key, num).doubleValue();
	}

	@Override
	public File getFile(String name) {
		if (fileUpload != null) {
			return this.fileUpload.getFile(name);
		}
		return null;
	}

	@Override
	public File[] getFiles() {
		if (fileUpload != null) {
			return this.fileUpload.getFiles();
		}
		return null;
	}

	@Override
	public String getOriginalFileName(String name) {
		if (fileUpload != null) {
			return this.fileUpload.getOriginalFileName(name);
		}
		return null;
	}

	@Override
	public float getFloat(String key) {
		return this.getNumber(key).floatValue();
	}

	@Override
	public float getFloat(String key, float num) {
		return this.getNumber(key, num).floatValue();
	}

	@Override
	public int getInt(String key) {
		return this.getNumber(key).intValue();
	}

	@Override
	public int getInt(String key, int num) {
		return this.getNumber(key, num).intValue();
	}

	@Override
	public long getLong(String key) {
		return this.getNumber(key).longValue();
	}

	@Override
	public long getLong(String key, long num) {
		return this.getNumber(key, num).longValue();
	}

	@Override
	public Number getNumber(String key) {
		return ServletUtil.getNumber(getHttpServletRequest(), key);
	}

	@Override
	public Number getNumber(String key, Number num) {
		return ServletUtil.getNumber(getHttpServletRequest(), key, num);
	}

	@Override
	public Number[] getNumbers(String key) {
		return ServletUtil.getNumbers(getHttpServletRequest(), key);
	}

	@Override
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

	@Override
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

	@Override
	public Object getSessionValue(String name) {
		return ServletUtil.getSessionValue(getHttpServletRequest(), name);
	}

	@Override
	public String getString(String key) {
		return ServletUtil.getString(getHttpServletRequest(), key);
	}

	@Override
	public String getStringRow(String key) {
		String v = this.getString(key);
		if (v != null) {
			return v.replaceAll("\n", "").replaceAll("\r", "");
		}
		return null;
	}

	@Override
	public String getStringWithoutBeginTrim(String key) {
		return ServletUtil.getStringWithoutBeginTrim(getHttpServletRequest(),
				key);
	}

	@Override
	public String getHtmlWithoutBeginTrim(String key) {
		String v = this.getStringWithoutBeginTrim(key);
		return DataUtil.toHtmlEx(v);
	}

	@Override
	public String getHtml(String key) {
		String v = this.getString(key);
		return DataUtil.toHtmlEx(v);
	}

	@Override
	public String getHtmlRow(String key) {
		String v = this.getString(key);
		return DataUtil.toHtmlRow(v);
	}

	@Override
	public String getHtmlAndSetAttr(String key) {
		String v = getHtml(key);
		this.setAttribute(key, v);
		return v;
	}

	@Override
	public String getHtmlRowAndSetAttr(String key) {
		String v = getHtmlRow(key);
		this.setAttribute(key, v);
		return v;
	}

	@Override
	public String getStringAndSetAttr(String key) {
		String s = ServletUtil.getString(getHttpServletRequest(), key);
		this.setAttribute(key, s);
		return s;
	}

	@Override
	public String getParameter(String name) {
		return ServletUtil.getString(getHttpServletRequest(), name);
	}

	@Override
	public String getEncodeString(String key) {
		String s = this.getString(key, "");
		return DataUtil.urlEncoder(s);
	}

	@Override
	public String getString(String key, String str) {
		String t = this.getString(key);
		if (t == null) {
			return str;
		}
		return t;
	}

	@Override
	public String getStringRow(String key, String def) {
		String v = this.getString(key, def);
		if (v != null) {
			return v.replaceAll("\n", "").replaceAll("\r", "");
		}
		return null;
	}

	@Override
	public String[] getStrings(String key) {
		return this.getHttpServletRequest().getParameterValues(key);
	}

	@Override
	public void setSessionValue(String name, Object value) {
		ServletUtil.setSessionValue(getHttpServletRequest(), name, value);
	}

	@Override
	public int getPage() {
		return ServletUtil.getPage(getHttpServletRequest());
	}

	@Override
	public PageSupport getPageSupport(int page, int size) {
		PageSupport pageSupport = PageSupport.getInstance(this.getPage(), size);
		getHttpServletRequest().setAttribute(HkUtil.PAGESUPPORT_ATTRIBUTE,
				pageSupport);
		return pageSupport;
	}

	@Override
	public PageSupport getPageSupport(int size) {
		return this.getPageSupport(this.getPage(), size);
	}

	private void initMultipart() throws IOException {
		if (ServletUtil.isMultipart(this.getHttpServletRequest())) {
			fileUpload = new FileUpload(this.getHttpServletRequest());
			this.request = fileUpload.getHkMultiRequest();
		}
	}

	@Override
	public String getServerInfo() {
		return ServletUtil.getServerInfo(getHttpServletRequest());
	}

	@Override
	public void setMessage(String msg) {
		this.setAttribute(HkUtil.MESSAGE_NAME, msg);
	}

	@Override
	public void setSessionMessage(String msg) {
		ServletUtil.setSessionMessage(getHttpServletRequest(), msg);
	}

	@Override
	public void removeSessionvalue(String name) {
		this.getHttpServletRequest().getSession().removeAttribute(name);
	}

	@Override
	public void invalidateSession() {
		this.getHttpServletRequest().getSession().invalidate();
	}

	public int getPageBegin(int size) {
		int page = this.getPage();
		getHttpServletRequest().setAttribute("page", page);
		return (page - 1) * size;
	}

	@Override
	public SimplePage getSimplePage(int size) {
		return ServletUtil.getSimplePage(getHttpServletRequest(), size);
	}

	@Override
	public void reSetAttribute(String name) {
		if (this.getAttribute(name) != null) {
			return;
		}
		String o = this.getString(name);
		if (o != null) {
			getHttpServletRequest().setAttribute(name, o);
		}
	}

	@Override
	public void setEncodeAttribute(String name, Object value) {
		getHttpServletRequest().setAttribute(name, value);
		if (value != null) {
			getHttpServletRequest().setAttribute("enc_" + name,
					DataUtil.urlEncoder(value.toString()));
		}
	}

	@Override
	public void reSetEncodeAttribute(String name) {
		String value = this.getString(name, "");
		getHttpServletRequest().setAttribute(name, value);
		this.setEncodeAttribute("enc_" + name, value);
	}

	@Override
	public String getRemoteAddr() {
		return ServletUtil.getRemoteAddr(getHttpServletRequest());
	}

	@Override
	public void setBackString(String s) {
		this.setAttribute(HkUtil.backString_key, s);
	}

	@Override
	public void reSetBackString() {
		this.reSetAttribute(HkUtil.backString_key);
	}

	@Override
	public String getBackString() {
		String s = (String) this.getHttpServletRequest().getAttribute(
				HkUtil.backString_key);
		if (s == null) {
			return "";
		}
		return s;
	}

	@Override
	public void setReturnUrl(String url) {
		this.setAttribute(HkUtil.RETURN_URL, DataUtil.urlEncoder(url));
		this.setAttribute(HkUtil.DENC_RETURN_URL, url);
	}

	@Override
	public void reSetReturnUrl() {
		if (this.getAttribute(HkUtil.DENC_RETURN_URL) != null
				&& this.getAttribute(HkUtil.DENC_RETURN_URL) != null) {
			return;
		}
		String s = this.getReturnUrl();
		this.setReturnUrl(s);
	}

	@Override
	public String getReturnUrl() {
		return this.getString(HkUtil.RETURN_URL, "");
	}

	@Override
	public String getEncodeReturnUrl() {
		return DataUtil.urlEncoder(this.getReturnUrl());
	}

	@Override
	public String getText(String key, Object... args) {
		Locale locale = (Locale) this.getAttribute(HkI18n.I18N_KEY);
		if (locale == null) {
			locale = Locale.SIMPLIFIED_CHINESE;
			this.setAttribute(HkI18n.I18N_KEY, locale);
		}
		return ResourceConfig.getText(locale, key, args);
	}

	@Override
	public void setText(String key, Object... args) {
		this.setMessage(this.getText(key, args));
	}

	@Override
	public void setSessionText(String key, Object... args) {
		this.setSessionMessage(this.getText(key, args));
		// ServletUtil.setSessionText(getHttpServletRequest(), key, args);
	}

	@Override
	public byte getByteAndSetAttr(String key) {
		byte v = this.getByte(key);
		this.getHttpServletRequest().setAttribute(key, v);
		return v;
	}

	@Override
	public byte getByteAndSetAttr(String key, byte num) {
		byte v = this.getByte(key, num);
		this.getHttpServletRequest().setAttribute(key, v);
		return v;
	}

	@Override
	public int getIntAndSetAttr(String key) {
		int v = this.getInt(key);
		this.getHttpServletRequest().setAttribute(key, v);
		return v;
	}

	@Override
	public long getLongAndSetAttr(String key) {
		long v = this.getLong(key);
		this.getHttpServletRequest().setAttribute(key, v);
		return v;
	}

	@Override
	public String saveToken() {
		return TokenUtil.saveToken(this.getHttpServletRequest());
	}

	@Override
	public boolean isTokenValid() {
		return TokenUtil.isTokenValid(this.getHttpServletRequest());
	}

	@Override
	public void clearToken() {
		TokenUtil.clearToken(this.getHttpServletRequest());
	}
}