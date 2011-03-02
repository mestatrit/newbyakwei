package com.hk.frame.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentFilterUtil {
	private Map<String, Integer> filterContentMap;

	private List<String> list;

	private String fileName;

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public ContentFilterUtil(String fileName) {
		this.fileName = fileName;
		this.init();
	}

	private void init() {
		this.filterContentMap = new HashMap<String, Integer>();
		this.list = new ArrayList<String>();
		String filePath = ContentFilterUtil.class.getResource("/").getPath()
				+ "/" + fileName;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath), "utf-8"));
			String s = null;
			while ((s = reader.readLine()) != null) {
				filterContentMap.put(s, 1);
				list.add(s);
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean hasFilterString(String content) {
		if (content == null) {
			return false;
		}
		String s = content.trim().replaceAll("\\s+", "").replaceAll("　", "");
		for (String o : list) {
			if (s.indexOf(o) != -1) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		ContentFilterUtil util = new ContentFilterUtil("samplefilter.txt");
		System.out.println(util.hasFilterString("法轮功"));
	}
}