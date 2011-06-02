package iwant.svr.impl;

import iwant.bean.AdminUser;
import iwant.svr.AdminUserSvr;

import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

@Component("adminUserSvr")
public class AdminUserSvrImpl implements AdminUserSvr {

	private ResourceBundle resourceBundle = ResourceBundle
			.getBundle("adminuser");

	@Override
	public AdminUser getAdminUserByName(String name) {
		if (!this.resourceBundle.containsKey(name)) {
			return null;
		}
		String value = resourceBundle.getString(name);
		String[] v = value.split(":");
		if (v.length != 4) {
			return null;
		}
		AdminUser adminUser = new AdminUser();
		adminUser.setName(name);
		adminUser.setAdminid(Integer.valueOf(v[0]));
		adminUser.setPwd(v[1]);
		adminUser.setLevel(Integer.valueOf(v[2]));
		adminUser.setCityid(Integer.valueOf(v[3]));
		return adminUser;
	}
}
