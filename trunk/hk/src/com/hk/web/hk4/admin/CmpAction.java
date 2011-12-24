package com.hk.web.hk4.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpFuncRef;
import com.hk.bean.CmpInfo;
import com.hk.bean.CmpInfoTml;
import com.hk.bean.CmpOtherInfo;
import com.hk.bean.CmpOtherWebInfo;
import com.hk.bean.CmpSvrCnf;
import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpFuncService;
import com.hk.svr.CmpInfoService;
import com.hk.svr.CmpOtherWebInfoService;
import com.hk.svr.CmpSvrCnfService;
import com.hk.svr.CompanyService;
import com.hk.svr.UserService;
import com.hk.svr.pub.CmpInfoTmlUtil;
import com.hk.web.pub.action.BaseAction;

@Component("/h4/admin/cmp")
public class CmpAction extends BaseAction {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

	@Autowired
	private CmpInfoService cmpInfoService;

	@Autowired
	private CmpOtherWebInfoService cmpOtherWebInfoService;

	@Autowired
	private CmpSvrCnfService cmpSvrCnfService;

	@Autowired
	private CmpFuncService cmpFuncService;

	public String execute(HkRequest req, HkResponse resp) {
		String name = req.getString("name");
		int pcityId = req.getInt("pcityId");
		byte status = req.getByte("status", (byte) -100);
		byte freezeflg = req.getByte("freezeflg", (byte) -1);
		SimplePage page = req.getSimplePage(20);
		List<Company> list = this.companyService
				.getCompanyListByCdn(name, status, freezeflg, pcityId, page
						.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.setAttribute("pcityId", pcityId);
		req.setEncodeAttribute("name", name);
		req.setAttribute("status", status);
		return this.getWeb4Jsp("admin/cmp/list.jsp");
	}

	/**
	 * 审核通过
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-13
	 */
	public String checkok(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		this.companyService.updateCompanyStatus(companyId,
				Company.COMPANYSTATUS_CHECKED);
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * 审核通过
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-13
	 */
	public String checkfail(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		this.companyService.updateCompanyStatus(companyId,
				Company.COMPANYSTATUS_CHECKFAIL);
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * 把企业转移给某用户的查找，可以根据nickname email mobile进行查找人
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String chgcmpuser(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		if (this.isForwardPage(req)) {
			return this.getWeb4Jsp("admin/cmp/chgcmpuser.jsp");
		}
		User user = null;
		UserOtherInfo info = null;
		String nickName = req.getString("nickName");
		String email = req.getString("email");
		String mobile = req.getString("mobile");
		if (!DataUtil.isEmpty(nickName)) {
			user = this.userService.getUserByNickName(nickName);
			if (user != null) {
				info = this.userService.getUserOtherInfo(user.getUserId());
			}
		}
		else if (!DataUtil.isEmpty(email)) {
			info = this.userService.getUserOtherInfoByeEmail(email);
			if (info != null) {
				user = this.userService.getUser(info.getUserId());
			}
		}
		else if (!DataUtil.isEmpty(mobile)) {
			info = this.userService.getUserOtherInfoByMobile(mobile);
			if (info != null) {
				user = this.userService.getUser(info.getUserId());
			}
		}
		if (user != null) {
			req.setAttribute("user", user);
			req.setAttribute("info", info);
		}
		return this.getWeb4Jsp("admin/cmp/chgcmpuser.jsp");
	}

	/**
	 * 把企业转移给某用户
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-4
	 */
	public String cfmchgcmpuser(HkRequest req, HkResponse resp) {
		long userId = req.getLong("userId");
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		company.setUserId(userId);
		this.companyService.updateCompanyUserId(companyId, userId);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 修改企业网站类型
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String chgcmpflg(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		if (this.isForwardPage(req)) {
			return this.getWeb4Jsp("admin/cmp/chgcmpflg.jsp");
		}
		company.setCmpflg(req.getByte("cmpflg"));
		this.companyService.updateCompany(company);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 更换企业模板
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String chgcmptml(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		CmpInfo cmpInfo = this.cmpInfoService.getCmpInfo(companyId);
		req.setAttribute("cmpInfo", cmpInfo);
		req.setAttribute("company", company);
		if (this.isForwardPage(req)) {
			List<CmpInfoTml> tmllist = CmpInfoTmlUtil
					.getCmpInfoTmlByCmpflg(company.getCmpflg());
			req.setAttribute("tmllist", tmllist);
			return this.getWeb4Jsp("admin/cmp/chgcmptml.jsp");
		}
		int tmlflg = req.getInt("tmlflg");
		if (cmpInfo != null) {
			cmpInfo.setTmlflg(tmlflg);
			this.cmpInfoService.updateCmpInfo(cmpInfo);
			this.setOpFuncSuccessMsg(req);
		}
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 设置企业网站文件容量
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String setfilesize(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		CmpOtherWebInfo cmpOtherWebInfo = this.cmpOtherWebInfoService
				.getCmpOtherWebInfo(companyId);
		int size = req.getInt("size") * 1024;
		if (cmpOtherWebInfo == null) {
			cmpOtherWebInfo = new CmpOtherWebInfo();
			cmpOtherWebInfo.setCompanyId(companyId);
			cmpOtherWebInfo.setTotalFileSize(size);
			this.cmpOtherWebInfoService.createCmpOtherWebInfo(cmpOtherWebInfo);
		}
		else {
			cmpOtherWebInfo.setTotalFileSize(size);
			this.cmpOtherWebInfoService.updateCmpOtherWebInfo(cmpOtherWebInfo);
		}
		this.setOpFuncSuccessMsg(req);
		return "r:/e/admin/admin_viewcmp.do?companyId=" + companyId;
	}

	/**
	 * 修改企业网站类型
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String setproductattrflg(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		company.setProductattrflg(req.getByte("productattrflg"));
		this.companyService.updateCompany(company);
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * 开启或关闭文件系统服务
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String setsvrfileflg(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		CmpSvrCnf cmpSvrCnf = this.cmpSvrCnfService.getCmpSvrCnf(companyId);
		if (cmpSvrCnf == null) {
			cmpSvrCnf = new CmpSvrCnf();
			cmpSvrCnf.setCompanyId(companyId);
			cmpSvrCnf.setFileflg(req.getByte("fileflg"));
			this.cmpSvrCnfService.createCmpSvrCnf(cmpSvrCnf);
		}
		else {
			cmpSvrCnf.setFileflg(req.getByte("fileflg"));
			this.cmpSvrCnfService.updateCmpSvrCnf(cmpSvrCnf);
		}
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * 开启或关闭视频系统服务
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String setsvrvideoflg(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		CmpSvrCnf cmpSvrCnf = this.cmpSvrCnfService.getCmpSvrCnf(companyId);
		if (cmpSvrCnf == null) {
			cmpSvrCnf = new CmpSvrCnf();
			cmpSvrCnf.setCompanyId(companyId);
			cmpSvrCnf.setVideoflg(req.getByte("videoflg"));
			this.cmpSvrCnfService.createCmpSvrCnf(cmpSvrCnf);
		}
		else {
			cmpSvrCnf.setVideoflg(req.getByte("videoflg"));
			this.cmpSvrCnfService.updateCmpSvrCnf(cmpSvrCnf);
		}
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * 查看足迹详细信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createcmpfunc(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long[] funcoid = req.getLongs("funcoid");
		List<CmpFuncRef> funcreflist = this.cmpFuncService
				.getCmpFuncRefListByCompanyId(companyId);
		for (CmpFuncRef o : funcreflist) {
			this.cmpFuncService.deleteCmpFuncRef(companyId, o.getOid());
		}
		if (funcoid != null) {
			for (long oid : funcoid) {
				CmpFuncRef cmpFuncRef = new CmpFuncRef();
				cmpFuncRef.setCompanyId(companyId);
				cmpFuncRef.setFuncoid(oid);
				cmpFuncRef.initCnfData();
				this.cmpFuncService.createCmpFuncRef(cmpFuncRef);
			}
		}
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createfuncok", null);
	}

	/**
	 * 查看足迹详细信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String view(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		req.setAttribute("company", company);
		CmpInfo cmpInfo = this.cmpInfoService.getCmpInfo(companyId);
		req.setAttribute("cmpInfo", cmpInfo);
		if (company.getUserId() > 0) {
			User user = this.userService.getUser(company.getUserId());
			req.setAttribute("user", user);
		}
		CmpSvrCnf cmpSvrCnf = this.cmpSvrCnfService.getCmpSvrCnf(companyId);
		req.setAttribute("cmpSvrCnf", cmpSvrCnf);
		CmpOtherWebInfo cmpOtherWebInfo = this.cmpOtherWebInfoService
				.getCmpOtherWebInfo(companyId);
		req.setAttribute("cmpOtherWebInfo", cmpOtherWebInfo);
		List<CmpFuncRef> cmpfuncreflist = this.cmpFuncService
				.getCmpFuncRefListByCompanyId(companyId);
		StringBuilder sb = new StringBuilder();
		for (CmpFuncRef o : cmpfuncreflist) {
			sb.append(o.getFuncoid()).append(",");
		}
		req.setAttribute("sel_funcoid", sb.toString());
		CmpOtherInfo cmpOtherInfo = this.companyService
				.getCmpOtherInfo(companyId);
		req.setAttribute("cmpOtherInfo", cmpOtherInfo);
		return this.getWeb4Jsp("admin/cmp/view.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String hidehkad(HkRequest req, HkResponse resp) {
		this.updateCmpOtherInfoAdclose(req, CmpOtherInfo.ADCLOSE_Y);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String showhkad(HkRequest req, HkResponse resp) {
		this.updateCmpOtherInfoAdclose(req, CmpOtherInfo.ADCLOSE_N);
		return null;
	}

	private void updateCmpOtherInfoAdclose(HkRequest req, byte adclose) {
		long companyId = req.getLong("companyId");
		CmpOtherInfo cmpOtherInfo = this.companyService
				.getCmpOtherInfo(companyId);
		if (cmpOtherInfo == null) {
			cmpOtherInfo = new CmpOtherInfo();
			cmpOtherInfo.setCompanyId(companyId);
			cmpOtherInfo.setAdclose(adclose);
			this.companyService.createCmpOtherInfo(cmpOtherInfo);
		}
		else {
			cmpOtherInfo.setAdclose(adclose);
			this.companyService.updateCmpOtherInfo(cmpOtherInfo);
		}
	}
}