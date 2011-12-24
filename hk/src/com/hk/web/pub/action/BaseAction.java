package com.hk.web.pub.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hk.bean.AdminUser;
import com.hk.bean.Box;
import com.hk.bean.City;
import com.hk.bean.CmpPersonTable;
import com.hk.bean.CmpTableSort;
import com.hk.bean.CmpUnion;
import com.hk.bean.CmpUnionKind;
import com.hk.bean.Company;
import com.hk.bean.CompanyPhoto;
import com.hk.bean.DelInfo;
import com.hk.bean.Follow;
import com.hk.bean.IpCity;
import com.hk.bean.Notice;
import com.hk.bean.UrlInfo;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserTool;
import com.hk.bean.ZoneAdmin;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.JsonUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.action.Action;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.SmsClient;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.BoxService;
import com.hk.svr.CmpTableService;
import com.hk.svr.CmpUnionService;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.CompanyService;
import com.hk.svr.FollowService;
import com.hk.svr.IpCityService;
import com.hk.svr.NoticeService;
import com.hk.svr.UserService;
import com.hk.svr.ZoneService;
import com.hk.svr.pub.Err;
import com.hk.web.util.HkStatus;
import com.hk.web.util.HkWebConfig;
import com.hk.web.util.HkWebUtil;
import com.hk.web.util.HttpShoppingCard;
import com.hk.web.util.ViewObjUtil;

public abstract class BaseAction implements Action {

	public int size20 = 20;

	public int size30;

	/**
	 * 检查喇叭是否是通知中的喇叭，如果是，就置通知为已读
	 * 
	 * @param req
	 */
	public void checkNoticeLaba(HkRequest req) {
		NoticeService noticeService = (NoticeService) HkUtil
				.getBean("noticeService");
		User loginUser = this.getLoginUser(req);
		long labaId = req.getLong("labaId");
		if (loginUser != null) {// 判断通知是否是当前查看喇叭,如果是就把通知置为已读
			if (noticeService.countNoReadNotice(loginUser.getUserId(),
					Notice.NOTICETYPE_LABAREPLY) == 1) {
				Notice notice = noticeService.getLastNoReadNotice(loginUser
						.getUserId(), Notice.NOTICETYPE_LABAREPLY);
				if (notice != null) {
					Map<String, String> map = JsonUtil.getMapFromJson(notice
							.getData());
					long oid = Long.valueOf(map.get("labaid"));
					if (oid == labaId) {
						noticeService.setNoticeRead(loginUser.getUserId(),
								notice.getNoticeId());
					}
				}
			}
		}
	}

	/**
	 * @param req
	 */
	public void setZoneList(HkRequest req) {
		// ZoneService zoneService = (ZoneService)
		// HkUtil.getBean("zoneService");
		// List<Pcity> clist = zoneService.getPcityListByCountryId(1);// 选取中国
		// List<Province> provincelist = zoneService.getProvinceList(1);
		// req.setAttribute("provincelist", provincelist);
		// req.setAttribute("clist", clist);
	}

	public boolean isCanSms(HttpServletRequest req, long userId) {
		User loginUser = getLoginUser(req);
		if (loginUser == null) {
			return false;
		}
		UserService userService = (UserService) HkUtil.getBean("userService");
		UserOtherInfo myinfo = userService.getUserOtherInfo(loginUser
				.getUserId());
		UserOtherInfo info = userService.getUserOtherInfo(userId);
		if (myinfo.isMobileAlreadyBind() && info.isMobileAlreadyBind()) {
			FollowService followService = (FollowService) HkUtil
					.getBean("followService");
			Follow follow = followService.getFollow(userId, loginUser
					.getUserId());
			if (follow != null) {
				return true;
			}
		}
		return false;
	}

	protected User getLoginUser(HttpServletRequest req) {
		User user = HkWebUtil.getLoginUser(req);
		return user;
	}

	protected String getInput(HkRequest request) {
		return HkWebUtil.getInput(request);
	}

