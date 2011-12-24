package com.hk.web.company.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.Company;
import com.hk.bean.CompanyReview;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.web.pub.action.BaseAction;

/**
 * 认领之后才可以使用的功能
 * 
 * @author yuanwei
 */
@Component("/e/op/auth/op")
public class AuthedOpAction extends BaseAction {
	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 修改点评状态
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String chgcheckflg(HkRequest req, HkResponse resp) throws Exception {
		byte flg = req.getByte("flg");
		long labaId = req.getLong("labaId");
		long companyId = req.getLong("companyId");
		this.companyService.updateCompanyReviewCheckflg(labaId, flg);
		req.setSessionText("op.exeok");
		return "r:/e/op/auth/op_review.do?companyId=" + companyId
				+ "&checkflg=" + req.getByte("checkflg");
	}

	/**
	 * 管理足迹评论
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String review(HkRequest req, HkResponse resp) throws Exception {
		byte checkflg = req.getByte("checkflg");
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		if (o == null) {
			return null;
		}
		SimplePage page = req.getSimplePage(size20);
		List<CompanyReview> list = this.companyService
				.getCompanyReviewListByCompanyId(companyId, checkflg, page
						.getBegin(), size20);
		page.setListSize(list.size());
		List<CompanyReviewVo> volist = CompanyReviewVo.createVoList(list, this
				.getUrlInfo(req));
		req.setAttribute("reviewvolist", volist);
		req.setAttribute("checkflg", checkflg);
		req.setAttribute("o", o);
		req.setAttribute("companyId", companyId);
		return "/WEB-INF/page/e/op/review.jsp";
	}
}