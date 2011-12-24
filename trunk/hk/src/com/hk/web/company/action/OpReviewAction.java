package com.hk.web.company.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.Company;
import com.hk.bean.CompanyReview;
import com.hk.bean.CompanyUserScore;
import com.hk.bean.Laba;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.LabaService;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.HkWebConfig;

@Component("/review/op/op")
public class OpReviewAction extends BaseAction {
	@Autowired
	private CompanyService companyService;

	@Autowired
	private LabaService labaService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 足迹打分
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toaddscore(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		long companyId = req.getLong("companyId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		Company company = this.companyService.getCompany(companyId);
		CompanyUserScore companyUserScore = this.companyService
				.getCompanyUserScore(companyId, loginUser.getUserId());
		req.setAttribute("company", company);
		req.setAttribute("companyUserScore", companyUserScore);
		req.setAttribute("companyId", companyId);
		return "/WEB-INF/page/e/op/addscore.jsp";
	}

	/**
	 * 足迹打分
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addscore(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		int score = req.getInt("score");
		int code = CompanyUserScore.validateScore(score);
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/review/op/op_toaddscore.do";
		}
		this.companyService.gradeCompany(loginUser.getUserId(), companyId,
				score);
		req.setSessionText("op.setcompanyscoreok");
		return "r:/e/cmp.do?companyId=" + companyId;
	}

	/**
	 * 企业评论
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String my(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		if (o == null) {
			return null;
		}
		int size = 20;
		SimplePage page = req.getSimplePage(size);
		List<CompanyReview> reviewlist = this.companyService
				.getUserCompanyReviewList(companyId, loginUser.getUserId(),
						page.getBegin(), size);
		page.setListSize(reviewlist.size());
		req.setAttribute("vo", new CompanyVo(o));
		req.setAttribute("reviewvolist", CompanyReviewVo.createVoList(
				reviewlist, this.getUrlInfo(req)));
		req.setAttribute("companyId", companyId);
		CompanyUserScore companyUserScore = this.companyService
				.getCompanyUserScore(companyId, loginUser.getUserId());
		req.setAttribute("companyUserScore", companyUserScore);
		return "/WEB-INF/page/e/op/myreview.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toadd(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		User loginUser = this.getLoginUser(req);
		req.reSetAttribute("companyId");
		CompanyUserScore companyUserScore = this.companyService
				.getCompanyUserScore(companyId, loginUser.getUserId());
		req.setAttribute("companyUserScore", companyUserScore);
		return "/WEB-INF/page/e/op/addreview.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String doaddweb(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		String content = req.getString("content");
		// content = DataUtil.toHtmlRow(content);
		int code = Laba.validateContent(content);
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "add_review");
		}
		int score = req.getInt("score");
		if (CompanyUserScore.validateScore(score) != Err.SUCCESS) {
			score = 0;
		}
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		CompanyReview o = new CompanyReview();
		o.setScore(score);
		o.setCompanyId(companyId);
		o.setUserId(loginUser.getUserId());
		req.setAttribute("o", o);
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setIp(req.getRemoteAddr());
		labaInfo.setUserId(loginUser.getUserId());
		labaInfo.setSendFrom(Laba.SENDFROM_WAP);
		String reviewContent = labaInfo.getParsedContent();
		String reviewLongContent = labaInfo.getLongParsedContent();
		String add = "对{[" + o.getCompanyId() + "," + company.getName() + "}说:";
		labaInfo.setParsedContent(add + labaInfo.getParsedContent());
		if (labaInfo.getLongParsedContent() != null) {
			labaInfo
					.setLongParsedContent(add + labaInfo.getLongParsedContent());
		}
		long labaId = this.labaService.createLaba(labaInfo);
		o.setLabaId(labaId);
		o.setSendFrom(labaInfo.getSendFrom());
		o.setContent(reviewContent);
		o.setLongContent(reviewLongContent);
		this.companyService.createCompanyReview(o);
		this.setOpFuncSuccessMsg(req);
		return this.initSuccess(req, "add_review");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String add(HkRequest req, HkResponse resp) throws Exception {
		return this.doaddwap(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String doaddwap(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		int score = req.getInt("score");
		if (CompanyUserScore.validateScore(score) != Err.SUCCESS) {
			score = 0;
		}
		String content = req.getString("content");
		if (DataUtil.isEmpty(content)) {
			return "/review/op/op_addscore.do";
		}
		// content = DataUtil.toHtmlRow(content);
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		CompanyReview o = new CompanyReview();
		o.setScore(score);
		o.setCompanyId(companyId);
		o.setUserId(loginUser.getUserId());
		req.setAttribute("o", o);
		int code = Laba.validateContent(content);
		if (code != Err.SUCCESS) {
			req.setMessage(req.getText(code + ""));
			if ("new".equals("from")) {
				return "/review/op/op_toadd.do";
			}
			return "/e/cmp.do";
		}
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setIp(req.getRemoteAddr());
		labaInfo.setUserId(loginUser.getUserId());
		labaInfo.setSendFrom(Laba.SENDFROM_WAP);
		if (!this.isPassLabaToken(req, content)) {// 解决重复提交喇叭
			return "r:/square.do";
		}
		String reviewContent = labaInfo.getParsedContent();
		String reviewLongContent = labaInfo.getLongParsedContent();
		String add = "对{[" + o.getCompanyId() + "," + company.getName() + "}说:";
		labaInfo.setParsedContent(add + labaInfo.getParsedContent());
		if (labaInfo.getLongParsedContent() != null) {
			labaInfo
					.setLongParsedContent(add + labaInfo.getLongParsedContent());
		}
		long labaId = this.labaService.createLaba(labaInfo);
		o.setLabaId(labaId);
		o.setSendFrom(labaInfo.getSendFrom());
		o.setContent(reviewContent);
		o.setLongContent(reviewLongContent);
		this.companyService.createCompanyReview(o);
		req.setSessionMessage(req.getText("op.submitinfook"));
		return "r:/e/cmp.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String loadreview(HkRequest req, HkResponse resp) throws Exception {
		CompanyReview o = (CompanyReview) req.getAttribute("o");
		long labaId = req.getLongAndSetAttr("labaId");
		if (o == null) {
			o = this.companyService.getCompanyReview(labaId);
		}
		CompanyReviewVo vo = CompanyReviewVo.createVo(o, null, true);
		req.setAttribute("companyreviewvo", vo);
		return this.getWeb3Jsp("e/review/edit_inc.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toeditweb(HkRequest req, HkResponse resp) throws Exception {
		CompanyReview o = (CompanyReview) req.getAttribute("o");
		long labaId = req.getLong("labaId");
		long companyId = req.getLong("companyId");
		if (o == null) {
			o = this.companyService.getCompanyReview(labaId);
		}
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		CompanyReviewVo vo = CompanyReviewVo.createVo(o, null, true);
		req.setAttribute("companyreviewvo", vo);
		req.reSetAttribute("labaId");
		req.reSetAttribute("companyId");
		return this.getWeb3Jsp("e/review/edit.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toedit(HkRequest req, HkResponse resp) throws Exception {
		CompanyReview o = (CompanyReview) req.getAttribute("o");
		if (o == null) {
			long labaId = req.getLong("labaId");
			o = this.companyService.getCompanyReview(labaId);
		}
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		CompanyReviewVo vo = CompanyReviewVo.createVo(o, null, true);
		req.setAttribute("companyreviewvo", vo);
		req.reSetAttribute("labaId");
		req.reSetAttribute("companyId");
		req.reSetAttribute("repage");
		return "/WEB-INF/page/e/op/editreview.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String edit(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		int score = req.getInt("score");
		String content = req.getString("content");
		if (DataUtil.isEmpty(content)) {
			return "/review/op/op_addscore.do";
		}
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		long labaId = req.getLong("labaId");
		CompanyReview o = this.companyService.getCompanyReview(labaId);
		if (o == null || o.getUserId() != loginUser.getUserId()) {
			return null;
		}
		o.setScore(score);
		o.setContent(content);
		req.setAttribute("companyreviewvo", o);
		int code = Laba.validateContent(content);
		if (code != Err.SUCCESS) {
			req.setMessage(req.getText(code + ""));
			return "/review/op/op_toedit.do";
		}
		String add = "对{[" + o.getCompanyId() + "," + company.getName() + "}说:";
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setIp(req.getRemoteAddr());
		labaInfo.setUserId(loginUser.getUserId());
		labaInfo.setSendFrom(Laba.SENDFROM_WAP);
		String longContent = null;
		// 更新评论内容
		o.setContent(labaInfo.getParsedContent());
		o.setLongContent(labaInfo.getLongParsedContent());
		String scontent = add + labaInfo.getParsedContent();
		if (!DataUtil.isEmpty(labaInfo.getLongParsedContent())) {
			longContent = add + labaInfo.getLongParsedContent();
		}
		this.labaService.updateLaba(labaId, scontent, longContent);
		this.companyService.updateCompanyReview(o);
		req.setSessionMessage(req.getText("op.exeok"));
		return "r:/e/cmp.do?companyId=" + companyId + "#review" + labaId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editweb(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		int score = req.getInt("score");
		String content = req.getString("content");
		long labaId = req.getLong("labaId");
		if (DataUtil.isEmpty(content)) {
			return null;
		}
		CompanyReview o = this.companyService.getCompanyReview(labaId);
		if (o == null || o.getUserId() != loginUser.getUserId()) {
			return null;
		}
		Company company = this.companyService.getCompany(o.getCompanyId());
		if (company == null) {
			return null;
		}
		o.setScore(score);
		o.setContent(content);
		req.setAttribute("companyreviewvo", o);
		int code = Laba.validateContent(content);
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "edit_reviewerror", null);
		}
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setIp(req.getRemoteAddr());
		labaInfo.setUserId(loginUser.getUserId());
		labaInfo.setSendFrom(Laba.SENDFROM_WAP);
		String longContent = null;
		// 更新评论内容
		o.setContent(labaInfo.getParsedContent());
		o.setLongContent(labaInfo.getLongParsedContent());
		String add = "对{[" + o.getCompanyId() + "," + company.getName() + "}说:";
		String scontent = add + labaInfo.getParsedContent();
		if (!DataUtil.isEmpty(labaInfo.getLongParsedContent())) {
			longContent = add + labaInfo.getLongParsedContent();
		}
		this.labaService.updateLaba(labaId, scontent, longContent);
		this.companyService.updateCompanyReview(o);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "edit_reviewok", null);
	}
}