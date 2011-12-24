package web.epp.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;
import web.pub.util.LabaParser;
import web.pub.util.WebUtil;

import com.hk.bean.Company;
import com.hk.bean.CompanyReview;
import com.hk.bean.Laba;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.LabaService;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.pub.Err;
import com.hk.web.company.action.CompanyReviewVo;
import com.hk.web.util.HkWebConfig;

@Component("/epp/review")
public class ReviewAction extends EppBaseAction {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private LabaService labaService;

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
		com.hk.bean.User loginUser = this.getLoginUser2(req);
		long noUserId = 0;
		if (loginUser != null) {
			noUserId = loginUser.getUserId();
		}
		SimplePage page = req.getSimplePage(20);
		List<CompanyReview> list = this.companyService
				.getCompanyReviewListByCompanyIdNoUser(companyId, noUserId,
						page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		if (loginUser != null) {
			List<CompanyReview> ulist = this.companyService
					.getCompanyReviewListByUserId(loginUser.getUserId(), 0, 1);
			if (ulist.size() > 0) {
				list.addAll(ulist);
				CompanyReview companyReview = ulist.get(0);
				req.setAttribute("companyReview", companyReview);
				req.setAttribute("userscore", companyReview.getScore());
			}
		}
		for (CompanyReview o : list) {
			String c = LabaParser.parseContent(WebUtil.getUrlInfo(req), o
					.getContent());
			o.setContent(c);
		}
		List<CompanyReviewVo> volist = CompanyReviewVo.createVoListForAPI(list);
		req.setAttribute("volist", volist);
		return this.getWapPath(req, "review/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		com.hk.bean.User loginUser = this.getLoginUser2(req);
		if (loginUser != null) {
			int score = req.getInt("score");
			String content = req.getString("content");
			String ip = req.getRemoteAddr();
			CompanyReview companyReview = new CompanyReview();
			companyReview.setScore(score);
			companyReview.setCompanyId(companyId);
			companyReview.setUserId(loginUser.getUserId());
			req.setAttribute("companyReview", companyReview);
			int code = Laba.validateContent(content);
			if (code != Err.SUCCESS) {
				req.setText(String.valueOf(code));
				return "/epp/review_tocreate.do";
			}
			LabaInPutParser parser = new LabaInPutParser(HkWebConfig
					.getShortUrlDomain());
			Company company = this.companyService.getCompany(companyId);
			LabaInfo labaInfo = parser.parse(content);
			if (DataUtil.validateIp(ip)) {
				labaInfo.setIp(ip);
			}
			String reviewContent = labaInfo.getParsedContent();
			String reviewLongContent = labaInfo.getLongParsedContent();
			String add = "对{[" + companyId + "," + company.getName() + "}说:";
			labaInfo.setParsedContent(add + labaInfo.getParsedContent());
			if (labaInfo.getLongParsedContent() != null) {
				labaInfo.setLongParsedContent(add
						+ labaInfo.getLongParsedContent());
			}
			labaInfo.setUserId(loginUser.getUserId());
			labaInfo.setSendFrom(Laba.SENDFROM_WAP);
			long labaId = this.labaService.createLaba(labaInfo);
			companyReview.setLabaId(labaId);
			companyReview.setSendFrom(labaInfo.getSendFrom());
			companyReview.setContent(reviewContent);
			companyReview.setLongContent(reviewLongContent);
			this.companyService.createCompanyReview(companyReview);
			req.setSessionText("func.mgrsite.review.create_ok");
		}
		return "r:/epp/review_list.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toupdate(HkRequest req, HkResponse resp) throws Exception {
		if (this.isNotLogin(req)) {
			return this.getLoginPath(req);
		}
		long companyId = req.getLong("companyId");
		long labaId = req.getLong("labaId");
		com.hk.bean.User loginUser = this.getLoginUser2(req);
		if (loginUser != null) {
			CompanyReview companyReview = this.companyService
					.getCompanyReview(labaId);
			if (companyReview != null) {
				companyReview.setContent(LabaParser.parseContent(null,
						companyReview.getContent()));
			}
			req.setAttribute("companyReview", companyReview);
		}
		req.setAttribute("labaId", labaId);
		req.setAttribute("companyId", companyId);
		return this.getWapPath(req, "review/update.jsp");
	}

	/**
	 * 写新评论
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tocreate(HkRequest req, HkResponse resp) throws Exception {
		if (this.isNotLogin(req)) {
			return this.getLoginPath(req);
		}
		com.hk.bean.User loginUser = this.getLoginUser2(req);
		List<CompanyReview> ulist = this.companyService
				.getCompanyReviewListByUserId(loginUser.getUserId(), 0, 1);
		if (ulist.size() > 0) {
			CompanyReview companyReview = ulist.get(0);
			req.setAttribute("userscore", companyReview.getScore());
		}
		return this.getWapPath(req, "review/create.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		com.hk.bean.User loginUser = this.getLoginUser2(req);
		if (loginUser != null) {
			long labaId = req.getLong("labaId");
			String content = req.getString("content");
			if (content != null) {
				content = DataUtil.limitLength(content, 500);
			}
			int score = req.getInt("score");
			CompanyReview o = this.companyService.getCompanyReview(labaId);
			if (o == null) {
				return null;
			}
			o.setScore(score);
			int code = Laba.validateContent(content);
			if (code != Err.SUCCESS) {
				req.setText(String.valueOf(code));
				return "/epp/review_toupdate.do";
			}
			Company company = this.companyService.getCompany(o.getCompanyId());
			LabaInPutParser parser = new LabaInPutParser(HkWebConfig
					.getShortUrlDomain());
			LabaInfo labaInfo = parser.parse(content);
			labaInfo.setUserId(loginUser.getUserId());
			String longContent = null;
			// 更新评论内容
			o.setContent(labaInfo.getParsedContent());
			o.setLongContent(labaInfo.getLongParsedContent());
			String add = "对{[" + o.getCompanyId() + "," + company.getName()
					+ "}说:";
			String scontent = add + labaInfo.getParsedContent();
			if (!DataUtil.isEmpty(labaInfo.getLongParsedContent())) {
				longContent = add + labaInfo.getLongParsedContent();
			}
			this.labaService.updateLaba(labaId, scontent, longContent);
			this.companyService.updateCompanyReview(o);
			req.setSessionText("func.mgrsite.review.update_ok");
		}
		return "r:/epp/review_list.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long labaId = req.getLong("labaId");
		Laba laba = this.labaService.getLaba(labaId);
		com.hk.bean.User user = this.getLoginUser2(req);
		if (laba != null && laba.getUserId() == user.getUserId()) {
			this.companyService.deleteCompanyReview(labaId);
		}
		req.setSessionText("func.mgrsite.review.delete_ok");
		return "r:/epp/review_list.do?companyId=" + companyId;
	}
}