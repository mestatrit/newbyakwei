package com.hk.api.action.cmp;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.bean.CompanyReview;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;

// @Component("/pubapi/protect/cmp/mgrreview/mgr")
public class MgrReviewAction extends BaseApiAction {

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return null;
	}

	public String delete(HkRequest req, HkResponse resp) throws Exception {
		long labaId = req.getLong("labaId");
		this.companyService.updateCompanyReviewCheckflg(labaId,
				CompanyReview.CHECKFLG_DISSENTIOUS);
		APIUtil.sendSuccessRespStatus(resp);
		return null;
	}
}