package com.hk.svr.equipment;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hk.bean.CheckInResult;
import com.hk.bean.UserEquEnjoy;
import com.hk.bean.UserEquipment;

/**
 * 瞌睡卡的使用
 * 
 * @author akwei
 */
public class Func2 extends FuncEquipment {

	private final Log log = LogFactory.getLog(Func2.class);

	@Override
	public boolean execute(UserEquEnjoy userEquEnjoy,
			UserEquipment userEquipment, Map<String, Object> ctxAttributeMap) {
		log.info("begin func2 ...瞌睡卡 [ " + userEquEnjoy.getUserId() + " , "
				+ userEquEnjoy.getEnjoyUserId() + " ]");
		CheckInResult checkInResult = (CheckInResult) ctxAttributeMap
				.get("checkInResult");
		if (checkInResult.isCheckInSuccess()) {
			ctxAttributeMap.put(EquipmentStatus.CAN_NOT_GET_POINTS, true);
			log.info("use func2 ...");
			if (userEquEnjoy.getForceEid() <= 0) {
				EquipmentMsg msg = new EquipmentMsg();
				msg.setUserId(userEquEnjoy.getUserId());
				Map<String, String> datamap = this.createVsFeed(userEquEnjoy,
						ctxAttributeMap);
				msg.setDatamap(datamap);
				ctxAttributeMap.put(EquipmentStatus.EQUIPMENTMSG_ATTR, msg);
			}
			return true;
		}
		return false;
	}
}