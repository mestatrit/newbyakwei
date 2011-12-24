package com.hk.sms.action;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.ReceivedSms;
import com.hk.sms2.SmsJob2;
import com.hk.web.pub.action.BaseAction;

@Component("/pub/smsmock")
public class SmsMockAction extends BaseAction {

	@Autowired
	private SmsJob2 smsJob2;

	public String execute(HkRequest req, HkResponse resp) {
		return "/WEB-INF/page/pub/smsmock.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String process(HkRequest req, HkResponse resp) {
		String port = req.getString("port", "");
		String mobile = req.getString("mobile");
		String content = req.getString("content");
		String spNumber = "1066916025";
		Date createTime = new Date();
		ReceivedSms o = new ReceivedSms();
		o.setPort(port);
		o.setMobile(mobile);
		o.setContent(content);
		o.setCreateTime(createTime);
		o.setSpNumber(spNumber + port);// 模拟长号码
		smsJob2.processSms(o);
		req.setSessionMessage("短信处理成功");
		return "r:/pub/smsmock.do";
	}
}