package web.epp.mgr.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpContact;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpContactService;
import com.hk.svr.pub.Err;

/**
 * 企业在线联系(qq在线代码需要用户自行申请)
 * 
 * @author akwei
 */
@Component("/epp/web/op/webadmin/cmpcontact")
public class CmpContactAction extends EppBaseAction {

	@Autowired
	private CmpContactService cmpContactService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_3", 1);
		long companyId = req.getLong("companyId");
		List<CmpContact> list = this.cmpContactService
				.getCmpContactListByCompanyId(companyId);
		req.setAttribute("list", list);
		return this.getWebPath("admin/cmpcontact/list.jsp");
	}

	/**
	 * 创建联系
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_3", 1);
		long companyId = req.getLong("companyId");
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWebPath("admin/cmpcontact/create.jsp");
		}
		CmpContact cmpContact = new CmpContact();
		cmpContact.setCompanyId(companyId);
		cmpContact.setQq(req.getHtmlRow("qq"));
		cmpContact.setQqhtml(req.getString("qqhtml"));
		cmpContact.setName(req.getHtmlRow("name"));
		int code = cmpContact.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		this.cmpContactService.createCmpContact(cmpContact);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * 修改联系
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_3", 1);
		long oid = req.getLongAndSetAttr("oid");
		int ch = req.getInt("ch");
		CmpContact cmpContact = this.cmpContactService.getCmpContact(oid);
		req.setAttribute("cmpContact", cmpContact);
		if (ch == 0) {
			return this.getWebPath("admin/cmpcontact/update.jsp");
		}
		cmpContact.setQq(req.getHtmlRow("qq"));
		cmpContact.setQqhtml(req.getString("qqhtml"));
		cmpContact.setName(req.getHtmlRow("name"));
		int code = cmpContact.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		this.cmpContactService.updateCmpContact(cmpContact);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 删除
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		this.cmpContactService.deleteCmpContact(oid);
		req.setSessionText("view2.data.delete.ok");
		return null;
	}
}