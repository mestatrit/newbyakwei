package com.hk.svr.pub;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hk.bean.taobao.ApiInvoke;
import com.hk.bean.taobao.ApiInvokeRate;
import com.hk.bean.taobao.Tb_Item_Cat;
import com.hk.frame.util.HkUtil;
import com.hk.svr.ApiInvokeService;
import com.taobao.api.TaobaoApiException;
import com.taobao.api.TaobaoRestClient;
import com.taobao.api.TaobaoXmlRestClient;
import com.taobao.api.model.ItemCat;
import com.taobao.api.model.ItemCatsGetRequest;
import com.taobao.api.model.ItemCatsResponse;
import com.taobao.api.model.Shop;
import com.taobao.api.model.ShopGetRequest;
import com.taobao.api.taobaoke.direct.TaobaokeDirectJsonRestClient;
import com.taobao.api.taobaoke.direct.TaobaokeDirectRestClient;
import com.taobao.api.taobaoke.model.Item;
import com.taobao.api.taobaoke.model.TaobaokeItem;
import com.taobao.api.taobaoke.model.TaobaokeItemDetail;
import com.taobao.api.taobaoke.model.TaobaokeItemsDetailGetRequest;
import com.taobao.api.taobaoke.model.TaobaokeItemsDetailGetResponse;
import com.taobao.api.taobaoke.model.TaobaokeItemsGetRequest;
import com.taobao.api.taobaoke.model.TaobaokeItemsGetResponse;

/**
 * 淘宝相关工具
 * 
 * @author akwei
 */
public class TaoBaoUtil {

	private static final Log log = LogFactory.getLog(TaoBaoUtil.class);

	private static String PARAM_KEY_PREFIX = "=";

	private static String PARAM_PREFIX = "&";

	private static String URL_ITEM_DETAIL = "/auction/item_detail.htm?";

	private static String URL_ITEM = "/item.htm?";

	private static String PARAM_URL_BEGIN_ITEM_DETAIL_ID = "item_num_id=";

	private static String PARAM_URL_BEGIN_ITEM_ID = "id=";

	private static TaobaoRestClient client;

	public static String taobao_base_url = "http://item.taobao.com/item.htm?id=";

	// private static TaobaoJsonRestClient jsonRestClient;
	private static TaobaokeDirectRestClient taobaokeDirectRestClient;

	// private static String appkey = "test";
	//
	// private static String secret = "test";
	//
	// private static String serviceUrl =
	// "http://gw.api.tbsandbox.com/router/rest";
	private static byte testflg = ApiInvoke.TESTFLG_PUBLICTEST;

	private static int invoke_count = 5000;

	private static String serviceUrl = "http://gw.api.taobao.com/router/rest";

	private static String appkey = "12130917";

	private static String secret = "00fb2a19b73de74d3561cf41dbea38b6";

	private static String version = "2.0";

	private static String partnerid = "";

	private static String taobaoke_nick = "larrywong";
	static {
		try {
			client = new TaobaoXmlRestClient(serviceUrl, version, appkey,
					secret);
			// jsonRestClient = new TaobaoJsonRestClient(serviceUrl, appkey,
			// secret);
			taobaokeDirectRestClient = new TaobaokeDirectJsonRestClient(
					partnerid, serviceUrl, appkey, secret);
		}
		catch (TaobaoApiException e) {
			e.printStackTrace();
			log.error("======== 淘宝app 认证错误 ==================");
		}
	}

	/**
	 * 分析淘宝商品url，获得淘宝商品的num_iid，淘宝url为2种。
	 * (1)http://item.taobao.com/auction/item_detail.htm?item_num_id=7123592138，
	 * (1)http://item.taobao.com/item.htm?id=7123592138
	 * 主要以这2种进行分析
	 * 
	 * @param taobao_item_url
	 * @return
	 *         2010-8-29
	 */
	public static long getTaoBaoItemNum_iidFromUrl(String taobao_item_url) {
		long item_num_iid = 0;
		if (taobao_item_url.indexOf(URL_ITEM_DETAIL) != -1) {
			int begin = taobao_item_url.indexOf('?') + 1;
			String param_part = taobao_item_url.substring(begin);
			item_num_iid = parseTaoBaoUrlParam(param_part,
					PARAM_URL_BEGIN_ITEM_DETAIL_ID);
		}
		else if (taobao_item_url.indexOf(URL_ITEM) != -1) {
			int begin = taobao_item_url.indexOf('?') + 1;
			String param_part = taobao_item_url.substring(begin);
			item_num_iid = parseTaoBaoUrlParam(param_part,
					PARAM_URL_BEGIN_ITEM_ID);
		}
		return item_num_iid;
	}

