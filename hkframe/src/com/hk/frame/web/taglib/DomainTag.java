package com.hk.frame.web.taglib;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import com.hk.frame.util.DataUtil;

public class DomainTag extends BaseBodyTag {
	private static final long serialVersionUID = -3773140137719699301L;

	private String domain;

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		if (!DataUtil.isEmpty(this.domain)) {
			this.getRequest().setAttribute(DOMAIN_ATTR, this.domain);
		}
	}
}