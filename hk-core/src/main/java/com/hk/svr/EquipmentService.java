package com.hk.svr;

import java.util.List;

import com.hk.bean.UserCmpEnjoy;
import com.hk.bean.UserEquEnjoy;
import com.hk.bean.UserEquPoint;
import com.hk.bean.UserEquipment;

/**
 * 装备相关逻辑操作
 * 
 * @author akwei
 */
public interface EquipmentService {

	/**
	 * 使用道具
	 * 
	 * @param enjoyUserId
	 * @param userEquipment
	 * @return true:成功 false:失败，没有剩余道具|道具不是可主动使用
	 *         2010-4-13
	 */
	boolean useEquipmentToUser(long enjoyUserId, UserEquipment userEquipment);

	/**
	 * 对足迹使用道具，当报到时触发
	 * 
	 * @param companyId
	 * @param userEquipment
	 * @return
	 *         2010-4-14
	 */
	boolean useEquipmentToCmp(long companyId, UserEquipment userEquipment);

	void createUserEquipment(UserEquipment userEquipment);

	/**
	 * 解除副作用道具
	 * 
	 * @param userEquEnjoy
	 * @param userEquipment
	 *            2010-4-13
	 */
	void destoryUserEquEnjoy(UserEquEnjoy userEquEnjoy,
			UserEquipment userEquipment);

	void destoryUserCmpEnjoy(UserCmpEnjoy userCmpEnjoy,
			UserEquipment userEquipment);

	/**
	 * 如果对象不存在，则创建对象，如果存在，则保存对象
	 * 
	 * @param userEquEnjoy
	 *            2010-4-13
	 */
	void createUserEquEnjoy(UserEquEnjoy userEquEnjoy);

	/**
	 * @param userId
	 * @param begin
	 * @param size 为0时，获取所有
	 * @return
	 *         2010-4-14
	 */
	List<UserEquipment> getUserEquipmentListByUserId(long userId, byte useflg,
			int begin, int size);

	List<UserEquEnjoy> getUserEquEnjoyListByUserId(long userId);

	List<UserEquEnjoy> getUserEquEnjoyListByEnjoyUserId(long enjoyUserId,
			int begin, int size);

	List<UserCmpEnjoy> getUserCmpEnjoyListByUserIdAndCompanyId(long userId,
			long companyId, int begin, int size);

	/**
	 * 获得未使用的道具
	 * 
	 * @param userId
	 * @param eid
	 * @return
	 *         2010-4-14
	 */
	UserEquipment getNotUseUserEquipmentByUserIdAndEid(long userId, long eid);

	UserEquipment getUserEquipment(long oid);

	/**
	 * 保存用户被剥削卡剥削的点数
	 * 
	 * @param userEquPoint
	 *            2010-4-14
	 */
	void saveUserEquPoint(UserEquPoint userEquPoint);

	UserEquPoint getUserEquPoint(long userId);

	void deleteUserEquPoint(long userId);
}