	/**
	 * 分析淘宝客url替换现有outer_code
	 * 例如淘宝客 url
	 * http://s.click.taobao.com/t_8?e=7HZ6jHSTarHW1Psl8X9UIeujvbt6%2BJW%2F35
	 * oFNltL3wMfF6PIo8pPsOk84mUyERobm40oZGEni4umaMlW7765IU7zrpJKASwfLxnfv5dYqab
	 * %2BcpPT&p=mm_17957674_0_0&n=19&u=12124174guwenakwei
	 * 
	 * @param click_url
	 * @param outer_code
	 * @return
	 *         2010-9-19
	 */
	public static String getClick_url(String click_url, String outer_code) {
		int idx = click_url.indexOf('?');
		if (idx == -1) {
			return click_url;
		}
		String uri = click_url.substring(0, idx);
		String[] params = click_url.substring(idx + 1).split("&");
		if (params == null) {
			return click_url;
		}
		for (int i = 0; i < params.length; i++) {
			// list.add(kv.split("&"));
			if (params[i].split("=")[0].equals("u")) {
				params[i] = "u=" + TaoBaoUtil.getAppkey() + outer_code;
			}
		}
		int lastidx = params.length - 1;
		StringBuilder sb = new StringBuilder(uri).append("?");
		for (int i = 0; i < params.length; i++) {
			sb.append(params[i]);
			if (i < lastidx) {
				sb.append("&");
			}
		}
		return sb.toString();
	}

	/**
	 * 获得商品数字id
	 * 
	 * @param param_part 参数部分类似于queryString
	 * @param param_key 商品id的key
	 * @return
	 *         2010-8-29
	 */
	private static long parseTaoBaoUrlParam(String param_part, String param_key) {
		long item_num_iid = 0;
		String[] params = param_part.split(PARAM_PREFIX);
		for (String p : params) {
			if (p.startsWith(param_key)) {
				String[] key_value = p.split(PARAM_KEY_PREFIX);
				if (key_value.length > 1) {
					String id_str = key_value[1].replaceAll("#", "");
					try {
						item_num_iid = Long.valueOf(id_str);
					}
					catch (NumberFormatException e) {
					}
					break;
				}
			}
		}
		return item_num_iid;
	}

	private static void processInvoke() throws TaoBaoAccessLimitException {
		if (testflg == ApiInvoke.TESTFLG_TEST) {
			return;
		}
		ApiInvokeService apiInvokeService = (ApiInvokeService) HkUtil
				.getBean("apiInvokeService");
		ApiInvoke apiInvoke = apiInvokeService.getApiInvokeByTestflg(testflg);
		if (apiInvoke != null) {
			if (testflg == ApiInvoke.TESTFLG_PUBLICTEST) {// 正式测试环境
				if (apiInvoke.getInvoke_count() >= invoke_count) {
					throw new TaoBaoAccessLimitException(
							"out of taobao publictest limit rate 5000 one day",
							apiInvoke.getInvoke_count());
				}
			}
			else {// 正式环境
				ApiInvokeRate apiInvokeRate = apiInvokeService
						.initApiInvokeRateForLast();
				if (apiInvoke.getInvoke_count() >= apiInvokeRate.getRate()) {
					throw new TaoBaoAccessLimitException(
							"out of taobao public limit rate "
									+ apiInvokeRate.getRate() + " one min",
							apiInvoke.getInvoke_count());
				}
			}
		}
		apiInvokeService.addInvoke_count(1, testflg, new Date());
	}

