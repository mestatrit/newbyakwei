package com.hk.web.pub.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.SmsClient;

@Component("/admin/smssend")
public class MgrSmsAction extends BaseAction {
	@Autowired
	private SmsClient smsClient;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return "/WEB-INF/page/admin/sms.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String send(HkRequest req, HkResponse resp) {
		String mobile = req.getString("mobile");
		String content = req.getString("content");
		try {
			this.smsClient.send(mobile, content);
			req.setSessionMessage("发送成功");
		}
		catch (Exception e) {
			req.setSessionMessage("发送失败");
		}
		return "r:/admin/smssend.do";
	}
}