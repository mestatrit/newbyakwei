package com.hk.web.user.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpCheckInUser;
import com.hk.bean.CmpCheckInUserLog;
import com.hk.bean.Company;
import com.hk.bean.Follow;
import com.hk.bean.User;
import com.hk.bean.UserSmsPort;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.SmsClient;
import com.hk.svr.CmpCheckInService;
import com.hk.svr.CompanyService;
import com.hk.svr.FollowService;
import com.hk.svr.UserService;
import com.hk.svr.UserSmsPortService;
import com.hk.svr.pub.HkbConfig;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.HkWebUtil;

@Component("/op/user")
public class OpUserAction extends BaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private FollowService followService;

	@Autowired
	private UserSmsPortService userSmsPortService;

	@Autowired
	private SmsClient smsClient;

	@Autowired
	private CmpCheckInService cmpCheckInService;

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 如果没有被对方关注，就只能有短信按钮。 如果有余额不足则发送，没有余额，提示如下： “请直接通过您的手机短信发送信息到对方火酷号号。窍门：先拨打
	 * 106691602588100035 再挂掉，然后通过拨打记录发送短信。” 返回. 如果余额足，则直接发送。 提交后，显示：
	 * “由于对方并没有关注您，短信不能送达对方手机，只能以站内私信给对方，等待对方查看”
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String sms(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		User loginUser = this.getLoginUser(req);
		if (userId == loginUser.getUserId()) {
			if (!this.userService.hasEnoughHkb(userId, -HkbConfig.getSendSms())) {
				req.setSessionText("func.noenoughhkb_sendsms");
				return "r:/home.do?userId=" + userId;
			}
			return "r:/op/sms_tosendtome.do";
		}
		if (this.userSmsPortService.getUserSmsPortByUserId(loginUser
				.getUserId()) == null) {
			req.setSessionText("func.nousersmsport");
			return "r:/home.do?userId=" + userId;
		}
		Follow follow = this.followService.getFollow(userId, loginUser
				.getUserId());
		req.reSetAttribute("userId");
		if (follow == null) {
			User receiver = this.userService.getUser(userId);
			req.setAttribute("receiver", receiver);
			req.setAttribute("receiverId", userId);
			if (this.userService.hasEnoughHkb(loginUser.getUserId(), -HkbConfig
					.getSendSms())) {
				req.setAttribute("enoughhkb", true);
			}
			else {
				req.setSessionText("func.noenoughhkb_sendsms");
				req.setAttribute("enoughhkb", false);
			}
			UserSmsPort userSmsPort = this.userSmsPortService
					.getUserSmsPortByUserId(userId);
			if (userSmsPort != null) {
				String userport = this.smsClient.getSmsConfig().getSpNumber()
						+ HkWebUtil.getUserPort(userId);
				req.setAttribute("userport", userport);
			}
			return "/WEB-INF/page/user/sendsms.jsp";
		}
		return "r:/msg/send_tosend2.do?receiverId=" + userId;
	}

	/**
	 * 大家在哪里
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String friendcheckin(HkRequest req, HkResponse resp) {
		// 报到数据
		int pcityId = this.getPcityId(req);
		Date date = new Date();
		Date min = DataUtil.getDate(date);
		Date max = DataUtil.getEndDate(date);
		List<CmpCheckInUserLog> cmpCheckInUserLogList = this.cmpCheckInService
				.getEffectCmpCheckInUserLogListByPcityId(pcityId, min, max, 0,
						20);
		if (cmpCheckInUserLogList.size() == 0) {
			cmpCheckInUserLogList = this.cmpCheckInService
					.getEffectCmpCheckInUserLogListByPcityId(0, min, max, 0, 20);
		}
		List<Long> list = new ArrayList<Long>();
		for (CmpCheckInUserLog o : cmpCheckInUserLogList) {
			list.add(o.getCompanyId());
		}
		Map<Long, Company> map = this.companyService.getCompanyMapInId(list);
		list.clear();
		for (CmpCheckInUserLog o : cmpCheckInUserLogList) {
			list.add(o.getUserId());
		}
		Map<Long, User> usermap = this.userService.getUserMapInId(list);
		for (CmpCheckInUserLog o : cmpCheckInUserLogList) {
			o.setCompany(map.get(o.getCompanyId()));
			o.setUser(usermap.get(o.getUserId()));
		}
		req.setAttribute("list", cmpCheckInUserLogList);
		return this.getWapJsp("/user/friendcheckin.jsp");
	}

	/**
	 * 报到
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String findcmp(HkRequest req, HkResponse resp) {
		req.setReturnUrl("/op/user_findcmp2.do");
		int ch = req.getIntAndSetAttr("ch");
		User loginUser = this.getLoginUser(req);
		SimplePage page = req.getSimplePage(20);
		if (ch == 0) {
			List<CmpCheckInUser> cmpcheckinuserlist = this.cmpCheckInService
					.getCmpCheckInUserListByUserId(loginUser.getUserId(), page
							.getBegin(), page.getSize() + 1);
			this.processListForPage(page, cmpcheckinuserlist);
			List<Long> idList = new ArrayList<Long>();
			for (CmpCheckInUser o : cmpcheckinuserlist) {
				idList.add(o.getCompanyId());
			}
			Map<Long, Company> map = this.companyService
					.getCompanyMapInId(idList);
			for (CmpCheckInUser o : cmpcheckinuserlist) {
				o.setCompany(map.get(o.getCompanyId()));
			}
			req.setAttribute("cmpcheckinuserlist", cmpcheckinuserlist);
			return this.getWapJsp("user/findcmpforcheckin.jsp");
		}
		String name = req.getString("name");
		int other = req.getIntAndSetAttr("other");
		int cityId = req.getIntAndSetAttr("cityId");
		req.setEncodeAttribute("name", name);
		List<Company> companylist = null;
		if (other == 0) {// 在本城市查询
			if (DataUtil.isEmpty(name)) {
				companylist = this.companyService.getCompanyListByPcityId(
						cityId, page.getBegin(), page.getSize() + 1);
			}
			else {
				List<Long> idList = this.companyService
						.getCompanyIdListWithSearch(cityId, name, page
								.getBegin(), page.getSize() + 1);
				Map<Long, Company> map = this.companyService
						.getCompanyMapInId(idList);
				companylist = new ArrayList<Company>();
				for (Long l : idList) {
					Company o = map.get(l.longValue());
					if (o != null) {
						companylist.add(o);
					}
				}
			}
		}
		else {// 其他城市查询
			if (DataUtil.isEmpty(name)) {
				companylist = this.companyService.getCompanyListByNoPcityId(
						cityId, page.getBegin(), page.getSize() + 1);
			}
			else {
				List<Long> idList = this.companyService
						.getCompanyIdListWithSearchNotPcityId(cityId, name,
								page.getBegin(), page.getSize() + 1);
				Map<Long, Company> map = this.companyService
						.getCompanyMapInId(idList);
				companylist = new ArrayList<Company>();
				for (Long l : idList) {
					Company o = map.get(l.longValue());
					if (o != null) {
						companylist.add(o);
					}
				}
			}
		}
		this.processListForPage(page, companylist);
		req.setAttribute("companylist", companylist);
		return this.getWapJsp("user/findcmpforcheckin.jsp");
	}

	/**
	 * 查看当前城市的足迹(报到使用)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-10
	 */
	public String findcmp2(HkRequest req, HkResponse resp) {
		int cityId = this.getPcityId(req);
		return "r:/op/user_findcmp.do?ch=1&cityId=" + cityId;
	}
}