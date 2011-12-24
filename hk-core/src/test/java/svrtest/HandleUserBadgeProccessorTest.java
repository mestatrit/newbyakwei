package svrtest;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Badge;
import com.hk.bean.CmpCheckInUserLog;
import com.hk.bean.Company;
import com.hk.bean.HandleCheckInUser;
import com.hk.bean.User;
import com.hk.bean.UserBadge;
import com.hk.frame.util.P;
import com.hk.svr.BadgeService;
import com.hk.svr.CmpCheckInService;
import com.hk.svr.CompanyService;
import com.hk.svr.badge.L1001HandleUserBadge;
import com.hk.svr.badge.L1004HandleUserBadge;
import com.hk.svr.badge.L1005HandleUserBadge;
import com.hk.svr.badge.L1006HandleUserBadge;
import com.hk.svr.badge.L1007HandleUserBadge;
import com.hk.svr.badge.L1008HandleUserBadge;
import com.hk.svr.badge.L1009HandleUserBadge;
import com.hk.svr.badge.NoLimitHandleUserBadge;

public class HandleUserBadgeProccessorTest extends HkServiceTest {

	private NoLimitHandleUserBadge noLimitHandleUserBadge;

	private BadgeService badgeService;

	private L1001HandleUserBadge l1001HandleUserBadge;

	private L1004HandleUserBadge l1004HandleUserBadge;

	private L1005HandleUserBadge l1005HandleUserBadge;

	private L1006HandleUserBadge l1006HandleUserBadge;

	private L1007HandleUserBadge l1007HandleUserBadge;

	private L1008HandleUserBadge l1008HandleUserBadge;

	private L1009HandleUserBadge l1009HandleUserBadge;

	private CmpCheckInService cmpCheckInService;

	@Autowired
	private CompanyService companyService;

	public void setCmpCheckInService(CmpCheckInService cmpCheckInService) {
		this.cmpCheckInService = cmpCheckInService;
	}

	public void setL1009HandleUserBadge(
			L1009HandleUserBadge l1009HandleUserBadge) {
		this.l1009HandleUserBadge = l1009HandleUserBadge;
	}

	public void setL1008HandleUserBadge(
			L1008HandleUserBadge l1008HandleUserBadge) {
		this.l1008HandleUserBadge = l1008HandleUserBadge;
	}

	public void setL1007HandleUserBadge(
			L1007HandleUserBadge l1007HandleUserBadge) {
		this.l1007HandleUserBadge = l1007HandleUserBadge;
	}

	public void setL1006HandleUserBadge(
			L1006HandleUserBadge l1006HandleUserBadge) {
		this.l1006HandleUserBadge = l1006HandleUserBadge;
	}

	public void setL1005HandleUserBadge(
			L1005HandleUserBadge l1005HandleUserBadge) {
		this.l1005HandleUserBadge = l1005HandleUserBadge;
	}

	public void setL1004HandleUserBadge(
			L1004HandleUserBadge l1004HandleUserBadge) {
		this.l1004HandleUserBadge = l1004HandleUserBadge;
	}

	public void setL1001HandleUserBadge(
			L1001HandleUserBadge l1001HandleUserBadge) {
		this.l1001HandleUserBadge = l1001HandleUserBadge;
	}

	public void setBadgeService(BadgeService badgeService) {
		this.badgeService = badgeService;
	}

	public void setNoLimitHandleUserBadge(
			NoLimitHandleUserBadge noLimitHandleUserBadge) {
		this.noLimitHandleUserBadge = noLimitHandleUserBadge;
	}

	public void ttestnoLimitHandleUserBadge() {
		long userId = 1;
		long companyId = 1;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		HandleCheckInUser handleCheckInUser = new HandleCheckInUser();
		handleCheckInUser.setOid(1);
		handleCheckInUser.setCompanyId(companyId);
		handleCheckInUser.setUserId(userId);
		Badge badge = this.badgeService.getBadge(4);
		noLimitHandleUserBadge.execute(paramMap, handleCheckInUser, badge);
		List<UserBadge> userBadgeList = this.badgeService
				.getUserBadgeListByUserId(userId, 0, 10000);
		for (UserBadge o : userBadgeList) {
			P.println(o.getBadgeId() + "|" + o.getLimitflg() + "|"
					+ o.getName() + "|" + o.getIntro() + "|");
		}
	}

