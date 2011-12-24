package com.etbhk.web.item.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etbhk.util.BaseTaoBaoAction;
import com.hk.bean.taobao.Tb_Item;
import com.hk.bean.taobao.Tb_Item_Cmt;
import com.hk.bean.taobao.Tb_Item_Cmt_Reply;
import com.hk.bean.taobao.Tb_User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.Tb_ItemService;
import com.hk.svr.Tb_Item_CmtService;
import com.hk.svr.Tb_UserService;
import com.hk.svr.pub.Err;

@Component("/tb/itemcmt")
public class ItemCmtAction extends BaseTaoBaoAction {

	@Autowired
	private Tb_ItemService tb_ItemService;

	@Autowired
	private Tb_Item_CmtService tb_Item_CmtService;

	@Autowired
	private Tb_UserService tbUserService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long cmtid = req.getLongAndSetAttr("cmtid");
		Tb_Item_Cmt cmt = this.tb_Item_CmtService.getTb_Item_Cmt(cmtid);
		if (cmt == null) {
			return null;
		}
		cmt.setTbUser(this.tbUserService.getTb_User(cmt.getUserid()));
		req.setAttribute("cmt", cmt);
		SimplePage page = req.getSimplePage(20);
		List<Tb_Item_Cmt_Reply> replylist = this.tb_Item_CmtService
				.getTb_Item_Cmt_ReplyListByCmtid(cmtid, true, page.getBegin(),
						page.getSize() + 1);
		this.processListForPage(page, replylist);
		req.setAttribute("replylist", replylist);
		req.setAttribute("itemid", cmt.getItemid());
		return this.getWebJsp("item/cmt.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-26
	 */
	public String list(HkRequest req, HkResponse resp) {
		long itemid = req.getLongAndSetAttr("itemid");
		SimplePage page = req.getSimplePage(20);
		List<Tb_Item_Cmt> cmtlist = this.tb_Item_CmtService
				.getTb_Item_CmtListByItemid(itemid, true, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, cmtlist);
		req.setAttribute("cmtlist", cmtlist);
		return this.getWebJsp("item/itemcmtlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-31
	 */
	public String prvcreate(HkRequest req, HkResponse resp) {
		long itemid = req.getLong("itemid");
		Tb_Item tbItem = this.tb_ItemService.getTb_Item(itemid);
		if (tbItem == null) {
			return null;
		}
		Tb_User tbUser = this.getLoginTb_User(req);
		// 创建点评数据
		Tb_Item_Cmt tbItemCmt = new Tb_Item_Cmt();
		tbItemCmt.setItemid(tbItem.getItemid());
		tbItemCmt.setContent(req.getHtml("content"));
		tbItemCmt.setCreate_time(new Date());
		tbItemCmt.setUserid(tbUser.getUserid());
		tbItemCmt.setSid(tbItem.getSid());
		tbItemCmt.setScore(req.getInt("score"));
		int code = tbItemCmt.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "cmterr", req
					.getString("formid", ""));
		}
		this.tb_Item_CmtService.createTb_Item_Cmt(tbItemCmt, false, req
				.getBoolean("holditem"),
				req.getBoolean("create_to_sina_weibo"), req.getServerName(),
				req.getContextPath());
		return this.onSuccess(req, "cmtok", req.getString("formid", ""));
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-31
	 */
	public String prvupdate(HkRequest req, HkResponse resp) {
		long cmtid = req.getLongAndSetAttr("cmtid");
		Tb_Item_Cmt tbItemCmt = this.tb_Item_CmtService.getTb_Item_Cmt(cmtid);
		if (this.isForwardPage(req)) {
			req.setAttribute("tbItemCmt", tbItemCmt);
			return this.getWebJsp("item/updatecmt.jsp");
		}
		Tb_User tbUser = this.getLoginTb_User(req);
		if (tbItemCmt == null || tbUser.getUserid() != tbItemCmt.getUserid()) {
			return null;
		}
		tbItemCmt.setContent(req.getHtml("content"));
		int code = tbItemCmt.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "cmterr", null);
		}
		this.tb_Item_CmtService.updateTb_Item_Cmt(tbItemCmt);
		return this.onSuccess(req, "cmtok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-31
	 */
	public String prvdelete(HkRequest req, HkResponse resp) {
		long cmtid = req.getLong("cmtid");
		Tb_Item_Cmt tbItemCmt = this.tb_Item_CmtService.getTb_Item_Cmt(cmtid);
		Tb_User tbUser = this.getLoginTb_User(req);
		if (tbItemCmt == null) {
			return null;
		}
		if (tbUser.getUserid() != tbItemCmt.getUserid()) {
			if (!this.isUserSysAdmin(req)) {
				return null;
			}
		}
		this.tb_Item_CmtService.deleteTb_Item_Cmt(tbItemCmt);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-31
	 */
	public String prvcreatereply(HkRequest req, HkResponse resp) {
		long cmtid = req.getLongAndSetAttr("cmtid");
		Tb_Item_Cmt_Reply tbItemCmtReply = new Tb_Item_Cmt_Reply();
		tbItemCmtReply.setCmtid(cmtid);
		tbItemCmtReply.setCreate_time(new Date());
		tbItemCmtReply.setUserid(this.getLoginTb_User(req).getUserid());
		tbItemCmtReply.setContent(req.getHtml("content"));
		int code = tbItemCmtReply.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "replyerr", null);
		}
		this.tb_Item_CmtService.createTb_Item_Cmt_Reply(tbItemCmtReply);
		tbItemCmtReply.setTbUser(this.getLoginTb_User(req));
		req.setAttribute("tbItemCmtReply", tbItemCmtReply);
		return this.getWebJsp("item/submit_reply.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-31
	 */
	public String prvdelreply(HkRequest req, HkResponse resp) {
		long replyid = req.getLong("replyid");
		Tb_Item_Cmt_Reply tbItemCmtReply = this.tb_Item_CmtService
				.getTb_Item_Cmt_Reply(replyid);
		if (tbItemCmtReply == null) {
			return null;
		}
		if (tbItemCmtReply.getUserid() != this.getLoginTb_User(req).getUserid()) {
			if (!this.isUserSysAdmin(req)) {
				return null;
			}
		}
		this.tb_Item_CmtService.deleteTb_Item_Cmt_Reply(tbItemCmtReply);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-31
	 */
	public String loadreply(HkRequest req, HkResponse resp) {
		long cmtid = req.getLongAndSetAttr("cmtid");
		List<Tb_Item_Cmt_Reply> list = this.tb_Item_CmtService
				.getTb_Item_Cmt_ReplyListByCmtid(cmtid, true, 0, 10);
		req.setAttribute("list", list);
		return this.getWebJsp("item/cmtreply_inc.jsp");
	}
}