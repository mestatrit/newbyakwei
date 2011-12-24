package com.hk.web.box.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Box;
import com.hk.bean.BoxPretype;
import com.hk.bean.BoxPrize;
import com.hk.bean.Equipment;
import com.hk.bean.User;
import com.hk.bean.UserBoxPrize;
import com.hk.bean.UserEquipment;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BoxOpenResult;
import com.hk.svr.BoxService;
import com.hk.svr.processor.BoxProccessorOpenResult;
import com.hk.svr.processor.BoxProcessor;
import com.hk.svr.pub.BoxPretypeUtil;
import com.hk.svr.pub.EquipmentConfig;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.pub.action.BaseAction;

@Component("/op/box/open")
public class OpenBoxAction extends BaseAction {

	@Autowired
	private BoxService boxService;

	@Autowired
	private BoxProcessor boxProcessor;

	public String execute(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		Box box = this.boxService.getBox(boxId);
		if (box == null) {
			return null;
		}
		User loginUser = this.getLoginUser(req);
		BoxProccessorOpenResult boxProccessorOpenResult = this.boxProcessor
				.openBox2(loginUser.getUserId(), loginUser.getPcityId(), box,
						req.getRemoteAddr());
		int result = boxProccessorOpenResult.getErrorCode();
		if (result != Err.SUCCESS) {
			req.setSessionText(String.valueOf(result), ZoneUtil.getCityName(box
					.getCityId()));
			return "r:/box/market.do";
		}
		if (boxProccessorOpenResult.isNoEnoughPoints()) {
			req.setSessionText("view2.notenoughpointsforopenbox");
			return "r:/op/user_findcmp.do";
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
			return "r:/box/box.do?boxId=" + boxId;
		}
		// 中了道具，没有中奖
		if (boxOpenResult.getUserEquipment() != null
				&& boxOpenResult.getUserBoxPrize() == null) {
			return "r:/op/box/open_equ.do?boxId=" + boxId + "&eid="
					+ boxOpenResult.getUserEquipment().getEid();
		}
		// 每中道具，也没有中奖
		if (boxOpenResult.getUserBoxPrize() == null) {
			req.setSessionText("view2.box.openbox.noprize");
			return "r:/box/box.do?boxId=" + boxId;
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
		return "r:/op/box/open_result.do?boxId=" + boxId + "&prizeId="
				+ prizeId + "&eid=" + eid + "&sysId="
				+ boxOpenResult.getUserBoxPrize().getSysId();
	}

	/**
	 * 中奖结果页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-23
	 */
	public String result(HkRequest req, HkResponse resp) {
		long boxId = req.getLongAndSetAttr("boxId");
		long sysId = req.getLong("sysId");
		long prizeId = req.getLong("prizeId");
		long eid = req.getLong("eid");
		User loginUser = this.getLoginUser(req);
		Box box = this.boxService.getBox(boxId);
		req.setAttribute("box", box);
		UserBoxPrize userBoxPrize = this.boxService.getUserBoxPrize(sysId);
		if (userBoxPrize.getUserId() == loginUser.getUserId()) {
			req.setAttribute("userBoxPrize", userBoxPrize);
			BoxPrize prize = this.boxService.getBoxPrize(prizeId);
			req.setAttribute("prize", prize);
		}
		Equipment equipment = EquipmentConfig.getEquipment(eid);
		req.setAttribute("equipment", equipment);
		return this.getWapJsp("box/result.jsp");
	}

	/**
	 * 宝箱获得道具结果页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-15
	 */
	public String equ(HkRequest req, HkResponse resp) {
		long boxId = req.getLongAndSetAttr("boxId");
		long eid = req.getLong("eid");
		Box box = this.boxService.getBox(boxId);
		req.setAttribute("box", box);
		Equipment equipment = EquipmentConfig.getEquipment(eid);
		req.setAttribute("equipment", equipment);
		return this.getWapJsp("box/equ.jsp");
	}
}