package com.etbhk.web.item.action;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etbhk.job.TbHkJob;
import com.etbhk.util.BaseTaoBaoAction;
import com.hk.bean.taobao.JsonKey;
import com.hk.bean.taobao.Tb_Item;
import com.hk.bean.taobao.Tb_Item_Cat;
import com.hk.bean.taobao.Tb_Item_Cmt;
import com.hk.bean.taobao.Tb_Item_Img;
import com.hk.bean.taobao.Tb_Item_Score;
import com.hk.bean.taobao.Tb_Item_User_Ref;
import com.hk.bean.taobao.Tb_User;
import com.hk.bean.taobao.Tb_User_Api;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.jms.HkMsgProducer;
import com.hk.jms.JmsMsg;
import com.hk.svr.Tb_ItemService;
import com.hk.svr.Tb_Item_CatService;
import com.hk.svr.Tb_Item_CmtService;
import com.hk.svr.Tb_Item_User_RefService;
import com.hk.svr.Tb_UserService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.TaoBaoAccessLimitException;
import com.hk.svr.pub.TaoBaoUtil;
import com.taobao.api.TaobaoApiException;
import com.taobao.api.taobaoke.model.Item;
import com.taobao.api.taobaoke.model.ItemImg;
import com.taobao.api.taobaoke.model.TaobaokeItem;
import com.taobao.api.taobaoke.model.TaobaokeItemDetail;

@Component("/tb/item")
public class ItemAction extends BaseTaoBaoAction {

	@Autowired
	private Tb_ItemService tb_ItemService;

	@Autowired
	private Tb_Item_CatService tb_Item_CatService;

	@Autowired
	private Tb_Item_CmtService tb_Item_CmtService;

	@Autowired
	private Tb_Item_User_RefService tb_Item_User_RefService;

	@Autowired
	private Tb_UserService tb_UserService;

	@Autowired
	private HkMsgProducer hkMsgProducer;

	@Autowired
	private TbHkJob tbHkJob;

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-26
	 */
	public String updateitems(HkRequest req, HkResponse resp) {
		this.tbHkJob.updateTb_Item();
		resp.sendHtml("updatetbitems ok");
		return null;
	}

