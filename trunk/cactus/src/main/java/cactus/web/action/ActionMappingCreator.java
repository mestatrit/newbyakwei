package cactus.web.action;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ActionMappingCreator {

	private String endpfix = "/";

	private ActionFinder actionFinder;

	public ActionMappingCreator(ActionFinder actionFinder) {
		this.actionFinder = actionFinder;
	}

	/**
	 * ActionMapping缓存
	 */
	private final Map<String, ActionMapping> mappingMap = new HashMap<String, ActionMapping>();

	public ActionMapping getActionMapping(HttpServletRequest request,
			String spcMappingUri, String url_extension)
			throws NoActionException, NoSuchMethodException {
		if (spcMappingUri != null) {
			return this.getActionMapping(spcMappingUri);
		}
		String mappingUri = this.getMappingString(request, url_extension);
		return this.getActionMapping(mappingUri);
	}

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
		ActionMapping mapping = mappingMap.get(mappingUri);
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
	 * 解析uri,获得ActionMapping，"_"作为Action与方法名的分割.例如/user_list,
	 * 对应的action为UserAction，list是action中的一个方法。
	 * 
	 * @param mappingUri
	 * @return
	 * @throws NoActionException
	 * @throws NoSuchMethodException
	 */
	private synchronized ActionMapping parseActionMapping(String mappingUri)
			throws NoActionException, NoSuchMethodException {
		ActionMapping mapping = mappingMap.get(mappingUri);
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
			mapping.setAction(this.actionFinder.findAction(actionName));
			mapping.setMappingUri(mappingUri);
			mapping.setActionName(actionName);
			mapping.setMethodName(methodName);
			mapping.setActionMethod(this.getMethod(mapping.getAction(),
					methodName));
			mapping.setAsmAction(this.createAsmAction(mapping));
			mappingMap.put(mappingUri, mapping);
			return mapping;
		}
		mapping = new ActionMapping();
		mapping.setAction(this.actionFinder.findAction(mappingUri));
		mapping.setActionName(mappingUri);
		mappingMap.put(mappingUri, mapping);
		return mapping;
	}

	private Method getMethod(Action action, String methodName)
			throws NoSuchMethodException {
		Method method = action.getClass().getMethod(methodName,
				new Class[] { HkRequest.class, HkResponse.class });
		return method;
	}

	/**
	 * 动态创建action子类，子类调用父类指定的方法
	 * 
	 * @param actionMapping
	 * @return
	 */
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
	 * 解析uri，获得{@link ActionMapping}
	 * ,"_"作为action与方法名的分隔符。例如：/user_list。可以对应UserAction中list的方法
	 * 
	 * @param request
	 * @return
	 */
	private String getMappingString(HttpServletRequest request,
			String url_extension) {
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
}
