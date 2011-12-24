package com.etbhk.admin.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etbhk.util.BaseTaoBaoAction;
import com.hk.bean.taobao.Tb_Item_Cat;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.Tb_Item_CatService;
import com.hk.svr.pub.TaoBaoUtil;
import com.hk.svr.pub.TaobaokeCdn;
import com.taobao.api.taobaoke.model.TaobaokeItem;
import com.taobao.api.taobaoke.model.TaobaokeItemsGetResponse;

@Component("/tb/admin/taokeitem")
public class TaokeItemAction extends BaseTaoBaoAction {

	@Autowired
	private Tb_Item_CatService tbItemCatService;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("taokeitem_mod", true);
		long parent_cid = req.getLongAndSetAttr("parent_cid");
		long cid = req.getLongAndSetAttr("cid");
		SimplePage page = req.getSimplePage(40);
		TaobaokeCdn taobaokeCdn = this.createTaobaokeCdn(req);
		List<TaobaokeItem> list = null;
		TaobaokeItemsGetResponse taobaokeItemsGetResponse = TaoBaoUtil
				.getTaobaokeItemList(taobaokeCdn, null, true);
		if (taobaokeItemsGetResponse != null) {
			list = taobaokeItemsGetResponse.getTaobaokeItems();
			if (taobaokeItemsGetResponse.getTotalResults() != null
					&& taobaokeItemsGetResponse.getTotalResults().intValue() > page
							.getPage()
							* page.getSize()) {
				page.setHasNext(true);
			}
		}
		if (list != null) {
			for (TaobaokeItem o : list) {
				if (o.getCommissionRate() != null) {
					double d = Double.valueOf(o.getCommissionRate());
					d = d / 100;
					o.setCommissionRate(String.valueOf(d));
				}
			}
		}
		req.setAttribute("list", list);
		req.setAttribute("taobaokeCdn", taobaokeCdn);
		List<Tb_Item_Cat> root_catlist = this.tbItemCatService
				.getTb_Item_CatListByParent_cid(0);
		req.setAttribute("root_catlist", root_catlist);
		Tb_Item_Cat parent = this.tbItemCatService.getTb_Item_Cat(parent_cid);
		req.setAttribute("parent", parent);
		if (cid <= 0) {
			cid = parent_cid;
		}
		else {
			Tb_Item_Cat item_cat = this.tbItemCatService.getTb_Item_Cat(cid);
			req.setAttribute("item_cat", item_cat);
		}
		if (parent_cid > 0) {
			List<Tb_Item_Cat> catlist = this.tbItemCatService
					.getTb_Item_CatListByParent_cid(parent_cid);
			req.setAttribute("catlist", catlist);
		}
		return this.getAdminJsp("taoke/list.jsp");
	}

	public TaobaokeCdn createTaobaokeCdn(HkRequest req) {
		TaobaokeCdn o = new TaobaokeCdn();
		o.setPage_size(40);
		o.setPage_no(req.getPage());
		o.setKeyword(req.getHtmlRowAndSetAttr("keyword"));
		if (req.getLong("cid") == 0) {
			o.setCid(req.getHtmlRowAndSetAttr("parent_cid"));
		}
		else {
			o.setCid(req.getHtmlRowAndSetAttr("cid"));
		}
		o.setStart_price(req.getDoubleAndSetAttr("start_price"));
		o.setEnd_price(req.getDoubleAndSetAttr("end_price"));
		String area = req.getHtmlRow("area");
		req.setEncodeAttribute("area", area);
		o.setArea(area);
		o.setStart_credit(req.getHtmlRowAndSetAttr("start_credit"));
		o.setEnd_credit(req.getHtmlRowAndSetAttr("end_credit"));
		if (req.getStringAndSetAttr("start_commissionRate") != null) {
			double d = req.getDouble("start_commissionRate") * 100;
			o.setStart_commissionRate(String.valueOf(d));
		}
		if (req.getStringAndSetAttr("end_commissionRate") != null) {
			double d = req.getDouble("end_commissionRate") * 100;
			o.setEnd_commissionRate(String.valueOf(d));
		}
		o.setStart_commissionNum(req
				.getHtmlRowAndSetAttr("start_commissionNum"));
		o.setEnd_commissionNum(req.getHtmlRowAndSetAttr("end_commissionNum"));
		o.setStart_totalnum(req.getHtmlRowAndSetAttr("start_totalnum"));
		o.setEnd_totalnum(req.getHtmlRowAndSetAttr("end_totalnum"));
		o.setCash_coupon(req.getHtmlRowAndSetAttr("cash_coupon"));
		o.setVip_card(req.getHtmlRowAndSetAttr("vip_card"));
		o.setOverseas_item(req.getHtmlRowAndSetAttr("overseas_item"));
		o.setSevendays_return(req.getHtmlRowAndSetAttr("sevendays_return"));
		o.setReal_describe(req.getHtmlRowAndSetAttr("real_describe"));
		o.setOnemonth_repair(req.getHtmlRowAndSetAttr("onemonth_repair"));
		o.setMall_item(req.getHtmlRowAndSetAttr("mall_item"));
		/*
		 * 默认排序:default price_desc(价格从高到低) price_asc(价格从低到高)
		 * credit_desc(信用等级从高到低) commissionRate_desc(佣金比率从高到底)
		 * commissionRate_asc(佣金比率从低到高) commissionNum_desc(成交量成高到低)
		 * commissionNum_asc(成交量从低到高) commissionVolume_desc(总支出佣金从高到底)
		 * commissionVolume_asc(总支出佣金从低到高) delistTime_desc(商品下架时间从高到底)
		 * delistTime_asc(商品下架时间从低到高)
		 */
		String sort = req.getString("sort", "price_desc");
		o.setSort(sort);
		req.setAttribute("sort", sort);
		return o;
	}
	// public String create(HkRequest req, HkResponse resp) {
	//		
	// }
	// private Tb_Item processTaobaoItem(HkRequest req, long createrid)
	// throws TaobaoApiException, TaoBaoAccessLimitException {
	// long num_iid = req.getLong("num_iid");
	// Tb_Item tbItem = this.tb_ItemService.getTb_ItemByNum_iid(num_iid);
	// if (tbItem == null) {
	// tbItem = new Tb_Item();
	// }
	// TaobaokeItemDetail taobaokeItemDetail = (TaobaokeItemDetail) req
	// .getSessionValue("taobaokeItemDetail");
	// if (taobaokeItemDetail == null) {
	// taobaokeItemDetail = TaoBaoUtil
	// .getTaobaokeItemDetailByNum_iidForTaobaoKe(num_iid + "",
	// null, true);
	// }
	// if (taobaokeItemDetail == null) {
	// return null;
	// }
	// Item taobao_item = taobaokeItemDetail.getItem();
	// if (taobao_item == null) {
	// return null;
	// }
	// tbItem.setVolume(req.getLong("volume"));
	// tbItem.setCommission(req.getDouble("commission"));
	// tbItem.setCommission_rate(req.getDouble("commission_rate") / 10000);
	// tbItem.setClick_url(taobaokeItemDetail.getClickUrl());
	// tbItem.setShop_click_url(taobaokeItemDetail.getShopClickUrl());
	// tbItem.setCreate_time(new Date());
	// tbItem.setLast_modified(tbItem.getCreate_time());
	// tbItem.setTitle(taobao_item.getTitle());
	// tbItem.setCid(Long.valueOf(taobao_item.getCid()));
	// tbItem.setDelist_time(taobao_item.getDelistTime());
	// tbItem.setList_time(taobao_item.getListTime());
	// tbItem.setModified(taobao_item.getModified());
	// tbItem.setDetail_url(taobao_item.getDetailUrl());
	// tbItem.setFreight_payerFromString(taobao_item.getFreightPayer());
	// if (taobao_item.getHasInvoice()) {
	// tbItem.setHas_invoice(Tb_Item.HAS_INVOICE_Y);
	// }
	// else {
	// tbItem.setHas_invoice(Tb_Item.HAS_INVOICE_N);
	// }
	// if (taobao_item.getHasWarranty()) {
	// tbItem.setHas_warranty(Tb_Item.HAS_WARRANTY_Y);
	// }
	// else {
	// tbItem.setHas_warranty(Tb_Item.HAS_WARRANTY_N);
	// }
	// tbItem.setItem_type(taobao_item.getType());
	// String location = null;
	// if (taobao_item.getLocation() != null) {
	// if (taobao_item.getLocation().getState() != null) {
	// location = taobao_item.getLocation().getState();
	// }
	// if (taobao_item.getLocation().getCity() != null) {
	// location += " " + taobao_item.getLocation().getCity();
	// }
	// }
	// tbItem.setLocation(location);
	// tbItem.setNick(taobao_item.getNick());
	// tbItem.setNum(taobao_item.getNum());
	// tbItem.setNum_iid(Long.valueOf(taobao_item.getNumIid()));
	// if (taobao_item.getOneStation()) {
	// tbItem.setOne_station(Tb_Item.ONE_STATION_Y);
	// }
	// else {
	// tbItem.setOne_station(Tb_Item.ONE_STATION_N);
	// }
	// tbItem.setPic_url(taobao_item.getPicUrl());
	// tbItem.setPrice(Double.valueOf(taobao_item.getPrice()));
	// if (!DataUtil.isEmpty(taobao_item.getProductId())) {
	// tbItem.setProduct_id(Long.valueOf(taobao_item.getProductId()));
	// }
	// tbItem.setStuff_statusFromString(taobao_item.getStuffStatus());
	// tbItem.setValid_thru(taobao_item.getValidThru());
	// tbItem.setUserid(createrid);
	// if (tbItem.getItemid() > 0) {
	// this.tb_ItemService.updateTb_Item(tbItem);
	// this.tb_ItemService.deleteTb_Item_ImgByNum_iid(tbItem.getNum_iid());
	// }
	// else {
	// this.tb_ItemService.createTb_Item(tbItem);
	// }
	// List<ItemImg> list = taobao_item.getItemImgs();
	// if (list != null) {
	// Tb_Item_Img tbItemImg = null;
	// for (ItemImg o : list) {
	// tbItemImg = new Tb_Item_Img();
	// tbItemImg.setNum_iid(tbItem.getNum_iid());
	// tbItemImg.setUrl(o.getUrl());
	// tbItemImg.setPosition(o.getPosition());
	// this.tb_ItemService.createTb_Item_Img(tbItemImg);
	// }
	// }
	// req.removeSessionvalue("taobaokeItemDetail");
	// return tbItem;
	// }
}