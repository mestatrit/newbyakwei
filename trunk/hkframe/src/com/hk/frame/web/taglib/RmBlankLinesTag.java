package com.hk.frame.web.taglib;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;

public class RmBlankLinesTag extends BaseBodyTag {
	private static final long serialVersionUID = -3571636163818425159L;

	@SuppressWarnings("unused")
	private boolean rm;

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		writer.append(this.process(this.getBodyContentAsString()));
	}

	private String process(String content) {
		return content;
	}

	@SuppressWarnings("unused")
	private String replace(String strSource, String strFrom, String strTo) {
		if (strSource == null) {
			return null;
		}
		int i = 0;
		if ((i = strSource.indexOf(strFrom, i)) >= 0) {
			char[] cSrc = strSource.toCharArray();
			char[] cTo = strTo.toCharArray();
			int len = strFrom.length();
			StringBuffer buf = new StringBuffer(cSrc.length);
			buf.append(cSrc, 0, i).append(cTo);
			i += len;
			int j = i;
			while ((i = strSource.indexOf(strFrom, i)) > 0) {
				buf.append(cSrc, j, i - j).append(cTo);
				i += len;
				j = i;
			}
			buf.append(cSrc, j, cSrc.length - j);
			return buf.toString();
		}
		return strSource;
	}

	public void setRm(boolean rm) {
		this.rm = rm;
	}
}