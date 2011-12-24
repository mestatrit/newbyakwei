package com.hk.web.cmpunion.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.bean.CmpUnion;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.web.http.HkFilter;
import com.hk.svr.CmpUnionService;
import com.hk.web.cmpunion.action.CmpUnionUtil;

public class SysUnionFilter extends HkFilter {
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String domain = request.getServerName();
		CmpUnionUtil.isPcBrowse(request);
		CmpUnionUtil.getLoginUser(request);
		if (domain.equalsIgnoreCase("mall.huoku.com")) {
			this.filterBySystem(request, response, chain);
		}
		else {
			this.filterByDomain(request, response, chain);
		}
	}

	private void filterByDomain(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String domain = request.getServerName();
		if (domain.startsWith("www")) {
			domain = domain.replaceFirst("www\\.", "");
		}
		CmpUnionService cmpUnionService = (CmpUnionService) HkUtil
				.getBean("cmpUnionService");
		CmpUnion cmpUnion = cmpUnionService.getCmpUnionByDomain(domain);
		if (cmpUnion == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		if (cmpUnion.isLimit()) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		if (cmpUnion.isStop()) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		request.setAttribute("cmpUnion", cmpUnion);
		request.setAttribute("uid", cmpUnion.getUid());
		chain.doFilter(request, response);
	}

	/**
	 * 针对http://mall.huoku.com/u/[0-9]+
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	private void filterBySystem(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		long uid = ServletUtil.getLong(request, "uid");
		CmpUnion cmpUnion = null;
		CmpUnionService cmpUnionService = (CmpUnionService) HkUtil
				.getBean("cmpUnionService");
		if (uid == 0) {
			String webName = ServletUtil.getString(request, "webName");
			if (webName != null) {
				cmpUnion = cmpUnionService.getCmpUnionByWebName(webName);
			}
		}
		else {
			cmpUnion = cmpUnionService.getCmpUnion(uid);
		}
		if (cmpUnion == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		if (cmpUnion.isStop()) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		request.setAttribute("uid", cmpUnion.getUid());
		request.setAttribute("cmpUnion", cmpUnion);
		chain.doFilter(request, response);
	}
}