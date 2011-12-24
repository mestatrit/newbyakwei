package com.hk.svr.pub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hk.bean.Equipment;
import com.hk.svr.equipment.EquipmentPoint;
import com.hk.svr.equipment.FuncEquipment;
import com.hk.svr.equipment.FuncSosEquipment;
import com.hk.svr.equipment.HandleEquipment;

public class EquipmentConfig {

	private static LinkedHashMap<Long, Equipment> map;

	private static Map<Long, HandleEquipment> handleMap;

	private static Map<Long, FuncEquipment> funcMap;

	private static Map<Long, FuncSosEquipment> sosFuncMap;

	private static Map<Long, EquipmentPoint> equipmentPointMap;

	public void setEquipmentPointMap(Map<Long, EquipmentPoint> equipmentPointMap) {
		EquipmentConfig.equipmentPointMap = equipmentPointMap;
	}

	public void setSosFuncMap(Map<Long, FuncSosEquipment> sosFuncMap) {
		EquipmentConfig.sosFuncMap = sosFuncMap;
	}

	public static int getPoints(long eid, Map<String, Object> ctxMap) {
		EquipmentPoint equipmentPoint = equipmentPointMap.get(eid);
		if (equipmentPoint == null) {
			return 0;
		}
		return equipmentPoint.getPoints(ctxMap);
	}

	public static FuncSosEquipment getFuncSosEquipment(long eid) {
		return sosFuncMap.get(eid);
	}

	public static List<Equipment> getEquipments() {
		Collection<Equipment> c = map.values();
		List<Equipment> list = new ArrayList<Equipment>();
		for (Equipment equipment : c) {
			Equipment o = new Equipment();
			o.copy(equipment);
			list.add(o);
		}
		return list;
	}

	public void setFuncMap(Map<Long, FuncEquipment> funcMap) {
		EquipmentConfig.funcMap = funcMap;
	}

	public void setHandleMap(Map<Long, HandleEquipment> handleMap) {
		EquipmentConfig.handleMap = handleMap;
	}

	public void setMap(LinkedHashMap<Long, Equipment> map) {
		EquipmentConfig.map = map;
	}

	public static Equipment getEquipment(long eid) {
		Equipment equipment = map.get(eid);
		if (equipment != null) {
			Equipment o = new Equipment();
			o.copy(equipment);
			return o;
		}
		return null;
	}

	public static HandleEquipment getHandleEquipment(long eid) {
		if (handleMap == null) {
			return null;
		}
		return handleMap.get(eid);
	}

	public static FuncEquipment getFuncEquipment(long eid) {
		return funcMap.get(eid);
	}
}