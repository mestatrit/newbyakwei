package web.epp.mgr.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpBbsKind;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpBbsService;
import com.hk.svr.pub.Err;

/**
 * 论坛分类
 * 
 * @author akwei
 */
@Component("/epp/web/op/webadmin/admincmpbbskind")
public class CmpBbsKindAction extends EppBaseAction {

	@Autowired
	private CmpBbsService cmpBbsService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_6", 1);
		long companyId = req.getLong("companyId");
		List<CmpBbsKind> list = this.cmpBbsService
				.getCmpBbsKindListByCompanyId(companyId);
		req.setAttribute("list", list);
		return this.getWebPath("admin/cmpbbskind/list.jsp");
	}

	/**
	 * 添加分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_6", 1);
		long companyId = req.getLong("companyId");
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWebPath("admin/cmpbbskind/create.jsp");
		}
		CmpBbsKind cmpBbsKind = new CmpBbsKind();
		cmpBbsKind.setCompanyId(companyId);
		cmpBbsKind.setName(req.getHtmlRow("name"));
		cmpBbsKind.setMustpic(req.getByte("mustpic"));
		int code = cmpBbsKind.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		if (this.cmpBbsService.createCmpBbsKind(cmpBbsKind)) {
			return this.onSuccess2(req, "createok", null);
		}
		return this.onError(req, Err.CMPBBSKIND_NAME_DUPLICATE, "createerror",
				null);
	}

	/**
	 * 修改分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_6", 1);
		long kindId = req.getLongAndSetAttr("kindId");
		CmpBbsKind cmpBbsKind = this.cmpBbsService.getCmpBbsKind(kindId);
		req.setAttribute("cmpBbsKind", cmpBbsKind);
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWebPath("admin/cmpbbskind/update.jsp");
		}
		cmpBbsKind.setName(req.getHtmlRow("name"));
		cmpBbsKind.setMustpic(req.getByte("mustpic"));
		int code = cmpBbsKind.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		if (this.cmpBbsService.updateCmpBbsKind(cmpBbsKind)) {
			return this.onSuccess2(req, "updateok", null);
		}
		return this.onError(req, Err.CMPBBSKIND_NAME_DUPLICATE, "updateerror",
				null);
	}

	/**
	 * 删除分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long kindId = req.getLong("kindId");
		this.cmpBbsService.deleteCmpBbsKind(kindId);
		return null;
	}
}