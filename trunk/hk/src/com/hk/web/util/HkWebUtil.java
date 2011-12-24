package com.hk.web.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import com.hk.bean.AdminUser;
import com.hk.bean.CmpActor;
import com.hk.bean.CmpFunc;
import com.hk.bean.CmpFuncRef;
import com.hk.bean.CmpFuncUtil;
import com.hk.bean.Company;
import com.hk.bean.CompanyKind;
import com.hk.bean.CompanyKindUtil;
import com.hk.bean.DelInfo;
import com.hk.bean.HkbLog;
import com.hk.bean.ParentKind;
import com.hk.bean.UrlInfo;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserSms;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.DesUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.util.mail.MailUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.view.CssColor;
import com.hk.frame.web.view.CssColorUtil;
import com.hk.frame.web.view.ShowMode;
import com.hk.frame.web.view.ShowModeUtil;
import com.hk.sms.BatchSms;
import com.hk.sms.Sms;
import com.hk.sms.SmsClient;
import com.hk.svr.CmpActorService;
import com.hk.svr.CmpFuncService;
import com.hk.svr.CompanyService;
import com.hk.svr.MsgService;
import com.hk.svr.NoticeService;
import com.hk.svr.UserService;
import com.hk.svr.UserSmsService;

public class HkWebUtil {

	// public static String LOGIN_USER = "com.hk.login.user.status";
	// public static String COOKIE_USER_USERNAME = "com.hk.login.user.input";
	public static String LOGIN_USER = "loginUser";

	public static String WAP_COOKIE_SET = "X-Wap-Proxy-Cookie";

	public static String WAP_COOKIE_SET_VALUE = "none";

	public static String COOKIE_USER_USERNAME = "hkinput";

	public static String HK_STATUS = "hkstatus";// v;userId_string;user_input;

	public static String BOMBER_SYS_LOGIN = "com.hk.bombersyslogin";

	public static String DELINFO = "com_hk_delinfo";

	public static String LASTURL_PARAM = "lasturl";

	public static int COOKIE_MAXAGE = 1209600;

	private static final Map<Long, Long> loginUserIdmap = new ConcurrentHashMap<Long, Long>();

	public static String SHOW_MENU = "showMenu";

	public static String SHOWMODE = "showMode";

	public static String CSSCOLOR = "cssColor";

	public static String URLMODE = "urlMode";

	public static String USERIDSET = "useridset";

	public static String labarefflg = " RT ";

	public static String ISADMIN_USER_ATTR = "sys_isadmin_user";

	public static String HTTPSHOPPINGCARDATTR = "httpshoppingcard";

	public static String PCITYCOOKIE_KEY = "com.hk.pcityid.key.v2";

	public static int PCITYCOOKIE_MAXAGE = 30 * 24 * 60 * 60;

	public static String SYS_ZONE_PCITYID_ATTR_KEY = "sys_zone_pcityId";

	public static String SYS_ZONE_PCITY_ATTR_KEY = "sys_zone_pcity";

	public static String USERSTOPSTATUS_ATTR_KEY = "com.hk.user.stopstatus.attr.key";

	public static void setWapCookieConfig(HttpServletResponse response) {
		response.setHeader("X-Wap-Proxy-Cookie", "none");
	}

	public static int getPcityId(HttpServletRequest request) {
		Cookie cookie = ServletUtil.getCookie(request, PCITYCOOKIE_KEY);
		if (cookie == null) {
			return 0;
		}
		int cityId;
		try {// 防止人为破坏cookie，所以加此异常
			cityId = Integer.valueOf(cookie.getValue());
		}
		catch (Exception e) {
			return 0;
		}
		return cityId;
	}

