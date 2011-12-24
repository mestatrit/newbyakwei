package web.epp.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpSellNet;
import com.hk.bean.CmpSellNetKind;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpSellNetService;

@Component("/epp/web/cmpsellnet")
public class CmpSellNetAction extends EppBaseAction {

	@Autowired
	private CmpSellNetService cmpSellNetService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		long companyId = req.getLong("companyId");
		long kindId = req.getLongAndSetAttr("kindId");
		SimplePage page = req.getSimplePage(20);
		List<CmpSellNet> list = null;
		if (kindId > 0) {
			list = this.cmpSellNetService.getCmpSellNetListByCompanyId(
					companyId, kindId, page.getBegin(), page.getSize() + 1);
		}
		else {
			list = this.cmpSellNetService.getCmpSellNetListByCompanyId(
					companyId, page.getBegin(), page.getSize() + 1);
		}
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		List<CmpSellNetKind> kindlist = this.cmpSellNetService
				.getCmpSellNetKindListByCompanyId(companyId);
		req.setAttribute("kindlist", kindlist);
		return this.getWebPath("cmpsellnet/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	public String wap(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		List<CmpSellNet> list = this.cmpSellNetService
				.getCmpSellNetListByCompanyId(companyId, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWapPath("cmpsellnet/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	public String map(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		long oid = req.getLongAndSetAttr("oid");
		CmpSellNet cmpSellNet = this.cmpSellNetService.getCmpSellNet(oid);
		if (cmpSellNet == null) {
			return null;
		}
		req.setAttribute("cmpSellNet", cmpSellNet);
		req.setEncodeAttribute("addr", cmpSellNet.getAddr());
		return this.getWebPath("cmpsellnet/map.jsp");
	}
}