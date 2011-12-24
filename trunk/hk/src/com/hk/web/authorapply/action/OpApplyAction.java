package com.hk.web.authorapply.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.AuthorApply;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.AuthorTagService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

@Component("/op/authorapply")
public class OpApplyAction extends BaseAction {
	@Autowired
	private AuthorTagService authorTagService;

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
	public String toedit(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		AuthorApply o = (AuthorApply) req.getAttribute("o");
		if (o == null) {
			o = this.authorTagService.getAuthorApplyByUserId(loginUser
					.getUserId());
		}
		if (o == null) {
			return null;
		}
		req.setAttribute("o", o);
		return this.getWapJsp("authorapply/edit.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String edit(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		AuthorApply o = this.authorTagService.getAuthorApplyByUserId(loginUser
				.getUserId());
		if (o == null) {
			return null;
		}
		o.setUserId(loginUser.getUserId());
		o.setBlog(DataUtil.toHtmlRow(req.getString("blog")));
		o.setEmail(DataUtil.toHtmlRow(req.getString("email")));
		o.setName(DataUtil.toHtmlRow(req.getString("name")));
		o.setTel(DataUtil.toHtmlRow(req.getString("tel")));
		o.setContent(DataUtil.toHtml(req.getString("content")));
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setAttribute("o", o);
			req.setText(code + "");
			return "/op/authorapply_toedit.do";
		}
		req.setSessionText("op.submitinfook");
		return "r:/more.do";
	}
}