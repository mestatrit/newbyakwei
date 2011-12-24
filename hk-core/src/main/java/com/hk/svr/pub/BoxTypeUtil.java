package com.hk.svr.pub;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import org.springframework.beans.factory.InitializingBean;
import com.hk.bean.BoxType;

public class BoxTypeUtil implements InitializingBean {
	private LinkedHashMap<Integer, String> typeStringMap;

	private static LinkedHashMap<Integer, BoxType> typeMap;

	private static List<BoxType> typeList;

	public void setTypeStringMap(LinkedHashMap<Integer, String> typeStringMap) {
		this.typeStringMap = typeStringMap;
	}

	private BoxTypeUtil() {//
	}

	public static List<BoxType> getTypeList() {
		return typeList;
	}

	public static BoxType getBoxType(int typeId) {
		return typeMap.get(typeId);
	}

	public void afterPropertiesSet() throws Exception {
		typeList = new ArrayList<BoxType>();
		typeMap = new LinkedHashMap<Integer, BoxType>();
		Set<Entry<Integer, String>> set = typeStringMap.entrySet();
		for (Entry<Integer, String> e : set) {
			BoxType o = new BoxType();
			o.setName(e.getValue());
			o.setTypeId(e.getKey());
			typeList.add(o);
			typeMap.put(e.getKey(), o);
		}
	}
}