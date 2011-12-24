package com.etbhk.web.ask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etbhk.job.TbHkJob;
import com.etbhk.util.BaseTaoBaoAction;
import com.hk.bean.taobao.Tb_Answer;
import com.hk.bean.taobao.Tb_Answer_Item;
import com.hk.bean.taobao.Tb_Ask;
import com.hk.bean.taobao.Tb_Ask_Cat;
import com.hk.bean.taobao.Tb_User;
import com.hk.bean.taobao.Tb_User_Api;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.Tb_AskService;
import com.hk.svr.Tb_Ask_CatService;
import com.hk.svr.Tb_UserService;
import com.hk.svr.processor.taobao.Tb_AskServiceEx;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.TaoBaoAccessLimitException;
import com.hk.svr.pub.TaoBaoUtil;
import com.taobao.api.TaobaoApiException;
import com.taobao.api.taobaoke.model.TaobaokeItemDetail;

@Component("/tb/ask")
public class AskAction extends BaseTaoBaoAction {

	@Autowired
	private Tb_AskService tbAskService;

	@Autowired
	private Tb_UserService tbUserService;

	@Autowired
	private Tb_AskServiceEx tbAskServiceEx;

	@Autowired
	private TbHkJob indexAskJob;

	@Autowired
	private Tb_Ask_CatService tbAskCatService;

	private final Log log = LogFactory.getLog(AskAction.class);

