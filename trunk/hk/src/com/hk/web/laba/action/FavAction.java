package com.hk.web.laba.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.FavLaba;
import com.hk.bean.Laba;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.LabaService;
import com.hk.svr.UserService;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.pub.action.LabaVo;

/**
 * 个人收藏的喇叭
 * 
 * @author akwei
 */
@Component("/laba/fav")
public class FavAction extends BaseAction {
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
		List<FavLaba> list = labaService.getFavLabaListByUserId(userId, page
				.getBegin(), size);
		page.setListSize(list.size());
		List<Laba> labalist = new ArrayList<Laba>();
		for (FavLaba o : list) {
			labalist.add(o.getLaba());
		}
		List<LabaVo> labavolist = LabaVo.createVoList(labalist, this
				.getLabaParserCfg(req));
		req.setAttribute("userId", userId);
		req.setAttribute("user", user);
		req.setAttribute("labavolist", labavolist);
		return "/WEB-INF/page/laba/fav.jsp";
	}
}