package iwant.svr;

import iwant.bean.AdminUser;

public interface AdminUserSvr {

	/**
	 * 根据登录名称查询登录用户
	 * 
	 * @param name
	 * @return
	 */
	AdminUser getAdminUserByName(String name);
}