package com.hk.web.box.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.AdminUser;
import com.hk.bean.Box;
import com.hk.bean.BoxPrize;
import com.hk.bean.UserBoxOpen;
import com.hk.bean.UserBoxPrize;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BoxService;
import com.hk.svr.UserService;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.web.pub.action.BaseAction;

@Component("/box/box")
public class BoxAction extends BaseAction {

	@Autowired
	private BoxService boxService;

	@Autowired
	private UserService userService;

	private int size = 20;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long boxId = req.getLong("boxId");
		Box box = boxService.getBox(boxId);
		if (box == null) {
			return null;
		}
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
		List<BoxPrize> list = this.boxService.getBoxPrizeListByBoxId(boxId);
		req.setAttribute("end", end);
		req.setAttribute("stop", stop);
		req.setAttribute("begin", begin);
		req.setAttribute("smsandweb", smsandweb);
		req.setAttribute("onlysmsopen", onlysmsopen);
		req.setAttribute("list", list);
		req.setAttribute("boxId", boxId);
		req.setAttribute("box", box);
		req.reSetAttribute("t");
		req.reSetAttribute("repage");
		return "/WEB-INF/page/box/box.jsp";
	}

	/**
	 * 所有开始的和已经结束的箱子列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		AdminUser adminUser = this.userService.getAdminUser(this.getLoginUser(
				req).getUserId());
		SimplePage page = req.getSimplePage(size);
		List<Box> list = null;
		if (adminUser == null) {
			list = this.boxService.getBeginBoxList(this.getLoginUser(req)
					.getUserId(), null, page.getBegin(), size);
		}
		else {
			list = this.boxService.getBeginBoxList(0, null, page.getBegin(),
					size);
		}
		page.setListSize(list.size());
		req.setAttribute("list", list);
		return "/WEB-INF/page/box/list.jsp";
	}

	/**
	 * 开箱记录
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String userlist(HkRequest req, HkResponse resp) throws Exception {
		long boxId = req.getLong("boxId");
		Box box = this.boxService.getBox(boxId);
		if (!this.hasBoxPower(box, req)) {
			return "r:/more.do";
		}
		SimplePage page = req.getSimplePage(size);
		List<UserBoxOpen> list = boxService.getUserBoxOpenListByBoxId(boxId,
				page.getBegin(), size);
		page.setListSize(list.size());
		req.setAttribute("serviceUserId", HkSvrUtil.getServiceUserId());
		req.setAttribute("box", box);
		req.setAttribute("boxId", boxId);
		req.setAttribute("list", list);
		req.reSetAttribute("repage");
		return "/WEB-INF/page/box/userlist.jsp";
	}

	/**
	 * 中奖开箱记录
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String awarduserlist(HkRequest req, HkResponse resp)
			throws Exception {
		long boxId = req.getLong("boxId");
		Box box = this.boxService.getBox(boxId);
		if (!this.hasBoxPower(box, req)) {
			return "r:/more.do";
		}
		SimplePage page = req.getSimplePage(size);
		List<UserBoxPrize> list = boxService.getUserBoxPrizeListByBoxId(boxId,
				page.getBegin(), size);
		page.setListSize(list.size());
		req.setAttribute("serviceUserId", HkSvrUtil.getServiceUserId());
		req.setAttribute("box", box);
		req.setAttribute("boxId", boxId);
		req.setAttribute("list", list);
		req.reSetAttribute("repage");
		return "/WEB-INF/page/box/awarduserlist.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String back(HkRequest req, HkResponse resp) throws Exception {
		String t = req.getString("t");
		int repage = req.getInt("repage");
		if (t == null) {
			return "r:/box/market.do?auto=1&cityId=" + this.getPcityId(req)
					+ "&page=" + repage;
		}
		if (t.equals("notbegin")) {
			return "r:/box/market_notbegin.do?page=" + repage;
		}
		return null;
	}
}