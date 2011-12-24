package web.epp.action.org;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;
import web.pub.util.WebUtil;

import com.hk.bean.CmpOrgStudyAd;
import com.hk.bean.CmpOrgStudyAdContent;
import com.hk.bean.CmpOrgStudyAdUser;
import com.hk.bean.CmpRefUser;
import com.hk.bean.CmpStudyKind;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserRegData;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpOrgStudyAdService;
import com.hk.svr.CmpOrgStudyAdUserService;
import com.hk.svr.CmpRefUserService;
import com.hk.svr.CmpStudyKindService;
import com.hk.svr.UserService;
import com.hk.svr.processor.CmpOrgStudyAdProcessor;
import com.hk.svr.processor.CmpOrgStudyAdUserProcessor;
import com.hk.svr.processor.UserProcessor;
import com.hk.svr.processor.UserRegResult;
import com.hk.svr.pub.Err;
import com.hk.svr.user.exception.EmailDuplicateException;
import com.hk.svr.user.exception.MobileDuplicateException;
import com.hk.web.hk4.user.RegUser;

@Component("/epp/web/org/studyad")
public class StudyAdAction extends EppBaseAction {

	@Autowired
	private CmpOrgStudyAdService cmpOrgStudyAdService;

	@Autowired
	private CmpOrgStudyAdUserService cmpOrgStudyAdUserService;

	@Autowired
	private CmpStudyKindService cmpStudyKindService;

	@Autowired
	private CmpOrgStudyAdUserProcessor cmpOrgStudyAdUserProcessor;

	@Autowired
	private CmpOrgStudyAdProcessor cmpOrgStudyAdProcessor;

	@Autowired
	private UserProcessor userProcessor;

	@Autowired
	private UserService userService;

