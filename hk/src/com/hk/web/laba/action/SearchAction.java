package com.hk.web.laba.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Laba;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.LabaService;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.pub.action.LabaVo;

/**
 * 搜索喇叭，使用lucene
 * 
 * @author akwei
 */
@Component("/laba/search")
public class SearchAction extends BaseAction {
	private int size = 21;

	@Autowired
	private LabaService labaService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("sfrom");
		String sw = req.getString("sw", "");
		sw = sw.replaceAll("\\+", "").replaceAll("&", "");
		if (isEmpty(sw)) {
			return "/WEB-INF/page/laba/search.jsp";
		}
		SimplePage page = req.getSimplePage(size);
		List<Laba> list = labaService.getLabaListForSearch(sw, page.getBegin(),
				size);
		page.setListSize(list.size());
		List<LabaVo> labavolist = LabaVo.createVoList(list, this
				.getLabaParserCfg(req));
		req.setAttribute("labavolist", labavolist);
		req.setEncodeAttribute("sw", sw);
		return "/WEB-INF/page/laba/search.jsp";
	}
}