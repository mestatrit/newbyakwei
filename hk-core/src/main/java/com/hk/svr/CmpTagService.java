package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpTag;
import com.hk.bean.CmpTagRef;

/**
 * 用户自定义标签模块，用户可以给足迹添加自己的标签
 * 
 * @author akwei
 */
public interface CmpTagService {
	/**
	 * 创建标签，如果没有cmptagref，需要创建cmptagref
	 * 
	 * @param companyId
	 * @param cmpTag
	 * @return true:初次添加该标签，false:用户或者其他人已经对某个足迹添加过改标签，不再次添加
	 */
	boolean createCmpTag(CmpTag cmpTag, long companyId, long userId, int pcityId);

	void deleteCmpTagRef(long oid);

	void deleteCmpTagRefByCompanyIdAndTagId(long companyId, long tagId);

	List<CmpTagRef> getCmpTagRefListByCompanyId(long companyId, int begin,
			int size);

	/**
	 * @param tagId
	 * @param pcityId 为0可忽略次条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpTagRef> getCmpTagRefListByTagId(long tagId, int pcityId, int begin,
			int size);

	CmpTagRef getCmpTagRef(long oid);

	CmpTagRef getCmpTagRefByCompanyIdAndTagId(long companyId, long tagId);

	CmpTag getCmpTagByName(String name);

	CmpTag getCmpTag(long tagId);

	/**
	 * 限制为在某地区查询,对标签名称模糊搜索
	 * 
	 * @param name
	 * @param pcityId 地区id
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpTagRef> getCmpTagRefListByPcityIdAndNameLike(int pcityId,
			String name, int begin, int size);
}