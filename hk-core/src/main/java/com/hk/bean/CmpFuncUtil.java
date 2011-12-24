package com.hk.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CmpFuncUtil {

	private static List<CmpFunc> cmpFuncList;

	private static final Map<Long, CmpFunc> map = new HashMap<Long, CmpFunc>();

	public void setCmpFuncList(List<CmpFunc> cmpFuncList) {
		CmpFuncUtil.cmpFuncList = cmpFuncList;
		for (CmpFunc o : cmpFuncList) {
			map.put(o.getOid(), o);
		}
	}

	public static List<CmpFunc> getCmpFuncList() {
		return cmpFuncList;
	}

	public static CmpFunc getCmpFun(long oid) {
		return map.get(oid);
	}
}