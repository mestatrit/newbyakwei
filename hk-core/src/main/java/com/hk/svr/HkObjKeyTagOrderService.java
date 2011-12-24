package com.hk.svr;

import java.util.List;
import com.hk.bean.HkObjKeyTagOrder;
import com.hk.bean.KeyTagSearchInfo;
import com.hk.svr.company.exception.NoEnoughMoneyException;
import com.hk.svr.company.exception.SmallerThanMinMoneyException;

public interface HkObjKeyTagOrderService {
	/**
	 * 如果有地域性限制，则查看该地区是否已经有此数据存在，如果当天已经更新过，就不进行更新。扣除当天差价余额。 余额不足时，不能创建
	 * 
	 * @param hkObjKeyTagOrder
	 * @return
	 */
	boolean createHkObjKeyTagOrder(HkObjKeyTagOrder hkObjKeyTagOrder)
			throws NoEnoughMoneyException, SmallerThanMinMoneyException;

	HkObjKeyTagOrder getHkObjKeyTagOrder(long oid);

	/**
	 * 更新竞排数据，每天只能更新一次竞排价格多不退，少要补.补足今天的后，更新将要扣除余额的值 . 当期限没有变化时，只需判断当天差价是否满足余额条件
	 * 当期限有变化时，扣除当天差价后，计算相差天数与新的最低竞排价格的乘积和余额的比较
	 * 
	 * @param oid
	 * @return false 已经更新过
	 */
	boolean updateHkObjKeyTagOrder(HkObjKeyTagOrder hkObjKeyTagOrder)
			throws NoEnoughMoneyException, SmallerThanMinMoneyException;

	/**
	 * 开启当前的竞排
	 * 
	 * @param oid
	 */
	void run(long oid);

	/**
	 * 停止当前的竞排
	 * 
	 * @param oid
	 */
	void stop(long oid);

	List<HkObjKeyTagOrder> getHkObjKeyTagOrderListByUserId(long userId,
			int cityId, int begin, int size);

	List<HkObjKeyTagOrder> getHkObjKeyTagOrderListByObjId(long objId,
			int cityId, int begin, int size);

	/**
	 * 页面排序的数据
	 * 
	 * @param cityId
	 * @param provinceId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<HkObjKeyTagOrder> getHkObjKeyTagOrderListForOrder(long tagId,
			int cityId, int begin, int size);

	void calculate(HkObjKeyTagOrder hkObjKeyTagOrder);

	void addKeyTagSerachCount(long tagId, int amount);

	/**
	 * 统计关键词搜索量，安装总量统计，按照月份统计
	 * 
	 * @param tagId
	 */
	void setKeyTagSearchCountInfo(long tagId);

	/**
	 * 查看某月的搜索关键词，按照搜索量排序
	 * 
	 * @param year
	 * @param month
	 * @param begin
	 * @param size
	 * @return
	 */
	List<KeyTagSearchInfo> getKeyTagSearchInfoListByYearAndMonth(int year,
			int month, int begin, int size);

	/**
	 * 查看某个关键词的竞拍数据，按照出价进行排序
	 * 
	 * @param cityId -1时忽略此条件
	 * @param tagId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<HkObjKeyTagOrder> getHkObjKeyTagOrderListByTagIdAndCityId(long tagId,
			int cityId, int begin, int size);

	/**
	 * @param tagId
	 * @param cityId -1时忽略此条件
	 * @return
	 */
	int countHkObjKeyTagOrderListByTagIdAndCityId(long tagId, int cityId);

	HkObjKeyTagOrder getHkObjKeyTagOrder(long tagId, long objId, int cityId);
}