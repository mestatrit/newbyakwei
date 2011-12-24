package com.hk.web.admin.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.hk.bean.AdminHkb;
import com.hk.bean.User;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.svr.UserService;

public class AdminHkbVo {
	private AdminHkb adminHkb;

	private String content;

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setAdminHkb(AdminHkb adminHkb) {
		this.adminHkb = adminHkb;
	}

	public AdminHkb getAdminHkb() {
		return adminHkb;
	}

	public AdminHkbVo(AdminHkb adminHkb) {
		this.adminHkb = adminHkb;
	}

	public static List<AdminHkbVo> createVoList(List<AdminHkb> list) {
		UserService userService = (UserService) HkUtil.getBean("userService");
		List<AdminHkbVo> volist = new ArrayList<AdminHkbVo>();
		List<Long> idList = new ArrayList<Long>();
		for (AdminHkb o : list) {
			idList.add(o.getUserId());
			AdminHkbVo vo = new AdminHkbVo(o);
			vo.setContent(ResourceConfig.getText("adminhkb.addflg"
					+ o.getAddflg()));
			volist.add(vo);
		}
		Map<Long, User> map = userService.getUserMapInId(idList);
		for (AdminHkbVo vo : volist) {
			vo.getAdminHkb().setUser(map.get(vo.getAdminHkb().getUserId()));
		}
		return volist;
	}
}