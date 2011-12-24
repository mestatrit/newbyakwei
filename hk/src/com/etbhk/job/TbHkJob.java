package com.etbhk.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.Tb_Item;
import com.hk.frame.util.DataUtil;
import com.hk.svr.Tb_ItemService;
import com.hk.svr.processor.taobao.Tb_AskServiceEx;
import com.hk.svr.pub.TaoBaoAccessLimitException;
import com.hk.svr.pub.TaoBaoUtil;
import com.taobao.api.TaobaoApiException;
import com.taobao.api.taobaoke.model.Item;
import com.taobao.api.taobaoke.model.TaobaokeItem;
import com.taobao.api.taobaoke.model.TaobaokeItemDetail;

public class TbHkJob {

	private boolean doing_index_allask;

	@Autowired
	private Tb_ItemService tbItemService;

	@Autowired
	private Tb_AskServiceEx tbAskServiceEx;

	private final Log log = LogFactory.getLog(TbHkJob.class);

	/**
	 * 重新创建索引
	 * 2010-9-11
	 */
	public synchronized void indexAllAsk() {
		try {
			doing_index_allask = true;
			this.tbAskServiceEx.indexAllAsk();
		}
		catch (IOException e) {
			log.error(e.toString());
		}
		doing_index_allask = false;
	}

	/**
	 * 添加新文件到索引中
	 * 2010-9-11
	 */
	public synchronized void addAskDocIntoIndex() {
		if (doing_index_allask) {// 当进行全部索引的时候，不进行新索引的添加
			return;
		}
		try {
			this.tbAskServiceEx.addAskDocIntoIndex();
		}
		catch (IOException e) {
			log.error(e.toString());
		}
	}

	/**
	 * 定时更新最火的数据
	 * 2010-9-26
	 */
	public synchronized void updateHuoItmeInfo() {
		int begin = 0;
		int size = 1000;
		List<Tb_Item> list = null;
		do {
			list = this.tbItemService.getTb_ItemListForKu(begin, size);
			for (Tb_Item o : list) {
				int res = (int) (o.getCommission()) * o.getHkscore();
				o.setHuo_status(res);
				this.tbItemService.updateTb_Item(o);
			}
			begin += size;
		}
		while (list.size() > 0);
	}

	/**
	 * 定时更新商品数据，检查商品是否已经删除，或更新
	 */
	public synchronized void updateTb_Item() {
		List<Tb_Item> list = null;
		int begin = 0;
		int size = 10;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -2);
		Date d = cal.getTime();
		List<String> num_iidlist = null;
		do {
			num_iidlist = new ArrayList<String>();
			list = this.tbItemService.getTb_ItemListForUpdate(d, begin, size);
			for (Tb_Item o : list) {
				num_iidlist.add(String.valueOf(o.getNum_iid()));
			}
			if (num_iidlist.size() == 0) {
				break;
			}
			try {
				List<TaobaokeItemDetail> detaillist = TaoBaoUtil
						.getTaobaokeItemDetailListByNum_iidsForTaobaoKe(
								num_iidlist.toArray(new String[num_iidlist
										.size()]), null, false);
				for (Tb_Item o : list) {
					if (detaillist != null && this.isExist(o, detaillist)) {
						this.tbItemService.updateTb_Item(o);
					}
					else {
						o.setLast_modified(new Date());
						this.tbItemService.updateTb_Item(o);
//						this.tbItemService.deleteTb_Item(o.getItemid());
					}
					try {
						Thread.sleep(500);
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			catch (TaobaoApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (TaoBaoAccessLimitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				begin = begin + size;
				try {
					Thread.sleep(2000);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		while (list.size() > 0);
	}

	private boolean isExist(Tb_Item tbItem, List<TaobaokeItemDetail> detaillist) {
		for (TaobaokeItemDetail o : detaillist) {
			if (o.getItem() == null) {
				return false;
			}
			if (Long.valueOf(o.getItem().getNumIid()).longValue() == tbItem
					.getNum_iid()) {
				this.buildTb_Item(tbItem, o);
				return true;
			}
		}
		return false;
	}

	private void buildTb_Item(Tb_Item tbItem,
			TaobaokeItemDetail taobaokeItemDetail) {
		Item item = taobaokeItemDetail.getItem();
		if (item != null) {
			TaobaokeItem taobaokeItem;
			try {
				taobaokeItem = this.getTaobaokeItem(item, taobaokeItemDetail);
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
				}
				tbItem.setClick_url(taobaokeItemDetail.getClickUrl());
				tbItem.setShop_click_url(taobaokeItemDetail.getShopClickUrl());
				tbItem.setTitle(item.getTitle());
				tbItem.setCid(Long.valueOf(item.getCid()));
				tbItem.setDelist_time(item.getDelistTime());
				tbItem.setList_time(item.getListTime());
				tbItem.setModified(item.getModified());
				tbItem.setDetail_url(item.getDetailUrl());
				tbItem.setFreight_payerFromString(item.getFreightPayer());
				if (item.getHasInvoice()) {
					tbItem.setHas_invoice(Tb_Item.HAS_INVOICE_Y);
				}
				else {
					tbItem.setHas_invoice(Tb_Item.HAS_INVOICE_N);
				}
				if (item.getHasWarranty()) {
					tbItem.setHas_warranty(Tb_Item.HAS_WARRANTY_Y);
				}
				else {
					tbItem.setHas_warranty(Tb_Item.HAS_WARRANTY_N);
				}
				tbItem.setItem_type(item.getType());
				String location = null;
				if (item.getLocation() != null) {
					if (item.getLocation().getState() != null) {
						location = item.getLocation().getState();
					}
					if (item.getLocation().getCity() != null) {
						location += " " + item.getLocation().getCity();
					}
				}
				tbItem.setLocation(location);
				tbItem.setNick(item.getNick());
				tbItem.setNum(item.getNum());
				tbItem.setNum_iid(Long.valueOf(item.getNumIid()));
				if (item.getOneStation()) {
					tbItem.setOne_station(Tb_Item.ONE_STATION_Y);
				}
				else {
					tbItem.setOne_station(Tb_Item.ONE_STATION_N);
				}
				tbItem.setPic_url(item.getPicUrl());
				tbItem.setPrice(Double.valueOf(item.getPrice()));
				if (!DataUtil.isEmpty(item.getProductId())) {
					tbItem.setProduct_id(Long.valueOf(item.getProductId()));
				}
				tbItem.setStuff_statusFromString(item.getStuffStatus());
				tbItem.setValid_thru(item.getValidThru());
			}
			catch (TaobaoApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (TaoBaoAccessLimitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tbItem.setLast_modified(new Date());
		}
	}

	public TaobaokeItem getTaobaokeItem(Item item,
			TaobaokeItemDetail taobaokeItemDetail) throws TaobaoApiException,
			TaoBaoAccessLimitException {
		if (DataUtil.isNotEmpty(taobaokeItemDetail.getClickUrl())) {
			List<TaobaokeItem> taobaokeItems = TaoBaoUtil
					.getTaobaokeItemListByItem(item, null, true);
			if (taobaokeItems == null) {
				return null;
			}
			for (TaobaokeItem taobaokeItem : taobaokeItems) {
				if (taobaokeItem.getNumIid().longValue() == Long.valueOf(item
						.getNumIid())) {
					return taobaokeItem;
				}
			}
		}
		return null;
	}
}