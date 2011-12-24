package com.hk.web.box.filter;

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
import com.hk.frame.web.http.HkRequest;
import com.hk.svr.UserService;
import com.hk.web.util.HkWebUtil;

public class OpBoxFilter extends HkFilter {
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		User loginUser = HkWebUtil.getLoginUser(request);
		if (loginUser == null) {
			return;
		}
		UserService userService = (UserService) HkUtil.getBean("userService");
		UserOtherInfo userOtherInfo = userService.getUserOtherInfo(loginUser
				.getUserId());
		if (userOtherInfo.getMobileBind() != UserOtherInfo.MOBILE_BIND) {
			HkRequest hkr = (HkRequest) request;
			hkr.setSessionMessage("手机号码未认证，不能发布宝箱和管理宝箱");
			// response.sendRedirect(request.getContextPath() + "/more.do");
			PathProcesser.proccessResult("r:/more.do", request, response);
			return;
		}
		chain.doFilter(request, response);
	}
}