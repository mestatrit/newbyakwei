package com.hk.svr.equipment;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hk.bean.UserEquEnjoy;
import com.hk.bean.UserEquipment;

/**
 * 对抗瞌睡卡
 * 
 * @author akwei
 */
public class FuncSos2 extends FuncSosEquipment {

	private final Log log = LogFactory.getLog(FuncSos2.class);

	@Override
	public boolean execute(UserEquEnjoy userEquEnjoy,
			Map<String, Object> ctxAttributeMap) {
		log.info("begin FuncSos2 ...");
		return super.execute(userEquEnjoy, ctxAttributeMap);
	}

	@Override
	void vsWin(UserEquEnjoy userEquEnjoy, Map<String, Object> ctxAttributeMap,
			UserEquipment userEquipment) {
		log.info("vsWin FuncSos2 ...");
	}
}