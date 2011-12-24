package com.hk.svr.pub;

import java.util.List;
import java.util.Map;

import com.hk.bean.CmpInfoTml;

public class CmpInfoTmlUtil {

	private static Map<Integer, List<CmpInfoTml>> map;

	public void setMap(Map<Integer, List<CmpInfoTml>> map) {
		CmpInfoTmlUtil.map = map;
	}

	public static List<CmpInfoTml> getCmpInfoTmlByCmpflg(int cmpflg) {
		return map.get(cmpflg);
	}
}