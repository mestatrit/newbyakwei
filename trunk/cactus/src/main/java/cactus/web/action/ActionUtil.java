package cactus.web.action;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;

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
			// spring3 annotation 获取方式
			return getBeanFromSpring3(name);
		}
		catch (BeansException e) {
			return (Action) HkUtil.getWebApplicationContext().getBean(name);
		}
	}

	private static synchronized Action getBeanFromSpring3(String name) {
		try {
			Action obj = (Action) HkUtil
					.getAnnotationconfigapplicationcontext().getBean(name);
			if (obj == null) {
				return null;
			}
			processObjectAutowiredField(obj);
			return obj;
		}
		catch (BeansException e) {
			return null;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 由于通过anntion lazy 注入的bean autowired的属性无值，特此注入
	 * 
	 * @param action
	 *            2010-6-28
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private static void processObjectAutowiredField(Action action)
			throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = action.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.get(action) == null) {
				if (field.getAnnotation(Autowired.class) != null) {
					Class<?> clazz = field.getType();
					try {
						Object fieldObj = HkUtil.getWebApplicationContext()
								.getBean(clazz);
						field.set(action, fieldObj);
					}
					catch (BeansException e) {
					}
				}
			}
		}
	}
}
