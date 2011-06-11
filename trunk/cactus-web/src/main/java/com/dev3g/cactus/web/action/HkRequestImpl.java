package com.dev3g.cactus.web.action;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.dev3g.cactus.util.DataUtil;
import com.dev3g.cactus.web.util.MessageUtil;
import com.dev3g.cactus.web.util.PageSupport;
import com.dev3g.cactus.web.util.ServletUtil;
import com.dev3g.cactus.web.util.SimplePage;

public class HkRequestImpl extends HttpServletRequestWrapper implements
		HkRequest {

	private HttpServletRequest httpServletRequest;

	private UploadFile[] uploadFiles;

	private File[] files;

	private Map<String, UploadFile> uploadFileMap = null;

	public HkRequestImpl(HttpServletRequest request) {
		super(request);
	}

	public HttpServletRequest getHttpServletRequest() {
		if (this.httpServletRequest == null) {
			return (HttpServletRequest) this.getRequest();
		}
		return this.httpServletRequest;
	}

	@Override
	public void setUploadFiles(UploadFile[] uploadFiles) {
		this.uploadFiles = uploadFiles;
		if (this.uploadFileMap == null) {
			this.uploadFileMap = new HashMap<String, UploadFile>();
		}
		if (this.files == null) {
			this.files = new File[this.uploadFiles.length];
		}
		for (int i = 0; i < this.uploadFiles.length; i++) {
			this.files[i] = this.uploadFiles[i].getFile();
			this.uploadFileMap.put(this.uploadFiles[i].getName(),
					this.uploadFiles[i]);
		}
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
		this.setAttribute(key, v);
		return v;
	}

	@Override
	public double getDouble(String key, double num) {
		return this.getNumber(key, num).doubleValue();
	}

	@Override
	public File getFile(String name) {
		UploadFile uploadFile = this.getUploadFile(name);
		if (uploadFile == null) {
			return null;
		}
		return uploadFile.getFile();
	}

	@Override
	public File[] getFiles() {
		return this.files;
	}

	@Override
	public UploadFile getUploadFile(String name) {
		if (this.uploadFileMap != null) {
			return this.uploadFileMap.get(name);
		}
		return null;
	}

	@Override
	public UploadFile[] getUploadFiles() {
		return this.uploadFiles;
	}

	@Override
	public String getOriginalFileName(String name) {
		UploadFile uploadFile = this.getUploadFile(name);
		if (uploadFile != null) {
			return uploadFile.getName();
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
	public String getHtml(String key) {
		String v = this.getString(key);
		return DataUtil.toHtml(v);
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
		String s = this.getString(key);
		if (s != null) {
			this.setAttribute(key, s);
		}
		return s;
	}

	@Override
	public String getParameter(String name) {
		return this.getString(name);
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
		getHttpServletRequest().setAttribute(WebCnf.PAGESUPPORT_ATTRIBUTE,
				pageSupport);
		return pageSupport;
	}

	@Override
	public PageSupport getPageSupport(int size) {
		return this.getPageSupport(this.getPage(), size);
	}

	@Override
	public void setMessage(String msg) {
		this.setAttribute(MessageUtil.MESSAGE_NAME, msg);
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
			this.setAttribute(name, o);
		}
	}

	@Override
	public void setEncodeAttribute(String name, String value) {
		this.setAttribute(name, value);
		if (value != null) {
			this.setAttribute("enc_" + name, DataUtil.urlEncoder(value));
		}
	}

	@Override
	public void reSetEncodeAttribute(String name) {
		String value = this.getString(name, "");
		this.setAttribute(name, value);
		this.setEncodeAttribute("enc_" + name, value);
	}

	@Override
	public String getRemoteAddr() {
		return ServletUtil.getRemoteAddr(getHttpServletRequest());
	}

	@Override
	public byte getByteAndSetAttr(String key) {
		byte v = this.getByte(key);
		this.setAttribute(key, v);
		return v;
	}

	@Override
	public byte getByteAndSetAttr(String key, byte num) {
		byte v = this.getByte(key, num);
		this.setAttribute(key, v);
		return v;
	}

	@Override
	public int getIntAndSetAttr(String key) {
		int v = this.getInt(key);
		this.setAttribute(key, v);
		return v;
	}

	@Override
	public long getLongAndSetAttr(String key) {
		long v = this.getLong(key);
		this.setAttribute(key, v);
		return v;
	}

	@Override
	public boolean isUploadExceedSize() {
		Boolean b = (Boolean) this.getAttribute(WebCnf.UPLOAD_EXCEEDEDSIZE_KEY);
		if (b == null) {
			return false;
		}
		return b.booleanValue();
	}
}