package com.hk.im.msn.adapter;

import net.sf.jml.MsnContact;
import net.sf.jml.MsnMessenger;
import net.sf.jml.MsnSwitchboard;
import net.sf.jml.MsnUserStatus;
import net.sf.jml.event.MsnAdapter;
import net.sf.jml.message.MsnInstantMessage;
import net.sf.jml.message.MsnUnknownMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Laba;
import com.hk.bean.UserBindInfo;
import com.hk.frame.util.ContentFilterUtil;
import com.hk.svr.LabaService;
import com.hk.svr.UserService;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.web.util.HkWebConfig;

public class HkMsnAdapter extends MsnAdapter {
	private final Log log = LogFactory.getLog(HkMsnAdapter.class);

	@Autowired
	private LabaService labaService;

	@Autowired
	private UserService userService;

	@Autowired
	private ContentFilterUtil contentFilterUtil;

	private String displayName;

	private String personalMessage;

	public String getDisplayName() {
		return displayName;
	}

	public String getPersonalMessage() {
		return personalMessage;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setPersonalMessage(String personalMessage) {
		this.personalMessage = personalMessage;
	}

	@Override
	public void exceptionCaught(MsnMessenger messenger, Throwable throwable) {
		log.error(throwable.toString());
	}

	@Override
	public void loginCompleted(MsnMessenger messenger) {
		log.info(messenger + " 登录 ");
		try {
			Thread.sleep(5000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		messenger.getOwner().setDisplayName(this.displayName);
		messenger.getOwner().setPersonalMessage(this.personalMessage);
	}

	@Override
	public void logout(MsnMessenger messenger) {
		log.info(messenger + " 登出");
	}

	@Override
	public void instantMessageReceived(MsnSwitchboard switchboard,
			MsnInstantMessage message, MsnContact friend) {
		log.info(switchboard + " 收到正常消息 "
				+ switchboard.getMessenger().getConnection().getExternalIP());
		UserBindInfo info = this.userService.getUserBindInfoByMsn(friend
				.getEmail().getEmailAddress());
		if (info == null) {
			message
					.setContent("请通过火酷网个人设置中的绑定MSN来输入您的msn账户。\n没有火酷账户请通过 www.huoku.com 进行注册");
			switchboard.sendMessage(message, false);
			return;
		}
		String content = message.getContent();
		if (this.contentFilterUtil.hasFilterString(content)) {
			return;
		}
		// content = DataUtil.toTextRow(content);
		content = content.trim();
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setUserId(info.getUserId());
		labaInfo.setSendFrom(Laba.SENDFROM_MSN);
		this.labaService.createLaba(labaInfo);
	}

	@Override
	public void unknownMessageReceived(MsnSwitchboard switchboard,
			MsnUnknownMessage message, MsnContact friend) {
		log.info(switchboard + " 收到目前不能处理的信息 " + message.getContentAsString());
	}

	@Override
	public void contactAddedMe(MsnMessenger messenger, MsnContact friend) {
		log.info(friend.getEmail() + " add " + messenger);
		messenger.getOwner().setInitStatus(MsnUserStatus.ONLINE);
		messenger.addFriend(friend.getEmail(), friend.getEmail()
				.getEmailAddress());
	}

	@Override
	public void contactRemovedMe(MsnMessenger messenger, MsnContact friend) {
		log.info(friend.getEmail() + " remove " + messenger);
		messenger.removeFriend(friend.getEmail(), false);
	}
}