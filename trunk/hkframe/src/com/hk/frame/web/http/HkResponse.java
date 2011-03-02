package com.hk.frame.web.http;

import javax.servlet.http.HttpServletResponse;

public interface HkResponse extends HttpServletResponse {

	void sendXML(String value);

	void sendXML2(String value);

	void sendHtml(Object value);

	void jsRedirect(String url);

	/**
	 * javascript 警告，然后关闭页面
	 * 
	 * @param s 输出信息
	 */
	void alertJSAndClose(String s);

	/**
	 * javascript 警告，然后重定向
	 * 
	 * @param s 输出信息
	 * @param url 重定向地址
	 */
	void alertJSAndRedirect(String s, String url);

	/**
	 * javascript 警告，然后后退
	 * 
	 * @param s 输出信息
	 */
	void alertJSAndGoBack(String s);

	/**
	 * javascript 警告，然后选择性确认
	 * 
	 * @param s 输出信息
	 * @param trueurl 当选择确定时，执行的url
	 * @param falseurl 当选择拒绝时，执行的url
	 */
	void alertConfirmJS(String s, String trueurl, String falseurl);

	void alertJS(String s);

	/**
	 * 删除cookie
	 * 
	 * @param name cookie名称
	 *            2010-8-17
	 */
	void removeCookie(String name, String domain, String path);
}