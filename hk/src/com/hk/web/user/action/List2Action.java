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
import com.hk.web.pub.action.IpZoneInfo;
import com.hk.web.user.UserVo;
import com.hk.web.user.UserVoBuilder;

@Component("/user/list2")
public class List2Action extends BaseAction {
	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private IpCityService ipCityService;

	private int size = 20;

	@SuppressWarnings("unchecked")
	public String execute(HkRequest req, HkResponse resp) {
		String w = req.getString("w", "all");
		req.setAttribute("w", w);
		if (w.equals("all")) {
			this.all(req);
		}
		else if (w.equals("city")) {
			this.city(req);
		}
		else if (w.equals("range")) {
			this.range(req);
		}
		else if (w.equals("ip")) {
			this.ip(req);
		}
		String forceforward = (String) req.getAttribute("forceforward");
		if (forceforward != null) {
			return "r:/user/list2.do?w=all";
		}
		User loginUser = this.getLoginUser(req);
		List<UserVo> uservolist = (List<UserVo>) req.getAttribute("uservolist");
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
		return "/WEB-INF/page/user/list2.jsp";
	}

	public void all(HkRequest req) {
		SimplePage page = req.getSimplePage(size);
		List<User> list = this.userService.getUserListSortFriend(page
				.getBegin(), size);
		List<UserVo> uservolist = new ArrayList<UserVo>();
		for (User u : list) {
			UserVo o = new UserVo();
			o.setUser(u);
			uservolist.add(o);
		}
		page.setListSize(uservolist.size());
		req.setAttribute("uservolist", uservolist);
	}

	public void city(HkRequest req) {
		User loginUser = this.getLoginUser(req);
		int ipCityId = req.getInt("ipCityId");
		if (ipCityId == 0) {
			IpCityRange range = this.ipCityService.getIpCityRange(req
					.getRemoteAddr());
			if (range != null) {
				ipCityId = range.getCityId();
			}
			if (ipCityId == 0) {
				if (loginUser != null) {
					User user = this.userService.getUser(loginUser.getUserId());
					IpZoneInfo ipZoneInfo = new IpZoneInfo(user.getPcityId());
					ipCityId = ipZoneInfo.getIpCityId();
				}
			}
		}
		SimplePage page = req.getSimplePage(size);
		List<IpCityUser> list = this.userService.getIpCityUserListSortFriend(
				ipCityId, page.getBegin(), size);
		List<UserVo> uservolist = new ArrayList<UserVo>();
		for (IpCityUser o : list) {
			UserVo vo = new UserVo();
			vo.setUser(o.getUser());
			uservolist.add(vo);
		}
		page.setListSize(uservolist.size());
		req.setAttribute("uservolist", uservolist);
	}

	public void range(HkRequest req) {
		SimplePage page = req.getSimplePage(size);
		String ip = req.getRemoteAddr();
		IpCityRange range = this.ipCityService.getIpCityRange(ip);
		if (range == null) {
			req.setAttribute("forceforward", "all");
			return;
		}
		List<IpCityRangeUser> list = this.userService
				.getIpCityRangeUserListSortFriend(range.getCityId(), page
						.getBegin(), size);
		List<UserVo> uservolist = new ArrayList<UserVo>();
		for (IpCityRangeUser o : list) {
			UserVo vo = new UserVo();
			vo.setUser(o.getUser());
			uservolist.add(vo);
		}
		page.setListSize(uservolist.size());
		req.setAttribute("uservolist", uservolist);
	}

	public void ip(HkRequest req) {
		SimplePage page = req.getSimplePage(size);
		String ip = req.getRemoteAddr();
		List<IpUser> list = this.userService.getIpUserListSortFriend(ip, page
				.getBegin(), size);
		List<UserVo> uservolist = new ArrayList<UserVo>();
		for (IpUser o : list) {
			UserVo vo = new UserVo();
			vo.setUser(o.getUser());
			uservolist.add(vo);
		}
		page.setListSize(uservolist.size());
		req.setAttribute("uservolist", uservolist);
	}
}