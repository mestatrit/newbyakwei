package cactus.web.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;

import cactus.util.HkUtil;

public class ActionUtil {

	public static final Map<String, Action> objMap = new HashMap<String, Action>();

	public static Action getAction(String name) {
		Action obj = objMap.get(name);
		if (obj == null) {
			obj = getBeanFromSpring(name);
			if (obj != null) {
				objMap.put(name, obj);
			}
		}
		return obj;
	}

	private static synchronized Action getBeanFromSpring(String name) {
		Action obj = objMap.get(name);
		if (obj != null) {
			return obj;
		}
		try {
			return (Action) HkUtil.getWebApplicationContext().getBean(name);
		}
		catch (BeansException e) {
			return null;
		}
	}
}
