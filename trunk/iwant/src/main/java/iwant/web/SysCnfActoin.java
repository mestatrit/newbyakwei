package iwant.web;

import iwant.bean.City;
import iwant.svr.ZoneSvr;
import iwant.web.admin.util.AdminUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cactus.web.action.HkRequest;
import cactus.web.action.HkResponse;

@Lazy
@Component("/syscnf")
public class SysCnfActoin extends BaseAction {

	@Autowired
	private ZoneSvr zoneSvr;

	/**
	 * @param resp
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
	public String findcity(HkRequest req, HkResponse resp) throws Exception {
		int cityid = AdminUtil.getLoginCityid(req);
		City city = this.zoneSvr.getCity(cityid);
		req.setAttribute("current_city", city);
		return null;
	}
}
