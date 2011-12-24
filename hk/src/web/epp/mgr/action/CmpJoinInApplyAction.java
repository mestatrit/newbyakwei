package web.epp.mgr.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpJoinInApply;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpJoinInApplyService;

/**
 * 加盟申请(管理员查看申请，可设置申请处理标志)
 * 
 * @author akwei
 */
@Component("/epp/web/op/webadmin/cmpjoininapply")
public class CmpJoinInApplyAction extends EppBaseAction {

	@Autowired
	private CmpJoinInApplyService cmpJoinApplyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_5", 1);
		byte readed = req.getByteAndSetAttr("readed");
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		List<CmpJoinInApply> list = this.cmpJoinApplyService
				.getCmpJoinInApplyListByCompanyIdAndReaded(companyId, readed,
						page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebPath("/admin/cmpjoininapply/list.jsp");
	}

	/**
	 * 删除申请
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		this.cmpJoinApplyService.deleteCmpJoinInApply(oid);
		return null;
	}

	/**
	 * 设置申请已处理
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String setread(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		this.cmpJoinApplyService.setCmpJoinInApplyReaded(oid,
				CmpJoinInApply.READED_Y);
		return null;
	}

	/**
	 * 设置申请未处理
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String setnoread(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		this.cmpJoinApplyService.setCmpJoinInApplyReaded(oid,
				CmpJoinInApply.READED_N);
		return null;
	}
}