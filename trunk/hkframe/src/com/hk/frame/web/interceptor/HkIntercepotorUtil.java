package com.hk.frame.web.interceptor;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

public class HkIntercepotorUtil implements InitializingBean {

	private static Map<String, HkInterceptor> interceptorMap;

	private static boolean freeze;

	public void setInterceptorMap(Map<String, HkInterceptor> interceptorMap) {
		if (freeze) {
			throw new RuntimeException("interceptorMap is freeze");
		}
		HkIntercepotorUtil.interceptorMap = interceptorMap;
	}

	public void afterPropertiesSet() throws Exception {
		freeze = true;
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