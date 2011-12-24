package com.hk.svr.pub;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import org.springframework.beans.factory.InitializingBean;
import com.hk.bean.BoxOpenType;

public class BoxOpenTypeUtil implements InitializingBean {
	private LinkedHashMap<Integer, String> map;

	private static LinkedHashMap<Integer, BoxOpenType> typeMap;

	private static List<BoxOpenType> typeList;

	public void setMap(LinkedHashMap<Integer, String> map) {
		this.map = map;
	}

	private BoxOpenTypeUtil() {//
	}

	public static List<BoxOpenType> getTypeList() {
		return typeList;
	}

	public static BoxOpenType getBoxOpenType(int typeId) {
		return typeMap.get(typeId);
	}

	public void afterPropertiesSet() throws Exception {
		typeList = new ArrayList<BoxOpenType>();
		typeMap = new LinkedHashMap<Integer, BoxOpenType>();
		Set<Entry<Integer, String>> set = map.entrySet();
		for (Entry<Integer, String> e : set) {
			BoxOpenType o = new BoxOpenType();
			o.setName(e.getValue());
			o.setTypeId(e.getKey());
			typeList.add(o);
			typeMap.put(e.getKey(), o);
		}
	}
}