package com.hk.web.company.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.hk.bean.Company;
import com.hk.bean.HkOrder;
import com.hk.frame.util.HkUtil;
import com.hk.svr.CompanyService;

public class HkObjOrderListVo {
	private CompanyVo companyVo;

	private HkOrder hkOrder;

	public CompanyVo getCompanyVo() {
		return companyVo;
	}

	public void setCompanyVo(CompanyVo companyVo) {
		this.companyVo = companyVo;
	}

	public HkOrder getHkOrder() {
		return hkOrder;
	}

	public void setHkOrder(HkOrder hkOrder) {
		this.hkOrder = hkOrder;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<HkObjOrderListVo> createVoList(List<?> list) {
		List<HkObjOrderListVo> volist = new ArrayList<HkObjOrderListVo>();
		List<Long> idList = new ArrayList<Long>();
		List<HkOrder> olist = (List<HkOrder>) list;
		for (HkOrder o : olist) {
			idList.add(o.getHkObjId());
		}
		CompanyService companyService = (CompanyService) HkUtil
				.getBean("companyService");
		Map<Long, Company> map = companyService.getCompanyMapInId(idList);
		for (HkOrder o : olist) {
			HkObjOrderListVo vo = new HkObjOrderListVo();
			CompanyVo companyVo = CompanyVo.createVo(map.get(o.getHkObjId()));
			vo.setCompanyVo(companyVo);
			vo.setHkOrder(o);
			volist.add(vo);
		}
		return volist;
	}
}