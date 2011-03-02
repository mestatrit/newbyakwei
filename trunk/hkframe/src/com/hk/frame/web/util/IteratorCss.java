package com.hk.frame.web.util;

import javax.servlet.http.HttpServletRequest;

public class IteratorCss {
	private static final String CSS = "hk_request_css";

	public static String getCss(HttpServletRequest request, String css1,
			String css2) {
		String css = (String) request.getAttribute(CSS);
		if (css == null) {
			request.setAttribute(CSS, css1);
			return css1;
		}
		if (css.equals(css1)) {
			request.setAttribute(CSS, css2);
			return css2;
		}
		request.setAttribute(CSS, css1);
		return css1;
	}
}