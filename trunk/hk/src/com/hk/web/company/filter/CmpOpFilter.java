package com.hk.web.company.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.web.http.HkFilter;
import com.hk.svr.CompanyService;
import com.hk.svr.UserService;
import com.hk.web.util.HkWebUtil;

/**
 * 如果企业已经被认领或者试用或者管理员修改企业信息
 * 
 * @author akwei
 */
public class CmpOpFilter extends HkFilter {
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String uri = request.getRequestURI();
		if (uri.indexOf("/review/op/op") != -1) {
			chain.doFilter(request, response);
			return;
		}
		long companyId = ServletUtil.getLong(request, "companyId");
		User loginUser = HkWebUtil.getLoginUser(request);
		UserService userService = (UserService) HkUtil.getBean("userService");
		UserOtherInfo info = userService
				.getUserOtherInfo(loginUser.getUserId());
		if (!info.isMobileAlreadyBind()) {
			ServletUtil.setSessionText(request, "func.mobilenotbind");
			if (companyId > 0) {
				response.sendRedirect("/e/cmp.do?companyId=" + companyId);
			}
			else {
				response.sendRedirect("/more.do");
			}
			return;
		}
		if (companyId > 0) {
			CompanyService companyService = (CompanyService) HkUtil
					.getBean("companyService");
			Company o = companyService.getCompany(companyId);
			if (o == null) {
				return;
			}
			if (o.isFreeze()) {
				ServletUtil.setSessionText(request,
						"func.company.cannotopforfreeze");
				response.sendRedirect("/e/cmp.do?companyId=" + companyId);
				return;
			}
			// 如果企业已经被认领或者试用
			if (o.getUserId() > 0) {
				if (loginUser.getUserId() != o.getUserId()) {// 不合法进入
					if (!HkWebUtil.isAdminUser(request)) {// 不合法进入
						ServletUtil.setSessionText(request,
								"func.when_authed_company_nopower_edit");
						response.sendRedirect("/e/cmp.do?companyId="
								+ companyId);
						return;
					}
				}
			}
		}
		chain.doFilter(request, response);
	}
}