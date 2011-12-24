package com.hk.listener.msg.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Notice;
import com.hk.bean.SendInfo;
import com.hk.bean.UserNoticeInfo;
import com.hk.frame.util.DataUtil;
import com.hk.listener.msg.MsgEventListener;
import com.hk.svr.UserService;
import com.hk.svr.notice.NoticeProcessor;

public class MsgEventListenerImpl implements MsgEventListener {
	@Autowired
	private NoticeProcessor noticeProcessor;

	@Autowired
	private UserService userService;

	public void msgCreated(SendInfo sendInfo, long userId, long senderId,
			String msg) {
		processWhenSendMsg(sendInfo, userId, senderId, msg);
	}

	public void processWhenSendMsg(SendInfo sendInfo, long userId,
			long senderId, String msg) {
		this.updateUserContactDegree(userId, senderId);
		this.notice(sendInfo, userId, senderId, msg);
	}

	private void updateUserContactDegree(long userId, long senderId) {
		if (userId == senderId) {
			return;
		}
		this.userService.updateUserContactDegree(senderId, userId);
	}

	private void notice(SendInfo sendInfo, long userId, long senderId,
			String msg) {
		if (userId == senderId) {
			return;
		}
		UserNoticeInfo userNoticeInfo = this.userService
				.getUserNoticeInfo(userId);
		if (userNoticeInfo == null || userNoticeInfo.isNoticeMsg()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", String.valueOf(senderId));
			map.put("nickname", userService.getUser(senderId).getNickName());
			map.put("mainid", String.valueOf(sendInfo.getReceiverPvtChat()
					.getMainId()));
			map.put("content", DataUtil.limitLength(msg, 15));
			Notice notice = new Notice();
			notice.setNoticeType(Notice.NOTICETYPE_CREATEMSG);
			notice.setUserId(userId);
			notice.setData(DataUtil.toJson(map));
			notice.setCreateTime(new Date());
			List<Notice> list = new ArrayList<Notice>();
			list.add(notice);
			noticeProcessor.addNotice(list);
		}
	}
}