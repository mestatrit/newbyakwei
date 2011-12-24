package com.hk.svr.equipment;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.UserEquEnjoy;
import com.hk.bean.UserEquipment;
import com.hk.svr.EquipmentService;

/**
 *反射卡：可以让任何释放在自己身上副作用卡反射到对方身上
 * 
 * @author akwei
 */
public class Func9 extends FuncEquipment {

	@Autowired
	private EquipmentService equipmentService;

	private final Log log = LogFactory.getLog(Func9.class);

	@Override
	public boolean execute(UserEquEnjoy userEquEnjoy,
			UserEquipment userEquipment, Map<String, Object> ctxAttributeMap) {
		log.info("begin func9 ... [ " + userEquEnjoy.getUserId() + " , "
				+ userEquEnjoy.getEnjoyUserId() + " ]");
		log.info("use func9 ...");
		UserEquEnjoy o = new UserEquEnjoy();
		o.setUserId(userEquEnjoy.getEnjoyUserId());
		o.setEnjoyUserId(userEquEnjoy.getUserId());
		o.setForceEid(userEquEnjoy.getUserEquipment().getEid());
		this.equipmentService.createUserEquEnjoy(o);
		// 动态
		EquipmentMsg msg = new EquipmentMsg();
		msg.setUserId(userEquEnjoy.getUserId());
		Map<String, String> datamap = this.createSosFeed(userEquEnjoy,
				userEquipment, ctxAttributeMap);
		msg.setDatamap(datamap);
		ctxAttributeMap.put(EquipmentStatus.EQUIPMENTMSG_ATTR, msg);
		return true;
	}
}