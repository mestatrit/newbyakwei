package com.hk.frame.web.taglib.web.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.AbstractTransactionalSpringContextTests;

@SuppressWarnings("deprecation")
public class HkTest extends AbstractTransactionalSpringContextTests {

	private HttpServletRequest request;

	private HttpServletResponse response;

	@Override
	protected String[] getConfigLocations() {
		return new String[] { "applicationContext.xml" };
	}

	@Override
	protected void onSetUp() throws Exception {
		request = new MockHttpServletRequest();
		request.setCharacterEncoding("utf-8");
		response = new MockHttpServletResponse();
		response.setCharacterEncoding("utf-8");
		super.onSetUp();
	}
}