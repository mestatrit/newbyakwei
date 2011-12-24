package com.hk.sms.cmd;

import java.util.ArrayList;
import java.util.List;

public class CmdFactory {
	private CmdFactory() {//
	}

	/**
	 * 是否包含短信关键字
	 * 
	 * @param key
	 * @return
	 */
	public static boolean isKey(String key) {
		if (key.startsWith("yz") || key.startsWith("bd")
				|| key.startsWith("xm")) {
			return true;
		}
		return false;
	}

	public static List<String> getKeyList() {
		List<String> list = new ArrayList<String>();
		list.add("yz");
		list.add("bd");
		list.add("xm");
		list.add("yu");
		list.add("#@");
		return list;
	}
}