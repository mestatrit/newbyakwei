package com.dev3g.cactus.web.action;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.dev3g.cactus.web.util.ServletUtil;

public class HkResponseImpl extends HttpServletResponseWrapper implements
		HkResponse {

	public HkResponseImpl(HttpServletResponse response) {
		super(response);
	}

	private HttpServletResponse getHttpServletResponse() {
		return (HttpServletResponse) this.getResponse();
	}

	@Override
	public void sendHtml(Object value) {
		ServletUtil.sendHtml(this.getHttpServletResponse(), value);
	}

	@Override
	public void sendXML(String value) {
		ServletUtil.sendXml(this.getHttpServletResponse(), value);
	}
}