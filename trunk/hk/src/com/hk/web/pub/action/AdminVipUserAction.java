package com.hk.web.pub.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.User;
import com.hk.bean.VipUser;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.UserService;
import com.hk.svr.VipUserService;

@Component("/admin/adminvipuser")
public class AdminVipUserAction extends BaseAction {
	@Autowired
	private UserService userService;

	@Autowired
	private VipUserService vipUserService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		SimplePage page = req.getSimplePage(20);
		List<VipUser> list = this.vipUserService.getVipUserList(
				page.getBegin(), page.getSize() + 1);
		List<Long> idList = new ArrayList<Long>();
		for (VipUser o : list) {
			idList.add(o.getUserId());
		}
		Map<Long, User> map = this.userService.getUserMapInId(idList);
		for (VipUser o : list) {
			o.setUser(map.get(o.getUserId()));
		}
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWapJsp("admin/vipuser/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		String nickName = req.getString("nickName");
		User user = this.userService.getUserByNickName(nickName);
		if (user != null) {
			VipUser vipUser = new VipUser();
			vipUser.setUserId(user.getUserId());
			this.vipUserService.createVipUser(vipUser);
		}
		return "r:/admin/adminvipuser.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		this.vipUserService.deleteVipUser(oid);
		return "r:/admin/adminvipuser.do";
	}
}