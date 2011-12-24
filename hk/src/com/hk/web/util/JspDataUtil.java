package com.hk.web.util;

import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.Box;
import com.hk.bean.BoxPretype;
import com.hk.bean.CmpAct;
import com.hk.bean.CmpProduct;
import com.hk.bean.CmpUnion;
import com.hk.bean.CmpUnionBoard;
import com.hk.bean.CmpUnionCmdKind;
import com.hk.bean.CmpUnionKind;
import com.hk.bean.CmpUnionLink;
import com.hk.bean.CmpUnionNotice;
import com.hk.bean.CmpUnionReq;
import com.hk.bean.CmpZoneInfo;
import com.hk.bean.Company;
import com.hk.bean.CompanyTag;
import com.hk.bean.Coupon;
import com.hk.bean.Feed;
import com.hk.bean.Pcity;
import com.hk.bean.RegCode;
import com.hk.bean.User;
import com.hk.bean.UserCmpTip;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.MessageUtil;
import com.hk.frame.util.ServletUtil;
import com.hk.svr.BoxService;
import com.hk.svr.CmpActService;
import com.hk.svr.CmpProductService;
import com.hk.svr.CmpTipService;
import com.hk.svr.CmpUnionMessageService;
import com.hk.svr.CmpUnionService;
import com.hk.svr.CompanyService;
import com.hk.svr.CompanyTagService;
import com.hk.svr.CouponService;
import com.hk.svr.FeedService;
import com.hk.svr.LabaService;
import com.hk.svr.RegCodeService;
import com.hk.svr.UserService;
import com.hk.svr.impl.CompanyScoreConfig;
import com.hk.svr.processor.FeedProcessor;
import com.hk.svr.pub.BoxPretypeUtil;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.feed.action.FeedVo;
import com.hk.web.hk4.venue.action.CmpTipVo;

public class JspDataUtil {

	public static void loadCompanyScore(HttpServletRequest request) {
		List<CompanyScoreConfig> list = CompanyScoreConfig.getList();
		request.setAttribute("companyScoreConfigList", list);
	}

	public static void loadCmpUnionMessage(HttpServletRequest request) {
		CmpUnionMessageService cmpUnionMessageService = (CmpUnionMessageService) HkUtil
				.getBean("cmpUnionMessageService");
		long uid = ServletUtil.getLong(request, "uid");
		int req_count = cmpUnionMessageService.countCmpUnionReqByUid(uid,
				CmpUnionReq.DEALFLG_N);
		int notice_count = cmpUnionMessageService.countCmpUnionNoticeByUid(uid,
				CmpUnionNotice.READFLG_N);
		request.setAttribute("req_count", req_count);
		request.setAttribute("notice_count", notice_count);
		String msg = MessageUtil.getMessage(request);
		request.setAttribute("sys_msg", msg);
	}

	public static void loadCompany(HttpServletRequest request) {
		long companyId = ServletUtil.getLong(request, "companyId");
		Company company = (Company) request.getAttribute("jsp_company");
		if (company == null) {
			CompanyService companyService = (CompanyService) HkUtil
					.getBean("companyService");
			company = companyService.getCompany(companyId);
			request.setAttribute("jsp_company", company);
		}
	}

	public static void loadCmpUnion(HttpServletRequest request) {
		long uid = ServletUtil.getLong(request, "uid");
		CmpUnion cmpUnion = (CmpUnion) request.getAttribute("jsp_cmpUnion");
		if (cmpUnion == null) {
			CmpUnionService cmpUnionService = (CmpUnionService) HkUtil
					.getBean("cmpUnionService");
			cmpUnion = cmpUnionService.getCmpUnion(uid);
			request.setAttribute("jsp_cmpUnion", cmpUnion);
		}
	}

	public static void loadHotTagList(HttpServletRequest request) {
		Integer kindId = (Integer) request.getAttribute("kindId");
		Integer cityId = (Integer) request.getAttribute("cityId");
		if (kindId != null && cityId != null) {
			CompanyTagService companyTagService = (CompanyTagService) HkUtil
					.getBean("companyTagService");
			// 热门标签
			List<CompanyTag> taglist = companyTagService
					.getCompanyTagListForHot(kindId, cityId, 0, 30);
			request.setAttribute("taglist", taglist);
		}
	}

