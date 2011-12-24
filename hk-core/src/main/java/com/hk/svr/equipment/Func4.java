package com.hk.svr.equipment;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hk.bean.CheckInResult;
import com.hk.bean.UserEquEnjoy;
import com.hk.bean.UserEquipment;

/**
 * 双倍卡,可以让接下来的1次报到得到加倍的点数
 * 
 * @author akwei
 */
public class Func4 extends FuncEquipment {

	private final Log log = LogFactory.getLog(Func4.class);

	@Override
	public boolean execute(UserEquEnjoy userEquEnjoy,
			UserEquipment userEquipment, Map<String, Object> ctxAttributeMap) {
		log.info("begin func4 ... 双倍卡 [ " + userEquipment.getUserId() + " ]");
		CheckInResult checkInResult = (CheckInResult) ctxAttributeMap
				.get("checkInResult");
		if (checkInResult.isCheckInSuccess()) {
			ctxAttributeMap.put(EquipmentStatus.CAN_GET_DOUBLE_POINTS, true);
			log.info("use func4 ...");
			// 动态
			this.createCmpFeed(userEquipment, ctxAttributeMap);
			return true;
		}
		return false;
	}
}