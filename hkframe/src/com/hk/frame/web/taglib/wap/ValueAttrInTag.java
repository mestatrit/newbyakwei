package com.hk.frame.web.taglib.wap;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;

public class ValueAttrInTag extends BaseWapTag {
	private static final long serialVersionUID = -8159321319227097934L;

	private Object value_in_attr;

	private String name;

	@Override
	protected void exeTag(JspWriter writer) throws IOException {
		if (value_in_attr != null
				&& Boolean.parseBoolean(value_in_attr.toString())) {
			this.getRequest().setAttribute(name, this.getBodyContentAsString());
			return;
		}
		writer.append(this.getBodyContentAsString());
		writer.append("</body>");
		writer.append("</html>");
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue_in_attr(Object value_in_attr) {
		this.value_in_attr = value_in_attr;
	}
}