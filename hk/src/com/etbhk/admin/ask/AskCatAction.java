package com.etbhk.admin.ask;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etbhk.util.BaseTaoBaoAction;
import com.hk.bean.taobao.Tb_Ask_Cat;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.Tb_Ask_CatService;
import com.hk.svr.pub.Err;

@Component("/tb/admin/askcat")
public class AskCatAction extends BaseTaoBaoAction {

	@Autowired
	private Tb_Ask_CatService tbAskCatService;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long parent_cid = req.getLongAndSetAttr("parent_cid");
		String name = req.getHtmlRow("name");
		List<Tb_Ask_Cat> list = this.tbAskCatService.getTb_Ask_CatList(
				parent_cid, name);
		req.setAttribute("list", list);
		req.setEncodeAttribute("name", name);
		if (parent_cid > 0) {
			Tb_Ask_Cat parent = this.tbAskCatService.getTb_Ask_Cat(parent_cid);
			req.setAttribute("parent", parent);
		}
		req.setAttribute("askcat_mod", true);
		return this.getAdminJsp("askcat/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-14
	 */
	public String create(HkRequest req, HkResponse resp) {
		req.setAttribute("askcat_mod", true);
		if (this.isForwardPage(req)) {
			long parent_cid = req.getLongAndSetAttr("parent_cid");
			if (parent_cid > 0) {
				Tb_Ask_Cat parent = this.tbAskCatService
						.getTb_Ask_Cat(parent_cid);
				req.setAttribute("parent", parent);
			}
			return this.getAdminJsp("askcat/create.jsp");
		}
		Tb_Ask_Cat tbAskCat = new Tb_Ask_Cat();
		tbAskCat.setParent_cid(req.getLong("parent_cid"));
		tbAskCat.setName(req.getHtmlRow("name"));
		int code = tbAskCat.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerr", null);
		}
		if (!this.tbAskCatService.createTb_Ask_Cat(tbAskCat)) {
			return this.onError(req, Err.TB_ASK_CAT_NAME_DUPLICATE_ERROR,
					"createerr", null);
		}
		return this.onSuccess(req, "createok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-14
	 */
	public String update(HkRequest req, HkResponse resp) {
		req.setAttribute("askcat_mod", true);
		long cid = req.getLongAndSetAttr("cid");
		Tb_Ask_Cat tbAskCat = this.tbAskCatService.getTb_Ask_Cat(cid);
		if (tbAskCat == null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			req.setAttribute("tbAskCat", tbAskCat);
			return this.getAdminJsp("askcat/update.jsp");
		}
		tbAskCat.setName(req.getHtmlRow("name"));
		int code = tbAskCat.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerr", null);
		}
		if (!this.tbAskCatService.updateTb_Ask_Cat(tbAskCat)) {
			return this.onError(req, Err.TB_ASK_CAT_NAME_DUPLICATE_ERROR,
					"createerr", null);
		}
		return this.onSuccess(req, "updateok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-14
	 */
	public String del(HkRequest req, HkResponse resp) {
		long cid = req.getLongAndSetAttr("cid");
		Tb_Ask_Cat tbAskCat = this.tbAskCatService.getTb_Ask_Cat(cid);
		if (tbAskCat == null || tbAskCat.isParent()) {
			return null;
		}
		this.tbAskCatService.deleteTb_Ask_Cat(tbAskCat);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-14
	 */
	public String updateordernum(HkRequest req, HkResponse resp) {
		long cid = req.getLongAndSetAttr("cid");
		int order_num = req.getInt("order_num");
		Tb_Ask_Cat tbAskCat = this.tbAskCatService.getTb_Ask_Cat(cid);
		if (tbAskCat == null) {
			return null;
		}
		order_num = Math.abs(order_num);
		tbAskCat.setOrder_num(order_num);
		this.tbAskCatService.updateTb_Ask_Cat(tbAskCat);
		return null;
	}
}