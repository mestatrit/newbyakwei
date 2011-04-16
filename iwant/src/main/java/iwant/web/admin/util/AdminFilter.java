package iwant.web.admin.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.frame.web.action.PathProcesser;
import com.hk.frame.web.http.HkFilter;

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
		if (!AdminUtil.isAdminLogined(request)) {
			PathProcesser.proccessResult("r:/sitemgrlogin.do", request,
					response);
			return;
		}
		request.setAttribute("admin_login", true);
		chain.doFilter(request, response);
	}
}