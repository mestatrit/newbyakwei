package com.hk.web.laba.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.Laba;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.LabaService;
import com.hk.svr.UserService;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.pub.action.LabaVo;
import com.hk.web.util.HkWebUtil;

/**
 * 用户的喇叭列表
 * 
 * @author akwei
 */
@Component("/laba/userlabalist")
public class UserLabaListAction extends BaseAction {

	private int size = 20;

	@Autowired
	private UserService userService;

	@Autowired
	private LabaService labaService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		User loginUser = this.getLoginUser(req);
		if (userId == 0 && loginUser != null) {
			userId = loginUser.getUserId();
		}
		if (userId == 0) {
			return null;
		}
		User user = userService.getUser(userId);
		SimplePage page = req.getSimplePage(size);
		List<Laba> list = labaService.getLabaListByUserId(userId, page
				.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		List<LabaVo> labavolist = LabaVo.createVoList(list, this
				.getLabaParserCfg(req));
		req.setAttribute("userId", userId);
		req.setAttribute("user", user);
		req.setAttribute("labavolist", labavolist);
		req.setAttribute("userlabalist", true);
		req.setAttribute(HkWebUtil.SHOW_MENU, true);
		return "/WEB-INF/page/laba/userlabalist.jsp";
	}
}