	public static List<Tb_Item_Cat> getItemCatList(long parent_cid,
			boolean checkInvoke) throws TaobaoApiException,
			TaoBaoAccessLimitException {
		if (checkInvoke) {
			processInvoke();
		}
		ItemCatsGetRequest req = new ItemCatsGetRequest();
		req.setFields("cid,name,status,sort_order,is_parent,parent_cid");
		req.setParentCid(parent_cid + "");
		ItemCatsResponse res = null;
		int err_repeat = 1;
		for (int i = 0; i < err_repeat; i++) {
			try {
				res = client.itemCatsGet(req);
				break;
			}
			catch (TaobaoApiException e) {
				if (i >= err_repeat) {
					throw e;
				}
				try {
					Thread.sleep(200);
				}
				catch (InterruptedException e1) {// 线程异常忽略
				}
			}
		}
		if (res == null) {
			return null;
		}
		List<ItemCat> catlist = res.getItemCats();
		List<Tb_Item_Cat> list = new ArrayList<Tb_Item_Cat>();
		Tb_Item_Cat o = null;
		String status_str = null;
		if (catlist != null) {
			for (ItemCat cat : catlist) {
				o = new Tb_Item_Cat();
				o.setCid(Long.valueOf(cat.getCid()));
				o.setParent_cid(Long.valueOf(cat.getParentCid()));
				o.setName(cat.getName());
				status_str = cat.getStatus();
				if (status_str.equals("normal")) {
					o.setStatus(Tb_Item_Cat.STATUS_NORMAL);
				}
				else {
					o.setStatus(Tb_Item_Cat.STATUS_DELETE);
				}
				o.setSort_order(cat.getSortOrder());
				if (cat.getIsParent()) {
					o.setParentflg(Tb_Item_Cat.PARENTFLG_Y);
				}
				else {
					o.setParentflg(Tb_Item_Cat.PARENTFLG_N);
				}
				o.setChild_update(Tb_Item_Cat.CHILD_UPDATE_N);
				list.add(o);
			}
		}
		return list;
	}

	public static Item getItemByTaoBao_Item_urlForTaoBaoKe(
			String taobao_item_url, String outerCode, boolean checkInvoke)
			throws TaobaoApiException, TaoBaoAccessLimitException {
		long num_iid = getTaoBaoItemNum_iidFromUrl(taobao_item_url);
		return getItemByNum_iidForTaobaoKe(num_iid + "", outerCode, checkInvoke);
	}

	public static Item getItemByNum_iidForTaobaoKe(String num_iid,
			String outerCode, boolean checkInvoke) throws TaobaoApiException,
			TaoBaoAccessLimitException {
		TaobaokeItemDetail taobaokeItemDetail = getTaobaokeItemDetailByNum_iidForTaobaoKe(
				num_iid, outerCode, checkInvoke);
		if (taobaokeItemDetail == null) {
			return null;
		}
		return taobaokeItemDetail.getItem();
	}

	private static String item_field = "click_url,shop_click_url,seller_credit_score,title,"
			+ "detail_url,num_iid,title,nick,type,cid,one_station,"
			+ "pic_url,num,valid_thru,list_time,delist_time,stuff_status,location,"
			+ "price,freight_payer,has_invoice,has_warranty,item_img,modified,product_id,volume";