	public void test1001() {
		for (int i = 0; i < 0; i++) {
			CmpCheckInUserLog cmpCheckInUserLog = new CmpCheckInUserLog();
			cmpCheckInUserLog.setCompanyId(1036 + i);
			cmpCheckInUserLog.setUserId(1);
			cmpCheckInUserLog.setSex(User.SEX_MALE);
			cmpCheckInUserLog.setKindId(1);
			cmpCheckInUserLog.setParentId(1);
			Company company = this.companyService.getCompany(1036 + i);
			this.cmpCheckInService.checkIn(cmpCheckInUserLog, false, company);
		}
		long userId = 1;
		long companyId = 1;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		HandleCheckInUser handleCheckInUser = new HandleCheckInUser();
		handleCheckInUser.setOid(1);
		handleCheckInUser.setCompanyId(companyId);
		handleCheckInUser.setUserId(userId);
		Badge badge = this.badgeService.getBadge(1);
		l1001HandleUserBadge.execute(paramMap, handleCheckInUser, badge);
		this.showUserBadge(userId);
	}

	private void showUserBadge(long userId) {
		List<UserBadge> userBadgeList = this.badgeService
				.getUserBadgeListByUserId(userId, 0, 10000);
		for (UserBadge o : userBadgeList) {
			P.println(o.getBadgeId() + "|" + o.getLimitflg() + "|"
					+ o.getName() + "|" + o.getIntro() + "|");
		}
	}

	/**
	 * 3个异性一起到达某地
	 */
	public void ttest1004() {
		for (int i = 0; i < 3; i++) {
			CmpCheckInUserLog cmpCheckInUserLog = new CmpCheckInUserLog();
			cmpCheckInUserLog.setCompanyId(1);
			cmpCheckInUserLog.setUserId(2 + i);
			cmpCheckInUserLog.setSex(User.SEX_FEMALE);
			cmpCheckInUserLog.setKindId(1);
			cmpCheckInUserLog.setParentId(1);
			Company company = this.companyService.getCompany(1);
			this.cmpCheckInService.checkIn(cmpCheckInUserLog, false, company);
		}
		/********************* 用户报到 **********************/
		CmpCheckInUserLog cmpCheckInUserLog = new CmpCheckInUserLog();
		cmpCheckInUserLog.setCompanyId(1);
		cmpCheckInUserLog.setUserId(1);
		cmpCheckInUserLog.setSex(User.SEX_MALE);
		cmpCheckInUserLog.setKindId(1);
		cmpCheckInUserLog.setParentId(1);
		Company company = this.companyService.getCompany(1);
		this.cmpCheckInService.checkIn(cmpCheckInUserLog, false, company);
		/********************* 用户报到 end **********************/
		long userId = 1;
		long companyId = 1;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		HandleCheckInUser handleCheckInUser = new HandleCheckInUser();
		handleCheckInUser.setOid(1);
		handleCheckInUser.setCompanyId(companyId);
		handleCheckInUser.setUserId(userId);
		Badge badge = this.badgeService.getBadge(9);
		l1004HandleUserBadge.execute(paramMap, handleCheckInUser, badge);
		this.showUserBadge(userId);
	}

