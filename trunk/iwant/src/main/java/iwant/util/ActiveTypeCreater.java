package iwant.util;

import iwant.bean.enumtype.ActiveType;

import java.util.HashMap;
import java.util.Map;

public class ActiveTypeCreater {

	private static final Map<Integer, ActiveType> map = new HashMap<Integer, ActiveType>(
			3);
	static {
		map.put(ActiveType.ALL.getValue(), ActiveType.ALL);
		map.put(ActiveType.NOTACTIVE.getValue(), ActiveType.NOTACTIVE);
		map.put(ActiveType.ACTIVE.getValue(), ActiveType.ACTIVE);
	}

	public static ActiveType getActiveType(int value) {
		return map.get(value);
	}
}