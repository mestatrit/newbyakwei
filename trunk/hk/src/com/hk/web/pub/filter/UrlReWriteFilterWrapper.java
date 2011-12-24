package com.hk.web.pub.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;

import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

public class UrlReWriteFilterWrapper implements Filter {

	private List<String> ingoreList = new ArrayList<String>();

	private UrlRewriteFilter urlRewriteFilter = new UrlRewriteFilter();

	public void init(FilterConfig filterConfig) throws ServletException {
		this.urlRewriteFilter.init(filterConfig);
		String endIngore = filterConfig.getInitParameter("endIngore");
		if (endIngore != null) {
			String[] t = endIngore.split(",");
			if (t != null) {
				for (String s : t) {
					ingoreList.add(s);
				}
			}
		}
	}

	public void destroy() {
		this.urlRewriteFilter.destroy();
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		for (String s : ingoreList) {
			if (uri.endsWith(s)) {
				chain.doFilter(request, response);
				return;
			}
		}
		if (req.getServerName().indexOf("huoku.com") == -1) {
			if (!urlRewriteFilter.isLoaded()) {
				throw new UnavailableException("urlwriter not initialised");
			}
			urlRewriteFilter.doFilter(request, response, chain);
			return;
		}
//		if (uri.startsWith("/ehk")) {// 淘宝火酷
//			if (!urlRewriteFilter.isLoaded()) {
//				throw new UnavailableException("urlwriter not initialised");
//			}
//			urlRewriteFilter.doFilter(request, response, chain);
//			return;
//		}
		chain.doFilter(request, response);
	}
}