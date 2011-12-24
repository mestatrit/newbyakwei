package com.hk.svr.implex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hk.bean.taobao.Tb_Sina_User;
import com.hk.bean.taobao.Tb_User;
import com.hk.svr.Tb_UserService;
import com.hk.svr.impl.Tb_UserServiceImpl;

public class Tb_UserServiceImplEx extends Tb_UserServiceImpl {

	private Tb_UserService tb_UserService;

	public void setTb_UserService(Tb_UserService tbUserService) {
		tb_UserService = tbUserService;
	}

	@Override
	public List<Tb_Sina_User> getTb_Sina_UserListInId(List<Long> idList,
			boolean buildUser) {
		List<Tb_Sina_User> list = this.tb_UserService.getTb_Sina_UserListInId(
				idList, buildUser);
		if (buildUser) {
			List<Long> useridList = new ArrayList<Long>();
			for (Tb_Sina_User o : list) {
				useridList.add(o.getUserid());
			}
			Map<Long, Tb_User> map = this.tb_UserService
					.getTb_UserMapInId(useridList);
			for (Tb_Sina_User o : list) {
				o.setTbUser(map.get(o.getUserid()));
			}
		}
		return list;
	}
}