package web.epp.mgr.action;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpOrg;
import com.hk.bean.CmpOrgApply;
import com.hk.bean.CmpOtherWebInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.mail.MailUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpOrgService;
import com.hk.svr.CmpOtherWebInfoService;

/**
 * 机构相关管理
 * 
 * @author akwei
 */
@Component("/epp/web/op/webadmin/org")
public class OrgAction extends EppBaseAction {

	@Autowired
	private CmpOtherWebInfoService cmpOtherWebInfoService;

	@Autowired
	private CmpOrgService cmpOrgService;

	@Autowired
	private MailUtil mailUtil;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_31", 1);
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		String name = req.getHtmlRow("name");
		List<CmpOrg> list = this.cmpOrgService.getCmpOrgListByCompanyId(
				companyId, name, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.setEncodeAttribute("name", name);
		CmpOtherWebInfo cmpOtherWebInfo = this.cmpOtherWebInfoService
				.getCmpOtherWebInfo(companyId);
		req.setAttribute("cmpOtherWebInfo", cmpOtherWebInfo);
		return this.getWebPath("admin/org/orglist.jsp");
	}

	/**
	 * 设计机构注册不需要申请
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String setorgnocheck(HkRequest req, HkResponse resp) {
		this.setorgcheckflg(req, CmpOtherWebInfo.ORGCHECK_N);
		return null;
	}

	/**
	 * 设计机构注册需要申请
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String setorgneedcheck(HkRequest req, HkResponse resp) {
		this.setorgcheckflg(req, CmpOtherWebInfo.ORGCHECK_Y);
		return null;
	}

	/**
	 * 设置机构是否需要申请
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public void setorgcheckflg(HkRequest req, byte orgcheck) {
		long companyId = req.getLong("companyId");
		CmpOtherWebInfo cmpOtherWebInfo = this.cmpOtherWebInfoService
				.getCmpOtherWebInfo(companyId);
		req.setAttribute("cmpOtherWebInfo", cmpOtherWebInfo);
		if (cmpOtherWebInfo == null) {
			cmpOtherWebInfo = new CmpOtherWebInfo();
			cmpOtherWebInfo.setCompanyId(companyId);
			cmpOtherWebInfo.setTotalFileSize(0);
			cmpOtherWebInfo.setUsedFileSize(0);
			cmpOtherWebInfo.setOrgcheck(orgcheck);
			this.cmpOtherWebInfoService.createCmpOtherWebInfo(cmpOtherWebInfo);
		}
		else {
			cmpOtherWebInfo.setOrgcheck(orgcheck);
			this.cmpOtherWebInfoService.updateCmpOtherWebInfo(cmpOtherWebInfo);
		}
	}

	/**
	 * 申请机构的列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String applylist(HkRequest req, HkResponse resp) {
		req.setAttribute("active_32", 1);
		long companyId = req.getLong("companyId");
		String userName = req.getHtmlRow("userName");
		String orgName = req.getHtmlRow("orgName");
		String tel = req.getHtmlRow("tel");
		String email = req.getHtmlRow("email");
		byte checkflg = req.getByteAndSetAttr("checkflg");
		SimplePage page = req.getSimplePage(20);
		List<CmpOrgApply> list = this.cmpOrgService
				.getCmpOrgApplyListByCompanyId(companyId, userName, tel, email,
						orgName, checkflg, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.setEncodeAttribute("userName", userName);
		req.setEncodeAttribute("orgName", orgName);
		req.setEncodeAttribute("tel", tel);
		req.setEncodeAttribute("email", email);
		return this.getWebPath("admin/org/applylist.jsp");
	}

	/**
	 * 申请不通过
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String applycheckno(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long oid = req.getLong("oid");
		CmpOrgApply cmpOrgApply = this.cmpOrgService.getCmpOrgApply(companyId,
				oid);
		if (cmpOrgApply != null) {
			cmpOrgApply.setCheckflg(CmpOrgApply.CHECKFLG_NO);
			this.cmpOrgService.updateCmpOrgApply(cmpOrgApply);
		}
		return null;
	}

	/**
	 * 申请通过，创建机构，发送邮件
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String applycheckok(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long oid = req.getLong("oid");
		CmpOrgApply cmpOrgApply = this.cmpOrgService.getCmpOrgApply(companyId,
				oid);
		if (cmpOrgApply != null && !cmpOrgApply.isCheckOk()) {
			// 创建机构
			CmpOrg cmpOrg = this.cmpOrgService.checkOkCmpOrgApply(cmpOrgApply);
			this.setOpFuncSuccessMsg(req);
			// 发送邮件
			String title = req.getText("epp.cmporgapply.checkok.email.title");
			String url = "http://" + req.getServerName() + "/edu/" + companyId
					+ "/" + cmpOrg.getOrgId();
			String content = req.getText(
					"epp.cmporgapply.checkok.email.content", cmpOrgApply
							.getOrgName(), url);
			if (DataUtil.isLegalEmail(cmpOrgApply.getEmail())) {
				try {
					this.mailUtil.sendHtmlMail(cmpOrgApply.getEmail(), title,
							content);
				}
				catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 机构设置机构状态为不可使用
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String setorgflgno(HkRequest req, HkResponse resp) {
		this.setorgflg(req, CmpOrg.FLG_N);
		return null;
	}

	/**
	 * 机构设置机构状态为可使用
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String setorgflgok(HkRequest req, HkResponse resp) {
		this.setorgflg(req, CmpOrg.FLG_Y);
		return null;
	}

	public String setorgflg(HkRequest req, byte flg) {
		long companyId = req.getLong("companyId");
		long orgId = req.getLong("orgId");
		CmpOrg cmpOrg = this.cmpOrgService.getCmpOrg(companyId, orgId);
		if (cmpOrg == null) {
			return null;
		}
		if (cmpOrg.getCompanyId() == companyId) {
			cmpOrg.setFlg(flg);
			this.cmpOrgService.updateCmpOrg(cmpOrg);
		}
		return null;
	}

	/**
	 * 开启机构报名详细信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String openuserinfo(HkRequest req, HkResponse resp) {
		this.setuserinfoflg(req, CmpOrg.USERINFOFLG_Y);
		return null;
	}

	/**
	 * 关闭机构报名详细信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String closeuserinfo(HkRequest req, HkResponse resp) {
		this.setuserinfoflg(req, CmpOrg.USERINFOFLG_N);
		return null;
	}

	/**
	 * 设置用户信息开关
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-7-12
	 */
	public String setuserinfoflg(HkRequest req, byte userinfoflg) {
		long companyId = req.getLong("companyId");
		long orgId = req.getLong("orgId");
		CmpOrg cmpOrg = this.cmpOrgService.getCmpOrg(companyId, orgId);
		if (cmpOrg == null || cmpOrg.getCompanyId() != companyId) {
			return null;
		}
		cmpOrg.setUserinfoflg(userinfoflg);
		this.cmpOrgService.updateCmpOrg(cmpOrg);
		return null;
	}

	private String setstyleflg(HkRequest req, byte styleflg) {
		long companyId = req.getLong("companyId");
		long orgId = req.getLong("orgId");
		CmpOrg cmpOrg = this.cmpOrgService.getCmpOrg(companyId, orgId);
		if (cmpOrg == null || cmpOrg.getCompanyId() != companyId) {
			return null;
		}
		cmpOrg.setStyleflg(styleflg);
		this.cmpOrgService.updateCmpOrg(cmpOrg);
		return null;
	}

	/**
	 * 关闭机构自定义配色
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String closestyle(HkRequest req, HkResponse resp) {
		this.setstyleflg(req, CmpOrg.STYLEFLG_N);
		return null;
	}

	/**
	 * 开启机构自定义配色
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String openstyle(HkRequest req, HkResponse resp) {
		this.setstyleflg(req, CmpOrg.STYLEFLG_Y);
		return null;
	}
}
