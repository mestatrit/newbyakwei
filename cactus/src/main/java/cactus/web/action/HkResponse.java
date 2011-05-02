package cactus.web.action;

import javax.servlet.http.HttpServletResponse;

public interface HkResponse extends HttpServletResponse {

	void sendXML(String value);

	void sendXML2(String value);

	void sendHtml(Object value);

	/**
	 * 删除cookie
	 * 
	 * @param name
	 *            cookie名称 2010-8-17
	 */
	void removeCookie(String name, String domain, String path);
}