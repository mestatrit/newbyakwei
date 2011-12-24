package com.hk.listener.laba.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.HkbLog;
import com.hk.bean.Laba;
import com.hk.bean.LabaCmt;
import com.hk.bean.Notice;
import com.hk.bean.ScoreLog;
import com.hk.bean.UserNoticeInfo;
import com.hk.frame.util.DataUtil;
import com.hk.listener.laba.LabaEventListener;
import com.hk.svr.LabaService;
import com.hk.svr.NoticeService;
import com.hk.svr.UserService;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.laba.parser.LabaOutPutParser;
import com.hk.svr.notice.NoticeProcessor;
import com.hk.svr.pub.HkbConfig;
import com.hk.svr.pub.ScoreConfig;

public class LabaEventListenerImpl implements LabaEventListener {
	@Autowired
	private NoticeProcessor noticeProcessor;

	@Autowired
	private LabaService labaService;

	@Autowired
	private UserService userService;

	@Autowired
	private NoticeService noticeService;

	private long longtime24 = 24 * 60 * 60 * 1000;

	public void labaCreated(Laba laba, LabaInfo labaInfo) {
		this.addHkbWhenCreateSmsLaba(laba, labaInfo);
		this.noticeWhenLabaHasUser(laba, labaInfo);
		this.addScoreWhenRefLaba(labaInfo);
		this.userService.updateUserUpdate(laba.getUserId());
	}

	public void labaCmtCreated(LabaCmt labaCmt, LabaInfo labaInfo) {
		this.notice(labaCmt, labaInfo);
		this.addScoreWhenCreateLabaCmt(labaCmt, labaInfo);
		this.processRespLaba(labaInfo);
		this.userService.updateUserUpdate(labaCmt.getUserId());
	}

	private void processRespLaba(LabaInfo labaInfo) {
		this.labaService.processRespLaba(labaInfo);
	}

	private void addScoreWhenRefLaba(LabaInfo labaInfo) {
		if (labaInfo.getRefLabaId() > 0) {
			Laba o = this.labaService.getLaba(labaInfo.getRefLabaId());
			if (o != null && o.getUserId() != labaInfo.getUserId()) {
				ScoreLog scoreLog = ScoreLog.create(o.getUserId(),
						HkbConfig.REFLABA, o.getLabaId(), ScoreConfig
								.getRefLaba());
				this.userService.addScore(scoreLog);
			}
		}
	}

	/**
	 * 短信吹喇叭加酷币
	 * 
	 * @param laba
	 * @param labaInfo
	 */
	private void addHkbWhenCreateSmsLaba(Laba laba, LabaInfo labaInfo) {
		long labaId = laba.getLabaId();
		if (labaInfo.getSendFrom() == Laba.SENDFROM_SMS) {
			HkbLog o = HkbLog.create(labaInfo.getUserId(),
					HkbConfig.CREATESMSLABA, labaId, HkbConfig
							.getCreateSmsLaba());
			this.userService.addHkb(o);
		}
	}

	private void addScoreWhenCreateLabaCmt(LabaCmt labaCmt, LabaInfo labaInfo) {
		// 回应喇叭的积分
		if (labaInfo.getReplyLabaId() > 0) {
			List<LabaCmt> cmtlist = this.labaService
					.getUserLabaCmtListByLabaId(labaCmt.getLabaId(), labaCmt
							.getUserId(), 0, 1);
			boolean canAddScore = false;
			if (cmtlist.size() == 0) {
				canAddScore = true;
			}
			else {
				LabaCmt o = cmtlist.iterator().next();
				// 超过24小时就可以加积分
				if (o.getCreateTime().getTime() + this.longtime24 <= System
						.currentTimeMillis()
						&& o.getUserId() != labaInfo.getUserId()) {
					canAddScore = true;
				}
			}
			if (canAddScore) {
				Laba o = this.labaService.getLaba(labaInfo.getReplyLabaId());
				if (o != null) {
					ScoreLog scoreLog = ScoreLog.create(o.getUserId(),
							HkbConfig.REPLYLABA, o.getLabaId(), ScoreConfig
									.getReplyLaba());
					this.userService.addScore(scoreLog);
				}
			}
		}
	}

