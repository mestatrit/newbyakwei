package com.hk.web.info.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.Information;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.SmsClient;
import com.hk.svr.InformationService;
import com.hk.svr.UserService;
import com.hk.web.pub.action.BaseAction;

@Component("/info/admin/admin")
public class AdminInfoAction extends BaseAction {
	@Autowired
	private InformationService informationService;

	@Autowired
	private UserService userService;

	@Autowired
	private SmsClient smsClient;

	private int size = 20;

	private final Log log = LogFactory.getLog(AdminInfoAction.class);

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		int t = req.getInt("t");
		Calendar now = Calendar.getInstance();
		Calendar date = Calendar.getInstance();
		Date maxTime = null;
		Date minTime = now.getTime();
		if (t == 0) {
			date.add(Calendar.DATE, +7);
		}
		else {
			date.add(Calendar.MONTH, +1);
		}
		maxTime = date.getTime();
		SimplePage page = req.getSimplePage(size);
		List<Information> list = this.informationService
				.getInformationListForEndTime(minTime, maxTime,
						page.getBegin(), size);
		page.setListSize(list.size());
		req.setAttribute("list", list);
		req.setAttribute("t", t);
		return "/WEB-INF/page/info/admin/list.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String smsnotice(HkRequest req, HkResponse resp) {
		long infoId = req.getInt("infoId");
		Information o = this.informationService.getInformation(infoId);
		User user = this.userService.getUser(o.getUserId());
		UserOtherInfo info = this.userService
				.getUserOtherInfo(user.getUserId());
		if (!DataUtil.isEmpty(info.getMobile())) {
			try {
				this.smsClient.send(info.getMobile(), req.getText(
						"func.sendsmsnoticecontent", o.getName()));
				req.setSessionMessage(req.getText("func.sendsmsok"));
			}
			catch (Exception e) {
				req.setSessionMessage(req.getText("func.sendsmsfail"));
				log.error(e.getMessage());
			}
		}
		return "r:/info/admin/admin.do?" + req.getQueryString();
	}
}