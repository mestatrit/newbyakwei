package iwant.web.admin.util;

import iwant.bean.AdminUser;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dev3g.cactus.web.action.HkFilter;
import com.dev3g.cactus.web.action.PathProcessor;

/**
 * 后台权限验证过滤器
 * 
 * @author akwei
 */
public class AdminFilter extends HkFilter {

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