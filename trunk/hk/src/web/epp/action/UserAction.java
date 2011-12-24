package web.epp.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;
import web.pub.util.WebUtil;

import com.hk.bean.City;
import com.hk.bean.CmpInfo;
import com.hk.bean.CmpMyBbs;
import com.hk.bean.CmpOrg;
import com.hk.bean.CmpOrgApply;
import com.hk.bean.CmpOrgStudyAdUser;
import com.hk.bean.CmpOtherWebInfo;
import com.hk.bean.CmpRefUser;
import com.hk.bean.Company;
import com.hk.bean.IpCity;
import com.hk.bean.User;
import com.hk.bean.UserRegData;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpOrgService;
import com.hk.svr.CmpOrgStudyAdUserService;
import com.hk.svr.CmpOtherWebInfoService;
import com.hk.svr.CmpRefUserService;
import com.hk.svr.IpCityService;
import com.hk.svr.UserService;
import com.hk.svr.ZoneService;
import com.hk.svr.processor.CmpBbsProcessor;
import com.hk.svr.processor.CmpOrgStudyAdUserProcessor;
import com.hk.svr.processor.UserProcessor;
import com.hk.svr.processor.UserRegResult;
import com.hk.svr.pub.Err;
import com.hk.svr.user.exception.EmailDuplicateException;
import com.hk.svr.user.exception.MobileDuplicateException;
import com.hk.web.hk4.user.RegUser;

