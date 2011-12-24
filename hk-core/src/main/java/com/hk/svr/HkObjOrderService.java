package com.hk.svr;

import java.util.List;
import com.hk.bean.HkObjOrder;
import com.hk.svr.company.exception.NoEnoughMoneyException;
import com.hk.svr.company.exception.SmallerThanMinMoneyException;

public interface HkObjOrderService {
	/**
	 * 更新竞排数据，每天只能更新一次竞排价格多不退，少要补.补足今天的后，更新将要扣除余额的值 . 当期限没有变化时，只需判断当天差价是否满足余额条件
	 * 当期限有变化时，扣除当天差价后，计算相差天数与新的最低竞排价格的乘积和余额的比较
	 * 
	 * @param hkObjOrder
	 * @return 如果当天设置过，再次设置时不成功，返回false ，设置成功，返回true
	 */
	boolean updateHkObjOrder(HkObjOrder hkObjOrder)
			throws NoEnoughMoneyException, SmallerThanMinMoneyException;

	/**
	 * 创建竞排数据.初次创建数据的时候 从余额中扣除当日竞排价格，如果已经存在数据，就更新相应的数据
	 * 
	 * @param hkObjOrder
	 */
	boolean createHkObjOrder(HkObjOrder hkObjOrder)
			throws NoEnoughMoneyException, SmallerThanMinMoneyException;

	/**
	 * 中止竞排
	 * 
	 * @param oid
	 */
	void stop(long oid);

	/**
	 * 开启竞排
	 * 
	 * @param oid
	 */
	void run(long oid);

	int countHkObjOrder(byte stopflg);

	List<HkObjOrder> getHkObjOrderList(byte stopflg, int begin, int size);

	/**
	 * 竞价排名，扣除每日竞拍价格最高到最低进行排序
	 * 
	 * @param kind 排名类型
	 * @param cityId 为0忽略此条件
	 * @param provinceId 为0忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<HkObjOrder> getHkObjOrderListForOrder(byte kind, int cityId,
			int begin, int size);

	List<HkObjOrder> getHkObjOrderListByUserId(long userId, int cityId,
			int begin, int size);

	List<HkObjOrder> getHkObjOrderListByObjId(long objId, int cityId,
			int begin, int size);

	HkObjOrder getHkObjOrder(long oid);

	void calculate(HkObjOrder hkObjOrder);

	HkObjOrder getHkObjOrder(long objId, byte kind, int cityId);
}