	public String execute(HkRequest req, HkResponse resp) {
		long itemid = req.getLongAndSetAttr("itemid");
		Tb_Item tbItem = this.tb_ItemService.getTb_Item(itemid);
		if (tbItem == null) {
			return null;
		}
		Tb_User loginTbUser = this.getLoginTb_User(req);
		req.setAttribute("tbItem", tbItem);
		// 分类
		Tb_Item_Cat tbItemCat = this.tb_Item_CatService.getTb_Item_Cat(tbItem
				.getCid());
		req.setAttribute("tbItemCat", tbItemCat);
		// 点评
		List<Tb_Item_Cmt> cmtlist = this.tb_Item_CmtService
				.getTb_Item_CmtListByItemid(itemid, true, 0, 11);
		if (cmtlist.size() == 11) {
			req.setAttribute("more_cmt", true);
			cmtlist.remove(10);
		}
		req.setAttribute("cmtlist", cmtlist);
		// 用户推荐的其他商品
		List<Tb_Item_User_Ref> item_user_reflist = this.tb_Item_User_RefService
				.getTb_Item_User_RefByUseridAndFlg(tbItem.getUserid(),
						Tb_Item_User_Ref.FLG_CMD, true, false, 0, 3);
		for (Tb_Item_User_Ref o : item_user_reflist) {// 去掉当前的itemid
			if (o.getItemid() == itemid) {
				item_user_reflist.remove(o);
				break;
			}
		}
		if (item_user_reflist.size() == 3) {
			item_user_reflist.remove(2);
		}
		req.setAttribute("item_user_reflist", item_user_reflist);
		// 推荐此商品的用户
		Tb_User tbUser = this.tb_UserService.getTb_User(tbItem.getUserid());
		req.setAttribute("tbUser", tbUser);
		// 还有谁拥有此商品
		List<Tb_Item_User_Ref> item_user_ownnerlist = this.tb_Item_User_RefService
				.getTb_Item_User_RefByItemidAndFlg(itemid,
						Tb_Item_User_Ref.FLG_HOLD, true, 0, 3);
		// 去除自己
		if (loginTbUser != null) {
			for (Tb_Item_User_Ref o : item_user_ownnerlist) {
				if (o.getUserid() == loginTbUser.getUserid()) {
					item_user_ownnerlist.remove(o);
					break;
				}
			}
			if (item_user_ownnerlist.size() == 3) {
				item_user_ownnerlist.remove(2);
			}
		}
		req.setAttribute("item_user_ownnerlist", item_user_ownnerlist);
		int user_score = 0;
		// 当前用户是否拥有、想要此商品
		if (loginTbUser != null) {
			Tb_Item_User_Ref tbItemUserRef_ownner = this.tb_Item_User_RefService
					.getTb_Item_User_RefByUseridAndItemidAndFlg(loginTbUser
							.getUserid(), itemid, Tb_Item_User_Ref.FLG_HOLD);
			req.setAttribute("tbItemUserRef_ownner", tbItemUserRef_ownner);
			Tb_Item_User_Ref tbItemUserRef_want = this.tb_Item_User_RefService
					.getTb_Item_User_RefByUseridAndItemidAndFlg(loginTbUser
							.getUserid(), itemid, Tb_Item_User_Ref.FLG_WANT);
			req.setAttribute("tbItemUserRef_want", tbItemUserRef_want);
			// 用户的打分
			Tb_Item_Score tbItemScore = this.tb_ItemService
					.getTb_Item_ScoreByItemidAndUserid(itemid, loginTbUser
							.getUserid());
			if (tbItemScore != null) {
				user_score = tbItemScore.getScore();
			}
			// 是否拥有新浪api
			if (this.tb_UserService.getTb_User_Api(loginTbUser.getUserid(),
					Tb_User_Api.REG_SOURCE_SINA) != null) {
				req.setAttribute("user_has_sinaapi", true);
			}
		}
		req.setAttribute("user_score", user_score);
		req.reSetAttribute("tocmt");
		return this.getWebJsp("item/item.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-31
	 */
	public String checkusersysadmin(HkRequest req, HkResponse resp) {
		this.isUserSysAdmin(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-31
	 */
	public String prvcreate(HkRequest req, HkResponse resp) {
		if (this.isForwardPage(req)) {
			return this.getWebJsp("item/create.jsp");
		}
		try {
			Tb_User tbUser = this.getLoginTb_User(req);
			// 创建点评数据
			Tb_Item_Cmt tbItemCmt = new Tb_Item_Cmt();
			tbItemCmt.setContent(req.getHtml("content"));
			tbItemCmt.setCreate_time(new Date());
			tbItemCmt.setUserid(tbUser.getUserid());
			int code = tbItemCmt.validate();
			if (code != Err.SUCCESS) {
				return this.onError(req, code, "cmterr", null);
			}
			Tb_Item tbItem = this.processTaobaoItem(req);
			if (tbItem == null) {
				return this.onError(req, Err.API_TAOBAO_ITEM_NOT_EXIST,
						"cmterr", null);
			}
			tbItemCmt.setItemid(tbItem.getItemid());
			tbItemCmt.setSid(tbItem.getSid());
			if (req.getInt("user_item_status") == 1) {
				tbItemCmt.setScore(req.getInt("score"));
			}
			this.tb_Item_CmtService.createTb_Item_Cmt(tbItemCmt, true, false,
					req.getBoolean("create_to_sina_weibo"),
					req.getServerName(), req.getContextPath());
			// 拥有
			if (req.getInt("user_item_status") == 1) {
				Tb_Item_User_Ref tbItemUserRef = this.tb_Item_User_RefService
						.getTb_Item_User_RefByUseridAndItemidAndFlg(tbUser
								.getUserid(), tbItem.getItemid(),
								Tb_Item_User_Ref.FLG_HOLD);
				if (tbItemUserRef == null) {
					tbItemUserRef = new Tb_Item_User_Ref();
					tbItemUserRef.setUserid(tbUser.getUserid());
					tbItemUserRef.setFlg(Tb_Item_User_Ref.FLG_HOLD);
					tbItemUserRef.setItemid(tbItem.getItemid());
					tbItemUserRef.setCmtid(tbItemCmt.getCmtid());
					this.tb_Item_User_RefService
							.createTb_Item_User_Ref(tbItemUserRef);
				}
			}
			// 想买
			else if (req.getInt("user_item_status") == 0) {
				Tb_Item_User_Ref tbItemUserRef = this.tb_Item_User_RefService
						.getTb_Item_User_RefByUseridAndItemidAndFlg(tbUser
								.getUserid(), tbItem.getItemid(),
								Tb_Item_User_Ref.FLG_WANT);
				if (tbItemUserRef == null) {
					tbItemUserRef = new Tb_Item_User_Ref();
					tbItemUserRef.setUserid(tbUser.getUserid());
					tbItemUserRef.setFlg(Tb_Item_User_Ref.FLG_WANT);
					tbItemUserRef.setItemid(tbItem.getItemid());
					tbItemUserRef.setCmtid(tbItemCmt.getCmtid());
					this.tb_Item_User_RefService
							.createTb_Item_User_Ref(tbItemUserRef);
				}
			}
			return this.onSuccess(req, "cmtok", tbItem.getItemid());
		}
		catch (TaobaoApiException e) {
			return this.onError(req, Err.API_TAOBAO_APP_TIMEOUT, "createerr",
					null);
		}
		catch (TaoBaoAccessLimitException e) {
			return this.onError(req, Err.API_TAOBAO_APP_TIMEOUT, "createerr",
					null);
		}
	}

	private Tb_Item processTaobaoItem(HkRequest req) throws TaobaoApiException,
			TaoBaoAccessLimitException {
		long num_iid = TaoBaoUtil.getTaoBaoItemNum_iidFromUrl(req
				.getString("taobao_item_url"));
		Tb_Item tbItem = this.tb_ItemService.getTb_ItemByNum_iid(num_iid);
		if (tbItem == null) {
			tbItem = new Tb_Item();
		}
		TaobaokeItemDetail taobaokeItemDetail = (TaobaokeItemDetail) req
				.getSessionValue("taobaokeItemDetail");
		if (taobaokeItemDetail == null) {
			taobaokeItemDetail = TaoBaoUtil
					.getTaobaokeItemDetailByNum_iidForTaobaoKe(num_iid + "",
							null, true);
		}
		if (taobaokeItemDetail == null) {
			return null;
		}
		Item taobao_item = taobaokeItemDetail.getItem();
		List<TaobaokeItem> taobaokeItems = TaoBaoUtil
				.getTaobaokeItemListByItem(taobao_item, null, true);
		if (taobao_item == null) {
			return null;
		}
		TaobaokeItem taobaokeItem = null;
		// 只有是淘宝可的推广商品才可以调用
		if (taobaokeItems != null
				&& DataUtil.isNotEmpty(taobaokeItemDetail.getClickUrl())) {
			for (TaobaokeItem o : taobaokeItems) {
				if (o.getNumIid().longValue() == Long.valueOf(taobao_item
						.getNumIid())) {
					taobaokeItem = o;
					break;
				}
			}
		}
		if (taobaokeItem != null) {
			if (DataUtil.isNotEmpty(taobaokeItem.getCommission())) {
				tbItem.setCommission(Double.valueOf(taobaokeItem
						.getCommission()));
			}
			if (DataUtil.isNotEmpty(taobaokeItem.getCommissionRate())) {
				tbItem.setCommission_rate(Double.valueOf(taobaokeItem
						.getCommissionRate()) / 10000);
			}
			if (taobaokeItem.getVolume() != null) {
				tbItem.setVolume(taobaokeItem.getVolume());
			}
			if (taobaokeItem.getCommissionNum() != null) {
				try {
					tbItem.setCommission_num(Integer.valueOf(taobaokeItem
							.getCommissionNum()));
				}
				catch (NumberFormatException e) {
				}
			}
		}
		tbItem.setClick_url(taobaokeItemDetail.getClickUrl());
		tbItem.setShop_click_url(taobaokeItemDetail.getShopClickUrl());
		tbItem.setCreate_time(new Date());
		tbItem.setLast_modified(tbItem.getCreate_time());
		tbItem.setTitle(taobao_item.getTitle());
		tbItem.setCid(Long.valueOf(taobao_item.getCid()));
		tbItem.setDelist_time(taobao_item.getDelistTime());
		tbItem.setList_time(taobao_item.getListTime());
		tbItem.setModified(taobao_item.getModified());
		tbItem.setDetail_url(taobao_item.getDetailUrl());
		tbItem.setFreight_payerFromString(taobao_item.getFreightPayer());
		if (taobao_item.getHasInvoice()) {
			tbItem.setHas_invoice(Tb_Item.HAS_INVOICE_Y);
		}
		else {
			tbItem.setHas_invoice(Tb_Item.HAS_INVOICE_N);
		}
		if (taobao_item.getHasWarranty()) {
			tbItem.setHas_warranty(Tb_Item.HAS_WARRANTY_Y);
		}
		else {
			tbItem.setHas_warranty(Tb_Item.HAS_WARRANTY_N);
		}
		tbItem.setItem_type(taobao_item.getType());
		String location = null;
		if (taobao_item.getLocation() != null) {
			if (taobao_item.getLocation().getState() != null) {
				location = taobao_item.getLocation().getState();
			}
			if (taobao_item.getLocation().getCity() != null) {
				location += " " + taobao_item.getLocation().getCity();
			}
		}
		tbItem.setLocation(location);
		tbItem.setNick(taobao_item.getNick());
		tbItem.setNum(taobao_item.getNum());
		tbItem.setNum_iid(Long.valueOf(taobao_item.getNumIid()));
		if (taobao_item.getOneStation()) {
			tbItem.setOne_station(Tb_Item.ONE_STATION_Y);
		}
		else {
			tbItem.setOne_station(Tb_Item.ONE_STATION_N);
		}
		tbItem.setPic_url(taobao_item.getPicUrl());
		tbItem.setPrice(Double.valueOf(taobao_item.getPrice()));
		if (!DataUtil.isEmpty(taobao_item.getProductId())) {
			tbItem.setProduct_id(Long.valueOf(taobao_item.getProductId()));
		}
		tbItem.setStuff_statusFromString(taobao_item.getStuffStatus());
		tbItem.setValid_thru(taobao_item.getValidThru());
		tbItem.setUserid(this.getLoginTb_User(req).getUserid());
		if (tbItem.getItemid() > 0) {
			this.tb_ItemService.updateTb_Item(tbItem);
			this.tb_ItemService.deleteTb_Item_ImgByNum_iid(tbItem.getNum_iid());
		}
		else {
			this.tb_ItemService.createTb_Item(tbItem);
		}
		List<ItemImg> list = taobao_item.getItemImgs();
		if (list != null) {
			Tb_Item_Img tbItemImg = null;
			for (ItemImg o : list) {
				tbItemImg = new Tb_Item_Img();
				tbItemImg.setNum_iid(tbItem.getNum_iid());
				tbItemImg.setUrl(o.getUrl());
				tbItemImg.setPosition(o.getPosition());
				this.tb_ItemService.createTb_Item_Img(tbItemImg);
			}
		}
		req.removeSessionvalue("taobaokeItemDetail");
		return tbItem;
	}

	// private void processRefTb_Item_CatList(long cid, List<Tb_Item_Cat> list)
	// {
	// Tb_Item_Cat tbItemCat = this.tb_Item_CatService.getTb_Item_Cat(cid);
	// if (tbItemCat != null) {
	// list.add(tbItemCat);
	// if (tbItemCat.getParent_cid() > 0) {
	// this.processRefTb_Item_CatList(tbItemCat.getParent_cid(), list);
	// }
	// }
	// }
	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-31
	 */
	public String loadtaobaoitem(HkRequest req, HkResponse resp) {
		String taobao_item_url = req.getStringAndSetAttr("taobao_item_url");
		if (DataUtil.isEmpty(taobao_item_url)) {
			req.setAttribute("taobao_success", true);
			return this.getWebJsp("item/item_data.jsp");
		}
		try {
			TaobaokeItemDetail taobaokeItemDetail = TaoBaoUtil
					.getTaobaokeItemDetailByTaoBao_Item_urlForTaobaoKe(
							taobao_item_url, null, true);
			if (taobaokeItemDetail == null
					|| taobaokeItemDetail.getItem() == null) {
				req.setAttribute("taobao_item", null);
				req.setAttribute("taobao_success", true);
				return this.getWebJsp("item/item_data.jsp");
			}
			req.removeSessionvalue("taobaokeItemDetail");
			req.setSessionValue("taobaokeItemDetail", taobaokeItemDetail);
			Item taobao_item = taobaokeItemDetail.getItem();
			if (taobao_item != null) {
				Tb_Item_Cat tbItemCat = this.tb_Item_CatService
						.getTb_Item_Cat(Long.valueOf(taobao_item.getCid()));
				req.setAttribute("tbItemCat", tbItemCat);
			}
			req.setAttribute("taobao_item", taobao_item);
			req.setAttribute("taobao_success", true);
			if (this.getLoginTb_User(req) != null) {
				// 是否拥有新浪api
				if (this.tb_UserService.getTb_User_Api(this
						.getLoginTb_User(req).getUserid(),
						Tb_User_Api.REG_SOURCE_SINA) != null) {
					req.setAttribute("user_has_sinaapi", true);
				}
			}
		}
		catch (TaobaoApiException e) {
			req.removeSessionvalue("taobaokeItemDetail");
			req.setAttribute("taobao_success", false);
		}
		catch (TaoBaoAccessLimitException e) {
			req.removeSessionvalue("taobaokeItemDetail");
			req.setAttribute("taobao_success", false);
		}
		return this.getWebJsp("item/item_data.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-31
	 */
	public String prvcreatescore(HkRequest req, HkResponse resp) {
		long itemid = req.getLong("itemid");
		int score = req.getInt("score");
		Tb_User loginTbUser = this.getLoginTb_User(req);
		Tb_Item_Score tbItemScore = this.tb_ItemService
				.getTb_Item_ScoreByItemidAndUserid(itemid, loginTbUser
						.getUserid());
		if (tbItemScore == null) {
			tbItemScore = new Tb_Item_Score();
		}
		tbItemScore.setItemid(itemid);
		tbItemScore.setUserid(loginTbUser.getUserid());
		tbItemScore.setScore(score);
		this.tb_ItemService.saveTb_Item_Score(tbItemScore);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-2
	 */
	public String prvstatusown(HkRequest req, HkResponse resp) {
		long itemid = req.getLong("itemid");
		Tb_Item tbItem = this.tb_ItemService.getTb_Item(itemid);
		if (tbItem == null) {
			return null;
		}
		Tb_User loginTbUser = this.getLoginTb_User(req);
		Tb_Item_User_Ref tbItemUserRef = this.tb_Item_User_RefService
				.getTb_Item_User_RefByUseridAndItemidAndFlg(loginTbUser
						.getUserid(), itemid, Tb_Item_User_Ref.FLG_HOLD);
		if (tbItemUserRef == null) {
			List<Tb_Item_Cmt> cmtlist = this.tb_Item_CmtService
					.getTb_Item_CmtListByItemidAndUserid(itemid, loginTbUser
							.getUserid(), 0, 1);
			if (cmtlist.size() == 1) {
				tbItemUserRef = new Tb_Item_User_Ref();
				tbItemUserRef.setUserid(loginTbUser.getUserid());
				tbItemUserRef.setFlg(Tb_Item_User_Ref.FLG_HOLD);
				tbItemUserRef.setItemid(itemid);
				tbItemUserRef.setCmtid(cmtlist.get(0).getCmtid());
				this.tb_Item_User_RefService
						.createTb_Item_User_Ref(tbItemUserRef);
			}
		}
		Tb_Item_User_Ref want_tbItemUserRef = this.tb_Item_User_RefService
				.getTb_Item_User_RefByUseridAndItemidAndFlg(loginTbUser
						.getUserid(), itemid, Tb_Item_User_Ref.FLG_WANT);
		if (want_tbItemUserRef != null) {
			this.tb_Item_User_RefService
					.deleteTb_Item_User_Ref(want_tbItemUserRef.getOid());
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-2
	 */
	public String prvstatuswant(HkRequest req, HkResponse resp) {
		long itemid = req.getLong("itemid");
		Tb_Item tbItem = this.tb_ItemService.getTb_Item(itemid);
		if (tbItem == null) {
			return null;
		}
		Tb_User loginTbUser = this.getLoginTb_User(req);
		Tb_Item_User_Ref tbItemUserRef = this.tb_Item_User_RefService
				.getTb_Item_User_RefByUseridAndItemidAndFlg(loginTbUser
						.getUserid(), itemid, Tb_Item_User_Ref.FLG_WANT);
		if (tbItemUserRef == null) {
			tbItemUserRef = new Tb_Item_User_Ref();
			tbItemUserRef.setUserid(loginTbUser.getUserid());
			tbItemUserRef.setFlg(Tb_Item_User_Ref.FLG_WANT);
			tbItemUserRef.setItemid(itemid);
			this.tb_Item_User_RefService.createTb_Item_User_Ref(tbItemUserRef);
		}
		Tb_Item_User_Ref hold_tbItemUserRef = this.tb_Item_User_RefService
				.getTb_Item_User_RefByUseridAndItemidAndFlg(loginTbUser
						.getUserid(), itemid, Tb_Item_User_Ref.FLG_HOLD);
		if (hold_tbItemUserRef != null) {
			this.tb_Item_User_RefService
					.deleteTb_Item_User_Ref(hold_tbItemUserRef.getOid());
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-2
	 */
	public String prvdelstatusown(HkRequest req, HkResponse resp) {
		long itemid = req.getLong("itemid");
		Tb_Item tbItem = this.tb_ItemService.getTb_Item(itemid);
		if (tbItem == null) {
			return null;
		}
		Tb_User loginTbUser = this.getLoginTb_User(req);
		Tb_Item_User_Ref tbItemUserRef = this.tb_Item_User_RefService
				.getTb_Item_User_RefByUseridAndItemidAndFlg(loginTbUser
						.getUserid(), itemid, Tb_Item_User_Ref.FLG_HOLD);
		if (tbItemUserRef != null) {
			this.tb_Item_User_RefService.deleteTb_Item_User_Ref(tbItemUserRef
					.getOid());
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-2
	 */
	public String prvdelstatuswant(HkRequest req, HkResponse resp) {
		long itemid = req.getLong("itemid");
		Tb_Item tbItem = this.tb_ItemService.getTb_Item(itemid);
		if (tbItem == null) {
			return null;
		}
		Tb_User loginTbUser = this.getLoginTb_User(req);
		Tb_Item_User_Ref tbItemUserRef = this.tb_Item_User_RefService
				.getTb_Item_User_RefByUseridAndItemidAndFlg(loginTbUser
						.getUserid(), itemid, Tb_Item_User_Ref.FLG_WANT);
		if (tbItemUserRef != null) {
			this.tb_Item_User_RefService.deleteTb_Item_User_Ref(tbItemUserRef
					.getOid());
		}
		return null;
	}

	/**
	 * 检查用户是否打过分和写过评论
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-31
	 */
	public String prvcheckuseritem(HkRequest req, HkResponse resp) {
		long itemid = req.getLong("itemid");
		Tb_User loginTbUser = this.getLoginTb_User(req);
		int count = this.tb_Item_CmtService.countTb_Item_CmtByUseridAndItemid(
				loginTbUser.getUserid(), itemid);
		int cmt_flg = 0;
		if (count > 0) {
			cmt_flg = 1;
		}
		// 用户的打分
		Tb_Item_Score tbItemScore = this.tb_ItemService
				.getTb_Item_ScoreByItemidAndUserid(itemid, loginTbUser
						.getUserid());
		int score_flg = 0;
		if (tbItemScore != null) {
			score_flg = 1;
		}
		resp.sendHtml(cmt_flg + ";" + score_flg);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-18
	 */
	public String prvurl(HkRequest req, HkResponse resp) {
		long itemid = req.getLong("itemid");
		Tb_User tbUser = this.getLoginTb_User(req);
		Tb_Item tbItem = this.tb_ItemService.getTb_Item(itemid);
		if (tbItem == null) {
			return null;
		}
		if (req.getBoolean("share_item")) {
			// 发送新浪微博
			JmsMsg jmsMsg = new JmsMsg();
			jmsMsg.setHead(JmsMsg.HEAD_OTHER_API_SHARE_ITEM);
			Map<String, String> map = new HashMap<String, String>();
			map.put(JsonKey.ITEMID, String.valueOf(itemid));
			map.put(JsonKey.USERID, String.valueOf(tbUser.getUserid()));
			map.put(JsonKey.SERVER_NAME, req.getServerName());
			map.put(JsonKey.CONTEXTPATH, req.getContextPath());
			jmsMsg.setBody(map);
			this.hkMsgProducer.send(jmsMsg.toMessage());
		}
		if (DataUtil.isNotEmpty(tbItem.getClick_url())) {
			// return "r:" + tbItem.getClick_url() + tbUser.getUserid();
			return "r:"
					+ TaoBaoUtil.getClick_url(tbItem.getClick_url(), String
							.valueOf(tbUser.getUserid()));
		}
		return TaoBaoUtil.taobao_base_url + tbItem.getNum_iid();
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-18
	 */
	public String prvdelete(HkRequest req, HkResponse resp) {
		long itemid = req.getLong("itemid");
		Tb_Item tbItem = this.tb_ItemService.getTb_Item(itemid);
		if (tbItem == null) {
			return null;
		}
		if (!this.isUserSysAdmin(req)) {
			return null;
		}
		this.tb_ItemService.deleteTb_Item(itemid);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-26
	 */
	public String loaditemdata(HkRequest req, HkResponse resp) {
		long itemid = req.getLong("itemid");
		if (itemid == 0) {
			Long id = (Long) req.getAttribute("itemid");
			if (id != null) {
				itemid = id.longValue();
			}
		}
		Tb_Item tbItem = this.tb_ItemService.getTb_Item(itemid);
		req.setAttribute("tbItem", tbItem);
		return null;
	}

	public static void main(String[] args) {
		String str = "1231231231231231";
		float str1 = Float.parseFloat(str);
		DecimalFormat fmt = new DecimalFormat("#.##E0");
		System.out.println(fmt.format(str1));
	}
}