@Component("/epp/web/user")
public class UserAction extends EppBaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private UserProcessor userProcessor;

	@Autowired
	private CmpBbsProcessor cmpBbsProcessor;

	@Autowired
	private CmpRefUserService cmpRefUserService;

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private ZoneService zoneService;

	@Autowired
	private CmpOrgService cmpOrgService;

	@Autowired
	private CmpOtherWebInfoService cmpOtherWebInfoService;

	@Autowired
	private CmpOrgStudyAdUserService cmpOrgStudyAdUserService;

	@Autowired
	private CmpOrgStudyAdUserProcessor cmpOrgStudyAdUserProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		int cmpflg = this.getCmpflg(req);
		if (cmpflg == 0) {
			return this.exe0(req, resp);
		}
		if (cmpflg == 2) {
			return this.exe2(req, resp);
		}
		return null;
	}

	public String exe0(HkRequest req, HkResponse resp) throws Exception {
		int tml = this.getTmlflg(req);
		if (tml == 0) {
			return this.exe00(req, resp);
		}
		return null;
	}

	public String exe2(HkRequest req, HkResponse resp) throws Exception {
		int tml = this.getTmlflg(req);
		if (tml == 0) {
			return this.exe20(req, resp);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-15
	 */
	public String exe00(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLongAndSetAttr("userId");
		long companyId = req.getLong("companyId");
		User user = this.userService.getUser(userId);
		req.setAttribute("user", user);
		List<CmpMyBbs> mycreatelist = this.cmpBbsProcessor
				.getCmpMyBbsListByCompanyIdAndUserIdAndBbsflg(companyId,
						userId, CmpMyBbs.BBSFLG_CREATE, true, 0, 7);
		if (mycreatelist.size() == 7) {
			req.setAttribute("more_create", true);
			mycreatelist.remove(6);
		}
		req.setAttribute("mycreatelist", mycreatelist);
		List<CmpMyBbs> myreplylist = this.cmpBbsProcessor
				.getCmpMyBbsListByCompanyIdAndUserIdAndBbsflg(companyId,
						userId, CmpMyBbs.BBSFLG_REPLY, true, 0, 7);
		if (myreplylist.size() == 7) {
			req.setAttribute("more_reply", true);
			myreplylist.remove(6);
		}
		req.setAttribute("myreplylist", myreplylist);
		return this.getWebPath("user/user.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-15
	 */
	public String exe20(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLongAndSetAttr("userId");
		long companyId = req.getLong("companyId");
		User user = this.userService.getUser(userId);
		req.setAttribute("user", user);
		User loginUser = this.getLoginUser2(req);
		CmpOrg cmpOrg = this.cmpOrgService.getCmpOrgByCompanyIdAndUserId(
				companyId, user.getUserId());
		req.setAttribute("cmpOrg", cmpOrg);
		if (loginUser != null && loginUser.getUserId() == userId) {
			if (cmpOrg == null) {
				CmpOrgApply cmpOrgApply = this.cmpOrgService
						.getCmpOrgApplyByCompanyIdAndUserId(companyId,
								loginUser.getUserId());
				req.setAttribute("cmpOrgApply", cmpOrgApply);
			}
		}
		// 用户的报名表
		List<CmpOrgStudyAdUser> cmporgstudyaduserlist = this.cmpOrgStudyAdUserProcessor
				.getCmpOrgStudyAdUserListByCompanyidAndUserId(companyId,
						userId, true, 0, 11);
		if (cmporgstudyaduserlist.size() == 11) {
			req.setAttribute("more_cmporgstudyaduser", true);
			cmporgstudyaduserlist.remove(10);
		}
		req.setAttribute("cmporgstudyaduserlist", cmporgstudyaduserlist);
		return this.getWebPath("mod/2/0/user/user.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-15
	 */
	public String bmlist(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLongAndSetAttr("userId");
		long companyId = req.getLong("companyId");
		User user = this.userService.getUser(userId);
		req.setAttribute("user", user);
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.cmpOrgStudyAdUserService
				.countCmpOrgStudyAdUserByCompanyidAndUserId(companyId, userId));
		// 用户的报名表
		List<CmpOrgStudyAdUser> list = this.cmpOrgStudyAdUserProcessor
				.getCmpOrgStudyAdUserListByCompanyidAndUserId(companyId,
						userId, true, page.getBegin(), page.getSize());
		req.setAttribute("list", list);
		return this.getWebPath("mod/2/0/user/bmlist.jsp");
	}

	/**
	 * 用户注册
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-25
	 */
	public String bbs(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long userId = req.getLongAndSetAttr("userId");
		User user = this.userService.getUser(userId);
		req.setAttribute("user", user);
		byte bbsflg = req.getByte("bbsflg");
		SimplePage page = req.getSimplePage(20);
		List<CmpMyBbs> list = this.cmpBbsProcessor
				.getCmpMyBbsListByCompanyIdAndUserIdAndBbsflg(companyId,
						userId, bbsflg, true, page.getBegin(),
						page.getSize() + 1);
		req.setAttribute("list", list);
		if (bbsflg == CmpMyBbs.BBSFLG_CREATE) {
			req.setAttribute("user_create_bbs", true);
		}
		else {
			req.setAttribute("user_reply_bbs", true);
		}
		return this.getWebPath("user/userbbs.jsp");
	}

	/**
	 * 用户注册
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-25
	 */
	private String reg00(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		User loginUser = this.getLoginUser2(req);
		if (loginUser != null) {
			return "r:http://" + req.getServerName();
		}
		int ch = req.getInt("ch");
		if (ch == 0) {
			String ip = req.getRemoteAddr();
			if (ip != null) {
				IpCity ipCity = this.ipCityService.getIpCityByIp(ip);
				if (ipCity != null) {
					City city = this.zoneService.getCityLike(DataUtil
							.filterZoneName(ipCity.getName()));
					req.setAttribute("city", city);
				}
			}
			return this.getWebPath("/reg/reg.jsp");
		}
		String email = req.getString("email");
		String mobile = req.getString("mobile");
		String password = req.getString("password");
		byte sex = req.getByte("sex", (byte) -1);
		RegUser regUser = new RegUser(req);
		List<Integer> codelist = regUser.validate(true);
		if (codelist.size() > 0) {
			return this.onErrorList(req, codelist, "regerrorlist");
		}
		UserRegData userRegData = new UserRegData();
		userRegData.setZoneName(req.getHtmlRow("zoneName"));
		userRegData.setEmail(email);
		userRegData.setMobile(mobile);
		userRegData.setPassword(password);
		userRegData.setSex(sex);
		userRegData.setInviteUserId(req.getLong("inviteUserId"));
		userRegData.setIp(req.getRemoteAddr());
		userRegData.setProuserId(req.getLong("prouserId"));
		userRegData.setNickName(req.getHtmlRow("nickName"));
		try {
			UserRegResult userRegResult = this.userProcessor.reg(userRegData,
					false);
			if (userRegResult.getErrorCode() != Err.SUCCESS) {
				return this.onError(req, userRegResult.getErrorCode(),
						"regerror", null);
			}
			long userId = userRegResult.getUserId();
			CmpRefUser cmpRefUser = new CmpRefUser();
			cmpRefUser.setCompanyId(companyId);
			cmpRefUser.setUserId(userId);
			cmpRefUser.setJoinflg(CmpRefUser.JOINFLG_REG);
			this.cmpRefUserService.createCmpRefUser(cmpRefUser);
			if (userRegResult.isNickNameDuplicate()) {
				req.setSessionText("epp.nickname_duplicate_tip", userRegData
						.getNickName());
			}
			User user = this.userService.getUser(userId);
			WebUtil.setLoginUser2(req, resp, user, email, true);
			return this.onSuccess2(req, "regok", null);
		}
		catch (EmailDuplicateException e) {
			return this.onError(req, Err.EPP_EMAIL_ALREADY_EXIST,
					new Object[] { req.getContextPath()
							+ "/epp/web/pwd.do?companyId="
							+ req.getLong("companyId") }, "regerror", null);
		}
		catch (MobileDuplicateException e) {
			return this
					.onError(req, Err.MOBILE_ALREADY_EXIST, "regerror", null);
		}
	}

	/**
	 * 用户注册
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-25
	 */
	private String reg01(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		User loginUser = this.getLoginUser2(req);
		if (loginUser != null) {
			return "r:http://" + req.getServerName();
		}
		int ch = req.getInt("ch");
		if (ch == 0) {
			String ip = req.getRemoteAddr();
			if (ip != null) {
				IpCity ipCity = this.ipCityService.getIpCityByIp(ip);
				if (ipCity != null) {
					City city = this.zoneService.getCityLike(DataUtil
							.filterZoneName(ipCity.getName()));
					req.setAttribute("city", city);
				}
			}
			return this.getWebPath("mod/0/1/reg/reg.jsp");
		}
		String email = req.getString("email");
		String mobile = req.getString("mobile");
		String password = req.getString("password");
		byte sex = req.getByte("sex", (byte) -1);
		RegUser regUser = new RegUser(req);
		List<Integer> codelist = regUser.validate(true);
		if (codelist.size() > 0) {
			return this.onErrorList(req, codelist, "regerrorlist");
		}
		UserRegData userRegData = new UserRegData();
		userRegData.setZoneName(req.getHtmlRow("zoneName"));
		userRegData.setEmail(email);
		userRegData.setMobile(mobile);
		userRegData.setPassword(password);
		userRegData.setSex(sex);
		userRegData.setInviteUserId(req.getLong("inviteUserId"));
		userRegData.setIp(req.getRemoteAddr());
		userRegData.setProuserId(req.getLong("prouserId"));
		userRegData.setNickName(req.getHtmlRow("nickName"));
		try {
			UserRegResult userRegResult = this.userProcessor.reg(userRegData,
					false);
			if (userRegResult.getErrorCode() != Err.SUCCESS) {
				return this.onError(req, userRegResult.getErrorCode(),
						"regerror", null);
			}
			long userId = userRegResult.getUserId();
			CmpRefUser cmpRefUser = new CmpRefUser();
			cmpRefUser.setCompanyId(companyId);
			cmpRefUser.setUserId(userId);
			cmpRefUser.setJoinflg(CmpRefUser.JOINFLG_REG);
			this.cmpRefUserService.createCmpRefUser(cmpRefUser);
			if (userRegResult.isNickNameDuplicate()) {
				req.setSessionText("epp.nickname_duplicate_tip", userRegData
						.getNickName());
			}
			User user = this.userService.getUser(userId);
			WebUtil.setLoginUser2(req, resp, user, email, true);
			return this.onSuccess2(req, "regok", null);
		}
		catch (EmailDuplicateException e) {
			return this.onError(req, Err.EPP_EMAIL_ALREADY_EXIST,
					new Object[] { req.getContextPath()
							+ "/epp/web/pwd.do?companyId="
							+ req.getLong("companyId") }, "regerror", null);
		}
		catch (MobileDuplicateException e) {
			return this
					.onError(req, Err.MOBILE_ALREADY_EXIST, "regerror", null);
		}
	}

	/**
	 * 注册流程，对于不同的企业网站类型以及模板类型有不同的注册过程实现
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-7
	 */
	public String reg(HkRequest req, HkResponse resp) throws Exception {
		Company o = (Company) req.getAttribute("o");
		if (o.getCmpflg() == 0) {
			return this.reg0(req, resp);
		}
		if (o.getCmpflg() == 2) {// 教育类型
			return this.reg2(req, resp);
		}
		return null;
	}

	private String reg0(HkRequest req, HkResponse resp) throws Exception {
		int tmlflg = this.getTmlflg(req);
		if (tmlflg == 0) {
			return this.reg00(req, resp);
		}
		if (tmlflg == 1) {
			return this.reg01(req, resp);
		}
		return null;
	}

	private String reg2(HkRequest req, HkResponse resp) throws Exception {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		if (cmpInfo.getTmlflg() == 0) {
			return this.reg20(req, resp);
		}
		return null;
	}

	/**
	 * 用户注册分为2部分，先进行注册，如果用户点击机构注册时，下一步到申请机构注册页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-7
	 */
	private String reg20(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		User loginUser = this.getLoginUser2(req);
		CmpOtherWebInfo cmpOtherWebInfo = this.cmpOtherWebInfoService
				.getCmpOtherWebInfo(companyId);
		req.setAttribute("cmpOtherWebInfo", cmpOtherWebInfo);
		if (loginUser != null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			String ip = req.getRemoteAddr();
			if (ip != null) {
				IpCity ipCity = this.ipCityService.getIpCityByIp(ip);
				if (ipCity != null) {
					City city = this.zoneService.getCityLike(DataUtil
							.filterZoneName(ipCity.getName()));
					req.setAttribute("city", city);
				}
			}
			return this.getWebPath("mod/2/0/reg/reg.jsp");
		}
		String email = req.getString("email");
		String mobile = req.getString("mobile");
		String password = req.getString("password");
		byte sex = req.getByte("sex", (byte) -1);
		RegUser regUser = new RegUser(req);
		List<Integer> codelist = regUser.validate(true);
		if (codelist.size() > 0) {
			return this.onErrorList(req, codelist, "regerrorlist");
		}
		UserRegData userRegData = new UserRegData();
		userRegData.setZoneName(req.getHtmlRow("zoneName"));
		userRegData.setEmail(email);
		userRegData.setMobile(mobile);
		userRegData.setPassword(password);
		userRegData.setSex(sex);
		userRegData.setInviteUserId(req.getLong("inviteUserId"));
		userRegData.setIp(req.getRemoteAddr());
		userRegData.setProuserId(req.getLong("prouserId"));
		userRegData.setNickName(req.getHtmlRow("nickName"));
		try {
			UserRegResult userRegResult = this.userProcessor.reg(userRegData,
					false);
			if (userRegResult.getErrorCode() != Err.SUCCESS) {
				return this.onError(req, userRegResult.getErrorCode(),
						"regerror", null);
			}
			long userId = userRegResult.getUserId();
			CmpRefUser cmpRefUser = new CmpRefUser();
			cmpRefUser.setCompanyId(companyId);
			cmpRefUser.setUserId(userId);
			cmpRefUser.setJoinflg(CmpRefUser.JOINFLG_REG);
			this.cmpRefUserService.createCmpRefUser(cmpRefUser);
			if (userRegResult.isNickNameDuplicate()) {
				req.setSessionText("epp.nickname_duplicate_tip", userRegData
						.getNickName());
			}
			User user = this.userService.getUser(userId);
			WebUtil.setLoginUser2(req, resp, user, email, true);
			return this.onSuccess2(req, "regok", userId);
		}
		catch (EmailDuplicateException e) {
			return this.onError(req, Err.EPP_EMAIL_ALREADY_EXIST,
					new Object[] { req.getContextPath()
							+ "/epp/web/pwd.do?companyId="
							+ req.getLong("companyId") }, "regerror", null);
		}
		catch (MobileDuplicateException e) {
			return this
					.onError(req, Err.MOBILE_ALREADY_EXIST, "regerror", null);
		}
	}

	/**
	 * 申请教育机构
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-7
	 */
	public String adminorg(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		CmpOtherWebInfo cmpOtherWebInfo = this.cmpOtherWebInfoService
				.getCmpOtherWebInfo(companyId);
		if (cmpOtherWebInfo != null && cmpOtherWebInfo.isOrgNeedCheck()) {
			return "r:/epp/web/user_prvapplyorg.do?companyId=" + companyId;
		}
		// 不需要申请，直接创建机构
		return "r:/epp/web/user_createorg.do?companyId=" + companyId;
	}

	/**
	 * 创建机构
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-7
	 */
	public String createorg(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		CmpOtherWebInfo cmpOtherWebInfo = this.cmpOtherWebInfoService
				.getCmpOtherWebInfo(companyId);
		// 如果需要申请，就不能创建
		if (cmpOtherWebInfo != null && cmpOtherWebInfo.isOrgNeedCheck()) {
			return null;
		}
		if (this.isForwardPage(req)) {
			return this.getWebPath("mod/2/0/user/createorg.jsp");
		}
		User loginUser = this.getLoginUser2(req);
		// 不需要申请，直接创建机构
		CmpOrg cmpOrg = this.cmpOrgService.getCmpOrgByCompanyIdAndUserId(
				companyId, loginUser.getUserId());
		if (cmpOrg != null) {
			return this.onSuccess2(req, "createok", null);
		}
		cmpOrg = new CmpOrg();
		cmpOrg.setName(req.getHtmlRow("orgname"));
		cmpOrg.setCompanyId(companyId);
		cmpOrg.setFlg(CmpOrg.FLG_Y);
		cmpOrg.setUserId(loginUser.getUserId());
		int code = cmpOrg.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		this.cmpOrgService.createCmpOrg(cmpOrg);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * 申请教育机构
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-7
	 */
	public String prvapplyorg(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		User loginUser = this.getLoginUser2(req);
		CmpOrg cmpOrg = this.cmpOrgService.getCmpOrgByCompanyIdAndUserId(
				companyId, loginUser.getUserId());
		if (cmpOrg == null) {
			req.setAttribute("can_apply_org", true);
		}
		if (this.isForwardPage(req)) {
			return this.getWebPath("mod/2/0/user/applyorg.jsp");
		}
		if (cmpOrg != null) {
			return this.onSuccess2(req, "applyok", null);
		}
		CmpOrgApply cmpOrgApply = this.cmpOrgService
				.getCmpOrgApplyByCompanyIdAndUserId(companyId, loginUser
						.getUserId());
		if (cmpOrgApply != null) {
			return this.onSuccess2(req, "applyok", null);
		}
		cmpOrgApply = new CmpOrgApply();
		cmpOrgApply.setUserName(req.getHtmlRow("userName"));
		cmpOrgApply.setEmail(req.getHtmlRow("email"));
		cmpOrgApply.setTel(req.getString("tel"));
		cmpOrgApply.setCompanyId(companyId);
		cmpOrgApply.setOrgName(req.getHtmlRow("orgname"));
		cmpOrgApply.setUserId(loginUser.getUserId());
		int code = cmpOrgApply.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "applyerror", null);
		}
		this.cmpOrgService.createCmpOrgApply(cmpOrgApply);
		req.setSessionText("epp.cmporgapply.create.success");
		return this.onSuccess2(req, "applyok", null);
	}
}