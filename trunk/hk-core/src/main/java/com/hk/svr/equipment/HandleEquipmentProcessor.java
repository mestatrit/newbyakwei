package com.hk.svr.equipment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Equipment;
import com.hk.bean.UserCmpEnjoy;
import com.hk.bean.UserEquEnjoy;
import com.hk.bean.UserEquipment;
import com.hk.frame.util.DataUtil;
import com.hk.svr.EquipmentService;
import com.hk.svr.pub.EquipmentConfig;

/**
 * 用户获得道具、使用道具的逻辑
 * 
 * @author akwei
 */
public class HandleEquipmentProcessor {

	@Autowired
	private EquipmentService equipmentService;

	private boolean processUserEquEnjoy(UserEquEnjoy userEquEnjoy,
			Map<String, Object> ctxAttributeMap) {
		if (userEquEnjoy == null) {
			return false;
		}
		if (userEquEnjoy.getForceEid() > 0) {// 反射卡使用后，强制道具
			FuncEquipment funcEquipment = EquipmentConfig
					.getFuncEquipment(userEquEnjoy.getForceEid());
			if (funcEquipment.execute(userEquEnjoy, null, ctxAttributeMap)) {
				this.equipmentService.destoryUserEquEnjoy(userEquEnjoy, null);
				return true;
			}
		}
		else {
			UserEquipment userEquipment = this.equipmentService
					.getUserEquipment(userEquEnjoy.getUeid());
			userEquipment.setEquipment(EquipmentConfig
					.getEquipment(userEquipment.getEid()));
			userEquEnjoy.setUserEquipment(userEquipment);
			FuncSosEquipment funcSosEquipment = EquipmentConfig
					.getFuncSosEquipment(userEquipment.getEid());
			if (funcSosEquipment != null) {
				return funcSosEquipment.execute(userEquEnjoy, ctxAttributeMap);
			}
		}
		return false;
	}

	public boolean isHasUserCmpEnjoy(long userId, long companyId) {
		List<UserCmpEnjoy> clist = this.equipmentService
				.getUserCmpEnjoyListByUserIdAndCompanyId(userId, companyId, 0,
						1);
		if (clist.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean isUserEquEnjoyFirst(long userId, long companyId) {
		List<UserEquEnjoy> ulist = this.equipmentService
				.getUserEquEnjoyListByEnjoyUserId(userId, 0, 1);
		List<UserCmpEnjoy> clist = this.equipmentService
				.getUserCmpEnjoyListByUserIdAndCompanyId(userId, companyId, 0,
						1);
		UserEquEnjoy userEquEnjoy = null;
		if (ulist.size() > 0) {
			userEquEnjoy = ulist.get(0);
		}
		UserCmpEnjoy userCmpEnjoy = null;
		if (clist.size() > 0) {
			userCmpEnjoy = clist.get(0);
		}
		boolean userbegin = true;
		if (userEquEnjoy == null) {
			userbegin = false;
		}
		else if (userCmpEnjoy == null) {
			userbegin = true;
		}
		else if (userEquEnjoy.getCreateTime().getTime() > userCmpEnjoy
				.getCreateTime().getTime()) {
			userbegin = false;
		}
		return userbegin;
	}

	public void processEquipment(long userId, long companyId,
			Map<String, Object> ctxAttributeMap) {
		ctxAttributeMap.put("companyId", companyId);
		boolean userbegin = true;
		List<UserEquEnjoy> ulist = this.equipmentService
				.getUserEquEnjoyListByEnjoyUserId(userId, 0, 1);
		List<UserCmpEnjoy> clist = this.equipmentService
				.getUserCmpEnjoyListByUserIdAndCompanyId(userId, companyId, 0,
						1);
		UserEquEnjoy userEquEnjoy = null;
		if (ulist.size() > 0) {
			userEquEnjoy = ulist.get(0);
		}
		UserCmpEnjoy userCmpEnjoy = null;
		if (clist.size() > 0) {
			userCmpEnjoy = clist.get(0);
		}
		if (userEquEnjoy == null) {
			userbegin = false;
		}
		else if (userCmpEnjoy == null) {
			userbegin = true;
		}
		else if (userEquEnjoy.getCreateTime().getTime() > userCmpEnjoy
				.getCreateTime().getTime()) {
			userbegin = false;
		}
		if (userbegin) {
			if (!this.processUserEquEnjoy(userEquEnjoy, ctxAttributeMap)) {
				this.processUserCmpEnjoy(userCmpEnjoy, ctxAttributeMap);
			}
		}
		else {
			if (!this.processUserCmpEnjoy(userCmpEnjoy, ctxAttributeMap)) {
				this.processUserEquEnjoy(userEquEnjoy, ctxAttributeMap);
			}
		}
	}

	private boolean processUserCmpEnjoy(UserCmpEnjoy userCmpEnjoy,
			Map<String, Object> ctxAttributeMap) {
		if (userCmpEnjoy == null) {
			return false;
		}
		UserEquipment userEquipment = this.equipmentService
				.getUserEquipment(userCmpEnjoy.getUeid());
		userEquipment.setEquipment(EquipmentConfig.getEquipment(userEquipment
				.getEid()));
		userCmpEnjoy.setUserEquipment(userEquipment);
		FuncEquipment funcEquipment = EquipmentConfig
				.getFuncEquipment(userEquipment.getEid());
		if (funcEquipment.execute(null, userEquipment, ctxAttributeMap)) {
			this.equipmentService.destoryUserCmpEnjoy(userCmpEnjoy,
					userEquipment);
			return true;
		}
		return false;
	}

	public UserEquipment processGet(long userId) {
		return this.processGet(userId, EquipmentConfig.getEquipments());
	}

	/**
	 * @param userId
	 * @param c
	 * @return 如果获得道具，返回对象，否则返回null
	 *         2010-4-13
	 */
	public UserEquipment processGet(long userId, List<Equipment> c) {
		int idx = DataUtil.getRandomNumber(c.size());
		return this.processGet(userId, c.get(idx));
	}

	/**
	 * 用户获得道具
	 * 
	 * @param userId
	 * @param equipment
	 * @return 如果获得道具，返回对象，否则返回null
	 *         2010-4-13
	 */
	public UserEquipment processGet(long userId, Equipment equipment) {
		HandleEquipment handleEquipment = EquipmentConfig
				.getHandleEquipment(equipment.getEid());
		if (handleEquipment == null) {
			return createUserEquipmentByDef(userId, equipment);
		}
		return handleEquipment.execute(userId, equipment);
	}

	private UserEquipment createUserEquipmentByDef(long userId,
			Equipment equipment) {
		if (equipment.getRate() == 0) {// 机率为0
			return null;
		}
		BigDecimal decimal = new BigDecimal(1).divide(new BigDecimal(equipment
				.getRate()), 0, BigDecimal.ROUND_UP);
		int range = decimal.intValue();
		int goal = DataUtil.getRandomNumber(range);
		int chance = DataUtil.getRandomNumber(range);
		if (chance == goal) {// 获得道具
			UserEquipment userEquipment = this.equipmentService
					.getNotUseUserEquipmentByUserIdAndEid(userId, equipment
							.getEid());
			if (userEquipment == null) {
				userEquipment = new UserEquipment();
				userEquipment.setEid(equipment.getEid());
				userEquipment.setUserId(userId);
				userEquipment.setUseflg(UserEquipment.USEFLG_N);
				userEquipment.setTouchflg(UserEquipment.TOUCHFLG_N);
				this.equipmentService.createUserEquipment(userEquipment);
				return userEquipment;
			}
			return null;
		}
		return null;
	}
}