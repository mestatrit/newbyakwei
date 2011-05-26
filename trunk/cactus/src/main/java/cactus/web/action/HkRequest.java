package cactus.web.action;

import java.io.File;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import cactus.web.util.PageSupport;
import cactus.web.util.SimplePage;

public interface HkRequest extends HttpServletRequest {

	String ISO = "ISO-8859-1";

	String UTF_8 = "UTF-8";

	Object getSessionValue(String name);

	Number getNumber(String key);

	Number getNumber(String key, Number num);

	String getString(String key);

	String getStringRow(String key);

	String getStringRow(String key, String def);

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

	void setSessionValue(String name, Object value);

	void removeSessionvalue(String name);

	void invalidateSession();

	SimplePage getSimplePage(int size);

	void reSetAttribute(String name);

	void reSetEncodeAttribute(String name);

	void setEncodeAttribute(String name, String value);
}