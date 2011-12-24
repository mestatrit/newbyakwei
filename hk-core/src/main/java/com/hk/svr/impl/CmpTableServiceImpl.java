package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpOrderTable;
import com.hk.bean.CmpPersonTable;
import com.hk.bean.CmpTable;
import com.hk.bean.CmpTableDailyData;
import com.hk.bean.CmpTablePhoto;
import com.hk.bean.CmpTablePhotoSet;
import com.hk.bean.CmpTableSort;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpTableService;

public class CmpTableServiceImpl implements CmpTableService {

	@Autowired
	private QueryManager manager;

	public int countCmpTable(long companyId, long sortId, String tableNum) {
		StringBuilder sql = new StringBuilder(
				"select count(*) from cmptable where companyid=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(companyId);
		if (sortId > 0) {
			sql.append(" and sortid=?");
			olist.add(sortId);
		}
		if (!DataUtil.isEmpty(tableNum)) {
			sql.append(" and tablenum like ?");
			olist.add("%" + tableNum + "%");
		}
		Query query = this.manager.createQuery();
		return query.countBySql("ds1", sql.toString(), olist);
	}

	public boolean createCmpTable(CmpTable cmpTable) {
		if (cmpTable.getCompanyId() <= 0) {
			throw new RuntimeException("companyid can not set 0");
		}
		if (cmpTable.getSortId() <= 0) {
			throw new RuntimeException("sortid can not set ");
		}
		CmpTable o = this.getCmpTableByCompanyIdAndTableNum(cmpTable
				.getCompanyId(), cmpTable.getTableNum());
		if (o != null) {
			return false;
		}
		cmpTable.setFreeflg(CmpTable.FREEFLG_FREE);
		Query query = manager.createQuery();
		query.addField("tableid", cmpTable.getTableId());
		query.addField("sortid", cmpTable.getSortId());
		query.addField("companyid", cmpTable.getCompanyId());
		query.addField("tablenum", cmpTable.getTableNum());
		query.addField("intro", cmpTable.getIntro());
		query.addField("bestpersonnum", cmpTable.getBestPersonNum());
		query.addField("mostpersonnum", cmpTable.getMostPersonNum());
		query.addField("leastprice", cmpTable.getLeastPrice());
		query.addField("opname", cmpTable.getOpname());
		query.addField("netorderflg", cmpTable.getNetOrderflg());
		query.addField("freeflg", cmpTable.getFreeflg());
		query.addField("orderflg", cmpTable.getOrderflg());
		long tableId = query.insert(CmpTable.class).longValue();
		cmpTable.setTableId(tableId);
		this.updateCmpPersonTableData(cmpTable.getCompanyId(), 0, cmpTable
				.getSortId(), 0, cmpTable.getBestPersonNum());
		return true;
	}

	/**
	 * 当创建或更新台面理想就餐人数时,需要调用此方法更新台面汇总数据表
	 * 
	 * @param companyId
	 * @param oldPersonNum
	 * @param newPersonNum
	 */
	private void updateCmpPersonTableData(long companyId, long old_sortId,
			long new_sortId, int oldPersonNum, int newPersonNum) {
		if (newPersonNum > 0) {
			Query query = manager.createQuery();
			CmpPersonTable new_obj = query.getObjectEx(CmpPersonTable.class,
					"companyid=? and sortid=? and personnum=?", new Object[] {
							companyId, new_sortId, newPersonNum });
			if (new_obj == null) {
				// create
				new_obj = new CmpPersonTable();
				new_obj.setCompanyId(companyId);
				new_obj.setPersonNum(newPersonNum);
				new_obj.setFreeCount(1);
				new_obj.setTotalCount(1);
				new_obj.setSortId(new_sortId);
				createCmpPersonTable(new_obj);
			}
			else {
				// update
				new_obj.setTotalCount(this.countTable(companyId, new_sortId,
						newPersonNum));
				new_obj.setFreeCount(this.countFreeTable(companyId, new_sortId,
						newPersonNum));
				this.updateCmpPersonTable(new_obj);
			}
		}
		if (oldPersonNum > 0) {// 当更新台面时,执行此流程
			Query query = manager.createQuery();
			CmpPersonTable old_obj = query.getObjectEx(CmpPersonTable.class,
					"companyid=? and sortid=? and personnum=?", new Object[] {
							companyId, old_sortId, oldPersonNum });
			if (old_obj == null) {
				old_obj = new CmpPersonTable();
				old_obj.setCompanyId(companyId);
				old_obj.setPersonNum(oldPersonNum);
				old_obj.setFreeCount(1);
				old_obj.setTotalCount(1);
				old_obj.setSortId(old_sortId);
				createCmpPersonTable(old_obj);
			}
			else {
				old_obj.setFreeCount(this.countFreeTable(companyId, old_sortId,
						oldPersonNum));
				old_obj.setTotalCount(this.countTable(companyId, old_sortId,
						oldPersonNum));
				if (old_obj.getTotalCount() < 0) {
					old_obj.setTotalCount(0);
				}
				this.updateCmpPersonTable(old_obj);
			}
		}
	}

	private int countFreeTable(long companyId, long sortId, int personCount) {
		Query query = manager.createQuery();
		return query.count(CmpTable.class,
				"companyid=? and sortid=? and bestpersonnum=? and freeflg=?",
				new Object[] { companyId, sortId, personCount,
						CmpTable.FREEFLG_FREE });
	}

	private int countTable(long companyId, long sortId, int personCount) {
		Query query = manager.createQuery();
		return query.count(CmpTable.class,
				"companyid=? and sortid=? and bestpersonnum=?", new Object[] {
						companyId, sortId, personCount });
	}

	private void createCmpPersonTable(CmpPersonTable cmpPersonTable) {
		Query query = manager.createQuery();
		query.addField("companyid", cmpPersonTable.getCompanyId());
		query.addField("personnum", cmpPersonTable.getPersonNum());
		query.addField("freecount", cmpPersonTable.getFreeCount());
		query.addField("totalcount", cmpPersonTable.getTotalCount());
		query.addField("sortid", cmpPersonTable.getSortId());
		long oid = query.insert(CmpPersonTable.class).longValue();
		cmpPersonTable.setOid(oid);
	}

	private void updateCmpPersonTable(CmpPersonTable cmpPersonTable) {
		Query query = manager.createQuery();
		query.addField("companyid", cmpPersonTable.getCompanyId());
		query.addField("personnum", cmpPersonTable.getPersonNum());
		query.addField("freecount", cmpPersonTable.getFreeCount());
		query.addField("totalcount", cmpPersonTable.getTotalCount());
		query.addField("sortid", cmpPersonTable.getSortId());
		query.updateById(CmpPersonTable.class, cmpPersonTable.getOid());
	}

	public CmpTable getCmpTableByCompanyIdAndTableNum(long companyId,
			String tableNum) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpTable.class, "companyid=? and tablenum=?",
				new Object[] { companyId, tableNum });
	}

	public boolean createCmpTableSort(CmpTableSort cmpTableSort) {
		if (cmpTableSort.getCompanyId() <= 0) {
			throw new RuntimeException("companyid can not set 0");
		}
		CmpTableSort o = this.getCmpTableSortByCompanyIdAndName(cmpTableSort
				.getCompanyId(), cmpTableSort.getName());
		if (o != null) {
			return false;
		}
		Query query = manager.createQuery();
		query.addField("sortid", cmpTableSort.getSortId());
		query.addField("companyid", cmpTableSort.getCompanyId());
		query.addField("name", cmpTableSort.getName());
		long sortId = query.insert(CmpTableSort.class).longValue();
		cmpTableSort.setSortId(sortId);
		return true;
	}

	private CmpTableSort getCmpTableSortByCompanyIdAndName(long companyId,
			String name) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpTableSort.class, "companyid=? and name=?",
				new Object[] { companyId, name });
	}

	public void deleteCmpTable(long tableId) {
		CmpTable table = this.getCmpTable(tableId);
		if (table == null) {
			return;
		}
		Query query = manager.createQuery();
		query.deleteById(CmpTable.class, tableId);
		this.updateCmpPersonTableData(table.getCompanyId(), table.getSortId(),
				0, table.getBestPersonNum(), 0);
	}

	public void deleteCmpTableSort(long sortId) {
		CmpTableSort o = this.getCmpTableSort(sortId);
		if (o == null) {
			return;
		}
		Query query = manager.createQuery();
		query.deleteById(CmpTableSort.class, sortId);
		List<CmpTable> list = this.getCmpTableList(o.getCompanyId(), sortId,
				null, -1, -1);
		for (CmpTable cmpTable : list) {
			query.addField("sortid", 0);
			query.updateById(CmpTable.class, cmpTable.getTableId());
		}
		// 删除persontable
		query.delete(CmpPersonTable.class, "sortid=?", new Object[] { sortId });
	}

	public CmpTable getCmpTable(long tableId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpTable.class, tableId);
	}

	public List<CmpTable> getCmpTableList(long companyId, long sortId,
			String tableNum, int begin, int size) {
		StringBuilder sql = new StringBuilder(
				"select * from cmptable where companyid=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(companyId);
		if (sortId > 0) {
			sql.append(" and sortid=?");
			olist.add(sortId);
		}
		if (!DataUtil.isEmpty(tableNum)) {
			sql.append(" and tablenum like ?");
			olist.add("%" + tableNum + "%");
		}
		sql.append(" order by orderflg asc");
		Query query = this.manager.createQuery();
		if (begin < 0 || size <= 0) {
			return query.listBySqlParamList("ds1", sql.toString(),
					CmpTable.class, olist);
		}
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				CmpTable.class, olist);
	}

	public List<CmpTable> getCmpTableList(long companyId, long sortId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpTable.class, "companyid=? and sortid=?",
				new Object[] { companyId, sortId }, "orderflg asc");
	}

	public CmpTableSort getCmpTableSort(long sortId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpTableSort.class, sortId);
	}

	public List<CmpTableSort> getCmpTableSortListByCompanyId(long companyId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpTableSort.class, "companyid=?",
				new Object[] { companyId }, "sortid desc");
	}

	public boolean updateCmpTable(CmpTable cmpTable) {
		CmpTable old = this.getCmpTable(cmpTable.getTableId());
		CmpTable o = this.getCmpTableByCompanyIdAndTableNum(cmpTable
				.getCompanyId(), cmpTable.getTableNum());
		if (o != null && o.getTableId() != cmpTable.getTableId()) {
			return false;
		}
		Query query = manager.createQuery();
		query.addField("tableid", cmpTable.getTableId());
		query.addField("sortid", cmpTable.getSortId());
		query.addField("companyid", cmpTable.getCompanyId());
		query.addField("tablenum", cmpTable.getTableNum());
		query.addField("intro", cmpTable.getIntro());
		query.addField("bestpersonnum", cmpTable.getBestPersonNum());
		query.addField("mostpersonnum", cmpTable.getMostPersonNum());
		query.addField("leastprice", cmpTable.getLeastPrice());
		query.addField("opname", cmpTable.getOpname());
		query.addField("netorderflg", cmpTable.getNetOrderflg());
		query.addField("freeflg", cmpTable.getFreeflg());
		query.addField("orderflg", cmpTable.getOrderflg());
		query.updateById(CmpTable.class, cmpTable.getTableId());
		this.updateCmpPersonTableData(cmpTable.getCompanyId(), old.getSortId(),
				cmpTable.getSortId(), old.getBestPersonNum(), cmpTable
						.getBestPersonNum());
		return true;
	}

	public boolean updateCmpTableSort(CmpTableSort cmpTableSort) {
		CmpTableSort o = this.getCmpTableSortByCompanyIdAndName(cmpTableSort
				.getCompanyId(), cmpTableSort.getName());
		if (o != null && o.getSortId() != cmpTableSort.getSortId()) {
			return false;
		}
		Query query = manager.createQuery();
		query.addField("sortid", cmpTableSort.getSortId());
		query.addField("companyid", cmpTableSort.getCompanyId());
		query.addField("name", cmpTableSort.getName());
		query.updateById(CmpTableSort.class, cmpTableSort.getSortId());
		return true;
	}

	public List<CmpTableSort> getCmpTableSortListInId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<CmpTableSort>();
		}
		StringBuilder sql = new StringBuilder(
				"select * from cmptablesort where sortid in(");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		Query query = manager.createQuery();
		return query.listBySqlEx("ds1", sql.toString(), CmpTableSort.class);
	}

	public Map<Long, CmpTableSort> getCmpTableSortMapInId(List<Long> idList) {
		List<CmpTableSort> list = this.getCmpTableSortListInId(idList);
		Map<Long, CmpTableSort> map = new HashMap<Long, CmpTableSort>();
		for (CmpTableSort sort : list) {
			map.put(sort.getSortId(), sort);
		}
		return map;
	}

	public void createCmpTablePhoto(CmpTablePhoto cmpTablePhoto) {
		this.validateCmpTablePhoto(cmpTablePhoto);
		Query query = manager.createQuery();
		query.addField("photoid", cmpTablePhoto.getPhotoId());
		query.addField("companyid", cmpTablePhoto.getCompanyId());
		query.addField("setid", cmpTablePhoto.getSetId());
		query.addField("path", cmpTablePhoto.getPath());
		query.addField("name", cmpTablePhoto.getName());
		long oid = query.insert(CmpTablePhoto.class).longValue();
		cmpTablePhoto.setOid(oid);
	}

	public void deleteCmpTablePhoto(long oid) {
		Query query = manager.createQuery();
		query.deleteById(CmpTablePhoto.class, oid);
	}

	public List<CmpTablePhoto> getCmpTablePhotoListByTableId(long tableId) {
		Query query = manager.createQuery();
		return query.listEx(CmpTablePhoto.class, "tableid=?",
				new Object[] { tableId }, "oid desc");
	}

	public CmpTablePhoto getCmpTablePhoto(long oid) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpTablePhoto.class, oid);
	}

	public List<CmpTable> getCmpTableByCompanyIdAndBestPersonNum(
			long companyId, long sortId, int bestPersonNum) {
		Query query = manager.createQuery();
		if (bestPersonNum <= 0 || sortId <= 0) {
			return query.listEx(CmpTable.class, "companyid=?",
					new Object[] { companyId }, "orderflg asc");
		}
		return query.listEx(CmpTable.class,
				"companyid=? and sortid=? and bestpersonnum=?", new Object[] {
						companyId, sortId, bestPersonNum }, "orderflg asc");
	}

	public void updateCmpTableFreeflg(long tableId, byte freeflg) {
		Query query = manager.createQuery();
		query.addField("freeflg", freeflg);
		query.updateById(CmpTable.class, tableId);
	}

	public List<CmpPersonTable> getCmpPersonTableListByCompanyId(long companyId) {
		Query query = manager.createQuery();
		return query.listEx(CmpPersonTable.class, "companyid=?",
				new Object[] { companyId }, "personnum asc");
	}

	public List<CmpPersonTable> getCmpPersonTableListByCompanyIdForTotalCountNotZero(
			long companyId) {
		Query query = manager.createQuery();
		return query.listEx(CmpPersonTable.class,
				"companyid=? and totalcount>?", new Object[] { companyId, 0 },
				"personnum asc");
	}

	public void createCmpOrderTable(CmpOrderTable cmpOrderTable) {
		if (cmpOrderTable.getCompanyId() <= 0) {
			throw new RuntimeException("invalid companyId");
		}
		if (cmpOrderTable.getTableId() <= 0) {
			throw new RuntimeException("invalid tableId");
		}
		Query query = manager.createQuery();
		query.addField("tableid", cmpOrderTable.getTableId());
		query.addField("begintime", cmpOrderTable.getBeginTime());
		query.addField("endtime", cmpOrderTable.getEndTime());
		query.addField("name", cmpOrderTable.getName());
		query.addField("tel", cmpOrderTable.getTel());
		query.addField("remark", cmpOrderTable.getRemark());
		query.addField("personnum", cmpOrderTable.getPersonNum());
		query.addField("objstatus", cmpOrderTable.getObjstatus());
		query.addField("companyid", cmpOrderTable.getCompanyId());
		query.addField("mealtime", cmpOrderTable.getMealTime());
		long oid = query.insert(CmpOrderTable.class).longValue();
		cmpOrderTable.setOid(oid);
	}

	public void deleteCmpOrderTable(long oid) {
		Query query = manager.createQuery();
		query.deleteById(CmpOrderTable.class, oid);
	}

	public void updateCmpOrderTable(CmpOrderTable cmpOrderTable) {
		if (cmpOrderTable.getCompanyId() <= 0) {
			throw new RuntimeException("invalid companyId");
		}
		if (cmpOrderTable.getTableId() <= 0) {
			throw new RuntimeException("invalid tableId");
		}
		Query query = manager.createQuery();
		query.addField("tableid", cmpOrderTable.getTableId());
		query.addField("begintime", cmpOrderTable.getBeginTime());
		query.addField("endtime", cmpOrderTable.getEndTime());
		query.addField("name", cmpOrderTable.getName());
		query.addField("tel", cmpOrderTable.getTel());
		query.addField("remark", cmpOrderTable.getRemark());
		query.addField("personnum", cmpOrderTable.getPersonNum());
		query.addField("objstatus", cmpOrderTable.getObjstatus());
		query.addField("companyid", cmpOrderTable.getCompanyId());
		query.addField("mealtime", cmpOrderTable.getMealTime());
		query.updateById(CmpOrderTable.class, cmpOrderTable.getOid());
	}

	public int calculateDailyCmpTableDataByDate(long companyId, long sortId,
			Date beginDate, Date endDate) {
		String sql = null;
		Object[] param = null;
		if (sortId > 0) {
			sql = "select sum(pbcount) from cmptabledailydata where companyid=? and sortid=? and createdate>=? and createdate<=?";
			param = new Object[] { companyId, sortId, beginDate, endDate };
		}
		else {
			sql = "select sum(pbcount) from cmptabledailydata where companyid=? and createdate>=? and createdate<=?";
			param = new Object[] { companyId, beginDate, endDate };
		}
		Query query = manager.createQuery();
		List<Object[]> list = query.listdata("ds1", sql, param);
		if (list.size() == 0) {
			return 0;
		}
		Object[] obj = list.get(0);
		if (obj[0] == null) {
			return 0;
		}
		return Integer.parseInt(obj[0].toString());
	}

	private void createCmpTableDailyData(CmpTableDailyData cmpTableDailyData) {
		Query query = manager.createQuery();
		query.addField("companyid", cmpTableDailyData.getCompanyId());
		query.addField("sortid", cmpTableDailyData.getSortId());
		query.addField("tableid", cmpTableDailyData.getTableId());
		query.addField("pbcount", cmpTableDailyData.getPbcount());
		query.addField("createdate", cmpTableDailyData.getCreateDate());
		long dataId = query.insert(CmpTableDailyData.class).longValue();
		cmpTableDailyData.setDataId(dataId);
	}

	/**
	 * 获得此餐厅当天的餐桌统计记录
	 * 
	 * @param companyId
	 * @param sortId
	 * @param tableId
	 * @param createDate 当天的时间
	 * @return
	 */
	private CmpTableDailyData getCmpTableDailyDataByDailyInfo(long companyId,
			long sortId, long tableId, Date createDate) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpTableDailyData.class,
				"companyid=? and sortid=? and tableid=? and createdate=?",
				new Object[] { companyId, sortId, tableId,
						DataUtil.getDate(createDate) });
	}

	private void updatePbcount(long dataId, int pbcount) {
		Query query = manager.createQuery();
		query.addField("pbcount", pbcount);
		query.updateById(CmpTableDailyData.class, dataId);
	}

	private int countCmpOrderTableByTableIdAndMealTime(long tableId,
			Date beginTime, Date endTime) {
		Query query = manager.createQuery();
		return query.count(CmpOrderTable.class,
				"tableid=? and mealtime>=? and mealtime<=?", new Object[] {
						tableId, beginTime, endTime });
	}

	public void updateCmpOrderTableObjStatus(long oid, byte objstatus) {
		Query query = manager.createQuery();
		query.addField("objstatus", objstatus);
		query.updateById(CmpOrderTable.class, oid);
	}

	private void updateDailyData(CmpOrderTable cmpOrderTable) {
		if (cmpOrderTable == null) {
			return;
		}
		CmpTable cmpTable = this.getCmpTable(cmpOrderTable.getTableId());
		this.updateCmpTableFreeflg(cmpOrderTable.getTableId(),
				CmpTable.FREEFLG_INUSE);
		Date mealTime = DataUtil.getDate(new Date());
		CmpTableDailyData dailyData = this.getCmpTableDailyDataByDailyInfo(
				cmpOrderTable.getCompanyId(), cmpTable.getSortId(), cmpTable
						.getTableId(), mealTime);
		if (dailyData == null) {
			dailyData = new CmpTableDailyData();
			dailyData.setCompanyId(cmpTable.getCompanyId());
			dailyData.setPbcount(1);
			dailyData.setSortId(cmpTable.getSortId());
			dailyData.setTableId(cmpTable.getTableId());
			dailyData.setCreateDate(mealTime);
			this.createCmpTableDailyData(dailyData);
		}
		else {
			Date beginTime = mealTime;
			Calendar cal = Calendar.getInstance();
			cal.setTime(beginTime);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			Date endTime = cal.getTime();
			int pbcount = this.countCmpOrderTableByTableIdAndMealTime(cmpTable
					.getTableId(), beginTime, endTime);
			this.updatePbcount(dailyData.getDataId(), pbcount);
		}
	}

	public void updateCmpOrderTableObjStatusMeal(long oid) {
		Query query = manager.createQuery();
		query.addField("objstatus", CmpOrderTable.OBJSTATUS_HAVINGMAILS);
		query.addField("mealtime", new Date());
		query.updateById(CmpOrderTable.class, oid);
		this.updateDailyData(this.getCmpOrderTable(oid));
		CmpOrderTable cmpOrderTable = this.getCmpOrderTable(oid);
		if (cmpOrderTable == null) {
			return;
		}
		this.updateCmpTableFreeflg(cmpOrderTable.getTableId(),
				CmpTable.FREEFLG_INUSE);
	}

	public CmpOrderTable getCmpOrderTable(long oid) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpOrderTable.class, oid);
	}

	public int countCmpOrderTableByCdn(long tableId, Date beginTime,
			Date endTime, String name, String tel) {
		StringBuilder sql = new StringBuilder(
				"select count(*) from cmpordertable where tableid=? and begintime>=? and begintime<=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(tableId);
		olist.add(beginTime);
		olist.add(endTime);
		if (name != null) {
			sql.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		if (tel != null) {
			sql.append(" and tel like ?");
			olist.add("%" + tel + "%");
		}
		Query query = manager.createQuery();
		return query.countBySql("ds1", sql.toString(), olist);
	}

	public List<CmpOrderTable> getCmpOrderTableListByCdn(long tableId,
			Date beginTime, Date endTime, String name, String tel, int begin,
			int size) {
		StringBuilder sql = new StringBuilder(
				"select * from cmpordertable where tableid=? and begintime>=? and begintime<=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(tableId);
		olist.add(beginTime);
		olist.add(endTime);
		if (name != null) {
			sql.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		if (tel != null) {
			sql.append(" and tel like ?");
			olist.add("%" + tel + "%");
		}
		sql.append(" order by begintime desc");
		Query query = manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				CmpOrderTable.class, olist);
	}

	public Map<Long, Long> getCmpTableIdMapByCompanyIdForTodayBooked(
			long companyId) {
		Date beginTime = DataUtil.getDate(new Date());
		Date endTime = DataUtil.getEndDate(new Date());
		String sql = "select distinct tableid from cmpordertable where companyid=? and objstatus=? and begintime>=? and begintime<=?";
		Query query = manager.createQuery();
		List<Object[]> list = query.listdata("ds1", sql, companyId,
				CmpOrderTable.OBJSTATUS_BOOKED, beginTime, endTime);
		Map<Long, Long> map = new HashMap<Long, Long>();
		for (Object[] obj : list) {
			Long id = Long.parseLong(obj[0].toString());
			map.put(id, id);
		}
		return map;
	}

	public int countCmpOrderTableByCompanyIdForBookedNotMeal(long companyId,
			Date date) {
		Query query = manager.createQuery();
		return query.count(CmpOrderTable.class,
				"companyid=? and begintime<=? and objstatus=?", new Object[] {
						companyId, date, CmpOrderTable.OBJSTATUS_BOOKED });
	}

	public List<CmpOrderTable> getCmpOrderTableListByCompanyIdForBookedNotMeal(
			long companyId, Date date, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpOrderTable.class,
				"companyid=? and begintime<=? and objstatus=?", new Object[] {
						companyId, date, CmpOrderTable.OBJSTATUS_BOOKED },
				"tableid desc", begin, size);
	}

	public List<CmpTable> getCmpTableListInId(long companyId, List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<CmpTable>();
		}
		StringBuilder sql = new StringBuilder(
				"select * from cmptable where companyid=? and tableid in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		Query query = manager.createQuery();
		return query.listBySqlEx("ds1", sql.toString(), CmpTable.class,
				companyId);
	}

	public Map<Long, CmpTable> getCmpTableMapInId(long companyId,
			List<Long> idList) {
		List<CmpTable> list = this.getCmpTableListInId(companyId, idList);
		Map<Long, CmpTable> map = new HashMap<Long, CmpTable>();
		for (CmpTable o : list) {
			map.put(o.getTableId(), o);
		}
		return map;
	}

	public void createCmpTablePhotoSet(CmpTablePhotoSet cmpTablePhotoSet) {
		this.validateCmpTablePhotoSet(cmpTablePhotoSet);
		Query query = manager.createQuery();
		query.addField("companyid", cmpTablePhotoSet.getCompanyId());
		query.addField("title", cmpTablePhotoSet.getTitle());
		query.addField("intro", cmpTablePhotoSet.getIntro());
		query.addField("path", cmpTablePhotoSet.getPath());
		long setId = query.insert(CmpTablePhotoSet.class).longValue();
		cmpTablePhotoSet.setSetId(setId);
	}

	public List<CmpTablePhoto> getCmpTablePhotoListBySetId(long setId) {
		Query query = manager.createQuery();
		return query.listEx(CmpTablePhoto.class, "setid=?",
				new Object[] { setId }, "oid desc");
	}

	private void validateCmpTablePhotoSet(CmpTablePhotoSet cmpTablePhotoSet) {
		if (cmpTablePhotoSet.getCompanyId() <= 0) {
			throw new RuntimeException("invalid companyId");
		}
	}

	public void updateCmpTablePhotoSet(CmpTablePhotoSet cmpTablePhotoSet) {
		this.validateCmpTablePhotoSet(cmpTablePhotoSet);
		Query query = manager.createQuery();
		query.addField("companyid", cmpTablePhotoSet.getCompanyId());
		query.addField("title", cmpTablePhotoSet.getTitle());
		query.addField("intro", cmpTablePhotoSet.getIntro());
		query.addField("path", cmpTablePhotoSet.getPath());
		query.updateById(CmpTablePhotoSet.class, cmpTablePhotoSet.getSetId());
	}

	public CmpTablePhotoSet getCmpTablePhotoSet(long setId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpTablePhotoSet.class, setId);
	}

	public List<CmpTablePhoto> getCmpTablePhotoListInId(List<Long> idList) {
		Query query = manager.createQuery();
		if (idList.size() == 0) {
			return new ArrayList<CmpTablePhoto>();
		}
		StringBuilder sql = new StringBuilder(
				"select * from cmptablephoto where oid in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		return query.listBySqlEx("ds1", sql.toString(), CmpTablePhoto.class);
	}

	private void validateCmpTablePhoto(CmpTablePhoto cmpTablePhoto) {
		if (cmpTablePhoto.getCompanyId() <= 0) {
			throw new RuntimeException("invalid companyId");
		}
		if (cmpTablePhoto.getSetId() <= 0) {
			throw new RuntimeException("invalid setId");
		}
		if (cmpTablePhoto.getPhotoId() <= 0) {
			throw new RuntimeException("invalid photoId");
		}
	}

	public void updateCmpTablePhoto(CmpTablePhoto cmpTablePhoto) {
		this.validateCmpTablePhoto(cmpTablePhoto);
		Query query = manager.createQuery();
		query.addField("photoid", cmpTablePhoto.getPhotoId());
		query.addField("companyid", cmpTablePhoto.getCompanyId());
		query.addField("setid", cmpTablePhoto.getSetId());
		query.addField("path", cmpTablePhoto.getPath());
		query.addField("name", cmpTablePhoto.getName());
		query.updateById(CmpTablePhoto.class, cmpTablePhoto.getOid());
	}

	public List<CmpTablePhotoSet> getCmpTablePhotoSetListByCompanyId(
			long companyId, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpTablePhotoSet.class, "companyid=?",
				new Object[] { companyId }, "setid desc", begin, size);
	}

	public int countCmpTablePhotoSetByCompanyId(long companyId) {
		Query query = manager.createQuery();
		return query.count(CmpTablePhotoSet.class, "companyid=?",
				new Object[] { companyId });
	}

	public void deleteCmpTablePhotoSet(long setId) {
		Query query = manager.createQuery();
		query.deleteById(CmpTablePhotoSet.class, setId);
		query.delete(CmpTablePhoto.class, "setid=?", new Object[] { setId });
	}

	public void updateCmpTableSetId(long tableId, long setId) {
		Query query = manager.createQuery();
		query.addField("setid", setId);
		query.updateById(CmpTable.class, tableId);
	}

	public List<CmpTablePhotoSet> getCmpTablePhotoSetListInId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<CmpTablePhotoSet>();
		}
		StringBuilder sql = new StringBuilder(
				"select * from cmptablephotoset where setid in(");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		Query query = manager.createQuery();
		return query.listBySqlEx("ds1", sql.toString(), CmpTablePhotoSet.class);
	}

	public Map<Long, CmpTablePhotoSet> getCmpTablePhotoSetMapInId(
			List<Long> idList) {
		List<CmpTablePhotoSet> list = this.getCmpTablePhotoSetListInId(idList);
		Map<Long, CmpTablePhotoSet> map = new HashMap<Long, CmpTablePhotoSet>();
		for (CmpTablePhotoSet set : list) {
			map.put(set.getSetId(), set);
		}
		return map;
	}
}