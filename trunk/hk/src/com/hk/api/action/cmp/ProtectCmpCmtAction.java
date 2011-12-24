package com.hk.api.action.cmp;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.api.util.SessionKey;
import com.hk.bean.ApiUser;
import com.hk.bean.CmpComment;
import com.hk.bean.Company;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpCommentService;
import com.hk.svr.CompanyService;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.web.util.HkWebConfig;

// @Component("/pubapi/protect/cmt")
public class ProtectCmpCmtAction extends BaseApiAction {

	@Autowired
	private CmpCommentService cmpCommentService;

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String create(HkRequest req, HkResponse resp) throws Exception {
		SessionKey o = this.getSessionKey(req);
		long companyId = req.getLong("companyId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		String content = req.getString("content");
		int code = CmpComment.validateContent(content);
		if (code != Err.SUCCESS) {
			APIUtil.sendRespStatus(resp, "fail", code);
			return null;
		}
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		CmpComment cmt = new CmpComment();
		cmt.setCompanyId(companyId);
		cmt.setContent(labaInfo.getParsedContent());
		cmt.setUserId(o.getUserId());
		ApiUser apiUser = this.getApiUser(req);
		cmt.setSendfrom(apiUser.getApiUserId());
		this.cmpCommentService.createCmpComment(cmt);
		VelocityContext context = new VelocityContext();
		context.put("code", Err.SUCCESS);
		context.put("status", "ok");
		context.put("cmtcreate", true);
		context.put("cmtId", cmt.getCmtId());
		this.write(resp, "vm/validate.vm", context);
		return null;
	}

	/**
	 * 只能删除自己的留言,或者管理员删除或者企业所有者删除
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delete(HkRequest req, HkResponse resp) throws Exception {
		SessionKey o = this.getSessionKey(req);
		long companyId = req.getLong("companyId");
		long cmtId = req.getLong("cmtId");
		CmpComment cmpComment = this.cmpCommentService.getCmpComment(companyId,
				cmtId);
		if (cmpComment != null) {
			Company company = this.companyService.getCompany(companyId);
			if (cmpComment.getUserId() == o.getUserId()
					|| company.getUserId() == o.getUserId()) {
				this.cmpCommentService.deleteCmpComment(companyId, cmtId);
				VelocityContext context = new VelocityContext();
				context.put("code", Err.SUCCESS);
				context.put("status", "ok");
				context.put("deletecmt", true);
				this.write(resp, "vm/validate.vm", context);
				return null;
			}
		}
		VelocityContext context = new VelocityContext();
		context.put("code", Err.NOOBJECT_ERROR);
		context.put("status", "fail");
		this.write(resp, "vm/validate.vm", context);
		return null;
	}
}