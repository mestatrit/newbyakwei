package com.hk.web.company.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Company;
import com.hk.bean.CompanyPhoto;
import com.hk.bean.User;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.CompanyService;
import com.hk.web.pub.action.BaseAction;

@Component("/e/photo")
public class PhotoAction extends BaseAction {
	@Autowired
	private CompanyPhotoService companyPhotoService;

	@Autowired
	private CompanyService companyService;

	private int size = 1;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		boolean canmgrphoto = false;
		CompanyVo vo = CompanyVo.createVo(company);
		SimplePage page = req.getSimplePage(size);
		PageSupport pageSupport = PageSupport.getInstance(req.getPage(), size);
		pageSupport.makePage(this.companyPhotoService
				.countPhotoByCompanyIdNoLogo(companyId, company.getHeadPath()));
		List<CompanyPhoto> list = this.companyPhotoService
				.getPhotoListByCompanyIdNoLogo(companyId,
						company.getHeadPath(), pageSupport.getBegin(), size);
		page.setListSize(list.size());
		int nextPage = pageSupport.getPage() + 1;
		int prePage = pageSupport.getPage() - 1;
		if (pageSupport.getPage() == 1) {
			prePage = -1;
		}
		if (pageSupport.getPage() == pageSupport.getPageCount()) {
			nextPage = -1;
		}
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			if (company.getUserId() > 0
					&& loginUser.getUserId() == company.getUserId()) {
				canmgrphoto = true;
			}
		}
		req.setAttribute("canmgrphoto", canmgrphoto);
		req.setAttribute("nextPage", nextPage);
		req.setAttribute("prePage", prePage);
		req.setAttribute("companyId", companyId);
		req.setAttribute("vo", vo);
		req.setAttribute("list", list);
		return "/WEB-INF/page/e/photo/list.jsp";
	}

	/**
	 * 查看大图
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String showbig(HkRequest req, HkResponse resp) {
		long photoId = req.getLong("photoId");
		req.reSetAttribute("companyId");
		CompanyPhoto o = this.companyPhotoService.getCompanyPhoto(photoId);
		String queryString = req.getQueryString();
		req.setAttribute("o", o);
		req.setAttribute("qs", queryString);
		int repage = req.getInt("repage");
		req.setAttribute("repage", repage);
		return "/WEB-INF/page/e/photo/bigphoto.jsp";
	}
}