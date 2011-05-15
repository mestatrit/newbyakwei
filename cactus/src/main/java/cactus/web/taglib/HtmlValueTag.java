package cactus.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import cactus.util.DataUtil;

public class HtmlValueTag extends BaseTag {

	private static final long serialVersionUID = 2444660484543529830L;

	private String value;

	private boolean onerow;

	private boolean textarea;

	public void setTextarea(boolean textarea) {
		this.textarea = textarea;
	}

	public void setOnerow(boolean onerow) {
		this.onerow = onerow;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		if (DataUtil.isEmpty(this.value)) {
			return;
		}
		if (onerow) {
			writer.append(DataUtil.toHtmlSimpleOneRow(this.value));
			return;
		}
		if (this.textarea) {
			writer.append(DataUtil.toHtmlSimple(this.value).replaceAll("<br/>",
					"\n"));
			return;
		}
		writer.append(DataUtil.toHtmlSimple(this.value));
	}
}