package com.hk.web.company.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.hk.bean.Company;
import com.hk.bean.HkObjKindOrder;
import com.hk.frame.util.HkUtil;
import com.hk.svr.CompanyService;

public class HkObjKindOrderVo {
	private CompanyVo companyVo;

	private HkObjKindOrder hkObjKindOrder;

	public CompanyVo getCompanyVo() {
		return companyVo;
	}

	public void setCompanyVo(CompanyVo companyVo) {
		this.companyVo = companyVo;
	}

	public HkObjKindOrder getHkObjKindOrder() {
		return hkObjKindOrder;
	}

	public void setHkObjKindOrder(HkObjKindOrder hkObjKindOrder) {
		this.hkObjKindOrder = hkObjKindOrder;
	}

	public static List<HkObjKindOrderVo> createVoList(List<HkObjKindOrder> list) {
		List<HkObjKindOrderVo> volist = new ArrayList<HkObjKindOrderVo>();
		List<Long> idList = new ArrayList<Long>();
		for (HkObjKindOrder o : list) {
			idList.add(o.getHkObjId());
		}
		CompanyService companyService = (CompanyService) HkUtil
				.getBean("companyService");
		Map<Long, Company> map = companyService.getCompanyMapInId(idList);
		for (HkObjKindOrder o : list) {
			HkObjKindOrderVo vo = new HkObjKindOrderVo();
			CompanyVo companyVo = CompanyVo.createVo(map.get(o.getHkObjId()));
			vo.setCompanyVo(companyVo);
			vo.setHkObjKindOrder(o);
			volist.add(vo);
		}
		return volist;
	}
}