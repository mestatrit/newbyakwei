package com.hk.web.user.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.Impression;
import com.hk.bean.User;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.ImpressionService;
import com.hk.svr.UserService;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.HkWebConfig;

@Component("/impression/op/op")
public class OpImpressionAction extends BaseAction {
	@Autowired
	private ImpressionService impressionService;

	@Autowired
	private UserService userService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 删除自己发的评价
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long prouserId = req.getLong("prouserId");
		long oid = req.getLong("oid");
		Impression impression = this.impressionService.getImpression(oid);
		if (impression != null) {
			if (impression.getSenderId() == this.getLoginUser(req).getUserId()) {
				this.impressionService.deleteImpression(impression.getOid());
				req.setSessionText("op.exeok");
			}
		}
		return "r:/prouser.do?prouserId=" + prouserId;
	}

	/**
	 * 写对某人的评价
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String add(HkRequest req, HkResponse resp) throws Exception {
		long prouserId = req.getLong("prouserId");
		User loginUser = this.getLoginUser(req);
		String content = req.getString("content");
		req.setAttribute("content", content);
		int code = Impression.validateContent(content);
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/prouser.do?prouserId=" + prouserId;
		}
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		Impression o = new Impression();
		o.setProuserId(prouserId);
		o.setSenderId(loginUser.getUserId());
		o.setContent(labaInfo.getParsedContent());
		if (this.impressionService.createImpression(o)) {
			this.userService.createWelProUser(loginUser.getUserId(), prouserId);
			req.setSessionText("op.submitinfook");
		}
		else {
			req.setSessionText("func.impression.already_exist");
		}
		return "r:/prouser.do?prouserId=" + prouserId;
	}
}