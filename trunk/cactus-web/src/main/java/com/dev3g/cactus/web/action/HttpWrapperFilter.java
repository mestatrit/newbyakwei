package com.dev3g.cactus.web.action;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dev3g.cactus.util.HkUtil;
import com.dev3g.cactus.web.action.upload.cos.ExceededSizeException;
import com.dev3g.cactus.web.util.HkWebUtil;
import com.dev3g.cactus.web.util.ServletUtil;

/**
 * 包装HttpServletRequest 和 HttpServletResponse，让下面的处理过程可直接调用最新的reqeust
 * response,需要放到filter中，逻辑处理之前最好在第一个filter的位置
 * 
 * @author akwei
 */
public class HttpWrapperFilter extends HkFilter {

	private MappingUriCreater mappingUriCreater;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		WebCnf webCnf = (WebCnf) HkUtil.getBean("webCnf");
		this.mappingUriCreater = webCnf.getMappingUriCreater();
	}

	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		WebCnf webCnf = (WebCnf) request.getAttribute(WebCnf.WEBCNF_OBJ_KEY);
		if (webCnf == null) {
			webCnf = (WebCnf) HkUtil.getBean("webCnf");
			request.setAttribute(WebCnf.WEBCNF_OBJ_KEY, webCnf);
		}
		if (ServletUtil.isMultipart(request)) {
			this.processUpload(request, response, chain, webCnf);
			return;
		}
		chain.doFilter(HkWebUtil.getHkRequest(request), HkWebUtil
				.getHkResponse(response));
	}

	private void processUpload(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, WebCnf webCnf)
			throws IOException, ServletException {
		if (webCnf.isMustCheckUpload()) {
			this.processCheckUpload(request, response, chain, webCnf);
			return;
		}
		this.processNoCheckUpload(request, response, chain, webCnf);
	}

	private void processNoCheckUpload(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, WebCnf webCnf)
			throws IOException, ServletException {
		FileUpload fileUpload;
		try {
			fileUpload = new FileUpload(request,
					webCnf.getUploadFileTempPath(), 0);
			HkRequest hkRequest = HkWebUtil.getHkRequest(fileUpload
					.getHkMultiRequest());
			hkRequest.setUploadFiles(fileUpload.getUploadFiles());
			chain.doFilter(hkRequest, HkWebUtil.getHkResponse(response));
		}
		catch (ExceededSizeException e) {
			request.setAttribute(WebCnf.UPLOAD_EXCEEDEDSIZE_KEY, true);
			chain.doFilter(HkWebUtil.getHkRequest(request), HkWebUtil
					.getHkResponse(response));
		}
	}

	private void processCheckUpload(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, WebCnf webCnf)
			throws IOException, ServletException {
		String mappinguri = this.mappingUriCreater.findMappingUri(request);
		UploadFileCheckCnf checkCnf = webCnf.getUploadFileCheckCnf(mappinguri);
		// 严格检查upload mappinguri,只有经过配置后的mappinguri才允许上传文件
		// mappinguri, 例如 uri: {appctx}/user/set_head.do
		// mappinguri: /user/set_head
		if (checkCnf == null) {
			// 如果没有对应配置的uri，忽略文件上传
			chain.doFilter(HkWebUtil.getHkRequest(request), HkWebUtil
					.getHkResponse(response));
			return;
		}
		try {
			FileUpload fileUpload = new FileUpload(request, webCnf
					.getUploadFileTempPath(), checkCnf.getMaxSize());
			HkRequest hkRequest = HkWebUtil.getHkRequest(fileUpload
					.getHkMultiRequest());
			hkRequest.setUploadFiles(fileUpload.getUploadFiles());
			chain.doFilter(hkRequest, HkWebUtil.getHkResponse(response));
		}
		catch (ExceededSizeException e) {
			request.setAttribute(WebCnf.UPLOAD_EXCEEDEDSIZE_KEY, true);
			chain.doFilter(HkWebUtil.getHkRequest(request), HkWebUtil
					.getHkResponse(response));
		}
	}
}