	public static String outLabaTime(HttpServletRequest request, String name) {
		Date date = (Date) request.getAttribute(name);
		if (date == null) {
			return "";
		}
		return DataUtil.getFmtTime(date);
	}

	public static void loadCmpLeftData(HttpServletRequest req, Company o) {
		if (o == null) {
			return;
		}
		long companyId = ServletUtil.getLong(req, "companyId");
		CompanyService companyService = (CompanyService) HkUtil
				.getBean("companyService");
		// 附近的足迹
		List<Company> nearbylist = companyService.getCompanyListNearBy(
				companyId, o.getParentKindId(), o.getPcityId(), o.getMarkerX(),
				o.getMarkerY(), 0, 5);
		if (nearbylist.size() == 5) {
			nearbylist.remove(4);
			req.setAttribute("more_nearbylist", true);
		}
		req.setAttribute("nearbylist", nearbylist);
		// 这个地区的最新其他足迹
		List<Company> othercmplist = companyService.getCompanyListByPcityId(o
				.getPcityId(), 0, 6);
		// 找出当前的足迹，并从列表中删除
		Company removeObj = null;
		for (Company c : othercmplist) {
			if (c.getCompanyId() == companyId) {
				removeObj = c;
				break;
			}
		}
		if (removeObj != null) {
			othercmplist.remove(removeObj);
		}
		if (othercmplist.size() > 4) {
			othercmplist.remove(4);
			req.setAttribute("more_othercmplist", true);
			if (othercmplist.size() > 4) {
				othercmplist.remove(4);
			}
		}
		req.setAttribute("othercmplist", othercmplist);
	}

	public static void loadLoginUser(HttpServletRequest req) {
		UserService userService = (UserService) HkUtil.getBean("userService");
		RegCodeService regCodeService = (RegCodeService) HkUtil
				.getBean("regCodeService");
		long userId = HkWebUtil.getLoginUser(req).getUserId();
		UserOtherInfo userOtherInfo = userService.getUserOtherInfo(userId);
		RegCode regCode = regCodeService.getRegCodeByUserId(userId);
		req.setAttribute("validateemail", userOtherInfo.isAuthedMail());
		req.setAttribute("mobileBind", userOtherInfo.isMobileAlreadyBind());
		req.setAttribute("regCode", regCode);
	}

	public static void loadBoxFormData(HttpServletRequest req) {
		List<BoxPretype> prelist = BoxPretypeUtil.getList();
		req.setAttribute("prelist", prelist);
	}

	public static void loadCmpUnionBox(HttpServletRequest request) {
		long uid = ServletUtil.getLong(request, "uid");
		BoxService boxService = (BoxService) HkUtil.getBean("boxService");
		List<Box> cmpunionboxlist = boxService.getBoxListByUid(uid, 0, 5);
		request.setAttribute("cmpunionboxlist", cmpunionboxlist);
	}

	public static void loadCmpUnionCoupon(HttpServletRequest request) {
		long uid = ServletUtil.getLong(request, "uid");
		CouponService couponService = (CouponService) HkUtil
				.getBean("couponService");
		List<Coupon> cmpunioncouponlist = couponService.getCouponListByUid(uid,
				0, 5);
		request.setAttribute("cmpunioncouponlist", cmpunioncouponlist);
	}

	public static void loadCmpUnionProduct(HttpServletRequest request) {
		long uid = ServletUtil.getLong(request, "uid");
		CmpProductService cmpProductService = (CmpProductService) HkUtil
				.getBean("cmpProductService");
		List<CmpProduct> cmpunionproductlist = cmpProductService
				.getCmpProductListByUid(uid, null, 0, 5);
		request.setAttribute("cmpunionproductlist", cmpunionproductlist);
	}

	public static void loadCmpUnionBoard(HttpServletRequest request) {
		long uid = ServletUtil.getLong(request, "uid");
		CmpUnionService cmpUnionService = (CmpUnionService) HkUtil
				.getBean("cmpUnionService");
		List<CmpUnionBoard> cmpunionboardlist = cmpUnionService
				.getCmpUnionBoardListByUid(uid, 0, 5);
		request.setAttribute("cmpunionboardlist", cmpunionboardlist);
	}

	public static void loadCmpUnionLink(HttpServletRequest request) {
		long uid = ServletUtil.getLong(request, "uid");
		CmpUnionService cmpUnionService = (CmpUnionService) HkUtil
				.getBean("cmpUnionService");
		List<CmpUnionLink> cmpunionlinklist = cmpUnionService
				.getCmpUnionLinkListByUid(uid, 0, 20);
		request.setAttribute("cmpunionlinklist", cmpunionlinklist);
	}

