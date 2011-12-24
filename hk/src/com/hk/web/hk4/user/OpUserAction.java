package com.hk.web.hk4.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Box;
import com.hk.bean.BoxPretype;
import com.hk.bean.BoxPrize;
import com.hk.bean.CmpCheckInUser;
import com.hk.bean.CmpCheckInUserLog;
import com.hk.bean.Equipment;
import com.hk.bean.User;
import com.hk.bean.UserBoxPrize;
import com.hk.bean.UserCoupon;
import com.hk.bean.UserEquipment;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BoxOpenResult;
import com.hk.svr.BoxService;
import com.hk.svr.EquipmentService;
import com.hk.svr.FollowService;
import com.hk.svr.UserService;
import com.hk.svr.equipment.HandleEquipmentProcessor;
import com.hk.svr.friend.exception.AlreadyBlockException;
import com.hk.svr.friend.validate.FollowValidate;
import com.hk.svr.processor.BoxProccessorOpenResult;
import com.hk.svr.processor.BoxProcessor;
import com.hk.svr.processor.CmpCheckInProcessor;
import com.hk.svr.processor.CouponProcessor;
import com.hk.svr.processor.EquipmentProcessor;
import com.hk.svr.pub.BoxPretypeUtil;
import com.hk.svr.pub.EquipmentConfig;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ZoneUtil;
import com.hk.svr.user.exception.UserNotExistException;
import com.hk.web.pub.action.BaseAction;

@Component("/h4/op/user")
public class OpUserAction extends BaseAction {

	@Autowired
	private FollowService followService;

	@Autowired
	private EquipmentService equipmentService;

	@Autowired
	private UserService userService;

	@Autowired
	private BoxService boxService;

	@Autowired
	private BoxProcessor boxProcessor;

	@Autowired
	private CmpCheckInProcessor cmpCheckInProcessor;

	@Autowired
	private HandleEquipmentProcessor handleEquipmentProcessor;

	@Autowired
	private CouponProcessor couponProcessor;

