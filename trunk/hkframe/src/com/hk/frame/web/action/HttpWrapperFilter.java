package com.hk.frame.web.action;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.frame.web.http.HkFilter;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkRequestImpl;
import com.hk.frame.web.http.HkResponse;
import com.hk.frame.web.http.HkResponseImpl;

/**
 * 包装HttpServletRequest 和 HttpServletResponse，让下面的处理过程可直接调用最新的reqeust
 * response,需要放到filter中，逻辑处理之前最好在第一个filter的位置
 * 
 * @author akwei
 */
public class HttpWrapperFilter extends HkFilter {

	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HkRequest hkRequest = new HkRequestImpl(request);
		HkResponse hkResponse = new HkResponseImpl(response);
		chain.doFilter(hkRequest, hkResponse);
	}
}