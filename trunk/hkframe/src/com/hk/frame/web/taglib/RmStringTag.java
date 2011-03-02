package com.hk.frame.web.taglib;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;

public class RmStringTag extends BaseBodyTag {
	private static final long serialVersionUID = -6422880012886743925L;

	private String value;

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		String s = this.getBodyContentAsString();
		StringBuilder sb = new StringBuilder(s);
		int idx = sb.lastIndexOf(value);
		if (idx != -1) {
			if (value.length() == 1) {
				sb.deleteCharAt(idx);
			}
			else {
				sb.delete(idx, idx + value.length());
			}
		}
		writer.append(sb.toString());
	}

	public static void main(String[] args) {
		String s = "dfsdf";
		StringBuilder sb = new StringBuilder(s);
		String ii = "fs";
		int idx = sb.indexOf(ii);
		sb.delete(idx, idx + ii.length());
		System.out.println(sb.toString());
	}
}