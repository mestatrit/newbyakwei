package com.hk.svr.pub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.InitializingBean;
import com.hk.bean.Template;
import com.hk.bean.TmlModule;

public class TemplateUtil implements InitializingBean {
	private static List<Template> templateList;

	private static final Map<Integer, Template> templateMap = new HashMap<Integer, Template>();

	private static final Map<Integer, List<Template>> kindTemplateListMap = new HashMap<Integer, List<Template>>();

	private static final Map<Integer, TmlModule> tmlModuleMap = new HashMap<Integer, TmlModule>();

	private static final Set<Integer> kindIdSet = new HashSet<Integer>();

	public void setTemplateList(List<Template> templateList) {
		TemplateUtil.templateList = templateList;
	}

	public static Template geTemplate(int templateId) {
		return templateMap.get(templateId);
	}

	public static List<Template> getTemplateListByKindId(int kindId) {
		return kindTemplateListMap.get(kindId);
	}

	public static TmlModule geTmlModule(int moduleId) {
		return tmlModuleMap.get(moduleId);
	}

	public void afterPropertiesSet() throws Exception {
		for (Template t : templateList) {
			kindIdSet.add(t.getKindId());
			templateMap.put(t.getTemplateId(), t);
			for (TmlModule o : t.getTmlModuleList()) {
				tmlModuleMap.put(o.getModuleId(), o);
			}
		}
		for (Integer i : kindIdSet) {
			List<Template> list = new ArrayList<Template>();
			for (Template t : templateList) {
				if (t.getKindId() == i.intValue()) {
					list.add(t);
				}
			}
			kindTemplateListMap.put(i, list);
		}
	}
}