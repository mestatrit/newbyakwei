package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.CmpOrgStudyAd;
import com.hk.bean.CmpOrgStudyAdContent;

public interface CmpOrgStudyAdService {

	void createCmpOrgStudyAd(CmpOrgStudyAd cmpOrgStudyAd,
			CmpOrgStudyAdContent cmpOrgStudyAdContent);

	void updateCmpOrgStudyAd(CmpOrgStudyAd cmpOrgStudyAd,
			CmpOrgStudyAdContent cmpOrgStudyAdContent);

	void deleteCmpOrgStudyAd(long companyId, long adid);

	CmpOrgStudyAd getCmpOrgStudyAd(long companyId, long adid);

	List<CmpOrgStudyAd> getCmpOrgStudyAdListByCompanyIdAndOrgIdForNext(
			long companyId, long orgId, long currentAdid, int size);

	List<CmpOrgStudyAd> getCmpOrgStudyAdListByCompanyIdAndKindId(
			long companyId, long kindId, int begin, int size);

	List<CmpOrgStudyAd> getCmpOrgStudyAdListByCompanyIdAndKindId2(
			long companyId, long kindId2, int begin, int size);

	List<CmpOrgStudyAd> getCmpOrgStudyAdListByCompanyIdAndKindId3(
			long companyId, long kindId3, int begin, int size);

	int countCmpOrgStudyAdByCompanyIdAndKindId(long companyId, long kindId);

	int countCmpOrgStudyAdByCompanyIdAndKindId2(long companyId, long kindId2);

	int countCmpOrgStudyAdByCompanyIdAndKindId3(long companyId, long kindId3);

	CmpOrgStudyAdContent getCmpOrgStudyAdContent(long companyId, long adid);

	/**
	 * @param companyId
	 * @param orgId
	 * @param title 为空时不参与查询条件，不为空时可模糊查询
	 * @param beign
	 * @param size
	 * @return
	 *         2010-7-7
	 */
	List<CmpOrgStudyAd> getCmpOrgStudyAdListByCompanyIdAndOrgId(long companyId,
			long orgId, String title, int begin, int size);

	Map<Long, CmpOrgStudyAd> getCmpOrgStudyAdMapByCompanyIdInId(long companyId,
			List<Long> idList);

	int countCmpOrgStudyAdByCompanyIdAndOrgId(long companyId, long orgId);
}