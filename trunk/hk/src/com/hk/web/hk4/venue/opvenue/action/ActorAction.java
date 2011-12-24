package com.hk.web.hk4.venue.opvenue.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpActor;
import com.hk.bean.CmpActorRole;
import com.hk.bean.CmpActorSpTime;
import com.hk.bean.CmpActorSvrRef;
import com.hk.bean.CmpSvr;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpActorService;
import com.hk.svr.CmpActorSpTimeService;
import com.hk.svr.CmpSvrService;
import com.hk.svr.processor.CmpActorProcessor;
import com.hk.svr.processor.CmpSvrProcessor;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

@Component("/h4/op/venue/actor")
public class ActorAction extends BaseAction {

	@Autowired
	private CmpActorService cmpActorService;

	@Autowired
	private CmpActorProcessor cmpActorProcessor;

	@Autowired
	private CmpSvrProcessor cmpSvrProcessor;

	@Autowired
	private CmpSvrService cmpSvrService;

	@Autowired
	private CmpActorSpTimeService cmpActorSpTimeService;

	/**
	 * 人员列表
	 */
	public String execute(HkRequest req, HkResponse resp) {
		req.setAttribute("active_36", 1);
		long companyId = req.getLongAndSetAttr("companyId");
		String name = req.getHtmlRow("name");
		long roleId = req.getLongAndSetAttr("roleId");
		SimplePage page = req.getSimplePage(20);
		List<CmpActor> list = this.cmpActorService.getCmpActorListByCompanyId(
				companyId, roleId, name, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.setEncodeAttribute("name", name);
		return this.getWeb4Jsp("venue/op2/actor/list.jsp");
	}

	/**
	 * 角色列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-21
	 */
	public String rolelist(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_35", 1);
		long companyId = req.getLongAndSetAttr("companyId");
		List<CmpActorRole> list = this.cmpActorService
				.getCmpActorRoleListByCompanyId(companyId);
		req.setAttribute("list", list);
		return this.getWeb4Jsp("venue/op2/actor/rolelist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-21
	 */
	public String createrole(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_35", 1);
		if (this.isForwardPage(req)) {
			return this.getWeb4Jsp("venue/op2/actor/createrole.jsp");
		}
		long companyId = req.getLongAndSetAttr("companyId");
		CmpActorRole cmpActorRole = new CmpActorRole();
		cmpActorRole.setName(req.getHtmlRow("name"));
		cmpActorRole.setCompanyId(companyId);
		int code = cmpActorRole.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		this.cmpActorService.createCmpActorRole(cmpActorRole);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-21
	 */
	public String updaterole(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_35", 1);
		long roleId = req.getLongAndSetAttr("roleId");
		CmpActorRole cmpActorRole = this.cmpActorService
				.getCmpActorRole(roleId);
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpActorRole", cmpActorRole);
			return this.getWeb4Jsp("venue/op2/actor/updaterole.jsp");
		}
		cmpActorRole.setName(req.getHtmlRow("name"));
		int code = cmpActorRole.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		this.cmpActorService.updateCmpActorRole(cmpActorRole);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-21
	 */
	public String delrole(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_35", 1);
		long companyId = req.getLongAndSetAttr("companyId");
		long roleId = req.getLong("roleId");
		CmpActorRole cmpActorRole = this.cmpActorService
				.getCmpActorRole(roleId);
		if (cmpActorRole == null || cmpActorRole.getCompanyId() != companyId) {
			return null;
		}
		this.cmpActorService.deleteCmpActorRole(roleId);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-21
	 */
	public String createactor(HkRequest req, HkResponse resp) {
		req.setAttribute("active_36", 1);
		if (this.isForwardPage(req)) {
			return this.getWeb4Jsp("venue/op2/actor/createactor.jsp");
		}
		long companyId = req.getLongAndSetAttr("companyId");
		CmpActor cmpActor = new CmpActor();
		cmpActor.setName(req.getHtmlRow("name"));
		cmpActor.setCompanyId(companyId);
		cmpActor.setRoleId(req.getLong("roleId"));
		cmpActor.setIntro(req.getHtml("intro"));
		cmpActor.setReserveflg(req.getByte("reserveflg"));
		cmpActor.setGender(req.getByte("gender"));
		int code = cmpActor.validate();
		String createErrorMethod = "createerror";
		String createSuccessMethod = "createok";
		if (code != Err.SUCCESS) {
			return this.onError(req, code, createErrorMethod, null);
		}
		try {
			code = this.cmpActorProcessor.createCmpActor(cmpActor, req
					.getFile("f"));
			if (code != Err.SUCCESS) {
				if (code == Err.UPLOAD_FILE_SIZE_LIMIT) {
					return this.onError(req, Err.UPLOAD_FILE_SIZE_LIMIT,
							new Object[] { "500K" }, createErrorMethod, null);
				}
				return this.onError(req, code, createErrorMethod, null);
			}
			this.setOpFuncSuccessMsg(req);
			return this.onSuccess2(req, createSuccessMethod, null);
		}
		catch (ImageException e) {
			return this.onError(req, Err.IMG_UPLOAD_ERROR, createErrorMethod,
					null);
		}
		catch (NotPermitImageFormatException e) {
			return this
					.onError(req, Err.IMG_FMT_ERROR, createErrorMethod, null);
		}
		catch (OutOfSizeException e) {
			return this.onError(req, Err.UPLOAD_FILE_SIZE_LIMIT,
					new Object[] { "500K" }, createErrorMethod, null);
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-21
	 */
	public String updateactor(HkRequest req, HkResponse resp) {
		req.setAttribute("active_36", 1);
		long companyId = req.getLongAndSetAttr("companyId");
		long actorId = req.getLongAndSetAttr("actorId");
		CmpActor cmpActor = this.cmpActorService.getCmpActor(actorId);
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpActor", cmpActor);
			List<CmpActorSvrRef> list = this.cmpSvrProcessor
					.getCmpActorSvrRefListByCompanyIdAndActorId(companyId,
							actorId, true);
			req.setAttribute("list", list);
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 00);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date beginTime = calendar.getTime();
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			Date endTime = calendar.getTime();
			List<CmpActorSpTime> todysptimelist = this.cmpActorSpTimeService
					.getCmpActorSpTimeListByCompanyIdEx(companyId, actorId,
							CmpActorSpTime.SPFLG_WORK, beginTime, endTime, 0, 1);
			if (todysptimelist.size() > 0) {
				req.setAttribute("can_set_work", false);
			}
			else {
				req.setAttribute("can_set_work", true);
			}
			return this.getWeb4Jsp("venue/op2/actor/updateactor.jsp");
		}
		cmpActor.setName(req.getHtmlRow("name"));
		cmpActor.setCompanyId(companyId);
		cmpActor.setRoleId(req.getLong("roleId"));
		cmpActor.setIntro(req.getHtml("intro"));
		cmpActor.setReserveflg(req.getByte("reserveflg"));
		cmpActor.setGender(req.getByte("gender"));
		int code = cmpActor.validate();
		String updateErrorMethod = "updateerror";
		String updateSuccessMethod = "updateok";
		if (code != Err.SUCCESS) {
			return this.onError(req, code, updateErrorMethod, null);
		}
		try {
			code = this.cmpActorProcessor.updateCmpActor(cmpActor, req
					.getFile("f"));
			if (code != Err.SUCCESS) {
				if (code == Err.UPLOAD_FILE_SIZE_LIMIT) {
					return this.onError(req, Err.UPLOAD_FILE_SIZE_LIMIT,
							new Object[] { "500K" }, updateErrorMethod, null);
				}
				return this.onError(req, code, updateErrorMethod, null);
			}
			this.setOpFuncSuccessMsg(req);
			return this.onSuccess2(req, updateSuccessMethod, null);
		}
		catch (ImageException e) {
			return this.onError(req, Err.IMG_UPLOAD_ERROR, updateErrorMethod,
					null);
		}
		catch (NotPermitImageFormatException e) {
			return this
					.onError(req, Err.IMG_FMT_ERROR, updateErrorMethod, null);
		}
		catch (OutOfSizeException e) {
			return this.onError(req, Err.UPLOAD_FILE_SIZE_LIMIT,
					new Object[] { "500K" }, updateErrorMethod, null);
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-21
	 */
	public String delactor(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		long actorId = req.getLong("actorId");
		CmpActor cmpActor = this.cmpActorService.getCmpActor(actorId);
		if (cmpActor == null || cmpActor.getCompanyId() != companyId) {
			return null;
		}
		this.cmpActorProcessor.deleteCmpActor(cmpActor);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-21
	 */
	public String rmsvrfromactor(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		long oid = req.getLong("oid");
		CmpActorSvrRef ref = this.cmpSvrService.getCmpActorSvrRef(companyId,
				oid);
		if (ref == null) {
			return null;
		}
		CmpSvr cmpSvr = this.cmpSvrService.getCmpSvr(companyId, ref.getSvrId());
		if (cmpSvr == null || cmpSvr.getCompanyId() != companyId) {
			return null;
		}
		CmpActor cmpActor = this.cmpActorService.getCmpActor(ref.getActorId());
		if (cmpActor == null || cmpActor.getCompanyId() != companyId) {
			return null;
		}
		this.cmpSvrService.deleteCmpActorSvrRef(companyId, oid);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-21
	 */
	public String rmsvrfromactor2(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		long oid = req.getLong("oid");
		CmpActorSvrRef ref = this.cmpSvrService.getCmpActorSvrRef(companyId,
				oid);
		if (ref == null) {
			return null;
		}
		CmpSvr cmpSvr = this.cmpSvrService.getCmpSvr(companyId, ref.getSvrId());
		if (cmpSvr == null || cmpSvr.getCompanyId() != companyId) {
			return null;
		}
		CmpActor cmpActor = this.cmpActorService.getCmpActor(ref.getActorId());
		if (cmpActor == null || cmpActor.getCompanyId() != companyId) {
			return null;
		}
		this.cmpSvrService.deleteCmpActorSvrRef(companyId, oid);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-21
	 */
	public String setworkday(HkRequest req, HkResponse resp) {
		req.reSetAttribute("companyId");
		long actorId = req.getLongAndSetAttr("actorId");
		CmpActor cmpActor = this.cmpActorService.getCmpActor(actorId);
		if (cmpActor == null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpActor", cmpActor);
			if (!DataUtil.isEmpty(cmpActor.getWorkDay())) {
				String[] t = cmpActor.getWorkDay().split(",");
				for (String s : t) {
					req.setAttribute("day_" + s, s);
				}
			}
			return this.getWeb4Jsp("venue/op2/actor/workday.jsp");
		}
		int[] day = req.getInts("day");
		StringBuilder sb = new StringBuilder();
		if (day != null) {
			for (int d : day) {
				if (d > 0) {
					sb.append(d).append(",");
				}
			}
			if (sb.length() > 1) {
				sb.deleteCharAt(sb.length() - 1);
			}
			cmpActor.setWorkDay(sb.toString());
		}
		this.cmpActorService.updateCmpActor(cmpActor);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-21
	 */
	public String sptimelist(HkRequest req, HkResponse resp) {
		req.setAttribute("active_36", 1);
		long companyId = req.getLongAndSetAttr("companyId");
		long actorId = req.getLongAndSetAttr("actorId");
		byte spflg = req.getByteAndSetAttr("spflg");
		String day = req.getStringAndSetAttr("day");
		Date beginTime = DataUtil.parseTime(day + " 00:00", "yyyy-MM-dd HH:mm");
		Date endTime = DataUtil.parseTime(day + " 59:59", "yyyy-MM-dd HH:mm");
		if (actorId > 0) {
			CmpActor cmpActor = this.cmpActorService.getCmpActor(actorId);
			req.setAttribute("cmpActor", cmpActor);
		}
		SimplePage page = req.getSimplePage(20);
		List<CmpActorSpTime> list = this.cmpActorSpTimeService
				.getCmpActorSpTimeListByCompanyIdEx(companyId, actorId, spflg,
						beginTime, endTime, page.getBegin(), page.getSize() + 1);
		req.setAttribute("list", list);
		req.setAttribute("beginTime", beginTime);
		req.setAttribute("endTime", endTime);
		return this.getWeb4Jsp("venue/op2/actor/sptimelist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-21
	 */
	public String createactorsptime(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		long actorId = req.getLongAndSetAttr("actorId");
		if (this.isForwardPage(req)) {
			return this.getWeb4Jsp("venue/op2/actor/createactorsptime.jsp");
		}
		String day = req.getString("day");
		String begint = req.getString("begint");
		if (DataUtil.isEmpty(begint)) {
			begint = "00:00";
		}
		String endt = req.getString("endt");
		if (DataUtil.isEmpty(endt)) {
			endt = "23:59";
		}
		Date beginTime = DataUtil.parseTime(day + " " + begint,
				"yyyy-MM-dd HH:mm");
		Date endTime = DataUtil.parseTime(day + " " + endt, "yyyy-MM-dd HH:mm");
		if (beginTime == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			beginTime = calendar.getTime();
		}
		if (endTime == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			endTime = calendar.getTime();
		}
		CmpActorSpTime cmpActorSpTime = new CmpActorSpTime();
		cmpActorSpTime.setActorId(actorId);
		cmpActorSpTime.setCompanyId(companyId);
		cmpActorSpTime.setBeginTime(beginTime);
		cmpActorSpTime.setEndTime(endTime);
		cmpActorSpTime.setCreateTime(new Date());
		cmpActorSpTime.setSpflg(CmpActorSpTime.SPFLG_NOTWORK);
		int code = cmpActorSpTime.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		this.cmpActorSpTimeService.createCmpActorSpTime(cmpActorSpTime);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-21
	 */
	public String updateactorsptime(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		long actorId = req.getLongAndSetAttr("actorId");
		long oid = req.getLongAndSetAttr("oid");
		CmpActorSpTime cmpActorSpTime = this.cmpActorSpTimeService
				.getCmpActorSpTime(companyId, oid);
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpActorSpTime", cmpActorSpTime);
			return this.getWeb4Jsp("venue/op2/actor/updateactorsptime.jsp");
		}
		String day = req.getString("day");
		String begint = req.getString("begint");
		if (DataUtil.isEmpty(begint)) {
			begint = "00:00";
		}
		String endt = req.getString("endt");
		if (DataUtil.isEmpty(endt)) {
			endt = "59:59";
		}
		Date beginTime = DataUtil.parseTime(day + " " + begint,
				"yyyy-MM-dd HH:mm");
		Date endTime = DataUtil.parseTime(day + " " + endt, "yyyy-MM-dd HH:mm");
		if (beginTime == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			beginTime = calendar.getTime();
		}
		if (endTime == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 59);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			endTime = calendar.getTime();
		}
		cmpActorSpTime.setActorId(actorId);
		cmpActorSpTime.setCompanyId(companyId);
		cmpActorSpTime.setBeginTime(beginTime);
		cmpActorSpTime.setEndTime(endTime);
		cmpActorSpTime.setCreateTime(new Date());
		cmpActorSpTime.setSpflg(CmpActorSpTime.SPFLG_NOTWORK);
		int code = cmpActorSpTime.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		this.cmpActorSpTimeService.updateCmpActorSpTime(cmpActorSpTime);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-21
	 */
	public String delactorsptime(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		long oid = req.getLongAndSetAttr("oid");
		CmpActorSpTime cmpActorSpTime = this.cmpActorSpTimeService
				.getCmpActorSpTime(companyId, oid);
		if (cmpActorSpTime == null
				|| cmpActorSpTime.getCompanyId() != companyId) {
			return null;
		}
		this.cmpActorSpTimeService.deleteCmpActorSpTime(companyId, oid);
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-21
	 */
	public String settodaywork(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		long actorId = req.getLong("actorId");
		CmpActorSpTime cmpActorSpTime = new CmpActorSpTime();
		cmpActorSpTime.setCompanyId(companyId);
		cmpActorSpTime.setActorId(actorId);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date beginTime = calendar.getTime();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date endTime = calendar.getTime();
		cmpActorSpTime.setBeginTime(beginTime);
		cmpActorSpTime.setEndTime(endTime);
		cmpActorSpTime.setCreateTime(new Date());
		cmpActorSpTime.setSpflg(CmpActorSpTime.SPFLG_WORK);
		List<CmpActorSpTime> list = this.cmpActorSpTimeService
				.getCmpActorSpTimeListByCompanyIdEx(companyId, actorId,
						CmpActorSpTime.SPFLG_WORK, beginTime, endTime, 0, 5);
		for (CmpActorSpTime o : list) {
			this.cmpActorSpTimeService.deleteCmpActorSpTime(o.getCompanyId(), o
					.getOid());
		}
		this.cmpActorSpTimeService.createCmpActorSpTime(cmpActorSpTime);
		this.setSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-21
	 */
	public String deltodaywork(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		long actorId = req.getLong("actorId");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date beginTime = calendar.getTime();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date endTime = calendar.getTime();
		List<CmpActorSpTime> list = this.cmpActorSpTimeService
				.getCmpActorSpTimeListByCompanyIdEx(companyId, actorId,
						CmpActorSpTime.SPFLG_WORK, beginTime, endTime, 0, 5);
		for (CmpActorSpTime o : list) {
			this.cmpActorSpTimeService.deleteCmpActorSpTime(o.getCompanyId(), o
					.getOid());
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-21
	 */
	public String updatestatus(HkRequest req, HkResponse resp) {
		long actorId = req.getLongAndSetAttr("actorId");
		CmpActor cmpActor = this.cmpActorService.getCmpActor(actorId);
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpActor", cmpActor);
			return this.getWeb4Jsp("venue/op2/actor/updatestatus.jsp");
		}
		byte actorStatus = req.getByte("actorStatus");
		cmpActor.setActorStatus(actorStatus);
		this.cmpActorService.updateCmpActor(cmpActor);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}
}