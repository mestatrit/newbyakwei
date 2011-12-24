package web.epp.mgr.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpRefUser;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.processor.CmpRefUserProcessor;

/**
 * 企业会员(报到过、登陆过都算作会员)
 * 
 * @author akwei
 */
@Component("/epp/web/op/webadmin/cmprefuser")
public class CmpRefUserAction extends EppBaseAction {

	@Autowired
	private CmpRefUserProcessor cmpRefUserProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_24", 1);
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		List<CmpRefUser> list = this.cmpRefUserProcessor
				.getCmpRefUserListByCompanyId(companyId, true, page.getBegin(),
						page.getSize() + 1);
		req.setAttribute("list", list);
		return this.getWebPath("admin/cmprefuser/list.jsp");
	}
}