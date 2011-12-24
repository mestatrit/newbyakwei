package com.hk.svr.equipment;

import java.util.Map;

import com.hk.svr.pub.CheckInPointConfig;

public class Point4 extends EquipmentPoint {

	@Override
	public int getPoints(Map<String, Object> ctxMap) {
		Integer todayEffectCheckInCount = (Integer) ctxMap
				.get("todayEffectCheckInCount");
		Integer totalEffectCheckInCount = (Integer) ctxMap
				.get("totalEffectCheckInCount");
		if (todayEffectCheckInCount == null || totalEffectCheckInCount == null) {
			return 0;
		}
		return CheckInPointConfig.getPoints(totalEffectCheckInCount,
				todayEffectCheckInCount) * 2;
	}
}