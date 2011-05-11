package com.hk.frame.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hk.frame.web.action.Action;

public class HkUtil implements ApplicationContextAware, InitializingBean {

	public static final String REQUEST_METHOD_GET = "get";

	public static String SERVER_DEFAULT_CHARSET = "ISO-8859-1";

	public static final String DEFAULT_CHARSET = "utf-8";

	private static ApplicationContext webApplicationContext = null;

	public static final String CLOUD_IMAGE_AUTH = "cloud_imgrand_auth";

	// private static final String EMPTY_CONTENT = "";
	public static final String JSESSIONID = "sid";

	public static final String COM_HK_JSESSIONID_IN_ATTRIBUTE = "com_hk_jsessionid_in_attribute";

	public static final String MESSAGE_NAME = "com_hk_message_name_request_session";

	public static final String SIMPLEPAGE_ATTRIBUTE = "com.hk.wap.simplepage.attribute";

	public static final String PAGESUPPORT_ATTRIBUTE = "com.hk.wap.pagesupport.attribute";

	public static final String NEEDTOPVIEW = "com_hk_wap_need_top_view_flg";

	public static final String RETURN_URL = "return_url";

	public static final String backString_key = "backString";

	public static final String DENC_RETURN_URL = "denc_return_url";

	public static final String TO_URL = "to_url";

	public static final String REQ_TOKEN_KEY = "req_com_hk_form_token_key";

	public static final String SESSION_TOKEN_KEY = "session_com_hk_form_token_key";

	private static final AnnotationConfigApplicationContext ANNOTATION_CONFIG_APPLICATION_CONTEXT = new AnnotationConfigApplicationContext();

	private static List<String> scanPathList;

	public static final String ACTION_EXE_ATTR_KEY = "com.hk.action_exe_attr_key";

	public void setScanPathList(List<String> scanPathList) {
		HkUtil.scanPathList = scanPathList;
	}

	public static final Map<String, Object> objMap = new HashMap<String, Object>();

	public static ApplicationContext getWebApplicationContext() {
		return webApplicationContext;
	}

	public static Object getBean(String name) {
		Object obj = objMap.get(name);
		if (obj == null) {
			obj = getBeanFromSpring(name);
			if (obj != null) {
				objMap.put(name, obj);
			}
		}
		return obj;
	}

	public static void main(String[] args) {
		System.out.println(HkUtil.class.getName());
	}

	private static synchronized Object getBeanFromSpring(String name) {
		Object obj = objMap.get(name);
		if (obj != null) {
			return obj;
		}
		// spring3 annotation 获取方式
		try {
			return webApplicationContext.getBean(name);
		}
		catch (BeansException e) {
			return getBeanFromSpring3(name);
		}
	}

	private static synchronized Object getBeanFromSpring3(String name) {
		try {
			Object obj = ANNOTATION_CONFIG_APPLICATION_CONTEXT.getBean(name);
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
						Object fieldObj = webApplicationContext.getBean(clazz);
						field.set(obj, fieldObj);
					}
					catch (BeansException e) {
					}
				}
			}
		}
	}

	public static String buildString(String... args) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			builder.append(args[i]);
		}
		return builder.toString();
	}

	public static int getRandomPageBegin(int count, int size) {
		Random r = new Random();
		int begin = 0;
		if (count > size) {
			while ((begin = r.nextInt(count - size + 1)) < 0) {
				//				
			}
		}
		return begin;
	}

	public static String randColor() {
		int len = 3;
		char[] array = { '0', '3', '6', '9', 'C', 'F' };
		String color = null;
		boolean ok = false;
		while (!ok) {
			StringBuilder temp = new StringBuilder("#");
			Random r = new Random();
			for (int i = 0; i < 3; i++) {
				temp.append(array[r.nextInt(len)]);
			}
			color = temp.toString();
			if (!color.equals("#FFF") || !color.equals("#CCC")) {
				ok = true;
			}
		}
		return color;
	}

	// public static String toHtmlValue(String value) {
	// if (value != null) {
	// return value.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
	// .replaceAll(">", "&gt;").replaceAll("\"", "&quot;")
	// .replaceAll("\n", "<br/>").replaceAll("\r", EMPTY_CONTENT)
	// .replaceAll("\\s+", "&nbsp;");
	// }
	// return EMPTY_CONTENT;
	// }
	// public static String toTextValue(String htmlValue) {
	// if (htmlValue != null) {
	// return htmlValue.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
	// .replaceAll(" +", " ").replaceAll("&amp;", "&").replaceAll(
	// "<br/>", "\n").replaceAll("&quot;", "\"");
	// }
	// return EMPTY_CONTENT;
	// }
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		webApplicationContext = applicationContext;
	}

	public void afterPropertiesSet() throws Exception {
		if (scanPathList != null) {
			for (String path : scanPathList) {
				ANNOTATION_CONFIG_APPLICATION_CONTEXT.scan(path);
			}
		}
	}

	public static Map<String, Action> getBeansOfType() {
		Map<String, Action> map = null;
		try {
			map = webApplicationContext
					.getBeansOfType(Action.class, true, true);
		}
		catch (BeansException e) {
			try {
				map = ANNOTATION_CONFIG_APPLICATION_CONTEXT.getBeansOfType(
						Action.class, true, true);
			}
			catch (BeansException e1) {
				throw new RuntimeException(e);
			}
		}
		return map;
	}
}