package com.hk.web.user.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.bean.UserBoxPrize;
import com.hk.bean.UserEquipment;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.EquipmentService;
import com.hk.svr.UserService;
import com.hk.svr.processor.BoxProcessor;
import com.hk.svr.processor.EquipmentProcessor;
import com.hk.web.pub.action.BaseAction;

/**
 * 用户宝库(开箱物品，优惠券，道具)
 * 
 * @author akwei
 */
@Component("/op/award")
public class AwardAction extends BaseAction {

	@Autowired
	private BoxProcessor boxProcessor;

	@Autowired
	private UserService userService;

	@Autowired
	private EquipmentProcessor equipmentProcessor;

	@Autowired
	private EquipmentService equipmentService;

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return null;
	}

	/**
	 * 开箱物品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-4-27
	 */
	public String prize(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		SimplePage page = req.getSimplePage(20);
		List<UserBoxPrize> list = this.boxProcessor
				.getUserBoxPrizeListByUserId(loginUser.getUserId(), true, page
						.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWapJsp("user/prize.jsp");
	}

	/**
	 * 优惠券
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-4-27
	 */
	public String coupon(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 当前获得的道具
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-4-27
	 */
	public String equ(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		SimplePage page = req.getSimplePage(20);
		List<UserEquipment> list = this.equipmentProcessor
				.getUserEquipmentListByUserId(loginUser.getUserId(),
						UserEquipment.USEFLG_N, true, false, false, page
								.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWapJsp("/user/equ.jsp");
	}

	/**
	 * 对用户选择道具，当报到时触发
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-14
	 */
	public String selequ(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		long userId = req.getLongAndSetAttr("userId");
		User user = this.userService.getUser(userId);
		if (ch == 0) {
			req.setAttribute("user", user);
			SimplePage page = req.getSimplePage(20);
			List<UserEquipment> list = this.equipmentProcessor
					.getUserEquipmentToUser(userId, page.getBegin(), page
							.getSize() + 1);
			this.processListForPage(page, list);
			req.setAttribute("list", list);
			return this.getWapJsp("user/selequ.jsp");
		}
		long oid = req.getLong("oid");
		UserEquipment userEquipment = this.equipmentService
				.getUserEquipment(oid);
		if (userEquipment != null) {
			this.equipmentService.useEquipmentToUser(userId, userEquipment);
			req.setSessionText("view2.userequipment.usetouser.success", user
					.getNickName());
		}
		return "r:/home.do?userId=" + userId;
	}

	/**
	 * 对足迹选择道具，当报到时触发
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-14
	 */
	public String selcmpequ(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		long userId = this.getLoginUser(req).getUserId();
		long companyId = req.getLongAndSetAttr("companyId");
		if (ch == 0) {
			Company company = this.companyService.getCompany(companyId);
			req.setAttribute("company", company);
			SimplePage page = req.getSimplePage(20);
			List<UserEquipment> list = this.equipmentProcessor
					.getUserEquipmentToCmp(userId, page.getBegin(), page
							.getSize() + 1);
			this.processListForPage(page, list);
			req.setAttribute("list", list);
			return this.getWapJsp("user/selcmpequ.jsp");
		}
		long oid = req.getLong("oid");
		UserEquipment userEquipment = this.equipmentService
				.getUserEquipment(oid);
		if (userEquipment != null) {
			this.equipmentService.useEquipmentToCmp(companyId, userEquipment);
			req.setSessionText("view2.userequipment.usetocmp.success");
		}
		return "r:/e/cmp.do?companyId=" + companyId;
	}

	/**
	 * 已使用的道具
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-4-27
	 */
	public String equused(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		SimplePage page = req.getSimplePage(20);
		List<UserEquipment> list = this.equipmentProcessor
				.getUserEquipmentListByUserId(loginUser.getUserId(),
						UserEquipment.USEFLG_Y, true, true, true, page
								.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWapJsp("/user/equused.jsp");
	}
}