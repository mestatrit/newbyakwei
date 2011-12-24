package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpAdminUser;

/**
 * 添加企业管理员
 * 
 * @author akwei
 */
public interface CmpAdminUserService {

	/**
	 * 创建管理员
	 * 
	 * @param cmpAdminUser
	 * @return false:已经存在不重复创建,true:创建成功
	 *         2010-5-10
	 */
	boolean createCmpAdminUser(CmpAdminUser cmpAdminUser);

	void deleteCmpAdminUser(long oid);

	CmpAdminUser getCmpAdminUser(long oid);

	CmpAdminUser getCmpAdminUserByCompanyIdAndUserId(long companyId, long userId);

	List<CmpAdminUser> getCmpAdminUserByCompanyId(long companyId);
}