package web.epp.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;
import web.pub.util.LabaParser;
import web.pub.util.WebUtil;

import com.hk.bean.CmpComment;
import com.hk.bean.Company;
import com.hk.bean.Laba;
import com.hk.bean.User;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpCommentService;
import com.hk.svr.CompanyService;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.pub.Err;
import com.hk.web.company.action.CmpCommentVo;
import com.hk.web.util.HkWebConfig;

@Component("/epp/cmt")
public class CmtAction extends EppBaseAction {

	@Autowired
	private CmpCommentService cmpCommentService;

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		SimplePage page = ServletUtil.getSimplePage(req, 20);
		List<com.hk.bean.CmpComment> list = cmpCommentService
				.getCmpCommentList(companyId, page.getBegin(),
						page.getSize() + 1);
		this.processListForPage(page, list);
		for (com.hk.bean.CmpComment o : list) {
			String c = LabaParser.parseContent(WebUtil.getUrlInfo(req), o
					.getContent());
			o.setContent(c);
		}
		List<CmpCommentVo> volist = CmpCommentVo.createVoList(list, null, true);
		req.setAttribute("volist", volist);
		return this.getWapPath(req, "cmt/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		User loginUser = this.getLoginUser2(req);
		if (loginUser != null) {
			String content = req.getString("content");
			int code = CmpComment.validateContent(content);
			if (code != Err.SUCCESS) {
				req.setText(String.valueOf(code));
				return "/epp/cmt_list.do";
			}
			LabaInPutParser parser = new LabaInPutParser(HkWebConfig
					.getShortUrlDomain());
			LabaInfo labaInfo = parser.parse(content);
			CmpComment cmt = new CmpComment();
			cmt.setCompanyId(companyId);
			cmt.setContent(labaInfo.getParsedContent());
			cmt.setUserId(loginUser.getUserId());
			cmt.setSendfrom(Laba.SENDFROM_WAP);
			this.cmpCommentService.createCmpComment(cmt);
		}
		return "r:/epp/cmt_list.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long cmtId = req.getLong("cmtId");
		long companyId = req.getLong("companyId");
		User loginUser = this.getLoginUser2(req);
		if (loginUser != null) {
			CmpComment cmpComment = this.cmpCommentService.getCmpComment(
					companyId, cmtId);
			if (cmpComment != null) {
				Company company = this.companyService.getCompany(companyId);
				if (cmpComment.getUserId() == loginUser.getUserId()
						|| company.getUserId() == loginUser.getUserId()) {
					this.cmpCommentService.deleteCmpComment(companyId, cmtId);
				}
			}
			req.setSessionText("func.mgrsite.cmt.delete_ok");
		}
		return "r:/epp/cmt_list.do?companyId=" + companyId;
	}
}