	public static void setPcityId(HttpServletRequest request,
			HttpServletResponse response, int pcityId) {
		Cookie cookie = new Cookie(PCITYCOOKIE_KEY, String.valueOf(pcityId));
		cookie.setDomain(getFmtCookieDomain(request.getServerName()));
		cookie.setMaxAge(PCITYCOOKIE_MAXAGE);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static String getPcLoginUrl(HttpServletRequest request) {
		String s = request.getRequestURL().append("?").append(
				request.getQueryString()).toString();
		String path = "http://" + HkWebConfig.getWebDomain()
				+ request.getContextPath() + "/reg_toregweb.do?return_url="
				+ DataUtil.urlEncoder(s);
		return path;
	}

	public static String getWapLoginUrl(HttpServletRequest request) {
		String s = request.getRequestURL().append("?").append(
				request.getQueryString()).toString();
		String path = "http://" + HkWebConfig.getWebDomain()
				+ request.getContextPath() + "/tologin.do?return_url="
				+ DataUtil.urlEncoder(s);
		return path;
	}

	public static void initAdminUser(HttpServletRequest request, long userId) {
		Boolean admin = (Boolean) request.getSession().getAttribute(
				ISADMIN_USER_ATTR);
		if (admin == null) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			AdminUser adminUser = userService.getAdminUser(userId);
			if (adminUser != null) {
				request.getSession().setAttribute(ISADMIN_USER_ATTR, true);
				request.setAttribute(ISADMIN_USER_ATTR, true);
			}
			else {
				request.getSession().setAttribute(ISADMIN_USER_ATTR, false);
				request.setAttribute(ISADMIN_USER_ATTR, false);
			}
		}
		else {
			request.setAttribute(ISADMIN_USER_ATTR, admin);
		}
	}

	public static boolean isAdminUser(HttpServletRequest request) {
		User loginUser = getLoginUser(request);
		if (loginUser == null) {
			return false;
		}
		initAdminUser(request, loginUser.getUserId());
		Boolean admin = (Boolean) request.getAttribute(ISADMIN_USER_ATTR);
		if (admin == null) {
			admin = (Boolean) request.getSession().getAttribute(
					ISADMIN_USER_ATTR);
		}
		if (admin != null && admin.booleanValue()) {
			return true;
		}
		return false;
	}

	public static String getReplyLabel(HttpServletRequest request) {
		ShowMode showMode = (ShowMode) request.getAttribute(SHOWMODE);
		if (showMode.getModeId() == 1) {
			return "回";
		}
		return "<img src=\"" + HkWebConfig.getReplyImg() + "\"/>";
	}

	public static String getDmLabel(HttpServletRequest request) {
		ShowMode showMode = (ShowMode) request.getAttribute(SHOWMODE);
		if (showMode.getModeId() == 1) {
			return "私";
		}
		return "<img src=\"" + HkWebConfig.getDmImg() + "\"/>";
	}

	public static String getFavLabel(HttpServletRequest request) {
		ShowMode showMode = (ShowMode) request.getAttribute(SHOWMODE);
		if (showMode.getModeId() == 1) {
			return "藏";
		}
		return "<img src=\"" + HkWebConfig.getStarGreyImg() + "\"/>";
	}

	public static String getDelFavLabel(HttpServletRequest request) {
		ShowMode showMode = (ShowMode) request.getAttribute(SHOWMODE);
		if (showMode.getModeId() == 1) {
			return "取消收藏";
		}
		return "<img src=\"" + HkWebConfig.getStarImg() + "\"/>";
	}

	public static String getRefLabel(HttpServletRequest request) {
		ShowMode showMode = (ShowMode) request.getAttribute(SHOWMODE);
		if (showMode.getModeId() == 1) {
			return "转";
		}
		return "<img src=\"" + HkWebConfig.getRetweetImg() + "\"/>";
	}

	public static String getRefLabel2(HttpServletRequest request) {
		ShowMode showMode = (ShowMode) request.getAttribute(SHOWMODE);
		if (showMode.getModeId() == 1) {
			return "转";
		}
		return "<img src=\"" + HkWebConfig.getRetweet2Img() + "\"/>";
	}

	public static String getDelLabel(HttpServletRequest request) {
		ShowMode showMode = (ShowMode) request.getAttribute(SHOWMODE);
		if (showMode.getModeId() == 1) {
			return "删";
		}
		return "<img src=\"" + HkWebConfig.getDelImg() + "\"/>";
	}

	public static UrlInfo getLabaUrlInf() {
		UrlInfo urlInfo = new UrlInfo();
		urlInfo.setRewriteUserUrl(true);
		urlInfo.setUserUrl("http://" + HkWebConfig.getWebDomain()
				+ "/home_viewbynickname.do?v={0}");
		urlInfo.setTagUrl("http://" + HkWebConfig.getWebDomain()
				+ HkWebConfig.getContextPath()
				+ "/laba/taglabalist_viewbyname.do?v={0}&reuserId={1}");
		urlInfo.setCompanyUrl("http://" + HkWebConfig.getWebDomain()
				+ "/e/cmp.do?companyId={0}");
		urlInfo.setCmpListUrl("http://" + HkWebConfig.getWebDomain()
				+ "/e/cmp_s.do?name={0}");
		return urlInfo;
	}

