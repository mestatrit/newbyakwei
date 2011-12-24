package com.hk.svr;

import com.hk.bean.taobao.Tb_Admin;

public interface Tb_AdminService {

	void createTb_Admin(Tb_Admin tbAdmin);

	void updateTb_Admin(Tb_Admin tbAdmin);

	void deleteTb_AdminByUserid(long userid);

	void deleteTb_Admin(long oid);

	Tb_Admin getTb_AdminByUserid(long userid);
}