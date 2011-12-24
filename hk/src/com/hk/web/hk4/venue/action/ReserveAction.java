package com.hk.web.hk4.venue.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.epp.action.GraduationTimeVo;
import web.epp.action.ReserveTimeVo;

import com.hk.bean.CmpActor;
import com.hk.bean.CmpActorSpTime;
import com.hk.bean.CmpActorSvrRef;
import com.hk.bean.CmpOtherInfo;
import com.hk.bean.CmpReserve;
import com.hk.bean.CmpSvr;
import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpActorService;
import com.hk.svr.CmpActorSpTimeService;
import com.hk.svr.CmpReserveService;
import com.hk.svr.CmpSvrService;
import com.hk.svr.CompanyService;
import com.hk.svr.processor.CmpReserveProcessor;
import com.hk.svr.processor.CmpSvrProcessor;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

/**
 * 预约
 * 
 * @author akwei
 */
@Component("/h4/op/reserve")
public class ReserveAction extends BaseAction {

	@Autowired
	private CmpReserveService cmpReserveService;

	@Autowired
	private CmpActorService cmpActorService;

	@Autowired
	private CmpSvrService cmpSvrService;

	@Autowired
	private CmpActorSpTimeService cmpActorSpTimeService;

	@Autowired
	private CmpSvrProcessor cmpSvrProcessor;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CmpReserveProcessor cmpReserveProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		long reserveId = req.getLongAndSetAttr("reserveId");
		long actorId = req.getLongAndSetAttr("actorId");
		long repeat_reserveId = req.getLongAndSetAttr("repeat_reserveId");
		User loginUser = this.getLoginUser(req);
		int update = req.getIntAndSetAttr("update");
		if (update != 1) {
			int reserve_count = this.cmpReserveService
					.countCmpReserveByCompanyIdAndUserIdForReserved(companyId,
							loginUser.getUserId());
			CmpOtherInfo cmpOtherInfo = this.companyService
					.getCmpOtherInfo(companyId);
			// 超过预约限制不能进行新的预约
			if (cmpOtherInfo == null && reserve_count >= 1) {
				// 超过预约限制，不能进行预约
				req.setAttribute("reserve_limit", true);
				return this.getWeb4Jsp("venue/meifa/reserve/tip.jsp");
			}
			if (cmpOtherInfo != null
					&& reserve_count >= cmpOtherInfo.getSvrrate()) {
				// 超过预约限制，不能进行预约
				req.setAttribute("reserve_limit", true);
				return this.getWeb4Jsp("venue/meifa/reserve/tip.jsp");
			}
			if (reserve_count > 0) {
				req.setAttribute("reserve_count", reserve_count);
				// 提示用户还有未完成的预约，是修改预约还是新建预约
				return this.getWeb4Jsp("venue/meifa/reserve/tip.jsp");
			}
		}
		return "r:/h4/op/reserve_sel.do?companyId=" + companyId + "&actorId="
				+ actorId + "&update=" + update + "&reserveId=" + reserveId
				+ "&repeat_reserveId=" + repeat_reserveId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-18
	 */
	public String sel(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		long reserveId = req.getLongAndSetAttr("reserveId");
		long actorId = req.getLongAndSetAttr("actorId");
		long repeat_reserveId = req.getLongAndSetAttr("repeat_reserveId");
		CmpReserve cmpReserve = null;
		if (req.getIntAndSetAttr("update") == 1) {
			cmpReserve = this.cmpReserveService.getCmpReserve(reserveId);
			req.setAttribute("cmpReserve", cmpReserve);
			companyId = cmpReserve.getCompanyId();
			req.setAttribute("companyId", companyId);
		}
		else if (repeat_reserveId > 0) {
			cmpReserve = this.cmpReserveService.getCmpReserve(repeat_reserveId);
			if (cmpReserve != null) {
				cmpReserve.setReserveTime(null);
				cmpReserve.setEndTime(null);
			}
			req.setAttribute("cmpReserve", cmpReserve);
		}
		CmpActor cmpActor = this.cmpActorService.getCmpActor(actorId);
		req.setAttribute("cmpActor", cmpActor);
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		// 服务人员提供的服务列表
		List<CmpActorSvrRef> svrreflist = this.cmpSvrProcessor
				.getCmpActorSvrRefListByCompanyIdAndActorId(companyId, actorId,
						true);
		ReserveInfo reserveInfo = this.getReserveInfo(req);
		String sel_svrId = "";
		if (reserveInfo != null) {
			sel_svrId = reserveInfo.getSvrIdData();
		}
		if (req.getIntAndSetAttr("update") == 1) {
			if (cmpReserve != null) {
				sel_svrId += "," + cmpReserve.getSvrdata();
			}
		}
		if (repeat_reserveId > 0 && cmpReserve != null) {
			sel_svrId = cmpReserve.getSvrdata();
		}
		req.setAttribute("sel_svrId", sel_svrId);
		req.setAttribute("svrreflist", svrreflist);
		return this.getWeb4Jsp("venue/meifa/reserve/reserve.jsp");
	}