	/**
	 * 同步修改api中对于urlinfo的信息
	 * 
	 * @param req
	 * @return
	 */
	protected UrlInfo getUrlInfo(HttpServletRequest req) {
		// UrlMode urlMode = (UrlMode) request.getAttribute(HkWebUtil.URLMODE);
		UrlInfo urlInfo = HkWebUtil.getLabaUrlInf();
		urlInfo.setNeedGwt(false);
		// if (urlMode != null) {
		// urlInfo.setNeedGwt(urlMode.isNeedGwt());
		// }
		// else {
		// urlInfo.setNeedGwt(true);
		// }
		return urlInfo;
	}

	/**
	 * 同步修改api中对于urlinfo的信息
	 * 
	 * @param req
	 * @return
	 */
	protected UrlInfo getUrlInfoWeb4(HttpServletRequest req) {
		UrlInfo urlInfo = HkWebUtil.getLabaUrlInfoWeb4(req.getContextPath());
		urlInfo.setNeedGwt(false);
		return urlInfo;
	}

	protected UrlInfo getUrlInfoWeb(HttpServletRequest req) {
		UrlInfo urlInfo = HkWebUtil.getLabaUrlInfoWeb(req.getContextPath());
		return urlInfo;
	}

	protected void setDelInfo(HkRequest request, DelInfo info) {
		request.setSessionValue(HkWebUtil.DELINFO, info);
	}

