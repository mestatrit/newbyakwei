package com.dev3g.cactus.web.action;

import java.util.Map;

/**
 * 拦截器暂时没有用处，还没想好如何使用
 * 
 * @author akwei
 */
public class HkIntercepotorUtil {

	private static Map<String, HkInterceptor> interceptorMap;

	public void setInterceptorMap(Map<String, HkInterceptor> interceptorMap) {
		HkIntercepotorUtil.interceptorMap = interceptorMap;
	}

	/**
	 * 根据uri进行查找是否在xml中配置了拦截器
	 * 
	 * @param uri
	 * @return
	 */
	public static HkInterceptor getInterceptor(String uri) {
		if (interceptorMap == null) {
			return null;
		}
		return interceptorMap.get(uri);
	}
}