	public static TaobaokeItemDetail getTaobaokeItemDetailByNum_iidForTaobaoKe(
			String num_iid, String outerCode, boolean checkInvoke)
			throws TaobaoApiException, TaoBaoAccessLimitException {
		List<TaobaokeItemDetail> list = getTaobaokeItemDetailListByNum_iidsForTaobaoKe(
				new String[] { num_iid }, outerCode, checkInvoke);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	public static TaobaokeItemDetail getTaobaokeItemDetailByTaoBao_Item_urlForTaobaoKe(
			String taobao_item_url, String outerCode, boolean checkInvoke)
			throws TaobaoApiException, TaoBaoAccessLimitException {
		long num_iid = getTaoBaoItemNum_iidFromUrl(taobao_item_url);
		return getTaobaokeItemDetailByNum_iidForTaobaoKe(num_iid + "",
				outerCode, checkInvoke);
	}

	/**
	 * 确定唯一商品获取佣金等数据
	 * 
	 * @param title
	 * @param cid
	 * @param area
	 * @param price
	 * @param outerCode
	 * @return
	 * @throws TaobaoApiException
	 * @throws TaoBaoAccessLimitException
	 *             2010-9-18
	 */
	public static List<TaobaokeItem> getTaobaokeItemListByKey(String title,
			String cid, String area, String price, String outerCode,
			boolean checkInvoke) throws TaobaoApiException,
			TaoBaoAccessLimitException {
		if (checkInvoke) {
			processInvoke();
		}
		TaobaokeItemsGetRequest taobaokeItemsGetRequest = new TaobaokeItemsGetRequest();
		taobaokeItemsGetRequest
				.setFields("num_iid,title,price,commission,commission_rate,commission_num,commission_volume,volume");
		taobaokeItemsGetRequest.setKeyword(title);
		taobaokeItemsGetRequest.setCid(cid);
		taobaokeItemsGetRequest.setArea(area);
		taobaokeItemsGetRequest.setStartPrice(price);
		taobaokeItemsGetRequest.setEndPrice(price);
		taobaokeItemsGetRequest.setNick(taobaoke_nick);
		taobaokeItemsGetRequest.setPageSize(40);
		if (outerCode != null) {
			taobaokeItemsGetRequest.setOuterCode(outerCode);
		}
		TaobaokeItemsGetResponse taobaokeItemsGetResponse = taobaokeDirectRestClient
				.taobaokeItemsGet(taobaokeItemsGetRequest);
		return taobaokeItemsGetResponse.getTaobaokeItems();
	}

	/**
	 * 确定唯一商品获取佣金等数据
	 * 
	 * @param title
	 * @param cid
	 * @param area
	 * @param price
	 * @param outerCode
	 * @return
	 * @throws TaobaoApiException
	 * @throws TaoBaoAccessLimitException
	 *             2010-9-18
	 */
	public static List<TaobaokeItem> getTaobaokeItemListByItem(Item item,
			String outerCode, boolean checkInvoke) throws TaobaoApiException,
			TaoBaoAccessLimitException {
		return getTaobaokeItemListByKey(item.getTitle(), item.getCid(), item
				.getLocation().getCity(), item.getPrice(), outerCode,
				checkInvoke);
	}

	public static final String TAOBAOKEITEMLIST_FIELDS = "num_iid,title,nick,pic_url,price,click_url,commission,commission_rate"
			+ ",commission_num,commission_volume,shop_click_url,seller_credit_score"
			+ ",taobaoke_cat_click_url,item_location,volume,keyword_click_url";

	public static TaobaokeItemsGetResponse getTaobaokeItemList(
			TaobaokeCdn taobaokeCdn, String outerCode, boolean checkInvoke)
			throws TaobaoApiException, TaoBaoAccessLimitException {
		if (checkInvoke) {
			processInvoke();
		}
		TaobaokeItemsGetRequest req = new TaobaokeItemsGetRequest();
		req.setFields(TAOBAOKEITEMLIST_FIELDS);
		req.setKeyword(taobaokeCdn.getKeyword());
		req.setCid(taobaokeCdn.getCid());
		req.setArea(taobaokeCdn.getArea());
		if (taobaokeCdn.getStart_price() > 0) {
			req.setStartPrice(String.valueOf(taobaokeCdn.getStart_price()));
			req.setEndPrice(String.valueOf(taobaokeCdn.getEnd_price()));
		}
		req.setAutoSend(taobaokeCdn.getAuto_send());
		if (taobaokeCdn.getStart_credit() != null) {
			req.setStartCredit(String.valueOf(taobaokeCdn.getStart_credit()));
			req.setEndCredit(String.valueOf(taobaokeCdn.getEnd_credit()));
		}
		req.setNick(taobaoke_nick);
		req.setPageSize(taobaokeCdn.getPage_size());
		req.setPageNo(taobaokeCdn.getPage_no());
		req.setSort(taobaokeCdn.getSort());
		req.setIsGuarantee(taobaokeCdn.getGuarantee());
		req.setStartCommissionRate(taobaokeCdn.getStart_commissionRate());
		req.setEndCommissionRate(taobaokeCdn.getEnd_commissionRate());
		req.setStartCommissionNum(taobaokeCdn.getStart_commissionNum());
		req.setEndCommissionNum(taobaokeCdn.getEnd_commissionNum());
		req.setStartTotalnum(taobaokeCdn.getStart_totalnum());
		req.setEndTotalnum(taobaokeCdn.getEnd_totalnum());
		req.setCashCoupon(taobaokeCdn.getCash_coupon());
		req.setVipCard(taobaokeCdn.getVip_card());
		req.setOverseasItem(taobaokeCdn.getOverseas_item());
		req.setSevendaysReturn(taobaokeCdn.getSevendays_return());
		req.setRealDescribe(taobaokeCdn.getReal_describe());
		req.setOnemonthRepair(taobaokeCdn.getOnemonth_repair());
		req.setCashOndelivery(taobaokeCdn.getCash_ondelivery());
		req.setMallItem(taobaokeCdn.getMall_item());
		if (outerCode != null) {
			req.setOuterCode(outerCode);
		}
		return taobaokeDirectRestClient.taobaokeItemsGet(req);
	}

	public static List<TaobaokeItemDetail> getTaobaokeItemDetailListByNum_iidsForTaobaoKe(
			String[] num_iid, String outerCode, boolean checkInvoke)
			throws TaobaoApiException, TaoBaoAccessLimitException {
		StringBuilder sb = new StringBuilder();
		for (String n : num_iid) {
			sb.append(n).append(",");
		}
		return getTaobaokeItemDetailListByNum_iidsForTaobaoKe(sb.toString(),
				outerCode, checkInvoke);
	}

	public static List<TaobaokeItemDetail> getTaobaokeItemDetailListByNum_iidsForTaobaoKe(
			String num_iids, String outerCode, boolean checkInvoke)
			throws TaobaoApiException, TaoBaoAccessLimitException {
		if (checkInvoke) {
			processInvoke();
		}
		TaobaokeItemsDetailGetRequest request = new TaobaokeItemsDetailGetRequest();
		request.setFields(item_field);
		request.setNick(taobaoke_nick);
		if (outerCode != null) {
			request.setOuterCode(outerCode);
		}
		request.setNumIids(num_iids);
		TaobaokeItemsDetailGetResponse response = taobaokeDirectRestClient
				.taobaokeItemsDetailGet(request);
		// P.println(response.getBody());
		List<TaobaokeItemDetail> list = response.getTaobaokeItemDetails();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}

	@SuppressWarnings("unused")
	private static Shop getTaobaoShopByNick(String nick)
			throws TaobaoApiException {
		ShopGetRequest req = new ShopGetRequest();
		req.setFields("sid,cid,title,nick,desc,pic_path,created,modified");
		req.setNick(nick);
		// ShopGetResponse resp = jsonRestClient.shopGet(req);
		// return resp.getShop();
		return null;
	}

	// public static Tb_Shop getTb_ShopByNick(String nick)
	// throws TaobaoApiException {
	// Shop shop = getTaobaoShopByNick(nick);
	// if (shop == null) {
	// return null;
	// }
	// Tb_Shop tbShop = new Tb_Shop();
	// tbShop.setTb_sid(Long.valueOf(shop.getSid()));
	// tbShop.setTitle(shop.getTitle());
	// tbShop.setCreated(shop.getCreated());
	// tbShop.setModified(shop.getModified());
	// tbShop.setCid(Long.valueOf(shop.getCid()));
	// tbShop.setIntro(shop.getDesc());
	// tbShop.setPic_path(shop.getPicPath());
	// tbShop.setNick(shop.getNick());
	// return tbShop;
	// }
	// public static List<Tb_Shop_Cat> getTb_Shop_CatList()
	// throws TaobaoApiException {
	// ShopCatsListGetResponse rsp = jsonRestClient.shopCatsListGet();
	// List<ShopCat> cats = rsp.getShopCats();
	// List<Tb_Shop_Cat> list = new ArrayList<Tb_Shop_Cat>();
	// Tb_Shop_Cat tbShopCat = null;
	// for (ShopCat o : cats) {
	// tbShopCat = new Tb_Shop_Cat();
	// tbShopCat.setCid(Long.valueOf(o.getCid()));
	// tbShopCat.setName(o.getName());
	// if (o.getIsParent()) {
	// tbShopCat.setParentflg(Tb_Shop_Cat.PARENTFLG_Y);
	// }
	// else {
	// tbShopCat.setParentflg(Tb_Shop_Cat.PARENTFLG_N);
	// }
	// tbShopCat.setParent_cid(Long.valueOf(o.getParentCid()));
	// list.add(tbShopCat);
	// }
	// return list;
	// }
	public static byte getTestflg() {
		return testflg;
	}

	public void setTestflg(byte testflg) {
		TaoBaoUtil.testflg = testflg;
	}

	public static String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		TaoBaoUtil.appkey = appkey;
	}

