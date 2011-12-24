package com.hk.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.CompanyKindService;

public class CompanyKindUtil {
	private static final LinkedHashMap<Integer, CompanyKind> map = new LinkedHashMap<Integer, CompanyKind>();

	private static LinkedHashMap<Integer, ParentKind> parentmap;

	public static List<Integer> valueList;

	private static List<CompanyKind> list;

	private static final List<ParentKind> parentlist = new ArrayList<ParentKind>();

	private CompanyKindUtil() {//
	}

	/**
	 * leftnav.jsp调用
	 * 
	 * @return
	 */
	public static List<ParentKind> getParentList() {
		return parentlist;
	}

	public void setParentmap(LinkedHashMap<Integer, ParentKind> parentmap) {
		CompanyKindUtil.parentmap = parentmap;
	}

	public static CompanyKind getCompanyKind(int kindId) {
		return map.get(kindId);
	}

	public static List<CompanyKind> getCompanyKindListByParentKindId(
			int parentId) {
		ParentKind o = parentmap.get(parentId);
		if (o == null) {
			return new ArrayList<CompanyKind>();
		}
		return o.getKindlist();
	}

	/**
	 * 子分类的id
	 * 
	 * @param kindId
	 * @return 返回父分类的id
	 */
	public static int getParentKindId(int kindId) {
		CompanyKind o = map.get(kindId);
		if (o != null) {
			return o.getParentId();
		}
		return 0;
	}

	/**
	 * @param kindId 大分类的id
	 * @return
	 */
	public static ParentKind getParentKind(int kindId) {
		return parentmap.get(kindId);
	}

	public static List<CompanyKind> getCompanKindList() {
		Collection<CompanyKind> c = map.values();
		List<CompanyKind> list = new ArrayList<CompanyKind>();
		list.addAll(c);
		return list;
	}

	public static boolean isElement(int kindId) {
		return DataUtil.isInElements(kindId, valueList
				.toArray(new Integer[valueList.size()]));
	}

	// public void afterPropertiesSet() throws Exception {
	// Collection<CompanyKind> c = map.values();
	// list = new ArrayList<CompanyKind>();
	// list.addAll(c);
	// valueList = new ArrayList<Integer>();
	// for (CompanyKind o : c) {
	// valueList.add(o.getKindId());
	// }
	// for (ParentKind o : parentmap.values()) {
	// List<CompanyKind> clist = new ArrayList<CompanyKind>();
	// for (CompanyKind oo : list) {
	// if (oo.getParentId() == o.getKindId()) {
	// clist.add(oo);
	// }
	// }
	// o.setKindlist(clist);
	// parentlist.add(o);
	// }
	// }
	public static void init() {
		CompanyKindService companyKindService = (CompanyKindService) HkUtil
				.getBean("companyKindService");
		// 获取所有分类
		list = companyKindService.getCompanyKindList(0);
		// 放入到map
		for (CompanyKind o : list) {
			map.put(o.getKindId(), o);
		}
		// 组装分类id集合
		valueList = new ArrayList<Integer>();
		for (CompanyKind o : list) {
			valueList.add(o.getKindId());
		}
		// 组装大分类的分类集合
		for (ParentKind o : parentmap.values()) {
			List<CompanyKind> clist = new ArrayList<CompanyKind>();
			for (CompanyKind oo : list) {
				if (oo.getParentId() == o.getKindId()) {
					clist.add(oo);
				}
			}
			o.setKindlist(clist);
			parentlist.add(o);
		}
	}
}