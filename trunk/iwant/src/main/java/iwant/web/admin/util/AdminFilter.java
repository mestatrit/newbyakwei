package iwant.web.admin.util;

import halo.web.action.HaloFilter;
import halo.web.action.PathProcessor;
import iwant.bean.AdminUser;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台权限验证过滤器
 * 
 * @author akwei
 */
public class AdminFilter extends HaloFilter {

	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		AdminUser adminUser = AdminUtil.getLoginAdminUser(request);
		if (adminUser == null) {
			PathProcessor
					.processResult("r:/sitemgrlogin.do", request, response);
			return;
		}
		request.setAttribute("admin_login", true);
		chain.doFilter(request, response);
	}
}