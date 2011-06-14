package com.dev3g.cactus.web.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dev3g.cactus.util.FileUtil;
import com.dev3g.cactus.web.util.HkWebUtil;

/**
 * mvc程序处理的核心类，对匹配的url进行处理并返回处路径
 * 
 * @author akwei
 */
public class ActionExe {

	private ActionMappingCreator actionMappingCreator;

	public void setActionMappingCreator(
			ActionMappingCreator actionMappingCreator) {
		this.actionMappingCreator = actionMappingCreator;
	}

	/**
	 * 执行uri对应的action以及方法，包括配置的拦截器一并运行
	 * 
	 * @param mappingUri
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private String invokeInterceptor(ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HkRequest hkRequest = HkWebUtil.getHkRequest(request);
		HkResponse hkResponse = HkWebUtil.getHkResponse(response);
		if (request.getAttribute(WebCnf.ACTION_EXE_ATTR_KEY) == null) {
			request.setAttribute(WebCnf.ACTION_EXE_ATTR_KEY, this);
		}
		try {
			// 如果设置了拦截器，则需要进行拦截器匹配
			HkInterceptor hkInterceptor = HkIntercepotorUtil
					.getInterceptor(mapping.getMappingUri());
			return new HkActionInvocation(mapping, hkRequest, hkResponse,
					hkInterceptor).invoke();
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			afterProcess(hkRequest);
		}
	}

	/**
	 * 处理对应mappingUri的action
	 * 
	 * @param mappingUri 去除contextPath，后缀之后剩下的部分<br>
	 *            例如：/webapp/user_list.do,mappingUri=/user_list
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String invoke(String mappingUri, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.invokeInterceptor(this.actionMappingCreator
				.getActionMapping(mappingUri), request, response);
	}

	public void afterProcess(HkRequest request) {
		this.deleteFiles(request);
	}

	private boolean deleteFiles(HkRequest request) {
		File[] files = request.getFiles();
		if (files != null) {
			for (File f : files) {
				if (!FileUtil.deleteFile(f)) {
					continue;
				}
			}
		}
		return true;
	}
}