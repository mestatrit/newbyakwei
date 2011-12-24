package com.hk.web.act.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.Act;
import com.hk.bean.ActSysNum;
import com.hk.bean.ActUser;
import com.hk.bean.InfoSmsPort;
import com.hk.bean.User;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.SmsClient;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.ActService;
import com.hk.svr.ActSysNumService;
import com.hk.svr.InfoSmsPortService;
import com.hk.svr.UserService;
import com.hk.web.pub.action.BaseAction;

@Component("/act/act")
public class ActAction extends BaseAction {
	@Autowired
	private ActService actService;

	@Autowired
	private UserService userService;

	@Autowired
	private ActSysNumService actSysNumService;

	@Autowired
	private InfoSmsPortService infoSmsPortService;

	@Autowired
	private SmsClient smsClient;

	private int size = 20;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLong("actId");
		Act act = this.actService.getAct(actId);
		if (act == null) {
			return null;
		}
		List<ActUser> userlist = this.actService.getActUserList(actId, 0, 6);
		boolean showmore = false;
		if (userlist.size() == 6) {
			showmore = true;
			userlist.remove(5);
		}
		List<Long> idList = new ArrayList<Long>();
		for (ActUser o : userlist) {
			idList.add(o.getUserId());
		}
		Map<Long, User> map = this.userService.getUserMapInId(idList);
		for (ActUser o : userlist) {
			o.setUser(map.get(o.getUserId()));
		}
		boolean adminact = false;
		User loginUser = this.getLoginUser(req);
		if (loginUser != null && loginUser.getUserId() == act.getUserId()) {
			adminact = true;
		}
		ActSysNum actSysNum = this.actSysNumService.getActSysNum(act
				.getActSysNumId());
		InfoSmsPort infoSmsPort = this.infoSmsPortService.getInfoSmsPort(act
				.getPortId());
		SmsPortProcessAble userinfomation_smsport = (SmsPortProcessAble) HkUtil
				.getBean("userinfomation_smsport");
		String allport = this.smsClient.getSmsConfig().getSpNumber()
				+ userinfomation_smsport.getBaseSmsPort()
				+ infoSmsPort.getPortNumber();
		req.setAttribute("allport", allport);
		req.setAttribute("infoSmsPort", infoSmsPort);
		req.setAttribute("actSysNum", actSysNum);
		req.setAttribute("adminact", adminact);
		req.setAttribute("actId", actId);
		req.setAttribute("showmore", showmore);
		req.setAttribute("act", act);
		req.setAttribute("userlist", userlist);
		return "/WEB-INF/page/act/act.jsp";
	}

	/**
	 * 用户参加的活动
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String useract(HkRequest req, HkResponse resp) throws Exception {
		SimplePage page = req.getSimplePage(size);
		User loginUser = this.getLoginUser(req);
		long userId = loginUser.getUserId();
		List<Long> actIdList = this.actService.getActIdListByJoinUserId(userId,
				page.getBegin(), size);
		page.setListSize(actIdList.size());
		Map<Long, Act> map = this.actService.getActMapInId(actIdList);
		List<Act> actList = new ArrayList<Act>();
		for (Long l : actIdList) {
			actList.add(map.get(l));
		}
		req.setAttribute("list", actList);
		return "/WEB-INF/page/act/useract.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String user(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLong("actId");
		Act act = this.actService.getAct(actId);
		if (act == null) {
			return null;
		}
		SimplePage page = req.getSimplePage(size);
		List<ActUser> list = this.actService.getActUserList(actId, page
				.getBegin(), size);
		page.setListSize(list.size());
		List<Long> idList = new ArrayList<Long>();
		for (ActUser o : list) {
			idList.add(o.getUserId());
		}
		Map<Long, User> usermap = this.userService.getUserMapInId(idList);
		for (ActUser o : list) {
			o.setUser(usermap.get(o.getUserId()));
		}
		boolean adminact = false;
		User loginUser = this.getLoginUser(req);
		if (loginUser != null && loginUser.getUserId() == act.getUserId()) {
			adminact = true;
		}
		req.setAttribute("adminact", adminact);
		req.setAttribute("actId", actId);
		req.setAttribute("act", act);
		req.setAttribute("list", list);
		return "/WEB-INF/page/act/user.jsp";
	}
}