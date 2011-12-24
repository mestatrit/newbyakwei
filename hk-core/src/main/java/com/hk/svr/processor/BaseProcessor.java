package com.hk.svr.processor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.City;
import com.hk.bean.CmpTip;
import com.hk.bean.Company;
import com.hk.bean.Feed;
import com.hk.bean.Province;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.JsonUtil;
import com.hk.svr.CompanyService;
import com.hk.svr.FeedService;
import com.hk.svr.ZoneService;

public class BaseProcessor {

	@Autowired
	private FeedService feedService;

	@Autowired
	private CompanyService companyService;

	protected void processOnCmpTipUpdated(CmpTip cmpTip) {
		Company company = this.companyService.getCompany(cmpTip.getCompanyId());
		List<Long> idList = this.feedService
				.getFeedIdListFromFeedInfoByObj2IdAndFeedType(
						cmpTip.getTipId(), Feed.FEEDTYPE_WRITETIPS);
		List<Feed> list = this.feedService.getFeedListInId(idList);
		for (Feed o : list) {
			Map<String, String> map = JsonUtil.getMapFromJson(o.getData());
			String tip = DataUtil.limitLength(DataUtil.toTextRow(cmpTip
					.getContent()), 50);
			map.put("tip", tip);
			map.put("cmpname", company.getName());
			o.setData(JsonUtil.toJson(map));
			this.feedService.updateFeed(o);
		}
	}

	protected ZoneResult findZone(String zoneName) {
		ZoneResult zoneResult = new ZoneResult();
		ZoneService zoneService = (ZoneService) HkUtil.getBean("zoneService");
		String _zoneName = DataUtil.filterZoneName(zoneName);
		City city = zoneService.getCityLike(_zoneName);
		if (city == null) {
			// 到省表中查找
			Province province = zoneService.getProvinceLike(_zoneName);
			// 省表也没有
			if (province == null) {
				return null;
			}
			// 省表存在
			zoneResult.setProvinceId(province.getProvinceId());
			return zoneResult;
		}
		zoneResult.setCityId(city.getCityId());
		return zoneResult;
	}
}
