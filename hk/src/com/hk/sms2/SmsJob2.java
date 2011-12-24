package com.hk.sms2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.frame.util.DataUtil;
import com.hk.sms.ReceivedSms;
import com.hk.sms.SmsClient;
import com.hk.sms.cmd.BaseCmd;
import com.hk.sms2.smsport.RootSmsPort;

public class SmsJob2 implements InitializingBean {
	private final Log log = LogFactory.getLog(SmsJob2.class);

	private LinkedHashMap<String, SmsPortProcessAble> smsPortMap;

	private List<PortWrapper> list;

	@Autowired
	private SmsClient smsClient;

	@Autowired
	private RootSmsPort rootSmsPort;

	public void setSmsPortMap(
			LinkedHashMap<String, SmsPortProcessAble> smsPortMap) {
		this.smsPortMap = smsPortMap;
	}

	public void processSms(ReceivedSms receivedSms) {
		String s = receivedSms.getContent().trim().replaceAll("＃", "#")
				.replaceAll("；", ";").replaceAll("：", ":").replaceAll("　", " ")
				.replaceAll("＠", "@").replaceAll("，", ",").replaceAll("。", ".")
				.replaceAll("【", "\\[").replaceAll("】", "\\]").replaceAll("［",
						"\\[").replaceAll("］", "\\]");
		if (!DataUtil.isEmpty(s)) {
			if (s.charAt(0) == '　') {
				s = s.replaceFirst("　", "");
			}
			if (s.charAt(s.length() - 1) == '　') {
				s = s.substring(0, s.length() - 1);
			}
			receivedSms.setContent(s);
		}
		SmsPortProcessAble smsPortProcessAble = null;
		if (DataUtil.isEmpty(receivedSms.getPort())) {
			smsPortProcessAble = this.rootSmsPort;
		}
		else {
			smsPortProcessAble = this.getSmsPortProcessAble(receivedSms
					.getPort());
		}
		if (smsPortProcessAble == null) {
			log.error("no sms port [ " + receivedSms.getPort()
					+ " ] was config");
			return;
		}
		BaseCmd cmd = smsPortProcessAble.getCmd(receivedSms.getContent());
		if (cmd == null) {
			log.error("no suitable cmd was config.content [ "
					+ receivedSms.getContent() + " ] smsPort [ "
					+ smsPortProcessAble.getClass().getName() + " ]");
			return;
		}
		try {
			cmd.execute(receivedSms, smsPortProcessAble);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<ReceivedSms> readMoSms() {
		try {
			List<ReceivedSms> list = this.smsClient.receive();
			return list;
		}
		catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	public void invoke() {
		List<ReceivedSms> list = this.readMoSms();
		if (list != null) {
			for (ReceivedSms sms : list) {
				this.processSms(sms);
			}
		}
	}

	private SmsPortProcessAble getSmsPortProcessAble(String port) {
		// return this.smsPortMap.get(port);
		for (PortWrapper o : list) {
			if (o.isStartWithKey()) {
				if (port.startsWith(o.getKey())) {
					return o.getSmsPortProcessAble();
				}
			}
			else {
				if (o.getKey().endsWith(port)) {
					return o.getSmsPortProcessAble();
				}
			}
		}
		return null;
	}

	public void afterPropertiesSet() throws Exception {
		list = new ArrayList<PortWrapper>();
		Set<Entry<String, SmsPortProcessAble>> set = smsPortMap.entrySet();
		for (Entry<String, SmsPortProcessAble> e : set) {
			PortWrapper o = new PortWrapper();
			int idx = e.getKey().indexOf("*");
			if (idx != -1) {
				o.setKey(e.getKey().substring(0, idx));
				o.setStartWithKey(true);
			}
			else {
				o.setKey(e.getKey());
				o.setStartWithKey(false);
			}
			o.setSmsPortProcessAble(e.getValue());
			list.add(o);
		}
	}

	static class PortWrapper {
		public String key;

		private boolean startWithKey;

		private SmsPortProcessAble smsPortProcessAble;

		public void setSmsPortProcessAble(SmsPortProcessAble smsPortProcessAble) {
			this.smsPortProcessAble = smsPortProcessAble;
		}

		public SmsPortProcessAble getSmsPortProcessAble() {
			return smsPortProcessAble;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}

		public void setStartWithKey(boolean startWithKey) {
			this.startWithKey = startWithKey;
		}

		public boolean isStartWithKey() {
			return startWithKey;
		}
	}
}