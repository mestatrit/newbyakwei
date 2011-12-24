package com.hk.web.company.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.CompanyReview;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.web.pub.action.BaseAction;

/**
 * 足迹的点评
 * 
 * @author akwei
 */
@Component("/cmpreview")
public class CmpReviewAction extends BaseAction {
	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		int size = 20;
		SimplePage page = req.getSimplePage(size);
		List<CompanyReview> reviewlist = this.companyService
				.getCompanyReviewListByCompanyId(companyId, page.getBegin(),
						page.getSize() + 1);
		this.processListForPage(page, reviewlist);
		req.setAttribute("companyreviewvolist", CompanyReviewVo.createVoList(
				reviewlist, this.getUrlInfo(req)));
		return this.getWeb3Jsp("e/review/review.jsp");
	}
}