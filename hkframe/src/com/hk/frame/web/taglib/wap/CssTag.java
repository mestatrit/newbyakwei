package com.hk.frame.web.taglib.wap;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;

public class CssTag extends BaseWapTag {
	private static final long serialVersionUID = 756365438370223524L;

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		String cssContent = this.getBodyContentAsString();
		this.getRequest().setAttribute(CSS_CONTENT_ATTR, cssContent);
	}
}