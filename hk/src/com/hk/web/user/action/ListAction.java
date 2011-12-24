package com.hk.web.user.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.HkGroup;
import com.hk.bean.IpCityRange;
import com.hk.bean.IpCityRangeUser;
import com.hk.bean.IpCityUser;
import com.hk.bean.IpUser;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.GroupService;
import com.hk.svr.IpCityService;
import com.hk.svr.UserService;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.user.UserVo;
import com.hk.web.user.UserVoBuilder;

@Component("/user/list1")
public class ListAction extends BaseAction {
	@Autowired
	private UserService userService;

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private GroupService groupService;

	private int size = 20;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		String w = req.getString("w", "all");
		User loginUser = this.getLoginUser(req);
		SimplePage page = req.getSimplePage(size);
		List<UserVo> uservolist = new ArrayList<UserVo>();
		String mip = req.getString("mip");
		String ip = null;
		if (!isEmpty(mip)) {
			ip = mip;
		}
		else {
			ip = req.getRemoteAddr();
		}
		IpCityRange range = this.ipCityService.getIpCityRange(ip);
		if (loginUser == null) {
			w = "all";
		}
		if (w.equals("range")) {
			if (range != null) {
				List<IpCityRangeUser> list = this.userService
						.getIpCityRangeUserList(range.getRangeId(), page
								.getBegin(), size);
				for (IpCityRangeUser ipu : list) {
					UserVo o = new UserVo();
					o.setUser(ipu.getUser());
					uservolist.add(o);
				}
			}
		}
		else if (w.equals("city")) {
			if (range != null) {
				List<IpCityUser> list = this.userService.getIpCityUserList(
						range.getCityId(), page.getBegin(), size);
				for (IpCityUser ipu : list) {
					UserVo o = new UserVo();
					o.setUser(ipu.getUser());
					uservolist.add(o);
				}
			}
		}
		else if (w.equals("ip")) {
			List<IpUser> list = this.userService.getIpUserList(ip, page
					.getBegin(), size);
			for (IpUser ipu : list) {
				UserVo o = new UserVo();
				o.setUser(ipu.getUser());
				uservolist.add(o);
			}
		}
		else if (w.equals("all")) {
			List<User> list = this.userService.getUserList(page.getBegin(),
					size);
			for (User u : list) {
				UserVo o = new UserVo();
				o.setUser(u);
				uservolist.add(o);
			}
		}
		page.setListSize(uservolist.size());
		req.setAttribute("w", w);
		UserVoBuilder builder = new UserVoBuilder();
		builder.setVolist(uservolist);
		builder.setLabaParserCfg(this.getLabaParserCfg(req));
		builder.setForceFriend(false);
		builder.setNeedFriend(true);
		builder.setLoginUser(loginUser);
		builder.setNeedLaba(true);
		UserVo.buildUserVoInfo(builder);
		req.setAttribute("uservobuilder", builder);
		req.setAttribute("uservolist", uservolist);
		List<HkGroup> glist = this.groupService.getGroupList(0, 6);
		req.setAttribute("glist", glist);
		return "/WEB-INF/page/user/list1.jsp";
	}
}