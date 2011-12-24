package com.hk.api.action.cmp;

import java.util.List;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.bean.CompanyReview;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.pub.Err;
import com.hk.web.company.action.CompanyReviewVo;

// @Component("/pubapi/review")
public class ReviewAction extends BaseApiAction {

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long labaId = req.getLong("labaId");
		CompanyReview companyReview = this.companyService
				.getCompanyReview(labaId);
		if (companyReview == null) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		CompanyReviewVo vo = CompanyReviewVo
				.createVo(companyReview, null, true);
		VelocityContext context = new VelocityContext();
		context.put("vo", vo);
		this.write(resp, "vm/e/review.vm", context);
		return null;
	}

	public String userlastreview(HkRequest req, HkResponse resp)
			throws Exception {
		long userId = req.getLong("userId");
		long companyId = req.getLong("companyId");
		List<CompanyReview> myreviewlist = this.companyService
				.getUserCompanyReviewList(companyId, userId, 0, 1);
		if (myreviewlist.size() == 1) {
			VelocityContext context = new VelocityContext();
			CompanyReview companyReview = myreviewlist.iterator().next();
			CompanyReviewVo vo = CompanyReviewVo.createVo(companyReview, null,
					true);
			context.put("vo", vo);
			this.write(resp, "vm/e/review.vm", context);
		}
		else {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
		}
		return null;
	}

	public String list(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		int size = this.getSize(req);
		SimplePage page = req.getSimplePage(size);
		List<CompanyReview> list = this.companyService
				.getCompanyReviewListByCompanyId(companyId,
						CompanyReview.CHECKFLG_NORMAL, page.getBegin(), size);
		List<CompanyReviewVo> volist = CompanyReviewVo.createVoListForAPI(list);
		VelocityContext context = new VelocityContext();
		context.put("volist", volist);
		this.write(resp, "vm/e/reviewlist.vm", context);
		return null;
	}
}