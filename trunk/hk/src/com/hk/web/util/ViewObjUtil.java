package com.hk.web.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.InitializingBean;

import com.hk.svr.pub.Err;

public class ViewObjUtil implements Err, InitializingBean {

	private ViewObjUtil() {//
	}

	private static String filePath;

	private static final Map<Integer, String> map = new HashMap<Integer, String>();

	public void setFilePath(String filePath) {
		ViewObjUtil.filePath = filePath;
	}

	public void afterPropertiesSet() throws Exception {
		ResourceBundle rb = ResourceBundle.getBundle(filePath);
		Enumeration<String> keys = rb.getKeys();
		while (keys.hasMoreElements()) {
			Integer key = Integer.parseInt(keys.nextElement());
			map.put(key, rb.getString(key.toString()));
		}
	}

	public static String getValue(int err) {
		return map.get(err);
	}

	public static String getValue2(int err) {
		ResourceBundle rb = ResourceBundle.getBundle(filePath);
		return rb.getString(String.valueOf(err));
	}
}