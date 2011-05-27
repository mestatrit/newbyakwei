package com.dev3g.cactus.web.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dev3g.cactus.util.HkUtil;
import com.dev3g.cactus.web.util.HkWebUtil;

public class ActionExeImpl implements ActionExe {

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
		if (request.getAttribute(HkUtil.ACTION_EXE_ATTR_KEY) == null) {
			request.setAttribute(HkUtil.ACTION_EXE_ATTR_KEY, this);
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

	@Override
	public String invoke(String mappingUri, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.invokeInterceptor(
				this.actionMappingCreator.getActionMapping(mappingUri),
				request, response);
	}

	public void afterProcess(HkRequest request) {
		File[] files = request.getFiles();
		if (files != null) {
			for (File f : files) {
				if (f != null && f.exists()) {
					f.delete();
				}
			}
		}
	}
}