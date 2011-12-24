package com.hk.svr;

import java.util.List;
import com.hk.bean.BizCircle;
import com.hk.bean.Company;

public interface BizCircleService {
	/**
	 * 如果有同名，就不创建
	 * 
	 * @param bizCircle
	 * @return
	 */
	boolean createBizCircle(BizCircle bizCircle);

	/**
	 * @param name 为空忽略此条件
	 * @param cityId 为0忽略此条件
	 * @param provinceId 为0忽略此条件
	 * @return
	 */
	List<BizCircle> getBizCircleList(String name, int cityId, int provinceId);

	/**
	 * 获取存在足迹的商圈列表
	 * 
	 * @param name
	 * @param cityId
	 * @param provinceId
	 * @return
	 */
	List<BizCircle> getBizCircleListForHasCmp(int cityId);

	void deleteBizCircle(int circleId);

	void updateBizCircle(BizCircle bizCircle);

	BizCircle getBizCircle(int circleId);

	void createCompanyBizCircle(long companyId, int circleId);

	void deleteCompanyBizCircle(long companyId, int circleId);

	void updateBizCircleCmpCount(int circleId);

	/**
	 * @param kindId 为0忽略此条件
	 * @param circleId
	 * @param cityId 为忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Company> getCompanyList(int circleId, int kindId, int parentId,
			int begin, int size);

	/**
	 * @param kindId 为0忽略此条件
	 * @param circleId
	 * @param cityId 为忽略此条件
	 * @return
	 */
	int countCompany(int circleId, int kindId, int parentId, int cityId);

	void updatecitydata();
}