	@Override
	public String execute(HkRequest req, HkResponse resp) {
		long aid = req.getLongAndSetAttr("aid");
		Tb_Ask tbAsk = this.tbAskService.getTb_Ask(aid);
		if (tbAsk == null) {
			return null;
		}
		req.setAttribute("tbAsk", tbAsk);
		if (tbAsk.getAnsid() > 0) {
			Tb_Answer bestans = this.tbAskService
					.getTb_Answer(tbAsk.getAnsid());
			bestans.setTbUser(this.tbUserService
					.getTb_User(bestans.getUserid()));
			req.setAttribute("bestans", bestans);
		}
		Tb_User tbUser = this.tbUserService.getTb_User(tbAsk.getUserid());
		req.setAttribute("tbUser", tbUser);
		SimplePage page = req.getSimplePage(20);
		List<Tb_Answer> anslist = this.tbAskServiceEx.getTb_AnswerListByAid(
				aid, true, page.getBegin(), page.getSize() + 1);
		for (Tb_Answer o : anslist) {
			if (o.getAnsid() == tbAsk.getAnsid()) {
				anslist.remove(o);
				break;
			}
		}
		req.setAttribute("anslist", anslist);
		req.reSetAttribute("toanswer");
		if (this.getLoginTb_User(req) != null) {
			// 是否拥有新浪api
			if (this.tbUserService.getTb_User_Api(this.getLoginTb_User(req)
					.getUserid(), Tb_User_Api.REG_SOURCE_SINA) != null) {
				req.setAttribute("user_has_sinaapi", true);
			}
		}
		return this.getWebJsp("ask/view.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-8
	 */
	public String q(HkRequest req, HkResponse resp) {
		if (req.getString("ask") != null) {
			return "r:/tb/ask_prvask?title="
					+ DataUtil.urlEncoder(req.getString("w"));
		}
		return "r:/tb/ask_list?t=0&w="
				+ DataUtil.urlEncoder(req.getString("w"));
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-8
	 */
	public String prvask(HkRequest req, HkResponse resp) {
		if (req.getString("search") != null) {
			return "r:/tb/ask_list?t=0&w="
					+ DataUtil.urlEncoder(req.getString("w"));
		}
		if (req.getString("ask") != null) {
			return "r:/tb/ask_prvask?title=" + req.getString("w");
		}
		if (this.isForwardPage(req)) {
			// 是否拥有新浪api
			if (this.tbUserService.getTb_User_Api(this.getLoginTb_User(req)
					.getUserid(), Tb_User_Api.REG_SOURCE_SINA) != null) {
				req.setAttribute("user_has_sinaapi", true);
			}
			req.reSetAttribute("title");
			req.setAttribute("parent_cid", 0);
			req.setAttribute("cid", 0);
			return this.getWebJsp("/ask/ask.jsp");
		}
		Tb_Ask tbAsk = new Tb_Ask();
		tbAsk.setTitle(req.getHtmlRow("title"));
		tbAsk.setContent(req.getHtml("content"));
		tbAsk.setUserid(this.getLoginTb_User(req).getUserid());
		tbAsk.setCreate_time(new Date());
		tbAsk.setCid(req.getLong("cid"));
		tbAsk.setParent_cid(req.getLong("parent_cid"));
		int code = tbAsk.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "askerr", null);
		}
		this.tbAskServiceEx.createTb_Ask(tbAsk, req
				.getBoolean("create_to_sina"), req.getServerName(), req
				.getContextPath());
		return this.onSuccess(req, "askok", tbAsk.getAid());
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-15
	 */
	public String loadaskcat(HkRequest req, HkResponse resp) {
		List<Tb_Ask_Cat> askcatlist = this.tbAskCatService.getTb_Ask_CatList();
		req.setAttribute("askcatlist", askcatlist);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-8
	 */
	public String prvupdateask(HkRequest req, HkResponse resp) {
		long aid = req.getLongAndSetAttr("aid");
		Tb_Ask tbAsk = this.tbAskService.getTb_Ask(aid);
		if (tbAsk == null) {
			return null;
		}
		req.setAttribute("title", tbAsk.getTitle());
		if (this.isForwardPage(req)) {
			req.setAttribute("tbAsk", tbAsk);
			req.setAttribute("parent_cid", tbAsk.getParent_cid());
			req.setAttribute("cid", tbAsk.getCid());
			return this.getWebJsp("/ask/updateask.jsp");
		}
		if (tbAsk.getUserid() != this.getLoginTb_User(req).getUserid()) {
			return null;
		}
		tbAsk.setCid(req.getLong("cid"));
		tbAsk.setParent_cid(req.getLong("parent_cid"));
		tbAsk.setTitle(req.getHtmlRow("title"));
		tbAsk.setContent(req.getHtml("content"));
		int code = tbAsk.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "askerr", null);
		}
		this.tbAskService.updateTb_Ask(tbAsk);
		return this.onSuccess(req, "askok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-8
	 */
	public String prvdelask(HkRequest req, HkResponse resp) {
		long aid = req.getLongAndSetAttr("aid");
		Tb_Ask tbAsk = this.tbAskService.getTb_Ask(aid);
		if (tbAsk == null
				|| tbAsk.getUserid() != this.getLoginTb_User(req).getUserid()) {
			return null;
		}
		this.tbAskServiceEx.deleteTb_Ask(tbAsk);
		return this.onSuccess(req, "askok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-8
	 */
	public String prvanswer(HkRequest req, HkResponse resp) {
		long aid = req.getLong("aid");
		Tb_Ask tbAsk = this.tbAskService.getTb_Ask(aid);
		if (tbAsk == null) {
			return null;
		}
		Tb_Answer tbAnswer = new Tb_Answer();
		tbAnswer.setUserid(this.getLoginTb_User(req).getUserid());
		tbAnswer.setCreate_time(new Date());
		tbAnswer.setContent(req.getHtml("content"));
		tbAnswer.setAid(aid);
		int code = tbAnswer.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "answererr", null);
		}
		this.buildReslove_content(tbAnswer, req);
		this.tbAskServiceEx.createTb_Answer(tbAnswer, tbAsk, req
				.getBoolean("create_to_sina"), req.getServerName(), req
				.getContextPath());
		return this.onSuccess(req, "answerok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-8
	 */
	public String prvupdateanswer(HkRequest req, HkResponse resp) {
		long ansid = req.getLongAndSetAttr("ansid");
		Tb_Answer tbAnswer = this.tbAskService.getTb_Answer(ansid);
		if (this.isForwardPage(req)) {
			req.setAttribute("tbAnswer", tbAnswer);
			return this.getWebJsp("ask/updateanswer.jsp");
		}
		Tb_User tbUser = this.getLoginTb_User(req);
		if (tbAnswer == null || tbUser.getUserid() != tbAnswer.getUserid()) {
			return null;
		}
		tbAnswer.setContent(req.getHtml("content"));
		int code = tbAnswer.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "answererr", null);
		}
		this.buildReslove_content(tbAnswer, req);
		this.tbAskService.updateTb_Answer(tbAnswer);
		return this.onSuccess(req, "answerok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-8
	 */
	public String prvdelansweritem(HkRequest req, HkResponse resp) {
		Tb_User tbUser = this.getLoginTb_User(req);
		String num_iid = req.getString("num_iid");
		if (DataUtil.isEmpty(num_iid)) {
			return null;
		}
		long ansid = req.getLong("ansid");
		Tb_Answer tbAnswer = this.tbAskService.getTb_Answer(ansid);
		if (tbAnswer == null || tbUser.getUserid() != tbAnswer.getUserid()) {
			return null;
		}
		tbAnswer.deleteItem(num_iid);
		this.tbAskService.updateTb_Answer(tbAnswer);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-9
	 */
	public String prvdelanswer(HkRequest req, HkResponse resp) {
		Tb_User tbUser = this.getLoginTb_User(req);
		long ansid = req.getLong("ansid");
		Tb_Answer tbAnswer = this.tbAskService.getTb_Answer(ansid);
		if (tbAnswer == null || tbUser.getUserid() != tbAnswer.getUserid()) {
			return null;
		}
		this.tbAskServiceEx.deleteTb_Answer(tbAnswer);
		this.setDelSuccessMsg(req);
		return null;
	}

	private void buildReslove_content(Tb_Answer tbAnswer, HkRequest req) {
		String[] item_url = req.getStrings("item_url");
		if (item_url == null) {
			return;
		}
		List<String> idlist = new ArrayList<String>();
		for (String url : item_url) {
			long num_iid = TaoBaoUtil.getTaoBaoItemNum_iidFromUrl(url);
			if (num_iid > 0) {
				idlist.add(num_iid + "");
			}
		}
		// 控制最多提交10个url
		idlist = DataUtil.subList(idlist, 0, 10);
		Map<String, String> map = null;
		List<String> resolvelist = tbAnswer.getResolveList();
		if (resolvelist == null) {
			resolvelist = new ArrayList<String>();
		}
		try {
			List<TaobaokeItemDetail> list = TaoBaoUtil
					.getTaobaokeItemDetailListByNum_iidsForTaobaoKe(idlist
							.toArray(new String[idlist.size()]), null, true);
			int i = 1;
			if (list != null && list.size() > 0) {
				for (TaobaokeItemDetail detail : list) {
					map = new HashMap<String, String>();
					map.put("t", detail.getItem().getTitle());
					map.put("p", detail.getItem().getPrice());
					map.put("i", detail.getItem().getPicUrl());
					map.put("u", detail.getClickUrl());
					map.put("tb_id", detail.getItem().getNumIid());
					map.put("id", i + "");
					resolvelist.add(DataUtil.toJson(map));
					i++;
				}
				resolvelist = DataUtil.subList(resolvelist, 0, 10);
				tbAnswer.setResolve_content(DataUtil.toJson(resolvelist));
			}
		}
		catch (TaobaoApiException e) {
			log.error("调用淘宝api错误");
		}
		catch (TaoBaoAccessLimitException e) {
			log.error("超过淘宝api调用频率");
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-8
	 */
	public String list(HkRequest req, HkResponse resp) {
		String w = req.getHtmlRowAndSetAttr("w");
		if (DataUtil.isNotEmpty(w)) {
			return this.listForSearch(req, w);
		}
		return this.getPublicAskList(req);
	}

	public String getPublicAskList(HkRequest req) {
		SimplePage page = req.getSimplePage(20);
		List<Tb_Ask> list = this.tbAskServiceEx.getTb_AskListForNew(true, true,
				page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.setAttribute("pub_show", true);
		return this.getWebJsp("/ask/list.jsp");
	}

	private String listForSearch(HkRequest req, String w) {
		SimplePage page = req.getSimplePage(20);
		List<Tb_Ask> list;
		try {
			list = this.tbAskServiceEx.searchAll(w, true, true,
					page.getBegin(), page.getSize() + 1);
		}
		catch (IOException e) {
			log.error(e.toString());
			list = new ArrayList<Tb_Ask>();
		}
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.setAttribute("search_show", true);
		req.setEncodeAttribute("w", w);
		return this.getWebJsp("ask/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-9
	 */
	public String prvsupportanswer(HkRequest req, HkResponse resp) {
		long ansid = req.getLong("ansid");
		Tb_Answer tbAnswer = this.tbAskServiceEx.supportAnswer(this
				.getLoginTb_User(req).getUserid(), ansid);
		if (tbAnswer == null) {
			return null;
		}
		resp.sendHtml(tbAnswer.getSupport_num() + ";"
				+ tbAnswer.getDiscmd_num());
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-9
	 */
	public String prvdiscmdanswer(HkRequest req, HkResponse resp) {
		long ansid = req.getLong("ansid");
		Tb_Answer tbAnswer = this.tbAskServiceEx.discmdAnswer(this
				.getLoginTb_User(req).getUserid(), ansid);
		if (tbAnswer == null) {
			return null;
		}
		resp.sendHtml(tbAnswer.getSupport_num() + ";"
				+ tbAnswer.getDiscmd_num());
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-9
	 */
	public String prvselbestanswer(HkRequest req, HkResponse resp) {
		long ansid = req.getLong("ansid");
		Tb_Answer tbAnswer = this.tbAskService.getTb_Answer(ansid);
		if (tbAnswer == null) {
			return null;
		}
		Tb_Ask tbAsk = this.tbAskService.getTb_Ask(tbAnswer.getAid());
		if (tbAsk == null
				|| tbAsk.getUserid() != this.getLoginTb_User(req).getUserid()) {
			return null;
		}
		this.tbAskServiceEx.selectBestAnswer(tbAsk, tbAnswer);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-9
	 */
	public String prvcancelbestanswer(HkRequest req, HkResponse resp) {
		long aid = req.getLong("aid");
		Tb_Ask tbAsk = this.tbAskService.getTb_Ask(aid);
		if (tbAsk == null
				|| tbAsk.getUserid() != this.getLoginTb_User(req).getUserid()) {
			return null;
		}
		tbAsk.setAnsid(0);
		tbAsk.setResolve_status(Tb_Ask.RESOLVE_STATUS_N);
		this.tbAskService.updateTb_Ask(tbAsk);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-8
	 */
	public String index1(HkRequest req, HkResponse resp) {
		long begin = System.currentTimeMillis();
		indexAskJob.indexAllAsk();
		resp.sendHtml("indexAllAsk ok [ "
				+ (System.currentTimeMillis() - begin) + " ms ]");
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-12
	 */
	public String item(HkRequest req, HkResponse resp) {
		long ansid = req.getLong("ansid");
		int id = req.getInt("id");
		Tb_Answer tbAnswer = this.tbAskService.getTb_Answer(ansid);
		if (tbAnswer == null) {
			return null;
		}
		List<Tb_Answer_Item> itemlist = tbAnswer.getTbAnswerItemList();
		if (itemlist != null && itemlist.size() > 0) {
			for (Tb_Answer_Item o : itemlist) {
				if (o.getId() == id) {
					if (o.getClick_url() != null) {
						return "r:" + o.getClick_url();
					}
					return "r:" + TaoBaoUtil.taobao_base_url + o.getNum_iid();
				}
			}
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-14
	 */
	public String noresolved(HkRequest req, HkResponse resp) {
		List<Tb_Ask> noresolvedasklist = this.tbAskServiceEx
				.getTb_AskListForNotResolved(true, 0, 10);
		req.setAttribute("noresolvedasklist", noresolvedasklist);
		return null;
	}
}