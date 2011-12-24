package com.hk.web.act.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hk.bean.Act;
import com.hk.bean.User;
import com.hk.frame.util.HkUtil;
import com.hk.frame.web.http.HkFilter;
import com.hk.svr.ActService;

public class OpActFilter extends HkFilter {
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		User loginUser = (User) request.getAttribute("loginUser");
		String s = request.getParameter("actId");
		if (s == null || s.trim().length() == 0) {
			chain.doFilter(request, response);
			return;
		}
		long actId = Long.parseLong(s);
		ActService actService = (ActService) HkUtil.getBean("actService");
		Act act = actService.getAct(actId);
		if (act == null) {
			return;
		}
		if (act.getUserId() != loginUser.getUserId()) {
			return;
		}
		chain.doFilter(request, response);
	}
}