package com.hk.listener.box.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Box;
import com.hk.bean.CmpUnion;
import com.hk.bean.CmpUnionFeed;
import com.hk.bean.Company;
import com.hk.bean.Feed;
import com.hk.bean.IpCityRange;
import com.hk.bean.UserBoxPrize;
import com.hk.frame.util.DataUtil;
import com.hk.listener.box.BoxEventListener;
import com.hk.svr.BoxOpenResult;
import com.hk.svr.BoxService;
import com.hk.svr.CmpUnionMessageService;
import com.hk.svr.CmpUnionService;
import com.hk.svr.CompanyService;
import com.hk.svr.FeedService;
import com.hk.svr.IpCityService;
import com.hk.svr.UserService;
import com.hk.svr.pub.CmpUnionMessageUtil;

public class BoxEventListenerImpl implements BoxEventListener {

	@Autowired
	private FeedService feedService;

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private CmpUnionService cmpUnionService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CmpUnionMessageService cmpUnionMessageService;

	@Autowired
	private BoxService boxService;

	@Autowired
	private UserService userService;

	public void feed(UserBoxPrize userBoxPrize, long userId, long boxId,
			String ip) {
		Feed feed = new Feed();
		feed.setUserId(userId);
		feed.setCreateTime(new Date());
		Map<String, String> map = new HashMap<String, String>();
		map.put("usernickname", this.userService.getUser(userId).getNickName());
		map.put("boxid", String.valueOf(boxId));
		map.put("boxname", this.boxService.getBox(boxId).getName());
		if (userBoxPrize != null) {
			map.put("prizeid", String.valueOf(userBoxPrize.getPrizeId()));
			map.put("prizename", this.boxService.getBoxPrize(
					userBoxPrize.getPrizeId()).getName());
		}
		feed.setData(DataUtil.toJson(map));
		feed.setFeedType(Feed.FEEDTYPE_OPENBOX);
		if (ip != null) {
			feed.setIpNumber(DataUtil.parseIpNumber(ip));
			IpCityRange range = this.ipCityService.getIpCityRange(ip);
			if (range != null) {
				feed.setRangeId(range.getRangeId());
				feed.setCityId(range.getCityId());
			}
		}
		this.feedService.createFeed(feed, null);
	}

	public void boxOpened(BoxOpenResult boxOpenResult, long userId, long boxId,
			String ip) {
		this.feed(boxOpenResult.getUserBoxPrize(), userId, boxId, ip);
	}

	public void boxCreated(Box box) {
		this.createCmpUnionFeed(box);
	}

	/**
	 * 如果足迹已经加入某个联盟，创建足迹在联盟中的动态
	 * 
	 * @param box
	 */
	private void createCmpUnionFeed(Box box) {
		long companyId = box.getCompanyId();
		if (companyId <= 0) {
			return;
		}
		Company company = this.companyService.getCompany(companyId);
		if (company == null || company.getUid() <= 0) {
			return;
		}
		CmpUnion cmpUnion = this.cmpUnionService.getCmpUnion(company.getUid());
		if (cmpUnion == null) {
			return;
		}
		box.setUid(cmpUnion.getUid());
		CmpUnionFeed feed = new CmpUnionFeed();
		feed.setUid(cmpUnion.getUid());
		feed.setFeedflg(CmpUnionMessageUtil.FEED_CREATEBOX);
		Map<String, String> map = new HashMap<String, String>();
		map.put("cmp", company.getName());
		map.put("boxid", box.getBoxId() + "");
		map.put("name", box.getName());
		feed.setData(DataUtil.toJson(map));
		feed.setObjId(companyId);
		cmpUnionMessageService.createCmpUnionFeed(feed);
	}
}