package com.hk.web.company.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.CmpComment;
import com.hk.bean.Laba;
import com.hk.bean.User;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpCommentService;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.HkWebConfig;

@Component("/cmt/op/op")
public class OpCmtAction extends BaseAction {
	@Autowired
	private CmpCommentService cmpCommentService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 删除留言
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String delcmt(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		long companyId = req.getLong("companyId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		long cmtId = req.getLong("cmtId");
		CmpComment cmpComment = this.cmpCommentService.getCmpComment(companyId,
				cmtId);
		if (cmpComment != null) {
			if (cmpComment.getUserId() == loginUser.getUserId()) {
				this.cmpCommentService.deleteCmpComment(companyId, cmtId);
				req.setSessionText("op.delinfook");
			}
		}
		if (req.getInt("f") == 1) {
			return "r:/e/cmp_cmt.do?companyId=" + companyId;
		}
		return "r:/e/cmp.do?companyId=" + companyId;
	}

	/**
	 * 留言
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String addcmt(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		User loginUser = this.getLoginUser(req);
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		String content = req.getString("content");
		int code = CmpComment.validateContent(content);
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/e/cmp.do?companyId=" + companyId;
		}
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		CmpComment cmt = new CmpComment();
		cmt.setCompanyId(companyId);
		cmt.setContent(labaInfo.getParsedContent());
		cmt.setUserId(loginUser.getUserId());
		cmt.setIp(req.getRemoteAddr());
		cmt.setSendfrom(Laba.SENDFROM_WAP);
		this.cmpCommentService.createCmpComment(cmt);
		req.setSessionText("op.submitinfook");
		return "r:/e/cmp.do?companyId=" + companyId;
	}
}