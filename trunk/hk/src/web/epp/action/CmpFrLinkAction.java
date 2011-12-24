package web.epp.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpFrLink;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpFrLinkService;

@Component("/epp/web/cmpfrlink")
public class CmpFrLinkAction extends EppBaseAction {

	@Autowired
	private CmpFrLinkService cmpFrLinkService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		List<CmpFrLink> list = this.cmpFrLinkService
				.getCmpFrLinkListByCompanyId(companyId);
		req.setAttribute("list", list);
		return this.getWebPath("cmpfrlink/list.jsp");
	}
}