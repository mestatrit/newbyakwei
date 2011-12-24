package web.epp.mgr.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import web.pub.action.EppBaseAction;

import com.hk.bean.CompanyReview;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.web.company.action.CompanyReviewVo;

@Deprecated
// @Component("/epp/mgr/review")
public class Sys_DelMgrReviewAction extends EppBaseAction {

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		List<CompanyReview> list = this.companyService
				.getCompanyReviewListByCompanyIdNoUser(companyId, 0, page
						.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		List<CompanyReviewVo> volist = CompanyReviewVo.createVoListForAPI(list);
		req.setAttribute("volist", volist);
		return this.getMgrJspPath(req, "review/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long labaId = req.getLong("labaId");
		this.companyService.deleteCompanyReview(labaId);
		req.setSessionText("func.mgrsite.review.delete_ok");
		return "r:/epp/mgr/review_list.do?companyId="
				+ req.getLong("companyId");
	}
}