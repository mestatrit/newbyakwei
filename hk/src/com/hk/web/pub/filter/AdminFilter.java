package com.hk.web.pub.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hk.bean.AdminUser;
import com.hk.bean.User;
import com.hk.frame.util.HkUtil;
import com.hk.frame.web.http.HkFilter;
import com.hk.svr.UserService;
import com.hk.web.util.HkWebUtil;

public class AdminFilter extends HkFilter {
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		User loginUser = HkWebUtil.getLoginUser(request);
		if (loginUser != null) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			AdminUser adminUser = userService.getAdminUser(loginUser
					.getUserId());
			if (adminUser != null) {
				chain.doFilter(request, response);
			}
		}
	}
}