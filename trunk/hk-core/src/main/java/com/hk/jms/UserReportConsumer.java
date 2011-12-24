package com.hk.jms;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.JsonKey;
import com.hk.frame.util.JsonUtil;
import com.hk.svr.Tb_UserItemReportService;

public class UserReportConsumer {

	@Autowired
	private Tb_UserItemReportService tbUserItemReportService;

	private final Log log = LogFactory.getLog(UserReportConsumer.class);

	public void processMessage(String value) {
		JmsMsg jmsMsg = new JmsMsg(value);
		if (jmsMsg.getHead().equals(JmsMsg.HEAD_USER_REPORT_ADDITEM)) {
			this.processUserReport(jmsMsg.getBody());
			return;
		}
		log.error("unknown message type [ " + value + " ]");
	}

	private void processUserReport(String body) {
		Map<String, String> map = JsonUtil.getMapFromJson(body);
		long userid = Long.valueOf(map.get(JsonKey.USERID));
		this.tbUserItemReportService.saveTb_UserItemDailyReport(userid, 1,
				new Date());
	}
}