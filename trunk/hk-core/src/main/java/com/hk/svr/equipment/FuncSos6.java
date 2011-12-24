package com.hk.svr.equipment;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Company;
import com.hk.bean.UserEquEnjoy;
import com.hk.bean.UserEquipment;
import com.hk.svr.CompanyService;

/**
 * 对抗剥削卡,是当前足迹地主的时候激活此功能
 * 
 * @author akwei
 */
public class FuncSos6 extends FuncSosEquipment {

	@Autowired
	private CompanyService companyService;

	private final Log log = LogFactory.getLog(FuncSos6.class);

	@Override
	public boolean execute(UserEquEnjoy userEquEnjoy,
			Map<String, Object> ctxAttributeMap) {
		log.info("begin FuncSos6 ...");
		long companyId = (Long) ctxAttributeMap.get("companyId");
		Company company = companyService.getCompany(companyId);
		if (company == null) {
			return false;
		}
		if (company.getMayorUserId() == userEquEnjoy.getEnjoyUserId()) {// 是地主才启动
			return super.execute(userEquEnjoy, ctxAttributeMap);
		}
		return false;
	}

	@Override
	void vsWin(UserEquEnjoy userEquEnjoy, Map<String, Object> ctxAttributeMap,
			UserEquipment userEquipment) {
		log.info("vsWin FuncSos6 ...");
	}
}