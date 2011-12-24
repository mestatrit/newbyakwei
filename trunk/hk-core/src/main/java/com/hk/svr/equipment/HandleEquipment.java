package com.hk.svr.equipment;

import com.hk.bean.Equipment;
import com.hk.bean.UserEquipment;

/**
 * 用户获得道具(装备)的逻辑
 * 
 * @author akwei
 */
public interface HandleEquipment {

	/**
	 * 根据机率来获得道具
	 * 
	 * @param userId
	 * @return 如果用户有幸获得道具，则返回对象，否则返回null
	 *         2010-4-13
	 */
	UserEquipment execute(long userId, Equipment equipment);
}