	public static void loadCmpUnionCmdKind(HttpServletRequest request) {
		long uid = ServletUtil.getLong(request, "uid");
		CmpUnionService cmpUnionService = (CmpUnionService) HkUtil
				.getBean("cmpUnionService");
		List<CmpUnionCmdKind> list = cmpUnionService
				.getCmpUnionCmdKindListByUid(uid, 0, 20);
		List<Long> idList = new ArrayList<Long>();
		for (CmpUnionCmdKind kind : list) {
			idList.add(kind.getKindId());
		}
		List<CmpUnionKind> cmdkindlist = cmpUnionService
				.getCmpUnionKindListInId(uid, idList);
		request.setAttribute("cmdkindlist", cmdkindlist);
	}

	public static void loadCmpUnionCmpAct(HttpServletRequest request) {
		long uid = ServletUtil.getLong(request, "uid");
		CmpActService cmpActService = (CmpActService) HkUtil
				.getBean("cmpActService");
		List<CmpAct> cmpactlist = cmpActService.getCmpActListForRun(uid, 0, 5);
		request.setAttribute("cmpactlist", cmpactlist);
	}

	public static void loadLabaRightIncData(HttpServletRequest request) {
		Long userId = (Long) request.getAttribute("userId");
		if (userId == null) {
			return;
		}
		UserService userService = (UserService) HkUtil.getBean("userService");
		LabaService labaService = (LabaService) HkUtil.getBean("labaService");
		User user = userService.getUser(userId);
		request.setAttribute("user", user);
		int labaCount = labaService.countByUserId(userId);
		request.setAttribute("labaCount", labaCount);
	}

	public static void loadCmpZoneInfo(HttpServletRequest request) {
		CompanyService companyService = (CompanyService) HkUtil
				.getBean("companyService");
		List<CmpZoneInfo> infolist = companyService.getCmpZoneInfoList();
		List<Pcity> cmpzonepcitylist = new ArrayList<Pcity>();
		for (CmpZoneInfo o : infolist) {
			Pcity pcity = ZoneUtil.getPcity(o.getPcityId());
			if (pcity != null) {
				cmpzonepcitylist.add(pcity);
			}
		}
		Collections.sort(cmpzonepcitylist, zhComparator);
		request.setAttribute("cmpzonepcitylist", cmpzonepcitylist);
	}

	public static void loadUserCmpTipDoneList(HttpServletRequest request,
			int size) {
		User loginUser = HkWebUtil.getLoginUser(request);
		long excludeUserId = 0;
		if (loginUser != null) {
			excludeUserId = loginUser.getUserId();
		}
		CmpTipService cmpTipService = (CmpTipService) HkUtil
				.getBean("cmpTipService");
		Integer pcityId = (Integer) request
				.getAttribute(HkWebUtil.SYS_ZONE_PCITYID_ATTR_KEY);
		if (pcityId != null) {
			if (pcityId.intValue() > 0) {
				List<UserCmpTip> usercmptipdonelist = cmpTipService
						.getUserCmpTipDoneListByPcityId(pcityId, excludeUserId,
								0, size);
				List<CmpTipVo> cmptipdonevolist = CmpTipVo.createVoList2(
						usercmptipdonelist, 0, true);
				request.setAttribute("cmptipdonevolist", cmptipdonevolist);
			}
		}
	}

