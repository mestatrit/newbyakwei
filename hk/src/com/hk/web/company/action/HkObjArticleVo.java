package com.hk.web.company.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.hk.bean.HkObj;
import com.hk.bean.HkObjArticle;
import com.hk.frame.util.HkUtil;
import com.hk.svr.CompanyService;

public class HkObjArticleVo {
	private HkObjArticle hkObjArticle;

	private HkObj hkObj;

	public HkObjArticle getHkObjArticle() {
		return hkObjArticle;
	}

	public void setHkObjArticle(HkObjArticle hkObjArticle) {
		this.hkObjArticle = hkObjArticle;
	}

	public HkObj getHkObj() {
		return hkObj;
	}

	public void setHkObj(HkObj hkObj) {
		this.hkObj = hkObj;
	}

	public static List<HkObjArticleVo> createVoList(List<HkObjArticle> list) {
		List<HkObjArticleVo> volist = new ArrayList<HkObjArticleVo>();
		CompanyService companyService = (CompanyService) HkUtil
				.getBean("companyService");
		List<Long> idlist = new ArrayList<Long>();
		for (HkObjArticle o : list) {
			idlist.add(o.getHkObjId());
		}
		Map<Long, HkObj> map = companyService.getHkObjMapInId(idlist);
		for (HkObjArticle o : list) {
			HkObjArticleVo vo = new HkObjArticleVo();
			vo.setHkObjArticle(o);
			vo.setHkObj(map.get(o.getHkObjId()));
			volist.add(vo);
		}
		return volist;
	}
}