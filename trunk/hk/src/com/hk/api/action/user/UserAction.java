package com.hk.api.action.user;

import java.util.List;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.api.util.SessionKey;
import com.hk.bean.ApiUser;
import com.hk.bean.City;
import com.hk.bean.DefFollowUser;
import com.hk.bean.RegFrom;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.FollowService;
import com.hk.svr.UserService;
import com.hk.svr.friend.exception.AlreadyBlockException;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.svr.pub.ZoneUtil;
import com.hk.svr.user.exception.EmailDuplicateException;
import com.hk.svr.user.exception.MobileDuplicateException;
import com.hk.web.user.UserVo;
import com.hk.web.user.UserVoBuilder;

// @Component("/pubapi/user")
public class UserAction extends BaseApiAction {

	@Autowired
	private UserService userService;

	@Autowired
	private FollowService followService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		User user = null;
		if (userId == 0) {
			String nickName = req.getString("nickName");
			user = this.userService.getUserByNickName(nickName);
		}
		else {
			user = this.userService.getUser(userId);
		}
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

	public String create(HkRequest req, HkResponse resp) {
		String input = req.getString("input");
		String password = req.getString("password");
		String ip = req.getString("ip");
		ip = DataUtil.getLegalIp(ip);
		long companyId = req.getLong("companyId");
		int code = User.validateReg(input, password);
		if (code != Err.SUCCESS) {
			APIUtil.sendFailRespStatus(resp, code);
			return null;
		}
		long userId = 0;
		try {
			userId = this.userService.createUser(input, password, ip);
			// 确定注册来源
			if (!HkSvrUtil.isNotCompany(companyId)) {
				this.userService.createRegfromUser(userId, RegFrom.CMP,
						companyId);
			}
			else {
				ApiUser apiUser = this.getApiUser(req);
				this.userService.createRegfromUser(userId, RegFrom.API, apiUser
						.getApiUserId());
			}
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
						// TODO Auto-generated catch block
					}
				}
			}
			APIUtil.sendSuccessRespStatus(resp);
			return null;
		}
		catch (EmailDuplicateException e) {
			APIUtil.sendFailRespStatus(resp, Err.EMAIL_ALREADY_EXIST);
			return null;
		}
		catch (MobileDuplicateException e) {
			APIUtil.sendFailRespStatus(resp, Err.MOBILE_ALREADY_EXIST);
			return null;
		}
	}

	public String availablekey(HkRequest req, HkResponse resp) throws Exception {
		SessionKey o = this.getSessionKey(req);
		boolean available = false;
		if (o != null) {
			available = true;
		}
		VelocityContext context = new VelocityContext();
		context.put("available", available);
		this.write(resp, "vm/availablekey.vm", context);
		return null;
	}

	public String search(HkRequest req, HkResponse resp) throws Exception {
		String nickName = req.getString("nickName");
		nickName = DataUtil.getSearchValue(nickName);
		int size = this.getSize(req);
		SimplePage page = req.getSimplePage(size);
		VelocityContext context = new VelocityContext();
		if (!DataUtil.isEmpty(nickName)) {
			List<User> list = this.userService.getUserListForSearch(nickName,
					page.getBegin(), size);
			UserVoBuilder builder = new UserVoBuilder();
			builder.setNeedLaba(true);
			builder.setNeedLaba(true);
			builder.setLabaParserCfg(this.getApiLabaParserCfg());
			List<UserVo> volist = UserVo.create(list, builder);
			context.put("userlist", volist);
		}
		this.write(resp, "vm/user/userlist.vm", context);
		return null;
	}
}