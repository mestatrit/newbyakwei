package com.dev3g.cactus.web.action;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dev3g.cactus.util.HkUtil;
import com.dev3g.cactus.web.util.HkWebUtil;

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
		WebCnf webCnf = (WebCnf) request.getAttribute(WebCnf.WEBCNF_OBJ_KEY);
		if (webCnf == null) {
			webCnf = (WebCnf) HkUtil.getBean("webCnf");
			request.setAttribute(WebCnf.WEBCNF_OBJ_KEY, webCnf);
		}
		String uri = request.getRequestURI();
		String localuri = uri.substring(request.getContextPath().length(),
				uri.length());
		UploadFileCheckCnf checkCnf = webCnf.getUploadFileCheckCnf(localuri);
		if (checkCnf != null) {
			request.setAttribute(WebCnf.UPLOAD_LIMIT_SIZE_KEY, checkCnf);
		}
		chain.doFilter(HkWebUtil.getHkRequest(request),
				HkWebUtil.getHkResponse(response));
	}
}