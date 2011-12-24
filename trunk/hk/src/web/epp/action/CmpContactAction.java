package web.epp.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpContact;
import com.hk.bean.Company;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpContactService;

@Component("/epp/web/cmpcontact")
public class CmpContactAction extends EppBaseAction {

	@Autowired
	private CmpContactService cmpContactService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		int cmpflg = this.getCmpflg(req);
		if (cmpflg == Company.CMPFLG_NORMAL) {
			return this.exe0(req, resp);
		}
		return null;
	}

	private String exe0(HkRequest req, HkResponse resp) throws Exception {
		int tmlflg = this.getTmlflg(req);
		if (tmlflg == 0) {
			return this.exe00(req, resp);
		}
		if (tmlflg == 1) {
			return this.exe01(req, resp);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-4
	 */
	private String exe00(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		this.setCmpNavInfo(req);
		List<CmpContact> list = this.cmpContactService
				.getCmpContactListByCompanyId(companyId);
		req.setAttribute("list", list);
		return this.getWebPath("cmpcontact/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-4
	 */
	private String exe01(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		this.setCmpNavInfo(req);
		List<CmpContact> list = this.cmpContactService
				.getCmpContactListByCompanyId(companyId);
		req.setAttribute("list", list);
		return this.getWebPath("mod/0/1/cmpcontact/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	public String wap(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		this.setCmpNavInfo(req);
		List<CmpContact> list = this.cmpContactService
				.getCmpContactListByCompanyId(companyId);
		req.setAttribute("list", list);
		return this.getWapPath("cmpcontact/list.jsp");
	}
}