package cactus.web.action;

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

	public void sendHtml(Object value) {
		ServletUtil.sendHtml(this.getHttpServletResponse(), value);
	}

	public void sendXML(String value) {
		ServletUtil.sendXml(this.getHttpServletResponse(), value);
	}
}