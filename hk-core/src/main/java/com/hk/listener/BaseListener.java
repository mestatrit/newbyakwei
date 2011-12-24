package com.hk.listener;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpTip;
import com.hk.bean.Company;
import com.hk.bean.Feed;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.JsonUtil;
import com.hk.svr.CompanyService;
import com.hk.svr.FeedService;

public class BaseListener {

	@Autowired
	private FeedService feedService;

	@Autowired
	private CompanyService companyService;

	public void processOnCmpTipUpdated(CmpTip cmpTip) {
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
}