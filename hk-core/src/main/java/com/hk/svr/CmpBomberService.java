package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpBomber;

/**
 * 炸弹分配逻辑
 * 
 * @author akwei
 */
public interface CmpBomberService {

	/**
	 * 添加或更新对象
	 * 
	 * @param cmpBomber
	 *            2010-5-9
	 */
	void saveCmpBomber(CmpBomber cmpBomber);

	void deleteCmpBomber(long oid);

	/**
	 * 使用炸弹,更新相应炸弹数据
	 * 
	 * @param userId
	 * @param add 使用的数量
	 *            2010-5-10
	 */
	void useBomb(long companyId, long userId, int add);

	CmpBomber getCmpBomber(long oid);

	CmpBomber getCmpBomberByCompanyIdAndUserId(long companyId, long userId);

	List<CmpBomber> getCmpBomberListByCompanyId(long companyId, int begin,
			int size);
}