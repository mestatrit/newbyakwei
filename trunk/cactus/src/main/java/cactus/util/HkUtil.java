package cactus.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class HkUtil implements ApplicationContextAware {

	public static final String REQUEST_METHOD_GET = "get";

	public static String SERVER_DEFAULT_CHARSET = "ISO-8859-1";

	public static final String DEFAULT_CHARSET = "utf-8";

	private static ApplicationContext webApplicationContext = null;

	public static final String CLOUD_IMAGE_AUTH = "cloud_imgrand_auth";

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

	public static final String ACTION_EXE_ATTR_KEY = "com.hk.action_exe_attr_key";

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

	private static synchronized Object getBeanFromSpring(String name) {
		Object obj = objMap.get(name);
		if (obj != null) {
			return obj;
		}
		try {
			return webApplicationContext.getBean(name);
		}
		catch (BeansException e) {
			return null;
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		webApplicationContext = applicationContext;
	}

	public static void main(String[] args) {
		System.out.println(HkUtil.class.getName());
	}
}