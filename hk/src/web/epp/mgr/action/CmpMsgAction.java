package web.epp.mgr.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpMsg;
import com.hk.bean.CmpUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpMsgService;

/**
 * 留言管理
 * 
 * @author akwei
 */
@Component("/epp/web/op/webadmin/cmpmsg")
public class CmpMsgAction extends EppBaseAction {

	@Autowired
	private CmpMsgService cmpMsgService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_4", 1);
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		List<CmpMsg> list = this.cmpMsgService.getCmpMsgListByCompanyId(
				companyId, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebPath("admin/cmpmsg/list.jsp");
	}

	/**
	 * 删除留言
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		this.cmpMsgService.deleteCmpMsg(oid);
		req.setSessionText("view2.data.delete.ok");
		return null;
	}

	/**
	 * 取消推荐留言
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String delcmppink(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		this.cmpMsgService.updateCmpMsgCmppink(oid, CmpUtil.CMPPINK_N);
		this.setDelPinkSuccessMsg(req);
		return null;
	}

	/**
	 * 推荐留言
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String cmppink(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		this.cmpMsgService.updateCmpMsgCmppink(oid, CmpUtil.CMPPINK_Y);
		this.setPinkSuccessMsg(req);
		return null;
	}
}