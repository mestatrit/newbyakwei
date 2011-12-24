package com.hk.web.pub.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.UserService;
import com.hk.svr.UserSmsService;

/**
 * 接收短信发送状态，更改短信发送状态
 * 
 * @author akwei
 */
@Component("/smsreport")
public class SmsReportAction extends BaseAction {
	private final Log log = LogFactory.getLog(SmsReportAction.class);

	@Autowired
	private UserSmsService userSmsService;

	@Autowired
	private UserService userService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		log.info("hksmsreport receive ============");
		long msgId = req.getLong("msgId");
		byte state = req.getByte("state");
		String mobile = req.getString("mobile");
		String statemsg = req.getString("statemsg");
		if (DataUtil.isEmpty(mobile)) {
			return null;
		}
		UserOtherInfo info = this.userService.getUserOtherInfoByMobile(mobile);
		if (info == null) {
			log.warn("no mobile user [ " + mobile + " ]");
			return null;
		}
		this.userSmsService.processUpdateUserSms(msgId, info.getUserId(),
				state, statemsg);
		return null;
	}
}