package com.hk.web.cmpunion.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.bean.User;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.web.http.HkFilter;
import com.hk.svr.CmpUnionService;
import com.hk.web.util.HkWebUtil;

public class OpCmpUnionFilter extends HkFilter {
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		long uid = ServletUtil.getLong(request, "uid");
		User loginUser = HkWebUtil.getLoginUser(request);
		CmpUnionService cmpUnionService = (CmpUnionService) HkUtil
				.getBean("cmpUnionService");
		if (cmpUnionService.isCmpUnionOpUser(uid, loginUser.getUserId())) {
			chain.doFilter(request, response);
		}
	}
}