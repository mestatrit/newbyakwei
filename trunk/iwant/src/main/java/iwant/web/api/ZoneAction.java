package iwant.web.api;

import iwant.bean.City;
import iwant.svr.ZoneSvr;
import iwant.web.admin.util.Err;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cactus.util.DataUtil;
import cactus.web.action.HkRequest;
import cactus.web.action.HkResponse;

@Lazy
@Component("/api/zone")
public class ZoneAction extends BaseApiAction {

	@Autowired
	private ZoneSvr zoneSvr;

	public String findcityid(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("name");
		if (DataUtil.isEmpty(name)) {
			return APIUtil.writeErr(resp, Err.CITY_NOT_EXIST);
		}
		City city = this.zoneSvr.getCityByNameLike(name);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("city", city);
		return APIUtil.writeData(resp, map, "vm/city.vm");
	}
}