	public static String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		TaoBaoUtil.secret = secret;
	}

	public static String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		TaoBaoUtil.serviceUrl = serviceUrl;
	}

	public static String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		TaoBaoUtil.partnerid = partnerid;
	}

	public static String getTaobaoke_nick() {
		return taobaoke_nick;
	}

	public void setTaobaoke_nick(String taobaokeNick) {
		taobaoke_nick = taobaokeNick;
	}

	// public static void main(String[] args) throws TaobaoApiException,
	// TaoBaoAccessLimitException {
	// String outerCode = null;
	// String taobao_item_url =
	// "http://item.taobao.com/item.htm?id=3627320683";
	// TaobaokeItemDetail detail =
	// getTaobaokeItemDetailByTaoBao_Item_urlForTaobaoKe(
	// taobao_item_url, "1", false);
	// if (detail == null || detail.getItem() == null) {
	// P.println("no item");
	// return;
	// }
	// P.println(detail.getClickUrl());
	// List<TaobaokeItem> list = getTaobaokeItemListByKey(detail.getItem()
	// .getTitle(), detail.getItem().getCid(), detail.getItem()
	// .getLocation().getCity(), detail.getItem().getPrice(),
	// outerCode, false);
	// for (TaobaokeItem o : list) {
	// P.println(o.getNumIid() + " | " + o.getPrice() + " | "
	// + o.getCommission() + " | " + o.getCommissionRate() + " | "
	// + o.getVolume());
	// }
	// String click_url =
	// "http://s.click.taobao.com/t_8?e=7HZ6jHSTarHW1Psl8X9UIeujvbt6%2BJW%2F35oFNltL3wMfF6PIo8pPsOk84mUyERobm40oZGEni4umaMlW7765IU7zrpJKASwfLxnfv5dYqab%2BcpPT&p=mm_17957674_0_0&n=19&u=12124174guwen";
	// P.println(click_url);
	// P.println(getClick_url(click_url, "1"));
	// String taobao_item_url =
	// "http://item.taobao.com/auction/item_detail.htm?item_num_id=7123592138#";
	// P.println(TaoBaoUtil.getTaoBaoItemNum_iidFromUrl(taobao_item_url));
	// taobao_item_url = "http://item.taobao.com/item.htm?id=7123592138#";
	// P.println(TaoBaoUtil.getTaoBaoItemNum_iidFromUrl(taobao_item_url));
	// }
	public static void main(String[] args) {
		// TaobaokeCdn taobaokeCdn = new TaobaokeCdn();
		// taobaokeCdn.setPage_size(20);
		// taobaokeCdn.setCid("11");
		// taobaokeCdn.setStart_commissionRate("1000");
		// taobaokeCdn.setEnd_commissionRate("2000");
		// taobaokeCdn.setStart_commissionNum("8");
		// taobaokeCdn.setEnd_commissionNum("100");
		// taobaokeCdn.setSort("commissionRate_asc");
		// TaobaokeItemsGetResponse resp = TaoBaoUtil.getTaobaokeItemList(
		// taobaokeCdn, null, false);
		// List<TaobaokeItem> list = resp.getTaobaokeItems();
		// for (TaobaokeItem o : list) {
		// P.println(o.getNumIid() + "|" + o.getCommissionRate() + "|"
		// + o.getCommissionNum() + "|" + o.getTitle());
		// }
		//		
		//		
		//		
		// TaobaokeItemsGetRequest req = new TaobaokeItemsGetRequest();
		// req.setFields("num_iid,commission_rate,commission_num");
		// req.setCid("11");
		// req.setNick(taobaoke_nick);
		// req.setPageSize(10);
		// req.setStartCommissionRate("1600");
		// req.setEndCommissionRate("2000");
		// req.setStartCommissionNum("8");
		// req.setEndCommissionNum("100");
		// req.setSort("commissionRate_asc");
		// TaobaokeItemsGetResponse resp = taobaokeDirectRestClient
		// .taobaokeItemsGet(req);
		// P.println(resp.getBody());
		// List<TaobaokeItem> list = resp.getTaobaokeItems();
		// for (TaobaokeItem o : list) {
		// P.println(o.getNumIid() + "|" + o.getCommissionRate() + "|"
		// + o.getCommissionNum());
		// }
		// TaobaokeItemsDetailGetRequest request = new
		// TaobaokeItemsDetailGetRequest();
		// request.setFields(item_field);
		// request.setNick(taobaoke_nick);
		// request.setNumIids("7061033000");
		// // request.setNumIids("7378606709,7638794071");
		// TaobaokeItemsDetailGetResponse response = taobaokeDirectRestClient
		// .taobaokeItemsDetailGet(request);
		// P.println(response.getBody());
		//		
		// try {
		// TaoBaoUtil
		// .getTaobaokeItemDetailByTaoBao_Item_urlForTaobaoKe(
		// "http://item.taobao.com/item.htm?id=7061033000&cm_cat=50029375&source=dou",
		// null, false);
		// }
		// catch (TaoBaoAccessLimitException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}