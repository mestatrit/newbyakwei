package com.hk.svr.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpRefUser;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.svr.CmpRefUserService;
import com.hk.svr.UserService;

public class CmpRefUserProcessor {

	@Autowired
	private CmpRefUserService cmpRefUserService;

	@Autowired
	private UserService userService;

	public List<CmpRefUser> getCmpRefUserListByCompanyId(long companyId,
			boolean buildUser, int begin, int size) {
		List<CmpRefUser> list = this.cmpRefUserService
				.getCmpRefUserListByCompanyId(companyId, begin, size);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpRefUser o : list) {
				idList.add(o.getUserId());
			}
			Map<Long, User> map = this.userService.getUserMapInId(idList);
			Map<Long, UserOtherInfo> othermap = this.userService
					.getUserOtherInfoMapInId(idList);
			for (CmpRefUser o : list) {
				o.setUser(map.get(o.getUserId()));
				o.setUserOtherInfo(othermap.get(o.getUserId()));
			}
		}
		return list;
	}
}