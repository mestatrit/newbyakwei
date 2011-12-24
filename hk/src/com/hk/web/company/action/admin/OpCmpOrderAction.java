package com.hk.web.company.action.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.Company;
import com.hk.bean.CompanyKind;
import com.hk.bean.CompanyKindUtil;
import com.hk.bean.HkObj;
import com.hk.bean.HkObjKeyTagOrder;
import com.hk.bean.HkObjKindOrder;
import com.hk.bean.HkObjOrder;
import com.hk.bean.HkObjOrderDef;
import com.hk.bean.HkOrder;
import com.hk.bean.KeyTag;
import com.hk.bean.KeyTagSearchInfo;
import com.hk.bean.Pcity;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.HkObjKeyTagOrderService;
import com.hk.svr.HkObjKindOrderService;
import com.hk.svr.HkObjOrderService;
import com.hk.svr.HkObjService;
import com.hk.svr.KeyTagService;
import com.hk.svr.company.exception.NoEnoughMoneyException;
import com.hk.svr.company.exception.SmallerThanMinMoneyException;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.pub.action.BaseAction;

/**
 * 商户操作足迹排序使用
 * 
 * @author akwei
 */
@Component("/e/op/cmporder")
public class OpCmpOrderAction extends BaseAction {
	@Autowired
	private HkObjService hkObjService;

	@Autowired
	private HkObjOrderService hkObjOrderService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private KeyTagService keyTagService;

	@Autowired
	private HkObjKindOrderService hkObjKindOrderService;

