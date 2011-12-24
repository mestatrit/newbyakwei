package com.hk.web.user.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.City;
import com.hk.bean.Company;
import com.hk.bean.CompanyUserStatus;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.UserService;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.pub.action.BaseAction;

/**
 * 用户的足迹
 * 
 * @author akwei
 */
@Component("/usercmp")
public class UserCmpAction extends BaseAction {
	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		req.setAttribute("userId", userId);
		SimplePage page = req.getSimplePage(20);
		// 足迹
		List<CompanyUserStatus> companyUserStatusList = this.companyService
				.getCompanyUserStatusListByUserIdAndUserStatus(userId,
						CompanyUserStatus.USERSTATUS_DONE, page.getBegin(),
						page.getSize() + 1);
		this.processListForPage(page, companyUserStatusList);
		List<Long> idList = new ArrayList<Long>();
		for (CompanyUserStatus status : companyUserStatusList) {
			idList.add(status.getCompanyId());
		}
		Map<Long, Company> cmpmap = this.companyService
				.getCompanyMapInId(idList);
		List<Company> cmplist = new ArrayList<Company>();
		for (CompanyUserStatus status : companyUserStatusList) {
			cmplist.add(cmpmap.get(status.getCompanyId()));
		}
		req.setAttribute("cmplist", cmplist);
		// 用户对象
		User user = this.userService.getUser(userId);
		req.setAttribute("user", user);
		// 用户所在地的热门足迹
		City city = ZoneUtil.getCity(user.getPcityId());
		if (city != null) {
			List<Company> hotcmplist = this.companyService
					.getCompanyListForHot(0, city.getCityId(), 0, 6);
			req.setAttribute("hotcmplist", hotcmplist);
		}
		return this.getWeb3Jsp("user/usercmplist.jsp");
	}
}