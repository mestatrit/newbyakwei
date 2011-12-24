package web.epp.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.Company;
import com.hk.bean.CompanyPhoto;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyPhotoService;

@Component("/epp/photo")
public class PhotoAction extends EppBaseAction {

	@Autowired
	private CompanyPhotoService companyPhotoService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String ignorehead(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		Company company = (Company) req.getAttribute("o");
		SimplePage page = req.getSimplePage(1);
		List<CompanyPhoto> list = null;
		if (company.getLogopath() != null) {
			list = this.companyPhotoService.getPhotoListByCompanyIdNoLogo(
					companyId, company.getLogopath(), page.getBegin(), page
							.getSize() + 1);
		}
		else {
			list = this.companyPhotoService.getPhotoListByCompanyId(companyId,
					page.getBegin(), page.getSize() + 1);
		}
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWapPath(req, "photo/photo.jsp");
	}
}