package com.hk.svr.processor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpCheckInUserLog;
import com.hk.bean.CmpTip;
import com.hk.bean.CmpTipDel;
import com.hk.bean.Company;
import com.hk.bean.CompanyUserStatus;
import com.hk.bean.Feed;
import com.hk.bean.FeedInfo;
import com.hk.bean.IpCityRange;
import com.hk.bean.User;
import com.hk.bean.UserCmpTip;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpCheckInService;
import com.hk.svr.CmpTipService;
import com.hk.svr.CompanyService;
import com.hk.svr.FeedService;
import com.hk.svr.IpCityService;
import com.hk.svr.UserService;

public class CmpTipProcessor extends BaseProcessor {

	@Autowired
	private CmpTipService cmpTipService;

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

	@Autowired
	private FeedService feedService;

	@Autowired
	private CmpCheckInService cmpCheckInService;

	public void createCmpTip(CmpTip cmpTip) {
		this.cmpTipService.createCmpTip(cmpTip);
		this.onCmpTipCreated(cmpTip);
	}

	private void onCmpTipCreated(CmpTip cmpTip) {
		this.createFeedWhenCreateDoneCmpTip(cmpTip);
	}

	private void createFeedWhenCreateDoneCmpTip(CmpTip cmpTip) {
		if (cmpTip.isDone() && !DataUtil.isEmpty(cmpTip.getContent())) {
			Feed feed = new Feed();
			feed.setFeedType(Feed.FEEDTYPE_WRITETIPS);
			feed.setUserId(cmpTip.getUserId());
			feed.setCreateTime(new Date());
			feed.setCityId(cmpTip.getPcityId());
			if (cmpTip.getIp() != null) {
				IpCityRange ipCityRange = this.ipCityService
						.getIpCityRange(cmpTip.getIp());
				if (ipCityRange != null) {
					feed.setRangeId(ipCityRange.getRangeId());
					feed.setIpNumber(DataUtil.parseIpNumber(cmpTip.getIp()));
				}
			}
			Company company = this.companyService.getCompany(cmpTip
					.getCompanyId());
			if (company != null) {
				Map<String, String> map = new HashMap<String, String>();
				User user = this.userService.getUser(feed.getUserId());
				map.put("nickname", user.getNickName());
				map.put("headpath", user.getHeadPath());
				map.put("companyid", String.valueOf(cmpTip.getCompanyId()));
				map.put("cmpname", company.getName());
				String tip = DataUtil.limitLength(DataUtil.toTextRow(cmpTip
						.getContent()), 50);
				map.put("tip", DataUtil.toHtmlRow(tip));
				map.put("tipid", String.valueOf(cmpTip.getTipId()));
				feed.setData(DataUtil.toJson(map));
				FeedInfo feedInfo = feed.createFeedInfo();
				feedInfo.setObjId(cmpTip.getCompanyId());
				feedInfo.setObj2Id(cmpTip.getTipId());
				this.feedService.createFeed(feed, feedInfo);
			}
		}
	}

	public void createUserCmpTip(UserCmpTip userCmpTip) {
		this.cmpTipService.createUserCmpTip(userCmpTip);
		this.userService.updateUserUpdate(userCmpTip.getUserId());
		this.updateCompanyUserStatus(userCmpTip);
		if (userCmpTip.isDone()) {
			this.addInffectCheckInOnAddToDo(userCmpTip);
		}
	}

	private void updateCompanyUserStatus(UserCmpTip userCmpTip) {
		byte status = 0;
		if (userCmpTip.isDone()) {
			status = CompanyUserStatus.USERSTATUS_DONE;
		}
		else {
			status = CompanyUserStatus.USERSTATUS_WANT;
		}
		this.companyService.createCompanyUserStatus(userCmpTip.getCompanyId(),
				userCmpTip.getUserId(), status);
	}

	/**
	 * 添加一个无效的报到
	 * 
	 * @param userCmpTip
	 */
	private void addInffectCheckInOnAddToDo(UserCmpTip userCmpTip) {
		int count = this.cmpCheckInService
				.countCmpCheckInUserLogByCompanyIdAndUserId(userCmpTip
						.getCompanyId(), userCmpTip.getUserId());
		if (count == 0) {
			Company company = this.companyService.getCompany(userCmpTip
					.getCompanyId());
			User user = this.userService.getUser(userCmpTip.getUserId());
			CmpCheckInUserLog cmpCheckInUserLog = new CmpCheckInUserLog();
			cmpCheckInUserLog.setCompanyId(userCmpTip.getCompanyId());
			cmpCheckInUserLog.setUserId(userCmpTip.getUserId());
			cmpCheckInUserLog.setKindId(company.getKindId());
			cmpCheckInUserLog.setParentId(company.getParentKindId());
			cmpCheckInUserLog.setPcityId(userCmpTip.getPcityId());
			cmpCheckInUserLog.setSex(user.getSex());
			cmpCheckInUserLog.setEffectflg(CmpCheckInUserLog.EFFECTFLG_N);
			this.cmpCheckInService.checkIn(cmpCheckInUserLog, true, company);
		}
	}

	// @SuppressWarnings("unused")
	// private void createLaba(CmpTip cmpTip) {
	// long companyId = cmpTip.getCompanyId();
	// long userId = cmpTip.getUserId();
	// Company company = this.companyService.getCompany(companyId);
	// String content = ResourceConfig.getText("view2.labacontentoncmptip",
	// "{[" + companyId + "," + company.getName() + "}", cmpTip
	// .getContent());
	// LabaInPutParser parser = new LabaInPutParser(null);
	// LabaInfo labaInfo = parser.parse(content);
	// labaInfo.setUserId(userId);
	// labaInfo.setSendFrom(Laba.SENDFROM_WEB);
	// this.labaService.createLaba(labaInfo);
	// }
	public CmpTip deleteCmpTip(long tipId) {
		CmpTip cmpTip = this.cmpTipService.deleteCmpTip(tipId);
		List<Long> idList = this.feedService
				.getFeedIdListFromFeedInfoByObj2IdAndFeedType(
						cmpTip.getTipId(), Feed.FEEDTYPE_WRITETIPS);
		for (Long l : idList) {
			this.feedService.deleteFeed(l);
		}
		return cmpTip;
	}

	public void updateCmpTip(CmpTip cmpTip) {
		this.cmpTipService.updateCmpTip(cmpTip);
		this.processOnCmpTipUpdated(cmpTip);
	}

	public void bombCmpTip(CmpTipDel cmpTipDel) {
		this.cmpTipService.bombCmpTip(cmpTipDel);
		List<Long> idList = this.feedService
				.getFeedIdListFromFeedInfoByObj2IdAndFeedType(cmpTipDel
						.getCmpTip().getTipId(), Feed.FEEDTYPE_WRITETIPS);
		for (Long l : idList) {
			this.feedService.deleteFeed(l);
		}
	}
}