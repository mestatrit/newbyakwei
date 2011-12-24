package web.epp.action;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpJoinInApply;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpJoinInApplyService;
import com.hk.svr.pub.Err;

@Component("/epp/web/cmpjoininapply")
public class CmpJoinInApplyAction extends EppBaseAction {

	@Autowired
	private CmpJoinInApplyService cmpJoinInApplyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 填写申请
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		CmpJoinInApply cmpJoinInApply = new CmpJoinInApply();
		cmpJoinInApply.setName(req.getHtmlRow("name"));
		cmpJoinInApply.setTel(req.getHtmlRow("tel"));
		cmpJoinInApply.setMobile(req.getHtmlRow("mobile"));
		cmpJoinInApply.setCmpname(req.getHtmlRow("cmpname"));
		cmpJoinInApply.setCompanyId(companyId);
		cmpJoinInApply.setCreateTime(new Date());
		cmpJoinInApply.setContent(req.getHtml("content"));
		cmpJoinInApply.setReaded(CmpJoinInApply.READED_N);
		cmpJoinInApply.setIp(req.getRemoteAddr());
		int code = cmpJoinInApply.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		this.cmpJoinInApplyService.createCmpJoinInApply(cmpJoinInApply);
		req.setSessionText("epp.cmpjoininapply.create.success");
		return this.onSuccess2(req, "createok", null);
	}
}