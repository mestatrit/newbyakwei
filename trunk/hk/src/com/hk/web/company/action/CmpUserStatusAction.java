package com.hk.web.company.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Company;
import com.hk.bean.CompanyUserStatus;
import com.hk.bean.User;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.web.pub.action.BaseAction;

@Component("/cmpuserstatus/op/op")
public class CmpUserStatusAction extends BaseAction {

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		User loginUser = this.getLoginUser(req);
		byte status = 0;
		if (req.getString("done") != null) {
			status = CompanyUserStatus.USERSTATUS_DONE;
		}
		else {
			status = CompanyUserStatus.USERSTATUS_WANT;
		}
		this.companyService.createCompanyUserStatus(companyId, loginUser
				.getUserId(), status);
		req.setSessionText("op.exeok");
		return "r:/e/cmp.do?companyId=" + companyId;
	}

	/**
	 * 删除去过想去
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		User loginUser = this.getLoginUser(req);
		byte status = req.getByte("status");
		CompanyUserStatus companyUserStatus = this.companyService
				.getCompanyUserStatus(companyId, loginUser.getUserId());
		if (companyUserStatus != null) {
			if (status == CompanyUserStatus.USERSTATUS_DONE) {
				companyUserStatus.setDoneStatus(CompanyUserStatus.NONE_FLG);
			}
			else {
				companyUserStatus.setUserStatus(CompanyUserStatus.NONE_FLG);
			}
			this.companyService.updateCompanyUserStatus(companyUserStatus);
		}
		return "r:/e/cmp.do?companyId=" + companyId;
	}

	public String web(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		User loginUser = this.getLoginUser(req);
		byte status = req.getByte("status");
		if (status != CompanyUserStatus.USERSTATUS_DONE
				&& status != CompanyUserStatus.USERSTATUS_WANT) {
			return null;
		}
		this.companyService.createCompanyUserStatus(companyId, loginUser
				.getUserId(), status);
		resp.sendHtml("0");// 操作完成，提交响应
		return null;
	}
}