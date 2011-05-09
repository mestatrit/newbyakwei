package iwant.web.admin;

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
import iwant.web.BaseAction;
import iwant.web.admin.util.AdminUtil;
import iwant.web.admin.util.Err;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cactus.web.action.HkRequest;
import cactus.web.action.HkResponse;

@Lazy
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
		AdminUtil.setLoginCity(resp, req.getInt("cityid"));
		return "r:/mgr/project.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createprovince(HkRequest req, HkResponse resp) {
		if (this.isForwardPage(req)) {
			return this.getAdminPath("zone/createprovince.jsp");
		}
		Province province = new Province();
		province.setCountryid(1);
		province.setName(req.getString("name"));
		List<String> errlist = ProvinceValidator.validate(province);
		if (errlist.isEmpty()) {
			try {
				this.zoneSvr.createProvince(province);
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
			req.setAttribute("province", province);
			return this.getAdminPath("zone/updateprovince.jsp");
		}
		province.setCountryid(1);
		province.setName(req.getString("name"));
		List<String> errlist = ProvinceValidator.validate(province);
		if (errlist.isEmpty()) {
			try {
				this.zoneSvr.updateProvince(province);
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
	public String createcity(HkRequest req, HkResponse resp) {
		int provinceid = req.getIntAndSetAttr("provinceid");
		if (this.isForwardPage(req)) {
			return this.getAdminPath("zone/createcity.jsp");
		}
		City city = new City();
		city.setProvinceid(provinceid);
		city.setName(req.getString("name"));
		List<String> errlist = CityValidator.validate(city);
		if (errlist.isEmpty()) {
			try {
				this.zoneSvr.createCity(city);
				return this.onSuccess(req, "createok", null);
			}
			catch (DuplicateCityNameException e) {
				errlist.add(Err.CITY_NAME_DUPLICATE);
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
		if (this.isForwardPage(req)) {
			req.setAttribute("city", city);
			return this.getAdminPath("zone/updatecity.jsp");
		}
		city.setName(req.getString("name"));
		List<String> errlist = CityValidator.validate(city);
		if (errlist.isEmpty()) {
			try {
				this.zoneSvr.createCity(city);
				return this.onSuccess(req, "updateok", null);
			}
			catch (DuplicateCityNameException e) {
				errlist.add(Err.CITY_NAME_DUPLICATE);
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
	public String createdistrict(HkRequest req, HkResponse resp) {
		int cityid = req.getIntAndSetAttr("cityid");
		if (this.isForwardPage(req)) {
			return this.getAdminPath("zone/createdistrict.jsp");
		}
		District district = new District();
		district.setCityid(cityid);
		district.setName(req.getString("name"));
		List<String> errlist = DistrictValidator.validate(district);
		if (errlist.isEmpty()) {
			try {
				this.zoneSvr.createDistrict(district);
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
		if (this.isForwardPage(req)) {
			return this.getAdminPath("zone/updatedistrict.jsp");
		}
		district.setName(req.getString("name"));
		List<String> errlist = DistrictValidator.validate(district);
		if (errlist.isEmpty()) {
			try {
				this.zoneSvr.createDistrict(district);
				return this.onSuccess(req, "updateok", null);
			}
			catch (DuplicateDistrictNameException e) {
				errlist.add(Err.DISTRICT_NAME_DUPLICATE);
			}
		}
		return this.onErrorList(req, errlist, "updateerr");
	}
}