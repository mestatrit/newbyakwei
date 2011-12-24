package com.hk.web.company.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CheckInResult;
import com.hk.bean.CmpCheckInUserLog;
import com.hk.bean.CmpTip;
import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.bean.UserCmpTip;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpCheckInService;
import com.hk.svr.CmpTipService;
import com.hk.svr.CompanyService;
import com.hk.svr.UserService;
import com.hk.svr.equipment.EquipmentMsg;
import com.hk.svr.processor.CmpCheckInProcessor;
import com.hk.svr.processor.CmpTipProcessor;
import com.hk.svr.pub.CheckInPointConfig;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.equipment.EquipmentMsgUtil;

@Component("/op/cmp")
public class UserOpCmpAction extends BaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CmpTipService cmpTipService;

	@Autowired
	private CmpCheckInService cmpCheckInService;

	@Autowired
	private CmpCheckInProcessor cmpCheckInProcessor;

	@Autowired
	private CmpTipProcessor cmpTipProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 用户报到
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String checkin(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		User loginUser = this.getLoginUser(req);
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		if (loginUser.getPcityId() != company.getPcityId()) {
			req.setSessionText(String.valueOf(Err.CHECKIN_PCITYID_ERROR),
					new Object[] { company.getPcity().getName() });
			return "r:/e/cmp.do?companyId=" + companyId;
		}
		CmpCheckInUserLog lastCmpCheckInUserLog = this.cmpCheckInService
				.getLastCmpCheckInUserLogByUserId(loginUser.getUserId());
		// 切换城市速度太快，有作弊嫌疑
		if (lastCmpCheckInUserLog != null) {
			Company last = this.companyService.getCompany(companyId);
			if (last != null) {
				if (last.getPcityId() != company.getPcityId()
						&& (System.currentTimeMillis()
								- lastCmpCheckInUserLog.getCreateTime()
										.getTime() <= 1200000)) {
					req.setSessionText(String
							.valueOf(Err.CHECKIN_SPEED_TOO_FAST));
					return "r:/e/cmp.do?companyId=" + companyId;
				}
			}
		}
		if (loginUser.getPcityId() != company.getPcityId()) {
			req.setSessionText(String.valueOf(Err.CHECKIN_PCITYID_ERROR),
					new Object[] { company.getPcity().getName() });
			return "r:/e/cmp.do?companyId=" + companyId;
		}
		int oldpoints = info.getPoints();
		CmpCheckInUserLog cmpCheckInUserLog = new CmpCheckInUserLog();
		cmpCheckInUserLog.setCompanyId(companyId);
		cmpCheckInUserLog.setUserId(loginUser.getUserId());
		cmpCheckInUserLog.setSex(loginUser.getSex());
		cmpCheckInUserLog.setKindId(company.getKindId());
		cmpCheckInUserLog.setParentId(company.getParentKindId());
		cmpCheckInUserLog.setPcityId(loginUser.getPcityId());
		CheckInResult checkInResult = this.cmpCheckInProcessor.checkIn(
				cmpCheckInUserLog, false, company, req.getRemoteAddr());
		info = this.userService.getUserOtherInfo(loginUser.getUserId());
		int newpoints = info.getPoints();
		int respoints = newpoints - oldpoints;
		List<String> msglist = new ArrayList<String>();
		if (respoints > 0) {
			req.setSessionText("func2.checkinokandpoints", respoints);
			if (newpoints >= CheckInPointConfig.getOpenBoxPoints()) {
				int box_open_count = newpoints
						/ CheckInPointConfig.getOpenBoxPoints();
				msglist.add(req.getText("func2.checkinokandpoints2", respoints,
						newpoints, box_open_count));
			}
			else {
				msglist.add(req.getText("func2.checkinokandpoints", respoints));
			}
		}
		else {
			msglist.add(req.getText("func2.checkinok"));
		}
		EquipmentMsg equipmentMsg = checkInResult.getEquipmentMsg();
		if (equipmentMsg != null) {
			String msg = EquipmentMsgUtil.getEquipmentMessage(equipmentMsg,
					req, true);
			if (msg != null) {
				msglist.add(msg);
			}
		}
		StringBuilder sb = new StringBuilder();
		for (String s : msglist) {
			sb.append("<div>").append(s).append("</div>");
		}
		req.setSessionMessage(sb.toString());
		return "r:/e/cmp.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createusercmptip(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		long tipId = req.getLong("tipId");
		CmpTip cmpTip = this.cmpTipService.getCmpTip(tipId);
		if (cmpTip == null) {
			return null;
		}
		byte doneflg = CmpTip.DONEFLG_DONE;
		if (req.getString("todo") != null) {
			doneflg = CmpTip.DONEFLG_TODO;
		}
		long companyId = cmpTip.getCompanyId();
		Company company = this.companyService.getCompany(companyId);
		UserCmpTip userCmpTip = new UserCmpTip();
		userCmpTip.setUserId(loginUser.getUserId());
		userCmpTip.setTipId(tipId);
		userCmpTip.setCreateTime(new Date());
		userCmpTip.setPcityId(company.getPcityId());
		userCmpTip.setDoneflg(doneflg);
		userCmpTip.setData(getData(company));
		userCmpTip.setCompanyId(companyId);
		this.cmpTipProcessor.createUserCmpTip(userCmpTip);
		return "r:/e/cmp_item.do?tipId=" + tipId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String deleteusercmptip(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		long tipId = req.getLong("tipId");
		this.cmpTipService.deleteUserCmpTipByUserIdAndTipId(loginUser
				.getUserId(), tipId);
		return "r:/e/cmp_item.do?tipId=" + tipId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String deletecmptip(HkRequest req, HkResponse resp) {
		long tipId = req.getLong("tipId");
		CmpTip cmpTip = this.cmpTipService.getCmpTip(tipId);
		if (cmpTip != null) {
			User loginUser = this.getLoginUser(req);
			if (cmpTip.getUserId() == loginUser.getUserId()) {
				this.cmpTipProcessor.deleteCmpTip(tipId);
			}
		}
		return "r:/home.do?userId=" + this.getLoginUser(req).getUserId();
	}
}