	public static void loadUserCmpTipToDoList(HttpServletRequest request,
			int size) {
		User loginUser = HkWebUtil.getLoginUser(request);
		long excludeUserId = 0;
		if (loginUser != null) {
			excludeUserId = loginUser.getUserId();
		}
		CmpTipService cmpTipService = (CmpTipService) HkUtil
				.getBean("cmpTipService");
		Integer pcityId = (Integer) request
				.getAttribute(HkWebUtil.SYS_ZONE_PCITYID_ATTR_KEY);
		if (pcityId != null) {
			if (pcityId.intValue() > 0) {
				List<UserCmpTip> usercmptiptodolist = cmpTipService
						.getUserCmpTipToDoListByPcityId(pcityId, excludeUserId,
								0, size);
				List<CmpTipVo> cmptiptodovolist = CmpTipVo.createVoList2(
						usercmptiptodolist, 0, true);
				request.setAttribute("cmptiptodovolist", cmptiptodovolist);
			}
		}
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("M月d日 HH:mm");

	private static SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");

	public static String outPutWeekAndTime(HttpServletRequest request) {
		Calendar cal_now = (Calendar) request.getAttribute("cal_now");
		if (cal_now == null) {
			cal_now = Calendar.getInstance();
			request.setAttribute("cal_now", cal_now);
		}
		Calendar cal = (Calendar) request.getAttribute("cal_jspdatautil");
		if (cal == null) {
			cal = Calendar.getInstance();
			request.setAttribute("cal_jspdatautil", cal);
		}
		Date createTime = (Date) request.getAttribute("createTime");
		cal.setTime(createTime);
		int w = cal.get(Calendar.DAY_OF_WEEK);
		if (cal.get(Calendar.DATE) == cal_now.get(Calendar.DATE)) {
			return getWeekValue(w) + " " + sdf2.format(createTime);
		}
		return getWeekValue(w) + " " + sdf.format(createTime);
	}

	public static String outStyleTime(HttpServletRequest request) {
		Calendar cal_now = (Calendar) request.getAttribute("cal_now");
		if (cal_now == null) {
			cal_now = Calendar.getInstance();
			request.setAttribute("cal_now", cal_now);
		}
		Calendar cal = (Calendar) request.getAttribute("cal_jspdatautil");
		if (cal == null) {
			cal = Calendar.getInstance();
			request.setAttribute("cal_jspdatautil", cal);
		}
		Date createTime = (Date) request.getAttribute("createTime");
		cal.setTime(createTime);
		int date = cal.get(Calendar.DATE);
		int now_date = cal_now.get(Calendar.DATE);
		if (date == now_date) {
			return "今天  " + sdf2.format(createTime);
		}
		if (now_date - date == 1) {
			return "昨天 " + sdf2.format(createTime);
		}
		return sdf.format(createTime);
	}

	public static void loadEquipmentFeed(HttpServletRequest request) {
		FeedService feedService = (FeedService) HkUtil.getBean("feedService");
		UserService userService = (UserService) HkUtil.getBean("userService");
		List<Feed> list = feedService.getFeedListByFeedType(Feed.FEEDTYPE_EQU,
				0, 10);
		List<FeedVo> equfeedvolist = FeedVo.createList(request, list, false,
				false);
		List<Long> idList = new ArrayList<Long>();
		for (Feed o : list) {
			idList.add(o.getUserId());
		}
		Map<Long, User> usermap = userService.getUserMapInId(idList);
		for (Feed o : list) {
			o.setUser(usermap.get(o.getUserId()));
		}
		request.setAttribute("equfeedvolist", equfeedvolist);
	}

	public static void loadBoxFeed(HttpServletRequest request) {
		FeedProcessor feedProcessor = (FeedProcessor) HkUtil
				.getBean("feedProcessor");
		List<Feed> list = feedProcessor.getFeedListByFeedType(
				Feed.FEEDTYPE_OPENBOX, true, 0, 50);
		LinkedHashMap<Long, Feed> feedMap = new LinkedHashMap<Long, Feed>();
		for (Feed feed : list) {
			if (!feedMap.containsKey(feed.getUserId())) {
				feedMap.put(feed.getUserId(), feed);
			}
		}
		list = new ArrayList<Feed>(feedMap.values());
		List<FeedVo> boxfeedvolist = FeedVo.createList(request, list, true,
				false);
		boxfeedvolist = DataUtil.subList(boxfeedvolist, 0, 10);
		request.setAttribute("boxfeedvolist", boxfeedvolist);
	}

	public static String getWeekValue(int w) {
		if (w == 1) {
			return "周日";
		}
		if (w == 2) {
			return "周一";
		}
		if (w == 3) {
			return "周二";
		}
		if (w == 4) {
			return "周三";
		}
		if (w == 5) {
			return "周四";
		}
		if (w == 6) {
			return "周五";
		}
		if (w == 7) {
			return "周六";
		}
		return null;
	}

	private static final Comparator<Pcity> zhComparator = new Comparator<Pcity>() {

		private final Collator cmp = Collator.getInstance(Locale.CHINA);

		public int compare(Pcity o1, Pcity o2) {
			return cmp.compare(o1.getName(), o2.getName());
		}
	};
}