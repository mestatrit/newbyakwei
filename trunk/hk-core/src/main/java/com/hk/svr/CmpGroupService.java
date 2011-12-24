package com.hk.svr;

import java.io.File;
import java.util.List;
import com.hk.bean.CmpGroup;
import com.hk.bean.CmpGroupQuestion;
import com.hk.bean.CmpGroupSmsPort;
import com.hk.bean.CmpGroupUser;

/**
 * 足迹俱乐部功能，包括管理俱乐部、成员，已经开通俱乐部短信通道功能
 * 
 * @author akwei
 */
public interface CmpGroupService {
	/**
	 * 创建俱乐部
	 * 
	 * @param cmpGroup
	 * @return 有重名返回false
	 */
	boolean createCmpGroup(CmpGroup cmpGroup);

	void updateLogopath(long cmpgroupId, File file) throws Exception;

	/**
	 * 修改俱乐部，其中俱乐部名称这项只有在创建后一个月以内才可以修改
	 * 
	 * @return 有重名返回false
	 * @param cmpGroup
	 */
	boolean updateCmpGroup(CmpGroup cmpGroup);

	/**
	 * 获得俱乐部对象
	 * 
	 * @param cmpgroupId
	 * @return
	 */
	CmpGroup getCmpGroup(long cmpgroupId);

	/**
	 * 根据足迹名称获得俱乐部对象
	 * 
	 * @param companyId
	 * @return
	 */
	CmpGroup getCmpGroupByCompanyId(long companyId);

	/**
	 * 根据名称获得俱乐部对象
	 * 
	 * @param name
	 * @return
	 */
	CmpGroup getCmpGroupByName(String name);

	/**
	 * 用户加入俱乐部
	 * 
	 * @param cmpGroupUser
	 * @return 已经存在就返回false
	 */
	boolean createCmpGroupUser(CmpGroupUser cmpGroupUser);

	/**
	 * 删除俱乐部成员
	 * 
	 * @param cmpgroupId
	 * @param userId
	 */
	void deleteCmpGroupUser(long cmpgroupId, long userId);

	/**
	 * 获得成员对象
	 * 
	 * @param cmpgroupId
	 * @param userId
	 * @return
	 */
	CmpGroupUser getCmpGroupUser(long cmpgroupId, long userId);

	/**
	 * 查看俱乐部开通的短信通道
	 * 
	 * @param cmpgroupId
	 * @return
	 */
	CmpGroupSmsPort getCmpGroupSmsPort(long cmpgroupId);

	/**
	 * 根据短信端口查看俱乐部开通的短信通道
	 * 
	 * @param port
	 * @return
	 */
	CmpGroupSmsPort getCmpGroupSmsPortByPort(String port);

	/**
	 * 创建俱乐部为验证使用的问题与答案
	 * 
	 * @param cmpGroupQuestion
	 */
	void createCmpGroupQuestion(CmpGroupQuestion cmpGroupQuestion);

	/**
	 * 删除问题数据
	 * 
	 * @param oid
	 */
	void deleteCmpGroupQuestion(long oid);

	/**
	 * 获得问题对象
	 * 
	 * @param oid
	 * @return
	 */
	CmpGroupQuestion getCmpGroupQuestion(long oid);

	/**
	 * 随即获得俱乐部设置的 一个问题
	 * 
	 * @param cmpgroupId
	 * @return
	 */
	CmpGroupQuestion getCmpGroupQuestionRandom(long cmpgroupId);

	/**
	 * 获得俱乐部设置的问题的集合
	 * 
	 * @param cmpgroupId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpGroupQuestion> getCmpGroupQuestionList(long cmpgroupId, int begin,
			int size);
}