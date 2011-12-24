package com.hk.svr;

import java.util.List;
import java.util.Map;
import com.hk.bean.HkObj;

/**
 * 关于足迹宝贝的竞排 等逻辑功能
 * 
 * @author akwei
 */
public interface HkObjService {
	HkObj getHkObj(long objId);

	List<HkObj> getHkObjListInId(List<Long> idList);

	Map<Long, HkObj> getHkObjMapInId(List<Long> idList);
}