	public static UrlInfo getLabaUrlInfoWeb(String contextPath) {
		UrlInfo urlInfo = new UrlInfo();
		urlInfo.setRewriteUserUrl(true);
		urlInfo.setUserUrl("http://" + HkWebConfig.getWebDomain() + contextPath
				+ "/home_webn.do?v={0}");
		urlInfo.setTagUrl("http://" + HkWebConfig.getWebDomain() + contextPath
				+ "/laba/taglabalist_viewbyname.do?v={0}&reuserId={1}");
		urlInfo.setCompanyUrl("http://" + HkWebConfig.getWebDomain()
				+ contextPath + "/cmp.do?companyId={0}");
		urlInfo.setCmpListUrl("http://" + HkWebConfig.getWebDomain()
				+ "/e/cmp_s.do?name={0}");
		return urlInfo;
	}

	public static UrlInfo getLabaUrlInfoWeb4(String contextPath) {
		UrlInfo urlInfo = new UrlInfo();
		urlInfo.setRewriteUserUrl(true);
		urlInfo.setUserUrl("/n?v={0}");
		urlInfo.setTagUrl("http://" + HkWebConfig.getWebDomain() + contextPath
				+ "/laba/taglabalist_n.do?v={0}&reuserId={1}");
		urlInfo.setCompanyUrl("/venue/{0}");
		urlInfo.setCmpListUrl("http://" + HkWebConfig.getWebDomain()
				+ "/search?ch=1&key={0}");
		return urlInfo;
	}

	public static void setLoginUserId(long userId) {
		Long i = Long.valueOf(userId);
		loginUserIdmap.put(i, i);
	}

	public static void removeLoginUserId(long userId) {
		loginUserIdmap.remove(Long.valueOf(userId));
	}

	public static boolean hasLoginUserId(long userId) {
		return loginUserIdmap.containsKey(Long.valueOf(userId));
	}

	public static Object getSessionValue(HttpSession session, String name) {
		return session.getAttribute(name);
	}

	public static void removeSessionValue(HttpSession session, String name) {
		session.removeAttribute(name);
	}

	public static User getLoginUser(HttpServletRequest request) {
		User user = (User) request.getAttribute(LOGIN_USER);
		if (user != null) {
			return user;
		}
		user = (User) request.getSession().getAttribute(LOGIN_USER);
		if (user == null) {
			return null;
		}
		HkStatus hkStatus = getHkStatus(request);
		if (hkStatus == null || hkStatus.getUserId() == 0) {
			return null;
		}
		return user;
	}

	public static int getNoReadNoticeCount(HttpServletRequest request) {
		User user = getLoginUser(request);
		if (user != null) {
			NoticeService noticeService = (NoticeService) HkUtil
					.getBean("noticeService");
			return noticeService.countNoReadNotice(user.getUserId());
		}
		return 0;
	}

	public static int getNewMsgCount(HttpServletRequest request) {
		User user = getLoginUser(request);
		if (user != null) {
			MsgService msgService = (MsgService) HkUtil.getBean("msgService");
			return msgService.countNoRead(user.getUserId());
		}
		return 0;
	}

	public static DelInfo getDelInfo(HttpServletRequest request) {
		DelInfo delInfo = (DelInfo) request.getSession().getAttribute(DELINFO);
		request.getSession().removeAttribute(DELINFO);
		return delInfo;
	}

	public static void removeLoginUser(HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().removeAttribute(HkWebUtil.LOGIN_USER);
		request.getSession().removeAttribute(HkWebUtil.ISADMIN_USER_ATTR);
		request.getSession().removeAttribute(HkWebUtil.USERSTOPSTATUS_ATTR_KEY);
		HkStatus hkStatus = getHkStatus(request);
		if (hkStatus != null) {
			hkStatus.setUserId(0);
			setHkCookie(request, response, hkStatus);
		}
	}

