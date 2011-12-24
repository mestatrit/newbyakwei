package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.hk.frame.util.DataUtil;

public class CompanyScoreConfig {
	private static LinkedHashMap<String, CompanyScoreConfig> map;

	private static final Map<Integer, Integer> starmap = new HashMap<Integer, Integer>();
	static {
		starmap.put(3, 5);
		starmap.put(2, 4);
		starmap.put(1, 3);
		starmap.put(-1, 2);
		starmap.put(-2, 1);
		starmap.put(0, 0);
	}

	public static int getStar(int key) {
		if (key > 3 || key < -2) {
			return 0;
		}
		return starmap.get(key);
	}

	private int score;// 用户可评分

	private int sysScore;// 系统评分

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getSysScore() {
		return sysScore;
	}

	public void setSysScore(int sysScore) {
		this.sysScore = sysScore;
	}

	public void setMap(LinkedHashMap<String, CompanyScoreConfig> map) {
		CompanyScoreConfig.map = map;
	}

	public static List<CompanyScoreConfig> getList() {
		List<CompanyScoreConfig> list = new ArrayList<CompanyScoreConfig>();
		list.addAll(map.values());
		return list;
	}

	public static CompanyScoreConfig getCompanyScoreConfig(int score) {
		return map.get(score + "");
	}

	public static boolean isElement(int score) {
		Collection<CompanyScoreConfig> c = map.values();
		List<Integer> valueList = new ArrayList<Integer>();
		for (CompanyScoreConfig o : c) {
			valueList.add(o.getScore());
		}
		return DataUtil.isInElements(score, valueList
				.toArray(new Integer[valueList.size()]));
	}
}