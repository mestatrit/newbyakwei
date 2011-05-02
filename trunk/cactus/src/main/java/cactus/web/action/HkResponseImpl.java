package cactus.web.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import cactus.web.util.ServletUtil;

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