package com.hk.web.hk4.venue.opvenue.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpActorReport;
import com.hk.bean.CmpReserve;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpActorService;
import com.hk.svr.CmpReserveService;
import com.hk.svr.CompanyService;
import com.hk.svr.processor.CmpReserveProcessor;
import com.hk.web.pub.action.BaseAction;

/**
 * 后台预约管理
 * 
 * @author akwei
 */
@Component("/h4/op/venue/reserve")
public class ReserveAction extends BaseAction {

	@Autowired
	private CmpReserveService cmpReserveService;

	@Autowired
	private CmpReserveProcessor cmpReserveProcessor;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CmpActorService cmpActorService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-20
	 */
	public String list(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		String username = req.getHtmlRow("username");
		String mobile = req.getHtmlRowAndSetAttr("mobile");
		long actorId = req.getLongAndSetAttr("actorId");
		int ignoredate = req.getInt("ignoredate");
		String day = req.getString("day");
		byte reserveStatus = req.getByteAndSetAttr("reserveStatus",
				CmpReserve.RESERVESTATUS_DEF);
		Date date = DataUtil.parseTime(day, "yyyy-MM-dd");
		if (date == null) {// 默认为当天
			if (ignoredate == 0) {// 如果没有忽略日期
				date = new Date();
			}
		}
		Date beginTime = null;
		Date endTime = null;
		if (date != null) {
			day = DataUtil.getFormatTimeData(date, "yyyy-MM-dd");
			req.setAttribute("day", day);
			beginTime = DataUtil.getDate(date);
			endTime = DataUtil.getEndDate(date);
		}
		SimplePage page = req.getSimplePage(20);
		List<CmpReserve> list = this.cmpReserveProcessor
				.getCmpReserveListByCompanyIdEx(companyId, actorId,
						reserveStatus, username, mobile, beginTime, endTime,
						true, true, page.getBegin(), page.getSize() + 1);
		req.setAttribute("list", list);
		req.setEncodeAttribute("username", username);
		// 统计未处理的预约,包含过期
		int undo_count = this.cmpReserveService
				.countCmpReserveByCompanyIdAndReserveStatus(companyId,
						CmpReserve.RESERVESTATUS_DEF);
		req.setAttribute("undo_count", undo_count);
		// 统计入座的预约
		int arrive_count = this.cmpReserveService
				.countCmpReserveByCompanyIdAndReserveStatus(companyId,
						CmpReserve.RESERVESTATUS_ARRIVE);
		req.setAttribute("arrive_count", arrive_count);
		// 统计正在服务的预约
		int doing_count = this.cmpReserveService
				.countCmpReserveByCompanyIdAndReserveStatus(companyId,
						CmpReserve.RESERVESTATUS_DOING);
		req.setAttribute("doing_count", doing_count);
		return this.getWeb4Jsp("venue/op2/reserve/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-20
	 */
	public String updatereservestatus(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long reserveId = req.getLong("reserveId");
		byte reserveStatus = req.getByte("reserveStatus");
		CmpReserve cmpReserve = this.cmpReserveService.getCmpReserve(reserveId);
		if (cmpReserve == null || cmpReserve.getCompanyId() != companyId) {
			return null;
		}
		cmpReserve.setReserveStatus(reserveStatus);
		this.cmpReserveService.updateCmpReserve(cmpReserve);
		int cmp_workcount = this.cmpReserveService
				.countCmpReserveByCompanyIdAndReserveStatus(companyId,
						reserveStatus);
		this.companyService
				.updateWorkCountByCompanyId(companyId, cmp_workcount);
		int actor_workcount = this.cmpReserveService
				.countCmpReserveByActorIdAndReserveStatus(cmpReserve
						.getActorId(), reserveStatus);
		this.cmpActorService.updateWorkCountByActorId(cmpReserve.getActorId(),
				actor_workcount);
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-20
	 */
	public String report(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		String begin = req.getHtmlRow("begin");
		String end = req.getHtmlRow("end");
		Date beginTime = DataUtil.parseTime(begin, "yyyy-MM-dd");
		Date endTime = DataUtil.parseTime(end, "yyyy-MM-dd");
		if (beginTime == null) {
			beginTime = new Date();
		}
		if (endTime == null) {
			endTime = new Date();
		}
		beginTime = DataUtil.getDate(beginTime);
		endTime = DataUtil.getEndDate(endTime);
		begin = DataUtil.getFormatTimeData(beginTime, "yyyy-MM-dd");
		end = DataUtil.getFormatTimeData(endTime, "yyyy-MM-dd");
		req.setAttribute("begin", begin);
		req.setAttribute("end", end);
		List<CmpActorReport> list = this.cmpReserveProcessor
				.getCmpActorReportListByCompanyId(companyId, beginTime,
						endTime, true);
		req.setAttribute("list", list);
		return this.getWeb4Jsp("venue/op2/reserve/report.jsp");
	}
}