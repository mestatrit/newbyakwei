package com.hk.web.pub.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.AuthorApply;
import com.hk.bean.AuthorTag;
import com.hk.bean.UserAuthorTag;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.mail.MailUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.AuthorTagService;

@Component("/admin/authorapply")
public class AdminAuthorApplyAction extends BaseAction {
	@Autowired
	private AuthorTagService authorTagService;

	@Autowired
	private MailUtil mailUtil;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		AuthorApply o = this.authorTagService.getAuthorApply(oid);
		UserAuthorTag userAuthorTag = this.authorTagService
				.getUserAuthorTagByUserId(o.getUserId());
		if (userAuthorTag != null) {
			AuthorTag authorTag = this.authorTagService
					.getAuthorTag(userAuthorTag.getTagId());
			req.setAttribute("authorTag", authorTag);
		}
		req.setAttribute("o", o);
		req.setAttribute("oid", oid);
		req.reSetAttribute("search_checkflg");
		return this.getWapJsp("admin/authorapply.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		byte checkflg = req.getByte("checkflg");
		SimplePage page = req.getSimplePage(this.size20);
		String name = req.getString("name");
		List<AuthorApply> list = this.authorTagService.getAuthorApplyList(name,
				checkflg, page.getBegin(), this.size20);
		req.setAttribute("list", list);
		req.setAttribute("checkflg", checkflg);
		req.setEncodeAttribute("name", name);
		return this.getWapJsp("admin/authorapplylist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String check(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		AuthorApply apply = this.authorTagService.getAuthorApply(oid);
		String not = req.getString("not");
		String fail = req.getString("fail");
		String ok = req.getString("ok");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String mail = apply.getEmail();
		String tag_name = DataUtil.toHtmlRow(req.getString("tag_name"));
		String edit = req.getString("edit");
		if (not != null) {
			this.authorTagService.checkAuthorApply(oid, AuthorApply.CHECKFLG_N,
					tag_name);
		}
		else if (fail != null) {
			this.authorTagService.checkAuthorApply(oid,
					AuthorApply.CHECKFLG_FAIL, tag_name);
			String title = req.getText("func.mail.authortag.checkfail.title");
			String content = req.getText(
					"func.mail.authortag.checkfail.content", sdf
							.format(new Date()));
			this.mailUtil.sendHtmlMail(mail, title, content);
		}
		else if (ok != null) {
			this.authorTagService.checkAuthorApply(oid, AuthorApply.CHECKFLG_Y,
					tag_name);
			String title = req.getText("func.mail.authortag.checkok.title");
			String content = req.getText("func.mail.authortag.checkok.content",
					sdf.format(new Date()));
			this.mailUtil.sendHtmlMail(mail, title, content);
		}
		else if (edit != null) {
			this.authorTagService.deleteUserAuthorTag(apply.getUserId());
			this.authorTagService.createUserAuthorTag(apply.getUserId(),
					tag_name);
		}
		req.setSessionText("op.exeok");
		return "/admin/authorapply_list.do?checkflg="
				+ req.getByte("search_checkflg");
	}
}