	@Autowired
	private EquipmentProcessor equipmentProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String createfriend(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		User loginUser = this.getLoginUser(req);
		try {
			FollowValidate.validateAddFollow(loginUser.getUserId(), userId);
			this.followService.addFollow(loginUser.getUserId(), userId, req
					.getRemoteAddr(), true);
		}
		catch (AlreadyBlockException e) {
			resp.sendHtml(Err.FOLLOW_USER_BLOCK);
			return null;
		}
		catch (UserNotExistException e) {//
		}
		resp.sendHtml(Err.SUCCESS);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String deletefriend(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		User loginUser = this.getLoginUser(req);
		this.followService.removeFollow(loginUser.getUserId(), userId);
		return null;
	}

	/**
	 * 屏蔽user
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String block(HkRequest request, HkResponse response) {
		long userId = request.getLong("userId");
		User loginUser = this.getLoginUser(request);
		try {
			this.followService.blockUser(loginUser.getUserId(), userId);
		}
		catch (AlreadyBlockException e) {
		}
		return null;
	}

	/**
	 * 取消屏蔽
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String unblock(HkRequest request, HkResponse response) {
		long userId = request.getLong("userId");
		User loginUser = this.getLoginUser(request);
		this.followService.removeBlockUser(loginUser.getUserId(), userId);
		return null;
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
					.getUserEquipmentToUser(this.getLoginUser(req).getUserId(),
							page.getBegin(), page.getSize() + 1);
			this.processListForPage(page, list);
			req.setAttribute("list", list);
			return this.getWeb4Jsp("user/selequ.jsp");
		}
		long oid = req.getLong("oid");
		UserEquipment userEquipment = this.equipmentService
				.getUserEquipment(oid);
		if (userEquipment != null) {
			this.equipmentService.useEquipmentToUser(userId, userEquipment);
			req.setSessionText("view2.userequipment.usetouser.success", user
					.getNickName(), EquipmentConfig.getEquipment(
					userEquipment.getEid()).getName());
		}
		return "r:/user/" + userId;
	}

	/**
	 * 开箱子
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-15
	 */
	public String openbox(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		Box box = this.boxService.getBox(boxId);
		User loginUser = this.getLoginUser(req);
		BoxProccessorOpenResult boxProccessorOpenResult = this.boxProcessor
				.openBox2(loginUser.getUserId(), loginUser.getPcityId(), box,
						req.getRemoteAddr());
		int result = boxProccessorOpenResult.getErrorCode();
		if (result != Err.SUCCESS) {
			req.setSessionText(String.valueOf(result), ZoneUtil.getCityName(box
					.getCityId()));
			return "r:/box/" + boxId;
		}
		// 没有足够的点数开箱
		if (boxProccessorOpenResult.isNoEnoughPoints()) {
			req.setSessionText("view2.notenoughpointsforopenbox");
			return "r:/user/tocheckin";
		}
		BoxOpenResult boxOpenResult = boxProccessorOpenResult
				.getBoxOpenResult();
		result = boxOpenResult.getErrorCode();
		if (result != Err.SUCCESS) {
			if (result == Err.BOX_OUT_OF_LIMIT) {
				BoxPretype boxPretype = BoxPretypeUtil.getBoxPretype(box
						.getPretype());
				req.setSessionText(String.valueOf(result),
						boxPretype.getName(), box.getPrecount());
			}
			else {
				req.setSessionText(String.valueOf(result));
			}
			return "r:/box/" + boxId;
		}
		// 中了道具，没有中奖
		if (boxOpenResult.getUserEquipment() != null
				&& boxOpenResult.getUserBoxPrize() == null) {
			return "r:/h4/op/user_boxequ.do?boxId=" + boxId + "&eid="
					+ boxOpenResult.getUserEquipment().getEid();
		}
		// 每中道具，也没有中奖
		if (boxOpenResult.getUserBoxPrize() == null) {
			req.setSessionText("view2.box.openbox.noprize");
			return "r:/box/" + boxId;
		}
		// 中奖
		BoxPrize prize = this.boxService.getBoxPrize(boxOpenResult
				.getUserBoxPrize().getPrizeId());
		long prizeId = prize.getPrizeId();
		long eid = 0;
		UserEquipment userEquipment = boxOpenResult.getUserEquipment();
		if (userEquipment != null) {
			Equipment equipment = EquipmentConfig.getEquipment(userEquipment
					.getEid());
			eid = equipment.getEid();
		}
		return "r:/h4/op/user_boxresult.do?boxId=" + boxId + "&prizeId="
				+ prizeId + "&eid=" + eid + "&sysId="
				+ boxOpenResult.getUserBoxPrize().getSysId();
	}

	/**
	 * 宝箱结果页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-15
	 */
	public String boxresult(HkRequest req, HkResponse resp) {
		long boxId = req.getLongAndSetAttr("boxId");
		long sysId = req.getLong("sysId");
		long prizeId = req.getLong("prizeId");
		long eid = req.getLong("eid");
		User loginUser = this.getLoginUser(req);
		Box box = this.boxService.getBox(boxId);
		req.setAttribute("box", box);
		BoxPrize prize = this.boxService.getBoxPrize(prizeId);
		req.setAttribute("prize", prize);
		UserBoxPrize userBoxPrize = this.boxService.getUserBoxPrize(sysId);
		if (userBoxPrize != null
				&& userBoxPrize.getUserId() == loginUser.getUserId()) {
			if (prize.getEid() > 0 && userBoxPrize.getPrizeId() == prizeId) {
				this.boxService.deleteUserBoxPrize(sysId);
				if (this.equipmentService.getNotUseUserEquipmentByUserIdAndEid(
						loginUser.getUserId(), prize.getEid()) != null) {
					// 已经有了没有使用的此道具，提示用户
					req.setText("view2.boxequ.already.exist");
				}
				else {
					// 把道具存入道具库
					UserEquipment userEquipment = new UserEquipment();
					userEquipment.setEid(prize.getEid());
					userEquipment.setUserId(loginUser.getUserId());
					userEquipment.setUseflg(UserEquipment.USEFLG_N);
					userEquipment.setTouchflg(UserEquipment.TOUCHFLG_N);
					this.equipmentService.createUserEquipment(userEquipment);
				}
			}
			req.setAttribute("userBoxPrize", userBoxPrize);
		}
		Equipment equipment = EquipmentConfig.getEquipment(eid);
		req.setAttribute("equipment", equipment);
		return this.getWeb4Jsp("box/result.jsp");
	}

	/**
	 * 宝箱获得道具结果页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-15
	 */
	public String boxequ(HkRequest req, HkResponse resp) {
		long boxId = req.getLongAndSetAttr("boxId");
		long eid = req.getLong("eid");
		Box box = this.boxService.getBox(boxId);
		req.setAttribute("box", box);
		Equipment equipment = EquipmentConfig.getEquipment(eid);
		req.setAttribute("equipment", equipment);
		return this.getWeb4Jsp("box/equ.jsp");
	}

	public String puteuq(HkRequest req, HkResponse resp) {
		long eid = req.getLong("eid");
		long userId = req.getLong("userId");
		Equipment equipment = EquipmentConfig.getEquipment(eid);
		if (equipment != null) {
			equipment.setRate(1);
			this.handleEquipmentProcessor.processGet(userId, equipment);
			resp.sendHtml(1);
		}
		else {
			resp.sendHtml(0);
		}
		return null;
	}

	/**
	 * 获得所有道具
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-29
	 */
	public String getallequ(HkRequest req, HkResponse resp) {
		long userId = req.getLong("userId");
		for (int i = 1; i <= 9; i++) {
			long eid = i;
			Equipment equipment = EquipmentConfig.getEquipment(eid);
			equipment.setRate(1);
			this.handleEquipmentProcessor.processGet(userId, equipment);
		}
		return null;
	}

	/**
	 * 主菜单的报到功能(我报到过的地方和我关注的人报道过的地方)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-22
	 */
	public String tocheckin(HkRequest req, HkResponse resp) {
		req.setAttribute("to_checkin", true);
		User loginUser = this.getLoginUser(req);
		List<CmpCheckInUserLog> friendloglist = this.cmpCheckInProcessor
				.getFriendCmpCheckInUserLog(loginUser.getUserId(), true, true,
						10);
		req.setAttribute("friendloglist", friendloglist);
		SimplePage page = req.getSimplePage(20);
		List<CmpCheckInUser> list = this.cmpCheckInProcessor
				.getCmpCheckInUserListByUserId(loginUser.getUserId(), false,
						true, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWeb4Jsp("user/tocheckin.jsp");
	}

	/**
	 * 优惠券列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-24
	 */
	public String coupon(HkRequest req, HkResponse resp) {
		req.setAttribute("to_award", true);
		User loginUser = this.getLoginUser(req);
		SimplePage page = req.getSimplePage(20);
		List<UserCoupon> list = this.couponProcessor.getUserCouponListByUserId(
				loginUser.getUserId(), true, page.getBegin(),
				page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWeb4Jsp("user/coupon.jsp");
	}

	/**
	 * 奖品列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-24
	 */
	public String prize(HkRequest req, HkResponse resp) {
		req.setAttribute("to_award", true);
		User loginUser = this.getLoginUser(req);
		SimplePage page = req.getSimplePage(20);
		List<UserBoxPrize> list = this.boxProcessor
				.getUserBoxPrizeListByUserId(loginUser.getUserId(), true, page
						.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWeb4Jsp("user/prize.jsp");
	}

	/**
	 * 用户的道具(装备)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-4-14
	 */
	public String equ(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("to_award", true);
		long userId = this.getLoginUser(req).getUserId();
		List<UserEquipment> list = this.equipmentProcessor
				.getUserEquipmentListByUserId(userId, UserEquipment.USEFLG_N,
						true, false, false, 0, -1);
		req.setAttribute("list", list);
		return this.getWeb4Jsp("user/equipment.jsp");
	}

	/**
	 * 用户的道具(装备)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-4-14
	 */
	public String equused(HkRequest req, HkResponse resp) throws Exception {
		long userId = this.getLoginUser(req).getUserId();
		SimplePage page = req.getSimplePage(20);
		List<UserEquipment> list = this.equipmentProcessor
				.getUserEquipmentListByUserId(userId, UserEquipment.USEFLG_Y,
						true, true, true, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWeb4Jsp("user/equipmentused.jsp");
	}
}