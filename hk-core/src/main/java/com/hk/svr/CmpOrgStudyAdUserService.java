package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpOrgStudyAdUser;

/**
 * 报名相关逻辑
 * 
 * @author akwei
 */
public interface CmpOrgStudyAdUserService {

	void createCmpOrgStudyAdUser(CmpOrgStudyAdUser cmpOrgStudyAdUser);

	void updateCmpOrgStudyAdUser(CmpOrgStudyAdUser cmpOrgStudyAdUser);

	void deleteCmpOrgStudyAdUser(long companyId, long oid);

	CmpOrgStudyAdUser getCmpOrgStudyAdUser(long companyId, long oid);

	List<CmpOrgStudyAdUser> getCmpOrgStudyAdUserListByCompanyIdAndOrgId(
			long companyId, long orgId, int begin, int size);

	List<CmpOrgStudyAdUser> getCmpOrgStudyAdUserListByCompanyIdAndOrgIdAndAdid(
			long companyId, long orgId, long adid, int begin, int size);

	int countCmpOrgStudyAdUserByCompanyidAndUserId(long companyId, long userId);

	List<CmpOrgStudyAdUser> getCmpOrgStudyAdUserListByCompanyidAndUserId(
			long companyId, long userId, int begin, int size);

	List<CmpOrgStudyAdUser> getCmpOrgStudyAdUserListByCompanyidAndAdidAndUserId(
			long companyId, long adid, long userId, int begin, int size);
}