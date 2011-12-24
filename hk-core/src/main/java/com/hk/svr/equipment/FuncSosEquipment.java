package com.hk.svr.equipment;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CheckInResult;
import com.hk.bean.Equipment;
import com.hk.bean.UserEquEnjoy;
import com.hk.bean.UserEquipment;
import com.hk.svr.EquipmentService;
import com.hk.svr.pub.EquipmentConfig;

public abstract class FuncSosEquipment {

	@Autowired
	private EquipmentService equipmentService;

	/**
	 * 对抗的处理
	 * 
	 * @param ctxAttributeMap
	 *            2010-4-13
	 */
	public boolean execute(UserEquEnjoy userEquEnjoy,
			Map<String, Object> ctxAttributeMap) {
		return this.vs(userEquEnjoy, ctxAttributeMap);
	}

	abstract void vsWin(UserEquEnjoy userEquEnjoy,
			Map<String, Object> ctxAttributeMap, UserEquipment userEquipment);

	protected boolean vs(UserEquEnjoy userEquEnjoy,
			Map<String, Object> ctxAttributeMap) {
		CheckInResult checkInResult = (CheckInResult) ctxAttributeMap
				.get("checkInResult");
		if (!checkInResult.isCheckInSuccess()) {
			return false;
		}
		long enjoyUserId = userEquEnjoy.getEnjoyUserId();
		// 查看目标人物是否有防御属性卡
		UserEquipment ouUserEquipment = userEquEnjoy.getUserEquipment();
		Equipment equipment = ouUserEquipment.getEquipment();
		if (equipment.getOpponentList() != null) {
			for (Long l : equipment.getOpponentList()) {
				UserEquipment o = this.equipmentService
						.getNotUseUserEquipmentByUserIdAndEid(enjoyUserId, l);
				if (o != null) {// 如果用户存在对抗道具,执行对抗道具功能
					o.setEquipment(EquipmentConfig.getEquipment(o.getEid()));
					FuncEquipment funcEquipment = EquipmentConfig
							.getFuncEquipment(o.getEid());
					if (funcEquipment.execute(userEquEnjoy, o, ctxAttributeMap)) {
						this.equipmentService.destoryUserEquEnjoy(userEquEnjoy,
								o);
						this.vsWin(userEquEnjoy, ctxAttributeMap, o);
						return true;
					}
				}
			}
		}
		// 由于没有对抗成功，则执行道具功能
		FuncEquipment funcEquipment = EquipmentConfig
				.getFuncEquipment(ouUserEquipment.getEid());
		if (funcEquipment != null) {
			if (funcEquipment.execute(userEquEnjoy, null, ctxAttributeMap)) {
				this.equipmentService.destoryUserEquEnjoy(userEquEnjoy, null);
				return true;
			}
		}
		return false;
	}
}