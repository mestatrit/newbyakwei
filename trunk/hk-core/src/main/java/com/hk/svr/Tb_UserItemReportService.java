package com.hk.svr;

import java.util.Date;
import java.util.List;

import com.hk.bean.taobao.Tb_UserItemDailyReport;
import com.hk.bean.taobao.Tb_UserItemMonthReport;
import com.hk.bean.taobao.Tb_UserItemWeekReport;

public interface Tb_UserItemReportService {

	/**
	 * 创建统计数据，对每日，星期，月的数据进行统计，如果已经存在指定日期的数据，只进行此数据中的更新
	 * 
	 * @param userid
	 * @param add_item_num
	 * @param create_date
	 *            2010-9-27
	 */
	void saveTb_UserItemDailyReport(long userid, int add_item_num,
			Date create_date);

	void deleteTb_UserItemDailyReport(Date min_date, Date max_date);

	void deleteTb_UserItemWeekReport(Date min_date, Date max_date);

	void deleteTb_UserItemMonthReport(Date min_date, Date max_date);

	List<Tb_UserItemDailyReport> getTb_UserItemDailyReportList(Date min_date,
			Date max_date, boolean buildUser, int begin, int size);

	List<Tb_UserItemWeekReport> getTb_UserItemWeekReportList(Date min_date,
			Date max_date, boolean buildUser, int begin, int size);

	List<Tb_UserItemMonthReport> getTb_UserItemMonthReportList(Date min_date,
			Date max_date, boolean buildUser, int begin, int size);
}