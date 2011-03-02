package com.hk.frame.web.http;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import com.hk.frame.util.ServletUtil;

public class HkResponseImpl extends HttpServletResponseWrapper implements
		HkResponse {

	public HkResponseImpl(HttpServletResponse response) {
		super(response);
	}

	private HttpServletResponse getHttpServletResponse() {
		return (HttpServletResponse) this.getResponse();
	}

	private void write(String value) {
		ServletUtil.write((HttpServletResponse) this.getResponse(), value);
	}

	public void alertConfirmJS(String s, String trueurl, String falseurl) {
		ServletUtil.alertConfirmJS((HttpServletResponse) this.getResponse(), s,
				trueurl, falseurl);
	}

	public void alertJS(String s) {
		ServletUtil.alertJS(this.getHttpServletResponse(), s);
	}

	public void alertJSAndClose(String s) {
		ServletUtil.alertJSAndClose(this.getHttpServletResponse(), s);
	}

	public void alertJSAndGoBack(String s) {
		ServletUtil.alertJSAndGoBack(this.getHttpServletResponse(), s);
	}

	public void alertJSAndRedirect(String s, String url) {
		ServletUtil.alertJSAndRedirect(this.getHttpServletResponse(), s, url);
	}

	public void jsRedirect(String url) {
		ServletUtil.jsRedirect(this.getHttpServletResponse(), url);
	}

	public void sendHtml(Object value) {
		ServletUtil.sendHtml(this.getHttpServletResponse(), value);
	}

	public void sendXML(String value) {
		this.getResponse().setContentType("text/xml;charset=UTF-8");
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append(value);
		this.write(sb.toString());
	}

	public void sendXML2(String value) {
		ServletUtil.sendXml2(this.getHttpServletResponse(), value);
	}

	public void removeCookie(String name, String domain, String path) {
		Cookie cookie = new Cookie(name, "");
		cookie.setPath(path);
		cookie.setMaxAge(0);
		cookie.setDomain(domain);
		this.getHttpServletResponse().addCookie(cookie);
	}
}