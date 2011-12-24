package com.hk.svr.pub;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.hk.bean.City;
import com.hk.bean.Company;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.CompanyService;
import com.hk.svr.UserService;
import com.hk.svr.ZoneService;
import com.hk.svr.impl.CompanyScoreConfig;

public class HkSvrUtil {
	private static long serviceUserId;

	private static int minOrderMoney;// 最低竞排价格

	private HkSvrUtil() {//
	}

	public void setMinOrderMoney(int minOrderMoney) {
		HkSvrUtil.minOrderMoney = minOrderMoney;
	}

	public static int getMinOrderMoney() {
		return minOrderMoney;
	}

	public void setServiceUserId(long serviceUserId) {
		HkSvrUtil.serviceUserId = serviceUserId;
	}

	public static long getServiceUserId() {
		return HkSvrUtil.serviceUserId;
	}

	public static boolean isNotUser(long userId) {
		UserService userService = (UserService) HkUtil.getBean("userService");
		if (userService.getUser(userId) == null) {
			return true;
		}
		return false;
	}

	public static boolean isNotCompany(long companyId) {
		CompanyService companyService = (CompanyService) HkUtil
				.getBean("companyService");
		if (companyService.getCompany(companyId) == null) {
			return true;
		}
		return false;
	}

	public static void setZone(String zoneName, Company o) {
		if (DataUtil.isEmpty(zoneName)) {
			return;
		}
		ZoneService zoneService = (ZoneService) HkUtil.getBean("zoneService");
		String zone = DataUtil.filterZoneName(zoneName);
		City city = zoneService.getCityLike(zone);
		if (city != null) {
			o.setPcityId(city.getCityId());
		}
	}

	/**
	 * x~x.3之内是x个星星 x.3~x.7是x.5个星星 x.7~x+1是x+1个星星
	 */
	public static int getStarsLevel(int totalScore, int totalVote) {
		if (totalScore == 0) {
			return 0;
		}
		int t_int = totalScore / totalVote;
		BigDecimal a = new BigDecimal(totalScore);
		BigDecimal b = new BigDecimal(totalVote);
		BigDecimal tmp_res1 = a.divide(b, 1, RoundingMode.HALF_UP);
		BigDecimal tmp_res2 = tmp_res1.add(new BigDecimal(-t_int));
		double remain = tmp_res2.doubleValue();
		remain = Math.abs(remain);
		Integer star = CompanyScoreConfig.getStar(t_int);
		if (star == null) {
			star = 0;
		}
		if (remain <= 0.3) {
			return star.intValue() * 10;
		}
		if (remain > 0.3 && remain < 0.7) {
			return Integer.parseInt(star.intValue() + "5");
		}
		return (star.intValue() + 1) * 10;
	}

	/**
	 * x~x.3之内是x个星星 x.3~x.7是x.5个星星 x.7~x+1是x+1个星星
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public static String makeCssStarRate(int arg0, int arg1) {
		if (arg0 == 0) {
			return "0";
		}
		int t_int = arg0 / arg1;
		BigDecimal a = new BigDecimal(arg0);
		BigDecimal b = new BigDecimal(arg1);
		BigDecimal tmp_res1 = a.divide(b, 1, RoundingMode.HALF_UP);
		BigDecimal tmp_res2 = tmp_res1.add(new BigDecimal(-t_int));
		double remain = tmp_res2.doubleValue();
		remain = Math.abs(remain);
		Integer star = CompanyScoreConfig.getStar(t_int);
		if (star == null) {
			star = 0;
		}
		if (remain <= 0.3) {
			return star.intValue() + "";
		}
		if (remain > 0.3 && remain < 0.7) {
			return star.intValue() + "_h";
		}
		return (star.intValue() + 1) + "";
	}
}