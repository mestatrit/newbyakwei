package com.hk.api.action.laba;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.api.util.SessionKey;
import com.hk.bean.ApiUser;
import com.hk.bean.Laba;
import com.hk.bean.RefLaba;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.LabaService;
import com.hk.svr.UserService;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.laba.parser.LabaOutPutParser;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.web.pub.action.LabaParserCfg;
import com.hk.web.pub.action.LabaVo;
import com.hk.web.util.HkWebConfig;

// @Component("/pubapi/protect/laba")
public class ProtectLabaAction extends BaseApiAction {

	@Autowired
	private LabaService labaService;

	@Autowired
	private UserService userService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return null;
	}

	public String delete(HkRequest req, HkResponse resp) {
		SessionKey o = this.getSessionKey(req);
		long labaId = req.getLong("labaId");
		Laba laba = this.labaService.getLaba(labaId);
		if (laba != null && laba.getUserId() == o.getUserId()) {
			VelocityContext context = new VelocityContext();
			labaService.removeLaba(o.getUserId(), labaId, false);
			context.put("code", Err.SUCCESS);
			context.put("status", "ok");
			context.put("deletelaba", true);
			this.write(resp, "vm/validate.vm", context);
		}
		else {
			APIUtil.sendRespStatus(resp, "fail", Err.NOOBJECT_ERROR);
		}
		return null;
	}

	public String share(HkRequest req, HkResponse resp) {
		SessionKey o = this.getSessionKey(req);
		long labaId = req.getLong("labaId");
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		if (laba.getRefLabaId() > 0) {
			laba = this.labaService.getLaba(laba.getRefLabaId());
		}
		// 自己不能转发自己的喇叭
		if (o.getUserId() == laba.getUserId()) {
			APIUtil.sendFailRespStatus(resp, Err.LABA_CAN_NOT_REPLY_YOURSELF);
			return null;
		}
		RefLaba refLaba = this.labaService.getRefLaba(laba.getLabaId(), o
				.getUserId());
		if (refLaba != null) {
			APIUtil.sendFailRespStatus(resp, Err.LABA_REFLABA_EXIST);
			return null;
		}
		User user = this.userService.getUser(laba.getUserId());
		LabaOutPutParser labaOutPutParser = new LabaOutPutParser();
		String content = DataUtil.toText(labaOutPutParser.getText(laba
				.getContent()));
		content = "转@" + user.getNickName() + ": " + content;
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setAddLabaTagRef(false);// 转载的喇叭，如果有标签，不把标签加到喇叭标签的关系表
		labaInfo.setRefLabaId(laba.getLabaId());// 转发的喇叭
		String ip = req.getString("ip");
		if (DataUtil.validateIp(ip)) {
			labaInfo.setIp(ip);
		}
		labaInfo.setUserId(o.getUserId());
		ApiUser apiUser = this.getApiUser(req);
		labaInfo.setSendFrom(apiUser.getApiUserId());
		long newLabaId = this.labaService.createLaba(labaInfo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("labaId", newLabaId);
		map.put("sharelaba", true);
		APIUtil.sendSuccessRespStatus(resp, map);
		return null;
	}

	/**
	 * 发喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) {
		SessionKey o = this.getSessionKey(req);
		VelocityContext context = new VelocityContext();
		if (HkSvrUtil.isNotUser(o.getUserId())) {// 无法识别的用户
			context.put("status", "fail");
			context.put("code", Err.USERID_ERROR + "");
			context.put("msg", ResourceConfig.getText(Err.USERID_ERROR + ""));
			this.write(resp, "vm/validate.vm", context);
			return null;
		}
		String content = req.getString("content");
		String ip = req.getString("ip");
		// long replyLabaId = req.getLong("replyLabaId");
		// Laba laba2 = null;
		// if (replyLabaId > 0) {
		// laba2 = this.labaService.getLaba(replyLabaId);
		// if (laba2 == null) {
		// replyLabaId = 0;
		// }
		// else if (laba2.getUserId() == o.getUserId()) {
		// replyLabaId = 0;
		// }
		// }
		int code = Laba.validateContent(content);
		if (code != Err.SUCCESS) {// 内容交验错误
			context.put("msg", ResourceConfig.getText(code + ""));
			context.put("code", code + "");
			context.put("status", "fail");
			this.write(resp, "vm/validate.vm", context);
			return null;
		}
		// content = DataUtil.toHtmlRow(content);
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		if (DataUtil.validateIp(ip)) {
			labaInfo.setIp(ip);
		}
		// labaInfo.setReplyLabaId(replyLabaId);
		labaInfo.setUserId(o.getUserId());
		ApiUser apiUser = this.getApiUser(req);
		labaInfo.setSendFrom(apiUser.getApiUserId());
		long labaId = this.labaService.createLaba(labaInfo);
		Laba laba = this.labaService.getLaba(labaId);
		LabaParserCfg cfg = this.getApiLabaParserCfg();
		cfg.setUserId(o.getUserId());
		LabaVo vo = LabaVo.create(laba, cfg);
		context.put("code", Err.SUCCESS);
		context.put("status", "ok");
		context.put("labacreate", true);
		context.put("vo", vo);
		this.write(resp, "vm/validate.vm", context);
		return null;
	}

	/**
	 * 好友喇叭输出
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String friend(HkRequest req, HkResponse resp) throws Exception {
		int size = this.getSize(req);
		SessionKey o = this.getSessionKey(req);
		SimplePage page = req.getSimplePage(size);
		List<Laba> list = labaService.getLabaListForFollowByUserId(o
				.getUserId(), page.getBegin(), size);
		LabaParserCfg cfg = this.getApiLabaParserCfg();
		cfg.setUserId(o.getUserId());
		List<LabaVo> labalist = LabaVo.createVoList(list, cfg);
		VelocityContext context = new VelocityContext();
		context.put("labalist", labalist);
		this.write(resp, "vm/laba/labalist.vm", context);
		return null;
	}
}