package com.hk.web.cmpunion.action.reqexe;

import com.hk.bean.CmpUnionReq;
import com.hk.frame.util.HkUtil;
import com.hk.svr.BoxService;
import com.hk.svr.CmpProductService;
import com.hk.svr.CompanyService;
import com.hk.svr.CouponService;

public class JoinInCmpUnionReqExecAble extends ReqExecAble {
	@Override
	public void execute(CmpUnionReq cmpUnionReq) {
		CompanyService companyService = (CompanyService) HkUtil
				.getBean("companyService");
		CouponService couponService = (CouponService) HkUtil
				.getBean("couponService");
		BoxService boxService = (BoxService) HkUtil.getBean("boxService");
		CmpProductService cmpProductService = (CmpProductService) HkUtil
				.getBean("cmpProductService");
		long uid = cmpUnionReq.getUid();
		long companyId = cmpUnionReq.getObjId();
		companyService.updateUid(companyId, uid);
		boxService.updateUid(companyId, uid);
		cmpProductService.updateUid(companyId, uid);
		couponService.updateUid(companyId, uid);
	}
}