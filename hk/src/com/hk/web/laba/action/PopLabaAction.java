package com.hk.web.laba.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Laba;
import com.hk.bean.RespLaba;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.LabaService;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.pub.action.LabaVo;

/**
 * 所有流行的喇叭
 * 
 * @author akwei
 */
@Component("/laba/poplaba")
public class PopLabaAction extends BaseAction {
	@Autowired
	private LabaService labaService;

	private int size = 20;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		SimplePage page = req.getSimplePage(size);
		List<RespLaba> list = labaService
				.getRespLabaList(page.getBegin(), size);
		List<Laba> labalist = new ArrayList<Laba>();
		for (RespLaba o : list) {
			labalist.add(o.getLaba());
		}
		List<LabaVo> labavolist = LabaVo.createVoList(labalist, this
				.getLabaParserCfg(req));
		req.setAttribute("labavolist", labavolist);
		return "/WEB-INF/page/laba/poplaba.jsp";
	}
}