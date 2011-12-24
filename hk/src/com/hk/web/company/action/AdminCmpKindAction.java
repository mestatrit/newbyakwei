package com.hk.web.company.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.CmpChildKind;
import com.hk.bean.CompanyKind;
import com.hk.bean.CompanyKindUtil;
import com.hk.bean.ParentKind;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyKindService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

/**
 * 管理分类以及小分类
 * 
 * @author akwei
 */
@Component("/e/admin/admincmpkind")
public class AdminCmpKindAction extends BaseAction {
	@Autowired
	private CompanyKindService companyKindService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return this.getWapJsp("e/admin/kind/parentlist.jsp");
	}

	/**
	 * 分类列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String cmpkindlist(HkRequest req, HkResponse resp) throws Exception {
		int s_parentId = req.getInt("s_parentId");
		List<CompanyKind> list = companyKindService
				.getCompanyKindList(s_parentId);
		ParentKind parentKind = CompanyKindUtil.getParentKind(s_parentId);
		req.setAttribute("s_parentId", s_parentId);
		req.setAttribute("parentKind", parentKind);
		req.setAttribute("list", list);
		return this.getWapJsp("/e/admin/kind/cmpkindlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toaddcmpkind(HkRequest req, HkResponse resp) throws Exception {
		int s_parentId = req.getInt("s_parentId");
		ParentKind parentKind = CompanyKindUtil.getParentKind(s_parentId);
		req.setAttribute("s_parentId", s_parentId);
		req.setAttribute("parentKind", parentKind);
		return this.getWapJsp("e/admin/kind/addcmpkind.jsp");
	}

	/**
	 * 创建分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addcmpkind(HkRequest req, HkResponse resp) throws Exception {
		int s_parentId = req.getInt("s_parentId");
		String name = req.getString("name");
		CompanyKind o = new CompanyKind();
		o.setParentId(s_parentId);
		o.setName(DataUtil.toHtmlRow(name));
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setAttribute("o", o);
			req.setText(code + "");
			return "/e/admin/admincmpkind_toaddcmpkind.do?s_parentId="
					+ s_parentId;
		}
		if (this.companyKindService.createCompanyKind(o)) {
			this.setOpFuncSuccessMsg(req);
			return "r:/e/admin/admincmpkind_cmpkindlist.do?s_parentId="
					+ s_parentId;
		}
		req.setText(Err.COMPANYKIND_NAME_DUPLICATE + "");
		return "/e/admin/admincmpkind_toaddcmpkind.do?s_parentId=" + s_parentId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toeditcmpkind(HkRequest req, HkResponse resp)
			throws Exception {
		int s_parentId = req.getInt("s_parentId");
		int kindId = req.getInt("kindId");
		CompanyKind o = (CompanyKind) req.getAttribute("o");
		if (o == null) {
			o = this.companyKindService.getCompanyKind(kindId);
		}
		req.setAttribute("kindId", kindId);
		req.setAttribute("o", o);
		req.setAttribute("s_parentId", s_parentId);
		return this.getWapJsp("e/admin/kind/editcmpkind.jsp");
	}

	/**
	 * 分类列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editcmpkind(HkRequest req, HkResponse resp) throws Exception {
		int s_parentId = req.getInt("s_parentId");
		String name = req.getString("name");
		int kindId = req.getInt("kindId");
		CompanyKind o = this.companyKindService.getCompanyKind(kindId);
		if (o == null) {
			return null;
		}
		o.setName(DataUtil.toHtmlRow(name));
		int code = o.validate();
		req.setAttribute("o", o);
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/e/admin/admincmpkind_toaddcmpkind.do";
		}
		if (this.companyKindService.updateCompanyKind(o)) {
			this.setOpFuncSuccessMsg(req);
			return "r:/e/admin/admincmpkind_cmpkindlist.do?s_parentId="
					+ s_parentId;
		}
		req.setText(Err.COMPANYKIND_NAME_DUPLICATE + "");
		return "/e/admin/admincmpkind_toeditcmpkind.do";
	}

	/**
	 * 分类列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delcmpkind(HkRequest req, HkResponse resp) throws Exception {
		int cfm = req.getInt("cfm");
		int s_parentId = req.getInt("s_parentId");
		int kindId = req.getInt("kindId");
		req.setAttribute("kindId", kindId);
		req.setAttribute("s_parentId", s_parentId);
		if (cfm == 0) {
			return this.getWapJsp("e/admin/kind/cfm_delcmpkind.jsp");
		}
		if (req.getString("ok") != null) {
			this.companyKindService.deleteCompanyKind(kindId);
			this.setOpFuncSuccessMsg(req);
		}
		return "r:/e/admin/admincmpkind_cmpkindlist.do?s_parentId="
				+ s_parentId;
	}

	/**
	 * 小分类列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String cmpchildkindlist(HkRequest req, HkResponse resp)
			throws Exception {
		int kindId = req.getInt("kindId");
		List<CmpChildKind> list = this.companyKindService
				.getCmpChildKindList(kindId);
		CompanyKind companyKind = CompanyKindUtil.getCompanyKind(kindId);
		req.setAttribute("companyKind", companyKind);
		req.setAttribute("list", list);
		req.setAttribute("kindId", kindId);
		req.reSetAttribute("s_parentId");
		return this.getWapJsp("/e/admin/kind/cmpchildkindlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toaddcmpchildkind(HkRequest req, HkResponse resp)
			throws Exception {
		int kindId = req.getInt("kindId");
		CompanyKind companyKind = CompanyKindUtil.getCompanyKind(kindId);
		req.setAttribute("kindId", kindId);
		req.setAttribute("companyKind", companyKind);
		req.reSetAttribute("s_parentId");
		return this.getWapJsp("e/admin/kind/addcmpchildkind.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toeditcmpchildkind(HkRequest req, HkResponse resp)
			throws Exception {
		int oid = req.getInt("oid");
		CmpChildKind o = (CmpChildKind) req.getAttribute("o");
		if (o == null) {
			o = this.companyKindService.getCmpChildKind(oid);
		}
		int kindId = req.getInt("kindId");
		CompanyKind companyKind = CompanyKindUtil.getCompanyKind(kindId);
		req.setAttribute("kindId", kindId);
		req.setAttribute("oid", oid);
		req.setAttribute("o", o);
		req.setAttribute("companyKind", companyKind);
		req.reSetAttribute("s_parentId");
		return this.getWapJsp("e/admin/kind/editcmpchildkind.jsp");
	}

	/**
	 * 删除小分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delcmpchildkind(HkRequest req, HkResponse resp)
			throws Exception {
		int cfm = req.getInt("cfm");
		int kindId = req.getInt("kindId");
		int oid = req.getInt("oid");
		req.setAttribute("kindId", kindId);
		req.setAttribute("oid", oid);
		req.reSetAttribute("s_parentId");
		if (cfm == 0) {
			return this.getWapJsp("e/admin/kind/cfm_delcmpchildkind.jsp");
		}
		if (req.getString("ok") != null) {
			this.companyKindService.deleteCmpChildKind(oid);
			this.setOpFuncSuccessMsg(req);
		}
		return "r:/e/admin/admincmpkind_cmpchildkindlist.do?kindId=" + kindId
				+ "&s_parentId=" + req.getInt("s_parentId");
	}

	/**
	 * 创建小分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addcmpchildkind(HkRequest req, HkResponse resp)
			throws Exception {
		int s_parentId = req.getInt("s_parentId");
		int kindId = req.getInt("kindId");
		String name = req.getString("name");
		CmpChildKind o = new CmpChildKind();
		o.setKindId(kindId);
		o.setName(DataUtil.toHtmlRow(name));
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setAttribute("o", o);
			req.setText(code + "");
			return "/e/admin/admincmpkind_toaddcmpchildkind.do";
		}
		if (this.companyKindService.createCmpChildKind(o)) {
			this.setOpFuncSuccessMsg(req);
			return "r:/e/admin/admincmpkind_cmpchildkindlist.do?s_parentId="
					+ s_parentId + "&kindId=" + kindId;
		}
		req.setText(Err.CMPCHILDKIND_NAME_DUPLICATE + "");
		return "/e/admin/admincmpkind_toaddcmpchildkind.do";
	}

	/**
	 * 修改小分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editcmpchildkind(HkRequest req, HkResponse resp)
			throws Exception {
		int s_parentId = req.getInt("s_parentId");
		int kindId = req.getInt("kindId");
		int oid = req.getInt("oid");
		String name = req.getString("name");
		CmpChildKind o = this.companyKindService.getCmpChildKind(oid);
		o.setName(DataUtil.toHtmlRow(name));
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setAttribute("o", o);
			req.setText(code + "");
			return "/e/admin/admincmpkind_toeditcmpchildkind.do";
		}
		if (this.companyKindService.updateCmpChildKind(o)) {
			this.setOpFuncSuccessMsg(req);
			return "r:/e/admin/admincmpkind_cmpchildkindlist.do?s_parentId="
					+ s_parentId + "&kindId=" + kindId;
		}
		req.setText(Err.CMPCHILDKIND_NAME_DUPLICATE + "");
		return "/e/admin/admincmpkind_toeditcmpchildkind.do";
	}
}