	/**
	 * 一晚上到过4个不同的地方
	 */
	public void ttest1005() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 3);
		Date createTime = cal.getTime();
		for (int i = 0; i < 4; i++) {
			CmpCheckInUserLog cmpCheckInUserLog = new CmpCheckInUserLog();
			cmpCheckInUserLog.setCompanyId(1036 + i);
			cmpCheckInUserLog.setUserId(1);
			cmpCheckInUserLog.setSex(User.SEX_MALE);
			cmpCheckInUserLog.setKindId(1);
			cmpCheckInUserLog.setParentId(1);
			cmpCheckInUserLog.setCreateTime(createTime);
			Company company = this.companyService.getCompany(1036 + i);
			this.cmpCheckInService.checkIn(cmpCheckInUserLog, false, company);
		}
		long userId = 1;
		long companyId = 1;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		HandleCheckInUser handleCheckInUser = new HandleCheckInUser();
		handleCheckInUser.setOid(1);
		handleCheckInUser.setCompanyId(companyId);
		handleCheckInUser.setUserId(userId);
		Badge badge = this.badgeService.getBadge(10);
		l1005HandleUserBadge.execute(paramMap, handleCheckInUser, badge);
		this.showUserBadge(userId);
	}

	/**
	 *一晚上在同一个地方报到3次
	 */
	public void ttest1006() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 3);
		Date createTime = cal.getTime();
		for (int i = 0; i < 3; i++) {
			CmpCheckInUserLog cmpCheckInUserLog = new CmpCheckInUserLog();
			cmpCheckInUserLog.setCompanyId(1);
			cmpCheckInUserLog.setUserId(1);
			cmpCheckInUserLog.setSex(User.SEX_MALE);
			cmpCheckInUserLog.setKindId(1);
			cmpCheckInUserLog.setParentId(1);
			cmpCheckInUserLog.setCreateTime(createTime);
			Company company = this.companyService.getCompany(1);
			this.cmpCheckInService.checkIn(cmpCheckInUserLog, false, company);
		}
		long userId = 1;
		long companyId = 1;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		HandleCheckInUser handleCheckInUser = new HandleCheckInUser();
		handleCheckInUser.setOid(1);
		handleCheckInUser.setCompanyId(companyId);
		handleCheckInUser.setUserId(userId);
		Badge badge = this.badgeService.getBadge(11);
		l1006HandleUserBadge.execute(paramMap, handleCheckInUser, badge);
		this.showUserBadge(userId);
	}

	/**
	 *一个月报到30次在任何地方
	 */
	public void ttest1007() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		Date createTime = cal.getTime();
		for (int i = 0; i < 28; i++) {
			CmpCheckInUserLog cmpCheckInUserLog = new CmpCheckInUserLog();
			cmpCheckInUserLog.setCompanyId(1036 + i);
			cmpCheckInUserLog.setUserId(1);
			cmpCheckInUserLog.setSex(User.SEX_MALE);
			cmpCheckInUserLog.setKindId(1);
			cmpCheckInUserLog.setParentId(1);
			cmpCheckInUserLog.setCreateTime(createTime);
			Company company = this.companyService.getCompany(1036 + i);
			this.cmpCheckInService.checkIn(cmpCheckInUserLog, false, company);
		}
		long userId = 1;
		long companyId = 1;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		HandleCheckInUser handleCheckInUser = new HandleCheckInUser();
		handleCheckInUser.setOid(1);
		handleCheckInUser.setCompanyId(companyId);
		handleCheckInUser.setUserId(userId);
		Badge badge = this.badgeService.getBadge(12);
		l1007HandleUserBadge.execute(paramMap, handleCheckInUser, badge);
		this.showUserBadge(userId);
	}

	/**
	 *12个小时内有10个不同地方的报到
	 */
	public void ttest1008() {
		Calendar cal = Calendar.getInstance();
		Date createTime = cal.getTime();
		for (int i = 0; i < 10; i++) {
			CmpCheckInUserLog cmpCheckInUserLog = new CmpCheckInUserLog();
			cmpCheckInUserLog.setCompanyId(1);
			cmpCheckInUserLog.setUserId(1);
			cmpCheckInUserLog.setSex(User.SEX_MALE);
			cmpCheckInUserLog.setKindId(1);
			cmpCheckInUserLog.setParentId(1);
			cmpCheckInUserLog.setCreateTime(createTime);
			Company company = this.companyService.getCompany(1);
			this.cmpCheckInService.checkIn(cmpCheckInUserLog, false, company);
		}
		long userId = 1;
		long companyId = 1;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		HandleCheckInUser handleCheckInUser = new HandleCheckInUser();
		handleCheckInUser.setOid(1);
		handleCheckInUser.setCompanyId(companyId);
		handleCheckInUser.setUserId(userId);
		Badge badge = this.badgeService.getBadge(13);
		l1008HandleUserBadge.execute(paramMap, handleCheckInUser, badge);
		this.showUserBadge(userId);
	}

	/**
	 *连续4个晚上都在某处
	 */
	public void ttest1009() {
		for (int i = 2; i >= 0; i--) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 3);
			cal.add(Calendar.DATE, -i);
			Date createTime = cal.getTime();
			CmpCheckInUserLog cmpCheckInUserLog = new CmpCheckInUserLog();
			cmpCheckInUserLog.setCompanyId(1);
			cmpCheckInUserLog.setUserId(1);
			cmpCheckInUserLog.setSex(User.SEX_MALE);
			cmpCheckInUserLog.setKindId(1);
			cmpCheckInUserLog.setParentId(1);
			cmpCheckInUserLog.setCreateTime(createTime);
			Company company = this.companyService.getCompany(1);
			this.cmpCheckInService.checkIn(cmpCheckInUserLog, false, company);
		}
		long userId = 1;
		long companyId = 1;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		HandleCheckInUser handleCheckInUser = new HandleCheckInUser();
		handleCheckInUser.setOid(1);
		handleCheckInUser.setCompanyId(companyId);
		handleCheckInUser.setUserId(userId);
		Badge badge = this.badgeService.getBadge(14);
		l1009HandleUserBadge.execute(paramMap, handleCheckInUser, badge);
		this.showUserBadge(userId);
	}
}