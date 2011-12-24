package com.hk.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;

/**
 * 足迹的最大分类
 * 
 * @author akwei
 */
public class ParentKind implements InitializingBean {
	private final static Map<Integer, ParentKind> map = new HashMap<Integer, ParentKind>();

	private int kindId;

	private String name;

	private List<CompanyKind> kindlist;

	public void setKindlist(List<CompanyKind> kindlist) {
		this.kindlist = kindlist;
	}

	public List<CompanyKind> getKindlist() {
		return kindlist;
	}

	public int getKindId() {
		return kindId;
	}

	public void setKindId(int kindId) {
		this.kindId = kindId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void afterPropertiesSet() throws Exception {
		map.put(this.kindId, this);
	}

	public static ParentKind getParentKind(int kindId) {
		return map.get(kindId);
	}
}