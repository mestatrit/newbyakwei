package com.hk.frame.web.taglib;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import com.hk.frame.web.util.IteratorCss;

public class IteratorCssTag extends BaseBodyTag {
	private static final long serialVersionUID = -4509302348834847433L;

	private String css1;

	private String css2;

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		writer.append(IteratorCss.getCss(this.getRequest(), css1, css2));
	}

	public void setCss1(String css1) {
		this.css1 = css1;
	}

	public void setCss2(String css2) {
		this.css2 = css2;
	}
}