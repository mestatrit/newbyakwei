package com.hk.api.action.user;

import java.io.File;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.api.util.SessionKey;
import com.hk.bean.City;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.UserService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ZoneUtil;
import com.hk.svr.user.validate.UserValidate;

//@Component("/pubapi/protect/user")
public class ProtectUserAction extends BaseApiAction {
	@Autowired
	private UserService userService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String mail(HkRequest req, HkResponse resp) {
		SessionKey o = this.getSessionKey(req);
		long userId = o.getUserId();
		UserOtherInfo info = this.userService.getUserOtherInfo(userId);
		VelocityContext context = new VelocityContext();
		context.put("mail", info.getEmail());
		this.write(resp, "vm/user/mail.vm", context);
		return null;
	}

	public String isauthmail(HkRequest req, HkResponse resp) {
		SessionKey o = this.getSessionKey(req);
		long userId = o.getUserId();
		UserOtherInfo info = this.userService.getUserOtherInfo(userId);
		VelocityContext context = new VelocityContext();
		context.put("authmail", info.isAuthedMail());
		this.write(resp, "vm/user/authmail.vm", context);
		return null;
	}

	public String uploadhead(HkRequest req, HkResponse resp) {
		SessionKey o = this.getSessionKey(req);
		File headFile = req.getFile("f");
		if (headFile == null) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		long userId = o.getUserId();
		try {
			this.userService.updateHead(userId, headFile);
			APIUtil.sendSuccessRespStatus(resp);
			return null;
		}
		catch (ImageException e) {
			APIUtil.sendFailRespStatus(resp, Err.IMG_UPLOAD_ERROR);
			return null;
		}
		catch (NotPermitImageFormatException e) {
			APIUtil.sendFailRespStatus(resp, Err.IMG_FMT_ERROR);
			return null;
		}
		catch (OutOfSizeException e) {
			APIUtil.sendFailRespStatus(resp, Err.IMG_UPLOAD_ERROR);
			return null;
		}
	}

	public String updateuserinfo(HkRequest req, HkResponse resp) {
		SessionKey sessionKey = this.getSessionKey(req);
		long userId = sessionKey.getUserId();
		UserOtherInfo info = this.userService.getUserOtherInfo(userId);
		String nickName = req.getString("nickName");
		String name = req.getString("name");
		byte sex = req.getByte("sex");
		userService.updateSex(userId, sex);
		String intro = req.getString("intro");
		int birthdayDate = req.getInt("birthdayDate");
		int birthdayMonth = req.getInt("birthdayMonth");
		info.setBirthdayDate(birthdayDate);
		info.setBirthdayMonth(birthdayMonth);
		info.setName(DataUtil.toHtmlRow(name));
		info.setIntro(DataUtil.toHtmlRow(intro));
		int code1 = User.validateNickName(nickName);
		if (code1 != Err.SUCCESS) {
			APIUtil.sendFailRespStatus(resp, code1);
			return null;
		}
		int code = info.validate();
		if (code != Err.SUCCESS) {
			APIUtil.sendFailRespStatus(resp, code);
			return null;
		}
		if (this.userService.updateNickName(userId, DataUtil
				.toHtmlRow(nickName))) {
			this.userService.updateUserOtherInfo(info);
			APIUtil.sendSuccessRespStatus(resp);
			return null;
		}
		APIUtil.sendFailRespStatus(resp, Err.NICKNAME_DUPLICATE);
		return null;
	}

	public String userinfo(HkRequest req, HkResponse resp) throws Exception {
		SessionKey sessionKey = this.getSessionKey(req);
		long userId = sessionKey.getUserId();
		User user = this.userService.getUser(userId);
		UserOtherInfo info = this.userService.getUserOtherInfo(userId);
		VelocityContext context = new VelocityContext();
		context.put("user", user);
		context.put("info", info);
		if (user.getCityId() > 0) {
			City city = ZoneUtil.getCity(user.getCityId());
			context.put("city", city);
		}
		this.write(resp, "vm/user/userinfo.vm", context);
		return null;
	}

	public String updatepwd(HkRequest req, HkResponse resp) throws Exception {
		SessionKey sessionKey = this.getSessionKey(req);
		long userId = sessionKey.getUserId();
		String newpwd = req.getString("newpwd");
		String oldpwd = req.getString("oldpwd");
		int code = UserValidate.validatePassword(newpwd);
		if (code != Err.SUCCESS) {
			APIUtil.sendFailRespStatus(resp, code);
			return null;
		}
		if (this.userService.updatePwd(userId, oldpwd, newpwd)) {
			APIUtil.sendSuccessRespStatus(resp);
			return null;
		}
		APIUtil.sendFailRespStatus(resp, Err.USER_OLDPASSWORD_ERROR);
		return null;
	}
}