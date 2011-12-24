package com.hk.web.pub.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.HkObj;
import com.hk.bean.HkObjArticle;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.mail.MailUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.HkObjArticleService;
import com.hk.svr.HkObjService;
import com.hk.web.util.HkWebConfig;

@Component("/admin/article")
public class AdminArticleAction extends BaseAction {
	@Autowired
	private HkObjArticleService hkObjArticleService;

	@Autowired
	private HkObjService hkObjService;

	@Autowired
	private MailUtil mailUtil;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long articleId = req.getLong("articleId");
		HkObjArticle o = this.hkObjArticleService.getHkObjArticle(articleId);
		req.setAttribute("o", o);
		req.setAttribute("articleId", articleId);
		req.reSetAttribute("search_checkflg");
		return this.getWapJsp("admin/article.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		SimplePage page = req.getSimplePage(this.size20);
		byte checkflg = req.getByte("checkflg");
		List<HkObjArticle> list = this.hkObjArticleService.getHkObjArticleList(
				checkflg, page.getBegin(), this.size20);
		page.setListSize(list.size());
		req.setAttribute("list", list);
		req.setAttribute("checkflg", checkflg);
		return this.getWapJsp("admin/articlelist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String check(HkRequest req, HkResponse resp) throws Exception {
		long articleId = req.getLong("articleId");
		String article_title = req.getString("article_title");
		HkObjArticle article = this.hkObjArticleService
				.getHkObjArticle(articleId);
		HkObj hkObj = this.hkObjService.getHkObj(article.getHkObjId());
		String mail = article.getEmail();
		String not = req.getString("not");
		String fail = req.getString("fail");
		String ok = req.getString("ok");
		String edit = req.getString("edit");
		String ok_edit = req.getString("ok_edit");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (not != null) {
			this.hkObjArticleService.checkHkObjArticle(articleId,
					HkObjArticle.CHECKFLG_N);
		}
		else if (fail != null) {
			this.hkObjArticleService.checkHkObjArticle(articleId,
					HkObjArticle.CHECKFLG_FAIL);
			String title = req.getText("func.mail.article.checkfail.title",
					article.getTitle());
			String content = req.getText("func.mail.article.checkfail.content",
					article.getTitle(), "url", sdf.format(new Date()));
			this.mailUtil.sendHtmlMail(mail, title, content);
		}
		else if (ok != null || ok_edit != null) {
			if (ok_edit != null) {
				if (!DataUtil.isEmpty(article_title)) {
					article.setTitle(DataUtil.toHtmlRow(article_title));
					this.hkObjArticleService.updateHkObjArticle(article);
				}
			}
			this.hkObjArticleService.checkHkObjArticle(articleId,
					HkObjArticle.CHECKFLG_Y);
			String title = req.getText("func.mail.article.checkok.title",
					article.getTitle());
			String url = "http://" + HkWebConfig.getWebDomain()
					+ "/e/cmp.do?companyId=" + hkObj.getObjId();
			String content = req.getText("func.mail.article.checkok.content",
					article.getTitle(), hkObj.getName(), url, sdf
							.format(new Date()));
			this.mailUtil.sendHtmlMail(mail, title, content);
		}
		else if (edit != null) {
			if (!DataUtil.isEmpty(article_title)) {
				article.setTitle(DataUtil.toHtmlRow(article_title));
				this.hkObjArticleService.updateHkObjArticle(article);
			}
		}
		req.setSessionText("op.exeok");
		return "r:/admin/article_list.do?checkflg="
				+ req.getByte("search_checkflg");
	}
}