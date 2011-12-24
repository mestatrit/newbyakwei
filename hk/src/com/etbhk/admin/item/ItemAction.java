package com.etbhk.admin.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etbhk.util.BaseTaoBaoAction;
import com.hk.bean.taobao.Tb_CmdItem;
import com.hk.bean.taobao.Tb_Item;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.Tb_ItemService;

@Component("/tb/admin/item")
public class ItemAction extends BaseTaoBaoAction {

	@Autowired
	private Tb_ItemService tbItemService;

	@Override
	public String execute(HkRequest req, HkResponse resp) {
		req.setAttribute("item_mod", true);
		SimplePage page = req.getSimplePage(20);
		String title = req.getHtmlRow("title");
		List<Tb_Item> list = this.tbItemService.getTb_ItemListCdn(title, page
				.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setEncodeAttribute("title", title);
		req.setAttribute("list", list);
		return this.getAdminJsp("item/list.jsp");
	}

	/**
	 * 推荐的商品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-10-8
	 */
	public String cmdlist(HkRequest req, HkResponse resp) {
		req.setAttribute("cmd_item_mod", true);
		SimplePage page = req.getSimplePage(20);
		List<Tb_CmdItem> list = this.tbItemService.getTb_CmdItemList(true, page
				.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getAdminJsp("item/cmdlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-10-8
	 */
	public String cmditem(HkRequest req, HkResponse resp) {
		long itemid = req.getLong("itemid");
		Tb_Item tbItem = this.tbItemService.getTb_Item(itemid);
		if (tbItem == null) {
			return null;
		}
		this.tbItemService.commendItem(itemid);
		resp.sendHtml(1);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-10-8
	 */
	public String recmditem(HkRequest req, HkResponse resp) {
		long oid = req.getLong("oid");
		Tb_CmdItem tbCmdItem = this.tbItemService.getTb_CmdItem(oid);
		if (tbCmdItem == null) {
			return null;
		}
		this.tbItemService.deleteTb_CmdItem(tbCmdItem);
		this.tbItemService.commendItem(tbCmdItem.getItemid());
		resp.sendHtml(1);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-10-8
	 */
	public String delitem(HkRequest req, HkResponse resp) {
		long itemid = req.getLong("itemid");
		this.tbItemService.deleteTb_Item(itemid);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-10-8
	 */
	public String delcmd(HkRequest req, HkResponse resp) {
		long oid = req.getLong("oid");
		Tb_CmdItem tbCmdItem = this.tbItemService.getTb_CmdItem(oid);
		if (tbCmdItem == null) {
			return null;
		}
		this.tbItemService.deleteTb_CmdItem(tbCmdItem);
		this.setDelSuccessMsg(req);
		return null;
	}
}