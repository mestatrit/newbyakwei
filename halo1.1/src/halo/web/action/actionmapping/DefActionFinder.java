package halo.web.action.actionmapping;

import halo.util.HaloUtil;
import halo.web.action.NoActionException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DefActionFinder {

	private static final Map<String, Object> actionMap = new HashMap<String, Object>();

	private static List<String> scanPathList;

	public static final AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();

	public static void init(List<String> scanPathList) {
		DefActionFinder.scanPathList = scanPathList;
		if (DefActionFinder.scanPathList != null) {
			for (String path : DefActionFinder.scanPathList) {
				annotationConfigApplicationContext.scan(path);
			}
		}
	}

	/**
	 * 查找Action class
	 * 
	 * @param actionUrl
	 *            除去request.getContextPath部分与除去后缀部分剩下的mappingUri<br/>
	 *            例如：app/user/set/action_list.do <br/>
	 *            actionUrl为/user/set/action
	 * @return
	 * @throws NoActionException
	 */
	public static Object findAction(String actionUrl) throws NoActionException {
		Object obj = actionMap.get(actionUrl);
		if (obj == null) {
			obj = getBeanFromSpring(actionUrl);
			if (obj != null) {
				actionMap.put(actionUrl, obj);
			}
			else {
				throw new NoActionException("no action [ " + actionUrl
						+ " ] is exist");
			}
		}
		return obj;
	}

	private static synchronized Object getBeanFromSpring(String name) {
		Object obj = actionMap.get(name);
		if (obj == null) {
			try {
				obj = HaloUtil.getWebApplicationContext().getBean(name);
			}
			catch (BeansException e) {
				obj = getBeanFromSpring3(name);
			}
			actionMap.put(name, obj);
		}
		return obj;
	}

	private static synchronized Object getBeanFromSpring3(String name) {
		try {
			Object obj = annotationConfigApplicationContext.getBean(name);
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
	 * @param obj
	 *            2010-6-28
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private static void processObjectAutowiredField(Object obj)
			throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.get(obj) == null) {
				if (field.getAnnotation(Autowired.class) != null) {
					Class<?> clazz = field.getType();
					try {
						Object fieldObj = HaloUtil.getWebApplicationContext()
								.getBean(clazz);
						field.set(obj, fieldObj);
					}
					catch (BeansException e) {
					}
				}
			}
		}
	}
}
