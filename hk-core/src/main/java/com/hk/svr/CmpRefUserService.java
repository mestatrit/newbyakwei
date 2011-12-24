package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpRefUser;

public interface CmpRefUserService {

	/**
	 * 如果已经存在，就不创建
	 * 
	 * @param cmpRefUser
	 *            2010-5-28
	 */
	void createCmpRefUser(CmpRefUser cmpRefUser);

	void deleteCmpRefUserByCompanyIdAndUserId(long companyId, long userId);

	List<CmpRefUser> getCmpRefUserListByCompanyId(long companyId, int begin,
			int size);

	CmpRefUser getCmpRefUserByCompanyIdAndUserId(long companyId, long userId);
}
