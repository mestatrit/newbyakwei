package iwant.web.admin;

import iwant.bean.AdminUser;
import iwant.bean.City;
import iwant.bean.District;
import iwant.bean.Province;
import iwant.bean.validate.CityValidator;
import iwant.bean.validate.DistrictValidator;
import iwant.bean.validate.ProvinceValidator;
import iwant.svr.ZoneSvr;
import iwant.svr.exception.DuplicateCityNameException;
import iwant.svr.exception.DuplicateDistrictNameException;
import iwant.svr.exception.DuplicateProvinceNameException;
import iwant.svr.exception.ProvinceNotFoundException;
import iwant.web.BaseAction;
import iwant.web.admin.util.AdminUtil;
import iwant.web.admin.util.Err;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dev3g.cactus.web.action.HkRequest;
import com.dev3g.cactus.web.action.HkResponse;

@Component("/mgr/zone")
public class ZoneAction extends BaseAction {

	@Autowired
	private ZoneSvr zoneSvr;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return this.getAdminPath("zone/zone.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String changecity(HkRequest req, HkResponse resp) throws Exception {
		AdminUser adminUser = this.getAdminUser(req);
		adminUser.setCityid(req.getInt("cityid"));
		AdminUtil.setLoginAdminUser(resp, adminUser);
		return "r:/mgr/project.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String changeposprovince(HkRequest req, HkResponse resp)
			throws Exception {
		int provinceid = req.getInt("provinceid");
		int toid = req.getInt("toid");
		Province province = this.zoneSvr.getProvince(provinceid);
		Province toProvince = this.zoneSvr.getProvince(toid);
		if (province == null || toProvince == null) {
			return null;
		}
		int tmp = province.getOrder_flg();
		province.setOrder_flg(toProvince.getOrder_flg());
		toProvince.setOrder_flg(tmp);
		this.zoneSvr.updateProvince(toProvince);
		this.zoneSvr.updateProvince(province);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String provincelist(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("op_zone", true);
		req.setAttribute("list", this.zoneSvr.getProvinceListByCountryid(1));
		return this.getAdminPath("zone/provincelist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String citylist(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("op_zone", true);
		int provinceid = req.getIntAndSetAttr("provinceid");
		req.setAttribute("province", this.zoneSvr.getProvince(provinceid));
		req.setAttribute("list", this.zoneSvr
				.getCityListByProvinceid(provinceid));
		return this.getAdminPath("zone/citylist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String districtlist(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("op_zone", true);
		int cityid = req.getIntAndSetAttr("cityid");
		req.setAttribute("city", this.zoneSvr.getCity(cityid));
		req.setAttribute("list", this.zoneSvr.getDistrictListByCityid(cityid));
		return this.getAdminPath("zone/districtlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createprovince(HkRequest req, HkResponse resp) {
		if (this.isForwardPage(req)) {
			req.setAttribute("op_zone", true);
			return this.getAdminPath("zone/createprovince.jsp");
		}
		Province province = new Province();
		province.setCountryid(1);
		province.setName(req.getString("name"));
		List<String> errlist = ProvinceValidator.validate(province);
		if (errlist.isEmpty()) {
			try {
				this.zoneSvr.createProvince(province);
				this.opCreateSuccess(req);
				return this.onSuccess(req, "createok", null);
			}
			catch (DuplicateProvinceNameException e) {
				errlist.add(Err.PROVINCE_NAME_DUPLICATE);
			}
		}
		return this.onErrorList(req, errlist, "createerr");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updateprovince(HkRequest req, HkResponse resp) {
		Province province = this.zoneSvr.getProvince(req
				.getIntAndSetAttr("provinceid"));
		if (province == null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			req.setAttribute("op_zone", true);
			req.setAttribute("province", province);
			return this.getAdminPath("zone/updateprovince.jsp");
		}
		province.setCountryid(1);
		province.setName(req.getString("name"));
		List<String> errlist = ProvinceValidator.validate(province);
		if (errlist.isEmpty()) {
			try {
				this.zoneSvr.updateProvince(province);
				this.opUpdateSuccess(req);
				return this.onSuccess(req, "updateok", null);
			}
			catch (DuplicateProvinceNameException e) {
				errlist.add(Err.PROVINCE_NAME_DUPLICATE);
			}
		}
		return this.onErrorList(req, errlist, "updateerr");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String deleteprovince(HkRequest req, HkResponse resp) {
		this.zoneSvr.deleteProvince(req.getInt("provinceid"));
		this.opDeleteSuccess(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createcity(HkRequest req, HkResponse resp) {
		int provinceid = req.getIntAndSetAttr("provinceid");
		if (this.isForwardPage(req)) {
			req.setAttribute("op_zone", true);
			return this.getAdminPath("zone/createcity.jsp");
		}
		City city = new City();
		city.setProvinceid(provinceid);
		city.setName(req.getString("name"));
		List<String> errlist = CityValidator.validate(city);
		if (errlist.isEmpty()) {
			try {
				this.zoneSvr.createCity(city);
				this.opCreateSuccess(req);
				return this.onSuccess(req, "createok", null);
			}
			catch (DuplicateCityNameException e) {
				errlist.add(Err.CITY_NAME_DUPLICATE);
			}
			catch (ProvinceNotFoundException e) {
				errlist.add(Err.PROVINCE_NOT_EXIST);
			}
		}
		return this.onErrorList(req, errlist, "createerr");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updatecity(HkRequest req, HkResponse resp) {
		City city = this.zoneSvr.getCity(req.getIntAndSetAttr("cityid"));
		if (city == null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			req.setAttribute("op_zone", true);
			req.setAttribute("city", city);
			req.setAttribute("provinceid", city.getProvinceid());
			return this.getAdminPath("zone/updatecity.jsp");
		}
		city.setName(req.getString("name"));
		List<String> errlist = CityValidator.validate(city);
		if (errlist.isEmpty()) {
			try {
				this.zoneSvr.updateCity(city);
				this.opUpdateSuccess(req);
				return this.onSuccess(req, "updateok", null);
			}
			catch (DuplicateCityNameException e) {
				errlist.add(Err.CITY_NAME_DUPLICATE);
			}
			catch (ProvinceNotFoundException e) {
				errlist.add(Err.PROVINCE_NOT_EXIST);
			}
		}
		return this.onErrorList(req, errlist, "updateerr");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String deletecity(HkRequest req, HkResponse resp) {
		this.zoneSvr.deleteCity(req.getInt("cityid"));
		this.opDeleteSuccess(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String changeposcity(HkRequest req, HkResponse resp)
			throws Exception {
		City city = this.zoneSvr.getCity(req.getInt("cityid"));
		City toCity = this.zoneSvr.getCity(req.getInt("toid"));
		if (city == null || toCity == null) {
			return null;
		}
		int tmp = city.getOrder_flg();
		city.setOrder_flg(toCity.getOrder_flg());
		toCity.setOrder_flg(tmp);
		this.zoneSvr.updateCity(toCity);
		this.zoneSvr.updateCity(city);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createdistrict(HkRequest req, HkResponse resp) {
		int cityid = req.getIntAndSetAttr("cityid");
		if (this.isForwardPage(req)) {
			req.setAttribute("op_zone", true);
			return this.getAdminPath("zone/createdistrict.jsp");
		}
		District district = new District();
		district.setCityid(cityid);
		district.setName(req.getString("name"));
		List<String> errlist = DistrictValidator.validate(district);
		if (errlist.isEmpty()) {
			try {
				this.zoneSvr.createDistrict(district);
				this.opCreateSuccess(req);
				return this.onSuccess(req, "createok", null);
			}
			catch (DuplicateDistrictNameException e) {
				errlist.add(Err.DISTRICT_NAME_DUPLICATE);
			}
		}
		return this.onErrorList(req, errlist, "createerr");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updatedistrict(HkRequest req, HkResponse resp) {
		District district = this.zoneSvr.getDistrict(req
				.getIntAndSetAttr("did"));
		if (district == null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			req.setAttribute("op_zone", true);
			req.setAttribute("cityid", district.getCityid());
			req.setAttribute("district", district);
			return this.getAdminPath("zone/updatedistrict.jsp");
		}
		district.setName(req.getString("name"));
		List<String> errlist = DistrictValidator.validate(district);
		if (errlist.isEmpty()) {
			try {
				this.zoneSvr.updateDistrict(district);
				this.opUpdateSuccess(req);
				return this.onSuccess(req, "updateok", null);
			}
			catch (DuplicateDistrictNameException e) {
				errlist.add(Err.DISTRICT_NAME_DUPLICATE);
			}
		}
		return this.onErrorList(req, errlist, "updateerr");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String deletedistrict(HkRequest req, HkResponse resp) {
		this.zoneSvr.deleteDistrict(req.getInt("did"));
		this.opDeleteSuccess(req);
		return null;
	}
}