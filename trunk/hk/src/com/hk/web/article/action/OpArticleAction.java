package com.hk.web.article.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.AuthorApply;
import com.hk.bean.Company;
import com.hk.bean.HkObj;
import com.hk.bean.HkObjArticle;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.mail.MailUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.AuthorTagService;
import com.hk.svr.CompanyService;
import com.hk.svr.HkObjArticleService;
import com.hk.svr.HkObjService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

@Component("/op/article")
public class OpArticleAction extends BaseAction {
	@Autowired
	private HkObjArticleService hkObjArticleService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private HkObjService hkObjService;

	@Autowired
	private AuthorTagService authorTagService;

	@Autowired
	private MailUtil mailUtil;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String my(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		SimplePage page = req.getSimplePage(this.size20);
		List<HkObjArticle> list = this.hkObjArticleService
				.getHkObjArticleListByUserId(loginUser.getUserId(), page
						.getBegin(), this.size20);
		page.setListSize(list.size());
		req.setAttribute("list", list);
		return this.getWapJsp("article/my.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toedit(HkRequest req, HkResponse resp) throws Exception {
		long articleId = req.getLong("articleId");
		HkObjArticle o = (HkObjArticle) req.getAttribute("o");
		if (o == null) {
			o = this.hkObjArticleService.getHkObjArticle(articleId);
		}
		if (o == null) {
			return null;
		}
		if (o.isCheckOk()) {
			req.setText("此文章已经审核成功，不能修改");
		}
		HkObj hkObj = this.hkObjService.getHkObj(o.getHkObjId());
		req.setAttribute("o", o);
		req.setAttribute("hkObj", hkObj);
		req.setAttribute("articleId", articleId);
		return this.getWapJsp("article/edit.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String edit(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		long articleId = req.getLong("articleId");
		HkObjArticle o = this.hkObjArticleService.getHkObjArticle(articleId);
		if (o == null) {
			return null;
		}
		if (o.getUserId() != loginUser.getUserId()) {
			return null;
		}
		if (o.isCheckOk()) {
			req.setText("此文章已经审核成功，不能修改");
		}
		else {
			o.setUserId(loginUser.getUserId());
			o.setTel(DataUtil.toHtmlRow(req.getString("tel")));
			o.setEmail(DataUtil.toHtmlRow(req.getString("email")));
			o.setBlog(DataUtil.toHtmlRow(req.getString("blog")));
			o.setAuthor(DataUtil.toHtmlRow(req.getString("author")));
			o.setUrl(DataUtil.toHtmlRow(req.getString("url")));
			o.setTitle(DataUtil.toHtmlRow(req.getString("title")));
			int code = o.validate();
			if (code != Err.SUCCESS) {
				req.setAttribute("o", o);
				req.setText(code + "");
				return "/op/article_toedit.do?articleId=" + articleId;
			}
			this.hkObjArticleService.updateHkObjArticle(o);
			req.setSessionText("op.submitinfook");
		}
		return "r:/op/article_my.do?page=" + req.getInt("repage");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		long articleId = req.getLong("articleId");
		HkObjArticle o = this.hkObjArticleService.getHkObjArticle(articleId);
		if (o == null) {
			return null;
		}
		if (o.getUserId() != loginUser.getUserId()) {
			return null;
		}
		this.hkObjArticleService.deleteHkObjArticle(articleId);
		req.setSessionText("op.submitinfook");
		return "r:/op/article_my.do?page=" + req.getInt("repage");
	}

	/**
	 * 到提交文章页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toadd(HkRequest req, HkResponse resp) throws Exception {
		long hkObjId = req.getLong("hkObjId");
		Company company = this.companyService.getCompany(hkObjId);
		User loginUser = this.getLoginUser(req);
		HkObjArticle article = this.hkObjArticleService
				.getUserLastHkObjArticle(loginUser.getUserId());
		req.setAttribute("article", article);
		req.setAttribute("company", company);
		req.setAttribute("hkObjId", hkObjId);
		req.setAttribute("widthauthortag", true);
		return this.getWebJsp("article/add.jsp");
	}

	/**
	 * 提交文章
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String add(HkRequest req, HkResponse resp) throws Exception {
		long hkObjId = req.getLong("hkObjId");
		HkObj hkObj = this.hkObjService.getHkObj(hkObjId);
		if (hkObj == null) {
			return null;
		}
		byte authorflg = req.getByte("authorflg", (byte) -1);
		User loginUser = this.getLoginUser(req);
		HkObjArticle o = new HkObjArticle();
		o.setHkObjId(hkObjId);
		o.setUserId(loginUser.getUserId());
		o.setTel(DataUtil.toHtmlRow(req.getString("tel")));
		o.setEmail(DataUtil.toHtmlRow(req.getString("email")));
		o.setBlog(DataUtil.toHtmlRow(req.getString("blog")));
		o.setAuthor(DataUtil.toHtmlRow(req.getString("author")));
		o.setUrl(DataUtil.toHtmlRow(req.getString("url")));
		o.setTitle(DataUtil.toHtmlRow(req.getString("title")));
		o.setAuthorflg(authorflg);
		int code = o.validate();
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "add_article");
		}
		if (o.getUrl() != null
				&& o.getUrl().toLowerCase().startsWith("http://")) {
			String url = o.getUrl().substring(7);
			o.setUrl(url);
		}
		if (o.getBlog() != null
				&& o.getBlog().toLowerCase().startsWith("http://")) {
			String url = o.getBlog().substring(7);
			o.setBlog(url);
		}
		int authortagflg = req.getInt("authortagflg");
		AuthorApply apply = null;
		if (authortagflg == 1) {
			apply = new AuthorApply();
			apply.setUserId(loginUser.getUserId());
			apply
					.setBlog(DataUtil.toHtmlRow(req
							.getString("authorapply_blog")));
			apply.setEmail(DataUtil.toHtmlRow(req
					.getString("authorapply_email")));
			apply.setName(DataUtil.toHtmlRow(req.getString("name")));
			apply.setTel(DataUtil.toHtmlRow(req.getString("authorapply_tel")));
			apply.setContent(DataUtil.toHtml(req.getString("content")));
			code = apply.validate();
			if (code != Err.SUCCESS) {
				return this.initError(req, code, "add_article");
			}
			if (apply.getBlog() != null
					&& apply.getBlog().toLowerCase().startsWith("http://")) {
				String url = apply.getBlog().substring(7);
				apply.setBlog(url);
			}
		}
		this.hkObjArticleService.createHkObjArticle(o);
		if (authortagflg == 1) {
			this.authorTagService.createAuthorApply(apply);
			String mail_title = req.getText("view.authorapply.mail.title",
					hkObj.getName());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String mail_content = req.getText("view.authorapply.mail.content",
					hkObj.getName(), sdf.format(new Date()));
			mailUtil.sendHtmlMail(o.getEmail(), mail_title, mail_content);
		}
		return this.initSuccess(req, "add_article");
	}

	/**
	 * 提交文章成功后，到信息提示页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addsuccess(HkRequest req, HkResponse resp) throws Exception {
		long hkObjId = req.getLong("hkObjId");
		req.setAttribute("hkObjId", hkObjId);
		return this.getWebJsp("article/addsuccess.jsp");
	}
}