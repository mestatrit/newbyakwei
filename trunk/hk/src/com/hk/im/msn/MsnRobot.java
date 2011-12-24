package com.hk.im.msn;

import net.sf.jml.MsnConnection;
import net.sf.jml.MsnContact;
import net.sf.jml.MsnList;
import net.sf.jml.MsnMessenger;
import net.sf.jml.MsnProtocol;
import net.sf.jml.MsnUserStatus;
import net.sf.jml.impl.MsnMessengerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import com.hk.im.msn.adapter.HkMsnAdapter;

public class MsnRobot implements InitializingBean {

	private final Log log = LogFactory.getLog(MsnRobot.class);

	private HkMsnAdapter msnAdapter;

	private String email;

	private String password;

	private boolean start;

	public void setStart(boolean start) {
		this.start = start;
	}

	private MsnMessenger messenger;

	public void setMsnAdapter(HkMsnAdapter msnAdapter) {
		this.msnAdapter = msnAdapter;
	}

	public HkMsnAdapter getMsnAdapter() {
		return msnAdapter;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public MsnMessenger getMessenger() {
		return messenger;
	}

	public void invoke() {
		MsnConnection con = this.getMessenger().getConnection();
		if (con == null) {
			log.warn("msn down,begin login again >>> >>> >>>");
			this.getMessenger().logout();
			try {
				Thread.sleep(5000);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.initMessenger();
		}
		else {
			MsnContact[] mcs = messenger.getContactList().getContactsInList(
					MsnList.FL);
			for (MsnContact o : mcs) {
				log.info("remove friend  " + o.getEmail());
				this.messenger.removeFriend(o.getEmail(), false);
			}
		}
		this.setMsn();
	}

	public void setMsn() {
		if (!this.start) {
			return;
		}
		messenger.getOwner().setDisplayName(
				this.getMsnAdapter().getDisplayName());
		messenger.getOwner().setPersonalMessage(
				this.getMsnAdapter().getPersonalMessage());
	}

	public void afterPropertiesSet() throws Exception {
		if (messenger == null) {
			messenger = MsnMessengerFactory.createMsnMessenger(email, password);
			this.initMessenger();
		}
	}

	public void initMessenger() {
		messenger
				.setSupportedProtocol(new MsnProtocol[] { MsnProtocol.MSNP11 });
		messenger.getOwner().setInitStatus(MsnUserStatus.ONLINE);
		messenger.getOwner().setNotifyMeWhenSomeoneAddedMe(false);
		messenger.getOwner().setOnlyNotifyAllowList(false);
		messenger.setLogIncoming(false);
		messenger.setLogOutgoing(false);
		messenger.removeListener(msnAdapter);
		messenger.addListener(msnAdapter);
		if (start) {
			messenger.login();
		}
	}
}