package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.CmpAdminGroup;
import com.hk.bean.CmpAdminGroupRef;

/**
 * 火酷系统足迹组的模块
 * 
 * @author akwei
 */
public interface CmpAdminGroupService {
	/**
	 * 创建足迹组
	 * 
	 * @param cmpAdminGroup
	 * @return true:创建成功 false:有重名，创建失败
	 */
	boolean createCmpAdminGroup(CmpAdminGroup cmpAdminGroup);

	/**
	 * 更新足迹组
	 * 
	 * @param cmpAdminGroup
	 * @return true:更新成功 false:有重名，更新失败
	 */
	boolean updateCmpAdminGroup(CmpAdminGroup cmpAdminGroup);

	/**
	 * 删除足迹组
	 * 
	 * @param groupId
	 */
	void deleteCmpAdminGroup(long groupId);

	/**
	 * 创建足迹与组的关系，如果存在，则不创建
	 * 
	 * @param groupId
	 * @param companyId
	 */
	void createCmpAdminGroupRef(long groupId, long companyId);

	/**
	 * 删除足迹与组的关系
	 * 
	 * @param groupId
	 * @param companyId
	 */
	void deleteCmpAdminGroupRef(long groupId, long companyId);

	/**
	 * 根据条件获得组的集合
	 * 
	 * @param name 组名称，为空忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpAdminGroup> getCmpAdminGroupList(String name, int begin, int size);

	/**
	 * 获得某个组的足迹与组关系集合
	 * 
	 * @param groupId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpAdminGroupRef> getCmpAdminGroupRefListByGroupId(long groupId,
			int begin, int size);

	List<CmpAdminGroupRef> getCmpAdminGroupRefListByCompanyId(long companyId);

	CmpAdminGroup getCmpAdminGroup(long groupId);

	Map<Long, CmpAdminGroup> getCmpAdminGroupMapInIdList(List<Long> idList);
}