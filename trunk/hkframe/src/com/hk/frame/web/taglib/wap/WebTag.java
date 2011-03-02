package com.hk.frame.web.taglib.wap;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;

public class WebTag extends BaseWapTag {
	private static final long serialVersionUID = 8589073843013099419L;

	private Object title;

	public void setTitle(Object title) {
		this.title = title;
	}

	@Override
	protected void exeTag(JspWriter writer) throws IOException {
		writer
				.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		writer.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		writer.append("<head>");
		if (title != null) {
			writer.append("<title>").append(title.toString())
					.append("</title>");
		}
		String hk_head_inner_content = (String) this.getRequest().getAttribute(
				HK_HEAD_INNER_CONTENT);
		if (hk_head_inner_content != null) {
			writer.append(hk_head_inner_content);
		}
		String hk_meta_data = (String) this.getRequest().getAttribute(
				HK_META_DATA);
		if (hk_meta_data != null) {
			writer.append(hk_meta_data);
		}
		writer.append("</head>");
		writer.append("<body");
		String otherBodyParam = (String) this.getRequest().getAttribute(
				BODY_OTHERBODYPARAM);
		if (otherBodyParam != null) {
			writer.append(" ");
			writer.append(otherBodyParam);
		}
		writer.append(">");
		writer.append(this.getBodyContentAsString());
		writer.append("</body>");
		writer.append("</html>");
	}
}