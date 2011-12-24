package web.epp.mgr.action;

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
import com.hk.svr.processor.CmpSellNetProcessor;
import com.hk.svr.pub.Err;

/**
 * 企业销售网络
 * 
 * @author akwei
 */
@Component("/epp/web/op/webadmin/cmpsellnet")
public class CmpSellNetAction extends EppBaseAction {

	@Autowired
	private CmpSellNetService cmpSellNetService;

	@Autowired
	private CmpSellNetProcessor cmpSellNetProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_2", 1);
		long companyId = req.getLong("companyId");
		long kindId = req.getLongAndSetAttr("kindId");
		String name = req.getHtmlRow("name");
		SimplePage page = req.getSimplePage(20);
		List<CmpSellNet> list = this.cmpSellNetProcessor
				.getCmpSellNetListByCompanyIdEx(companyId, name, kindId, true,
						page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.setEncodeAttribute("name", name);
		return this.getWebPath("/admin/cmpsellnet/list.jsp");
	}

	/**
	 * 添加销售网络
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_2", 1);
		long companyId = req.getLong("companyId");
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWebPath("/admin/cmpsellnet/create.jsp");
		}
		CmpSellNet cmpSellNet = new CmpSellNet();
		cmpSellNet.setName(req.getHtmlRow("name"));
		cmpSellNet.setAddr(req.getHtmlRow("addr"));
		cmpSellNet.setTel(req.getHtmlRow("tel"));
		cmpSellNet.setKindId(req.getLong("kindId"));
		cmpSellNet.setCompanyId(companyId);
		int code = cmpSellNet.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		this.cmpSellNetService.createCmpSellNet(cmpSellNet);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * 修改销售网络
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_2", 1);
		long companyId = req.getLong("companyId");
		long oid = req.getLongAndSetAttr("oid");
		int ch = req.getInt("ch");
		CmpSellNet cmpSellNet = this.cmpSellNetService.getCmpSellNet(oid);
		req.setAttribute("cmpSellNet", cmpSellNet);
		if (ch == 0) {
			return this.getWebPath("/admin/cmpsellnet/update.jsp");
		}
		cmpSellNet.setName(req.getHtmlRow("name"));
		cmpSellNet.setAddr(req.getHtmlRow("addr"));
		cmpSellNet.setTel(req.getHtmlRow("tel"));
		cmpSellNet.setKindId(req.getLong("kindId"));
		cmpSellNet.setCompanyId(companyId);
		int code = cmpSellNet.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		this.cmpSellNetService.updateCmpSellNet(cmpSellNet);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 删除销售网络
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLongAndSetAttr("oid");
		this.cmpSellNetService.deleteCmpSellNet(oid);
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * 删除销售网络
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String setorderflg(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLongAndSetAttr("oid");
		int orderflg = req.getInt("orderflg");
		this.cmpSellNetService.updateCmpSellNetOrderflg(oid, orderflg);
		req.setSessionText("epp.cmpsellnet.orderflg.set.success");
		return this.onSuccess2(req, "setorderflgok", null);
	}

	/**
	 * 添加销售网络分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String createkind(HkRequest req, HkResponse resp) throws Exception {
		CmpSellNetKind cmpSellNetKind = new CmpSellNetKind();
		cmpSellNetKind.setCompanyId(req.getLong("companyId"));
		cmpSellNetKind.setName(req.getHtmlRow("name"));
		int code = cmpSellNetKind.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		this.cmpSellNetService.createCmpSellNetKind(cmpSellNetKind);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * 修改销售网络分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String updatekind(HkRequest req, HkResponse resp) throws Exception {
		long kindId = req.getLongAndSetAttr("kindId");
		CmpSellNetKind cmpSellNetKind = this.cmpSellNetService
				.getCmpSellNetKind(kindId);
		if (req.getInt("ch") == 0) {
			req.reSetAttribute("navoid");
			req.setAttribute("cmpSellNetKind", cmpSellNetKind);
			return this.getWebPath("admin/cmpsellnet/updatekind.jsp");
		}
		cmpSellNetKind.setName(req.getHtmlRow("name"));
		int code = cmpSellNetKind.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		this.cmpSellNetService.updateCmpSellNetKind(cmpSellNetKind);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-8
	 */
	public String kindlist(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		List<CmpSellNetKind> list = this.cmpSellNetService
				.getCmpSellNetKindListByCompanyId(companyId);
		req.setAttribute("list", list);
		req.reSetAttribute("navoid");
		return this.getWebPath("admin/cmpsellnet/kindlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-8
	 */
	public String delkind(HkRequest req, HkResponse resp) throws Exception {
		long kindId = req.getLong("kindId");
		this.cmpSellNetService.deleteCmpSellNetKind(kindId);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-8
	 */
	public String setmap(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLongAndSetAttr("oid");
		req.reSetAttribute("navoid");
		CmpSellNet cmpSellNet = this.cmpSellNetService.getCmpSellNet(oid);
		req.setAttribute("cmpSellNet", cmpSellNet);
		req.setEncodeAttribute("addr", cmpSellNet.getAddr());
		return this.getWebPath("admin/cmpsellnet/setmap.jsp");
	}
}