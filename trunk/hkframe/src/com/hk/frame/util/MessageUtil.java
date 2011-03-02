package com.hk.frame.util;

import javax.servlet.http.HttpServletRequest;

public class MessageUtil {
	private MessageUtil() {//
	}

	public static String getMessage(HttpServletRequest request) {
		String msg = (String) request.getAttribute(HkUtil.MESSAGE_NAME);
		if (msg == null) {
			msg = (String) request.getSession().getAttribute(
					HkUtil.MESSAGE_NAME);
		}
		request.getSession().removeAttribute(HkUtil.MESSAGE_NAME);
		return msg;
	}
}