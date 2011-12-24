package com.hk.svr.equipment;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.UserEquEnjoy;
import com.hk.bean.UserEquPoint;
import com.hk.bean.UserEquipment;
import com.hk.svr.EquipmentService;
import com.hk.svr.UserService;

/**
 *复活卡：可以恢复掉最后一次被革命卡灭掉的点数
 * 
 * @author akwei
 */
public class Func8 extends FuncEquipment {

	@Autowired
	private EquipmentService equipmentService;

	@Autowired
	private UserService userService;

	private final Log log = LogFactory.getLog(Func8.class);

	@Override
	public boolean execute(UserEquEnjoy userEquEnjoy,
			UserEquipment userEquipment, Map<String, Object> ctxAttributeMap) {
		log.info("begin func8 ...复活卡 [ " + userEquEnjoy.getUserId() + " , "
				+ userEquEnjoy.getEnjoyUserId() + " ]");
		UserEquPoint userEquPoint = this.equipmentService
				.getUserEquPoint(userEquEnjoy.getEnjoyUserId());
		if (userEquPoint == null) {
			return false;
		}
		if (userEquPoint.getPoints() == 0) {
			return false;
		}
		this.userService.addPoints(userEquEnjoy.getEnjoyUserId(), userEquPoint
				.getPoints());
		this.equipmentService.deleteUserEquPoint(userEquPoint.getUserId());
		// 动态
		EquipmentMsg msg = new EquipmentMsg();
		msg.setUserId(userEquEnjoy.getUserId());
		Map<String, String> datamap = this.createSosFeed(userEquEnjoy,
				userEquipment, ctxAttributeMap);
		msg.setDatamap(datamap);
		ctxAttributeMap.put(EquipmentStatus.EQUIPMENTMSG_ATTR, msg);
		log.info("use func8 ...");
		return true;
	}
}