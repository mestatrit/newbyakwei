package com.hk.svr;

import java.util.List;
import java.util.Map;
import com.hk.bean.KeyTag;
import com.hk.bean.KeyTagSearchInfo;

/**
 * 功能包括关键词的创建，关键词与足迹宝贝的关系数据，足迹宝贝在关键词中的排序
 * 
 * @author akwei
 */
public interface KeyTagService {
	/**
	 * 不允许有重复的name,如果已经存在，就返回当前数据的id，不存在，就创建
	 * 
	 * @param name
	 * @return
	 */
	long createKeyTag(String name);

	KeyTag getKeyTagByName(String name);

	KeyTag getKeyTag(long tagId);

	boolean createHkObjKeyTag(long tagId, long objId);

	KeyTagSearchInfo getKeyTagSearchInfoByYearAndMonth(long tagId, int year,
			int month);

	List<KeyTag> getKeyTagListInId(List<Long> idList);

	Map<Long, KeyTag> getKeyTagMapInId(List<Long> idList);
}