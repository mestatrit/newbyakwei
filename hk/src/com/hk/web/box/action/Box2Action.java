package com.hk.web.box.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Box;
import com.hk.bean.BoxPretype;
import com.hk.bean.BoxPrize;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BoxService;
import com.hk.svr.UserService;
import com.hk.svr.pub.BoxPretypeUtil;
import com.hk.svr.pub.CheckInPointConfig;
import com.hk.web.pub.action.BaseAction;

@Component("/box")
public class Box2Action extends BaseAction {

	@Autowired
	private BoxService boxService;

	@Autowired
	private UserService userService;

	/**
	 * 可开的箱子列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("to_box", true);
		int cityId = req.getIntAndSetAttr("cityId");
		int selcity = req.getInt("selcity");
		if (selcity == 1) {
			cityId = this.getPcityId(req);
			req.setAttribute("cityId", cityId);
		}
		SimplePage page = req.getSimplePage(20);
		List<Box> boxlist = null;
		if (cityId > 0) {
			boxlist = this.boxService.getCanOpenBoxListByCityId(cityId,
					(byte) -1, page.getBegin(), page.getSize() + 1);
		}
		else {
			boxlist = this.boxService.getCanOpenBoxListByNoCity((byte) -1, page
					.getBegin(), page.getSize() + 1);
		}
		if (selcity == 1 && cityId > 0 && boxlist.size() == 0) {
			return "r:/box_list.do";
		}
		this.processListForPage(page, boxlist);
		req.setAttribute("boxlist", boxlist);
		return this.getWeb4Jsp("box/boxlist.jsp");
	}

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long boxId = req.getLongAndSetAttr("boxId");
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
		int piccount = 0;
		for (BoxPrize o : list) {
			if (o.getPath() != null) {
				piccount++;
			}
		}
		BoxPrize firstPrize = null;
		if (list.size() > 0) {
			firstPrize = list.remove(0);
			req.setAttribute("firstPrize", firstPrize);
		}
		req.setAttribute("piccount", piccount);
		BoxPretype boxPretype = BoxPretypeUtil.getBoxPretype(box.getPretype());
		req.setAttribute("boxPretype", boxPretype);
		req.setAttribute("end", end);
		req.setAttribute("stop", stop);
		req.setAttribute("begin", begin);
		req.setAttribute("smsandweb", smsandweb);
		req.setAttribute("onlysmsopen", onlysmsopen);
		req.setAttribute("list", list);
		req.setAttribute("box", box);
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			if (box.getUserId() == loginUser.getUserId() || isAdminUser(req)) {
				req.setAttribute("canadminbox", true);
			}
			UserOtherInfo userOtherInfo = this.userService
					.getUserOtherInfo(loginUser.getUserId());
			int canOpenBoxCount = userOtherInfo.getPoints()
					/ CheckInPointConfig.getOpenBoxPoints();
			req.setAttribute("canOpenBoxCount", canOpenBoxCount);
		}
		return this.getWeb4Jsp("box/box.jsp");
	}
}