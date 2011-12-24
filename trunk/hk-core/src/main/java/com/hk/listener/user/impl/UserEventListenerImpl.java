package com.hk.listener.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Feed;
import com.hk.bean.Invite;
import com.hk.bean.IpCityRange;
import com.hk.bean.Notice;
import com.hk.bean.UserNoticeInfo;
import com.hk.frame.util.DataUtil;
import com.hk.listener.user.FollowEvent;
import com.hk.listener.user.FollowEventListener;
import com.hk.listener.user.InviteEventListener;
import com.hk.svr.FeedService;
import com.hk.svr.InviteService;
import com.hk.svr.IpCityService;
import com.hk.svr.NoticeService;
import com.hk.svr.UserService;
import com.hk.svr.notice.NoticeProcessor;

public class UserEventListenerImpl implements FollowEventListener,
		InviteEventListener {
	@Autowired
	private InviteService inviteService;

	@Autowired
	private NoticeProcessor noticeProcessor;

	@Autowired
	private FeedService feedService;

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private UserService userService;

	@Autowired
	private NoticeService noticeService;

	public void followCreated(FollowEvent event) {
		this.notice(event);
		this.feed(event);
	}

	private void notice(FollowEvent event) {
		boolean ok = event.isResutlt();
		if (ok) {
			long userId = event.getUserId();
			long friendId = event.getFriendId();
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", String.valueOf(userId));
			map.put("nickname", userService.getUser(userId).getNickName());
			Notice notice = new Notice();
			notice.setData(DataUtil.toJson(map));
			notice.setNoticeType(Notice.NOTICETYPE_FOLLOW);
			notice.setUserId(friendId);
			notice.setReadflg(Notice.READFLG_N);
			notice.setCreateTime(new Date());
			List<Notice> list = new ArrayList<Notice>();
			list.add(notice);
			UserNoticeInfo userNoticeInfo = this.userService
					.getUserNoticeInfo(friendId);
			if (userNoticeInfo != null && !userNoticeInfo.isSysNoticeFollow()) {// 不进行系统通知
				notice.setReadflg(Notice.READFLG_Y);
			}
			this.noticeService.createNotice(notice);
			if (userNoticeInfo == null
					|| userNoticeInfo.isNoticeFollowForMail()
					|| userNoticeInfo.isNoticeFollowForIM()) {// 进行即时消息和mail通知
				noticeProcessor.addNotice(list);
			}
		}
	}

	private void feed(FollowEvent event) {
		boolean ok = event.isResutlt();
		if (ok) {
			long userId = event.getUserId();
			long friendId = event.getFriendId();
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", String.valueOf(friendId));
			map.put("usernickname", this.userService.getUser(userId)
					.getNickName());
			map.put("nickname", this.userService.getUser(friendId)
					.getNickName());
			String ip = event.getIp();
			Feed feed = new Feed();
			feed.setUserId(userId);
			feed.setFeedType(Feed.FEEDTYPE_FOLLOW);
			feed.setCreateTime(new Date());
			feed.setData(DataUtil.toJson(map));
			if (ip != null) {
				feed.setIpNumber(DataUtil.parseIpNumber(ip));
				IpCityRange range = this.ipCityService.getIpCityRange(ip);
				if (range != null) {
					feed.setRangeId(range.getRangeId());
				}
			}
			this.feedService.createFeed(feed, null);
		}
	}

	public void followDeleted(FollowEvent event) {
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
}