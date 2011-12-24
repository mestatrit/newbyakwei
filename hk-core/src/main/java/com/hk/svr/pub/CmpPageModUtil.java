package com.hk.svr.pub;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.hk.bean.CmpMod;
import com.hk.bean.CmpPageMod;

public class CmpPageModUtil implements InitializingBean {

	public static Map<String, CmpMod> cmpModMap;

	public static Map<Integer, CmpPageMod> cmpPageModMap;

	public void setCmpModMap(Map<String, CmpMod> cmpModMap) {
		CmpPageModUtil.cmpModMap = cmpModMap;
	}

	public static CmpPageMod getCmpPageMod(int pageModId) {
		return cmpPageModMap.get(pageModId);
	}

	public static List<CmpPageMod> getHomeCmpPageModListByCmpflgAndTmlflg(
			byte cmpflg, int tmlflg) {
		CmpMod cmpMod = cmpModMap.get(cmpflg + "" + tmlflg);
		if (cmpMod == null) {
			return null;
		}
		return cmpMod.getHomePageModList();
	}

	public void afterPropertiesSet() throws Exception {
		cmpPageModMap = new HashMap<Integer, CmpPageMod>();
		Collection<CmpMod> c = cmpModMap.values();
		for (CmpMod mod : c) {
			if (mod.getHomePageModList() != null) {
				for (CmpPageMod o : mod.getHomePageModList()) {
					cmpPageModMap.put(o.getPageModId(), o);
				}
			}
			if (mod.getSecondPageModList() != null) {
				for (CmpPageMod o : mod.getSecondPageModList()) {
					cmpPageModMap.put(o.getPageModId(), o);
				}
			}
			if (mod.getThirdPageModList() != null) {
				for (CmpPageMod o : mod.getThirdPageModList()) {
					cmpPageModMap.put(o.getPageModId(), o);
				}
			}
		}
	}
}