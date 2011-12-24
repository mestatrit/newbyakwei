package com.hk.web.user.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.HkUtil;
import com.hk.frame.web.action.PathProcesser;
import com.hk.frame.web.http.HkFilter;
import com.hk.svr.UserService;
import com.hk.web.util.HkWebUtil;

public class BlackUserFilter extends HkFilter {
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String uri = request.getRequestURI();
		if (uri.indexOf("api/") != -1 || uri.startsWith("/epp/")) {
			chain.doFilter(request, response);
			return;
		}
		if (uri.startsWith("/sys/")) {
			chain.doFilter(request, response);
			return;
		}
		User loginUser = HkWebUtil.getLoginUser(request);
		if (loginUser != null) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			UserOtherInfo o = userService.getUserOtherInfo(loginUser
					.getUserId());
			if (o != null && o.getUserStatus() == UserOtherInfo.USERSTATUS_STOP) {
				PathProcesser.proccessResult("/logout.do", request, response);
				return;
			}
		}
		chain.doFilter(request, response);
	}
}