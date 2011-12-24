package com.hk.web.notice.job;

import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import net.sf.jml.Email;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Notice;
import com.hk.bean.UserBindInfo;
import com.hk.bean.UserNoticeInfo;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.frame.util.mail.MailUtil;
import com.hk.im.msn.MsnRobot;
import com.hk.svr.UserService;
import com.hk.svr.notice.NoticeProcessor;

public class NoticeJob {
	@Autowired
	private NoticeProcessor noticeProcessor;

	@Autowired
	private MsnRobot msnRobot;

	@Autowired
	private UserService userService;

	@Autowired
	private MailUtil mailUtil;

	private final Log log = LogFactory.getLog(NoticeJob.class);

	public void invoke() {
		List<Notice> list = this.noticeProcessor.getNotictListAndRemove();
		for (Notice o : list) {
			try {
				this.prosessNotice(o);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void prosessNotice(Notice notice) throws Exception {
		long userId = notice.getUserId();
		UserNoticeInfo userNoticeInfo = this.userService
				.getUserNoticeInfo(userId);
		UserBindInfo userBindInfo = this.userService.getUserBindInfo(userId);
		if (userBindInfo == null || DataUtil.isEmpty(userBindInfo.getMsn())) {
			return;
		}
		if (notice.getNoticeType() == Notice.NOTICETYPE_LABAREPLY) {
			this.processLabaReply(notice, userNoticeInfo, userBindInfo);
		}
		else if (notice.getNoticeType() == Notice.NOTICETYPE_CREATEMSG) {
			this.processSendmsg(notice, userNoticeInfo, userBindInfo);
		}
		else if (notice.getNoticeType() == Notice.NOTICETYPE_FOLLOW) {
			this.processFollow(notice, userNoticeInfo, userBindInfo);
		}
	}

	private void processFollow(Notice notice, UserNoticeInfo userNoticeInfo,
			UserBindInfo userBindInfo) {
		if (userNoticeInfo != null && !userNoticeInfo.isNoticeFollowForMail()
				&& !userNoticeInfo.isNoticeFollowForIM()) {
			return;
		}
		Map<String, String> map = DataUtil.getMapFromJson(notice.getData());
		String nickName = map.get("nickname");
		long userId = Long.valueOf(map.get("userid"));
		String content = ResourceConfig.getText(
				"func.msn.noticejob.follow.content0", "@" + nickName)
				+ "\n"
				+ ResourceConfig.getText("func.msn.noticejob.follow.content1",
						userId);
		if (userNoticeInfo == null || userNoticeInfo.isNoticeFollowForIM()) {
			this.sendMsnMessage(userBindInfo, content);
		}
		if (userNoticeInfo == null || userNoticeInfo.isNoticeFollowForMail()) {
			String title = ResourceConfig.getText(
					"func.mail.noticejob.follow.title", nickName);
			content = ResourceConfig.getText(
					"func.mail.noticejob.follow.content", nickName, userId);
			this.sendMailMessage(notice, title, content);
		}
	}

	private void processLabaReply(Notice notice, UserNoticeInfo userNoticeInfo,
			UserBindInfo userBindInfo) {
		if (userNoticeInfo != null
				&& !userNoticeInfo.isNoticeLabaReplyForMail()
				&& !userNoticeInfo.isNoticeLabaReplyForIM()) {// 不通过msn和mail通知
			return;
		}
		Map<String, String> map = DataUtil.getMapFromJson(notice.getData());
		String content2 = map.get("content");
		String nickName = map.get("nickname");
		String content = ResourceConfig.getText(
				"func.msn.noticejob.labareply.content0", nickName)
				+ "\n"
				+ ResourceConfig.getText(
						"func.msn.noticejob.labareply.content1", content2)
				+ "\n"
				+ ResourceConfig
						.getText("func.msn.noticejob.labareply.content2")
				+ "\n"
				+ ResourceConfig
						.getText("func.msn.noticejob.labareply.content3");
		if (userNoticeInfo == null || userNoticeInfo.isNoticeLabaReplyForIM()) {
			this.sendMsnMessage(userBindInfo, content);
		}
		if (userNoticeInfo == null || userNoticeInfo.isNoticeLabaReplyForMail()) {
			String title = ResourceConfig.getText(
					"func.mail.noticejob.labareply.title", nickName);
			content = ResourceConfig
					.getText("func.mail.noticejob.labareply.content", nickName,
							content2);
			this.sendMailMessage(notice, title, content);
		}
	}

	private void processSendmsg(Notice notice, UserNoticeInfo userNoticeInfo,
			UserBindInfo userBindInfo) {
		if (userNoticeInfo != null && !userNoticeInfo.isNoticeMsg()) {
			return;
		}
		Map<String, String> map = DataUtil.getMapFromJson(notice.getData());
		String content2 = map.get("content");
		String nickName = map.get("nickname");
		long mainId = Long.valueOf(map.get("mainid"));
		String content = ResourceConfig.getText(
				"func.msn.noticejob.msg.content0", nickName)
				+ "\n"
				+ ResourceConfig.getText("func.msn.noticejob.msg.content1",
						DataUtil.toText(content2))
				+ "\n"
				+ ResourceConfig.getText("func.msn.noticejob.msg.content2",
						mainId + "")
				+ "\n"
				+ ResourceConfig.getText("func.msn.noticejob.msg.content3");
		this.sendMsnMessage(userBindInfo, content);
	}

	private void sendMsnMessage(UserBindInfo userBindInfo, String content) {
		if (userBindInfo != null && !DataUtil.isEmpty(userBindInfo.getMsn())) {
			String emailAddress = userBindInfo.getMsn();
			Email email = Email.parseStr(emailAddress);
			this.msnRobot.getMessenger().addFriend(email, emailAddress);
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.msnRobot.getMessenger().sendText(email, content);
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.msnRobot.getMessenger().removeFriend(email, false);
		}
	}

	private void sendMailMessage(Notice notice, String title, String content) {
		UserOtherInfo info = this.userService.getUserOtherInfo(notice
				.getUserId());
		if (!DataUtil.isEmpty(info.getEmail())) {
			try {
				this.mailUtil.sendHtmlMail(info.getEmail(), title, content);
			}
			catch (MessagingException e) {
				log.error(e.getMessage());
			}
		}
	}
}