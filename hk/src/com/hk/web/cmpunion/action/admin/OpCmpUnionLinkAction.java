package com.hk.web.cmpunion.action.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpUnionLink;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpUnionService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

@Component("/cmpunion/op/link")
public class OpCmpUnionLinkAction extends BaseAction {
	@Autowired
	private CmpUnionService cmpUnionService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLongAndSetAttr("uid");
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.cmpUnionService.countCmpUnionLinkByUid(uid));
		List<CmpUnionLink> list = this.cmpUnionService
				.getCmpUnionLinkListByUid(uid, page.getBegin(), page
						.getSize());
		req.setAttribute("list", list);
		return this.getWeb3Jsp("unionadmin/link/linklist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String loadlink(HkRequest req, HkResponse resp) throws Exception {
		long linkId = req.getLongAndSetAttr("linkId");
		CmpUnionLink cmpUnionLink = this.cmpUnionService
				.getCmpUnionLink(linkId);
		req.setAttribute("o", cmpUnionLink);
		req.reSetAttribute("uid");
		return this.getWeb3Jsp("unionadmin/link/edit_inc.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLong("uid");
		long linkId = req.getLong("linkId");
		CmpUnionLink cmpUnionLink = this.cmpUnionService
				.getCmpUnionLink(linkId);
		if (cmpUnionLink == null || cmpUnionLink.getUid() != uid) {
			return null;
		}
		this.cmpUnionService.deleteCmpUnionLink(linkId);
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		long linkId = req.getLong("linkId");
		String title = req.getString("title");
		String url = req.getString("url");
		CmpUnionLink cmpUnionLink = this.cmpUnionService
				.getCmpUnionLink(linkId);
		cmpUnionLink.setTitle(DataUtil.toHtmlRow(title));
		cmpUnionLink.setUrl(DataUtil.toHtmlRow(url));
		int code = cmpUnionLink.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "linkerror", null);
		}
		this.cmpUnionService.updateCmpUnionLink(cmpUnionLink);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "linkok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLong("uid");
		String title = req.getString("title");
		String url = req.getString("url");
		CmpUnionLink cmpUnionLink = new CmpUnionLink();
		cmpUnionLink.setUid(uid);
		cmpUnionLink.setTitle(DataUtil.toHtmlRow(title));
		cmpUnionLink.setUrl(DataUtil.toHtmlRow(url));
		int code = cmpUnionLink.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "linkerror", null);
		}
		this.cmpUnionService.createCmpUnionLink(cmpUnionLink);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "linkok", null);
	}
}