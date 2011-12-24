package com.hk.svr.equipment;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hk.bean.CheckInResult;
import com.hk.bean.UserEquEnjoy;
import com.hk.bean.UserEquipment;

/**
 *自保卡：拥有自保卡的用户，在被别人使用副作用卡的时候，自动无效
 * 
 * @author akwei
 */
public class Func7 extends FuncEquipment {

	private final Log log = LogFactory.getLog(Func7.class);

	@Override
	public boolean execute(UserEquEnjoy userEquEnjoy,
			UserEquipment userEquipment, Map<String, Object> ctxAttributeMap) {
		log.info("begin func7 ... 自保卡 [ " + userEquEnjoy.getUserId() + " , "
				+ userEquEnjoy.getEnjoyUserId() + " ]");
		CheckInResult checkInResult = (CheckInResult) ctxAttributeMap
				.get("checkInResult");
		if (checkInResult.isCheckInSuccess()) {
			log.info("use func7 ...");
			// 动态
			EquipmentMsg msg = new EquipmentMsg();
			msg.setUserId(userEquEnjoy.getUserId());
			Map<String, String> datamap = this.createSosFeed(userEquEnjoy,
					userEquipment, ctxAttributeMap);
			msg.setDatamap(datamap);
			ctxAttributeMap.put(EquipmentStatus.EQUIPMENTMSG_ATTR, msg);
			return true;
		}
		return false;
	}
}