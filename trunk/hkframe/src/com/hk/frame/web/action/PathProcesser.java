package com.hk.frame.web.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.frame.util.ExceptionConfig;

public class PathProcesser {

	protected static final String HTTP_BEGIN = "http://";

	protected static final String HTTPS_BEGIN = "https://";

	protected static final String PATH_SEPARATOR = "/";

	public static void doExceptionForward(Exception exception,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取异常名称
		String type = exception.getClass().getName();
		// 根据名称找到对应的forward
		String result = ExceptionConfig.getExceptionForward(type);
		// 如果没有找到，就处理对应的servlet相应异常
		if (result == null) {
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
		proccessResult(result, request, response);
	}

	/**
	 * 处理返回页面。<br/>
	 * 例如r:/login则会redirect到{contextPath}/login。<br/>
	 * rr:/login则会redirect到/login。<br/>
	 * /login则会forward到{contextPath}/login<br/>
	 * 
	 * @param result
	 *            页面路径。如果以r:开头，则为redirect，如果以rr:开头，则不需要在返回的路径中加入contextPath
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void proccessResult(String result,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean containCtxPath = true;
		if (result == null) {
			return;
		}
		String path = null;
		boolean redirect = false;
		if (result.startsWith("r:")) {
			path = result.substring(2);
			redirect = true;
		}
		else if (result.startsWith("rr:")) {
			path = result.substring(3);
			redirect = true;
			containCtxPath = false;
		}
		else {
			path = result;
		}
		if (path.startsWith(HTTP_BEGIN) || path.startsWith(HTTPS_BEGIN)) {
			doStartWithHTTPForward(path, response);
			return;
		}
		doForward(redirect, path, containCtxPath, request, response);
	}

	public static void doStartWithHTTPForward(String path,
			HttpServletResponse response) throws IOException {
		response.sendRedirect(path);
	}

	/**
	 * 跳转页面。
	 * 
	 * @param isRedirect
	 *            是否重定向
	 * @param path
	 *            跳转路径
	 * @param containCtxPath
	 *            是否添加contextPath
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void doForward(boolean isRedirect, String path,
			boolean containCtxPath, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (path.startsWith(PATH_SEPARATOR)) {
			if (isRedirect) {
				if (containCtxPath) {
					response.sendRedirect(request.getContextPath() + path);
				}
				else {
					response.sendRedirect(path);
				}
				return;
			}
			request.getRequestDispatcher(path).forward(request, response);
			return;
		}
		if (isRedirect) {
			response.sendRedirect(path);
			return;
		}
		request.getRequestDispatcher(path).forward(request, response);
	}
}