package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.CmpInfo;
import com.hk.bean.CmpLanguageRef;

public interface CmpInfoService {

	CmpInfo getCmpInfoByDomain(String domain);

	CmpInfo getCmpInfo(long companyId);

	/**
	 * @param cmpInfo
	 * @return false:如果有重名，创建不成功,true:创建成功
	 *         2010-6-4
	 */
	boolean createCmpInfo(CmpInfo cmpInfo);

	/**
	 * @param domain
	 * @return 如果有域名重复，修改失败
	 */
	boolean updateCmpInfo(CmpInfo cmpInfo);

	/**
	 * 企业多语言配置，在一个站点添加关联语言企业时，是相互关联
	 * 
	 * @param companyId
	 * @param refCompanyId
	 *            2010-6-5
	 */
	void createCmpLanguageRef(long companyId, long refCompanyId);

	/**
	 * 删除语言关联
	 * 
	 * @param oid
	 *            2010-6-5
	 */
	void deleteCmpLanguageRef(long oid);

	List<CmpLanguageRef> getCmpLanguageRefListByCompanyId(long companyId);

	List<CmpInfo> getCmpInfoListInId(List<Long> idList);

	Map<Long, CmpInfo> getCmpInfoMapInId(List<Long> idList);

	/**
	 * @param begin
	 * @param size 为负数时，获取所有数据
	 * @return
	 *         2010-7-6
	 */
	List<CmpInfo> getCmpInfoList(int begin, int size);
}