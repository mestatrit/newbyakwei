package com.hk.frame.util;

import java.util.Collection;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;

public class Copy_2_of_ResourceConfig implements InitializingBean {
	private static Map<String, LocalResourceConfig> localResourceConfigMap;

	private static LocalResourceConfig defConfig;

	public void setLocalResourceConfigMap(
			Map<String, LocalResourceConfig> localResourceConfigMap) {
		Copy_2_of_ResourceConfig.localResourceConfigMap = localResourceConfigMap;
	}

	public static String getText(String key, Object... args) {
		return Copy_2_of_ResourceConfig.defConfig.getText(key, args);
	}

	public void afterPropertiesSet() throws Exception {
		Collection<LocalResourceConfig> c = localResourceConfigMap.values();
		for (LocalResourceConfig o : c) {
			if (o.isDefResource()) {
				defConfig = o;
				break;
			}
		}
	}
}