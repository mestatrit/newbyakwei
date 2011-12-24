package com.hk.web.util.equipment;

import java.util.Map;

import com.hk.frame.web.http.HkRequest;
import com.hk.svr.equipment.EquipmentMsg;

public class EquipmentMsgUtil {

	/**
	 * 获得返回的道具使用消息
	 * 
	 * @param equipmentMsg
	 * @param request
	 * @return
	 *         2010-4-29
	 */
	public static String getEquipmentMessage(EquipmentMsg equipmentMsg,
			HkRequest req, boolean wap) {
		Map<String, String> datamap = equipmentMsg.getDatamap();
		String vs = datamap.get("vs");
		if (vs != null && vs.equals("1")) {
			if (wap) {
				return processVsWap(equipmentMsg, req);
			}
			return processVs(equipmentMsg, req);
		}
		String sos = datamap.get("sos");
		if (sos != null && sos.equals("1")) {
			if (wap) {
				return processSosWap(equipmentMsg, req);
			}
			return processSos(equipmentMsg, req);
		}
		return null;
	}

	private static String processVsWap(EquipmentMsg equipmentMsg, HkRequest req) {
		Map<String, String> datamap = equipmentMsg.getDatamap();
		String nickName = datamap.get("nickname");
		String userurl = "<a href=\"" + req.getContextPath()
				+ "/home.do?userId=" + equipmentMsg.getUserId() + "/\">"
				+ nickName + "</a>";
		String ename = datamap.get("ename");
		return req.getText("view2.equ.message_vsfail", userurl, ename);
	}

	private static String processVs(EquipmentMsg equipmentMsg, HkRequest req) {
		Map<String, String> datamap = equipmentMsg.getDatamap();
		String nickName = datamap.get("nickname");
		String userurl = "<a href=\"/user/" + equipmentMsg.getUserId() + "\">"
				+ nickName + "</a>";
		String ename = datamap.get("ename");
		return req.getText("view2.equ.message_vsfail", userurl, ename);
	}

	private static String processSosWap(EquipmentMsg equipmentMsg, HkRequest req) {
		Map<String, String> datamap = equipmentMsg.getDatamap();
		String onickname = datamap.get("onickname");
		String vsename = datamap.get("vsename");
		String ename = datamap.get("ename");
		String userurl = "<a href=\"" + req.getContextPath()
				+ "/home.do?userId=" + equipmentMsg.getUserId() + "\">"
				+ onickname + "</a>";
		return req.getText("view2.equ.message_vswin", vsename, userurl, ename);
	}

	private static String processSos(EquipmentMsg equipmentMsg, HkRequest req) {
		Map<String, String> datamap = equipmentMsg.getDatamap();
		String onickname = datamap.get("onickname");
		String vsename = datamap.get("vsename");
		String ename = datamap.get("ename");
		String userurl = "<a href=\"/user/" + equipmentMsg.getUserId() + "/\">"
				+ onickname + "</a>";
		return req.getText("view2.equ.message_vswin", vsename, userurl, ename);
	}
}