	/**
	 * 顺序为version;userId_string;user_input,若以后有就顺序加新的,版本号自动添加,无需人工
	 * 
	 * @param response
	 * @param list
	 */
	public static void setHkCookie(HttpServletRequest request,
			HttpServletResponse response, HkStatus hkStatus) {
		hkStatus.setVersion(HkWebConfig.getVersion());
		HkWebUtil.setWapCookieConfig(response);
		try {
			Cookie cookie = new Cookie(HK_STATUS, desUtil.encrypt(hkStatus
					.toString()));
			cookie.setPath("/");
			cookie.setDomain(getFmtCookieDomain(request.getServerName()));
			cookie.setMaxAge(HkWebUtil.COOKIE_MAXAGE);
			response.addCookie(cookie);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String getFmtCookieDomain(String domain) {
		if (domain.indexOf("huoku.com") != -1) {
			return ".huoku.com";
		}
		return domain;
	}

	// private static final String WEBSITE = "hk_website";
	// public static WebSiteInfo getWebSiteInfo(HttpServletRequest request,
	// HttpServletResponse response) {
	// WebSiteInfo info = (WebSiteInfo) request.getAttribute(WEBSITE);
	// if (info != null) {
	// return info;
	// }
	// long r_companyid = ServletUtil.getLong(request, "r_companyid");
	// if (r_companyid > 0) {
	// String r_website = ServletUtil.getString(request, "r_website");
	// CompanyService companyService = (CompanyService) HkUtil
	// .getBean("companyService");
	// Company company = companyService.getCompany(r_companyid);
	// if (company != null) {
	// info = new WebSiteInfo();
	// info.setName(company.getName());
	// info.setUrl(r_website);
	// request.setAttribute(WEBSITE, info);
	// StringBuilder sb = new StringBuilder();
	// sb.append(info.getUrl()).append(":").append(info.getName());
	// Cookie cookie = new Cookie(WEBSITE, DataUtil.urlEncoder(sb
	// .toString()));
	// cookie.setMaxAge(-1);
	// cookie.setPath("/");
	// cookie.setDomain(HkWebConfig.getCookie_domain());
	// response.addCookie(cookie);
	// return info;
	// }
	// }
	// else {
	// Cookie cookie = ServletUtil.getCookie(request, WEBSITE);
	// if (cookie != null) {
	// String[] v = DataUtil.urlDecoder(cookie.getValue()).split(":");
	// if (v.length == 2) {
	// info = new WebSiteInfo();
	// info.setUrl(v[0]);
	// info.setName(v[1]);
	// request.setAttribute(WEBSITE, info);
	// return info;
	// }
	// }
	// }
	// return null;
	// }
	private static DesUtil desUtil = new DesUtil("akweiflyshowhuoku");

	public static HkStatus getHkStatus(HttpServletRequest request) {
		Cookie cookie = getCookie(request, HkWebUtil.HK_STATUS);
		if (cookie != null) {
			String s = cookie.getValue();
			if (!DataUtil.isEmpty(s)) {
				try {
					String v = desUtil.decrypt(s);
					String[] ts = v.split(";");
					if (ts == null || ts.length != 6) {
						return null;
					}
					HkStatus hkStatus = new HkStatus();
					hkStatus.setVersion(ts[0]);
					hkStatus.setUserId(Long.parseLong(ts[1]));
					hkStatus.setInput(ts[2]);
					hkStatus.setShowModeId(Integer.parseInt(ts[3]));
					hkStatus.setCssColorId(Integer.parseInt(ts[4]));
					hkStatus.setUrlModeId(Integer.parseInt(ts[5]));
					return hkStatus;
				}
				catch (Exception e) {
					return null;
				}
			}
		}
		return null;
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {
		return ServletUtil.getCookie(request, name);
	}

	public static String getInput(HttpServletRequest request) {
		HkStatus hkStatus = getHkStatus(request);
		if (hkStatus == null) {
			return null;
		}
		return hkStatus.getInput();
	}

	public static void outPrint(JspWriter out, String s) throws IOException {
		out.print(s);
	}

	public static HkStatus getAdapterHkStatus(HttpServletRequest request) {
		HkStatus hkStatus = new HkStatus();
		if (ServletUtil.isPc(request)) {// pc方式直接打开链接
			hkStatus.setCssColorId(0);
			hkStatus.setShowModeId(0);
			hkStatus.setUrlModeId(1);
		}
		else {// mobile方式通过gwt打开链接
			hkStatus.setCssColorId(0);
			hkStatus.setShowModeId(0);
			hkStatus.setUrlModeId(2);
		}
		return hkStatus;
	}

	public static void sendBatchSms(BatchSms batchSms, HkbLog hkbLog) {
		SmsClient smsClient = (SmsClient) HkUtil.getBean("smsClient");
		smsClient.sendBatchIgnoreError(batchSms);
		if (hkbLog != null) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			userService.addHkb(hkbLog);
		}
	}

	public static String getUserPort(@SuppressWarnings("unused") long userId) {
//		UserSmsPortService userSmsPortService = (UserSmsPortService) HkUtil
//				.getBean("userSmsPortService");
//		UserSmsPort myUserSmsPort = userSmsPortService
//				.getUserSmsPortByUserId(userId);
//		SmsPortProcessAble usersms_smsport = (SmsPortProcessAble) HkUtil
//				.getBean("usersms_smsport");
//		if (myUserSmsPort == null) {
//			return null;
//		}
		// update 由于短信功能失效，所以不使用
//		return usersms_smsport.getBaseSmsPort() + myUserSmsPort.getPort();
		return "";
	}

	public static UserSms sendSms(Sms sms, long senderId, HkbLog hkbLog) {
		SmsClient smsClient = (SmsClient) HkUtil.getBean("smsClient");
		String content = sms.getContent();
		String add_0 = ResourceConfig.getText("func.sendsms_add0");
		UserSmsService userSmsService = (UserSmsService) HkUtil
				.getBean("userSmsService");
		UserService userService = (UserService) HkUtil.getBean("userService");
		String add_1 = ResourceConfig.getText("func.sendsms_add1", userService
				.getUser(senderId).getNickName());
		String content0 = content + add_0;
		String content1 = content + add_1;
		if (content1.length() <= 70) {
			sms.setContent(content1);
		}
		else if (content0.length() <= 70) {
			sms.setContent(content0);
		}
		try {
			UserOtherInfo info = userService.getUserOtherInfoByMobile(sms
					.getMobile());
			if (info == null) {
				return null;
			}
			UserSms userSms = new UserSms();
			userSms.setContent(DataUtil.toHtmlRow(sms.getContent()));
			userSms.setPort(sms.getPort());
			userSms.setReceiverId(info.getUserId());
			userSms.setSenderId(senderId);
			userSmsService.createUserSms(userSms);
			sms.setExmsgid(userSms.getMsgId() + "");
			smsClient.sendIgnoreError(sms);
			if (hkbLog != null) {
				userService.addHkb(hkbLog);
			}
			return userSms;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void initBrowser(HttpServletRequest request) {
		HkStatus hkStatus = (HkStatus) request
				.getAttribute(HkWebUtil.HK_STATUS);
		int showModeId = 0;
		int cssColorId = 0;
		if (hkStatus != null) {
			showModeId = hkStatus.getShowModeId();
			cssColorId = hkStatus.getCssColorId();
		}
		String smid = request.getParameter("smid");
		if (!DataUtil.isEmpty(smid)) {
			showModeId = Integer.parseInt(smid);
		}
		String ccid = request.getParameter("ccid");
		if (!DataUtil.isEmpty(ccid)) {
			cssColorId = Integer.parseInt(ccid);
		}
		ShowModeUtil showModeUtil = (ShowModeUtil) HkUtil
				.getBean("showModeUtil");
		CssColorUtil cssColorUtil = (CssColorUtil) HkUtil
				.getBean("cssColorUtil");
		ShowMode showMode = showModeUtil.getShowMode(showModeId);
		CssColor cssColor = cssColorUtil.getCssColor(cssColorId);
		request.setAttribute(HkWebUtil.SHOWMODE, showMode);
		request.setAttribute(HkWebUtil.CSSCOLOR, cssColor);
	}

	public static void sendRegMail(HkRequest req, String mail) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		MailUtil mailUtil = (MailUtil) HkUtil.getBean("mailUtil");
		String date = sdf.format(new Date());
		String content = req.getText("reg.mailwhenreg", "http://"
				+ HkWebConfig.getWebDomain(), date);
		try {
			mailUtil
					.sendHtmlMail(mail, req.getText("func.mail.title"), content);
		}
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public static void loadCompany(HttpServletRequest request) {
		long companyId = ServletUtil.getLong(request, "companyId");
		Company company = (Company) request.getAttribute("company");
		if (company == null) {
			CompanyService companyService = (CompanyService) HkUtil
					.getBean("companyService");
			company = companyService.getCompany(companyId);
			request.setAttribute("company", company);
		}
	}

	public static void loadCmpFuncInfo(HttpServletRequest request) {
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpFuncService cmpFuncService = (CmpFuncService) HkUtil
				.getBean("cmpFuncService");
		List<CmpFuncRef> list = cmpFuncService
				.getCmpFuncRefListByCompanyId(companyId);
		for (CmpFuncRef o : list) {
			if (o.getFuncoid() == 1) {
				request.setAttribute("hasfuncoid_reserve", true);
			}
			if (o.getFuncoid() == 2) {
				request.setAttribute("hasfuncoid_googleadcode", true);
			}
		}
	}

	private static final String VIEWERID_KEY = "hk_viewerid";

	/**
	 * 对访问网站的用户分配唯一id
	 * 
	 * @param request
	 * @return
	 *         2010-5-8
	 */
	public static String getViewerId(HttpServletRequest request,
			HttpServletResponse response) {
		Cookie cookie = ServletUtil.getCookie(request, VIEWERID_KEY);
		if (cookie != null) {
			String v = cookie.getValue().trim();
			if (v.length() > 37) {
				String ipv = v.substring(5, v.length() - 32);
				if (ipv.equals(String.valueOf(DataUtil.parseIpNumber(request
						.getRemoteAddr())))) {
					return v;
				}
			}
		}
		return createViewerId(request, response);
	}

	private static String createViewerId(HttpServletRequest request,
			HttpServletResponse response) {
		String viewerId = DataUtil.getRandom(5)
				+ DataUtil.parseIpNumber(request.getRemoteAddr())
				+ request.getSession().getId().toLowerCase();
		Cookie cookie = new Cookie(VIEWERID_KEY, viewerId);
		cookie.setPath("/");
		cookie.setDomain(request.getServerName());
		cookie.setMaxAge(HkWebUtil.COOKIE_MAXAGE);
		response.addCookie(cookie);
		return viewerId;
	}

	public static void loadAdmin(HttpServletRequest request) {
		User loginUser = HkWebUtil.getLoginUser(request);
		UserService userService = (UserService) HkUtil.getBean("userService");
		if (loginUser != null) {
			if (userService.getAdminUser(loginUser.getUserId()) != null) {
				request.setAttribute("userAdmin", true);
			}
		}
	}

	/**
	 * 获取足迹分类与子分类的信息放到request中
	 * 
	 * @param request
	 *            2010-8-12
	 */
	public static void loadCmpKindInfo(HttpServletRequest request) {
		List<ParentKind> parentlist = CompanyKindUtil.getParentList();
		List<CompanyKind> kindlist = CompanyKindUtil.getCompanKindList();
		request.setAttribute("parentlist", parentlist);
		request.setAttribute("kindlist", kindlist);
	}

	public static void loadCmpFuncList(HttpServletRequest request) {
		List<CmpFunc> cmpfunclist = CmpFuncUtil.getCmpFuncList();
		request.setAttribute("cmpfunclist", cmpfunclist);
	}

	/**
	 * 判断是huoku.com/或是其他域名访问
	 * 
	 * @param request
	 *            2010-8-20
	 */
	public static void loadCmpServerNameInfo(HttpServletRequest request) {
		String domain = request.getServerName();
		if (domain.indexOf("huoku.com") != -1) {
			request.setAttribute("huoku_view", true);
		}
		else {
			request.setAttribute("huoku_view", false);
		}
	}

	public static void loadCmpActorForReserve(HttpServletRequest request) {
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpActorService cmpActorService = (CmpActorService) HkUtil
				.getBean("cmpActorService");
		List<CmpActor> actorlist = cmpActorService
				.getCmpActorListByCompanyIdForCanReserve(companyId, 0, -1);
		request.setAttribute("actorlist", actorlist);
	}
}