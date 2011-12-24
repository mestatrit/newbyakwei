package com.hk.web.bomb.action;

import org.springframework.stereotype.Component;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.web.pub.action.BaseAction;

/**
 * 已废除
 * 
 * @author yuanwei
 */
@Component("/loginbomb/bomb")
public class LoginBombAction extends BaseAction {
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return "/WEB-INF/page/bomb/login.jsp";
	}
	// /**
	// * @param req
	// * @param resp
	// * @return
	// * @throws Exception
	// */
	// public String login(HkRequest req, HkResponse resp) throws Exception {
	// String pwd = req.getString("pwd");
	// if (isEmpty(pwd)) {
	// req.setSessionMessage("请输入密码");
	// return "r:/loginbomb/bomb.do";
	// }
	// User user = this.getLoginUser(req);
	// boolean status = this.bombService.login(user.getUserId(), pwd);
	// if (status) {
	// req.setSessionValue(HkWebUtil.BOMBER_SYS_LOGIN, status);
	// Bomber bomber = this.bombService.getBomber(user.getUserId());
	// if (bomber.getUserLevel() != Bomber.USERLEVEL_NORMAL) {
	// return "r:/adminbomb/bomb.do";
	// }
	// return "r:/bomb/bomb_list.do";
	// }
	// req.setSessionMessage("密码错误");
	// return "r:/loginbomb/bomb.do";
	// }
}