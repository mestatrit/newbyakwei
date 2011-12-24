package web.epp.mgr.action;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpHomePicAd;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpHomePicAdService;
import com.hk.svr.processor.CmpHomePicAdProcessor;
import com.hk.svr.pub.Err;

@Component("/epp/web/op/webadmin/cmphomepicad")
public class CmpHomePicAdAction extends EppBaseAction {

	@Autowired
	private CmpHomePicAdProcessor cmpHomePicAdProcessor;

	@Autowired
	private CmpHomePicAdService cmpHomePicAdService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		List<CmpHomePicAd> list = this.cmpHomePicAdService
				.getCmpHomePicAdListByCompanyId(companyId, 0, 4);
		req.setAttribute("list", list);
		return this.getWebPath("admin/cmphomepicad/list.jsp");
	}

	/**
	 * 发布新广告，最多只能发布5个
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String create(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		List<CmpHomePicAd> list = this.cmpHomePicAdService
				.getCmpHomePicAdListByCompanyId(companyId, 0, 4);
		if (list.size() >= 4) {
			req.setSessionText("epp.cmphomepicad.limit", new Object[] { 4 });
			return "r:/epp/web/op/webadmin/cmphomepicad.do?companyId="
					+ companyId;
		}
		if (this.isForwardPage(req)) {
			return this.getWebPath("admin/cmphomepicad/create.jsp");
		}
		CmpHomePicAd cmpHomePicAd = new CmpHomePicAd();
		cmpHomePicAd.setTitle(req.getHtmlRow("title"));
		cmpHomePicAd.setCompanyId(companyId);
		cmpHomePicAd.setUrl(req.getHtmlRow("url"));
		int code = cmpHomePicAd.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		try {
			if (cmpHomePicAd.getUrl().toLowerCase().startsWith("http://")) {
				cmpHomePicAd.setUrl(cmpHomePicAd.getUrl().substring(7));
			}
			code = this.cmpHomePicAdProcessor.createCmpHomePicAd(cmpHomePicAd,
					req.getFile("f"));
			if (code != Err.SUCCESS) {
				if (code == Err.IMG_OUTOFSIZE_ERROR) {
					return this.onError(req, code, new Object[] { "100K" },
							"createerror", null);
				}
				return this.onError(req, code, "createerror", null);
			}
			this.setOpFuncSuccessMsg(req);
			return this.onSuccess2(req, "createok", null);
		}
		catch (IOException e) {
			return this.onError(req, Err.IMG_UPLOAD_ERROR, "createerror", null);
		}
	}

	/**
	 * 更新广告
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		long adid = req.getLongAndSetAttr("adid");
		CmpHomePicAd cmpHomePicAd = this.cmpHomePicAdService
				.getCmpHomePicAd(adid);
		if (cmpHomePicAd == null) {
			return null;
		}
		req.setAttribute("cmpHomePicAd", cmpHomePicAd);
		if (req.getInt("ch") == 0) {
			return this.getWebPath("admin/cmphomepicad/update.jsp");
		}
		cmpHomePicAd.setTitle(req.getHtmlRow("title"));
		cmpHomePicAd.setUrl(req.getHtmlRow("url"));
		int code = cmpHomePicAd.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		try {
			if (cmpHomePicAd.getUrl().toLowerCase().startsWith("http://")) {
				cmpHomePicAd.setUrl(cmpHomePicAd.getUrl().substring(7));
			}
			code = this.cmpHomePicAdProcessor.updateCmpHomePicAd(cmpHomePicAd,
					req.getFile("f"));
			if (code != Err.SUCCESS) {
				if (code == Err.IMG_OUTOFSIZE_ERROR) {
					return this.onError(req, code, new Object[] { "100K" },
							"updateerror", null);
				}
				return this.onError(req, code, "updateerror", null);
			}
			this.setOpFuncSuccessMsg(req);
			return this.onSuccess2(req, "updateok", null);
		}
		catch (IOException e) {
			return this.onError(req, Err.IMG_UPLOAD_ERROR, "updateerror", null);
		}
	}

	/**
	 * 删除广告
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long adid = req.getLongAndSetAttr("adid");
		long companyId = req.getLong("companyId");
		CmpHomePicAd cmpHomePicAd = this.cmpHomePicAdService
				.getCmpHomePicAd(adid);
		if (cmpHomePicAd == null) {
			return null;
		}
		if (cmpHomePicAd.getCompanyId() == companyId) {
			this.cmpHomePicAdProcessor.deleteCmpHomePicAd(adid);
			this.setDelSuccessMsg(req);
		}
		return null;
	}
}