package cactus.web.action;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cactus.util.HkUtil;

public class ActionExe {

	private final Log log = LogFactory.getLog(ActionExe.class);

	private String url_extension;

	private boolean debug;

	private String endpfix = "/";

	/**
	 *寻找与uri对应的ActionMapper. uri与{@link ActionMapping} 一对一匹配.
	 * 
	 * @param mappingUri
	 * @return
	 * @throws NoActionException
	 * @throws NoSuchMethodException
	 */
	public ActionMapping getActionMapping(String mappingUri)
			throws NoActionException, NoSuchMethodException {
		ActionMapping mapping = ActionMapping.mappingMap.get(mappingUri);
		if (mapping == null) {
			try {
				mapping = parseActionMapping(mappingUri);
			}
			catch (NoActionException e) {
				throw e;
			}
			catch (NoSuchMethodException e) {
				throw e;
			}
		}
		return mapping;
	}

	/**
	 * 解析uri，获得{@link ActionMapping}
	 * ,"_"作为action与方法名的分隔符。例如：/user_list。可以对应UserAction中list的方法
	 * 
	 * @param request
	 * @return
	 */
	public String getMappingString(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String localuri = uri.substring(request.getContextPath().length(), uri
				.length());
		String actionNameAndMethod = null;
		// 如果uri有后缀则去掉后缀。例如/user_list.do，需要获得有用的部分为/user_list
		if (url_extension != null && localuri.endsWith(url_extension)) {
			actionNameAndMethod = localuri.substring(0, localuri
					.lastIndexOf(url_extension));
		}
		else {
			// 如果uri为"/"结尾，则需要去掉""。例如/user_list/，需要获得有用的部分为/user_list
			// 这种情况还没遇到，不知是否要去掉
			if (localuri.endsWith(endpfix)) {
				actionNameAndMethod = localuri.substring(0, localuri
						.lastIndexOf(endpfix));
			}
			else {
				actionNameAndMethod = localuri;
			}
		}
		return actionNameAndMethod;
	}

	/**
	 * 解析uri,获得ActionMapping，"_"作为Action与方法名的分割.例如/user_list,
	 * 对应的action为UserAction，list是action中的一个方法。
	 * 
	 * @param mappingUri
	 * @return
	 * @throws NoActionException
	 * @throws NoSuchMethodException
	 */
	public synchronized ActionMapping parseActionMapping(String mappingUri)
			throws NoActionException, NoSuchMethodException {
		ActionMapping mapping = ActionMapping.mappingMap.get(mappingUri);
		if (mapping != null) {
			return mapping;
		}
		int idx = mappingUri.lastIndexOf('_');
		if (idx != -1) {
			// 分解出method的名字
			String methodName = mappingUri.substring(idx + 1, mappingUri
					.length());
			// 分解出Action的名字
			String actionName = mappingUri.substring(0, idx);
			mapping = new ActionMapping();
			// action通过lazy方式加载
			Action action = (Action) HkUtil.getBean(actionName);
			if (action == null) {
				throw new NoActionException("no action [ " + actionName
						+ " ] is exist");
			}
			// 创建一个新的ActionMapping，并放入缓存
			mapping.setAction(action);
			mapping.setActionName(actionName);
			mapping.setMethodName(methodName);
			Method method = null;
			method = this.getMethod(mapping.getAction(), methodName);
			mapping.setActionMethod(method);
			mapping.setAsmAction(this.createAsmAction(mapping));
			ActionMapping.mappingMap.put(mappingUri, mapping);
			return mapping;
		}
		mapping = new ActionMapping();
		// action通过lazy方式加载
		Action action = (Action) HkUtil.getBean(mappingUri);
		if (action == null) {
			throw new NoActionException("no action [ " + mappingUri
					+ " ] is exist");
		}
		mapping.setAction(action);
		mapping.setActionName(mappingUri);
		ActionMapping.mappingMap.put(mappingUri, mapping);
		return mapping;
	}

	private Action createAsmAction(ActionMapping actionMapping) {
		ASMActionCreater asmActionCreater = new ASMActionCreater(Thread
				.currentThread().getContextClassLoader());
		Class<Action> clazz = asmActionCreater.createASMAction(actionMapping);
		try {
			Object obj = clazz.getConstructor().newInstance();
			Action action = (Action) obj;
			Field field = action.getClass().getDeclaredField("action");
			field.setAccessible(true);
			field.set(action, actionMapping.getAction());
			return action;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 执行uri对应的action以及方法
	 * 
	 * @param mappingUri
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String invokeInterceptor(String mappingUri,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HkRequest hkRequest = null;
		HkResponse hkResponse = null;
		if (request instanceof HkRequest) {
			hkRequest = (HkRequest) request;
		}
		else {
			hkRequest = new HkRequestImpl(request);
		}
		if (response instanceof HkResponse) {
			hkResponse = (HkResponse) response;
		}
		else {
			hkResponse = new HkResponseImpl(response);
		}
		if (request.getAttribute(HkUtil.ACTION_EXE_ATTR_KEY) == null) {
			request.setAttribute(HkUtil.ACTION_EXE_ATTR_KEY, this);
		}
		try {
			ActionMapping mapping = this.getActionMapping(mappingUri);
			HkActionInvocation invocation = new HkActionInvocation();
			invocation.setRequest(hkRequest);
			invocation.setResponse(hkResponse);
			invocation.setActionMapping(mapping);
			// 如果设置了拦截器，则需要进行拦截器匹配
			HkInterceptor hkInterceptor = HkIntercepotorUtil
					.getInterceptor(mappingUri);
			invocation.addInterceptor(hkInterceptor);
			return invocation.invoke();
		}
		catch (NoActionException e) {
			if (debug) {
				log.error("no action [ " + request.getRemoteAddr() + " ] [ "
						+ request.getRequestURI() + " ] " + e.getMessage());
				log.error("queryString [ " + request.getQueryString() + " ]");
				log.error("===================== end =====================");
			}
			return null;
		}
		catch (NoSuchMethodException e) {
			if (debug) {
				log.error("no method [ " + request.getRemoteAddr() + " ] [ "
						+ request.getRequestURI() + " ] " + e.getMessage());
				log.error("queryString [ " + request.getQueryString() + " ]");
				log.error("===================== end =====================");
			}
			return null;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			proccessClearUploadFile(hkRequest);
		}
	}

	public void proccess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String mappingUri = this.getMappingString(request);
			String path = this.invokeInterceptor(mappingUri, request, response);
			PathProcesser.proccessResult(path, request, response);
		}
		catch (Exception e) {
			PathProcesser.doExceptionForward(e, request, response);
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

	public Method getMethod(Action action, String methodName)
			throws NoSuchMethodException {
		Method method = action.getClass().getMethod(methodName,
				new Class[] { HkRequest.class, HkResponse.class });
		return method;
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