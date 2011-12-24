package com.hk.svr;

import java.util.List;

import com.hk.bean.VipUser;

public interface VipUserService {
	void createVipUser(VipUser vipUser);

	void deleteVipUser(long oid);

	List<VipUser> getVipUserList(int begin, int size);

	VipUser getVipUserByUserId(long userId);
}