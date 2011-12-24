package com.hk.svr.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpBomber;
import com.hk.bean.User;
import com.hk.svr.CmpBomberService;
import com.hk.svr.UserService;

public class CmpBomberProcessor {

	@Autowired
	private CmpBomberService cmpBomberService;

	@Autowired
	private UserService userService;

	public List<CmpBomber> getCmpBomberListByCompanyId(long companyId,
			boolean buildUser, int begin, int size) {
		List<CmpBomber> list = this.cmpBomberService
				.getCmpBomberListByCompanyId(companyId, begin, size);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpBomber o : list) {
				idList.add(o.getUserId());
			}
			Map<Long, User> map = this.userService.getUserMapInId(idList);
			for (CmpBomber o : list) {
				o.setUser(map.get(o.getUserId()));
			}
		}
		return list;
	}
}