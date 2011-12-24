package com.hk.sms.cmd;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Laba;
import com.hk.bean.User;
import com.hk.bean.UserLabaReply;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.sms.ReceivedSms;
import com.hk.sms.Sms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.LabaService;
import com.hk.svr.UserService;
import com.hk.web.pub.action.LabaVo;

public class LabaReplyCmd extends BaseCmd {
	@Autowired
	private LabaService labaService;

	@Autowired
	private UserService userService;

	private final Log log = LogFactory.getLog(LabaReplyCmd.class);

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) {
		log.info("get userlabareply [ " + receivedSms.getContent() + " ]");
		String mobile = receivedSms.getMobile();
		Sms o = new Sms();
		o.setMobile(mobile);
		o.setPort(receivedSms.getPort());
		o.setLinkid(receivedSms.getLinkid());
		int size = 1;
		int begin = 0;
		if (receivedSms.getContent().equalsIgnoreCase("cx")) {
			begin = 0;
		}
		else {
			try {
				begin = Integer.parseInt(receivedSms.getContent()) - 1;
			}
			catch (NumberFormatException e1) {
				begin = 0;
			}
		}
		if (begin < 0) {
			begin = 0;
		}
		UserOtherInfo info = this.userService.getUserOtherInfoByMobile(mobile);
		if (info == null) {
			return null;
		}
		if (info.getMobileBind() == UserOtherInfo.MOBILE_NOT_BIND) {
			o.setContent("手机没有绑定,不能使用此服务.");
			this.sendMsg(o);
		}
		Calendar b = Calendar.getInstance();
		Calendar e = Calendar.getInstance();
		b.set(Calendar.HOUR_OF_DAY, 0);
		b.set(Calendar.MINUTE, 0);
		b.set(Calendar.SECOND, 0);
		e.set(Calendar.HOUR_OF_DAY, 23);
		e.set(Calendar.MINUTE, 59);
		e.set(Calendar.SECOND, 59);
		List<UserLabaReply> list = labaService
				.getUserLabaReplyListByUserIdAndTime(info.getUserId(), b
						.getTime(), e.getTime(), begin, size);
		int count = this.labaService.counttUserLabaReplyListByUserIdAndTime(
				info.getUserId(), b.getTime(), e.getTime());
		if (count == 0) {
			o.setContent("今天您没有任何回复\n");
			this.sendMsg(o);
			return null;
		}
		String s0 = "今天有" + count + "个新回复\n";
		Laba laba = list.iterator().next().getLaba();
		LabaVo vo = LabaVo.create(laba, null);
		User labaUser = this.userService.getUser(laba.getUserId());
		String s1 = labaUser.getNickName() + ":" + vo.getContent();
		int sum = 180;
		int remain = sum - s0.length();
		if (count == 1) {
			if (s1.length() > remain) {
				s1 = DataUtil.limitLength(s1, remain);
			}
			o.setContent(s0 + s1);
			this.sendMsg(o);
			return null;
		}
		if (begin > count - 1) {
			return null;
		}
		String s2 = "";
		if (begin == count - 1) {// 最后一条
		}
		else {// begin < count - 1
			if (begin < count - 2) {
				s2 = "\n回复" + (begin + 2) + "~" + count + "中任意数字查看对应回复内容";
			}
			else {
				s2 = "\n回复" + count + "查看对应回复内容";
			}
		}
		remain = remain - s2.length();
		s1 = DataUtil.limitLength(s1, remain);
		o.setContent(s0 + s1 + s2);
		this.sendMsg(o);
		return null;
	}
}