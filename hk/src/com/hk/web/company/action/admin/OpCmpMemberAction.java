package com.hk.web.company.action.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpMember;
import com.hk.bean.CmpMemberGrade;
import com.hk.bean.CmpMemberMoneyLog;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpMemberService;
import com.hk.svr.UserService;
import com.hk.svr.pub.Err;
import com.hk.svr.user.exception.EmailDuplicateException;
import com.hk.svr.user.exception.MobileDuplicateException;
import com.hk.web.pub.action.BaseAction;

@Component("/e/op/auth/member")
public class OpCmpMemberAction extends BaseAction {
	@Autowired
	private CmpMemberService cmpMemberService;

	@Autowired
	private UserService userService;

	/**
	 * 会员列表
	 */
	public String execute(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		String name = req.getString("name");
		String mobile = req.getStringAndSetAttr("mobile");
		String email = req.getStringAndSetAttr("email");
		long gradeId = req.getLongAndSetAttr("gradeId");
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.cmpMemberService.countCmpMember(companyId,
				name, mobile, email, gradeId));
		List<CmpMember> memberlist = this.cmpMemberService.getCmpMemberList(
				companyId, name, mobile, email, gradeId, page.getBegin(), page
						.getSize());
		req.setAttribute("memberlist", memberlist);
		List<CmpMemberGrade> gradelist = this.cmpMemberService
				.getCmpMemberGradeListByCompanyId(companyId);
		req.setAttribute("gradelist", gradelist);
		req.setAttribute("op_func", 12);
		req.setEncodeAttribute("name", name);
		return this.getWeb3Jsp("e/member/op/memberlist.jsp");
	}

	/**
	 * 创建会员级别
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String creategrade(HkRequest req, HkResponse resp) {
		String name = req.getString("name");
		long companyId = req.getLong("companyId");
		double rebate = req.getDouble("rebate");
		CmpMemberGrade grade = new CmpMemberGrade();
		grade.setName(DataUtil.toRowValue(name));
		grade.setCompanyId(companyId);
		grade.setRebate(rebate);
		int code = grade.validate();
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "grade");
		}
		this.cmpMemberService.createCmpMemberGrade(grade);
		this.setOpFuncSuccessMsg(req);
		return this.initSuccess(req, "grade");
	}

	/**
	 * 修改会员级别
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String updategrade(HkRequest req, HkResponse resp) {
		long gradeId = req.getLong("gradeId");
		String name = req.getString("name");
		long companyId = req.getLong("companyId");
		double rebate = req.getDouble("rebate");
		CmpMemberGrade grade = this.cmpMemberService.getCmpMemberGrade(gradeId);
		if (grade == null) {
			return null;
		}
		grade.setName(DataUtil.toRowValue(name));
		grade.setCompanyId(companyId);
		grade.setRebate(rebate);
		int code = grade.validate();
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "grade");
		}
		this.cmpMemberService.updateCmpMemberGrade(grade);
		this.setOpFuncSuccessMsg(req);
		return this.initSuccess(req, "grade");
	}

	/**
	 * 删除会员级别
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String deletegrade(HkRequest req, HkResponse resp) {
		long gradeId = req.getLong("gradeId");
		this.cmpMemberService.deleteCmpMemberGrade(gradeId);
		this.setOpFuncSuccessMsg(req);
		return this.initSuccess(req, "grade");
	}

	/**
	 * 会员级别列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String gradelist(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		List<CmpMemberGrade> gradelist = this.cmpMemberService
				.getCmpMemberGradeListByCompanyId(companyId);
		req.setAttribute("gradelist", gradelist);
		req.setAttribute("op_func", 13);
		return this.getWeb3Jsp("e/member/op/gradelist.jsp");
	}

	/**
	 * 创建会员
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String create(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		long gradeId = req.getLong("gradeId");
		String mobile = req.getString("mobile");
		String email = req.getString("email");
		double money = req.getDouble("money");
		String name = req.getString("name");
		CmpMember cmpMember = new CmpMember();
		cmpMember.setCompanyId(companyId);
		cmpMember.setGradeId(gradeId);
		cmpMember.setEmail(DataUtil.toHtmlRow(email));
		cmpMember.setMobile(DataUtil.toHtmlRow(mobile));
		cmpMember.setMoney(money);
		cmpMember.setName(DataUtil.toHtmlRow(name));
		int code = cmpMember.validate();
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "member");
		}
		long userId = 0;
		if (mobile != null) {
			UserOtherInfo info = this.userService
					.getUserOtherInfoByMobile(mobile);
			if (info != null) {
				userId = info.getUserId();
			}
		}
		if (userId == 0 && email != null) {
			UserOtherInfo info = this.userService
					.getUserOtherInfoByeEmail(email);
			if (info != null) {
				userId = info.getUserId();
			}
		}
		// 用户不存在，默认注册用户
		// 默认给用户注册，设置用户标识为企业会员注册未激活
		if (userId == 0) {
			String password = DataUtil.getRandom(6);
			try {
				userId = this.userService.createUser(email, mobile, password,
						null, null);
			}
			catch (EmailDuplicateException e) {
				return this.initError(req, Err.EMAIL_ALREADY_EXIST, "member");
			}
			catch (MobileDuplicateException e) {
				return this.initError(req, Err.MOBILE_ALREADY_EXIST, "member");
			}
		}
		// this.userService.updateUserStatus(userId,
		// UserOtherInfo.USERSTATUS_CMPMEMBER);
		cmpMember.setUserId(userId);
		this.cmpMemberService.createCmpMember(cmpMember);
		this.setOpFuncSuccessMsg(req);
		return this.initSuccess(req, "member");
	}

	/**
	 * 更新会员信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String update(HkRequest req, HkResponse resp) {
		long memberId = req.getLong("memberId");
		long companyId = req.getLongAndSetAttr("companyId");
		long gradeId = req.getLong("gradeId");
		String mobile = req.getString("mobile");
		String email = req.getString("email");
		CmpMember cmpMember = this.cmpMemberService.getCmpMember(memberId);
		cmpMember.setCompanyId(companyId);
		cmpMember.setGradeId(gradeId);
		cmpMember.setEmail(DataUtil.toHtmlRow(email));
		cmpMember.setMobile(DataUtil.toHtmlRow(mobile));
		int code = cmpMember.validate();
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "member");
		}
		this.cmpMemberService.updateCmpMember(cmpMember);
		this.setOpFuncSuccessMsg(req);
		return this.initSuccess(req, "member");
	}

	/**
	 * 更新会员信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String delete(HkRequest req, HkResponse resp) {
		long memberId = req.getLong("memberId");
		this.cmpMemberService.deleteCmpMember(memberId);
		this.setOpFuncSuccessMsg(req);
		return this.initSuccess(req, "member");
	}

	/**
	 * 更新会员信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String loadmember(HkRequest req, HkResponse resp) {
		long memberId = req.getLongAndSetAttr("memberId");
		int t = req.getInt("t");
		CmpMember m = this.cmpMemberService.getCmpMember(memberId);
		if (m == null) {
			return null;
		}
		req.setAttribute("m", m);
		req.reSetAttribute("companyId");
		if (t == 0) {
			List<CmpMemberGrade> gradelist = this.cmpMemberService
					.getCmpMemberGradeListByCompanyId(m.getCompanyId());
			req.setAttribute("gradelist", gradelist);
			return this.getWeb3Jsp("e/member/op/member_inc.jsp");
		}
		return this.getWeb3Jsp("e/member/op/member_money_inc.jsp");
	}

	/**
	 * 更新会员信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String loadgrade(HkRequest req, HkResponse resp) {
		long gradeId = req.getLongAndSetAttr("gradeId");
		CmpMemberGrade g = this.cmpMemberService.getCmpMemberGrade(gradeId);
		if (g == null) {
			return null;
		}
		req.setAttribute("g", g);
		req.reSetAttribute("companyId");
		return this.getWeb3Jsp("e/member/op/grade_inc.jsp");
	}

	/**
	 * 会员充值
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String addmoney(HkRequest req, HkResponse resp) {
		long memberId = req.getLong("memberId");
		double money = req.getDouble("money");
		if (req.getString("add") == null) {
			money = -money;
		}
		if (this.cmpMemberService.addMoney(memberId,
				CmpMemberMoneyLog.ADDFLG_CHARGE, money)) {
			this.setOpFuncSuccessMsg(req);
			return this.initSuccess(req, "money");
		}
		return this.initError(req, Err.MONEY_BALANCE_NOT_ENOUGH, "money");
	}
}