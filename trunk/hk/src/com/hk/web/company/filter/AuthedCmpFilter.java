package com.hk.web.company.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.web.http.HkFilter;
import com.hk.svr.CompanyService;
import com.hk.web.util.HkWebUtil;

/**
 * 如果企业没有被认领，不能使用某些功能的过滤器
 * 
 * @author akwei
 */
public class AuthedCmpFilter extends HkFilter {
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		User loginUser = HkWebUtil.getLoginUser(request);
		long companyId = ServletUtil.getLong(request, "companyId");
		if (companyId > 0) {
			CompanyService companyService = (CompanyService) HkUtil
					.getBean("companyService");
			Company o = companyService.getCompany(companyId);
			if (o == null) {
				return;
			}
			// 如果企业没有被认领 ,不合法进入
			if (o.getUserId() > 0) {
				if (loginUser.getUserId() != o.getUserId()) {
					if (!HkWebUtil.isAdminUser(request)) {// 不合法进入
						ServletUtil.setSessionText(request,
								"func.when_authed_company_nopower_edit");
						response.sendRedirect("/e/cmp.do?companyId="
								+ companyId);
						return;
					}
				}
			}
			else {
				ServletUtil.setSessionText(request,
						"func.when_authed_company_nopower_edit");
				response.sendRedirect("/e/cmp.do?companyId=" + companyId);
				return;
			}
		}
		chain.doFilter(request, response);
	}
}