package com.hk.sms2.smsport;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import com.hk.frame.util.DataUtil;
import com.hk.sms.cmd.BaseCmd;
import com.hk.sms2.SmsPortProcessAble;

public class DefSmsPortProcessAble extends SmsPortProcessAble implements
		InitializingBean {
	private static final Set<String> smsKeySet = new HashSet<String>();

	private LinkedHashMap<String, BaseCmd> cmdMap;

	private final Log log = LogFactory.getLog(DefSmsPortProcessAble.class);

	public final void setCmdMap(LinkedHashMap<String, BaseCmd> cmdMap) {
		this.cmdMap = cmdMap;
	}

	public LinkedHashMap<String, BaseCmd> getCmdMap() {
		return cmdMap;
	}

	@Override
	public BaseCmd getCmd(String content) {
		Set<Entry<String, BaseCmd>> set = cmdMap.entrySet();
		for (Entry<String, BaseCmd> e : set) {
			if (e.getKey().equals("*") || DataUtil.isEmpty(content)) {
				return e.getValue();
			}
			String[] cmdStr = e.getKey().split(":");
			if (cmdStr[0].equals("startwith")) {
				if (content.toLowerCase().startsWith(cmdStr[1])) {
					log.info("cmdStr [ " + cmdStr[0] + "," + cmdStr[1] + " ]");
					return e.getValue();
				}
				continue;
			}
			if (content.toLowerCase().equals(e.getKey())) {
				return e.getValue();
			}
			return null;
		}
		return null;
	}

	public void afterPropertiesSet() throws Exception {
		Set<String> keys = this.getCmdMap().keySet();
		for (String s : keys) {
			String[] cmdStr = s.split(":");
			if (cmdStr.length == 1) {
				smsKeySet.add(s);
			}
			if (cmdStr[0].equals("startwith")) {
				smsKeySet.add(cmdStr[1]);
			}
		}
	}
}