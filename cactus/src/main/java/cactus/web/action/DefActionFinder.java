package cactus.web.action;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cactus.util.HkUtil;

public class DefActionFinder implements ActionFinder {

	private final Map<String, Action> actionMap = new HashMap<String, Action>();

	private List<String> scanPathList;

	public final AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();

	public void setScanPathList(List<String> scanPathList) {
		this.scanPathList = scanPathList;
		if (this.scanPathList != null) {
			for (String path : this.scanPathList) {
				annotationConfigApplicationContext.scan(path);
			}
		}
	}

	@Override
	public Action findAction(String key) throws NoActionException {
		Action obj = actionMap.get(key);
		if (obj == null) {
			obj = getBeanFromSpring(key);
			if (obj != null) {
				actionMap.put(key, obj);
			}
			else {
				throw new NoActionException("no action [ " + key
						+ " ] is exist");
			}
		}
		return obj;
	}

	private synchronized Action getBeanFromSpring(String name) {
		Action obj = actionMap.get(name);
		if (obj == null) {
			try {
				obj = (Action) HkUtil.getWebApplicationContext().getBean(name);
			}
			catch (BeansException e) {
				obj = getBeanFromSpring3(name);
			}
			actionMap.put(name, obj);
		}
		return obj;
	}

	private synchronized Action getBeanFromSpring3(String name) {
		try {
			Object obj = this.annotationConfigApplicationContext.getBean(name);
			if (obj == null) {
				return null;
			}
			processObjectAutowiredField(obj);
			return (Action) obj;
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
	private void processObjectAutowiredField(Object obj)
			throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.get(obj) == null) {
				if (field.getAnnotation(Autowired.class) != null) {
					Class<?> clazz = field.getType();
					try {
						Object fieldObj = HkUtil.getWebApplicationContext()
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
