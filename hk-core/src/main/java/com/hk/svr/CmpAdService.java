package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.CmpAd;
import com.hk.bean.CmpAdGroup;
import com.hk.bean.CmpAdRef;
import com.hk.svr.processor.CmpHomePicAdProcessor;

public interface CmpAdService {

	/**
	 * 实际调用
	 * {@link CmpHomePicAdProcessor#createCmpHomePicAd(com.hk.bean.CmpHomePicAd, java.io.File, int)}
	 * 
	 * @param cmpAd
	 *            2010-6-13
	 */
	void createCmpAd(CmpAd cmpAd);

	/**
	 * 实际调用
	 * {@link CmpHomePicAdProcessor#updateCmpHomePicAd(com.hk.bean.CmpHomePicAd, java.io.File, int)}
	 * 
	 * @param cmpAd
	 *            2010-6-13
	 */
	void updateCmpAd(CmpAd cmpAd);

	/**
	 * 实际调用 {@link CmpHomePicAdProcessor#deleteCmpHomePicAd(long)}
	 * 
	 * @param adid
	 *            2010-6-13
	 */
	void deleteCmpAd(long companyId, long adid);

	CmpAd getCmpAd(long adid);

	/**
	 * @param companyId
	 * @param groupId >0时，进入查询条件,<=0时,不进入查询条件
	 * @param begin
	 * @param size
	 * @return
	 *         2010-6-28
	 */
	List<CmpAd> getCmpAdListByCompanyId(long companyId, long groupId,
			int begin, int size);

	Map<Long, CmpAd> getCmpAdMaByCompanyIdAndInId(long companyId,
			List<Long> idList);

	boolean createCmpAdGroup(CmpAdGroup cmpAdGroup);

	boolean updateCmpAdGroup(CmpAdGroup cmpAdGroup);

	CmpAdGroup getCmpAdGroup(long companyId, long groupId);

	/**
	 * 删除组时，与组关联的数据删除
	 * 
	 * @param groupId
	 *            2010-6-28
	 */
	void deleteCmpAdGroup(long companyId, long groupId);

	/**
	 * 组列表
	 * 
	 * @param companyId
	 * @param name 可为空,不为空时，可模糊查询组名称
	 * @param begin
	 * @param size
	 * @return
	 *         2010-6-28
	 */
	List<CmpAdGroup> getCmpAdGroupListByCompanyId(long companyId, String name,
			int begin, int size);

	List<CmpAd> getCmpAdListByCompanyIdAndGroupId(long companyId, long groupId);

	boolean createCmpAdRef(CmpAdRef cmpAdRef);

	void deleteCmpAdRef(long companyId, long oid);

	List<CmpAdRef> getCmpAdRefListByCompanyId(long companyId, int begin,
			int size);

	CmpAdRef getCmpAdRefByCompanyIdAndAdid(long companyId, long adid);

	CmpAdRef getCmpAdRefByCompanyIdAndOid(long companyId, long oid);
}