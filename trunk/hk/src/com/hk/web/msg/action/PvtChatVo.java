package com.hk.web.msg.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.hk.bean.PvtChat;
import com.hk.bean.UserSms;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserSmsService;

public class PvtChatVo {
	private PvtChat pvtChat;

	private UserSms userSms;

	public PvtChat getPvtChat() {
		return pvtChat;
	}

	public void setPvtChat(PvtChat pvtChat) {
		this.pvtChat = pvtChat;
	}

	public UserSms getUserSms() {
		return userSms;
	}

	public void setUserSms(UserSms userSms) {
		this.userSms = userSms;
	}

	/**
	 * 组装私信值对象,包含是否是短信发送
	 * 
	 * @param list
	 * @return
	 */
	public static List<PvtChatVo> createVoList(List<PvtChat> list) {
		PvtChat.initUserInList(list);
		List<Long> msgIdList = new ArrayList<Long>();
		for (PvtChat o : list) {
			if (o.getSmsmsgId() > 0) {
				msgIdList.add(o.getSmsmsgId());
			}
		}
		UserSmsService userSmsService = (UserSmsService) HkUtil
				.getBean("userSmsService");
		Map<Long, UserSms> map = userSmsService.getUserSmsMapInId(msgIdList);
		List<PvtChatVo> volist = new ArrayList<PvtChatVo>();
		for (PvtChat o : list) {
			PvtChatVo vo = new PvtChatVo();
			vo.setPvtChat(o);
			vo.setUserSms(map.get(o.getSmsmsgId()));
			volist.add(vo);
		}
		return volist;
	}
}