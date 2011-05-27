package iwant.web.api;

import iwant.bean.City;
import iwant.bean.District;
import iwant.svr.ZoneSvr;
import iwant.web.admin.util.Err;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.dev3g.cactus.util.DataUtil;
import com.dev3g.cactus.web.action.HkRequest;
import com.dev3g.cactus.web.action.HkResponse;

@Lazy
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
		District district = this.zoneSvr.getDistrictByCityidAndNameLike(city
				.getCityid(), districtname);
		if (district == null) {
			return APIUtil.writeErr(resp, Err.DISTRICT_NOT_EXIST);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("district", district);
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
		map.put("list", this.zoneSvr.getProvinceListByCountryid(1));
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
		map.put("list", this.zoneSvr.getCityListByProvinceid(req
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
		map.put("list", this.zoneSvr.getDistrictListByCityid(req
				.getInt("cityid")));
		return APIUtil.writeData(resp, map, "vm/districtlist.vm");
	}
}