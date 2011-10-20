package iwant.web.api;

import halo.util.DataUtil;
import halo.web.action.HkRequest;
import halo.web.action.HkResponse;
import iwant.bean.City;
import iwant.bean.District;
import iwant.bean.Province;
import iwant.svr.ZoneSvr;
import iwant.web.admin.util.Err;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("/api/zone")
public class ZoneAction extends BaseApiAction {

	@Autowired
	private ZoneSvr zoneSvr;

	public String findcity(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("name");
		if (DataUtil.isEmpty(name)) {
			return APIUtil.writeErr(resp, Err.CITY_NOT_EXIST);
		}
		City city = this.zoneSvr.getCityByNameLike(name);
		if (city == null) {
			return APIUtil.writeErr(resp, Err.CITY_NOT_EXIST);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("city", city);
		return APIUtil.writeData(resp, map, "vm/city.vm");
	}

	public String finddistrict(HkRequest req, HkResponse resp) throws Exception {
		String cityname = req.getString("cityname");
		if (DataUtil.isEmpty(cityname)) {
			return APIUtil.writeErr(resp, Err.CITY_NOT_EXIST);
		}
		City city = this.zoneSvr.getCityByNameLike(cityname);
		if (city == null) {
			return APIUtil.writeErr(resp, Err.CITY_NOT_EXIST);
		}
		String districtname = req.getString("districtname");
		if (DataUtil.isEmpty(districtname)) {
			return APIUtil.writeErr(resp, Err.DISTRICT_NOT_EXIST);
		}
		District district = this.zoneSvr.getDistrictByCityidAndNameLike(
				city.getCityid(), districtname);
		if (district == null) {
			return APIUtil.writeErr(resp, Err.DISTRICT_NOT_EXIST);
		}
		Province province = this.zoneSvr.getProvince(city.getProvinceid());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("district", district);
		map.put("city", city);
		map.put("province", province);
		return APIUtil.writeData(resp, map, "vm/district.vm");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String provincelist(HkRequest req, HkResponse resp) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", this.zoneSvr.getProvinceListByCountryidForShow(1));
		return APIUtil.writeData(resp, map, "vm/provincelist.vm");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String citylist(HkRequest req, HkResponse resp) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", this.zoneSvr.getCityListByProvinceidForShow(req
				.getInt("provinceid")));
		return APIUtil.writeData(resp, map, "vm/citylist.vm");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String districtlist(HkRequest req, HkResponse resp) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", this.zoneSvr.getDistrictListByCityidForShow(req
				.getInt("cityid")));
		return APIUtil.writeData(resp, map, "vm/districtlist.vm");
	}
}
