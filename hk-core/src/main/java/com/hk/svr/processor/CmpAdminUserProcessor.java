package com.hk.svr.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpAdminUser;
import com.hk.bean.User;
import com.hk.svr.CmpAdminUserService;
import com.hk.svr.UserService;

public class CmpAdminUserProcessor {

	@Autowired
	private CmpAdminUserService cmpAdminUserService;

	@Autowired
	private UserService userService;

	public List<CmpAdminUser> getCmpAdminUserByCompanyId(long companyId,
			boolean buildUser) {
		List<CmpAdminUser> list = this.cmpAdminUserService
				.getCmpAdminUserByCompanyId(companyId);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpAdminUser o : list) {
				idList.add(o.getUserId());
			}
			Map<Long, User> map = this.userService.getUserMapInId(idList);
			for (CmpAdminUser o : list) {
				o.setUser(map.get(o.getUserId()));
			}
		}
		return list;
	}
}