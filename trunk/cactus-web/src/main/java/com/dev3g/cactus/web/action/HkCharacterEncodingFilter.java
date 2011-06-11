package com.dev3g.cactus.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * 处理不必要的文件过滤
 * 
 * @author akwei
 */
public class HkCharacterEncodingFilter extends CharacterEncodingFilter {

	private List<String> ingoreList = new ArrayList<String>();

	@Override
	protected void initFilterBean() throws ServletException {
		super.initFilterBean();
		FilterConfig config = this.getFilterConfig();
		String endIngore = config.getInitParameter("endIngore");
		if (endIngore != null) {
			String[] t = endIngore.split(",");
			if (t != null) {
				for (String s : t) {
					ingoreList.add(s);
				}
			}
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request)
			throws ServletException {
		String uri = request.getRequestURI();
		for (String s : ingoreList) {
			if (uri.endsWith(s)) {
				return true;
			}
		}
		return super.shouldNotFilter(request);
	}
}