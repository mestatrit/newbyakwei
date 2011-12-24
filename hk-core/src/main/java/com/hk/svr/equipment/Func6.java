package com.hk.svr.equipment;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.UserCmpPoint;
import com.hk.bean.UserEquEnjoy;
import com.hk.bean.UserEquPoint;
import com.hk.bean.UserEquipment;
import com.hk.svr.CmpCheckInService;
import com.hk.svr.EquipmentService;
import com.hk.svr.UserService;

/**
 * 剥削卡的使用
 * 
 * @author akwei
 */
public class Func6 extends FuncEquipment {

	@Autowired
	private UserService userService;

	@Autowired
	private CmpCheckInService cmpCheckInService;

	@Autowired
	private EquipmentService equipmentService;

	private final Log log = LogFactory.getLog(Func6.class);

	@Override
	public boolean execute(UserEquEnjoy userEquEnjoy,
			UserEquipment userEquipment, Map<String, Object> ctxAttributeMap) {
		log.info("begin func6 ...[ " + userEquEnjoy.getUserId() + " , "
				+ userEquEnjoy.getEnjoyUserId() + " ]");
		log.info("use func6 ...");
		long companyId = (Long) ctxAttributeMap.get("companyId");
		long userId = userEquEnjoy.getEnjoyUserId();
		UserCmpPoint userCmpPoint = this.cmpCheckInService
				.getUserCmpPointByUserIdAndCompanyId(userId, companyId);
		if (userCmpPoint == null || userCmpPoint.getPoints() == 0) {
			return false;
		}
		BigDecimal res = new BigDecimal(userCmpPoint.getPoints()).divide(
				new BigDecimal(3), 0, BigDecimal.ROUND_HALF_UP);
		int respoints = res.intValue();
		if (respoints == 0) {
			respoints = userCmpPoint.getPoints();
		}
		this.userService.addPoints(userEquEnjoy.getUserId(), respoints);
		this.userService.addPoints(userId, -respoints);
		userCmpPoint.setPoints(userCmpPoint.getPoints() - respoints);
		this.cmpCheckInService.updateUserCmpPoints(userCmpPoint);
		UserEquPoint userEquPoint = new UserEquPoint();
		userEquPoint.setUserId(userId);
		userEquPoint.setPoints(respoints);
		this.equipmentService.saveUserEquPoint(userEquPoint);
		if (userEquEnjoy.getForceEid() <= 0) {
			// 动态数据
			EquipmentMsg msg = new EquipmentMsg();
			msg.setUserId(userEquEnjoy.getUserId());
			Map<String, String> datamap = this.createVsFeed(userEquEnjoy,
					ctxAttributeMap);
			msg.setDatamap(datamap);
			ctxAttributeMap.put(EquipmentStatus.EQUIPMENTMSG_ATTR, msg);
		}
		return true;
	}
}