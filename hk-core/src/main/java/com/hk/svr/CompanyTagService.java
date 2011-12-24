package com.hk.svr;

import java.util.List;
import com.hk.bean.CompanyTag;
import com.hk.bean.CompanyTagRef;

public interface CompanyTagService {
	/**
	 * 如果同一个类型和名称就不创建
	 * 
	 * @param name
	 * @param kindId
	 * @return
	 */
	boolean createCompanyTag(String name, int kindId);

	/**
	 * 删除标签以及与足迹之间的关系数据以及地区标签的数据
	 * 
	 * @param tagId
	 */
	void deleteCompanyTag(int tagId);

	List<CompanyTag> getCompanyTagList(String name, int kindId, int begin,
			int size);

	CompanyTag getCompanyTag(int tagId);

	boolean updateCompanyTag(CompanyTag companyTag);

	/**
	 * 数据创建后，更新地区标签中足迹数量
	 * 
	 * @param companyId
	 * @param tagId
	 * @param cityId
	 * @return
	 */
	boolean createCompanyTagRef(long companyId, int tagId, int cityId);

	/**
	 * 删除数据后，更新地区标签中足迹数量
	 * 
	 * @param companyId
	 * @param tagId
	 * @param cityId
	 */
	void deleteCompanyTagRef(long companyId, int tagId, int cityId);

	/**
	 * 删除足迹相关标签，并更新标签所在地区的数量
	 * 
	 * @param companyId
	 */
	void deleteCompanyTagRef(long companyId);

	void updateCompanyTagRefByCompanyId(long companyId, int old_cityid,
			int new_cityId);

	List<CompanyTag> getCompanyTagListByCompanyId(long companyId);

	List<CompanyTag> getCompanyTagListExcept(int kindId, List<Integer> idList);

	/**
	 * 只取cmpcount>0的数据
	 * 
	 * @param kindId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CompanyTag> getCompanyTagListForHot(int kindId, int cityId, int begin,
			int size);

	int countCompanyTagRefByTagIdAndCityId(int tagId, int cityId);

	List<CompanyTagRef> getCompanyTagRefListByTagIdAndCityId(int tagId,
			int cityId, int begin, int size);
}