	private ReserveInfo getReserveInfo(HkRequest req) {
		Cookie cookie = req.getCookie("hk_reserve_cookie");
		if (cookie != null) {
			return new ReserveInfo(cookie.getValue());
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-28
	 */
	public String createreserve(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		long userId = loginUser.getUserId();
		long companyId = req.getLong("companyId");
		long actorId = req.getLong("actorId");
		long[] svrId = req.getLongs("svrId");
		// 用户在此足迹未完成的预约的数量
		int reserve_count = this.cmpReserveService
				.countCmpReserveByCompanyIdAndUserIdForReserved(companyId,
						loginUser.getUserId());
		CmpOtherInfo cmpOtherInfo = this.companyService
				.getCmpOtherInfo(companyId);
		// 超过预约限制不能进行新的预约
		if (cmpOtherInfo == null && reserve_count >= 1) {
			return this.onError(req, Err.CMPOTHERINFO_SVRRATE_ERR,
					"createerror", null);
		}
		if (cmpOtherInfo != null && reserve_count >= cmpOtherInfo.getSvrrate()) {
			return this.onError(req, Err.CMPOTHERINFO_SVRRATE_ERR,
					"createerror", null);
		}
		CmpActor cmpActor = this.cmpActorService.getCmpActor(actorId);
		if (cmpActor == null || cmpActor.getCompanyId() != companyId) {
			return null;
		}
		String datestr = req.getString("datestr");
		String begintime = req.getString("begintime");
		String endtime = req.getString("endtime");
		Date begin = DataUtil.parseTime(datestr + " " + begintime,
				"yyyy-MM-dd HH:mm");
		Date end = DataUtil.parseTime(datestr + " " + endtime,
				"yyyy-MM-dd HH:mm");
		CmpReserve cmpReserve = new CmpReserve();
		cmpReserve.setCompanyId(companyId);
		cmpReserve.setActorId(actorId);
		cmpReserve.setReserveTime(begin);
		cmpReserve.setEndTime(end);
		cmpReserve.setReserveStatus(CmpReserve.RESERVESTATUS_DEF);
		cmpReserve.setUserId(userId);
		cmpReserve.setUsername(req.getHtmlRow("username"));
		cmpReserve.setMobile(req.getHtmlRow("mobile"));
		if (svrId != null) {
			StringBuilder sb = new StringBuilder();
			for (long id : svrId) {
				CmpSvr svr = this.cmpSvrService.getCmpSvr(companyId, id);
				if (svr == null) {
					return null;
				}
				sb.append(id).append(",");
			}
			if (sb.length() > 1) {
				sb.deleteCharAt(sb.length() - 1);
			}
			cmpReserve.setSvrdata(sb.toString());
		}
		int code = cmpReserve.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		// 验证时间是否可以预约
		Date[] ds = this.getWorkTime(begin, companyId);
		Date beginWorkTime = ds[0];
		Date endWorkTime = ds[1];
		List<ReserveTimeVo> reservetimevolist = ReserveTimeVo.initList(
				beginWorkTime, endWorkTime);
		if (!this.isCanReserve(cmpActor, companyId, begin, begin, cmpReserve
				.getEndTime(), reservetimevolist, userId, 0)) {
			return this.onError(req, Err.CMPRESERVE_SELDATE_ERROR,
					"createerror", null);
		}
		this.cmpReserveService.createCmpReserve(cmpReserve);
		resp.removeCookie("hk_reserve_cookie", req.getServerName(), "/");
		req.setSessionText(String.valueOf(Err.CMPRESERVE_CREATE_OK));
		return this.onSuccess2(req, "createok", cmpReserve.getReserveId());
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-28
	 */
	public String updatereserve(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		long userId = loginUser.getUserId();
		long companyId = req.getLong("companyId");
		long actorId = req.getLong("actorId");
		long[] svrId = req.getLongs("svrId");
		CmpActor cmpActor = this.cmpActorService.getCmpActor(actorId);
		if (cmpActor == null || cmpActor.getCompanyId() != companyId) {
			return null;
		}
		String datestr = req.getString("datestr");
		String begintime = req.getString("begintime");
		String endtime = req.getString("endtime");
		Date begin = DataUtil.parseTime(datestr + " " + begintime,
				"yyyy-MM-dd HH:mm");
		Date end = DataUtil.parseTime(datestr + " " + endtime,
				"yyyy-MM-dd HH:mm");
		long reserveId = req.getLong("reserveId");
		CmpReserve cmpReserve = this.cmpReserveService.getCmpReserve(reserveId);
		if (cmpReserve == null) {
			return null;
		}
		cmpReserve.setReserveTime(begin);
		cmpReserve.setEndTime(end);
		cmpReserve.setReserveStatus(CmpReserve.RESERVESTATUS_DEF);
		cmpReserve.setUserId(userId);
		cmpReserve.setUsername(req.getHtmlRow("username"));
		cmpReserve.setMobile(req.getHtmlRow("mobile"));
		if (svrId != null) {
			StringBuilder sb = new StringBuilder();
			for (long id : svrId) {
				CmpSvr svr = this.cmpSvrService.getCmpSvr(companyId, id);
				if (svr == null) {
					return null;
				}
				sb.append(id).append(",");
			}
			if (sb.length() > 1) {
				sb.deleteCharAt(sb.length() - 1);
			}
			cmpReserve.setSvrdata(sb.toString());
		}
		int code = cmpReserve.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		// 验证时间是否可以预约
		Date[] ds = this.getWorkTime(begin, companyId);
		Date beginWorkTime = ds[0];
		Date endWorkTime = ds[1];
		List<ReserveTimeVo> reservetimevolist = ReserveTimeVo.initList(
				beginWorkTime, endWorkTime);
		if (!this.isCanReserve(cmpActor, companyId, begin, begin, cmpReserve
				.getEndTime(), reservetimevolist, userId, reserveId)) {
			return this.onError(req, Err.CMPRESERVE_SELDATE_ERROR,
					"createerror", null);
		}
		this.cmpReserveService.updateCmpReserve(cmpReserve);
		req.setSessionText(String.valueOf(Err.CMPRESERVE_CREATE_OK));
		return this.onSuccess2(req, "createok", cmpReserve.getReserveId());
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-28
	 */
	public String loadinfoforreserve(HkRequest req, HkResponse resp) {
		long actorId = req.getLongAndSetAttr("actorId");
		long companyId = req.getLong("companyId");
		Date date = DataUtil.parseTime(req.getString("datestr"), "yyyy-MM-dd");
		// 如果没有选择时间，默认查看当天
		if (date == null) {
			date = new Date();
		}
		User loginUser = this.getLoginUser(req);
		long reserveId = req.getLong("reserveId");
		if (reserveId == 0) {// 认为不是修改预约数据，就要进行预约数量条件认证
			// 用户在此足迹未完成的预约的数量
			int reserve_count = this.cmpReserveService
					.countCmpReserveByCompanyIdAndUserIdForReserved(companyId,
							loginUser.getUserId());
			CmpOtherInfo cmpOtherInfo = this.companyService
					.getCmpOtherInfo(companyId);
			if (cmpOtherInfo == null && reserve_count >= 1) {
				req.setAttribute("out_of_svr_limit", true);
				req.setAttribute("reser_limimt_count", 1);
				return this
						.getWeb4Jsp("venue/meifa/reserve/reserve_reserveinfo.jsp");
			}
			if (cmpOtherInfo != null
					&& reserve_count >= cmpOtherInfo.getSvrrate()) {
				req.setAttribute("out_of_svr_limit", true);
				req.setAttribute("reser_limimt_count", cmpOtherInfo
						.getSvrrate());
				return this
						.getWeb4Jsp("venue/meifa/reserve/reserve_reserveinfo.jsp");
			}
		}
		CmpActor cmpActor = this.cmpActorService.getCmpActor(actorId);
		req.setAttribute("cmpActor", cmpActor);
		Date[] ds = this.getWorkTime(date, companyId);
		Date beginWorkTime = ds[0];
		Date endWorkTime = ds[1];
		List<ReserveTimeVo> reservetimevolist = ReserveTimeVo.initList(
				beginWorkTime, endWorkTime);
		boolean canwork = this.isActorCanWork(cmpActor, companyId, date);
		if (canwork) {
			// 验证此预约是否是用的的未完成预约
			if (reserveId > 0) {
				CmpReserve cmpReserve = this.cmpReserveService
						.getCmpReserve(reserveId);
				if (cmpReserve == null) {// 不存在的预约
					reserveId = 0;
				}
				else {
					// 不是用户的预约
					if (cmpReserve.getUserId() != loginUser.getUserId()) {
						reserveId = 0;
					}
					else {// 不是预约状态
						if (!cmpReserve.isReserved()) {
							reserveId = 0;
						}
					}
				}
			}
			this.isCanReserve(cmpActor, companyId, date, null, null,
					reservetimevolist, loginUser.getUserId(), reserveId);
			req.setAttribute("reservetimevolist", reservetimevolist);
		}
		else {
			req.setAttribute("cannotwork", true);
		}
		return this.getWeb4Jsp("venue/meifa/reserve/reserve_reserveinfo.jsp");
	}

	/**
	 * 计算当天的营业开始时间与结束时间
	 * 
	 * @param date 所选日期
	 * @param companyId
	 * @return
	 *         2010-8-17
	 */
	private Date[] getWorkTime(Date date, long companyId) {
		CmpOtherInfo cmpOtherInfo = this.companyService
				.getCmpOtherInfo(companyId);
		Date beginWorkTime = null;
		Date endWorkTime = null;
		if (cmpOtherInfo != null) {
			String begin_date = DataUtil.getFormatTimeData(date, "yyyy-MM-dd");
			String end_date = DataUtil.getFormatTimeData(date, "yyyy-MM-dd");
			beginWorkTime = DataUtil.parseTime(begin_date + " "
					+ cmpOtherInfo.getBeginTime(), "yyyy-MM-dd HH:mm");
			endWorkTime = DataUtil.parseTime(end_date + " "
					+ cmpOtherInfo.getEndTime(), "yyyy-MM-dd HH:mm");
		}
		else {
			Calendar t = Calendar.getInstance();
			t.setTime(date);
			t.set(Calendar.HOUR_OF_DAY, 9);
			t.set(Calendar.MINUTE, 0);
			t.set(Calendar.SECOND, 0);
			t.set(Calendar.MILLISECOND, 0);
			beginWorkTime = t.getTime();
			t.set(Calendar.HOUR_OF_DAY, 22);
			endWorkTime = t.getTime();
		}
		return new Date[] { beginWorkTime, endWorkTime };
	}

	/**
	 * 所选日期是否可以预约
	 * 
	 * @param cmpActor
	 * @param companyId
	 * @param date 所选的开始时间
	 * @return
	 *         2010-8-8
	 */
	private boolean isActorCanWork(CmpActor cmpActor, long companyId, Date date) {
		long actorId = cmpActor.getActorId();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date beginTime = calendar.getTime();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Date endTime = calendar.getTime();
		if (cmpActor.isOnWorkDay(date)) {
			return true;
		}
		// 非工作日的值班
		List<CmpActorSpTime> worktimelist = this.cmpActorSpTimeService
				.getCmpActorSpTimeListByCompanyIdEx(companyId, actorId,
						CmpActorSpTime.SPFLG_WORK, beginTime, endTime, 0, 1);
		if (worktimelist.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 所选时间是否可以预约
	 * 
	 * @param cmpActor
	 * @param companyId
	 * @param date 所选的开始时间
	 * @return
	 *         2010-8-8
	 */
	private boolean isCanReserve(CmpActor cmpActor, long companyId, Date date,
			Date sel_date, Date sel_endTime,
			List<ReserveTimeVo> reservetimevolist, long currentUserId,
			long reserveId) {
		long actorId = cmpActor.getActorId();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date beginTime = calendar.getTime();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Date endTime = calendar.getTime();
		boolean canwork = this.isActorCanWork(cmpActor, companyId, date);
		if (!canwork) {
			return false;
		}
		List<CmpActorSpTime> sptimelist = null;
		List<CmpReserve> deflist = null;// 预约的数据
		List<CmpReserve> worklist = null;// 正在工作的数据
		// List<CmpReserve> doinglist = null;
		// 如果当天是工作日
		if (cmpActor.isOnWorkDay(date)) {
			// 查看是否请假，默认取10条
			sptimelist = this.cmpActorSpTimeService
					.getCmpActorSpTimeListByCompanyIdEx(companyId, actorId,
							CmpActorSpTime.SPFLG_NOTWORK, beginTime, endTime,
							0, 10);
		}
		deflist = this.cmpReserveService.getCmpReserveListByCompanyIdEx(
				companyId, actorId, CmpReserve.RESERVESTATUS_DEF, null, null,
				beginTime, endTime, 0, 100);
		worklist = this.cmpReserveService.getCmpReserveListByCompanyIdEx(
				companyId, actorId, CmpReserve.RESERVESTATUS_DOING, null, null,
				beginTime, endTime, 0, 100);
		this.processReserve(reservetimevolist, sptimelist, deflist, worklist,
				currentUserId, reserveId);
		if (sel_date != null && sel_endTime != null) {
			// 判断
			List<GraduationTimeVo> gtvolist = new ArrayList<GraduationTimeVo>();
			for (ReserveTimeVo reserveTimeVo : reservetimevolist) {
				gtvolist.addAll(reserveTimeVo.getList());
			}
			for (GraduationTimeVo vo : gtvolist) {
				if (sel_date.getTime() <= vo.getBeginTime().getTime()
						&& sel_endTime.getTime() >= vo.getEndTime().getTime()
						&& !vo.isCanReserve()) {
					return false;
				}
				if (sel_date.getTime() <= vo.getBeginTime().getTime()
						&& sel_endTime.getTime() > vo.getBeginTime().getTime()
						&& sel_endTime.getTime() <= vo.getEndTime().getTime()
						&& !vo.isCanReserve()) {
					return false;
				}
				if (sel_date.getTime() >= vo.getBeginTime().getTime()
						&& sel_date.getTime() < vo.getEndTime().getTime()
						&& sel_endTime.getTime() > vo.getEndTime().getTime()
						&& !vo.isCanReserve()) {
					return false;
				}
				if (sel_date.getTime() > vo.getBeginTime().getTime()
						&& sel_endTime.getTime() < vo.getEndTime().getTime()
						&& !vo.isCanReserve()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	private void processReserve(List<ReserveTimeVo> reserveTimeVoList,
			List<CmpActorSpTime> cmpActorSpTimeList,
			List<CmpReserve> cmpReserveList,
			List<CmpReserve> doingCmpReserveList, long currentUserId,
			long reserveId) {
		// 判断
		List<GraduationTimeVo> gtvolist = new ArrayList<GraduationTimeVo>();
		for (ReserveTimeVo reserveTimeVo : reserveTimeVoList) {
			gtvolist.addAll(reserveTimeVo.getList());
		}
		for (GraduationTimeVo graduationTimeVo : gtvolist) {
			if (cmpActorSpTimeList != null) {
				// 判断假期，如果在休假时间，不能预约
				for (CmpActorSpTime cmpActorSpTime : cmpActorSpTimeList) {
					if (!this.isDateCanReserve(cmpActorSpTime.getBeginTime(),
							cmpActorSpTime.getEndTime(), graduationTimeVo
									.getBeginTime(), graduationTimeVo
									.getEndTime())) {
						graduationTimeVo.setCanReserve(false);
					}
				}
			}
			if (cmpReserveList != null) {
				// 判断预约，如果在预约时间，不能进行新的预约
				for (CmpReserve cmpReserve : cmpReserveList) {
					if (!this.isDateCanReserve(cmpReserve.getReserveTime(),
							cmpReserve.getEndTime(), graduationTimeVo
									.getBeginTime(), graduationTimeVo
									.getEndTime())) {
						if (reserveId == 0 || cmpReserve.isExpire()) {
							graduationTimeVo.setCanReserve(false);
						}
						else if (reserveId > 0) {
							// 如果是修改此预约，则不考虑此预约的时间是否可以进行预约
							if (!cmpReserve.isReserved()
									|| cmpReserve.getReserveId() != reserveId) {
								graduationTimeVo.setCanReserve(false);
							}
						}
						if (currentUserId == cmpReserve.getUserId()
								&& !cmpReserve.isExpire()) {
							graduationTimeVo.setCurrentUser(true);
						}
					}
				}
			}
			if (doingCmpReserveList != null) {
				// 判断正在工作，如果正在工作的时间，不能进行新的预约
				for (CmpReserve cmpReserve : doingCmpReserveList) {
					if (!this.isDateCanReserve(cmpReserve.getReserveTime(),
							cmpReserve.getEndTime(), graduationTimeVo
									.getBeginTime(), graduationTimeVo
									.getEndTime())) {
						graduationTimeVo.setCanReserve(false);
					}
				}
			}
		}
	}

	/**
	 * @param beginTime 实际开始时间
	 * @param endTime 实际结束时间
	 * @param obeginTime 刻度开始时间
	 * @param oendTime 刻度结束时间
	 * @return
	 *         2010-7-30
	 */
	private boolean isDateCanReserve(Date beginTime, Date endTime,
			Date obeginTime, Date oendTime) {
		// 时间刻度包含在范围内
		if (beginTime.getTime() <= obeginTime.getTime()
				&& endTime.getTime() >= oendTime.getTime()) {
			return false;
		}
		// 结束时间在范围内
		if (beginTime.getTime() < obeginTime.getTime()
				&& endTime.getTime() <= oendTime.getTime()
				&& endTime.getTime() > obeginTime.getTime()) {
			return false;
		}
		// 开始时间在范围内
		if (beginTime.getTime() >= obeginTime.getTime()
				&& beginTime.getTime() < oendTime.getTime()
				&& endTime.getTime() > oendTime.getTime()) {
			return false;
		}
		// 时间包含在刻度之内
		if (beginTime.getTime() > obeginTime.getTime()
				&& endTime.getTime() < oendTime.getTime()) {
			return false;
		}
		return true;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-9
	 */
	public String view(HkRequest req, HkResponse resp) {
		req.reSetAttribute("companyId");
		long reserveId = req.getLongAndSetAttr("reserveId");
		CmpReserve cmpReserve = this.cmpReserveService.getCmpReserve(reserveId);
		if (cmpReserve == null) {
			return null;
		}
		req.setAttribute("cmpReserve", cmpReserve);
		CmpActor cmpActor = this.cmpActorService.getCmpActor(cmpReserve
				.getActorId());
		req.setAttribute("cmpActor", cmpActor);
		if (!DataUtil.isEmpty(cmpReserve.getSvrdata())) {
			List<Long> idList = new ArrayList<Long>();
			String[] ids = cmpReserve.getSvrdata().split(",");
			for (String id : ids) {
				idList.add(Long.valueOf(id));
			}
			List<CmpSvr> svrlist = this.cmpSvrService.getCmpSvrListInId(idList);
			req.setAttribute("svrlist", svrlist);
		}
		return this.getWeb4Jsp("venue/meifa/reserve/view.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-16
	 */
	public String myreserve(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		long userId = loginUser.getUserId();
		SimplePage page = req.getSimplePage(20);
		byte reserveStatus = req.getByteAndSetAttr("reserveStatus",
				CmpReserve.RESERVESTATUS_DEF);
		boolean un = req.getBoolean("un");// 作废预约标识
		List<CmpReserve> list = this.cmpReserveProcessor
				.getCmpReserveListByUserIdAndReserveStatus(userId,
						reserveStatus, un, true, true, true, page.getBegin(),
						page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.setAttribute("RESERVESTATUS_SUCCESS",
				CmpReserve.RESERVESTATUS_SUCCESS);
		req.setAttribute("RESERVESTATUS_CANCEL",
				CmpReserve.RESERVESTATUS_CANCEL);
		req.setAttribute("RESERVESTATUS_DEF", CmpReserve.RESERVESTATUS_DEF);
		req.setAttribute("un", un);
		return this.getWeb4Jsp("venue/meifa/reserve/myreserve.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-9
	 */
	public String cancelreserve(HkRequest req, HkResponse resp) {
		long reserveId = req.getLong("reserveId");
		CmpReserve cmpReserve = this.cmpReserveService.getCmpReserve(reserveId);
		if (cmpReserve == null
				|| cmpReserve.getUserId() != this.getLoginUser(req).getUserId()) {
			return null;
		}
		cmpReserve.setReserveStatus(CmpReserve.RESERVESTATUS_CANCEL);
		this.cmpReserveService.updateCmpReserve(cmpReserve);
		req.setSessionText("epp.cmpreser.cancel.ok");
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-9
	 */
	public String updatelast(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		long companyId = req.getLongAndSetAttr("companyId");
		long actorId = req.getLongAndSetAttr("actorId");
		List<CmpReserve> list = this.cmpReserveService
				.getCmpReserveListByCompanyIdAndUserIdAndReserveStatus(
						companyId, loginUser.getUserId(),
						CmpReserve.RESERVESTATUS_DEF, 0, 1);
		if (list.size() == 0) {
			return "r:/h4/op/reserve_sel.do?companyId=" + companyId
					+ "&actorId=" + actorId;
		}
		CmpReserve cmpReserve = list.get(0);
		return "r:/h4/op/reserve_sel.do?companyId=" + companyId + "&actorId="
				+ cmpReserve.getActorId() + "&update=1&reserveId="
				+ cmpReserve.getReserveId();
	}
}