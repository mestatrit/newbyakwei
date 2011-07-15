package iwant.web;

import halo.web.action.HkRequest;
import halo.web.action.HkResponse;
import iwant.bean.City;
import iwant.svr.ZoneSvr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("/syscnf")
public class SysCnfActoin extends BaseAction {

	@Autowired
	private ZoneSvr zoneSvr;

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String zone(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("syscnf_provincelist", this.zoneSvr
				.getProvinceListByCountryid(1));
		req.setAttribute("syscnf_citylist", this.zoneSvr.getCityList());
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String districtlist(HkRequest req, HkResponse resp) throws Exception {
		int cityid = this.getLoginCityid(req);
		City city = this.zoneSvr.getCity(cityid);
		req.setAttribute("syscnf_city", city);
		req.setAttribute("syscnf_districtlist", this.zoneSvr
				.getDistrictListByCityid(cityid));
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String findcity(HkRequest req, HkResponse resp) throws Exception {
		int cityid = this.getLoginCityid(req);
		City city = this.zoneSvr.getCity(cityid);
		req.setAttribute("current_city", city);
		return null;
	}
}
