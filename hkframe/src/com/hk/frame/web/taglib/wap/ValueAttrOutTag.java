package com.hk.frame.web.taglib.wap;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;

public class ValueAttrOutTag extends BaseWapTag {
	private static final long serialVersionUID = -4933349465520615690L;

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected void exeTag(JspWriter writer) throws IOException {
		String v = (String) this.getRequest().getAttribute(name);
		if (v != null) {
			writer.append(v);
		}
	}
}