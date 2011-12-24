package com.hk.web.pub.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.web.action.PathProcesser;
import com.hk.frame.web.http.HkFilter;
import com.hk.svr.pub.Err;
import com.hk.web.util.HkWebConfig;
import com.hk.web.util.HkWebUtil;

public class LoginFilter extends HkFilter {
	private Map<String, Integer> map = new HashMap<String, Integer>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String s = filterConfig.getInitParameter("ignore");
		if (s != null) {
			String[] uri = s.split(",");
			for (int i = 0; i < uri.length; i++) {
				map.put(uri[i], 1);
			}
		}
	}

	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String r = request.getRequestURL().toString();
		String muri = r.substring(request.getContextPath().length());
		if (map.containsKey(muri)) {
			chain.doFilter(request, response);
		}
		else {
			User user = HkWebUtil.getLoginUser(request);
			if (user == null) {
				if (ServletUtil.getInt(request, "ajax") == 1) {
					ServletUtil.sendHtml(response, Err.USER_NOT_LOGIN);
					return;
				}
				String query = request.getQueryString();
				if (DataUtil.isEmpty(query)) {
					query = "";
				}
				// Boolean pc = (Boolean) request.getAttribute("pcbrowse");
				// if (pc != null && pc) {
				// String alurl = request.getRequestURL().append(
				// request.getQueryString()).toString();
				// String path = "http://" + HkWebConfig.getWebDomain()
				// + "/reg_toregweb.do?return_url="
				// + DataUtil.urlEncoder(alurl);
				// PathProcesser.proccessResult(path, request, response);
				// return;
				// }
				String alurl = request.getRequestURL().append("?").append(
						request.getQueryString()).toString();
				String path = "http://" + HkWebConfig.getWebDomain()
						+ "/tologin.do?return_url="
						+ DataUtil.urlEncoder(alurl);
				PathProcesser.proccessResult(path, request, response);
			}
			else {
				chain.doFilter(request, response);
			}
		}
	}
}