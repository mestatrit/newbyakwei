package com.hk.frame.web.action;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionCtrl extends HttpServlet {

	private static final long serialVersionUID = -6592484990302927841L;

	private ActionExe actionExe = new ActionExe();

	@Override
	public void init(ServletConfig config) throws ServletException {
		String url_extension = config.getInitParameter("url-extension");
		String v = config.getInitParameter("debug");
		if (v != null && v.equals("true")) {
			actionExe.setDebug(true);
		}
		actionExe.setUrl_extension(url_extension);
		super.init(config);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.actionExe.proccess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.actionExe.proccess(request, response);
	}
}