package com.etbhk.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.bean.taobao.Tb_Admin;
import com.hk.bean.taobao.Tb_User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.web.action.PathProcesser;
import com.hk.frame.web.http.HkFilter;
import com.hk.svr.Tb_AdminService;
import com.hk.svr.Tb_UserService;

public class TbFilter extends HkFilter {

	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setAttribute(TbHkWebUtil.CTXPATH, request.getContextPath());
		Tb_User tbUser = (Tb_User) ServletUtil.getSessionValue(request,
				TbHkWebUtil.SESSION_LOGIN_USER_ATTRKEY);
		if (tbUser == null) {// session里没有
			tbUser = TbHkWebUtil.getLoginUserFromCookie(request);// cookie中取
			if (tbUser != null) {
				Tb_UserService tb_UserService = (Tb_UserService) HkUtil
						.getBean("tb_UserService");
				tbUser = tb_UserService.getTb_User(tbUser.getUserid());
			}
			if (tbUser != null) {// 合法的登录用户
				// 放到session中
				ServletUtil.setSessionValue(request,
						TbHkWebUtil.SESSION_LOGIN_USER_ATTRKEY, tbUser);
			}
		}
		if (tbUser != null) {
			request.setAttribute(TbHkWebUtil.LOGIN_USER_ATTRKEY, tbUser);
		}
		String uri = request.getRequestURI();
		if (uri.indexOf("/op/") != -1 || uri.indexOf("_prv") != -1
				|| uri.startsWith("/tb/admin/")) {// 需要用户登录
			if (tbUser == null) {
				this.processLogin(request, response);
				return;
			}
		}
		// 后台管理
		if (uri.startsWith("/tb/admin/")) {
			if (tbUser == null) {
				this.processLogin(request, response);
				return;
			}
			Tb_AdminService tbAdminService = (Tb_AdminService) HkUtil
					.getBean("tb_AdminService");
			Tb_Admin tbAdmin = tbAdminService.getTb_AdminByUserid(tbUser
					.getUserid());
			if (tbAdmin == null) {
				this.processLogin(request, response);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private void processLogin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = "http://" + request.getServerName()
				+ "/tb/login?return_url="
				+ DataUtil.urlEncoder(ServletUtil.getReturnUrl(request));
		PathProcesser.proccessResult(path, request, response);
	}
}