	protected boolean hasBoxPower(Box box, HkRequest req) {
		if (box == null) {
			return false;
		}
		User loginUser = this.getLoginUser(req);
		if (loginUser == null) {
			return false;
		}
		boolean creater = box.getUserId() != loginUser.getUserId();
		if (!creater) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			AdminUser adminUser = userService.getAdminUser(this.getLoginUser(
					req).getUserId());
			if (adminUser == null) {
				req.setSessionMessage("只有宝箱发布者或者管理员才能查看");
				return false;
			}
			return true;
		}
		return true;
	}

	protected boolean isEmpty(String value) {
		return DataUtil.isEmpty(value);
	}

	private final static String LAST_LABA_CONTENT = "com.hk.laba.lastcontent";

	protected void saveSessionLastLaba(HttpServletRequest req, String content) {
		HttpSession session = req.getSession();
		session.setAttribute(LAST_LABA_CONTENT, content);
	}

	/**
	 *暂时无效
	 * 
	 * @param req
	 * @param content
	 * @return 2010-4-10
	 */
	protected boolean isPassLabaToken(HttpServletRequest req, String content) {
		HttpSession session = req.getSession();
		String session_content = (String) session
				.getAttribute(LAST_LABA_CONTENT);
		if (session_content == null) {// 没有喇叭内容,可以提交
			return true;
		}
		if (session_content.equals(content)) {// 与最后一次喇叭内容相同,不能提交
			return false;
		}
		return true;
	}

	protected void clearSessionLastLaba(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.removeAttribute(LAST_LABA_CONTENT);
	}

	protected LabaParserCfg getLabaParserCfgWeb(HkRequest req) {
		LabaParserCfg cfg = new LabaParserCfg();
		cfg.setContextPath(req.getContextPath());
		cfg.setUrlInfo(this.getUrlInfoWeb(req));
		cfg.setFormatRef(true);
		cfg.setCheckUserRef(true);
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			UserTool userTool = (UserTool) req.getSessionValue("userTool");
			if (userTool == null) {
				UserService userService = (UserService) HkUtil
						.getBean("userService");
				userTool = userService.getUserTool(loginUser.getUserId());
				if (userTool == null) {
					userTool = UserTool.createDefault(loginUser.getUserId());
				}
				req.setSessionValue("userTool", userTool);
			}
			if (userTool.getShowReply() > 0) {
				int charsize = 0;
				if (userTool.getShowReply() == 1) {
					charsize = 3;
				}
				else if (userTool.getShowReply() == 2) {
					charsize = 5;
				}
				else if (userTool.getShowReply() == 3) {
					charsize = 8;
				}
				cfg.setFilterReplyCharSize(charsize);
			}
			cfg.setLabartflg(userTool.getLabartflg());
			cfg.setUserId(loginUser.getUserId());
		}
		return cfg;
	}

	protected LabaParserCfg getLabaParserCfgWeb4(HkRequest req) {
		LabaParserCfg cfg = new LabaParserCfg();
		cfg.setContextPath(req.getContextPath());
		cfg.setUrlInfo(this.getUrlInfoWeb4(req));
		cfg.setFormatRef(true);
		cfg.setCheckUserRef(true);
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			UserTool userTool = (UserTool) req.getSessionValue("userTool");
			if (userTool == null) {
				UserService userService = (UserService) HkUtil
						.getBean("userService");
				userTool = userService.getUserTool(loginUser.getUserId());
				if (userTool == null) {
					userTool = UserTool.createDefault(loginUser.getUserId());
				}
				req.setSessionValue("userTool", userTool);
			}
			cfg.setLabartflg(userTool.getLabartflg());
			cfg.setUserId(loginUser.getUserId());
		}
		return cfg;
	}

	protected LabaParserCfg getLabaParserCfg(HkRequest req) {
		LabaParserCfg cfg = new LabaParserCfg();
		// cfg.setContextPath(req.getContextPath());
		cfg.setUrlInfo(this.getUrlInfo(req));
		cfg.setFormatRef(true);
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			UserTool userTool = (UserTool) req.getSessionValue("userTool");
			if (userTool == null) {
				UserService userService = (UserService) HkUtil
						.getBean("userService");
				userTool = userService.getUserTool(loginUser.getUserId());
				if (userTool == null) {
					userTool = UserTool.createDefault(loginUser.getUserId());
				}
				req.setSessionValue("userTool", userTool);
			}
			if (userTool.getShowReply() > 0) {
				int charsize = 0;
				if (userTool.getShowReply() == 1) {
					charsize = 3;
				}
				else if (userTool.getShowReply() == 2) {
					charsize = 5;
				}
				else if (userTool.getShowReply() == 3) {
					charsize = 8;
				}
				cfg.setFilterReplyCharSize(charsize);
			}
			cfg.setLabartflg(userTool.getLabartflg());
			cfg.setUserId(loginUser.getUserId());
		}
		return cfg;
	}

	public void reSetQueryString(HttpServletRequest req) {
		String s = req.getQueryString();
		req.setAttribute("queryString", s);
	}

	public IpZoneInfo getIpZoneInfo(HttpServletRequest req) {
		IpZoneInfo ipZoneInfo = new IpZoneInfo(req.getRemoteAddr());
		return ipZoneInfo;
	}

	protected String getCompanyPort(String port) {
		SmsPortProcessAble cmpSmsPortProcessAble = (SmsPortProcessAble) HkUtil
				.getBean("cmpSmsPortProcessAble");
		SmsClient smsClient = (SmsClient) HkUtil.getBean("smsClient");
		String companyport = smsClient.getSmsConfig().getSpNumber()
				+ cmpSmsPortProcessAble.getBaseSmsPort() + port;
		return companyport;
	}

	protected String processLoginAndReg(HkRequest req, HkResponse resp,
			String login_input, String rurl, boolean reg_status,
			boolean spsubmit) {
		Long userId = (Long) req.getSessionValue("login_userId");
		if (userId == null || userId == 0) {
			if (spsubmit) {
				return null;
			}
			return "r:/tologin.do";
		}
		UserService userService = (UserService) HkUtil.getBean("userService");
		String input = login_input;
		User user = userService.getUser(userId);
		if (user == null) {
			if (spsubmit) {
				return null;
			}
			return "r:/tologin.do";
		}
		req.removeSessionvalue("reg_status");
		if (!reg_status) {
			if (HkWebConfig.isLoginValidateFollow()) {
				if (user.getUserId() != HkWebConfig.getSysFollowUserId()) {
					FollowService followService = (FollowService) HkUtil
							.getBean("followService");
					if (followService.getFollow(HkWebConfig
							.getSysFollowUserId(), user.getUserId()) == null) {
						if (spsubmit) {
							return null;
						}
						req
								.setSessionMessage("系统正在维修保养中,只有维修人员可以进入,少安毋躁.请耐心等待我们的再次开放");
						return "r:/tologin.do";
					}
				}
			}
		}
		HkStatus hkStatus = HkWebUtil.getHkStatus(req);
		if (hkStatus == null) {
			hkStatus = new HkStatus();
		}
		hkStatus.setUserId(user.getUserId());
		hkStatus.setInput(input);
		HkWebUtil.setHkCookie(req, resp, hkStatus);
		String return_url = rurl;
		if (return_url == null) {
			return_url = req.getString("return_url");
		}
		req.removeSessionvalue("login_userId");
		req.removeSessionvalue("login_input");
		if (spsubmit) {
			return this.getSpSubmitJspPath();
		}
		if (!isEmpty(return_url)) {
			return "r:" + return_url;
		}
		return "r:/square.do";
	}

	protected String getWapJsp(String refpath) {
		return "/WEB-INF/page/" + refpath;
	}

	protected String getWebJsp(String refpath) {
		return "/WEB-INF/web/" + refpath;
	}

	protected String getWeb2Jsp(String refpath) {
		return "/WEB-INF/web2/" + refpath;
	}

	protected String getWeb3Jsp(String refpath) {
		return "/WEB-INF/web3/" + refpath;
	}

	protected String getWeb4Jsp(String refpath) {
		return "/WEB-INF/web4/" + refpath;
	}

	protected String getUnionWebJsp(String refpath) {
		return "/WEB-INF/union/web/" + refpath;
	}

	protected String getUnionWapJsp(String refpath) {
		return "/WEB-INF/union/wap/" + refpath;
	}

	// private boolean isPcBrowse(HttpServletRequest req) {
	// Boolean b = (Boolean) req.getAttribute("pcbrowse");
	// if (b != null && b) {
	// return true;
	// }
	// return false;
	// }
	protected String getSpSubmitJspPath() {
		return this.getWeb3Jsp("pub/aftersubmit.jsp");
	}

	protected String getOnSuccessJspPath() {
		return this.getWeb3Jsp("pub/aftersuccess.jsp");
	}

	/**
	 * web版使用此方法 主要是用来验证用户输入的数据，并返回相应信息到页面，配合ajax或者iframe使用 当验证通过时使用
	 * 
	 * @param req
	 * @param op_func
	 * @return
	 */
	protected String initSuccess(HkRequest req, String op_func) {
		return initError(req, Err.SUCCESS, op_func);
	}

	protected String onSuccess(HkRequest req, String value, String op_func) {
		req.setAttribute("value", value);
		req.setAttribute("op_func", op_func);
		return this.getOnSuccessJspPath();
	}

	/**
	 * web版使用此方法 主要是用来验证用户输入的数据，并返回相应信息到页面，配合ajax或者iframe使用 当验证不通过时使用
	 * 
	 * @param req
	 * @param code
	 * @param op_func
	 * @return
	 */
	protected String initError(HkRequest req, int code, String op_func) {
		return this.initError(req, code, null, op_func);
	}

	protected String initError(HkRequest req, int code, Object[] args,
			String op_func) {
		return this.initError(req, code, -1, args, op_func, null, null);
	}

	protected String initError(HkRequest req, int code, int view_obj_idx,
			Object[] args, String op_func) {
		return this.initError(req, code, view_obj_idx, args, op_func,
				"afterSubmit", null);
	}

	protected String initError(HkRequest req, int code, int view_obj_idx,
			Object[] args, String op_func, String functionName, Object respValue) {
		ViewProcess viewProcess = new ViewProcess();
		viewProcess.setError(code);
		viewProcess.setView_obj_idx(view_obj_idx);
		viewProcess.setOp_func(op_func);
		viewProcess.setRespValue(respValue);
		viewProcess.setError_msg(req.getText(code + "", args));
		if (functionName == null) {
			viewProcess.setFunctionName("afterSubmit");
		}
		else {
			viewProcess.setFunctionName(functionName);
		}
		viewProcess.setJspPath(this.getSpSubmitJspPath());
		return this.afterProc(req, viewProcess);
	}

	protected String initSuccess(HkRequest req, String op_func,
			String functionName, Object respValue) {
		return this.initError(req, Err.SUCCESS, 0, null, op_func, functionName,
				respValue);
	}

	protected void setOpFuncSuccessMsg(HkRequest req) {
		req.setSessionText("op.submitinfook");
	}

	protected void setDelSuccessMsg(HkRequest req) {
		req.setSessionText("op.delinfook");
	}

	protected String getWebLoginForward(HkRequest req) {
		String query = req.getQueryString();
		if (DataUtil.isEmpty(query)) {
			query = "";
		}
		String r = req.getRequestURL().toString();
		return "r:/reg_toregweb.do?return_url="
				+ DataUtil.urlEncoder(r + "?" + query);
	}

	protected String getNotFoundForward(HkResponse resp) {
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}

	protected String afterProc(HkRequest req, ViewProcess viewProcess) {
		req.setAttribute("error", viewProcess.getError());
		req.setAttribute("error_msg", viewProcess.getError_msg());
		req.setAttribute("op_func", viewProcess.getOp_func());
		req.setAttribute("functionName", viewProcess.getFunctionName());
		if (viewProcess.getRespValue() != null) {
			req.setAttribute("respValue", viewProcess.getRespValue());
		}
		String obj_id_param = ViewObjUtil.getValue(viewProcess.getError());
		if (obj_id_param != null) {
			if (viewProcess.getView_obj_idx() != -1) {
				req.setAttribute("obj_id_param",
						obj_id_param.split(",")[viewProcess.getView_obj_idx()]);
			}
			else {
				req.setAttribute("obj_id_param", obj_id_param);
			}
		}
		return viewProcess.getJspPath();
	}

	protected String onError(HkRequest req, int code, String functionName,
			Object respValue) {
		req.setAttribute("error", code);
		req.setAttribute("error_msg", req.getText(String.valueOf(code)));
		req.setAttribute("functionName", functionName);
		if (respValue != null) {
			req.setAttribute("respValue", respValue);
		}
		return this.getWeb3Jsp("pub/onerror.jsp");
	}

	protected String onError(HkRequest req, int code, Object[] args,
			String functionName, Object respValue) {
		req.setAttribute("error", code);
		req.setAttribute("error_msg", req.getText(String.valueOf(code), args));
		req.setAttribute("functionName", functionName);
		if (respValue != null) {
			req.setAttribute("respValue", respValue);
		}
		return this.getWeb3Jsp("pub/onerror.jsp");
	}

	protected String onErrorList(HkRequest req, List<Integer> list,
			String functionName) {
		Map<String, String> map = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		for (Integer i : list) {
			map.put(String.valueOf("error_" + i), req
					.getText(String.valueOf(i)));
			sb.append(i).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String json = JsonUtil.toJson(map);
		req.setAttribute("json", json);
		req.setAttribute("errorlist", sb.toString());
		req.setAttribute("functionName", functionName);
		return this.getWeb3Jsp("pub/onerrorlist.jsp");
	}

	protected String onSuccess2(HkRequest req, String functionName,
			Object respValue) {
		return this.onError(req, Err.SUCCESS, functionName, respValue);
	}

	/**
	 * 处理页面分页
	 * 
	 * @param <T>
	 * @param req
	 * @param list
	 *            结果集
	 * @param page
	 *            分页类
	 */
	protected <T> void processPage(HkRequest req, List<T> list, SimplePage page) {
		req.setAttribute("page", page.getPage());
		if (page.getPage() > 1) {
			req.setAttribute("haspre", true);
		}
		if (list.size() == page.getSize() + 1) {
			req.setAttribute("hasmore", true);
			list.remove(list.size() - 1);
		}
	}

	protected HttpShoppingCard getShoppingCard(HkRequest req) {
		return (HttpShoppingCard) req
				.getAttribute(HkWebUtil.HTTPSHOPPINGCARDATTR);
	}

	protected void loadTableListData(HkRequest req) {
		CmpTableService cmpTableService = (CmpTableService) HkUtil
				.getBean("cmpTableService");
		// 获取台面分类相关数据
		long companyId = req.getLongAndSetAttr("companyId");
		List<CmpTableSort> sortlist = cmpTableService
				.getCmpTableSortListByCompanyId(companyId);
		List<CmpPersonTable> plist = cmpTableService
				.getCmpPersonTableListByCompanyIdForTotalCountNotZero(companyId);
		List<CmpTableSort> newsortlist = new ArrayList<CmpTableSort>();
		for (CmpTableSort sort : sortlist) {
			List<CmpPersonTable> cptlist = new ArrayList<CmpPersonTable>();
			for (CmpPersonTable cpt : plist) {
				if (cpt.getSortId() == sort.getSortId()) {
					cptlist.add(cpt);
				}
			}
			sort.setCmpPersonTableList(cptlist);
			if (cptlist.size() > 0) {
				newsortlist.add(sort);
			}
		}
		req.setAttribute("plist", plist);
		req.setAttribute("sortlist", newsortlist);
	}

	protected void loadTableListData2(HkRequest req) {
		CmpTableService cmpTableService = (CmpTableService) HkUtil
				.getBean("cmpTableService");
		// 获取台面分类相关数据
		long companyId = req.getLongAndSetAttr("companyId");
		List<CmpTableSort> sortlist = cmpTableService
				.getCmpTableSortListByCompanyId(companyId);
		List<CmpPersonTable> plist = cmpTableService
				.getCmpPersonTableListByCompanyIdForTotalCountNotZero(companyId);
		List<CmpTableSort> newsortlist = new ArrayList<CmpTableSort>();
		for (CmpTableSort sort : sortlist) {
			int freeCount = 0;
			int totalCount = 0;
			for (CmpPersonTable cpt : plist) {
				if (cpt.getSortId() == sort.getSortId()) {
					freeCount += cpt.getFreeCount();
					totalCount += cpt.getTotalCount();
				}
			}
			sort.setFreeCount(freeCount);
			sort.setTotalCount(totalCount);
			newsortlist.add(sort);
		}
		req.setAttribute("plist", plist);
		req.setAttribute("sortlist", newsortlist);
	}

	protected boolean isAdminUser(HkRequest req) {
		return HkWebUtil.isAdminUser(req);
	}

	protected long getUidFromCompany(long companyId) {
		CompanyService companyService = (CompanyService) HkUtil
				.getBean("companyService");
		CmpUnionService cmpUnionService = (CmpUnionService) HkUtil
				.getBean("cmpUnionService");
		long uid = 0;
		Company company = companyService.getCompany(companyId);
		if (company != null && company.getUid() >= 0) {
			CmpUnion cmpUnion = cmpUnionService.getCmpUnion(company.getUid());
			if (cmpUnion != null) {
				uid = cmpUnion.getUid();
			}
		}
		return uid;
	}

	protected void processListForPage(SimplePage page, List<?> list) {
		if (list.size() == page.getSize() + 1) {
			page.setHasNext(true);
			list.remove(page.getSize());
		}
	}

	protected void loadCmpUnionKindList(List<CmpUnionKind> list, long kindId) {
		if (kindId <= 0) {
			return;
		}
		CmpUnionService cmpUnionService = (CmpUnionService) HkUtil
				.getBean("cmpUnionService");
		CmpUnionKind kind = cmpUnionService.getCmpUnionKind(kindId);
		if (kind.getParentId() > 0) {
			this.loadCmpUnionKindList(list, kind.getParentId());
		}
		list.add(kind);
	}

	protected int getPcityId(HkRequest req) {
		Integer obj = (Integer) req
				.getAttribute(HkWebUtil.SYS_ZONE_PCITYID_ATTR_KEY);
		if (obj == null) {
			return 0;
		}
		return obj;
	}

	protected ZoneAdmin getZoneAdmin(HttpServletRequest req) {
		return (ZoneAdmin) req.getAttribute("zoneAdmin");
	}

	protected void getRangePhotoId(HkRequest req, long companyId, long photoId) {
		CompanyPhotoService companyPhotoService = (CompanyPhotoService) HkUtil
				.getBean("companyPhotoService");
		List<Long> list = companyPhotoService
				.getPhotoIdListByCompanyIdVoteStyle(companyId);
		Long[] arr = list.toArray(new Long[list.size()]);
		int idx = -1;
		int pre_idx = -1;
		int next_idx = -1;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].longValue() == photoId) {
				idx = i;
				break;
			}
		}
		if (idx == 0) {
			pre_idx = idx;
			idx = idx + 1;
			next_idx = idx + 1;
		}
		else {
			pre_idx = idx - 1;
			next_idx = idx + 1;
		}
		List<CompanyPhoto> photolist = new ArrayList<CompanyPhoto>();
		if (pre_idx >= 0 && pre_idx < arr.length) {
			CompanyPhoto pre_photo = companyPhotoService
					.getCompanyPhoto(arr[pre_idx]);
			photolist.add(pre_photo);
			req.setAttribute("pre_id", arr[pre_idx]);
			if (pre_idx - 1 >= 0 && pre_idx - 1 < arr.length) {
				req.setAttribute("has_pre", true);
			}
		}
		if (idx >= 0 && idx < arr.length) {
			CompanyPhoto companyPhoto = companyPhotoService
					.getCompanyPhoto(arr[idx]);
			photolist.add(companyPhoto);
		}
		if (next_idx >= 0 && next_idx < arr.length) {
			CompanyPhoto nex_photo = companyPhotoService
					.getCompanyPhoto(arr[next_idx]);
			photolist.add(nex_photo);
			req.setAttribute("next_id", arr[next_idx]);
			if (next_idx - 1 >= 0 && next_idx - 1 < arr.length) {
				req.setAttribute("has_next", true);
			}
		}
		req.setAttribute("photolist", photolist);
	}

	protected String getData(Company company) {
		Map<String, String> datamap = new HashMap<String, String>();
		datamap.put("companyid", String.valueOf(company.getCompanyId()));
		datamap.put("cmpname", company.getName());
		return JsonUtil.toJson(datamap);
	}

	protected String getLoginForward() {
		return "r:/tologin.do";
	}

	protected boolean hasOpBoxPower(HkRequest req) {
		BoxService boxService = (BoxService) HkUtil.getBean("boxService");
		User loginUser = this.getLoginUser(req);
		Box box = boxService.getBox(req.getInt("boxId"));
		if (box == null) {
			return false;
		}
		if (box.getUserId() == loginUser.getUserId() || isAdminUser(req)) {
			return true;
		}
		return false;
	}

	protected String getZoneNameFromIdP(String ip) {
		IpCityService ipCityService = (IpCityService) HkUtil
				.getBean("ipCityService");
		IpCity ipCity = ipCityService.getIpCityByIp(ip);
		if (ipCity != null) {
			ZoneService zoneService = (ZoneService) HkUtil
					.getBean("zoneService");
			City city = zoneService.getCityLike(DataUtil.filterZoneName(ipCity
					.getName()));
			if (city != null) {
				return city.getCity();
			}
		}
		return null;
	}

	protected boolean isForwardPage(HkRequest req) {
		if (req.getInt("ch") == 0) {
			return true;
		}
		return false;
	}

	protected void setSuccessMsg(HkRequest req) {
		req.setSessionText("op.setting.op.success");
	}
}