package iwant.web;

import iwant.svr.ZoneSvr;

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
}
