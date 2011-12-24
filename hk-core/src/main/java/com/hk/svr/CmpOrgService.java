package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpOrg;
import com.hk.bean.CmpOrgApply;
import com.hk.bean.CmpOrgNav;

/**
 * 机构业务逻辑实现
 * 
 * @author akwei
 */
public interface CmpOrgService {

	void createCmpOrg(CmpOrg cmpOrg);

	void updateCmpOrg(CmpOrg cmpOrg);

	CmpOrg getCmpOrg(long companyId, long orgId);

	CmpOrg getCmpOrgByCompanyIdAndUserId(long companyId, long userId);

	/**
	 * @param companyId
	 * @param name 为空时不参加查询，不为空时，可模糊查询此字段
	 * @param begin
	 * @param size
	 * @return
	 *         2010-7-9
	 */
	List<CmpOrg> getCmpOrgListByCompanyId(long companyId, String name,
			int begin, int size);

	void createCmpOrgNav(CmpOrgNav cmpOrgNav);

	void updateCmpOrgNav(CmpOrgNav cmpOrgNav);

	/**
	 * 按照自定义顺序获取栏目集合
	 * 
	 * @param companyId
	 * @param orgId
	 * @return
	 *         2010-7-6
	 */
	List<CmpOrgNav> getCmpOrgNavListByCompanyIdAndOrgId(long companyId,
			long orgId);

	CmpOrgNav getCmpOrgNav(long companyId, long navId);

	/**
	 * 栏目删除后，栏目下的文章将navId置为0
	 * 
	 * @param companyId
	 * @param navId
	 *            2010-7-6
	 */
	void deleteCmpOrgNav(long companyId, long navId);

	void createCmpOrgApply(CmpOrgApply cmpOrgApply);

	void updateCmpOrgApply(CmpOrgApply cmpOrgApply);

	CmpOrg checkOkCmpOrgApply(CmpOrgApply cmpOrgApply);

	void deleteCmpOrgApply(long companyId, long oid);

	/**
	 * 按照申请正序排列
	 * 
	 * @param companyId
	 * @param userName
	 * @param orgName
	 * @param checkflg 为<0时，不参加查询,>=0时参与查询参考值 {@link CmpOrgApply#CHECKFLG_NO},
	 *            {@link CmpOrgApply#CHECKFLG_UNCHECKED},
	 *            {@link CmpOrgApply#CHECKFLG_YES}
	 * @param begin
	 * @param size
	 * @return
	 *         2010-7-9
	 */
	List<CmpOrgApply> getCmpOrgApplyListByCompanyId(long companyId,
			String userName, String tel, String email, String orgName,
			byte checkflg, int begin, int size);

	CmpOrgApply getCmpOrgApply(long companyId, long oid);

	CmpOrgApply getCmpOrgApplyByCompanyIdAndUserId(long companyId, long userId);
}