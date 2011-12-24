package com.hk.svr.equipment;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hk.bean.UserEquEnjoy;
import com.hk.bean.UserEquipment;

/**
 * 风油精卡：可以让自己身上的瞌睡卡失效
 * 
 * @author akwei
 */
public class Func3 extends FuncEquipment {

	private final Log log = LogFactory.getLog(Func3.class);

	@Override
	public boolean execute(UserEquEnjoy userEquEnjoy,
			UserEquipment userEquipment, Map<String, Object> ctxAttributeMap) {
		log.info("begin func3 ... 风油精卡 [ " + userEquipment.getUserId() + " ]");
		log.info("use func3 ...");
		EquipmentMsg msg = new EquipmentMsg();
		msg.setUserId(userEquEnjoy.getUserId());
		Map<String, String> datamap = this.createSosFeed(userEquEnjoy,
				userEquipment, ctxAttributeMap);
		msg.setDatamap(datamap);
		ctxAttributeMap.put(EquipmentStatus.EQUIPMENTMSG_ATTR, msg);
		return true;
	}
}