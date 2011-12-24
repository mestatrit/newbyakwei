package com.hk.web.cmpunion.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Box;
import com.hk.bean.BoxPrize;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BoxService;

@Component("/union/box")
public class BoxAction extends CmpUnionBaseAction {
	@Autowired
	private BoxService boxService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long boxId = req.getLongAndSetAttr("boxId");
		Box box = this.boxService.getBox(boxId);
		req.setAttribute("box", box);
		boolean onlysmsopen = false;
		boolean smsandweb = false;
		boolean begin = false;
		boolean stop = false;
		boolean end = false;
		long nowtime = System.currentTimeMillis();
		if (box.getOpentype() == Box.BOX_OPENTYPE_SMS) {
			onlysmsopen = true;
		}
		else if (box.getOpentype() == Box.BOX_OPENTYPE_SMSANDWEB) {
			smsandweb = true;
		}
		if (box.getBeginTime().getTime() < nowtime) {
			begin = true;
		}
		if (box.getEndTime().getTime() < nowtime) {
			end = true;
		}
		if (box.getBoxStatus() != Box.BOX_STATUS_NORMAL) {
			stop = true;
		}
		List<BoxPrize> prizelist = this.boxService
				.getBoxPrizeListByBoxId(boxId);
		req.setAttribute("end", end);
		req.setAttribute("stop", stop);
		req.setAttribute("begin", begin);
		req.setAttribute("smsandweb", smsandweb);
		req.setAttribute("onlysmsopen", onlysmsopen);
		req.setAttribute("prizelist", prizelist);
		return this.getUnionWapJsp("box/box.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLongAndSetAttr("uid");
		SimplePage page = req.getSimplePage(20);
		List<Box> list = this.boxService.getBoxListByUid(uid, page.getBegin(),
				page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getUnionWapJsp("box/boxlist.jsp");
	}
}