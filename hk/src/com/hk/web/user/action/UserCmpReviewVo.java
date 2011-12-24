package com.hk.web.user.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hk.bean.Company;
import com.hk.bean.CompanyReview;
import com.hk.bean.UserCmpReview;
import com.hk.frame.util.HkUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.svr.CompanyService;
import com.hk.web.company.action.CompanyReviewVo;
import com.hk.web.util.HkWebUtil;

public class UserCmpReviewVo {
	private Company company;

	private CompanyReviewVo companyReviewVo;

	public void setCompany(Company company) {
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}

	public CompanyReviewVo getCompanyReviewVo() {
		return companyReviewVo;
	}

	public void setCompanyReviewVo(CompanyReviewVo companyReviewVo) {
		this.companyReviewVo = companyReviewVo;
	}

	public static List<UserCmpReviewVo> makeList(List<UserCmpReview> list,
			HkRequest req) {
		CompanyService companyService = (CompanyService) HkUtil
				.getBean("companyService");
		List<Long> reviewIdList = new ArrayList<Long>();
		List<Long> cmpIdList = new ArrayList<Long>();
		for (UserCmpReview r : list) {
			reviewIdList.add(r.getLabaId());
			cmpIdList.add(r.getCompanyId());
		}
		List<CompanyReview> reviewlist = companyService
				.getCompanyReviewListInId(reviewIdList);
		List<CompanyReviewVo> reviewvolist = CompanyReviewVo.createVoList(
				reviewlist, HkWebUtil.getLabaUrlInfoWeb(req.getContextPath()));
		Map<Long, CompanyReviewVo> reviewvomap = new HashMap<Long, CompanyReviewVo>();
		for (CompanyReviewVo o : reviewvolist) {
			reviewvomap.put(o.getCompanyReview().getLabaId(), o);
		}
		List<UserCmpReviewVo> volist = new ArrayList<UserCmpReviewVo>();
		Map<Long, Company> cmpmap = companyService.getCompanyMapInId(cmpIdList);
		for (UserCmpReview r : list) {
			UserCmpReviewVo vo = new UserCmpReviewVo();
			vo.setCompany(cmpmap.get(r.getCompanyId()));
			vo.setCompanyReviewVo(reviewvomap.get(r.getLabaId()));
			volist.add(vo);
		}
		return volist;
	}

	public static List<UserCmpReviewVo> makeList(List<CompanyReviewVo> list) {
		CompanyService companyService = (CompanyService) HkUtil
				.getBean("companyService");
		List<Long> cmpIdList = new ArrayList<Long>();
		for (CompanyReviewVo o : list) {
			cmpIdList.add(o.getCompanyReview().getCompanyId());
		}
		Map<Long, Company> cmpmap = companyService.getCompanyMapInId(cmpIdList);
		List<UserCmpReviewVo> volist = new ArrayList<UserCmpReviewVo>();
		for (CompanyReviewVo o : list) {
			UserCmpReviewVo vo = new UserCmpReviewVo();
			vo.setCompany(cmpmap.get(o.getCompanyReview().getCompanyId()));
			vo.setCompanyReviewVo(o);
			volist.add(vo);
		}
		return volist;
	}
}