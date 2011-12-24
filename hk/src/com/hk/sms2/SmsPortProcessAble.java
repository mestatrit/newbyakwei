package com.hk.sms2;

import com.hk.sms.cmd.BaseCmd;

public abstract class SmsPortProcessAble {
	private String baseSmsPort = "";

	public void setBaseSmsPort(String baseSmsPort) {
		this.baseSmsPort = baseSmsPort;
	}

	public String getBaseSmsPort() {
		return baseSmsPort;
	}

	abstract public BaseCmd getCmd(String content);
}