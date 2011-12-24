package com.hk.svr;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hk.bean.CmpOrderTable;
import com.hk.bean.CmpPersonTable;
import com.hk.bean.CmpTable;
import com.hk.bean.CmpTablePhoto;
import com.hk.bean.CmpTablePhotoSet;
import com.hk.bean.CmpTableSort;

public interface CmpTableService {
	/**
	 * 创建台面分类
	 * 
	 * @param cmpTableSort
	 * @return true:创建成功.false:已经存在重名分类,创建失败
	 */
	boolean createCmpTableSort(CmpTableSort cmpTableSort);

	/**
	 * 更新台面分类
	 * 
	 * @param cmpTableSort
	 * @return true:更新成功.false:已经存在重名分类,更新失败
	 */
	boolean updateCmpTableSort(CmpTableSort cmpTableSort);

	/**
	 * 删除分类，分类下的台面分类id置为0
	 * 
	 * @param sortId
	 */
	void deleteCmpTableSort(long sortId);

	/**
	 * 获得分类对象
	 * 
	 * @param sortId
	 * @return
	 */
	CmpTableSort getCmpTableSort(long sortId);

	/**
	 * 获取餐厅的所有台面分类
	 * 
	 * @param companyId
	 * @return
	 */
	List<CmpTableSort> getCmpTableSortListByCompanyId(long companyId);

	/**
	 * 创建台面
	 * 
	 * @param cmpTableSort
	 * @return true:创建成功.false:已经存在重名,创建失败
	 */
	boolean createCmpTable(CmpTable cmpTable);

	/**
	 * 更新台面
	 * 
	 * @param cmpTable
	 * @return true:更新成功.false:已经存在重名,更新失败
	 */
	boolean updateCmpTable(CmpTable cmpTable);

	/**
	 * 删除台面
	 * 
	 * @param tableId
	 */
	void deleteCmpTable(long tableId);

	/**
	 * 获得台面对象
	 * 
	 * @param tableId
	 * @return
	 */
	CmpTable getCmpTable(long tableId);

	CmpTable getCmpTableByCompanyIdAndTableNum(long companyId, String tableNum);

	/**
	 * 统计台面数量
	 * 
	 * @param companyId 餐厅id
	 * @param sortId 分类id，如果为0，则忽略此条件
	 * @param tableNum 台号，如果为空，则忽略此条件
	 * @return
	 */
	int countCmpTable(long companyId, long sortId, String tableNum);

	/**
	 * 根据条件获得台面集合
	 * 
	 * @param companyId 餐厅id
	 * @param sortId 分类id，如果为0，则忽略此条件
	 * @param tableNum 台号，如果为空，则忽略此条件
	 * @param begin 为-1时，忽略 开始记录号,并忽略size
	 * @param size 为<=0时，忽略数据量,并忽略begin
	 * @return
	 */
	List<CmpTable> getCmpTableList(long companyId, long sortId,
			String tableNum, int begin, int size);

	List<CmpTable> getCmpTableList(long companyId, long sortId);

	List<CmpTable> getCmpTableListInId(long companyId, List<Long> idList);

	Map<Long, CmpTable> getCmpTableMapInId(long companyId, List<Long> idList);

	List<CmpTableSort> getCmpTableSortListInId(List<Long> idList);

	Map<Long, CmpTableSort> getCmpTableSortMapInId(List<Long> idList);

	void createCmpTablePhoto(CmpTablePhoto cmpTablePhoto);

	void deleteCmpTablePhoto(long oid);

	// List<CmpTablePhoto> getCmpTablePhotoListByTableId(long tableId);
	CmpTablePhoto getCmpTablePhoto(long oid);

	/**
	 * 根据人数获取台面,若bestPersonNum 为0 或sortId为0,则获取全部
	 * 
	 * @param companyId
	 * @param sortId
	 * @param bestPersonNum
	 * @return
	 */
	List<CmpTable> getCmpTableByCompanyIdAndBestPersonNum(long companyId,
			long sortId, int bestPersonNum);

	void updateCmpTableFreeflg(long tableId, byte freeflg);

	List<CmpPersonTable> getCmpPersonTableListByCompanyId(long companyId);

	List<CmpPersonTable> getCmpPersonTableListByCompanyIdForTotalCountNotZero(
			long companyId);

	/**
	 * 用户预约餐桌
	 * 
	 * @param cmpOrderTable
	 */
	void createCmpOrderTable(CmpOrderTable cmpOrderTable);

	void updateCmpOrderTable(CmpOrderTable cmpOrderTable);

	void deleteCmpOrderTable(long oid);

	/**
	 * @param companyId
	 * @param sortId 分类为0时，忽略此条件
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	int calculateDailyCmpTableDataByDate(long companyId, long sortId,
			Date beginDate, Date endDate);

	/**
	 * @param tableId
	 * @param beginTime
	 * @param endTime
	 * @param name 可模糊查询，如果为空，则忽略此条件
	 * @param tel 可模糊查询，如果为空，则忽略此条件
	 * @return
	 */
	int countCmpOrderTableByCdn(long tableId, Date beginTime, Date endTime,
			String name, String tel);

	/**
	 * @param tableId
	 * @param beginTime
	 * @param endTime
	 * @param name 可模糊查询，如果为空，则忽略此条件
	 * @param tel 可模糊查询，如果为空，则忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpOrderTable> getCmpOrderTableListByCdn(long tableId, Date beginTime,
			Date endTime, String name, String tel, int begin, int size);

	void updateCmpOrderTableObjStatus(long oid, byte objstatus);

	/**
	 * 设置用户就餐，修改餐桌状态为正在使用
	 * 
	 * @param oid
	 */
	void updateCmpOrderTableObjStatusMeal(long oid);

	CmpOrderTable getCmpOrderTable(long oid);

	Map<Long, Long> getCmpTableIdMapByCompanyIdForTodayBooked(long companyId);

	int countCmpOrderTableByCompanyIdForBookedNotMeal(long companyId, Date date);

	List<CmpOrderTable> getCmpOrderTableListByCompanyIdForBookedNotMeal(
			long companyId, Date date, int begin, int size);

	List<CmpTablePhoto> getCmpTablePhotoListBySetId(long setId);

	List<CmpTablePhoto> getCmpTablePhotoListInId(List<Long> idList);

	void createCmpTablePhotoSet(CmpTablePhotoSet cmpTablePhotoSet);

	void updateCmpTablePhotoSet(CmpTablePhotoSet cmpTablePhotoSet);

	CmpTablePhotoSet getCmpTablePhotoSet(long setId);

	int countCmpTablePhotoSetByCompanyId(long companyId);

	List<CmpTablePhotoSet> getCmpTablePhotoSetListByCompanyId(long companyId,
			int begin, int size);

	void updateCmpTablePhoto(CmpTablePhoto cmpTablePhoto);

	/**
	 * 删除图集，图集图片记录也要删除
	 * 
	 * @param setId
	 */
	void deleteCmpTablePhotoSet(long setId);

	void updateCmpTableSetId(long tableId, long setId);

	List<CmpTablePhotoSet> getCmpTablePhotoSetListInId(List<Long> idList);

	Map<Long, CmpTablePhotoSet> getCmpTablePhotoSetMapInId(List<Long> idList);
}