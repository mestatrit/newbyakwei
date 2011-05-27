package com.dev3g.cactus.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.springframework.beans.factory.InitializingBean;

public class LocalResourceConfig implements InitializingBean {

	private boolean defResource;

	private Map<String, String> propertiesMap = new HashMap<String, String>();

	private List<String> list;

	private List<String> fileList;

	private String postfix;

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	public void setFileList(List<String> fileList) {
		this.fileList = fileList;
	}

	public void setDefResource(boolean defResource) {
		this.defResource = defResource;
	}

	public boolean isDefResource() {
		return defResource;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public Map<String, String> getPropertiesMap() {
		return propertiesMap;
	}

	public String getText(String key, Object... args) {
		if (args == null) {
			return propertiesMap.get(key);
		}
		String value = propertiesMap.get(key);
		if (value == null) {
			return key;
		}
		return MessageFormat.format(value, args);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		propertiesMap.clear();
		// proccessWithCache();
		proccessFile();
	}

	/**
	 * 使用普通文件替代properties作为配置文件,方便修改
	 * 
	 * @throws IOException
	 */
	public void proccessFile() throws IOException {
		List<String> olist = new ArrayList<String>();
		String basepath = LocalResourceConfig.class.getResource("/").getPath();
		for (String s : fileList) {
			if (DataUtil.isEmpty(s)) {
				continue;
			}
			if (s.endsWith("/")) {
				File f = new File(basepath + s);
				if (f.isDirectory()) {
					File[] fs = f.listFiles();
					if (fs != null) {
						for (File o : fs) {
							if (o.isFile()) {
								String shortName = o.getName().substring(0,
										o.getName().indexOf('.'));
								olist.add(basepath + s + shortName);
							}
						}
					}
				}
			}
			else {
				olist.add(basepath + s);
			}
		}
		String[] array = olist.toArray(new String[olist.size()]);
		if (array == null) {
			return;
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals("")) {
				continue;
			}
			File file = new File(array[i] + "." + postfix);
			if (file.exists()) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(file), "utf-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					if (line.startsWith("#")) {
						continue;
					}
					String[] e = this.parseKeyAndValue(line);
					propertiesMap.put(e[0], e[1]);
				}
			}
		}
	}

	private String[] parseKeyAndValue(String line) {
		int idx = line.indexOf('=');
		String key = line.substring(0, idx).trim();
		String value = line.substring(idx + 1, line.length()).trim();
		return new String[] { key, value };
	}

	public void proccessWithCache() {
		List<String> olist = new ArrayList<String>();
		String basepath = LocalResourceConfig.class.getResource("/").getPath();
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
		String[] array = olist.toArray(new String[olist.size()]);
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
}