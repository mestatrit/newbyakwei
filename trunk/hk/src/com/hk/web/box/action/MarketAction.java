package com.hk.web.box.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.Box;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BoxService;
import com.hk.web.pub.action.BaseAction;

@Component("/box/market")
public class MarketAction extends BaseAction {

	@Autowired
	private BoxService boxService;

	private int size = 21;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		int cityId = req.getIntAndSetAttr("cityId");
		int c = req.getInt("c");
		if (c == 1) {
			cityId = this.getPcityId(req);
			req.setAttribute("cityId", cityId);
		}
		SimplePage page = req.getSimplePage(size);
		List<Box> list = null;
		if (cityId > 0) {
			list = this.boxService.getCanOpenBoxListByCityId(cityId, (byte) -1,
					page.getBegin(), page.getSize() + 1);
		}
		else {
			list = this.boxService.getCanOpenBoxListByNoCity((byte) -1, page
					.getBegin(), page.getSize() + 1);
		}
		if (cityId > 0 && list.size() == 0 && req.getInt("auto") == 1) {
			return "r:/box/market.do";
		}
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.setReturnUrl("/box/market.do?c=1");
		return this.getWapJsp("box/market.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String notbegin(HkRequest req, HkResponse resp) throws Exception {
		SimplePage page = req.getSimplePage(size);
		// List<OpenBox> list = this.boxService.getNotBeginOpenBoxList(page
		// .getBegin(), size);
		List<Box> list = this.boxService.getNotBeginBoxList(0, null, page
				.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return "/WEB-INF/page/box/notbegin.jsp";
	}
}