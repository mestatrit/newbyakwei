package com.hk.web.pub.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent arg0) {
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		// User user = (User) event.getSession()
		// .getAttribute(HkWebUtil.LOGIN_USER);
		// if (user != null) {
		// HkWebUtil.removeLoginUserId(user.getUserId());
		// }
	}
}