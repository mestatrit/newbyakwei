package cactus.web.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cactus.util.HkUtil;
import cactus.web.util.HkWebUtil;

public class ActionExe {

	private final Log log = LogFactory.getLog(ActionExe.class);

	private String url_extension;

	private boolean debug;

	private ActionMappingCreator actionMappingCreator = new ActionMappingCreator();

	/**
	 * 执行uri对应的action以及方法
	 * 
	 * @param mappingUri
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String invokeInterceptor(ActionMapping mapping,
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
			proccessClearUploadFile(hkRequest);
		}
	}

	public String invoke(HttpServletRequest request, String spcMappingUri,
			HttpServletResponse response) throws Exception {
		return this.invokeInterceptor(this.actionMappingCreator
				.getActionMapping(request, spcMappingUri, this.url_extension),
				request, response);
	}

	public void proccess(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			PathProcessor.processResult(this.invoke(request, null, response),
					request, response);
		}
		catch (NoActionException e) {
			if (debug) {
				log.error("no action [ " + request.getRemoteAddr() + " ] [ "
						+ request.getRequestURI() + " ] " + e.getMessage());
				log.error("queryString [ " + request.getQueryString() + " ]");
				log.error("===================== end =====================");
			}
		}
		catch (NoSuchMethodException e) {
			if (debug) {
				log.error("no method [ " + request.getRemoteAddr() + " ] [ "
						+ request.getRequestURI() + " ] " + e.getMessage());
				log.error("queryString [ " + request.getQueryString() + " ]");
				log.error("===================== end =====================");
			}
		}
		catch (Exception e) {
			PathProcessor.doExceptionForward(e, request, response);
		}
	}

	public void proccessClearUploadFile(HkRequest request) {
		File[] files = request.getFiles();
		if (files != null) {
			for (File f : files) {
				if (f != null && f.exists()) {
					f.delete();
				}
			}
		}
	}

	public String getUrl_extension() {
		return url_extension;
	}

	public void setUrl_extension(String urlExtension) {
		url_extension = urlExtension;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}