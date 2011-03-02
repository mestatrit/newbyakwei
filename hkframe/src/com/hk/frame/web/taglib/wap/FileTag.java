package com.hk.frame.web.taglib.wap;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;

public class FileTag extends BaseWapTag {
	private static final long serialVersionUID = -8053348310989323364L;

	private String name;

	@Override
	protected void exeTag(JspWriter writer) throws IOException {
		writer.append("<input type=\"file\" name=\"");
		writer.append(name);
		writer.append("\"/>");
	}

	public void setName(String name) {
		this.name = name;
	}
}