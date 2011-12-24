package com.hk.web.company.action.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpOrderTable;
import com.hk.bean.CmpTable;
import com.hk.bean.CmpTablePhotoSet;
import com.hk.bean.CmpTableSort;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpTableService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.web.company.action.DateInfo;
import com.hk.web.pub.action.BaseAction;

@Component("/e/op/auth/table")
public class OpCmpTableAction extends BaseAction {
	@Autowired
	private CmpTableService cmpTableService;

	/**
	 * 台面列表
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		long sortId = req.getLongAndSetAttr("sortId");
		String tableNum = req.getString("tableNum");
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.cmpTableService.countCmpTable(companyId,
				sortId, tableNum));
		List<CmpTable> list = this.cmpTableService.getCmpTableList(companyId,
				sortId, tableNum, page.getBegin(), page.getSize());
		List<Long> idList = new ArrayList<Long>();
		for (CmpTable table : list) {
			idList.add(table.getSortId());
		}
		Map<Long, CmpTableSort> map = this.cmpTableService
				.getCmpTableSortMapInId(idList);
		Map<Long, CmpTablePhotoSet> setmap = this.cmpTableService
				.getCmpTablePhotoSetMapInId(idList);
		for (CmpTable table : list) {
			table.setCmpTableSort(map.get(table.getSortId()));
			table.setCmpTablePhotoSet(setmap.get(table.getSetId()));
		}
		req.setAttribute("list", list);
		req.setEncodeAttribute("tableNum", tableNum);
		List<CmpTableSort> sortlist = this.cmpTableService
				.getCmpTableSortListByCompanyId(companyId);
		req.setAttribute("sortlist", sortlist);
		return this.getWeb3Jsp("e/table/op/tablelist.jsp");
	}

	/**
	 * 获取台面分类数据
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String loadsort(HkRequest req, HkResponse resp) throws Exception {
		long sortId = req.getLongAndSetAttr("sortId");
		CmpTableSort sort = this.cmpTableService.getCmpTableSort(sortId);
		req.setAttribute("sort", sort);
		req.reSetAttribute("companyId");
		return this.getWeb3Jsp("e/table/op/sort_inc.jsp");
	}

	/**
	 * 获取台面分类数据
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String loadtable(HkRequest req, HkResponse resp) throws Exception {
		long tableId = req.getLongAndSetAttr("tableId");
		CmpTable o = this.cmpTableService.getCmpTable(tableId);
		req.setAttribute("o", o);
		long companyId = req.getLongAndSetAttr("companyId");
		List<CmpTableSort> sortlist = this.cmpTableService
				.getCmpTableSortListByCompanyId(companyId);
		req.setAttribute("sortlist", sortlist);
		return this.getWeb3Jsp("e/table/op/table_inc.jsp");
	}

	/**
	 * 台面分类列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String sortlist(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		List<CmpTableSort> list = this.cmpTableService
				.getCmpTableSortListByCompanyId(companyId);
		req.setAttribute("list", list);
		return this.getWeb3Jsp("e/table/op/sortlist.jsp");
	}

	/**
	 * 创建台面分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createsort(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		String name = req.getString("name");
		CmpTableSort sort = new CmpTableSort();
		sort.setCompanyId(companyId);
		sort.setName(DataUtil.toHtmlRow(name));
		int code = sort.validate();
		if (code != Err.SUCCESS) {
			return this.initError(req, code, -1, null, "sort", "onsorterror",
					null);
		}
		if (this.cmpTableService.createCmpTableSort(sort)) {
			this.setOpFuncSuccessMsg(req);
			return this.initSuccess(req, "sort", "onsortsuccess", null);
		}
		return this.initError(req, Err.CMPTABLESORT_NAME_DUPLICATE, -1, null,
				"sort", "onsorterror", null);
	}

	/**
	 * 更新台面分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updatesort(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		long sortId = req.getLong("sortId");
		String name = req.getString("name");
		CmpTableSort sort = this.cmpTableService.getCmpTableSort(sortId);
		if (sort == null) {
			return null;
		}
		sort.setCompanyId(companyId);
		sort.setName(DataUtil.toHtmlRow(name));
		int code = sort.validate();
		if (code != Err.SUCCESS) {
			return this.initError(req, code, -1, null, "sort",
					"onsortupdateerror", null);
		}
		if (this.cmpTableService.updateCmpTableSort(sort)) {
			this.setOpFuncSuccessMsg(req);
			return this.initSuccess(req, "sort", "onsortupdatesuccess", null);
		}
		return this.initError(req, Err.CMPTABLESORT_NAME_DUPLICATE, -1, null,
				"sort", "onsortupdateerror", null);
	}

	/**
	 * 删除台面分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delsort(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		long sortId = req.getLong("sortId");
		CmpTableSort sort = this.cmpTableService.getCmpTableSort(sortId);
		if (sort == null) {
			return null;
		}
		if (sort.getCompanyId() == companyId) {
			this.cmpTableService.deleteCmpTableSort(sortId);
			this.setOpFuncSuccessMsg(req);
		}
		return null;
	}

	/**
	 * 创建台面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createtable(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		long sortId = req.getLong("sortId");
		String tableNum = req.getString("tableNum");
		String intro = req.getString("intro");
		String opname = req.getString("opname");
		int bestPersonNum = req.getInt("bestPersonNum");
		int mostPersonNum = req.getInt("mostPersonNum");
		int leastPrice = req.getInt("leastPrice");
		byte netOrderflg = req.getByte("netOrderflg", (byte) -1);
		int orderflg = req.getInt("orderflg");// 排序显示标志
		CmpTable table = new CmpTable();
		table.setOrderflg(orderflg);
		table.setCompanyId(companyId);
		table.setSortId(sortId);
		table.setTableNum(tableNum);
		table.setIntro(DataUtil.toHtmlRow(intro));
		table.setOpname(DataUtil.toHtmlRow(opname));
		table.setBestPersonNum(bestPersonNum);
		table.setMostPersonNum(mostPersonNum);
		table.setLeastPrice(leastPrice);
		table.setNetOrderflg(netOrderflg);
		int code = table.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "ontableerror", null);
		}
		if (this.cmpTableService.createCmpTable(table)) {
			this.setOpFuncSuccessMsg(req);
			return this.onSuccess2(req, "ontablesuccess", null);
		}
		return this.onError(req, Err.CMPTABLE_TABLENUM_DUPLICATE,
				"ontableerror", null);
	}

	/**
	 * 更新台面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updatetable(HkRequest req, HkResponse resp) throws Exception {
		long tableId = req.getLong("tableId");
		long companyId = req.getLongAndSetAttr("companyId");
		long sortId = req.getLong("sortId");
		String tableNum = req.getString("tableNum");
		String intro = req.getString("intro");
		String opname = req.getString("opname");
		int bestPersonNum = req.getInt("bestPersonNum");
		int mostPersonNum = req.getInt("mostPersonNum");
		int leastPrice = req.getInt("leastPrice");
		byte netOrderflg = req.getByte("netOrderflg");// 是否接受网络预订标志
		int orderflg = req.getInt("orderflg");// 排序显示标志
		CmpTable table = this.cmpTableService.getCmpTable(tableId);
		if (table == null) {
			return null;
		}
		table.setCompanyId(companyId);
		table.setOrderflg(orderflg);
		table.setSortId(sortId);
		table.setTableNum(tableNum);
		table.setIntro(DataUtil.toHtmlRow(intro));
		table.setOpname(DataUtil.toHtmlRow(opname));
		table.setBestPersonNum(bestPersonNum);
		table.setMostPersonNum(mostPersonNum);
		table.setLeastPrice(leastPrice);
		table.setNetOrderflg(netOrderflg);
		int code = table.validate();
		if (code != Err.SUCCESS) {
			return this.initError(req, code, -1, null, "table",
					"ontableupdateerror", null);
		}
		if (this.cmpTableService.updateCmpTable(table)) {
			this.setOpFuncSuccessMsg(req);
			return this.initSuccess(req, "sort", "ontableupdatesuccess", null);
		}
		return this.initError(req, Err.CMPTABLE_TABLENUM_DUPLICATE, -1, null,
				"table", "ontableupdateerror", null);
	}

	/**
	 *删除台面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String deltable(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long tableId = req.getLong("tableId");
		CmpTable table = this.cmpTableService.getCmpTable(tableId);
		if (table == null) {
			return null;
		}
		if (table.getCompanyId() != companyId) {
			return null;
		}
		this.cmpTableService.deleteCmpTable(tableId);
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 *到安排台面页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list2(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		int num = req.getIntAndSetAttr("num");
		long sortId = req.getLongAndSetAttr("sortId");
		List<CmpTable> list = this.cmpTableService
				.getCmpTableByCompanyIdAndBestPersonNum(companyId, sortId, num);
		Map<Long, Long> idMap = this.cmpTableService
				.getCmpTableIdMapByCompanyIdForTodayBooked(companyId);
		for (CmpTable o : list) {
			o.setBooked(idMap.containsKey(o.getTableId()));
		}
		req.setAttribute("list", list);
		this.loadTableListData(req);
		req.setReturnUrl(ServletUtil.getReturnUrl(req));
		// 查看当前时间有预约，但是没有到达的数据
		int booked_not_meal_count = this.cmpTableService
				.countCmpOrderTableByCompanyIdForBookedNotMeal(companyId,
						new Date());
		req.setAttribute("booked_not_meal_count", booked_not_meal_count);
		return this.getWeb3Jsp("e/table/op/tablelist2.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setinuse(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		this.cmpTableService.updateCmpOrderTableObjStatusMeal(oid);
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setfree(HkRequest req, HkResponse resp) throws Exception {
		long tableId = req.getLong("tableId");
		this.cmpTableService.updateCmpTableFreeflg(tableId,
				CmpTable.FREEFLG_FREE);
		return null;
	}

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, 1);
		cal.set(Calendar.DAY_OF_WEEK, 7);
		System.out.println(cal.getTime());
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String datetable(HkRequest req, HkResponse resp) throws Exception {
		DateInfo df = new DateInfo(req);
		Date beginTime = df.getBeginTime();
		Date endTime = df.getEndTime();
		long tableId = req.getLongAndSetAttr("tableId");
		String name = req.getString("name");
		String tel = req.getStringAndSetAttr("tel");
		req.setEncodeAttribute("name", name);
		CmpTable cmpTable = this.cmpTableService.getCmpTable(tableId);
		if (cmpTable == null) {
			return this.getNotFoundForward(resp);
		}
		req.setAttribute("cmpTable", cmpTable);
		PageSupport page = req.getPageSupport(20);
		int count = this.cmpTableService.countCmpOrderTableByCdn(tableId,
				beginTime, endTime, name, tel);
		page.setTotalCount(count);
		List<CmpOrderTable> cmpOrderTableList = this.cmpTableService
				.getCmpOrderTableListByCdn(tableId, beginTime, endTime, name,
						tel, page.getBegin(), page.getSize());
		req.setAttribute("cmpOrderTableList", cmpOrderTableList);
		long companyId = req.getLongAndSetAttr("companyId");
		this.loadTableListData2(req);
		List<CmpTable> tablelist = this.cmpTableService.getCmpTableList(
				companyId, cmpTable.getSortId());
		req.setAttribute("tablelist", tablelist);
		// 查看当前时间有预约，但是没有到达的数据
		int booked_not_meal_count = this.cmpTableService
				.countCmpOrderTableByCompanyIdForBookedNotMeal(companyId,
						new Date());
		req.setAttribute("booked_not_meal_count", booked_not_meal_count);
		return this.getWeb3Jsp("e/table/op/datetable.jsp");
	}

	/**
	 * 翻台率统计
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String countinfo(HkRequest req, HkResponse resp) throws Exception {
		long sortId = req.getLongAndSetAttr("sortId");
		long companyId = req.getLongAndSetAttr("companyId");
		DateInfo df = new DateInfo(req);
		Date beginTime = df.getBeginTime();
		Date endTime = df.getEndTime();
		beginTime = DataUtil.getDate(beginTime);
		endTime = DataUtil.getDate(endTime);
		int count = this.cmpTableService.countCmpTable(companyId, sortId, null);
		int sum = this.cmpTableService.calculateDailyCmpTableDataByDate(
				companyId, sortId, beginTime, endTime);
		double rate = (double) sum / (double) count;
		this.loadTableListData2(req);
		CmpTableSort cmpTableSort = this.cmpTableService
				.getCmpTableSort(sortId);
		req.setAttribute("count", count);
		req.setAttribute("sum", sum);
		req.setAttribute("rate", rate);
		req.setAttribute("cmpTableSort", cmpTableSort);
		List<CmpTable> tablelist = this.cmpTableService.getCmpTableList(
				companyId, sortId);
		req.setAttribute("tablelist", tablelist);
		return this.getWeb3Jsp("e/table/op/countinfo.jsp");
	}

	/**
	 *加载预约数据
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String loadcmpordertable(HkRequest req, HkResponse resp)
			throws Exception {
		long oid = req.getLongAndSetAttr("oid");
		long companyId = req.getLongAndSetAttr("companyId");
		CmpOrderTable o = this.cmpTableService.getCmpOrderTable(oid);
		if (o == null) {
			return null;
		}
		CmpTable cmpTable = this.cmpTableService.getCmpTable(o.getTableId());
		req.setAttribute("cmpTable", cmpTable);
		List<CmpTable> tablelist = this.cmpTableService.getCmpTableList(
				companyId, cmpTable.getSortId());
		req.setAttribute("tablelist", tablelist);
		List<CmpTableSort> sortlist = this.cmpTableService
				.getCmpTableSortListByCompanyId(companyId);
		req.setAttribute("sortlist", sortlist);
		req.setAttribute("o", o);
		return this.getWeb3Jsp("e/table/op/cmpordertable_inc.jsp");
	}

	/**
	 *加载台面数据根据分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String loadcmptablelistbysortid(HkRequest req, HkResponse resp)
			throws Exception {
		long sortId = req.getLongAndSetAttr("sortId");
		long companyId = req.getLongAndSetAttr("companyId");
		List<CmpTable> tablelist = this.cmpTableService.getCmpTableList(
				companyId, sortId);
		req.setAttribute("tablelist", tablelist);
		return this.getWeb3Jsp("e/table/op/tablelist_inc.jsp");
	}

	/**
	 *创建预约
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createcmpordertable(HkRequest req, HkResponse resp)
			throws Exception {
		String name = req.getString("name");
		int personNum = req.getInt("personNum");
		long companyId = req.getLong("companyId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		long tableId = req.getLong("tableId");
		CmpTable cmpTable = this.cmpTableService.getCmpTable(tableId);
		if (cmpTable == null) {
			return this.onError(req, Err.CMPTABLE_NOTEXIST,
					"oncmpordertableerror", null);
		}
		String remark = req.getString("remark");
		String tel = req.getString("tel");
		String bd = req.getString("bd");
		String bt = req.getString("bt");
		String ed = req.getString("ed");
		String et = req.getString("et");
		Date beginTime = DataUtil.parseTime(bd + " " + bt, "yyyy-MM-dd HH:mm");
		Date endTime = DataUtil.parseTime(ed + " " + et, "yyyy-MM-dd HH:mm");
		CmpOrderTable cmpOrderTable = new CmpOrderTable();
		cmpOrderTable.setCompanyId(companyId);
		cmpOrderTable.setTableId(tableId);
		cmpOrderTable.setName(DataUtil.toHtmlRow(name));
		cmpOrderTable.setTel(DataUtil.toHtmlRow(tel));
		cmpOrderTable.setPersonNum(personNum);
		cmpOrderTable.setRemark(DataUtil.toHtmlRow(remark));
		cmpOrderTable.setBeginTime(beginTime);
		cmpOrderTable.setEndTime(endTime);
		int code = cmpOrderTable.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "oncmpordertableerror", null);
		}
		this.cmpTableService.createCmpOrderTable(cmpOrderTable);
		if (req.getString("meal") != null) {
			this.cmpTableService.updateCmpOrderTableObjStatusMeal(cmpOrderTable
					.getOid());
		}
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "oncmpordertablesuccess", null);
	}

	/**
	 *修改预约
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updatecmpordertable(HkRequest req, HkResponse resp)
			throws Exception {
		long oid = req.getLong("oid");
		long companyId = req.getLong("companyId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		long tableId = req.getLong("tableId");
		CmpTable cmpTable = this.cmpTableService.getCmpTable(tableId);
		if (cmpTable == null) {
			return this.onError(req, Err.CMPTABLE_NOTEXIST,
					"oncmpordertableerror", null);
		}
		String name = req.getString("name");
		int personNum = req.getInt("personNum");
		String remark = req.getString("remark");
		String tel = req.getString("tel");
		CmpOrderTable cmpOrderTable = this.cmpTableService
				.getCmpOrderTable(oid);
		cmpOrderTable.setTableId(tableId);
		cmpOrderTable.setName(DataUtil.toHtmlRow(name));
		cmpOrderTable.setTel(DataUtil.toHtmlRow(tel));
		cmpOrderTable.setPersonNum(personNum);
		cmpOrderTable.setRemark(DataUtil.toHtmlRow(remark));
		String bd = req.getString("bd");
		String bt = req.getString("bt");
		String ed = req.getString("ed");
		String et = req.getString("et");
		Date beginTime = DataUtil.parseTime(bd + " " + bt, "yyyy-MM-dd HH:mm");
		Date endTime = DataUtil.parseTime(ed + " " + et, "yyyy-MM-dd HH:mm");
		cmpOrderTable.setBeginTime(beginTime);
		cmpOrderTable.setEndTime(endTime);
		int code = cmpOrderTable.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "oncmpordertableerror", null);
		}
		this.cmpTableService.updateCmpOrderTable(cmpOrderTable);
		if (req.getString("meal") != null) {
			this.cmpTableService.updateCmpOrderTableObjStatusMeal(oid);
		}
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "oncmpordertablesuccess", null);
	}

	/**
	 *已经预约，但没有到达的
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String bookednotmeal(HkRequest req, HkResponse resp)
			throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		Date date = new Date();
		int count = this.cmpTableService
				.countCmpOrderTableByCompanyIdForBookedNotMeal(companyId, date);
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(count);
		List<CmpOrderTable> list = this.cmpTableService
				.getCmpOrderTableListByCompanyIdForBookedNotMeal(companyId,
						date, page.getBegin(), page.getSize());
		List<Long> idList = new ArrayList<Long>();
		for (CmpOrderTable o : list) {
			idList.add(o.getTableId());
		}
		Map<Long, CmpTable> map = this.cmpTableService.getCmpTableMapInId(
				companyId, idList);
		for (CmpOrderTable o : list) {
			o.setCmpTable(map.get(o.getTableId()));
		}
		req.setAttribute("list", list);
		this.loadTableListData2(req);
		return this.getWeb3Jsp("e/table/op/bookednotmeal.jsp");
	}
}