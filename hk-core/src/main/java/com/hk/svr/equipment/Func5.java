package com.hk.svr.equipment;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Company;
import com.hk.bean.UserEquEnjoy;
import com.hk.bean.UserEquipment;
import com.hk.svr.CompanyService;
import com.hk.svr.processor.CmpCheckInProcessor;

/**
 * 地主卡,直接获得下一个报到地方的地主
 * 
 * @author akwei
 */
public class Func5 extends FuncEquipment {

	@Autowired
	private CompanyService companyService;

	private final Log log = LogFactory.getLog(Func5.class);

	@Autowired
	private CmpCheckInProcessor cmpCheckInProcessor;

	@Override
	public boolean execute(UserEquEnjoy userEquEnjoy,
			UserEquipment userEquipment, Map<String, Object> ctxAttributeMap) {
		log.info("begin func5 ...地主卡  [ " + userEquipment.getUserId() + " ]");
		Company company = (Company) ctxAttributeMap.get("company");
		String ip = (String) ctxAttributeMap.get("ip");
		if (company.getMayorUserId() != userEquipment.getUserId()) {
			this.cmpCheckInProcessor.checkMayor(userEquipment.getUserId(),
					company, ip, true);
			company.setMayorUserId(userEquipment.getUserId());
			this.companyService.updateCompany(company);
			log.info("use func5 ...");
			// 动态
			this.createCmpFeed(userEquipment, ctxAttributeMap);
			return true;
		}
		return false;
	}
}