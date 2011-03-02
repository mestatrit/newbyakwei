package com.hk.frame.web.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;

public class VelocityInitServlet extends HttpServlet {
	private static final long serialVersionUID = 5980233852603409637L;//

	@Override
	public void init(ServletConfig config) throws ServletException {
		this.initVM();
	}

	private void initVM() {
		try {
			Velocity
					.setProperty("class.resource.loader.class",
							"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
			Velocity
					.setProperty(RuntimeConstants.COUNTER_NAME, "velocityCount");
			Velocity.setProperty(RuntimeConstants.COUNTER_INITIAL_VALUE, "0");
			Velocity.setProperty(RuntimeConstants.ENCODING_DEFAULT, "UTF-8");
			Velocity.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
			Velocity.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");
			Velocity.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");
			Velocity.init();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}