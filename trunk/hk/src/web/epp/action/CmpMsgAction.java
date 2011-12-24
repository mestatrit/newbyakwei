package web.epp.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpContact;
import com.hk.bean.CmpMsg;
import com.hk.bean.Company;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpContactService;
import com.hk.svr.CmpMsgService;
import com.hk.svr.pub.Err;

@Component("/epp/web/cmpmsg")
public class CmpMsgAction extends EppBaseAction {

	@Autowired
	private CmpMsgService cmpMsgService;

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
		this.setCmpNavInfo(req);
		return this.getWebPath("cmpmsg/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-4
	 */
	private String exe01(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		long companyId = req.getLong("companyId");
		List<CmpContact> contactlist = this.cmpContactService
				.getCmpContactListByCompanyId(companyId);
		req.setAttribute("contactlist", contactlist);
		return this.getWebPath("mod/0/1/cmpmsg/list.jsp");
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
		return this.getWapPath("cmpmsg/list.jsp");
	}

	/**
	 * 填写留言
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String msgwapcreate(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		CmpMsg cmpMsg = new CmpMsg();
		cmpMsg.setName(req.getHtmlRow("name"));
		cmpMsg.setTel(req.getHtmlRow("tel"));
		cmpMsg.setContent(req.getHtmlRow("content"));
		cmpMsg.setCompanyId(companyId);
		cmpMsg.setCreateTime(new Date());
		cmpMsg.setIp(req.getRemoteAddr());
		int code = cmpMsg.validate();
		if (code != Err.SUCCESS) {
			req.setAttribute("cmpMsg", cmpMsg);
			req.setText(String.valueOf(code));
			return "/epp/web/cmpmsg_wap.do?companyId=" + companyId;
		}
		this.cmpMsgService.createCmpMsg(cmpMsg);
		req.setSessionText("epp.cmpmsg.create.success");
		return "r:/epp/web/cmpmsg_wap.do?companyId=" + companyId + "&navId="
				+ req.getLong("navId");
	}

	/**
	 * 填写留言
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String msgcreate(HkRequest req, HkResponse resp) throws Exception {
		int ch = req.getInt("ch");
		if (ch == 0) {
			this.setCmpNavInfo(req);
			return this.getWebPath("cmpmsg/create.jsp");
		}
		long companyId = req.getLong("companyId");
		CmpMsg cmpMsg = new CmpMsg();
		cmpMsg.setName(req.getHtmlRow("name"));
		cmpMsg.setTel(req.getHtmlRow("tel"));
		cmpMsg.setContent(req.getHtmlRow("content"));
		cmpMsg.setCompanyId(companyId);
		cmpMsg.setCreateTime(new Date());
		cmpMsg.setIp(req.getRemoteAddr());
		int code = cmpMsg.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		this.cmpMsgService.createCmpMsg(cmpMsg);
		req.setSessionText("epp.cmpmsg.create.success");
		return this.onSuccess2(req, "createok", null);
	}
}