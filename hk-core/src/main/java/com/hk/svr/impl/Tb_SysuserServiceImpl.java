package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.Tb_Sysuser;
import com.hk.bean.taobao.Tb_User;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.Tb_SysuserService;
import com.hk.svr.Tb_UserService;

public class Tb_SysuserServiceImpl implements Tb_SysuserService {

	@Autowired
	private QueryManager manager;

	@Autowired
	private Tb_UserService tbUserService;

	@Override
	public void createTb_Sysuser(Tb_Sysuser tbSysuser) {
		this.manager.createQuery().insertObject(tbSysuser);
	}

	@Override
	public void deleteTb_Sysuser(long userid) {
		this.manager.createQuery().deleteById(Tb_Sysuser.class, userid);
	}

	@Override
	public List<Tb_Sysuser> getTb_SysuserList(boolean buildUser, int begin,
			int size) {
		List<Tb_Sysuser> list = this.manager.createQuery().listEx(
				Tb_Sysuser.class, "userid desc", begin, size);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (Tb_Sysuser o : list) {
				idList.add(o.getUserid());
			}
			Map<Long, Tb_User> map = this.tbUserService
					.getTb_UserMapInId(idList);
			for (Tb_Sysuser o : list) {
				o.setTbUser(map.get(o.getUserid()));
			}
		}
		return list;
	}
}