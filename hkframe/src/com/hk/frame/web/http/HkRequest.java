package com.hk.frame.web.http;

import java.io.File;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.hk.frame.util.page.PageSupport;
import com.hk.frame.util.page.SimplePage;

public interface HkRequest extends HttpServletRequest {

	String ISO = "ISO-8859-1";

	String UTF_8 = "UTF-8";

	void setSessionValue(String name, Object value);

	Object getSessionValue(String name);

	Number getNumber(String key);

	Number getNumber(String key, Number num);

	String getString(String key);

	String getStringWithoutBeginTrim(String key);

	String getHtmlWithoutBeginTrim(String key);

	String getHtml(String key);

	String getHtmlAndSetAttr(String key);

	String getHtmlRow(String key);

	String getHtmlRowAndSetAttr(String key);

	String getStringAndSetAttr(String key);

	String getEncodeString(String key);

	String getString(String key, String str);

	String[] getStrings(String key);

	Number[] getNumbers(String key);

	int[] getInts(String key);

	long[] getLongs(String key);

	int getInt(String key);

	int getIntAndSetAttr(String key);

	boolean getBoolean(String key);

	int getInt(String key, int num);

	long getLong(String key);

	long getLongAndSetAttr(String key);

	long getLong(String key, long num);

	double getDouble(String key);

	double getDoubleAndSetAttr(String key);

	double getDouble(String key, double num);

	float getFloat(String key);

	float getFloat(String key, float num);

	byte getByte(String key);

	byte getByteAndSetAttr(String key);

	byte getByteAndSetAttr(String key, byte num);

	byte getByte(String key, byte num);

	File getFile(String name);

	File[] getFiles();

	String getOriginalFileName(String name);

	Cookie getCookie(String name);

	PageSupport getPageSupport(int page, int size);

	PageSupport getPageSupport(int size);

	int getPage();

	String getServerInfo();

	void setMessage(String msg);

	void setSessionMessage(String msg);

	void removeSessionvalue(String name);

	void invalidateSession();

	SimplePage getSimplePage(int size);

	void reSetAttribute(String name);

	void reSetEncodeAttribute(String name);

	void setEncodeAttribute(String name, Object value);

	void setBackString(String s);

	/**
	 * 把urlencode之后的值set
	 * 
	 * @param url
	 *            2010-7-13
	 */
	void setReturnUrl(String url);

	void reSetReturnUrl();

	String getReturnUrl();

	String getEncodeReturnUrl();

	void reSetBackString();

	String getBackString();

	String getText(String key, Object... args);

	/**
	 * 从资源配置文件中提取内容并放入request.attribute中 目前资源配置文件为txt
	 * 
	 * @param key
	 * @param args
	 */
	void setText(String key, Object... args);

	void setSessionText(String key, Object... args);

	String saveToken();

	boolean isTokenValid();

	void clearToken();
}