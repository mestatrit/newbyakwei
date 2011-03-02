package com.hk.frame.web.taglib.wap;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;

public class WapTag extends BaseWapTag {
	private static final long serialVersionUID = 8589073843013099419L;

	private String title;

	private String style;

	@SuppressWarnings("unused")
	private boolean rm;

	private String bodyId;

	@Override
	protected void exeTag(JspWriter writer) throws IOException {
		// writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		writer
				.append("<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.0//EN\" \"http://www.wapforum.org/DTD/xhtml-mobile10.dtd\">");
		writer.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		writer.append("<head>");
		writer.append("<title>");
		writer.append(this.getTitle());
		writer.append("</title>");
		writer.append("<link rel=\"stylesheet\"");
		if (style == null) {
			style = (String) this.getRequest().getAttribute(CSS_IMPORT_ATTR);
		}
		if (style != null && !style.equals("")) {
			writer.append(" href=\"");
			writer.append(this.getRequest().getContextPath());
			writer.append(style);
			writer.append("\"");
		}
		writer.append(" type=\"text/css\"/>");
		String cssContent = (String) this.getRequest().getAttribute(
				CSS_CONTENT_ATTR);
		if (cssContent != null) {
			writer.append(cssContent);
		}
		writer.append("</head>");
		writer.append("<body");
		if (this.bodyId != null) {
			writer.append(" id=\"");
			writer.append(this.bodyId);
			writer.append("\"");
		}
		String otherBodyParam = (String) this.getRequest().getAttribute(
				BODY_OTHERBODYPARAM);
		if (otherBodyParam != null) {
			writer.append(otherBodyParam);
		}
		writer.append(">");
		writer.append(this.getBodyContentAsString());
		writer.append("</body>");
		writer.append("</html>");
	}

	public void setRm(boolean rm) {
		this.rm = rm;
	}

	public String getTitle() {
		if (title == null) {
			return "";
		}
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setBodyId(String bodyId) {
		this.bodyId = bodyId;
	}
}