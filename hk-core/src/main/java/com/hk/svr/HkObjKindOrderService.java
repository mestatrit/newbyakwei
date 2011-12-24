package com.hk.svr;

import java.util.List;
import com.hk.bean.HkObjKindOrder;
import com.hk.svr.company.exception.NoEnoughMoneyException;
import com.hk.svr.company.exception.SmallerThanMinMoneyException;

/**
 * 关于足迹分类排名的操作
 * 
 * @author akwei
 */
public interface HkObjKindOrderService {
	/**
	 * 每天只能更新一次，再次更新返回 false
	 * 
	 * @param hkObjKindOrder
	 * @return
	 * @throws NoEnoughMoneyException
	 * @throws SmallerThanMinMoneyException
	 */
	boolean createHkObjKindOrder(HkObjKindOrder hkObjKindOrder)
			throws NoEnoughMoneyException, SmallerThanMinMoneyException;

	HkObjKindOrder getHkObjKindOrder(long oid);

	HkObjKindOrder getHkObjKindOrder(int kindId, long objId, int cityId);

	void run(long oid);

	void stop(long oid);

	List<HkObjKindOrder> getHkObjKindOrderList(int kindId, int cityId,
			int begin, int size);

	List<HkObjKindOrder> getHkObjKindOrderListByUserId(long userId, int cityId,
			int begin, int size);

	List<HkObjKindOrder> getHkObjKindOrderListByObjId(long objId, int cityId,
			int begin, int size);
}