package com.etbhk.web.index.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etbhk.util.BaseTaoBaoAction;
import com.hk.bean.taobao.Tb_Ask;
import com.hk.bean.taobao.Tb_CmdItem;
import com.hk.bean.taobao.Tb_Item;
import com.hk.bean.taobao.Tb_Item_Cmt;
import com.hk.bean.taobao.Tb_User;
import com.hk.bean.taobao.Tb_UserItemWeekReport;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.Tb_ItemService;
import com.hk.svr.Tb_Item_CmtService;
import com.hk.svr.Tb_UserItemReportService;
import com.hk.svr.Tb_UserService;
import com.hk.svr.processor.taobao.Tb_AskServiceEx;

@Component("/tb/index")
public class IndexAction extends BaseTaoBaoAction {

	@Autowired
	private Tb_UserService tb_UserService;

	@Autowired
	private Tb_ItemService tb_ItemService;

	@Autowired
	private Tb_Item_CmtService tb_Item_CmtService;

	@Autowired
	private Tb_AskServiceEx tbAskServiceEx;

	@Autowired
	private Tb_UserItemReportService tbUserItemReportService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// 最火
		List<Tb_Item> huolist = this.tb_ItemService.getTb_ItemListForHuo(0, 21);
		req.setAttribute("huolist", huolist);
		// 最酷
		List<Tb_Item> kulist = this.tb_ItemService.getTb_ItemListForKu(0, 21);
		req.setAttribute("kulist", kulist);
		// 推荐商品
		List<Tb_CmdItem> cmdlist = this.tb_ItemService.getTb_CmdItemList(true,
				0, 4);
		List<Tb_Item> indexlist = null;
		if (cmdlist.size() == 0) {
			// 最新商品
			indexlist = this.tb_ItemService.getTb_ItemListForNew(0, 4);
		}
		else {
			indexlist = new ArrayList<Tb_Item>();
			for (Tb_CmdItem o : cmdlist) {
				indexlist.add(o.getTbItem());
			}
		}
		req.setAttribute("indexlist", indexlist);
		// 最新点评
		List<Tb_Item_Cmt> newcmtlist = this.tb_Item_CmtService
				.getTb_Item_CmtListForNew(true, true, 0, 30);
		req.setAttribute("newcmtlist", newcmtlist);
		return this.getWebJsp("index/index.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-9-26
	 */
	public String indexrightinc(HkRequest req, HkResponse resp) {
		// 最新用户
		List<Tb_User> newuserlist = this.tb_UserService.getTb_UserListForNew(0,
				6);
		req.setAttribute("newuserlist", newuserlist);
		// 火酷达人
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_MONTH, -1);
		cal.set(Calendar.DAY_OF_WEEK, 1);
		Date min = DataUtil.getDate(cal.getTime());
		cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_MONTH, -1);
		cal.set(Calendar.DAY_OF_WEEK, 7);
		Date max = DataUtil.getEndDate(cal.getTime());
		List<Tb_UserItemWeekReport> reports = this.tbUserItemReportService
				.getTb_UserItemWeekReportList(min, max, true, 0, 6);
		List<Tb_User> supermanlist = null;
		if (reports.size() == 0) {
			supermanlist = this.tb_UserService.getTb_UserListForSuperMan(0, 6);
		}
		else {
			supermanlist = new ArrayList<Tb_User>();
			for (Tb_UserItemWeekReport o : reports) {
				supermanlist.add(o.getTbUser());
			}
		}
		req.setAttribute("supermanlist", supermanlist);
		// 最新提出的问题
		List<Tb_Ask> asklist = this.tbAskServiceEx.getTb_AskListForNew(true,
				false, 0, 10);
		req.setAttribute("asklist", asklist);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-26
	 */
	public String huo(HkRequest req, HkResponse resp) {
		SimplePage page = req.getSimplePage(49);
		List<Tb_Item> huolist = this.tb_ItemService.getTb_ItemListForHuo(page
				.getBegin(), page.getSize() + 1);
		req.setAttribute("huolist", huolist);
		return this.getWebJsp("index/huo.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-26
	 */
	public String ku(HkRequest req, HkResponse resp) {
		SimplePage page = req.getSimplePage(49);
		List<Tb_Item> kulist = this.tb_ItemService.getTb_ItemListForKu(page
				.getBegin(), page.getSize() + 1);
		req.setAttribute("kulist", kulist);
		return this.getWebJsp("index/ku.jsp");
	}
}