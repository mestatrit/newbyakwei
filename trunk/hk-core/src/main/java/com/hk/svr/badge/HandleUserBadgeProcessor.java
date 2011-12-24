package com.hk.svr.badge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Badge;
import com.hk.bean.Company;
import com.hk.bean.HandleCheckInUser;
import com.hk.bean.UserBadge;
import com.hk.svr.BadgeService;
import com.hk.svr.CompanyService;
import com.hk.svr.HandleService;

public class HandleUserBadgeProcessor {
	@Autowired
	private HandleService handleService;

	@Autowired
	private BadgeService badgeService;

	@Autowired
	private CompanyService companyService;

	private Map<String, HandleUserBadge> map;

	public void setMap(Map<String, HandleUserBadge> map) {
		this.map = map;
	}

	/**
	 * 计算用户获得的徽章
	 */
	public void invoke() {
		List<HandleCheckInUser> list = this.handleService
				.getHandleCheckInUserList(0, 20);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (HandleCheckInUser handleCheckInUser : list) {
			this.processNoLimitBadge(paramMap, handleCheckInUser);
			this.processSysLimitBadge(paramMap, handleCheckInUser);
			this.processLimitBadge(paramMap, handleCheckInUser);
			this.handleService.deleteHandleCheckInUser(handleCheckInUser
					.getUserId(), handleCheckInUser.getCompanyId());
		}
	}

	/**
	 * 无限制徽章处理，都是根据有效报到次数来计算
	 * 
	 * @param handleCheckInUser
	 */
	private void processNoLimitBadge(Map<String, Object> paramMap,
			HandleCheckInUser handleCheckInUser) {
		HandleUserBadge handleUserBadge = this.map.get("0");
		if (handleUserBadge == null) {
			return;
		}
		long userId = handleCheckInUser.getUserId();
		List<UserBadge> userBadgeList = this.badgeService
				.getUserBadgeListByUserIdAndNoLimit(userId);
		List<Long> idList = new ArrayList<Long>();
		for (UserBadge o : userBadgeList) {
			idList.add(o.getBadgeId());
		}
		List<Badge> badgeList = this.badgeService
				.getBadgeListForNoLimitNotInId(idList);
		for (Badge o : badgeList) {
			handleUserBadge.execute(paramMap, handleCheckInUser, o);
		}
	}

	/**
	 * 离散类型徽章，根据自定义规则进行计算
	 * 
	 * @param handleCheckInUser
	 */
	private void processSysLimitBadge(Map<String, Object> paramMap,
			HandleCheckInUser handleCheckInUser) {
		long userId = handleCheckInUser.getUserId();
		List<UserBadge> userBadgeList = this.badgeService
				.getUserBadgeListByUserIdAndSysLimit(userId);
		List<Long> idList = new ArrayList<Long>();
		for (UserBadge o : userBadgeList) {
			idList.add(o.getBadgeId());
		}
		List<Badge> badgeList = this.badgeService
				.getBadgeListForSysLimitNotInId(idList);
		for (Badge o : badgeList) {
			HandleUserBadge handleUserBadge = this.map.get(o.getLimitflg()
					+ ":" + o.getRuleflg());
			if (handleUserBadge != null) {
				handleUserBadge.execute(paramMap, handleCheckInUser, o);
			}
		}
	}

	/**
	 * 限制类型徽章，此系列徽章的获得与足迹和足迹组有关系
	 * 
	 * @param handleCheckInUser
	 */
	private void processLimitBadge(Map<String, Object> paramMap,
			HandleCheckInUser handleCheckInUser) {
		List<Badge> all_badge_list = new ArrayList<Badge>();
		long companyId = handleCheckInUser.getCompanyId();
		// 足迹相关
		all_badge_list.addAll(this.badgeService
				.getBadgeListByCompanyId(companyId));
		// 分类的
		Company company = this.companyService.getCompany(companyId);
		all_badge_list.addAll(this.badgeService.getBadgeListByKindId(company
				.getParentKindId()));
		// 子分类的
		all_badge_list.addAll(this.badgeService.getBadgeListByKindId(company
				.getKindId()));
		// 足迹组的
		all_badge_list.addAll(this.badgeService
				.getBadgeListByCompanyInGroup(company.getCompanyId()));
		List<Badge> newlist = new ArrayList<Badge>();
		List<UserBadge> userBadgeList = this.badgeService
				.getUserBadgeListByUserIdForLimit(handleCheckInUser.getUserId());
		Map<Long, UserBadge> map = new HashMap<Long, UserBadge>();
		for (UserBadge o : userBadgeList) {
			map.put(o.getBadgeId(), o);
		}
		for (Badge o : all_badge_list) {
			if (!map.containsKey(o.getBadgeId())) {
				newlist.add(o);
			}
		}
		for (Badge o : newlist) {
			HandleUserBadge handleUserBadge = this.map.get(o.getLimitflg()
					+ ":" + o.getRuleflg());
			if (handleUserBadge != null) {
				handleUserBadge.execute(paramMap, handleCheckInUser, o);
			}
		}
		// long companyId = handleCheckInUser.getCompanyId();
		// List<Badge> badgeList = this.badgeService
		// .getBadgeListByCompanyId(companyId);
		// for (Badge o : badgeList) {
		// HandleUserBadge handleUserBadge = this.map.get(o.getLimitflg()
		// + ":" + o.getRuleflg());
		// if (handleUserBadge != null) {
		// handleUserBadge.execute(paraMap, handleCheckInUser, o);
		// }
		// }
		// // 分类的
		// Company company = this.companyService.getCompany(companyId);
		// badgeList = this.badgeService.getBadgeListByKindId(company
		// .getParentKindId());
		// for (Badge o : badgeList) {
		// HandleUserBadge handleUserBadge = this.map.get(o.getLimitflg()
		// + ":" + o.getRuleflg());
		// if (handleUserBadge != null) {
		// handleUserBadge.execute(paraMap, handleCheckInUser, o);
		// }
		// }
		// // 子分类的
		// badgeList =
		// this.badgeService.getBadgeListByKindId(company.getKindId());
		// for (Badge o : badgeList) {
		// HandleUserBadge handleUserBadge = this.map.get(o.getLimitflg()
		// + ":" + o.getRuleflg());
		// if (handleUserBadge != null) {
		// handleUserBadge.execute(paraMap, handleCheckInUser, o);
		// }
		// }
		// // 足迹组的
		// List<CmpAdminGroupRef> cmpAdminGroupRefList =
		// this.cmpAdminGroupService
		// .getCmpAdminGroupRefListByCompanyId(companyId);
	}
}