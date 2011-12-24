package com.hk.web.user.action;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.UserService;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.user.UserVo;
import com.hk.web.user.UserVoBuilder;

@Component("/user/search")
public class SearchAction extends BaseAction {
	@Autowired
	private UserService userService;

	private int size = 20;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		req.reSetAttribute("sfrom");
		String sw = req.getString("sw", "");
		sw = sw.replaceAll("\\+", "").replaceAll("&", "");
		if (isEmpty(sw)) {
			return "/WEB-INF/page/user/search.jsp";
		}
		SimplePage page = req.getSimplePage(size);
		List<User> list = this.userService.getUserListForSearch(sw, page
				.getBegin(), size);
		List<UserVo> uservolist = new ArrayList<UserVo>();
		for (User u : list) {
			UserVo o = new UserVo();
			o.setUser(this.userService.getUser(u.getUserId()));
			uservolist.add(o);
		}
		page.setListSize(uservolist.size());
		UserVoBuilder builder = new UserVoBuilder();
		builder.setVolist(uservolist);
		builder.setLabaParserCfg(this.getLabaParserCfg(req));
		builder.setNeedFriend(true);
		builder.setLoginUser(loginUser);
		builder.setNeedLaba(true);
		builder.setNeedFansCount(true);
		UserVo.buildUserVoInfo(builder);
		req.setAttribute("uservobuilder", builder);
		req.setAttribute("uservolist", uservolist);
		req.setEncodeAttribute("sw", sw);
		return "/WEB-INF/page/user/search.jsp";
	}
}