	@Autowired
	private HkObjKeyTagOrderService hkObjKeyTagOrderService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return this.getWeb3Jsp("e/op/intro_order.jsp");
	}

	/**
	 * 设置停止，启动状态
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setstatus(HkRequest req, HkResponse resp) throws Exception {
		int flg = req.getInt("flg");
		long oid = req.getLong("oid");
		byte stopflg = req.getByte("stopflg");
		User loginUser = this.getLoginUser(req);
		long userId = loginUser.getUserId();
		if (stopflg == HkOrder.STOPFLG_N) {
			if (flg == 0) {// 首页
				HkObjOrder o = this.hkObjOrderService.getHkObjOrder(oid);
				if (o == null || o.getUserId() != userId) {
					return null;
				}
				this.hkObjOrderService.run(oid);
			}
			else if (flg == 1) {// 关键词
				HkObjKeyTagOrder o = this.hkObjKeyTagOrderService
						.getHkObjKeyTagOrder(oid);
				if (o == null || o.getUserId() != userId) {
					return null;
				}
				this.hkObjKeyTagOrderService.run(oid);
			}
			else if (flg == 2) {// 分类
				HkObjKindOrder o = this.hkObjKindOrderService
						.getHkObjKindOrder(oid);
				if (o == null || o.getUserId() != userId) {
					return null;
				}
				this.hkObjKindOrderService.run(oid);
			}
		}
		else if (stopflg == HkOrder.STOPFLG_Y) {
			if (flg == 0) {// 首页
				HkObjOrder o = this.hkObjOrderService.getHkObjOrder(oid);
				if (o == null || o.getUserId() != userId) {
					return null;
				}
				this.hkObjOrderService.stop(oid);
			}
			else if (flg == 1) {// 关键词
				HkObjKeyTagOrder o = this.hkObjKeyTagOrderService
						.getHkObjKeyTagOrder(oid);
				if (o == null || o.getUserId() != userId) {
					return null;
				}
				this.hkObjKeyTagOrderService.stop(oid);
			}
			else if (flg == 2) {// 分类
				HkObjKindOrder o = this.hkObjKindOrderService
						.getHkObjKindOrder(oid);
				if (o == null || o.getUserId() != userId) {
					return null;
				}
				this.hkObjKindOrderService.stop(oid);
			}
		}
		return this.initSuccess(req, "");
	}

	/**
	 * 转发到修改相应数据的页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toedit(HkRequest req, HkResponse resp) throws Exception {
		int flg = req.getInt("flg");
		long oid = req.getLong("oid");
		long companyId = req.getLong("companyId");
		int cityId = req.getInt("cityId");
		if (flg == 0) {// 首页
			return "r:/e/op/cmporder_toeditorder.do?oid=" + oid + "&companyId="
					+ companyId + "&cityId=" + cityId;
		}
		if (flg == 1) {// 关键词
			return "r:/e/op/cmporder_tobuykeytag.do?oid=" + oid + "&companyId="
					+ companyId + "&cityId=" + cityId;
		}
		if (flg == 2) {// 分类
			return "r:/e/op/cmporder_toeditkindorder.do?oid=" + oid
					+ "&companyId=" + companyId + "&cityId=" + cityId;
		}
		return this.getWeb3Jsp("e/op/intro_order.jsp");
	}

	/**
	 * 用户购买的竞价排名列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String myorderlist(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(this.size30);
		int flg = req.getInt("flg");
		int cityId = req.getInt("cityId", -1);
		req.setAttribute("cityId", cityId);
		req.setAttribute("flg", flg);
		if (flg == 1) {// 关键字排名列表
			req.setAttribute("op_func", 7);
			List<HkObjKeyTagOrder> list = this.hkObjKeyTagOrderService
					.getHkObjKeyTagOrderListByObjId(companyId, cityId, page
							.getBegin(), size20);
			// 组装排名对象
			List<Long> idList = new ArrayList<Long>();
			List<Long> tagidList = new ArrayList<Long>();
			for (HkObjKeyTagOrder o : list) {
				idList.add(o.getHkObjId());
				tagidList.add(o.getTagId());
			}
			Map<Long, HkObj> map = this.hkObjService.getHkObjMapInId(idList);
			Map<Long, KeyTag> tagmap = this.keyTagService
					.getKeyTagMapInId(tagidList);
			for (HkObjKeyTagOrder o : list) {
				o.setHkObj(map.get(o.getHkObjId()));
				o.setKeyTag(tagmap.get(o.getTagId()));
			}
			// 组装 end
			req.setAttribute("list", list);
		}
		else if (flg == 0) {// 首页
			req.setAttribute("op_func", 6);
			List<HkObjOrder> list = this.hkObjOrderService
					.getHkObjOrderListByObjId(companyId, cityId, page
							.getBegin(), size20);
			// 组装排名对象
			List<Long> idList = new ArrayList<Long>();
			for (HkObjOrder o : list) {
				idList.add(o.getHkObjId());
			}
			Map<Long, HkObj> map = this.hkObjService.getHkObjMapInId(idList);
			for (HkObjOrder o : list) {
				o.setHkObj(map.get(o.getHkObjId()));
			}
			// 组装 end
			req.setAttribute("list", list);
		}
		else if (flg == 2) {// 分类
			req.setAttribute("op_func", 8);
			List<HkObjKindOrder> list = this.hkObjKindOrderService
					.getHkObjKindOrderListByObjId(companyId, cityId, page
							.getBegin(), size20);
			// 组装排名对象
			List<Long> idList = new ArrayList<Long>();
			for (HkObjKindOrder o : list) {
				o.setCompanyKind(CompanyKindUtil.getCompanyKind(o.getKindId()));
				idList.add(o.getHkObjId());
			}
			Map<Long, HkObj> map = this.hkObjService.getHkObjMapInId(idList);
			for (HkObjKindOrder o : list) {
				o.setHkObj(map.get(o.getHkObjId()));
			}
			// 组装 end
			req.setAttribute("list", list);
		}
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		req.setAttribute("companyId", companyId);
		this.setZoneList(req);
		return this.getWeb3Jsp("e/op/myorderlist.jsp");
	}

	/**
	 * 修改足迹在自己分类的竞价排名信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toeditkindorder(HkRequest req, HkResponse resp)
			throws Exception {
		long oid = req.getLong("oid");
		long companyId = req.getLong("companyId");
		int cityId = req.getInt("cityId");
		int kindId = req.getInt("kindId");
		HkObjKindOrder o = null;
		if (oid > 0) {
			o = hkObjKindOrderService.getHkObjKindOrder(oid);
			if (o != null) {
				kindId = o.getKindId();
				companyId = o.getHkObjId();
			}
		}
		else {
			o = hkObjKindOrderService.getHkObjKindOrder(kindId, companyId,
					cityId);
		}
		this.setMoveOidInfo(req);
		Company company = this.companyService.getCompany(companyId);
		CompanyKind companyKind = CompanyKindUtil.getCompanyKind(company
				.getKindId());
		req.setAttribute("companyKind", companyKind);
		req.setAttribute("companyId", companyId);
		req.setAttribute("kindId", kindId);
		Pcity pcity = ZoneUtil.getPcity(cityId);
		String zone = null;
		if (pcity != null) {
			zone = pcity.getName();
		}
		else {
			zone = "全国";
		}
		req.setAttribute("zone", zone);
		req.setAttribute("pcity", pcity);
		req.reSetAttribute("cityId");
		req.setAttribute("o", o);
		if (o == null) {
			return this.getWebJsp("e/op/editkindorder.jsp");
		}
		return this.getWeb3Jsp("e/op/editkindorder2.jsp");
	}

	/**
	 * 到设置竞价排名页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String keytagorderlist(HkRequest req, HkResponse resp)
			throws Exception {
		long tagId = req.getLong("tagId");
		int cityId = req.getInt("cityId", -1);
		int count = this.hkObjKeyTagOrderService
				.countHkObjKeyTagOrderListByTagIdAndCityId(tagId, cityId);
		SimplePage page = req.getSimplePage(this.size20);
		List<HkObjKeyTagOrder> list = this.hkObjKeyTagOrderService
				.getHkObjKeyTagOrderListByTagIdAndCityId(tagId, cityId, page
						.getBegin(), this.size20);
		KeyTag keyTag = this.keyTagService.getKeyTag(tagId);
		req.setAttribute("keyTag", keyTag);
		req.setAttribute("count", count);
		req.setAttribute("tagId", tagId);
		req.setAttribute("cityId", cityId);
		req.setAttribute("list", list);
		req.setAttribute("base_page_size", this.size20 * (req.getPage() - 1));
		this.setZoneList(req);
		req.reSetAttribute("companyId");
		req.setEncodeAttribute("name", keyTag.getName());
		return this.getWeb3Jsp("e/op/keytagorderlist.jsp");
	}

	/**
	 * 到设置竞价排名页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String findkeytag(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("name");
		long companyId = req.getLong("companyId");
		if (!DataUtil.isEmpty(name)) {
			KeyTag keyTag = this.keyTagService.getKeyTagByName(name);
			if (keyTag != null) {
				// 查看购买情况
				int count = this.hkObjKeyTagOrderService
						.countHkObjKeyTagOrderListByTagIdAndCityId(keyTag
								.getTagId(), -1);
				if (count > 0) {// 如果有人购买就到清单列表页面
					return "r:/e/op/cmporder_keytagorderlist.do?tagId="
							+ keyTag.getTagId() + "&companyId=" + companyId;
				}
				// 查看当月搜索情况
				Calendar now = Calendar.getInstance();
				int year = now.get(Calendar.YEAR);
				int month = now.get(Calendar.MONTH) + 1;
				KeyTagSearchInfo keyTagSearchInfo = this.keyTagService
						.getKeyTagSearchInfoByYearAndMonth(keyTag.getTagId(),
								year, month);
				req.setAttribute("keyTagSearchInfo", keyTagSearchInfo);
			}
			req.setAttribute("keyTag", keyTag);
			req.setAttribute("name", name);
		}
		req.reSetAttribute("companyId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		req.reSetAttribute("companyId");
		return this.getWeb3Jsp("e/op/findkeytag.jsp");
	}

	/**
	 * 购买关键词竞价排名
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tobuykeytag(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		String name = req.getString("name");
		long companyId = req.getLong("companyId");
		int cityId = req.getInt("cityId");
		HkObjKeyTagOrder o = null;
		KeyTag keyTag = null;
		if (oid > 0) {
			o = this.hkObjKeyTagOrderService.getHkObjKeyTagOrder(oid);
		}
		if (o != null) {
			keyTag = this.keyTagService.getKeyTag(o.getTagId());
			companyId = o.getHkObjId();
			cityId = o.getCityId();
			if (keyTag != null) {
				name = keyTag.getName();
			}
		}
		else {
			keyTag = this.keyTagService.getKeyTagByName(name);
		}
		req.setAttribute("name", name);
		req.setAttribute("companyId", companyId);
		req.setAttribute("cityId", cityId);
		this.setZoneList(req);
		this.setMoveOidInfo(req);
		if (keyTag != null && cityId > -1) {
			if (o == null) {
				o = this.hkObjKeyTagOrderService.getHkObjKeyTagOrder(keyTag
						.getTagId(), companyId, cityId);
			}
			if (o != null) {
				req.setAttribute("o", o);
				req.setAttribute("cannotfmodify", !o.isCanUpdate());// 如果当天已经更新过，就不能再次更新
				return this.getWeb3Jsp("e/op/buykeytag2.jsp");
			}
		}
		return this.getWeb3Jsp("e/op/buykeytag.jsp");
	}

	/**
	 * 购买关键词竞价排名
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String buykeytag(HkRequest req, HkResponse resp) {
		String name = req.getString("name");
		if (DataUtil.isEmpty(name)) {
			return null;
		}
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		int money = req.getInt("money");
		money = Math.abs(money);
		int pday = req.getInt("pday");
		User loginUser = this.getLoginUser(req);
		int cityId = req.getInt("cityId");
		if (cityId < 0) {
			return this.initError(req, Err.ZONE_ERROR, "buykeytag");
		}
		long tagId = this.keyTagService.createKeyTag(name);
		if (company.getUserId() != loginUser.getUserId()) {
			return null;
		}
		long move_oid = req.getLong("move_oid");
		if (move_oid > 0) {// 如果有比较的竞价，则需要判断是否大于排在他前面的竞价，不大于，不能提交
			HkObjOrder move_hkObjOrder = this.hkObjOrderService
					.getHkObjOrder(move_oid);
			if (move_hkObjOrder != null) {
				int move_money = move_hkObjOrder.getMoney();
				if (money < move_money) {
					return this.initError(req, Err.SMALLERTHAN_MINMONEY, 1,
							new Object[] { move_money + "" }, "buykeytag");
				}
			}
		}
		HkObjKeyTagOrder o = this.hkObjKeyTagOrderService.getHkObjKeyTagOrder(
				tagId, companyId, cityId);
		if (o == null) {
			o = new HkObjKeyTagOrder();
			o.setHkObjId(companyId);
			o.setCityId(cityId);
			o.setMoney(money);
			o.setPday(pday);
			o.setTagId(tagId);
		}
		else {
			o.setMoney(money);
			o.setPday(pday);
		}
		o.setUserId(loginUser.getUserId());
		int code = o.validate();
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "buykeytag");
		}
		try {
			boolean status = this.hkObjKeyTagOrderService
					.createHkObjKeyTagOrder(o);
			if (!status) {
				return this.initError(req, Err.ORDER_DATA_ALREADY_UPDATE,
						"buykeytag");
			}
			this.setOpFuncSuccessMsg(req);
			return this.initSuccess(req, "buykeytag");
		}
		catch (NoEnoughMoneyException e) {
			return this.initError(req, Err.NOENOUGH_MONEY, "buykeytag");
		}
		catch (SmallerThanMinMoneyException e) {
			return this.initError(req, code, new Object[] { e.getMinmoney() },
					"buykeytag");
		}
	}

	/**
	 * 到设置竞价排名页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toeditorder(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		long companyId = req.getLong("companyId");
		int cityId = req.getInt("cityId");
		byte kind = HkObjOrderDef.KIND_LEVEL_1;
		this.setMoveOidInfo(req);
		HkObjOrder o = null;
		if (oid > 0) {
			o = hkObjOrderService.getHkObjOrder(oid);
		}
		else {
			o = hkObjOrderService.getHkObjOrder(companyId, kind, cityId);
		}
		req.setAttribute("companyId", companyId);
		req.setAttribute("cityId", cityId);
		req.setAttribute("o", o);
		req.setAttribute("kind", kind);
		Pcity pcity = ZoneUtil.getPcity(cityId);
		String zone = null;
		if (pcity != null) {
			zone = pcity.getName();
		}
		else {
			zone = "全国";
		}
		req.setAttribute("zone", zone);
		req.setAttribute("pcity", pcity);
		if (o != null) {
			req.setAttribute("cannotfmodify", !o.isCanUpdate());// 如果当天已经更新过，就不能再次更新
		}
		if (o != null) {
			return this.getWeb3Jsp("e/op/editorder2.jsp");
		}
		return this.getWeb3Jsp("e/op/editorder.jsp");
	}

	private void setMoveOidInfo(HkRequest req) {
		long move_oid = req.getLong("move_oid");
		if (move_oid > 0) {
			HkObjOrder move_hkObjOrder = this.hkObjOrderService
					.getHkObjOrder(move_oid);
			if (move_hkObjOrder != null) {
				int move_money = move_hkObjOrder.getMoney();
				req.setAttribute("move_oid", move_oid);
				req.setAttribute("move_money", move_money);
			}
		}
	}

	/**
	 * 到设置竞价排名页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editorder(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		int cityId = req.getInt("cityId");
		int money = req.getInt("money");// 获得的是人民币数值，需要转换为酷币
		int pday = req.getInt("pday");
		money = Math.abs(money);
		pday = Math.abs(pday);
		byte kind = HkObjOrderDef.KIND_LEVEL_1;
		long move_oid = req.getLong("move_oid");
		if (move_oid > 0) {// 如果有比较的竞价，则需要判断是否大于排在他前面的竞价，不大于，不能提交
			HkObjOrder move_hkObjOrder = this.hkObjOrderService
					.getHkObjOrder(move_oid);
			if (move_hkObjOrder != null) {
				double move_money = move_hkObjOrder.getMoney();
				if (money < move_money) {
					return this.initError(req, Err.SMALLERTHAN_MINMONEY,
							new Object[] { move_money + "" }, "edit_hkorder");
				}
			}
		}
		HkObjOrder o = hkObjOrderService.getHkObjOrder(companyId, kind, cityId);
		User loginUser = this.getLoginUser(req);
		if (o != null) {// 更新操作
			int addflg = req.getInt("addflg");
			if (addflg == 0) {
				pday = o.getPday() + pday;
			}
			else {
				pday = o.getPday() - pday;
				if (pday <= 0) {// 竞价天数不能早于当前时间
					return this.initError(req,
							Err.HKOBJORDER_PDAY_EARLIER_THAN_NOW,
							"edit_hkorder");
				}
			}
			o.setMoney(money);
			o.setPday(pday);
		}
		else {
			o = new HkObjOrder();
			o.setHkObjId(companyId);
			o.setCityId(cityId);
			o.setMoney(money);
			o.setPday(pday);
			o.setKind(kind);
		}
		o.setUserId(loginUser.getUserId());
		int code = o.validate();
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "edit_hkorder");
		}
		try {
			this.hkObjOrderService.createHkObjOrder(o);
			this.setOpFuncSuccessMsg(req);
			return this.initSuccess(req, "edit_hkorder");
		}
		catch (NoEnoughMoneyException e) {
			return this.initError(req, Err.NOENOUGH_MONEY, "edit_hkorder");
		}
		catch (SmallerThanMinMoneyException e) {
			return this.initError(req, Err.SMALLERTHAN_MINMONEY,
					new Object[] { e.getMinmoney() + "" }, "edit_hkorder");
		}
	}

	/**
	 * 设置分类竞价排名
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editkindorder(HkRequest req, HkResponse resp)
			throws Exception {
		long companyId = req.getLong("companyId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		int cityId = req.getInt("cityId");
		int money = req.getInt("money");// 获得的是人民币数值，需要转换为酷币
		int pday = req.getInt("pday");
		money = Math.abs(money);
		pday = Math.abs(pday);
		long move_oid = req.getLong("move_oid");
		if (move_oid > 0) {// 如果有比较的竞价，则需要判断是否大于排在他前面的竞价，不大于，不能提交
			HkObjOrder move_hkObjOrder = this.hkObjOrderService
					.getHkObjOrder(move_oid);
			if (move_hkObjOrder != null) {
				double move_money = move_hkObjOrder.getMoney();
				if (money < move_money) {
					return this.initError(req, Err.SMALLERTHAN_MINMONEY,
							new Object[] { move_money + "" },
							"edit_hkkindorder");
				}
			}
		}
		Company company = this.companyService.getCompany(companyId);
		HkObjKindOrder o = this.hkObjKindOrderService.getHkObjKindOrder(company
				.getKindId(), companyId, cityId);
		if (o != null) {
			int addflg = req.getInt("addflg");
			if (addflg == 0) {
				pday = o.getPday() + pday;
			}
			else {
				pday = o.getPday() - pday;
				if (pday <= 0) {// 竞价天数不能早于当前时间
					return this.initError(req,
							Err.HKOBJORDER_PDAY_EARLIER_THAN_NOW,
							"edit_hkkindorder");
				}
			}
		}
		if (o == null) {
			o = new HkObjKindOrder();
		}
		User loginUser = this.getLoginUser(req);
		o.setKindId(company.getKindId());
		o.setHkObjId(companyId);
		o.setCityId(cityId);
		o.setMoney(money);
		o.setPday(pday);
		o.setUserId(loginUser.getUserId());
		int code = o.validate();
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "edit_hkkindorder");
		}
		try {
			this.hkObjKindOrderService.createHkObjKindOrder(o);
			this.setOpFuncSuccessMsg(req);
			return this.initSuccess(req, "edit_hkkindorder");
		}
		catch (NoEnoughMoneyException e) {
			return this.initError(req, Err.NOENOUGH_MONEY, "edit_hkkindorder");
		}
		catch (SmallerThanMinMoneyException e) {
			return this.initError(req, Err.SMALLERTHAN_MINMONEY, 2,
					new Object[] { e.getMinmoney() + "" }, "edit_hkkindorder");
		}
	}
}