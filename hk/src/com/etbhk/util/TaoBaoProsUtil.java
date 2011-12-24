package com.etbhk.util;

import java.util.ArrayList;
import java.util.List;

import com.hk.frame.util.DataUtil;

public class TaoBaoProsUtil {

	/**
	 * 转换商品属性(pid1:vid1:pid_name1:vid_name1;pid2:vid2:pid_name2:vid_name2)
	 * 
	 * @param propsName
	 * @return
	 *         2010-8-31
	 */
	public static List<String> getPropsNameList(String propsName) {
		List<String> list = new ArrayList<String>();
		if (DataUtil.isEmpty(propsName)) {
			return list;
		}
		String[] props = propsName.split(";");
		for (String pv : props) {
			String[] t = pv.split(":");
			if (t.length == 4) {
				list.add(t[2] + "：" + t[3]);
			}
		}
		return list;
	}
}