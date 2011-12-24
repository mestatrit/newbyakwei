package com.hk.svr.equipment;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CheckInResult;
import com.hk.bean.UserEquEnjoy;
import com.hk.bean.UserEquipment;
import com.hk.svr.UserService;
import com.hk.svr.pub.CheckInPointConfig;

/**
 * 报到卡,让你在一个地方能够多报到1次，本报到可以获得点数，但是仍然为无效报到
 * 
 * @author akwei
 */
public class Func1 extends FuncEquipment {

	@Autowired
	private UserService userService;

	private final Log log = LogFactory.getLog(Func1.class);

	@Override
	public boolean execute(UserEquEnjoy userEquEnjoy,
			UserEquipment userEquipment, Map<String, Object> ctxAttributeMap) {
		log.info("begin func1 ... 报到卡 [ " + userEquipment.getUserId() + " ]");
		CheckInResult checkInResult = (CheckInResult) ctxAttributeMap
				.get("checkInResult");
		if (!checkInResult.isCheckInSuccess()
				&& !checkInResult.isCheckInTimeTooShort()) {
			long userId = userEquipment.getUserId();
			this.userService.addPoints(userId, CheckInPointConfig
					.getOtherCheckIn());
			log.info("use func1 ...");
			// 动态
			this.createCmpFeed(userEquipment, ctxAttributeMap);
			return true;
		}
		return false;
	}
}