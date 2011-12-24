package com.hk.web.admin.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.City;
import com.hk.bean.Country;
import com.hk.bean.Province;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.ZoneService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

@Component("/admin/zone")
public class AdminZoneAction extends BaseAction {
	@Autowired
	private ZoneService zoneService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		List<Country> list = this.zoneService.getCountryList();
		req.setAttribute("list", list);
		return this.getWapJsp("/admin/zone/countrylist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String provincelist(HkRequest req, HkResponse resp) throws Exception {
		int countryId = req.getIntAndSetAttr("countryId");
		Country country = this.zoneService.getCountry(countryId);
		req.setAttribute("country", country);
		List<Province> list = this.zoneService.getProvinceList(countryId);
		req.setAttribute("list", list);
		return this.getWapJsp("/admin/zone/provincelist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String citylist(HkRequest req, HkResponse resp) throws Exception {
		int provinceId = req.getIntAndSetAttr("provinceId");
		Province province = this.zoneService.getProvince(provinceId);
		req.setAttribute("province", province);
		List<City> list = this.zoneService.getCityList(provinceId);
		req.setAttribute("list", list);
		return this.getWapJsp("/admin/zone/citylist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createcountry(HkRequest req, HkResponse resp)
			throws Exception {
		String country = req.getString("country");
		Country o = new Country();
		o.setCountry(DataUtil.toHtmlRow(country));
		req.setAttribute("o", o);
		if (!this.zoneService.createCountry(o)) {
			req.setText(String.valueOf(Err.ZONE_COUNTRY_NAME_DUPLICATE));
			return "/admin/zone.do";
		}
		this.setOpFuncSuccessMsg(req);
		return "r:/admin/zone.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createprovince(HkRequest req, HkResponse resp)
			throws Exception {
		int countryId = req.getIntAndSetAttr("countryId");
		String province = req.getString("province");
		Province o = new Province();
		o.setProvince(DataUtil.toHtmlRow(province));
		o.setCountryId(countryId);
		req.setAttribute("o", o);
		if (!this.zoneService.createProvince(o)) {
			req.setText(String.valueOf(Err.ZONE_PROVINCE_NAME_DUPLICATE));
			return "/admin/zone_provincelist.do";
		}
		this.setOpFuncSuccessMsg(req);
		return "r:/admin/zone_provincelist.do?countryId=" + countryId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createcity(HkRequest req, HkResponse resp) throws Exception {
		int provinceId = req.getIntAndSetAttr("provinceId");
		Province province = this.zoneService.getProvince(provinceId);
		String city = req.getString("city");
		City o = new City();
		o.setCity(DataUtil.toHtmlRow(city));
		o.setProvinceId(provinceId);
		o.setCountryId(province.getCountryId());
		req.setAttribute("o", o);
		if (!this.zoneService.createCity(o)) {
			req.setText(String.valueOf(Err.ZONE_CITY_NAME_DUPLICATE));
			return "/admin/zone_citylist.do";
		}
		this.setOpFuncSuccessMsg(req);
		return "r:/admin/zone_citylist.do?provinceId=" + provinceId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editcity(HkRequest req, HkResponse resp) throws Exception {
		int cityId = req.getIntAndSetAttr("cityId");
		City o = (City) req.getAttribute("o");
		if (o == null) {
			o = this.zoneService.getCity(cityId);
		}
		req.setAttribute("o", o);
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWapJsp("admin/zone/editcity.jsp");
		}
		String city = req.getString("city");
		if (!DataUtil.isEmpty(city)) {
			o.setCity(DataUtil.toHtmlRow(city));
			if (!this.zoneService.updateCity(o)) {
				req.setText(String.valueOf(Err.ZONE_CITY_NAME_DUPLICATE));
				return "/admin/zone_editcity.do?ch=0";
			}
			this.setOpFuncSuccessMsg(req);
		}
		return "r:/admin/zone_citylist.do?provinceId=" + o.getProvinceId();
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editprovince(HkRequest req, HkResponse resp) throws Exception {
		int provinceId = req.getIntAndSetAttr("provinceId");
		Province o = (Province) req.getAttribute("o");
		if (o == null) {
			o = this.zoneService.getProvince(provinceId);
		}
		req.setAttribute("o", o);
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWapJsp("admin/zone/editprovince.jsp");
		}
		String province = req.getString("province");
		if (!DataUtil.isEmpty(province)) {
			o.setProvince(DataUtil.toHtmlRow(province));
			if (!this.zoneService.updateProvince(o)) {
				req.setText(String.valueOf(Err.ZONE_PROVINCE_NAME_DUPLICATE));
				return "/admin/zone_editprovince.do?ch=0";
			}
			this.setOpFuncSuccessMsg(req);
		}
		return "r:/admin/zone_provincelist.do?countryId=" + o.getCountryId();
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editcountry(HkRequest req, HkResponse resp) throws Exception {
		int countryId = req.getIntAndSetAttr("countryId");
		Country o = (Country) req.getAttribute("o");
		if (o == null) {
			o = this.zoneService.getCountry(countryId);
		}
		req.setAttribute("o", o);
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWapJsp("admin/zone/editcountry.jsp");
		}
		String country = req.getString("country");
		if (!DataUtil.isEmpty(country)) {
			o.setCountry(DataUtil.toHtmlRow(country));
			if (!this.zoneService.updateCountry(o)) {
				req.setText(String.valueOf(Err.ZONE_COUNTRY_NAME_DUPLICATE));
				return "/admin/zone_editcountry.do?ch=0";
			}
			this.setOpFuncSuccessMsg(req);
		}
		return "r:/admin/zone.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String delete(HkRequest req, HkResponse resp) {
		req.reSetAttribute("cityId");
		req.reSetAttribute("provinceId");
		req.reSetAttribute("countryId");
		req.reSetAttribute("method");
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWapJsp("admin/zone/delete.jsp");
		}
		String method = req.getString("method");
		if (method.equals("country")) {
			int countryId = req.getInt("countryId");
			if (req.getString("ok") != null) {
				this.zoneService.deleteCountry(countryId);
				this.setOpFuncSuccessMsg(req);
			}
			return "r:/admin/zone.do";
		}
		if (method.equals("province")) {
			int provinceId = req.getInt("provinceId");
			Province province = this.zoneService.getProvince(provinceId);
			if (req.getString("ok") != null) {
				this.zoneService.deleteProvince(provinceId);
				this.setOpFuncSuccessMsg(req);
			}
			return "r:/admin/zone_provincelist.do?countryId="
					+ province.getCountryId();
		}
		if (method.equals("city")) {
			int cityId = req.getInt("cityId");
			City city = this.zoneService.getCity(cityId);
			if (req.getString("ok") != null) {
				this.zoneService.deleteCity(cityId);
				this.setOpFuncSuccessMsg(req);
			}
			return "r:/admin/zone_citylist.do?provinceId="
					+ city.getProvinceId();
		}
		return null;
	}
}