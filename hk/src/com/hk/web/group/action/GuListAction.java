package com.hk.web.group.action;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.HkGroup;
import com.hk.bean.HkGroupUser;
import com.hk.bean.User;
import com.hk.bean.UserRecentUpdate;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.GroupService;
import com.hk.svr.UserService;
import com.hk.svr.pub.DataSort;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.user.UserVo;
import com.hk.web.user.UserVoBuilder;

/**
 * 圈子成员集合,根据不同的排序方式显示成员
 * 
 * @author yuanwei
 */
@Component("/group/gulist")
public class GuListAction extends BaseAction {
	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	private int size = 20;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		int s = req.getInt("s");
		int gid = req.getInt("gid");
		List<UserVo> uservolist = null;
		if (s == 0) {// 关注度
			uservolist = this.s0(req);
		}
		else if (s == 1) {// 活跃度
			uservolist = this.s1(req);
		}
		else if (s == 2) {// 最新加入
			uservolist = this.s2(req);
		}
		req.setAttribute("s", s);
		req.setAttribute("gid", gid);
		HkGroup group = this.groupService.getGroup(gid);
		req.setAttribute("group", group);
		List<HkGroup> glist = this.groupService.getGroupList(0, 6);// 圈子集合
		req.setAttribute("glist", glist);
		req.setBackString("from=gulist&gid=" + gid);
		User loginUser = this.getLoginUser(req);
		boolean joined = false;
		if (loginUser != null) {
			HkGroupUser groupUser = this.groupService.getGroupUser(gid,
					loginUser.getUserId());
			if (groupUser != null) {
				joined = true;
			}
		}
		req.setAttribute("joined", joined);
		UserVoBuilder builder = new UserVoBuilder();
		builder.setVolist(uservolist);
		builder.setLabaParserCfg(this.getLabaParserCfg(req));
		builder.setNeedFriend(true);
		builder.setLoginUser(loginUser);
		builder.setNeedLaba(true);
		UserVo.buildUserVoInfo(builder);
		req.setAttribute("uservobuilder", builder);
		req.setAttribute("uservolist", uservolist);
		return "/WEB-INF/page/group/gulist.jsp";
	}

	public List<UserVo> s0(HkRequest req) throws Exception {
		SimplePage page = req.getSimplePage(size);
		int gid = req.getInt("gid");
		List<HkGroupUser> gulist = this.groupService
				.getGroupUserListByGroupId(gid);
		List<Long> idList = new ArrayList<Long>();// 圈子成员的userid集合
		for (HkGroupUser o : gulist) {
			idList.add(o.getUserId());
		}
		req.setAttribute("idList", idList);
		DataSort sort = new DataSort();
		sort.setDesc(true);
		sort.setField("fanscount");
		List<User> ulist = this.userService.getUserListInId(idList, sort,
				page.getBegin(), size);// 从userid集合中获取user对象
		List<UserVo> volist = new ArrayList<UserVo>();// 生成页面显示对象
		for (User u : ulist) {
			UserVo vo = new UserVo();
			vo.setUser(u);
			volist.add(vo);
		}
		return volist;
	}

	public List<UserVo> s1(HkRequest req) throws Exception {
		SimplePage page = req.getSimplePage(size);
		int gid = req.getInt("gid");
		List<HkGroupUser> gulist = this.groupService
				.getGroupUserListByGroupId(gid);
		List<Long> idList = new ArrayList<Long>();// 圈子成员的userid集合
		for (HkGroupUser o : gulist) {
			idList.add(o.getUserId());
		}
		req.setAttribute("idList", idList);
		DataSort sort = new DataSort();
		sort.setDesc(true);
		sort.setField("last30labacount");
		List<UserRecentUpdate> urulist = this.userService
				.getUserRecentUpdateListInUserId(idList, sort, page.getBegin(),
						size);// 从userid集合中获取userrecentupdate(活跃度)对象
		List<UserVo> volist = new ArrayList<UserVo>(urulist.size());// 生成页面显示对象
		for (UserRecentUpdate o : urulist) {
			UserVo vo = new UserVo();
			vo.setUser(this.userService.getUser(o.getUserId()));
			volist.add(vo);
		}
		return volist;
	}

	public List<UserVo> s2(HkRequest req) throws Exception {
		SimplePage page = req.getSimplePage(size);
		int gid = req.getInt("gid");
		List<HkGroupUser> gulist = this.groupService.getGroupUserListByGroupId(
				gid, page.getBegin(), size);
		List<Long> idList = new ArrayList<Long>();
		for (HkGroupUser o : gulist) {
			idList.add(o.getUserId());
		}
		req.setAttribute("idList", idList);
		List<UserVo> volist = new ArrayList<UserVo>(gulist.size());// 生成页面显示对象
		for (HkGroupUser o : gulist) {
			UserVo vo = new UserVo();
			vo.setUser(o.getUser());
			volist.add(vo);
		}
		return volist;
	}
}