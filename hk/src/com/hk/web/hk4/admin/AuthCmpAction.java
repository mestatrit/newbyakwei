package com.hk.web.hk4.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.AuthCompany;
import com.hk.bean.CmpFuncRef;
import com.hk.bean.Company;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpFuncService;
import com.hk.svr.CompanyService;
import com.hk.web.company.action.AuthCompanyVo;
import com.hk.web.pub.action.BaseAction;

/**
 * 足迹后台管理
 * 
 * @author akwei
 */
@Component("/h4/admin/authcmp")
public class AuthCmpAction extends BaseAction {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CmpFuncService cmpFuncService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return this.authcmplist(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-12
	 */
	public String authcmplist(HkRequest req, HkResponse resp) throws Exception {
		SimplePage page = req.getSimplePage(20);
		byte mainStatus = req.getByte("mainStatus",
				AuthCompany.MAINSTATUS_UNCHECK);
		List<AuthCompany> list = this.companyService
				.getAuthCompanyListByMainStatus(mainStatus, page.getBegin(),
						page.getSize() + 1);
		this.processListForPage(page, list);
		List<AuthCompanyVo> volist = AuthCompanyVo.createVoList(list);
		req.setAttribute("volist", volist);
		req.setAttribute("mainStatus", mainStatus);
		return this.getWeb4Jsp("admin/authcmp/list.jsp");
	}

	public String tocheckok(HkRequest req, HkResponse resp) {
		long sysId = req.getLongAndSetAttr("sysId");
		AuthCompany authCompany = this.companyService.getAuthCompany(sysId);
		if (authCompany == null) {
			return null;
		}
		if (authCompany.getCompanyId() == 0) {
			return this.checkokforcreate(req, resp);
		}
		Company company = this.companyService.getCompany(authCompany
				.getCompanyId());
		if (company == null) {
			return this.checkokforcreate(req, resp);
		}
		return this.checkokforupdate(req, resp);
	}

	/**
	 * 不存在足迹的，创建足迹，建立关联并审核通过.
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-12
	 */
	public String checkokforcreate(HkRequest req, HkResponse resp) {
		long sysId = req.getLongAndSetAttr("sysId");
		AuthCompany authCompany = this.companyService.getAuthCompany(sysId);
		if (authCompany == null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			req.setAttribute("authCompany", authCompany);
			return this.getWeb4Jsp("admin/authcmp/auth_createcmp.jsp");
		}
		// 创建足迹
		Company company = new Company();
		company.setName(authCompany.getName());
		company.setCreaterId(authCompany.getUserId());
		company.setUserId(authCompany.getUserId());
		company.setPcityId(req.getInt("pcityId"));
		company.setParentKindId(req.getInt("parentKindId"));
		company.setKindId(req.getInt("kindId"));
		company.setCompanyStatus(Company.COMPANYSTATUS_CHECKED);
		List<Integer> errlist = company.validateList2();
		if (errlist.size() > 0) {
			return this.onErrorList(req, errlist, "createerr");
		}
		this.companyService.createCompany(company, null);
		// 创建足迹功能
		long[] funcoid = req.getLongs("funcoid");
		if (funcoid != null) {
			for (long oid : funcoid) {
				CmpFuncRef cmpFuncRef = new CmpFuncRef();
				cmpFuncRef.setCompanyId(company.getCompanyId());
				cmpFuncRef.setFuncoid(oid);
				cmpFuncRef.initCnfData();
				this.cmpFuncService.createCmpFuncRef(cmpFuncRef);
			}
		}
		// 更新分类
		authCompany.setCompanyId(company.getCompanyId());
		authCompany.setMainStatus(AuthCompany.MAINSTATUS_CHECKED);
		this.companyService.updateAuthCompany(authCompany);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-12
	 */
	public String del(HkRequest req, HkResponse resp) {
		long sysId = req.getLong("sysId");
		AuthCompany authCompany = this.companyService.getAuthCompany(sysId);
		if (authCompany == null) {
			return null;
		}
		this.companyService.deleteAuthCompany(sysId);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * 不存在足迹的，创建足迹，建立关联并审核通过.
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-12
	 */
	public String checkokforupdate(HkRequest req, HkResponse resp) {
		long sysId = req.getLong("sysId");
		AuthCompany authCompany = this.companyService.getAuthCompany(sysId);
		if (authCompany == null) {
			return null;
		}
		Company company = this.companyService.getCompany(authCompany
				.getCompanyId());
		if (company == null) {
			return null;
		}
		req.setAttribute("company", company);
		if (this.isForwardPage(req)) {
			return this.getWeb4Jsp("admin/authcmp/auth_updatecmp.jsp");
		}
		if (authCompany.getCompanyId() > 0) {
			company.setUserId(authCompany.getUserId());
			company.setParentKindId(req.getInt("parentKindId"));
			company.setKindId(req.getInt("kindId"));
			company.setCompanyStatus(Company.COMPANYSTATUS_CHECKED);
			List<Integer> errlist = company.validateList2();
			if (errlist.size() > 0) {
				return this.onErrorList(req, errlist, "updateerr");
			}
			this.companyService.updateCompany(company);
			// 创建足迹功能
			long[] funcoid = req.getLongs("funcoid");
			if (funcoid != null) {
				for (long oid : funcoid) {
					CmpFuncRef cmpFuncRef = new CmpFuncRef();
					cmpFuncRef.setCompanyId(company.getCompanyId());
					cmpFuncRef.setFuncoid(oid);
					cmpFuncRef.initCnfData();
					this.cmpFuncService.createCmpFuncRef(cmpFuncRef);
				}
			}
			authCompany.setMainStatus(AuthCompany.MAINSTATUS_CHECKED);
			this.companyService.updateAuthCompany(authCompany);
			this.setOpFuncSuccessMsg(req);
		}
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 审核不通过
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-12
	 */
	public String checkfail(HkRequest req, HkResponse resp) {
		long sysId = req.getLong("sysId");
		AuthCompany authCompany = this.companyService.getAuthCompany(sysId);
		if (authCompany == null) {
			return null;
		}
		Company company = this.companyService.getCompany(authCompany
				.getCompanyId());
		if (company != null) {
			company.setUserId(0);
			this.companyService.updateCompany(company);
		}
		authCompany.setMainStatus(AuthCompany.MAINSTATUS_CHECKFAIL);
		this.companyService.updateAuthCompany(authCompany);
		this.setOpFuncSuccessMsg(req);
		return null;
	}
}