package com.dev3g.cactus.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dev3g.cactus.web.action.HkRequest;
import com.dev3g.cactus.web.action.HkRequestImpl;
import com.dev3g.cactus.web.action.HkResponse;
import com.dev3g.cactus.web.action.HkResponseImpl;

public class HkWebUtil {

	public static HkRequest getHkRequest(HttpServletRequest request) {
		if (request instanceof HkRequest) {
			return (HkRequest) request;
		}
		return new HkRequestImpl(request);
	}

	public static HkResponse getHkResponse(HttpServletResponse response) {
		if (response instanceof HkResponse) {
			return (HkResponse) response;
		}
		return new HkResponseImpl(response);
	}
}