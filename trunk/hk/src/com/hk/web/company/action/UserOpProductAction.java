package com.hk.web.company.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpProduct;
import com.hk.bean.CmpProductReview;
import com.hk.bean.CmpProductUserStatus;
import com.hk.bean.Company;
import com.hk.bean.CompanyUserScore;
import com.hk.bean.Laba;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpProductService;
import com.hk.svr.CompanyService;
import com.hk.svr.LabaService;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.HkWebConfig;

@Component("/op/product")
public class UserOpProductAction extends BaseAction {

	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private LabaService labaService;

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 加载点评数据
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String loadreviewforedit(HkRequest req, HkResponse resp)
			throws Exception {
		long labaId = req.getLongAndSetAttr("labaId");
		CmpProductReview review = this.cmpProductService
				.getCmpProductReview(labaId);
		CmpProductReviewVo cmpProductReviewVo = CmpProductReviewVo.createVo(
				review, null);
		req.setAttribute("cmpProductReviewVo", cmpProductReviewVo);
		req.setAttribute("pid", review.getProductId());
		return this.getWeb3Jsp("e/product/op/edit_inc.jsp");
	}

	/**
	 * 打分
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createscore(HkRequest req, HkResponse resp) throws Exception {
		long productId = req.getLong("productId");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(productId);
		if (cmpProduct == null) {
			return null;
		}
		User loginUser = this.getLoginUser(req);
		long userId = loginUser.getUserId();
		int score = req.getInt("score");
		if (CompanyUserScore.validateScore(score) == Err.SUCCESS) {
			this.cmpProductService.createCmpProductUserScore(userId, productId,
					score);
		}
		return null;
	}

	/**
	 * 发表评论
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createreview(HkRequest req, HkResponse resp) throws Exception {
		long productId = req.getLong("productId");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(productId);
		if (cmpProduct == null) {
			return null;
		}
		int optflg = req.getInt("optflg");
		User loginUser = this.getLoginUser(req);
		long userId = loginUser.getUserId();
		String content = req.getString("content");
		int score = req.getInt("score");
		if (CompanyUserScore.validateScore(score) != Err.SUCCESS) {
			score = 0;
		}
		if (DataUtil.isEmpty(content) && score != 0) {
			this.cmpProductService.createCmpProductUserScore(userId, productId,
					score);
			req.setSessionText("func.cmpproductreview.setscoreok");
			if (optflg == 1) {
				return this.onSuccess2(req, "opcreatereview2success", null);
			}
			return this.initSuccess(req, "addreview", "oncmpproductreviewok",
					null);
		}
		// content = DataUtil.toHtmlRow(content);
		int code = Laba.validateContent(content);
		if (code != Err.SUCCESS) {
			if (optflg == 1) {
				return this.onError(req, code, "opcreatereview2error", null);
			}
			return this.initError(req, code, -1, null, "addreview",
					"oncmpproductreviewerror", null);
		}
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setUserId(userId);
		labaInfo.setSendFrom(Laba.SENDFROM_SMS);
		labaInfo.setProductId(productId);
		long labaId = this.labaService.createLaba(labaInfo);
		CmpProductReview o = new CmpProductReview();
		o.setUserId(userId);
		o.setContent(labaInfo.getParsedContent());
		o.setLongContent(labaInfo.getLongParsedContent());
		o.setScore(score);
		o.setProductId(productId);
		o.setLabaId(labaId);
		o.setSendFrom(labaInfo.getSendFrom());
		o.setCompanyId(cmpProduct.getCompanyId());
		this.cmpProductService.createReview(o);
		this.setOpFuncSuccessMsg(req);
		if (optflg == 1) {
			return this.onSuccess2(req, "opcreatereview2success", null);
		}
		return this.initSuccess(req, "addreview", "oncmpproductreviewok", null);
	}

	/**
	 * 修改评论
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editreview(HkRequest req, HkResponse resp) throws Exception {
		long productId = req.getLong("productId");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(productId);
		if (cmpProduct == null) {
			return null;
		}
		long labaId = req.getLong("labaId");
		User loginUser = this.getLoginUser(req);
		long userId = loginUser.getUserId();
		String content = req.getString("content");
		int score = req.getInt("score");
		if (CompanyUserScore.validateScore(score) != Err.SUCCESS) {
			score = 0;
		}
		if (DataUtil.isEmpty(content) && score != 0) {
			this.cmpProductService.createCmpProductUserScore(userId, productId,
					score);
			req.setSessionText("func.cmpproductreview.setscoreok");
			return this.initSuccess(req, "addreview", "oncmpproductreviewok",
					null);
		}
		int code = Laba.validateContent(content);
		if (code != Err.SUCCESS) {
			return this.initError(req, code, -1, null, "editreview",
					"oncmpproductreviewerror", null);
		}
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setUserId(userId);
		labaInfo.setSendFrom(Laba.SENDFROM_SMS);
		this.labaService.updateLaba(labaId, labaInfo.getParsedContent(),
				labaInfo.getLongParsedContent());
		CmpProductReview o = this.cmpProductService.getCmpProductReview(labaId);
		o.setUserId(userId);
		o.setContent(labaInfo.getParsedContent());
		o.setLongContent(labaInfo.getLongParsedContent());
		o.setScore(score);
		o.setSendFrom(labaInfo.getSendFrom());
		this.cmpProductService.updateReview(o);
		CmpProductReview review = this.cmpProductService
				.getCmpProductReview(labaId);
		CmpProductReviewVo vo = CmpProductReviewVo.createVo(review, this
				.getUrlInfoWeb(req));
		req.setAttribute("reviewvo", vo);
		return this.getWeb3Jsp("e/product/op/updateview_inc.jsp");
	}

	/**
	 * 收藏产品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String fav(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		int pid = req.getInt("pid");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(pid);
		this.cmpProductService.favProduct(loginUser.getUserId(), pid,
				cmpProduct.getCompanyId());
		return null;
	}

	/**
	 * 收藏产品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String favwap(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		int pid = req.getInt("pid");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(pid);
		this.cmpProductService.favProduct(loginUser.getUserId(), pid,
				cmpProduct.getCompanyId());
		return "r:/product_wap.do?pid=" + pid;
	}

	/**
	 * 删除点评
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delreview(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		long labaId = req.getLong("labaId");
		CmpProductReview review = this.cmpProductService
				.getCmpProductReview(labaId);
		if (review != null) {
			boolean candel = false;
			if (review.getUserId() == loginUser.getUserId()) {
				candel = true;
			}
			else {
				CmpProduct cmpProduct = this.cmpProductService
						.getCmpProduct(review.getProductId());
				Company company = this.companyService.getCompany(cmpProduct
						.getCompanyId());
				if (company.getUserId() > 0
						&& company.getUserId() == loginUser.getUserId()) {
					candel = true;
				}
				else if (company.getCreaterId() > 0
						&& company.getUserId() == loginUser.getUserId()) {
					candel = true;
				}
			}
			if (candel) {
				this.cmpProductService.deleteReview(labaId);
				req.setSessionText("func.delproductreviewok");
			}
			String return_url = req.getReturnUrl();
			if (DataUtil.isNotEmpty(return_url)) {
				return "r:" + return_url;
			}
			return "r:/product.do?pid=" + review.getProductId();
		}
		return null;
	}

	/**
	 * 取消收藏产品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delfav(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		int pid = req.getInt("pid");
		this.cmpProductService.deleteFavProduct(loginUser.getUserId(), pid);
		return null;
	}

	/**
	 * 取消收藏产品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delfavwap(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		int pid = req.getInt("pid");
		this.cmpProductService.deleteFavProduct(loginUser.getUserId(), pid);
		return "r:/product_wap.do?pid=" + pid;
	}

	/**
	 * 使用过该产品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String done(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		int pid = req.getInt("pid");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(pid);
		if (cmpProduct == null) {
			return null;
		}
		this.cmpProductService.createCmpProductUserStatus(
				loginUser.getUserId(), pid,
				CmpProductUserStatus.USERSTATUS_DONE);
		if (this.cmpProductService.hasReviewed(loginUser.getUserId(), pid)) {
			resp.sendHtml(1);
		}
		resp.sendHtml(0);
		return null;
	}

	/**
	 * 想使用该产品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String want(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		int pid = req.getInt("pid");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(pid);
		if (cmpProduct == null) {
			return null;
		}
		this.cmpProductService.createCmpProductUserStatus(
				loginUser.getUserId(), pid,
				CmpProductUserStatus.USERSTATUS_WANT);
		return null;
	}
}