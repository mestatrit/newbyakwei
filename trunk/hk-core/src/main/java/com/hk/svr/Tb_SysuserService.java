package com.hk.svr;

import java.util.List;

import com.hk.bean.taobao.Tb_Sysuser;

public interface Tb_SysuserService {

	void createTb_Sysuser(Tb_Sysuser tbSysuser);

	List<Tb_Sysuser> getTb_SysuserList(boolean buildUser, int begin, int size);

	void deleteTb_Sysuser(long userid);
}