	@Autowired
	private CmpRefUserService cmpRefUserService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		this.loadOrgInfo(req);
		long companyId = req.getLong("companyId");
		long orgId = req.getLong("orgId");
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.cmpOrgStudyAdService
				.countCmpOrgStudyAdByCompanyIdAndOrgId(companyId, orgId));
		List<CmpOrgStudyAd> list = this.cmpOrgStudyAdService
				.getCmpOrgStudyAdListByCompanyIdAndOrgId(companyId, orgId,
						null, page.getBegin(), page.getSize());
		req.setAttribute("list", list);
		return this.getWebPath("mod/2/0/org/studyad/list.jsp");
	}

	/**
	 * 查看招生简章
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-11
	 */
	public String view(HkRequest req, HkResponse resp) throws Exception {
		this.loadOrgInfo(req);
		long companyId = req.getLong("companyId");
		long orgId = req.getLong("orgId");
		long adid = req.getLongAndSetAttr("adid");
		CmpOrgStudyAd cmpOrgStudyAd = this.cmpOrgStudyAdService
				.getCmpOrgStudyAd(companyId, adid);
		if (cmpOrgStudyAd == null) {
			return null;
		}
		CmpOrgStudyAdContent cmpOrgStudyAdContent = this.cmpOrgStudyAdService
				.getCmpOrgStudyAdContent(companyId, adid);
		CmpStudyKind cmpStudyKind = this.cmpStudyKindService.getCmpStudyKind(
				companyId, cmpOrgStudyAd.getKindId());
		req.setAttribute("cmpStudyKind", cmpStudyKind);
		req.setAttribute("cmpOrgStudyAd", cmpOrgStudyAd);
		req.setAttribute("cmpOrgStudyAdContent", cmpOrgStudyAdContent);
		List<CmpOrgStudyAd> nextlist = this.cmpOrgStudyAdService
				.getCmpOrgStudyAdListByCompanyIdAndOrgIdForNext(companyId,
						orgId, adid, 1);
		req.setAttribute("nextlist", nextlist);
		User loginUser = this.getLoginUser2(req);
		if (loginUser != null) {
			UserOtherInfo userOtherInfo = this.userService
					.getUserOtherInfo(loginUser.getUserId());
			req.setAttribute("userOtherInfo", userOtherInfo);
			// 用户最后报名的时间
			List<CmpOrgStudyAdUser> list = this.cmpOrgStudyAdUserService
					.getCmpOrgStudyAdUserListByCompanyidAndAdidAndUserId(
							companyId, adid, loginUser.getUserId(), 0, 1);
			if (list.size() > 0) {
				req.setAttribute("last_bm_CmpOrgStudyAdUser", list.get(0));
			}
		}
		return this.getWebPath("mod/2/0/org/studyad/view.jsp");
	}

	/**
	 * 创建招生简章
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-11
	 */
	public String selkind(HkRequest req, HkResponse resp) throws Exception {
		this.loadOrgInfo(req);
		long companyId = req.getLong("companyId");
		long adid = req.getLongAndSetAttr("adid");
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		CmpOrgStudyAd cmpOrgStudyAd = this.cmpOrgStudyAdService
				.getCmpOrgStudyAd(companyId, adid);
		req.setAttribute("cmpOrgStudyAd", cmpOrgStudyAd);
		if (this.isForwardPage(req)) {
			String name = req.getHtmlRow("name");
			long parentId = req.getLongAndSetAttr("parentId");
			CmpStudyKind parent = this.cmpStudyKindService.getCmpStudyKind(
					companyId, parentId);
			req.setAttribute("parent", parent);
			PageSupport page = req.getPageSupport(20);
			page.setTotalCount(this.cmpStudyKindService
					.countCmpStudyKindByCompanyIdAndParentIdEx(companyId,
							parentId, name));
			List<CmpStudyKind> list = this.cmpStudyKindService
					.getCmpStudyKindListByCompanyIdAndParentIdEx(companyId,
							parentId, name, page.getBegin(), page.getSize());
			req.setAttribute("list", list);
			return this.getWebPath("mod/2/0/org/studyad/selkind.jsp");
		}
		long kindId = req.getLong("kindId");
		if (cmpOrgStudyAd != null) {
			CmpStudyKind cmpStudyKind = this.cmpStudyKindService
					.getCmpStudyKind(companyId, kindId);
			if (cmpStudyKind == null) {
				return null;
			}
			cmpOrgStudyAd.setKindId(kindId);
			this.cmpOrgStudyAdProcessor
					.updateCmpOrgStudyAd(cmpOrgStudyAd, null);
			this.setOpFuncSuccessMsg(req);
		}
		String return_url = req.getReturnUrl();
		if (DataUtil.isEmpty(return_url)) {
			return null;
		}
		return "r:" + return_url + "&kindId=" + kindId;
	}

	/**
	 * 创建招生简章
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-11
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		this.loadOrgInfo(req);
		long companyId = req.getLong("companyId");
		long orgId = req.getLong("orgId");
		long kindId = req.getLongAndSetAttr("kindId");
		CmpStudyKind cmpStudyKind = this.cmpStudyKindService.getCmpStudyKind(
				companyId, kindId);
		if (cmpStudyKind == null) {
			return "r:/edu/" + companyId + "/" + orgId + "/zhaosheng/"
					+ req.getLong("orgnavId");
		}
		req.setAttribute("cmpStudyKind", cmpStudyKind);
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		if (this.isForwardPage(req)) {
			return this.getWebPath("mod/2/0/org/studyad/create.jsp");
		}
		CmpOrgStudyAd cmpOrgStudyAd = new CmpOrgStudyAd();
		cmpOrgStudyAd.setCompanyId(companyId);
		cmpOrgStudyAd.setOrgId(orgId);
		cmpOrgStudyAd.setTitle(req.getHtmlRow("title"));
		cmpOrgStudyAd.setCreateTime(new Date());
		cmpOrgStudyAd.setAvailableTime(DataUtil.parseTime(req
				.getString("availableTime"), "yyyy-MM-dd"));
		cmpOrgStudyAd.setBeginTime(DataUtil.parseTime(req
				.getString("beginTime"), "yyyy-MM-dd"));
		cmpOrgStudyAd.setSchoolName(req.getHtmlRow("schoolName"));
		cmpOrgStudyAd.setStudyAddr(req.getHtmlRow("studyAddr"));
		cmpOrgStudyAd.setStudyUser(req.getHtmlRow("studyUser"));
		cmpOrgStudyAd.setPrice(req.getHtmlRow("price"));
		cmpOrgStudyAd.setTeachType(req.getHtmlRow("teachType"));
		cmpOrgStudyAd.setKindId(kindId);
		CmpOrgStudyAdContent cmpOrgStudyAdContent = new CmpOrgStudyAdContent();
		cmpOrgStudyAdContent.setCompanyId(companyId);
		cmpOrgStudyAdContent.setOrgId(orgId);
		cmpOrgStudyAdContent.setContent(req.getHtmlWithoutBeginTrim("content"));
		int code = cmpOrgStudyAd.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		code = cmpOrgStudyAdContent.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		this.cmpOrgStudyAdProcessor.createCmpOrgStudyAd(cmpOrgStudyAd,
				cmpOrgStudyAdContent);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", cmpOrgStudyAd.getAdid());
	}

	/**
	 * 修改招生简章
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-11
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		this.loadOrgInfo(req);
		long companyId = req.getLong("companyId");
		long adid = req.getLongAndSetAttr("adid");
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		CmpOrgStudyAd cmpOrgStudyAd = this.cmpOrgStudyAdService
				.getCmpOrgStudyAd(companyId, adid);
		CmpOrgStudyAdContent cmpOrgStudyAdContent = this.cmpOrgStudyAdService
				.getCmpOrgStudyAdContent(companyId, adid);
		if (this.isForwardPage(req)) {
			long kindId = cmpOrgStudyAd.getKindId();
			CmpStudyKind cmpStudyKind = this.cmpStudyKindService
					.getCmpStudyKind(companyId, kindId);
			req.setAttribute("cmpStudyKind", cmpStudyKind);
			req.setAttribute("cmpOrgStudyAd", cmpOrgStudyAd);
			req.setAttribute("cmpOrgStudyAdContent", cmpOrgStudyAdContent);
			return this.getWebPath("mod/2/0/org/studyad/update.jsp");
		}
		cmpOrgStudyAd.setTitle(req.getHtmlRow("title"));
		cmpOrgStudyAd.setCreateTime(new Date());
		cmpOrgStudyAd.setAvailableTime(DataUtil.parseTime(req
				.getString("availableTime"), "yyyy-MM-dd"));
		cmpOrgStudyAd.setBeginTime(DataUtil.parseTime(req
				.getString("beginTime"), "yyyy-MM-dd"));
		cmpOrgStudyAd.setSchoolName(req.getHtmlRow("schoolName"));
		cmpOrgStudyAd.setStudyAddr(req.getHtmlRow("studyAddr"));
		cmpOrgStudyAd.setStudyUser(req.getHtmlRow("studyUser"));
		cmpOrgStudyAd.setPrice(req.getHtmlRow("price"));
		cmpOrgStudyAd.setTeachType(req.getHtmlRow("teachType"));
		cmpOrgStudyAdContent.setContent(req.getHtmlWithoutBeginTrim("content"));
		int code = cmpOrgStudyAd.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		code = cmpOrgStudyAdContent.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		this.cmpOrgStudyAdProcessor.updateCmpOrgStudyAd(cmpOrgStudyAd,
				cmpOrgStudyAdContent);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 修改招生简章
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-11
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		this.loadOrgInfo(req);
		long companyId = req.getLong("companyId");
		long adid = req.getLongAndSetAttr("adid");
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		CmpOrgStudyAd cmpOrgStudyAd = this.cmpOrgStudyAdService
				.getCmpOrgStudyAd(companyId, adid);
		if (cmpOrgStudyAd == null) {
			return null;
		}
		this.cmpOrgStudyAdService.deleteCmpOrgStudyAd(companyId, adid);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * 修改招生简章
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-11
	 */
	public String pubbm(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long adid = req.getLong("adid");
		long orgId = req.getLong("orgId");
		// 验证码检测
		String imgv = req.getString("imgv");
		String imgv_session = (String) req
				.getSessionValue(HkUtil.CLOUD_IMAGE_AUTH);
		if (!DataUtil.eqNotNull(imgv, imgv_session)) {
			return this.onError(req, Err.IMG_VALIDATE_CODE_ERROR,
					"createerror", null);
		}
		User loginUser = this.getLoginUser2(req);
		long userId = 0;
		if (loginUser == null) {
			// 先注册后报名
			RegUser regUser = new RegUser(req);
			String pwd = DataUtil.getRandom(6);
			regUser.setPassword(pwd);
			regUser.setRepassword(pwd);
			regUser.setNickName(req.getHtmlRow("name"));
			List<Integer> codelist = regUser.validate(false);
			if (codelist.size() > 0) {
				return this.onErrorList(req, codelist, "createerrorlist");
			}
			UserRegData userRegData = new UserRegData();
			userRegData.setEmail(req.getHtmlRow("email"));
			userRegData.setMobile(req.getHtmlRow("mobile"));
			userRegData.setPassword(pwd);
			userRegData.setZoneName(req.getHtmlRow("city"));
			userRegData.setSex(req.getByte("sex"));
			userRegData.setIp(req.getRemoteAddr());
			userRegData.setNickName(req.getHtmlRow("name"));
			try {
				UserRegResult userRegResult = this.userProcessor.reg(
						userRegData, false);
				if (userRegResult.getErrorCode() != Err.SUCCESS) {
					return this.onError(req, userRegResult.getErrorCode(),
							"createerror", null);
				}
				userId = userRegResult.getUserId();
				CmpRefUser cmpRefUser = new CmpRefUser();
				cmpRefUser.setCompanyId(companyId);
				cmpRefUser.setUserId(userId);
				cmpRefUser.setJoinflg(CmpRefUser.JOINFLG_REG);
				this.cmpRefUserService.createCmpRefUser(cmpRefUser);
				User user = this.userService.getUser(userId);
				UserOtherInfo userOtherInfo = this.userService
						.getUserOtherInfo(userId);
				userOtherInfo.setName(req.getHtmlRow("name"));
				int code = userOtherInfo.validate();
				if (code == Err.SUCCESS) {
					this.userService.updateUserOtherInfo(userOtherInfo);
				}
				WebUtil.setLoginUser2(req, resp, user, req.getHtmlRow("email"),
						true);
			}
			catch (EmailDuplicateException e) {
				return this.onError(req, Err.EMAIL_ALREADY_EXIST,
						"createerror", null);
			}
			catch (MobileDuplicateException e) {
				return this.onError(req, Err.MOBILE_ALREADY_EXIST,
						"createerror", null);
			}
		}
		else {
			userId = loginUser.getUserId();
			if (DataUtil.isLegalMobile(req.getString("mobile"))) {
				UserOtherInfo userOtherInfo = this.userService
						.getUserOtherInfo(userId);
				if (DataUtil.isEmpty(userOtherInfo.getMobile())) {
					try {
						this.userService.updateMobile(userId, req
								.getString("mobile"));
					}
					catch (MobileDuplicateException e) {
						return this.onError(req, Err.MOBILE_ALREADY_EXIST,
								"createerror", null);
					}
				}
			}
		}
		CmpOrgStudyAdUser cmpOrgStudyAdUser = new CmpOrgStudyAdUser();
		cmpOrgStudyAdUser.setUserId(userId);
		cmpOrgStudyAdUser.setAdid(adid);
		cmpOrgStudyAdUser.setCompanyId(companyId);
		cmpOrgStudyAdUser.setOrgId(orgId);
		cmpOrgStudyAdUser.setName(req.getHtmlRow("name"));
		cmpOrgStudyAdUser.setTel(req.getHtmlRow("tel"));
		cmpOrgStudyAdUser.setMobile(req.getHtmlRow("mobile"));
		cmpOrgStudyAdUser.setCity(req.getHtmlRow("city"));
		cmpOrgStudyAdUser.setIm(req.getHtmlRow("im"));
		cmpOrgStudyAdUser.setMsg(req.getHtml("msg"));
		cmpOrgStudyAdUser.setSex(req.getByte("sex"));
		cmpOrgStudyAdUser.setEmail(req.getHtmlRow("email"));
		int code = cmpOrgStudyAdUser.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		this.cmpOrgStudyAdUserService
				.createCmpOrgStudyAdUser(cmpOrgStudyAdUser);
		req.setSessionText("epp.cmporgstudyaduser.create.success");
		// 验证通过后删除在session中的验证码
		req.removeSessionvalue(HkUtil.CLOUD_IMAGE_AUTH);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * 报名用户管理
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-11
	 */
	public String userlist(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("org_active_userlist", 1);
		this.loadOrgInfo(req);
		if (!this.isCanAdminOrg(req)) {
			return null;
		}
		long companyId = req.getLong("companyId");
		long orgId = req.getLong("orgId");
		PageSupport page = req.getPageSupport(20);
		List<CmpOrgStudyAdUser> list = this.cmpOrgStudyAdUserProcessor
				.getCmpOrgStudyAdUserListByCompanyIdAndOrgId(companyId, orgId,
						true, page.getBegin(), page.getSize());
		req.setAttribute("list", list);
		return this.getWebPath("mod/2/0/org/studyad/userlist.jsp");
	}
}