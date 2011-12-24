package com.hk.svr.processor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Invite;
import com.hk.bean.InviteCode;
import com.hk.bean.Notice;
import com.hk.bean.UserInviteConfig;
import com.hk.frame.util.DataUtil;
import com.hk.svr.InviteService;
import com.hk.svr.NoticeService;
import com.hk.svr.UserService;

public class InviteProcessor {

	@Autowired
	private InviteService inviteService;

	@Autowired
	private UserService userService;

	@Autowired
	private NoticeService noticeService;

	public InviteCode createInviteCode(long userId) {
		UserInviteConfig userInviteConfig = this.inviteService
				.getUserInviteConfig(userId);
		if (userInviteConfig == null) {
			userInviteConfig = new UserInviteConfig();
			userInviteConfig.setUserId(userId);
			userInviteConfig.setInviteNum(5);
		}
		InviteCode inviteCode = this.inviteService.createInviteCode(userId);
		userInviteConfig.addInviteNum(-1);
		this.inviteService.saveUserInviteConfig(userInviteConfig);
		return inviteCode;
	}

	public void acceptInvite(long inviteId, long friendId, byte addhkbflg) {
		inviteService.acceptInvite(inviteId, friendId, addhkbflg);
		acceptInvite(inviteId, friendId);
	}

	public void acceptNewInvite(long userId, long friendId, byte inviteType,
			byte addhkbflg, boolean needNoticeAndFeed) {
		inviteService.acceptNewInvite(userId, friendId, inviteType, addhkbflg);
		acceptNewInvite(userId, friendId, needNoticeAndFeed);
	}

	public void acceptInvite(long inviteId, long friendId) {
		Invite invite = this.inviteService.getInvite(inviteId);
		if (invite != null) {
			this.process(invite.getUserId(), friendId);
		}
	}

	public void acceptNewInvite(long userId, long friendId,
			boolean needNoticeAndFeed) {
		if (needNoticeAndFeed) {
			this.process(userId, friendId);
		}
	}

	private void process(long userId, long friendId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", String.valueOf(friendId));
		map.put("nickname", userService.getUser(friendId).getNickName());
		Notice notice = new Notice();
		notice.setData(DataUtil.toJson(map));
		notice.setNoticeType(Notice.NOTICETYPE_INVITE);
		notice.setUserId(userId);
		notice.setReadflg(Notice.READFLG_N);
		notice.setCreateTime(new Date());
		this.noticeService.createNotice(notice);
	}
}