	/**
	 * 如果不是喇叭回复，喇叭中带有其他人的名字，就给对方通知
	 * 
	 * @param labaId
	 * @param joinPoint
	 */
	private void noticeWhenLabaHasUser(Laba laba, LabaInfo labaInfo) {
		if (labaInfo.getReplyLabaId() > 0 || labaInfo.getRefLabaId() > 0) {
			return;
		}
		for (Long userId : labaInfo.getUserIdList()) {
			if (userId != labaInfo.getUserId()) {// 如果不是喇叭发表人
				UserNoticeInfo info = this.userService
						.getUserNoticeInfo(userId);
				if (info == null || info.isNoticeUserInLaba()) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("userid", String.valueOf(labaInfo.getUserId()));
					map.put("nickname", userService.getUser(
							labaInfo.getUserId()).getNickName());
					map.put("labaid", String.valueOf(laba.getLabaId()));
					String content = labaInfo.getContent();
					// 处理足迹特殊数据
					Set<String> cmpset = LabaOutPutParser
							.parseCompanyName(content);
					for (String s : cmpset) {
						String[] tmp = s.split(",");
						String cmpname = null;
						if (tmp.length > 1) {
							cmpname = tmp[1];
						}
						String pat = "\\{\\[" + s + "\\}";
						content = content.replaceAll(pat, "[" + cmpname + "]");
					}
					map.put("content", DataUtil.limitLength(content, 200));
					Notice notice = new Notice();
					notice.setData(DataUtil.toJson(map));
					notice.setNoticeType(Notice.NOTICETYPE_USER_IN_LABA);
					notice.setUserId(userId);
					notice.setCreateTime(new Date());
					notice.setReadflg(Notice.READFLG_N);
					this.noticeService.createNotice(notice);
				}
			}
		}
	}

	private void notice(LabaCmt labaCmt, LabaInfo labaInfo) {
		List<Notice> list = new ArrayList<Notice>();
		long replyLabaId = labaInfo.getReplyLabaId();
		if (replyLabaId <= 0) {
			return;
		}
		Laba o = this.labaService.getLaba(replyLabaId);
		if (o == null) {
			return;
		}
		if (o.getUserId() != labaInfo.getUserId()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", String.valueOf(labaInfo.getUserId()));
			map.put("nickname", userService.getUser(labaInfo.getUserId())
					.getNickName());
			map.put("labaid", String.valueOf(labaCmt.getLabaId()));
			map.put("cmtid", String.valueOf(labaCmt.getCmtId()));
			map
					.put("content", DataUtil.limitLength(labaInfo.getContent(),
							200));
			Notice notice = new Notice();
			notice.setData(DataUtil.toJson(map));
			notice.setNoticeType(Notice.NOTICETYPE_LABAREPLY);
			notice.setUserId(o.getUserId());
			notice.setCreateTime(new Date());
			notice.setReadflg(Notice.READFLG_N);
			list.add(notice);
			UserNoticeInfo userNoticeInfo = this.userService
					.getUserNoticeInfo(o.getUserId());
			if (userNoticeInfo != null
					&& !userNoticeInfo.isSysNoticeLabaReply()) {// 不进行系统通知
				notice.setReadflg(Notice.READFLG_Y);
			}
			this.noticeService.createNotice(notice);
			if (userNoticeInfo == null
					|| userNoticeInfo.isNoticeLabaReplyForMail()
					|| userNoticeInfo.isNoticeLabaReplyForIM()) {// 进行即时消息和mail通知
				noticeProcessor.addNotice(list);
			}
		}
		else {
			if (labaCmt.getUserId() != labaInfo.getReplyUserId()
					&& labaInfo.getReplyUserId() > 0) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("userid", String.valueOf(labaInfo.getUserId()));
				map.put("nickname", userService.getUser(labaInfo.getUserId())
						.getNickName());
				map.put("labaid", String.valueOf(labaCmt.getLabaId()));
				map.put("cmtid", String.valueOf(labaCmt.getCmtId()));
				map.put("content", DataUtil.limitLength(labaInfo.getContent(),
						200));
				Notice notice = new Notice();
				notice.setData(DataUtil.toJson(map));
				notice.setNoticeType(Notice.NOTICETYPE_LABAREPLY);
				notice.setUserId(labaInfo.getReplyUserId());
				notice.setCreateTime(new Date());
				notice.setReadflg(Notice.READFLG_N);
				list.add(notice);
				UserNoticeInfo userNoticeInfo = this.userService
						.getUserNoticeInfo(o.getUserId());
				if (userNoticeInfo != null
						&& !userNoticeInfo.isSysNoticeLabaReply()) {// 不进行系统通知
					notice.setReadflg(Notice.READFLG_Y);
				}
				this.noticeService.createNotice(notice);
				if (userNoticeInfo == null
						|| userNoticeInfo.isNoticeLabaReplyForMail()
						|| userNoticeInfo.isNoticeLabaReplyForIM()) {// 进行即时消息和mail通知
					noticeProcessor.addNotice(list);
				}
			}
		}
	}
}