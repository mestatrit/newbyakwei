package com.hk.api.action.cmp;

import java.util.List;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.bean.Company;
import com.hk.bean.CompanyPhoto;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.CompanyService;
import com.hk.svr.pub.Err;

// @Component("/pubapi/cmpphoto")
public class PhotoAction extends BaseApiAction {

	@Autowired
	private CompanyPhotoService companyPhotoService;

	@Autowired
	private CompanyService companyService;

	/**
	 * 获取图片忽略头图所使用的图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String ignorehead(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		PageSupport pageSupport = PageSupport.getInstance(req.getPage(), 1);
		pageSupport.makePage(this.companyPhotoService
				.countPhotoByCompanyIdNoLogo(companyId, company.getHeadPath()));
		List<CompanyPhoto> list = this.companyPhotoService
				.getPhotoListByCompanyIdNoLogo(companyId,
						company.getHeadPath(), pageSupport.getBegin(), 1);
		int nextPage = pageSupport.getPage() + 1;
		int prePage = pageSupport.getPage() - 1;
		if (pageSupport.getPage() == 1) {
			prePage = -1;
		}
		if (pageSupport.getPage() == pageSupport.getPageCount()) {
			nextPage = -1;
		}
		VelocityContext context = new VelocityContext();
		context.put("list", list);
		context.put("nextPage", nextPage);
		context.put("prePage", prePage);
		this.write(resp, "vm/e/photo2.vm", context);
		return null;
	}

	/**
	 * 获得单张图片
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long photoId = req.getLong("photoId");
		CompanyPhoto o = this.companyPhotoService.getCompanyPhoto(photoId);
		if (o == null) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		Company company = this.companyService.getCompany(companyId);
		VelocityContext context = new VelocityContext();
		context.put("p", o);
		context.put("company", company);
		this.write(resp, "vm/e/cmpphoto.vm", context);
		return null;
	}

	/**
	 * 获得所有图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		int size = this.getSize(req);
		SimplePage page = req.getSimplePage(size);
		List<CompanyPhoto> photolist = this.companyPhotoService
				.getPhotoListByCompanyId(companyId, page.getBegin(), size);
		Company company = this.companyService.getCompany(companyId);
		VelocityContext context = new VelocityContext();
		context.put("photolist", photolist);
		context.put("company", company);
		this.write(resp, "vm/e/cmpphotolist.vm", context);
		return null;
	}
}