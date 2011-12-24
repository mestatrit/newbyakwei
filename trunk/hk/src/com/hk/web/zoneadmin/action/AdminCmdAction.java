package com.hk.web.zoneadmin.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmdCmp;
import com.hk.bean.CmdProduct;
import com.hk.bean.CmpProduct;
import com.hk.bean.Company;
import com.hk.bean.ZoneAdmin;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpProductService;
import com.hk.svr.CompanyService;
import com.hk.svr.ZoneAdminService;
import com.hk.web.pub.action.BaseAction;

@Component("/op/cmd/cmd")
public class AdminCmdAction extends BaseAction {
	@Autowired
	private ZoneAdminService zoneAdminService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CmpProductService cmpProductService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String cmdcmplist(HkRequest req, HkResponse resp) throws Exception {
		ZoneAdmin zoneAdmin = this.getZoneAdmin(req);
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.companyService.countCmdCmp(zoneAdmin
				.getPcityId()));
		List<CmdCmp> cmdcmplist = this.companyService.getCmdCmpList(zoneAdmin
				.getPcityId(), page.getBegin(), page.getSize());
		List<Long> idList = new ArrayList<Long>();
		for (CmdCmp cmp : cmdcmplist) {
			idList.add(cmp.getCompanyId());
		}
		Map<Long, Company> map = this.companyService.getCompanyMapInId(idList);
		for (CmdCmp cmp : cmdcmplist) {
			cmp.setCompany(map.get(cmp.getCompanyId()));
		}
		req.setAttribute("cmdcmplist", cmdcmplist);
		req.setAttribute("op_func", 1);
		return this.getWeb3Jsp("zoneadmin/cmdcmplist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String cmdproductlist(HkRequest req, HkResponse resp)
			throws Exception {
		ZoneAdmin zoneAdmin = this.getZoneAdmin(req);
		int pcityId = zoneAdmin.getPcityId();
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.cmpProductService.countCmdProduct(pcityId));
		List<CmdProduct> cmdproductlist = this.cmpProductService
				.getCmdProductList(pcityId, page.getBegin(), page.getSize());
		List<Long> idList = new ArrayList<Long>();
		for (CmdProduct o : cmdproductlist) {
			idList.add(o.getProductId());
		}
		Map<Long, CmpProduct> map = this.cmpProductService
				.getCmpProductMapInId(idList);
		for (CmdProduct o : cmdproductlist) {
			o.setCmpProduct(map.get(o.getProductId()));
		}
		req.setAttribute("cmdproductlist", cmdproductlist);
		req.setAttribute("op_func", 2);
		return this.getWeb3Jsp("zoneadmin/cmdproductlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delcmdcmp(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		CmdCmp cmdCmp = this.companyService.getCmdCmp(oid);
		if (cmdCmp == null
				|| cmdCmp.getPcityId() != this.getZoneAdmin(req).getPcityId()) {
			return null;
		}
		this.companyService.deleteCmdCmp(oid);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delcmdproduct(HkRequest req, HkResponse resp)
			throws Exception {
		long oid = req.getLong("oid");
		CmdProduct cmdProduct = this.cmpProductService.getCmdProduct(oid);
		if (cmdProduct == null
				|| cmdProduct.getPcityId() != this.getZoneAdmin(req)
						.getPcityId()) {
			return null;
		}
		this.cmpProductService.deleteCmdProduct(oid);
		return null;
	}

	public String createcmdcmp(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		ZoneAdmin zoneAdmin = this.getZoneAdmin(req);
		if (zoneAdmin == null || zoneAdmin.getPcityId() != company.getPcityId()) {
			if (!this.isAdminUser(req)) {
				return null;
			}
		}
		CmdCmp cmdCmp = new CmdCmp();
		cmdCmp.setCompanyId(companyId);
		cmdCmp.setPcityId(company.getPcityId());
		this.companyService.createCmdCmp(cmdCmp);
		resp.sendHtml(1);
		return null;
	}

	public String createcmdproduct(HkRequest req, HkResponse resp)
			throws Exception {
		long userId = this.getLoginUser(req).getUserId();
		long pid = req.getLong("pid");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(pid);
		if (cmpProduct == null) {
			return null;
		}
		long companyId = cmpProduct.getCompanyId();
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		ZoneAdmin zoneAdmin = this.zoneAdminService.getZoneAdmin(userId);
		if (zoneAdmin == null || zoneAdmin.getPcityId() != company.getPcityId()) {
			if (!this.isAdminUser(req)) {
				return null;
			}
		}
		CmdProduct cmdProduct = new CmdProduct();
		cmdProduct.setPcityId(company.getPcityId());
		cmdProduct.setProductId(pid);
		cmdProduct.setCompanyId(companyId);
		this.cmpProductService.createCmdProduct(cmdProduct);
		resp.sendHtml(1);
		return null;
	}
}