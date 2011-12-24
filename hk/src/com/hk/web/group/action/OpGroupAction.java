package com.hk.web.group.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.HkGroup;
import com.hk.bean.User;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.GroupService;
import com.hk.web.pub.action.BaseAction;

@Component("/group/op/op")
public class OpGroupAction extends BaseAction {
	@Autowired
	private GroupService groupService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 加入圈子
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String adduser(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		int gid = req.getInt("gid");
		List<HkGroup> list = this.groupService.getGroupListByUserId(loginUser
				.getUserId(), 0, 4);
		if (list.size() > 3) {
			req.setSessionMessage("最多只能加入3个圈子");
			return "r:/group/gulist.do?gid=" + gid;
		}
		this.groupService.addGroupUser(gid, loginUser.getUserId());
		req.setSessionMessage("操作成功");
		return "r:/group/gulist.do?gid=" + gid;
	}

	/**
	 * 退出圈子
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String deluser(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		int gid = req.getInt("gid");
		this.groupService.deleteGroupUser(gid, loginUser.getUserId());
		req.setSessionMessage("操作成功");
		return "r:/group/gulist.do?gid=" + gid;
	}
}