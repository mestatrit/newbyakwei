package com.hk.svr.equipment;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Company;
import com.hk.bean.Feed;
import com.hk.bean.FeedInfo;
import com.hk.bean.IpCityRange;
import com.hk.bean.User;
import com.hk.bean.UserEquEnjoy;
import com.hk.bean.UserEquipment;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CompanyService;
import com.hk.svr.FeedService;
import com.hk.svr.IpCityService;
import com.hk.svr.UserService;
import com.hk.svr.pub.EquipmentConfig;

/**
 * 装备的使用逻辑
 * 
 * @author akwei
 */
public abstract class FuncEquipment {

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private FeedService feedService;

	@Autowired
	private UserService userService;

	@Autowired
	private CompanyService companyService;

	/**
	 * 使用道具功能
	 * 
	 * @param userEquEnjoy
	 * @param userEquipment
	 * @param ctxAttributeMap
	 * @return true:使用了,false:未使用
	 *         2010-4-13
	 */
	abstract boolean execute(UserEquEnjoy userEquEnjoy,
			UserEquipment userEquipment, Map<String, Object> ctxAttributeMap);

	protected Feed preparedFeed(UserEquipment userEquipment,
			Map<String, Object> ctxAttributeMap) {
		int cityId = (Integer) ctxAttributeMap.get("cityId");
		String ip = (String) ctxAttributeMap.get("ip");
		Feed feed = new Feed();
		feed.setUserId(userEquipment.getUserId());
		feed.setCityId(cityId);
		feed.setCreateTime(new Date());
		feed.setFeedType(Feed.FEEDTYPE_EQU);
		if (ip != null) {
			feed.setIpNumber(DataUtil.parseIpNumber(ip));
			IpCityRange ipCityRange = this.ipCityService.getIpCityRange(ip);
			if (ipCityRange != null) {
				feed.setRangeId(ipCityRange.getRangeId());
				feed.setIpNumber(DataUtil.parseIpNumber(ip));
			}
		}
		return feed;
	}

	protected Map<String, String> createVsFeed(UserEquEnjoy userEquEnjoy,
			Map<String, Object> ctxAttributeMap) {
		Map<String, String> map = new HashMap<String, String>();
		User enjoyUser = this.userService
				.getUser(userEquEnjoy.getEnjoyUserId());
		User user = this.userService.getUser(userEquEnjoy.getUserId());
		map.put("vs", "1");
		map.put("nickname", user.getNickName());
		map.put("enjoyuserid", String.valueOf(userEquEnjoy.getEnjoyUserId()));
		map.put("enjoynickname", enjoyUser.getNickName());
		map
				.put("eid", String.valueOf(userEquEnjoy.getUserEquipment()
						.getEid()));
		map.put("ename", EquipmentConfig.getEquipment(
				userEquEnjoy.getUserEquipment().getEid()).getName());
		String data = DataUtil.toJson(map);
		Feed feed = this.preparedFeed(userEquEnjoy.getUserEquipment(),
				ctxAttributeMap);
		feed.setData(data);
		this.feedService.createFeed(feed, null);
		return map;
	}

	protected Map<String, String> createSosFeed(UserEquEnjoy userEquEnjoy,
			UserEquipment userEquipment, Map<String, Object> ctxAttributeMap) {
		this.createVsFeed(userEquEnjoy, ctxAttributeMap);
		Map<String, String> map = new HashMap<String, String>();
		User ouser = this.userService.getUser(userEquEnjoy.getUserId());
		User user = this.userService.getUser(userEquipment.getUserId());
		map.put("sos", "1");
		map.put("nickname", user.getNickName());
		map.put("vseid", String.valueOf(userEquipment.getEid()));
		map.put("vsename", userEquipment.getEquipment().getName());
		map.put("ouserid", String.valueOf(userEquEnjoy.getUserId()));
		map.put("onickname", ouser.getNickName());
		map
				.put("eid", String.valueOf(userEquEnjoy.getUserEquipment()
						.getEid()));
		map.put("ename", EquipmentConfig.getEquipment(
				userEquEnjoy.getUserEquipment().getEid()).getName());
		String data = DataUtil.toJson(map);
		Feed feed = this.preparedFeed(userEquipment, ctxAttributeMap);
		feed.setData(data);
		this.feedService.createFeed(feed, null);
		return map;
	}

	protected void createCmpFeed(UserEquipment userEquipment,
			Map<String, Object> ctxAttributeMap) {
		// 动态
		Map<String, String> map = new HashMap<String, String>();
		long companyId = (Long) ctxAttributeMap.get("companyId");
		Company company = this.companyService.getCompany(companyId);
		User user = this.userService.getUser(userEquipment.getUserId());
		map.put("forcmp", "1");
		map.put("nickname", user.getNickName());
		map.put("companyid", String.valueOf(companyId));
		map.put("cmpname", company.getName());
		map.put("eid", String.valueOf(userEquipment.getEid()));
		map.put("ename", EquipmentConfig.getEquipment(userEquipment.getEid())
				.getName());
		String data = DataUtil.toJson(map);
		Feed feed = this.preparedFeed(userEquipment, ctxAttributeMap);
		feed.setData(data);
		FeedInfo feedInfo = new FeedInfo();
		feedInfo.setObjId(company.getCompanyId());
		this.feedService.createFeed(feed, null);
	}
}