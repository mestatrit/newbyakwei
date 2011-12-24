package web.pub.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.util.WebUtil;

import com.hk.bean.DefFollowUser;
import com.hk.frame.util.HkUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.FollowService;
import com.hk.svr.UserService;
import com.hk.svr.friend.exception.AlreadyBlockException;
import com.hk.svr.pub.Err;
import com.hk.svr.user.exception.EmailDuplicateException;
import com.hk.svr.user.exception.MobileDuplicateException;

/**
 * wap版本注册
 * 
 * @author akwei
 */
@Component("/epp/reg")
public class RegAction extends EppBaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private FollowService followService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return this.getWapPath(req, "reg.jsp");
	}

	/**
	 * 到注册页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-7
	 */
	public String exe20(HkRequest req, HkResponse resp) throws Exception {
		return this.getWapPath(req, "reg.jsp");
	}

	public String reg(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		if (this.getLoginUser2(req) != null) {
			return "r:/epp/index.do?companyId=" + companyId;
		}
		String code = req.getString("code");
		String input = req.getString("input", "");
		input = input.replaceAll("　", "");
		String password = req.getString("password");
		String password1 = req.getString("password1");
		req.setAttribute("input", input);
		if (input == null || password == null) {
			return "/epp/reg.do";
		}
		if (!password.equals(password1)) {
			req.setMessage(req.getText("func.reg.pwdnotequal"));
			return "/epp/reg.do";
		}
		int vacode = com.hk.bean.User.validateReg(input, password);
		if (vacode != Err.SUCCESS) {
			req.setText(String.valueOf(vacode));
			return "/epp/reg.do";
		}
		String session_code = (String) req
				.getSessionValue(HkUtil.CLOUD_IMAGE_AUTH);
		if (session_code == null || !session_code.equals(code)) {
			req.setText(String.valueOf(Err.REG_INPUT_VALIDATECODE));
			return "/epp/reg.do";
		}
		long userId = 0;
		try {
			userId = this.userService.createUserWithRegCode(input, password, req
					.getRemoteAddr(), null);
			// 添加默认关注
			List<DefFollowUser> list = this.userService.getDefFollowUserList(0,
					20);
			for (DefFollowUser o : list) {
				if (this.userService.getUser(o.getUserId()) != null) {
					try {
						this.followService.addFollow(userId, o.getUserId(), req
								.getRemoteAddr(), false);
					}
					catch (AlreadyBlockException e) {
					}
				}
			}
			com.hk.bean.User user = this.userService.getUser(userId);
			WebUtil.setLoginUser2(req, resp, user, input, req
					.getBoolean("autologin"));
			req.removeSessionvalue(HkUtil.CLOUD_IMAGE_AUTH);
			return "r:/epp/index.do?companyId=" + companyId;
		}
		catch (EmailDuplicateException e) {
			req.setMessage(req.getText("func.mailalreadyexist"));
			return "/epp/reg.do";
		}
		catch (MobileDuplicateException e) {
			req.setMessage(req.getText("func.mobilealreadyexist"));
			return "/epp/reg.do";
		}
	}
}