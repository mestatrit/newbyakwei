package svrtest;

import java.util.Date;

import com.hk.bean.CmpJoinInApply;
import com.hk.svr.CmpJoinInApplyService;

public class CmpJoinInApplyServiceTest extends HkServiceTest {

	private CmpJoinInApplyService cmpJoinInApplyService;

	public void setCmpJoinInApplyService(
			CmpJoinInApplyService cmpJoinInApplyService) {
		this.cmpJoinInApplyService = cmpJoinInApplyService;
	}

	public void testCreateCmpJoinInApply() {
		long companyId = 1;
		String name = "akwie";
		String cmpname = "水分的空间上领导叫浪费老师的";
		String tel = null;
		String mobile = null;
		String content = "一位接近上海市政府的人士近日透露，为尽快实现对住房保有环节征税，上海最终选定的方案是应用现有的房产税概念，以减少立法审批流程。根据现有的房产税暂行条例，房产税征收对象是经营性物业，而此次上海方案将把持有多套住宅解释为经营行为";
		for (int i = 0; i < 100; i++) {
			CmpJoinInApply cmpJoinInApply = new CmpJoinInApply();
			cmpJoinInApply.setCompanyId(companyId);
			cmpJoinInApply.setCmpname(cmpname);
			cmpJoinInApply.setTel(tel);
			cmpJoinInApply.setMobile(mobile);
			cmpJoinInApply.setContent(content);
			cmpJoinInApply.setCreateTime(new Date());
			cmpJoinInApply.setName(name);
			this.cmpJoinInApplyService.createCmpJoinInApply(cmpJoinInApply);
		}
		this.commit();
	}
}