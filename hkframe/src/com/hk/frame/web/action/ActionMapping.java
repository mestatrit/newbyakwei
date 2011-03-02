package com.hk.frame.web.action;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Action类的匹配对象
 * 
 * @author akwei
 */
class ActionMapping {

	/**
	 * ActionMapping缓存
	 */
	public static final Map<String, ActionMapping> mappingMap = new HashMap<String, ActionMapping>();

	// public static final Map<String, Action> actionMap = new HashMap<String,
	// Action>();
	/**
	 * Action中要运行的方法名称
	 */
	private String methodName;

	/**
	 * Action对象
	 */
	private Action action;

	/**
	 * Action匹配名称
	 */
	private String actionName;

	/**
	 * Action中要运行的方法对象
	 */
	private Method actionMethod;

	// /**
	// * 进行匹配的uri
	// */
	// private String mappingUri;
	//
	// public String getMappingUri() {
	// return mappingUri;
	// }
	//
	// public void setMappingUri(String mappingUri) {
	// this.mappingUri = mappingUri;
	// }
	public ActionMapping() {//
	}

	public void setActionMethod(Method actionMethod) {
		this.actionMethod = actionMethod;
	}

	public Method getActionMethod() {
		return actionMethod;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public static Map<String, ActionMapping> getMappingMap() {
		return mappingMap;
	}

	// public static Map<String, Action> getActionMap() {
	// return actionMap;
	// }
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public boolean hasMethod() {
		if (this.methodName == null) {
			return false;
		}
		return true;
	}
}