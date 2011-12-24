package com.hk.web.pub.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hk.frame.web.action.PathProcesser;
import com.hk.frame.web.http.HkFilter;
import com.hk.web.util.BlackIpUtil;

public class IpFilter extends HkFilter {
	private final Log log = LogFactory.getLog(IpFilter.class);

	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request.getRequestURI().indexOf("api/") != -1) {
			chain.doFilter(request, response);
			return;
		}
		String ip = request.getRemoteAddr();
		if (BlackIpUtil.containIp(ip)) {
			log.info("blackip [ " + ip + " ]");
			// request.getRequestDispatcher("/WEB-INF/page/build.jsp").forward(
			// arg0, arg1);
			PathProcesser.proccessResult("/WEB-INF/page/build.jsp", request,
					response);
			return;
		}
		chain.doFilter(request, response);
	}
}