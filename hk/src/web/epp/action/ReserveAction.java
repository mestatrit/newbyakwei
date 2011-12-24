package web.epp.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpActor;
import com.hk.bean.CmpActorSvrRef;
import com.hk.bean.CmpReserve;
import com.hk.bean.CmpSvr;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpActorService;
import com.hk.svr.CmpReserveService;
import com.hk.svr.CmpSvrService;
import com.hk.svr.processor.CmpReserveProcessor;
import com.hk.svr.processor.CmpSvrProcessor;
import com.hk.svr.pub.Err;

public class ReserveAction extends EppBaseAction {

	@Autowired
	private CmpActorService cmpActorService;

	@Autowired
	private CmpReserveService cmpReserveService;

	@Autowired
	private CmpSvrProcessor cmpSvrProcessor;

	@Autowired
	private CmpSvrService cmpSvrService;

	@Autowired
	private CmpReserveProcessor cmpReserveProcessor;

	public String execute(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		User loginUser = this.getLoginUser2(req);
		long userId = loginUser.getUserId();
		SimplePage page = req.getSimplePage(20);
		List<CmpReserve> list = this.cmpReserveProcessor
				.getCmpReserveListByCompanyIdAndUserId(companyId, userId, true,
						true, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebPath("mod/3/0/reserve/myreserve.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-9
	 */
	public String cancelreserve(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long reserveId = req.getLong("reserveId");
		CmpReserve cmpReserve = this.cmpReserveService.getCmpReserve(reserveId);
		if (cmpReserve == null
				|| cmpReserve.getCompanyId() != companyId
				|| cmpReserve.getUserId() != this.getLoginUser2(req)
						.getUserId()) {
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
	 * @throws Exception
	 *             2010-8-9
	 */
	public String view(HkRequest req, HkResponse resp) {
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
		return this.getWebPath("mod/3/0/reserve/view.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-28
	 */
	public String sel(HkRequest req, HkResponse resp) {
		this.setCmpNavInfo(req);
		// User loginUser = this.getLoginUser2(req);
		long actorId = req.getLongAndSetAttr("actorId");
		long companyId = req.getLong("companyId");
		CmpActor cmpActor = this.cmpActorService.getCmpActor(actorId);
		req.setAttribute("cmpActor", cmpActor);
		// 服务人员提供的服务列表
		List<CmpActorSvrRef> svrreflist = this.cmpSvrProcessor
				.getCmpActorSvrRefListByCompanyIdAndActorId(companyId, actorId,
						true);
		long reserveId = req.getLongAndSetAttr("reserveId");
		if (req.getIntAndSetAttr("update") == 1) {
			CmpReserve cmpReserve = this.cmpReserveService
					.getCmpReserve(reserveId);
			req.setAttribute("cmpReserve", cmpReserve);
		}
		req.setAttribute("svrreflist", svrreflist);
		return this.getWebPath("mod/3/0/actor/reserve.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-28
	 */
	public String createreserve(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser2(req);
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
		Date begin = DataUtil.parseTime(datestr + " " + begintime,
				"yyyy-MM-dd HH:mm");
		CmpReserve cmpReserve = new CmpReserve();
		cmpReserve.setCompanyId(companyId);
		cmpReserve.setActorId(actorId);
		cmpReserve.setReserveTime(begin);
		cmpReserve.setReserveStatus(CmpReserve.RESERVESTATUS_DEF);
		cmpReserve.setUserId(userId);
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
		if (begin != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(begin);
			cal.add(Calendar.MINUTE, 30);
			Date endTime = cal.getTime();
			cmpReserve.setEndTime(endTime);
		}
		int code = cmpReserve.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		// 验证时间是否可以预约
		// List<ReserveTimeVo> reservetimevolist =
		// ReserveTimeVo.initList(begin);
		// if (!this.isCanReserve(cmpActor, companyId, begin, begin, cmpReserve
		// .getEndTime(), reservetimevolist)) {
		// return this.onError(req, Err.CMPRESERVE_SELDATE_ERROR,
		// "createerror", null);
		// }
		this.cmpReserveService.createCmpReserve(cmpReserve);
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
		User loginUser = this.getLoginUser2(req);
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
		Date begin = DataUtil.parseTime(datestr + " " + begintime,
				"yyyy-MM-dd HH:mm");
		long reserveId = req.getLong("reserveId");
		CmpReserve cmpReserve = this.cmpReserveService.getCmpReserve(reserveId);
		if (cmpReserve == null) {
			return null;
		}
		cmpReserve.setReserveTime(begin);
		cmpReserve.setReserveStatus(CmpReserve.RESERVESTATUS_DEF);
		cmpReserve.setUserId(userId);
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
		if (begin != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(begin);
			cal.add(Calendar.MINUTE, 30);
			Date endTime = cal.getTime();
			cmpReserve.setEndTime(endTime);
		}
		int code = cmpReserve.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		// 验证时间是否可以预约
		// List<ReserveTimeVo> reservetimevolist =
		// ReserveTimeVo.initList(begin);
		// if (!this.isCanReserve(cmpActor, companyId, begin, begin, cmpReserve
		// .getEndTime(), reservetimevolist)) {
		// return this.onError(req, Err.CMPRESERVE_SELDATE_ERROR,
		// "createerror", null);
		// }
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
		Date date = DataUtil.parseTime(req.getString("datestr"), "yyyy-MM-dd");
		// 如果没有选择时间，默认查看当天
		if (date == null) {
			date = new Date();
		}
		CmpActor cmpActor = this.cmpActorService.getCmpActor(actorId);
		req.setAttribute("cmpActor", cmpActor);
		// List<ReserveTimeVo> reservetimevolist = ReserveTimeVo.initList(date);
		// boolean canwork = this.isActorCanWork(cmpActor, companyId, date);
		// if (canwork) {
		// this.isCanReserve(cmpActor, companyId, date, null, null,
		// reservetimevolist);
		// req.setAttribute("reservetimevolist", reservetimevolist);
		// }
		// else {
		// req.setAttribute("cannotwork", true);
		// }
		return this.getWebPath("mod/3/0/actor/reserve_reserveinfo.jsp");
	}
	// /**
	// * 所选时间是否可以预约
	// *
	// * @param cmpActor
	// * @param companyId
	// * @param date 所选的开始时间
	// * @return
	// * 2010-8-8
	// */
	// private boolean isActorCanWork(CmpActor cmpActor, long companyId, Date
	// date) {
	// long actorId = cmpActor.getActorId();
	// Calendar calendar = Calendar.getInstance();
	// calendar.setTime(date);
	// calendar.set(Calendar.HOUR_OF_DAY, 0);
	// calendar.set(Calendar.MINUTE, 0);
	// calendar.set(Calendar.SECOND, 0);
	// Date beginTime = calendar.getTime();
	// calendar.set(Calendar.HOUR_OF_DAY, 23);
	// calendar.set(Calendar.MINUTE, 59);
	// calendar.set(Calendar.SECOND, 59);
	// Date endTime = calendar.getTime();
	// if (cmpActor.isOnWorkDay(date)) {
	// return true;
	// }
	// // 非工作日的值班
	// List<CmpActorSpTime> worktimelist = this.cmpActorSpTimeService
	// .getCmpActorSpTimeListByCompanyIdEx(companyId, actorId,
	// CmpActorSpTime.SPFLG_WORK, beginTime, endTime, 0, 1);
	// if (worktimelist.size() > 0) {
	// return true;
	// }
	// return false;
	// }
	// /**
	// * 所选时间是否可以预约
	// *
	// * @param cmpActor
	// * @param companyId
	// * @param date 所选的开始时间
	// * @return
	// * 2010-8-8
	// */
	// private boolean isCanReserve(CmpActor cmpActor, long companyId, Date
	// date,
	// Date sel_date, Date sel_endTime,
	// List<ReserveTimeVo> reservetimevolist) {
	// long actorId = cmpActor.getActorId();
	// Calendar calendar = Calendar.getInstance();
	// calendar.setTime(date);
	// calendar.set(Calendar.HOUR_OF_DAY, 0);
	// calendar.set(Calendar.MINUTE, 0);
	// calendar.set(Calendar.SECOND, 0);
	// Date beginTime = calendar.getTime();
	// calendar.set(Calendar.HOUR_OF_DAY, 23);
	// calendar.set(Calendar.MINUTE, 59);
	// calendar.set(Calendar.SECOND, 59);
	// Date endTime = calendar.getTime();
	// boolean canwork = this.isActorCanWork(cmpActor, companyId, date);
	// if (!canwork) {
	// return false;
	// }
	// List<CmpActorSpTime> sptimelist = null;
	// List<CmpReserve> deflist = null;// 预约的数据
	// List<CmpReserve> worklist = null;// 正在工作的数据
	// // List<CmpReserve> doinglist = null;
	// // 如果当天是工作日
	// if (cmpActor.isOnWorkDay(date)) {
	// // 查看是否请假，默认取10条
	// sptimelist = this.cmpActorSpTimeService
	// .getCmpActorSpTimeListByCompanyIdEx(companyId, actorId,
	// CmpActorSpTime.SPFLG_NOTWORK, beginTime, endTime,
	// 0, 10);
	// }
	// deflist = this.cmpReserveService.getCmpReserveListByCompanyIdEx(
	// companyId, actorId, CmpReserve.RESERVESTATUS_DEF, beginTime,
	// endTime, 0, 100);
	// worklist = this.cmpReserveService.getCmpReserveListByCompanyIdEx(
	// companyId, actorId, CmpReserve.RESERVESTATUS_DOING, beginTime,
	// endTime, 0, 100);
	// this.processReserve(reservetimevolist, sptimelist, deflist, worklist);
	// if (sel_date != null && sel_endTime != null) {
	// // 判断
	// List<GraduationTimeVo> gtvolist = new ArrayList<GraduationTimeVo>();
	// for (ReserveTimeVo reserveTimeVo : reservetimevolist) {
	// gtvolist.addAll(reserveTimeVo.getList());
	// }
	// for (GraduationTimeVo vo : gtvolist) {
	// if (sel_date.getTime() <= vo.getBeginTime().getTime()
	// && sel_endTime.getTime() >= vo.getEndTime().getTime()
	// && !vo.isCanReserve()) {
	// return false;
	// }
	// if (sel_date.getTime() <= vo.getBeginTime().getTime()
	// && sel_endTime.getTime() > vo.getBeginTime().getTime()
	// && sel_endTime.getTime() <= vo.getEndTime().getTime()
	// && !vo.isCanReserve()) {
	// return false;
	// }
	// if (sel_date.getTime() >= vo.getBeginTime().getTime()
	// && sel_date.getTime() < vo.getEndTime().getTime()
	// && sel_endTime.getTime() > vo.getEndTime().getTime()
	// && !vo.isCanReserve()) {
	// return false;
	// }
	// if (sel_date.getTime() > vo.getBeginTime().getTime()
	// && sel_endTime.getTime() < vo.getEndTime().getTime()
	// && !vo.isCanReserve()) {
	// return false;
	// }
	// }
	// return true;
	// }
	// return false;
	// }
	// private void processReserve(List<ReserveTimeVo> reserveTimeVoList,
	// List<CmpActorSpTime> cmpActorSpTimeList,
	// List<CmpReserve> cmpReserveList,
	// List<CmpReserve> doingCmpReserveList) {
	// // 判断
	// List<GraduationTimeVo> gtvolist = new ArrayList<GraduationTimeVo>();
	// for (ReserveTimeVo reserveTimeVo : reserveTimeVoList) {
	// gtvolist.addAll(reserveTimeVo.getList());
	// }
	// for (GraduationTimeVo graduationTimeVo : gtvolist) {
	// if (cmpActorSpTimeList != null) {
	// // 判断假期，如果在休假时间，不能预约
	// for (CmpActorSpTime cmpActorSpTime : cmpActorSpTimeList) {
	// if (!this.isDateCanReserve(cmpActorSpTime.getBeginTime(),
	// cmpActorSpTime.getEndTime(), graduationTimeVo
	// .getBeginTime(), graduationTimeVo
	// .getEndTime())) {
	// graduationTimeVo.setCanReserve(false);
	// }
	// }
	// }
	// if (cmpReserveList != null) {
	// // 判断预约，如果在预约时间，不能进行新的预约
	// for (CmpReserve cmpReserve : cmpReserveList) {
	// if (!this.isDateCanReserve(cmpReserve.getReserveTime(),
	// cmpReserve.getEndTime(), graduationTimeVo
	// .getBeginTime(), graduationTimeVo
	// .getEndTime())) {
	// graduationTimeVo.setCanReserve(false);
	// }
	// }
	// }
	// if (doingCmpReserveList != null) {
	// // 判断正在工作，如果正在工作的时间，不能进行新的预约
	// for (CmpReserve cmpReserve : doingCmpReserveList) {
	// if (!this.isDateCanReserve(cmpReserve.getReserveTime(),
	// cmpReserve.getEndTime(), graduationTimeVo
	// .getBeginTime(), graduationTimeVo
	// .getEndTime())) {
	// graduationTimeVo.setCanReserve(false);
	// }
	// }
	// }
	// }
	// }
	// /**
	// * @param beginTime 实际开始时间
	// * @param endTime 实际结束时间
	// * @param obeginTime 刻度开始时间
	// * @param oendTime 刻度结束时间
	// * @return
	// * 2010-7-30
	// */
	// private boolean isDateCanReserve(Date beginTime, Date endTime,
	// Date obeginTime, Date oendTime) {
	// // 时间刻度包含在范围内
	// if (beginTime.getTime() <= obeginTime.getTime()
	// && endTime.getTime() >= oendTime.getTime()) {
	// return false;
	// }
	// // 结束时间在范围内
	// if (beginTime.getTime() < obeginTime.getTime()
	// && endTime.getTime() <= oendTime.getTime()
	// && endTime.getTime() > obeginTime.getTime()) {
	// return false;
	// }
	// // 开始时间在范围内
	// if (beginTime.getTime() >= obeginTime.getTime()
	// && beginTime.getTime() < oendTime.getTime()
	// && endTime.getTime() > oendTime.getTime()) {
	// return false;
	// }
	// // 时间包含在刻度之内
	// if (beginTime.getTime() > obeginTime.getTime()
	// && endTime.getTime() < oendTime.getTime()) {
	// return false;
	// }
	// return true;
	// }
}