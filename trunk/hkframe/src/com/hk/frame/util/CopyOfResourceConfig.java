package com.hk.frame.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import org.springframework.beans.factory.InitializingBean;

public class CopyOfResourceConfig implements InitializingBean {
	private static String properties;

	private static final Map<String, String> propertiesMap = new HashMap<String, String>();

	private static List<String> list;

	public void setList(List<String> list) {
		CopyOfResourceConfig.list = list;
	}

	public Map<String, String> getPropertiesMap() {
		return propertiesMap;
	}

	public static String getText(String key, Object... args) {
		if (args == null) {
			return CopyOfResourceConfig.propertiesMap.get(key);
		}
		String value = CopyOfResourceConfig.propertiesMap.get(key);
		if (value == null) {
			return key;
		}
		return MessageFormat.format(value, args);
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		CopyOfResourceConfig.properties = properties;
	}

	public void afterPropertiesSet() throws Exception {
		proccessWithCache();
	}

	public static void proccessWithCache() {
		CopyOfResourceConfig.propertiesMap.clear();
		List<String> olist = new ArrayList<String>();
		String basepath = CopyOfResourceConfig.class.getResource("/").getPath();
		for (String s : list) {
			if (s.endsWith("/")) {
				File f = new File(basepath + s);
				if (f.isDirectory()) {
					File[] fs = f.listFiles();
					if (fs != null) {
						for (File o : fs) {
							if (o.isFile()) {
								String shortName = o.getName().substring(0,
										o.getName().indexOf('.'));
								olist.add(s + shortName);
							}
						}
					}
				}
			}
			else {
				olist.add(s);
			}
		}
		String[] array = null;
		if (properties == null) {
			array = olist.toArray(new String[olist.size()]);
		}
		else {
			array = properties.split(",");
		}
		if (array == null) {
			return;
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals("")) {
				continue;
			}
			ResourceBundle rb = ResourceBundle.getBundle(array[i]);
			Enumeration<String> keys = rb.getKeys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				propertiesMap.put(key, rb.getString(key));
			}
		}
	}

	public static void proccessWithoutcache() {
		CopyOfResourceConfig.propertiesMap.clear();
		String[] array = properties.split(",");
		if (array == null) {
			return;
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals("")) {
				continue;
			}
			Properties p = new Properties();
			String path = CopyOfResourceConfig.class.getResource("/").getPath()
					+ array[i] + ".properties";
			File file = new File(path);
			try {
				p.load(new FileInputStream(file));
				Enumeration<Object> keys = p.keys();
				while (keys.hasMoreElements()) {
					String key = (String) keys.nextElement();
					propertiesMap.put(key, p.getProperty(key));
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(CopyOfResourceConfig.class.getResource("/").getPath());
		System.out.println(CopyOfResourceConfig.class.getResource("").getPath());
	}
}