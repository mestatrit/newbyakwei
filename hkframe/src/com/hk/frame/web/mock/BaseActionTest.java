package com.hk.frame.web.mock;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.AbstractTransactionalSpringContextTests;

import com.hk.frame.util.HkUtil;

@SuppressWarnings("deprecation")
public class BaseActionTest extends AbstractTransactionalSpringContextTests {

	protected MockHttpServletRequest createMockHttpServletRequest(
			String method, String uri) {
		MockHttpServletRequest request = new MockHttpServletRequest(method, uri);
		HkUtil.SERVER_DEFAULT_CHARSET = "UTF-8";
		request.setCharacterEncoding("UTF-8");
		request.setServerName("localhost");
		request.setServerPort(8080);
		request.setContextPath("/app");
		return request;
	}

	protected MockHttpServletResponse createMockHttpServletResponse() {
		MockHttpServletResponse response = new MockHttpServletResponse();
		response.setContentType("utf-8");
		return response;
	}

	protected void commit() {
		this.transactionManager.commit(this.transactionStatus);
	}
}