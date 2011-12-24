package web.pub.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.util.WebUtil;

import com.hk.bean.CmpAdminUser;
import com.hk.bean.CmpInfo;
import com.hk.bean.CmpRefUser;
import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpAdminUserService;
import com.hk.svr.CmpRefUserService;
import com.hk.svr.UserService;
import com.hk.svr.processor.UserLoginResult;
import com.hk.svr.processor.UserProcessor;
import com.hk.svr.pub.Err;

@Component("/epp/login")
public class LoginAction extends EppBaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private UserProcessor userProcessor;

	@Autowired
	private CmpAdminUserService cmpAdminUserService;

	@Autowired
	private CmpRefUserService cmpRefUserService;

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// User loginUser = this.getLoginUser2(req);
		// if (loginUser != null) {
		// return "r:/epp/index.do?companyId=" + req.getLong("companyId");
		// }
		// return this.getWapPath(req, "login.jsp");
		User loginUser = this.getLoginUser2(req);
		if (loginUser != null) {
			return "r:/m";
		}
		return this.getWapPath("login.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String web2(HkRequest req, HkResponse resp) {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		if (cmpInfo.getTmlflg() == 0) {
			return this.web20(req, resp);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String web3(HkRequest req, HkResponse resp) {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		if (cmpInfo.getTmlflg() == 0) {
			return this.web30(req, resp);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private String web20(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser2(req);
		if (loginUser != null) {
			return "r:http://" + req.getServerName();
		}
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWebPath("mod/2/0/user/login.jsp");
		}
		String input = req.getString("input");
		String password = req.getString("password");
		if (DataUtil.isEmpty(input) || DataUtil.isEmpty(password)) {
			return this.onError(req, Err.LOGIN_USER_OR_PASSWORD_ERROR,
					"loginerror", null);
		}
		UserLoginResult userLoginResult = this.userProcessor.login(input,
				password);
		if (userLoginResult.getError() != Err.SUCCESS) {
			return this.onError(req, userLoginResult.getError(), "loginerror",
					null);
		}
		long userId = userLoginResult.getUserId();
		CmpRefUser cmpRefUser = new CmpRefUser();
		cmpRefUser.setCompanyId(req.getLong("companyId"));
		cmpRefUser.setUserId(userId);
		cmpRefUser.setJoinflg(CmpRefUser.JOINFLG_LOGIN);
		cmpRefUserService.createCmpRefUser(cmpRefUser);
		int foradmin = req.getInt("foradmin");
		if (foradmin == 1) {
			long companyId = req.getLong("companyId");
			CmpAdminUser cmpAdminUser = this.cmpAdminUserService
					.getCmpAdminUserByCompanyIdAndUserId(companyId, userId);
			if (cmpAdminUser == null) {// 不是网站管理员,就查看是否是网站超级管理员
				Company company = (Company) req.getAttribute("o");
				if (company == null) {
					return null;
				}
				if (company.getUserId() != userId) {// 如果不是超级管理员，就提示
					return this.onError(req, Err.USER_NOT_CMPADMINUSER,
							"loginerror", null);
				}
			}
		}
		User user = this.userService.getUser(userId);
		WebUtil.setLoginUser2(req, resp, user, input, req
				.getBoolean("autologin"));
		return this.onSuccess2(req, "loginok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private String web30(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser2(req);
		if (loginUser != null) {
			return "r:http://" + req.getServerName();
		}
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWebPath("mod/3/0/user/login.jsp");
		}
		String input = req.getString("input");
		String password = req.getString("password");
		if (DataUtil.isEmpty(input) || DataUtil.isEmpty(password)) {
			return this.onError(req, Err.LOGIN_USER_OR_PASSWORD_ERROR,
					"loginerror", null);
		}
		UserLoginResult userLoginResult = this.userProcessor.login(input,
				password);
		if (userLoginResult.getError() != Err.SUCCESS) {
			return this.onError(req, userLoginResult.getError(), "loginerror",
					null);
		}
		long userId = userLoginResult.getUserId();
		CmpRefUser cmpRefUser = new CmpRefUser();
		cmpRefUser.setCompanyId(req.getLong("companyId"));
		cmpRefUser.setUserId(userId);
		cmpRefUser.setJoinflg(CmpRefUser.JOINFLG_LOGIN);
		cmpRefUserService.createCmpRefUser(cmpRefUser);
		int foradmin = req.getInt("foradmin");
		if (foradmin == 1) {
			long companyId = req.getLong("companyId");
			CmpAdminUser cmpAdminUser = this.cmpAdminUserService
					.getCmpAdminUserByCompanyIdAndUserId(companyId, userId);
			if (cmpAdminUser == null) {// 不是网站管理员,就查看是否是网站超级管理员
				Company company = (Company) req.getAttribute("o");
				if (company == null) {
					return null;
				}
				if (company.getUserId() != userId) {// 如果不是超级管理员，就提示
					return this.onError(req, Err.USER_NOT_CMPADMINUSER,
							"loginerror", null);
				}
			}
		}
		User user = this.userService.getUser(userId);
		WebUtil.setLoginUser2(req, resp, user, input, req
				.getBoolean("autologin"));
		return this.onSuccess2(req, "loginok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String web0(HkRequest req, HkResponse resp) throws Exception {
		int tmlflg = this.getTmlflg(req);
		if (tmlflg == 0) {
			return this.web00(req, resp);
		}
		if (tmlflg == 1) {
			return this.web01(req, resp);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String web00(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser2(req);
		if (loginUser != null) {
			return "r:http://" + req.getServerName();
		}
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWebPath("user/login.jsp");
		}
		String input = req.getString("input");
		String password = req.getString("password");
		if (DataUtil.isEmpty(input) || DataUtil.isEmpty(password)) {
			return this.onError(req, Err.LOGIN_USER_OR_PASSWORD_ERROR,
					"loginerror", null);
		}
		UserLoginResult userLoginResult = this.userProcessor.login(input,
				password);
		if (userLoginResult.getError() != Err.SUCCESS) {
			return this.onError(req, userLoginResult.getError(), "loginerror",
					null);
		}
		long userId = userLoginResult.getUserId();
		CmpRefUser cmpRefUser = new CmpRefUser();
		cmpRefUser.setCompanyId(req.getLong("companyId"));
		cmpRefUser.setUserId(userId);
		cmpRefUser.setJoinflg(CmpRefUser.JOINFLG_LOGIN);
		cmpRefUserService.createCmpRefUser(cmpRefUser);
		int foradmin = req.getInt("foradmin");
		if (foradmin == 1) {
			long companyId = req.getLong("companyId");
			CmpAdminUser cmpAdminUser = this.cmpAdminUserService
					.getCmpAdminUserByCompanyIdAndUserId(companyId, userId);
			if (cmpAdminUser == null) {// 不是网站管理员,就查看是否是网站超级管理员
				Company company = (Company) req.getAttribute("o");
				if (company == null) {
					return null;
				}
				if (company.getUserId() != userId) {// 如果不是超级管理员，就提示
					return this.onError(req, Err.USER_NOT_CMPADMINUSER,
							"loginerror", null);
				}
			}
		}
		User user = this.userService.getUser(userId);
		WebUtil.setLoginUser2(req, resp, user, input, req
				.getBoolean("autologin"));
		return this.onSuccess2(req, "loginok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String web01(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser2(req);
		if (loginUser != null) {
			return "r:http://" + req.getServerName();
		}
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWebPath("mod/0/1/user/login.jsp");
		}
		String input = req.getString("input");
		String password = req.getString("password");
		if (DataUtil.isEmpty(input) || DataUtil.isEmpty(password)) {
			return this.onError(req, Err.LOGIN_USER_OR_PASSWORD_ERROR,
					"loginerror", null);
		}
		UserLoginResult userLoginResult = this.userProcessor.login(input,
				password);
		if (userLoginResult.getError() != Err.SUCCESS) {
			return this.onError(req, userLoginResult.getError(), "loginerror",
					null);
		}
		long userId = userLoginResult.getUserId();
		CmpRefUser cmpRefUser = new CmpRefUser();
		cmpRefUser.setCompanyId(req.getLong("companyId"));
		cmpRefUser.setUserId(userId);
		cmpRefUser.setJoinflg(CmpRefUser.JOINFLG_LOGIN);
		cmpRefUserService.createCmpRefUser(cmpRefUser);
		int foradmin = req.getInt("foradmin");
		if (foradmin == 1) {
			long companyId = req.getLong("companyId");
			CmpAdminUser cmpAdminUser = this.cmpAdminUserService
					.getCmpAdminUserByCompanyIdAndUserId(companyId, userId);
			if (cmpAdminUser == null) {// 不是网站管理员,就查看是否是网站超级管理员
				Company company = (Company) req.getAttribute("o");
				if (company == null) {
					return null;
				}
				if (company.getUserId() != userId) {// 如果不是超级管理员，就提示
					return this.onError(req, Err.USER_NOT_CMPADMINUSER,
							"loginerror", null);
				}
			}
		}
		User user = this.userService.getUser(userId);
		WebUtil.setLoginUser2(req, resp, user, input, req
				.getBoolean("autologin"));
		return this.onSuccess2(req, "loginok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String web(HkRequest req, HkResponse resp) throws Exception {
		int cmpflg = this.getCmpflg(req);
		if (cmpflg == 0) {
			return this.web0(req, resp);
		}
		if (cmpflg == 2) {
			return this.web2(req, resp);
		}
		if (cmpflg == 3) {
			return this.web3(req, resp);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String validate(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser2(req);
		if (loginUser != null) {
			return "r:/epp/index.do?companyId=" + req.getLong("companyId");
		}
		if (req.getString("reg") != null) {
			return "r:/epp/reg.do?companyId=" + req.getLong("companyId");
		}
		String input = req.getString("input");
		String password = req.getString("password");
		req.setAttribute("input", input);
		if (DataUtil.isEmpty(input) || DataUtil.isEmpty(password)) {
			return "/epp/login.do";
		}
		input = input.replaceAll("　", "").replaceAll("＠", "@");
		UserLoginResult userLoginResult = this.userProcessor.login(input,
				password);
		if (userLoginResult.getError() != Err.SUCCESS) {
			req.setText(String.valueOf(userLoginResult.getError()));
			return "/epp/login.do";
		}
		long userId = userLoginResult.getUserId();
		CmpRefUser cmpRefUser = new CmpRefUser();
		cmpRefUser.setCompanyId(req.getLong("companyId"));
		cmpRefUser.setUserId(userId);
		cmpRefUser.setJoinflg(CmpRefUser.JOINFLG_LOGIN);
		cmpRefUserService.createCmpRefUser(cmpRefUser);
		User user = userService.getUser(userId);
		WebUtil.setLoginUser2(req, resp, user, input, req
				.getBoolean("autologin"));
		String return_url = req.getReturnUrl();
		if (DataUtil.isNotEmpty(return_url)) {
			return "r:" + return_url;
		}
		return "r:/m";
	}
}