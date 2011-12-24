package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.Tb_User;
import com.hk.bean.taobao.Tb_UserItemDailyReport;
import com.hk.bean.taobao.Tb_UserItemMonthReport;
import com.hk.bean.taobao.Tb_UserItemWeekReport;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.Tb_UserItemReportService;
import com.hk.svr.Tb_UserService;

public class Tb_UserItemReportServiceImpl implements Tb_UserItemReportService {

	@Autowired
	private QueryManager manager;

	@Autowired
	private Tb_UserService tbUserService;

	@Override
	public void deleteTb_UserItemDailyReport(Date minDate, Date maxDate) {
		this.manager.createQuery().delete(Tb_UserItemDailyReport.class,
				"create_date>=? and create_date<=?",
				new Object[] { minDate, maxDate });
	}

	@Override
	public void deleteTb_UserItemMonthReport(Date minDate, Date maxDate) {
		this.manager.createQuery().delete(Tb_UserItemDailyReport.class,
				"create_month>=? and create_month<=?",
				new Object[] { minDate, maxDate });
	}

	@Override
	public void deleteTb_UserItemWeekReport(Date minDate, Date maxDate) {
		this.manager.createQuery().delete(Tb_UserItemDailyReport.class,
				"create_week>=? and create_week<=?",
				new Object[] { minDate, maxDate });
	}

	@Override
	public void saveTb_UserItemDailyReport(long userid, int addItemNum,
			Date createDate) {
		Query query = this.manager.createQuery();
		// 每星期数据处理
		this.processDaily(query, userid, addItemNum, createDate);
		// 每日数据处理
		this.processWeek(query, userid, addItemNum, createDate);
		// 每月数据处理
		this.processMonth(query, userid, addItemNum, createDate);
	}

	private void processDaily(Query query, long userid, int addItemNum,
			Date createDate) {
		Date date = DataUtil.getDate(createDate);
		// 每日数据处理
		Tb_UserItemDailyReport tbUserItemDailyReport = query.getObjectEx(
				Tb_UserItemDailyReport.class, "create_date=?",
				new Object[] { date });
		if (tbUserItemDailyReport == null) {
			tbUserItemDailyReport = new Tb_UserItemDailyReport();
			tbUserItemDailyReport.setUserid(userid);
			tbUserItemDailyReport.setItem_num(addItemNum);
			tbUserItemDailyReport.setCreate_date(date);
			tbUserItemDailyReport.setOid(query.insertObject(
					tbUserItemDailyReport).longValue());
		}
		else {
			tbUserItemDailyReport.setItem_num(tbUserItemDailyReport
					.getItem_num()
					+ addItemNum);
			query.updateObject(tbUserItemDailyReport);
		}
	}

	private void processMonth(Query query, long userid, int addItemNum,
			Date createDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(createDate);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date date_month = DataUtil.getDate(cal.getTime());
		Tb_UserItemMonthReport tbUserItemMonthReport = query.getObjectEx(
				Tb_UserItemMonthReport.class, "create_month=?",
				new Object[] { date_month });
		if (tbUserItemMonthReport == null) {
			tbUserItemMonthReport = new Tb_UserItemMonthReport();
			tbUserItemMonthReport.setUserid(userid);
			tbUserItemMonthReport.setItem_num(addItemNum);
			tbUserItemMonthReport.setCreate_month(date_month);
			tbUserItemMonthReport.setOid(query.insertObject(
					tbUserItemMonthReport).longValue());
		}
		else {
			tbUserItemMonthReport.setItem_num(tbUserItemMonthReport
					.getItem_num()
					+ addItemNum);
			query.updateObject(tbUserItemMonthReport);
		}
	}

	private void processWeek(Query query, long userid, int addItemNum,
			Date createDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(createDate);
		cal.set(Calendar.DAY_OF_WEEK, 1);
		Date date_week = DataUtil.getDate(cal.getTime());
		Tb_UserItemWeekReport tbUserItemWeekReport = query.getObjectEx(
				Tb_UserItemWeekReport.class, "create_week=?",
				new Object[] { date_week });
		if (tbUserItemWeekReport == null) {
			tbUserItemWeekReport = new Tb_UserItemWeekReport();
			tbUserItemWeekReport.setUserid(userid);
			tbUserItemWeekReport.setItem_num(addItemNum);
			tbUserItemWeekReport.setCreate_week(date_week);
			tbUserItemWeekReport.setOid(query
					.insertObject(tbUserItemWeekReport).longValue());
		}
		else {
			tbUserItemWeekReport.setItem_num(tbUserItemWeekReport.getItem_num()
					+ addItemNum);
			query.updateObject(tbUserItemWeekReport);
		}
	}

	@Override
	public List<Tb_UserItemDailyReport> getTb_UserItemDailyReportList(
			Date minDate, Date maxDate, boolean buildUser, int begin, int size) {
		List<Tb_UserItemDailyReport> list = this.manager.createQuery()
				.listEx(Tb_UserItemDailyReport.class,
						"create_date>=? and create_date<=?",
						new Object[] { minDate, maxDate }, "item_num desc",
						begin, size);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (Tb_UserItemDailyReport o : list) {
				idList.add(o.getUserid());
			}
			Map<Long, Tb_User> map = this.tbUserService
					.getTb_UserMapInId(idList);
			for (Tb_UserItemDailyReport o : list) {
				o.setTbUser(map.get(o.getUserid()));
			}
		}
		return list;
	}

	@Override
	public List<Tb_UserItemMonthReport> getTb_UserItemMonthReportList(
			Date minDate, Date maxDate, boolean buildUser, int begin, int size) {
		List<Tb_UserItemMonthReport> list = this.manager.createQuery()
				.listEx(Tb_UserItemMonthReport.class,
						"create_month>=? and create_month<=?",
						new Object[] { minDate, maxDate }, "item_num desc",
						begin, size);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (Tb_UserItemMonthReport o : list) {
				idList.add(o.getUserid());
			}
			Map<Long, Tb_User> map = this.tbUserService
					.getTb_UserMapInId(idList);
			for (Tb_UserItemMonthReport o : list) {
				o.setTbUser(map.get(o.getUserid()));
			}
		}
		return list;
	}

	@Override
	public List<Tb_UserItemWeekReport> getTb_UserItemWeekReportList(
			Date minDate, Date maxDate, boolean buildUser, int begin, int size) {
		List<Tb_UserItemWeekReport> list = this.manager.createQuery()
				.listEx(Tb_UserItemWeekReport.class,
						"create_week>=? and create_week<=?",
						new Object[] { minDate, maxDate }, "item_num desc",
						begin, size);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (Tb_UserItemWeekReport o : list) {
				idList.add(o.getUserid());
			}
			Map<Long, Tb_User> map = this.tbUserService
					.getTb_UserMapInId(idList);
			for (Tb_UserItemWeekReport o : list) {
				o.setTbUser(map.get(o.getUserid()));
			}
		}
		return list;
	}
}