package com.dev3g.cactus.web.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionResultProcessor {

	public void processResult(String path, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			PathProcessor.processResult(path, request, response);
		}
		catch (Exception e) {
			PathProcessor.doExceptionForward(e, request, response);
		}
	}
}