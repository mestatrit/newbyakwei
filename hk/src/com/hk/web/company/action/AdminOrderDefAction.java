package com.hk.web.company.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.City;
import com.hk.bean.CompanyKind;
import com.hk.bean.CompanyKindUtil;
import com.hk.bean.HkObjKeyTagOrderDef;
import com.hk.bean.HkObjOrderDef;
import com.hk.bean.KeyTag;
import com.hk.bean.Province;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.KeyTagService;
import com.hk.svr.OrderDefService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.pub.action.BaseAction;

@Component("/admin/orderdef")
public class AdminOrderDefAction extends BaseAction {
	@Autowired
	private OrderDefService orderDefService;

	@Autowired
	private KeyTagService keyTagService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 列出所有分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String findkeytag(HkRequest req, HkResponse resp) throws Exception {
		this.setZone(req);
		String name = req.getString("name");
		if (!DataUtil.isEmpty(name)) {
			KeyTag keyTag = keyTagService.getKeyTagByName(name);
			if (keyTag == null) {
				req.setText("nodataview");
				req.setAttribute("name", name);
				req.setEncodeAttribute("name", name);
			}
			else {// 如果找到，就到关键词设置页面
				return "r:/admin/orderdef_keytag.do?tagId=" + keyTag.getTagId()
						+ "&cityId=" + req.getInt("cityId") + "&provinceId="
						+ req.getInt("provinceId");
			}
		}
		return this.getWapJsp("admin/orderdef/findkeytag.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String keytag(HkRequest req, HkResponse resp) throws Exception {
		this.setZone(req);
		long tagId = req.getLong("tagId");
		KeyTag keyTag = (KeyTag) req.getAttribute("keyTag");
		if (keyTag == null) {
			keyTag = this.keyTagService.getKeyTag(tagId);
		}
		HkObjKeyTagOrderDef o = (HkObjKeyTagOrderDef) req.getAttribute("o");
		if (o == null) {
			o = this.orderDefService.getHkObjKeyTagOrderDef(tagId, 0);
		}
		req.setAttribute("o", o);
		req.setAttribute("keyTag", keyTag);
		req.setAttribute("tagId", tagId);
		return this.getWapJsp("admin/orderdef/keytag.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addkeytag(HkRequest req, HkResponse resp) throws Exception {
		this.setZone(req);
		String name = req.getString("name");
		if (!DataUtil.isEmpty(name)) {
			long tagId = this.keyTagService.createKeyTag(name);
			return "r:/admin/orderdef_keytag.do?tagId=" + tagId + "&cityId="
					+ req.getInt("cityId") + "&provinceId="
					+ req.getInt("provinceId");
		}
		return "/admin/orderdef_keytag.do";
	}

	/**
	 * 列出所有分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String cmpkindlist(HkRequest req, HkResponse resp) throws Exception {
		List<CompanyKind> list = CompanyKindUtil.getCompanKindList();
		req.setAttribute("list", list);
		this.setZone(req);
		return this.getWapJsp("admin/orderdef/cmpkindlist.jsp");
	}

	private void setZone(HkRequest req) {
		City city = ZoneUtil.getCity(req.getInt("cityId"));
		Province province = ZoneUtil.getProvince(req.getInt("provinceId"));
		req.setAttribute("city", city);
		req.setAttribute("province", province);
		String zone = null;
		if (city != null) {
			zone = city.getCity();
		}
		else if (province != null) {
			zone = province.getProvince();
		}
		else {
			zone = "全国";
		}
		req.setAttribute("zone", zone);
		req.reSetAttribute("cityId");
		req.reSetAttribute("provinceId");
	}

	/**
	 * 查看分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String cmpkind(HkRequest req, HkResponse resp) throws Exception {
		int kindId = req.getInt("kindId");
		CompanyKind companyKind = CompanyKindUtil.getCompanyKind(kindId);
		HkObjOrderDef o1 = (HkObjOrderDef) req.getAttribute("o1");
		if (o1 == null) {
			o1 = this.orderDefService
					.getHkObjOrderDef(HkObjOrderDef.KIND_LEVEL_1, kindId,
							getPcityIdFromCity(req));
		}
		HkObjOrderDef o2 = (HkObjOrderDef) req.getAttribute("o2");
		if (o2 == null) {
			o2 = this.orderDefService
					.getHkObjOrderDef(HkObjOrderDef.KIND_LEVEL_2, kindId,
							getPcityIdFromCity(req));
		}
		req.setAttribute("o1", o1);
		req.setAttribute("o2", o2);
		req.setAttribute("kindId", kindId);
		req.setAttribute("companyKind", companyKind);
		this.setZone(req);
		return this.getWapJsp("admin/orderdef/cmpkind.jsp");
	}

	private int getPcityIdFromCity(HkRequest req) {
		int cityId = req.getInt("cityId");
		int provinceId = req.getInt("provinceId");
		int pcityId = ZoneUtil.getPcityId(cityId, provinceId);
		return pcityId;
	}

	/**
	 * 修改这个分类在某个地区的最低竞价
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		int kindId = req.getInt("kindId");
		int money1 = req.getInt("money1");
		int money2 = req.getInt("money2");
		money1 = Math.abs(money1);
		money2 = Math.abs(money2);
		int cityId = req.getInt("cityId");
		int provinceId = req.getInt("provinceId");
		int pcityId = ZoneUtil.getPcityId(cityId, provinceId);
		HkObjOrderDef o1 = new HkObjOrderDef();
		o1.setCityId(pcityId);
		o1.setMoney(money1);
		o1.setKind(HkObjOrderDef.KIND_LEVEL_1);
		o1.setKindId(kindId);
		HkObjOrderDef o2 = new HkObjOrderDef();
		o2.setCityId(pcityId);
		o2.setMoney(money2);
		o2.setKind(HkObjOrderDef.KIND_LEVEL_2);
		o2.setKindId(kindId);
		req.setAttribute("o1", o1);
		req.setAttribute("o2", o2);
		int code = o1.validate();
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/admin/orderdef_cmpkind.do";
		}
		code = o2.validate();
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/admin/orderdef_cmpkind.do";
		}
		this.orderDefService.updateHkObjOrderDef(o1);
		this.orderDefService.updateHkObjOrderDef(o2);
		req.setSessionText("op.submitinfook");
		return "r:/admin/orderdef_cmpkindlist.do?cityId=" + cityId
				+ "&provinceId=" + provinceId;
	}

	/**
	 * 修改关键词在某个地区的最低竞价
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String update2(HkRequest req, HkResponse resp) throws Exception {
		long tagId = req.getLong("tagId");
		int money = req.getInt("money");
		money = Math.abs(money);
		int cityId = req.getInt("cityId");
		int provinceId = req.getInt("provinceId");
		int pcityId = ZoneUtil.getPcityId(cityId, provinceId);
		HkObjKeyTagOrderDef o = new HkObjKeyTagOrderDef();
		o.setCityId(pcityId);
		o.setMoney(money);
		o.setTagId(tagId);
		req.setAttribute("o", o);
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/admin/orderdef_keytag.do";
		}
		this.orderDefService.updateHkObjKeyTagOrderDef(o);
		req.setSessionText("op.submitinfook");
		return "r:/admin/orderdef_findkeytag.do?cityId=" + cityId
				+ "&provinceId=" + provinceId;
	}
}