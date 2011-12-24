package com.hk.web.laba.action;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.Laba;
import com.hk.bean.User;
import com.hk.bean.UserLabaReply;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.LabaService;
import com.hk.svr.UserService;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.pub.action.LabaVo;
import com.hk.web.util.HkWebUtil;

/**
 * 回复某个用户的所有喇叭
 * 
 * @author akwei
 */
@Component("/laba/reuserlist")
public class ReUserListAction extends BaseAction {
	@Autowired
	private UserService userService;

	@Autowired
	private LabaService labaService;

	private int size = 21;

	public String execute(HkRequest request, HkResponse response)
			throws Exception {
		long userId = request.getLong("userId");
		User loginUser = this.getLoginUser(request);
		if (userId == 0 && loginUser != null) {
			userId = loginUser.getUserId();
		}
		SimplePage page = request.getSimplePage(size);
		List<UserLabaReply> list = labaService.getUserLabaReplyList(userId,
				page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		List<Laba> labalist = new ArrayList<Laba>();
		for (UserLabaReply o : list) {
			labalist.add(o.getLaba());
		}
		List<LabaVo> labavolist = LabaVo.createVoList(labalist, this
				.getLabaParserCfg(request));
		User user = userService.getUser(userId);
		request.setAttribute("user", user);
		request.setAttribute("userId", userId);
		request.setAttribute("labavolist", labavolist);
		request.setAttribute("reuserlist", true);
		request.setAttribute(HkWebUtil.SHOW_MENU, true);
		return "/WEB-INF/page/laba/reuserlist.jsp";
	}
}