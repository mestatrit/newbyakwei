package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpChildKind;
import com.hk.bean.CmpChildKindRef;
import com.hk.bean.CompanyKind;

public interface CompanyKindService {

	/**
	 * 创建小分类
	 * 
	 * @param cmpChildKind
	 * @return true创建成功 false存在重名，创建失败
	 */
	boolean createCmpChildKind(CmpChildKind cmpChildKind);

	/**
	 * 修改小分类
	 * 
	 * @param cmpChildKind
	 * @return true修改成功 false存在重名，修改失败
	 */
	boolean updateCmpChildKind(CmpChildKind cmpChildKind);

	/**
	 * 删除小分类，同时删除小分类的关系数据
	 * 
	 * @param oid
	 */
	void deleteCmpChildKind(int oid);

	/**
	 * 获得小分类集合
	 * 
	 * @param kindId <=0时忽略此条件
	 * @param order_type 排序方式 0:按照id倒叙排列 1:按照足迹数量倒叙排列
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpChildKind> getCmpChildKindList(int kindId, int order_type,
			int begin, int size);

	CmpChildKind getCmpChildKind(int oid);

	/**
	 * 创建小分类与足迹之间的唯一关系数据,小分类只能选一个，如果存在，就先删除，然后在添加新的数据
	 * 
	 * @param companyId
	 * @param oid
	 * @param cityId
	 * @return true 创建成功 false已经存在，不再次创建
	 */
	boolean createCmpChildKindRef(int oid, long companyId, int cityId);

	/**
	 * 删除小分类与足迹之间的关系
	 * 
	 * @param oid
	 * @param companyId
	 */
	void deleteCmpChildKindRef(int oid, long companyId);

	/**
	 * 删除与oid小分类有关的足迹之间的关系
	 * 
	 * @param oid
	 */
	void deleteCmpChildKindRefByOid(int oid);

	/**
	 * 删除与足迹有关的小分类关系数据
	 * 
	 * @param companyId
	 */
	void deleteCmpChildKindRefByCompanyId(long companyId);

	/**
	 * 根据oid获得小分类关系数据
	 * 
	 * @param oid
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpChildKindRef> getCmpChildKindRefListByOid(int oid, int begin,
			int size);

	boolean createCompanyKind(CompanyKind companyKind);

	void deleteCompanyKind(int kindId);

	/**
	 * @param parentId 为0忽略此条件
	 * @return
	 */
	List<CompanyKind> getCompanyKindList(int parentId);

	/**
	 * @param kindId 为0忽略此条件
	 * @return
	 */
	List<CmpChildKind> getCmpChildKindList(int kindId);

	CompanyKind getCompanyKind(int kindId);

	boolean updateCompanyKind(CompanyKind companyKind);

	void updateCompanyKindCmpCount(int kindId);

	CmpChildKindRef getCmpChildKindRefByCompanyId(long companyId);

	List<CmpChildKindRef> getCmpChildKindRefList(int oid, int cityId,
			int begin, int size);

	int countCmpChildKindRef(int oid, int cityId);
}