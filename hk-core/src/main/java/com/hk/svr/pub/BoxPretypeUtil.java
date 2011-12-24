package com.hk.svr.pub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.springframework.beans.factory.InitializingBean;
import com.hk.bean.BoxPretype;

public class BoxPretypeUtil implements InitializingBean {
	private LinkedHashMap<Integer, String> map;

	private static List<BoxPretype> list;

	private static Map<Integer, BoxPretype> preTypeMap;

	private BoxPretypeUtil() {//
	}

	public void setMap(LinkedHashMap<Integer, String> map) {
		this.map = map;
	}

	public void afterPropertiesSet() throws Exception {
		list = new ArrayList<BoxPretype>(map.size());
		preTypeMap = new HashMap<Integer, BoxPretype>(map.size());
		Set<Entry<Integer, String>> set = map.entrySet();
		for (Entry<Integer, String> e : set) {
			BoxPretype o = new BoxPretype();
			o.setTypeId(e.getKey());
			String[] a = e.getValue().split(":");
			o.setName(a[0]);
			o.setTime(Long.parseLong(a[1]));
			list.add(o);
			preTypeMap.put(e.getKey(), o);
		}
	}

	public static List<BoxPretype> getList() {
		return list;
	}

	public static BoxPretype getBoxPretype(int typeId) {
		return preTypeMap.get(typeId);
	}

	public static long getBoxPretypeTime(int typeId) {
		BoxPretype o = getBoxPretype(typeId);
		if (o == null) {
			return 0;
		}
		return o.getTime();
	}
}