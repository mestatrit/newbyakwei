package com.hk.web.pub.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.City;
import com.hk.bean.User;
import com.hk.bean.ZoneAdmin;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.UserService;
import com.hk.svr.ZoneAdminService;
import com.hk.svr.ZoneService;
import com.hk.svr.pub.Err;

@Component("/admin/zoneadmin")
public class OpZoneAdminAction extends BaseAction {
	@Autowired
	private ZoneAdminService zoneAdminService;

	@Autowired
	private UserService userService;

	@Autowired
	private ZoneService zoneService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		int pcityId = req.getInt("pcityId");
		if (pcityId <= 0) {
			String zoneName = req.getString("zoneName");
			if (zoneName != null) {
				City city = this.zoneService.getCityLike(zoneName);
				if (city != null) {
					pcityId = city.getCityId();
				}
				else {
					req.setText(String.valueOf(Err.ZONE_NOT_EXIST));
				}
				req.setEncodeAttribute("zoneName", zoneName);
			}
		}
		SimplePage page = req.getSimplePage(20);
		List<ZoneAdmin> list = this.zoneAdminService.getZoneAdminList(pcityId,
				page.getBegin(), page.getSize() + 1);
		List<Long> idList = new ArrayList<Long>();
		for (ZoneAdmin zoneAdmin : list) {
			idList.add(zoneAdmin.getUserId());
		}
		Map<Long, User> map = this.userService.getUserMapInId(idList);
		for (ZoneAdmin zoneAdmin : list) {
			zoneAdmin.setUser(map.get(zoneAdmin.getUserId()));
		}
		req.setAttribute("list", list);
		return this.getWapJsp("/zoneadmin/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tocreate(HkRequest req, HkResponse resp) throws Exception {
		return this.getWapJsp("/zoneadmin/create.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		String nickName = req.getString("nickName");
		String zoneName = req.getString("zoneName");
		City city = this.zoneService.getCityLike(zoneName);
		if (city == null) {
			req.setText(String.valueOf(Err.ZONE_NOT_EXIST));
			return "/admin/zoneadmin_tocreate.do";
		}
		User user = this.userService.getUserByNickName(nickName);
		if (user == null) {
			req.setText(String.valueOf(Err.USER_NOT_EXIST));
			return "/admin/zoneadmin_tocreate.do";
		}
		ZoneAdmin zoneAdmin = new ZoneAdmin();
		zoneAdmin.setPcityId(city.getCityId());
		zoneAdmin.setUserId(user.getUserId());
		this.zoneAdminService.createZoneAdmin(zoneAdmin);
		this.setOpFuncSuccessMsg(req);
		return "r:/admin/zoneadmin.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delete(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		this.zoneAdminService.deleteZoneAdmin(oid);
		this.setOpFuncSuccessMsg(req);
		return "r:/admin/zoneadmin.do";
	}
}