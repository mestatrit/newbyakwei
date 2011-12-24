package web.epp.mgr.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpActor;
import com.hk.bean.CmpReserve;
import com.hk.bean.CmpSvr;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpActorService;
import com.hk.svr.CmpReserveService;
import com.hk.svr.CmpSvrService;
import com.hk.svr.processor.CmpReserveProcessor;

@Component("/epp/web/op/webadmin/reserve")
public class ReserveAction extends EppBaseAction {

	@Autowired
	private CmpReserveProcessor cmpReserveProcessor;

	@Autowired
	private CmpReserveService cmpReserveService;

	@Autowired
	private CmpActorService cmpActorService;

	@Autowired
	private CmpSvrService cmpSvrService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		String name = req.getString("name");
		long actorId = 0;
		if (!DataUtil.isEmpty(name)) {
			CmpActor cmpActor = this.cmpActorService.getCmpActorByName(
					companyId, name);
			if (cmpActor != null) {
				actorId = cmpActor.getActorId();
			}
			req.setEncodeAttribute("name", name);
		}
		byte reserveStatus = req.getByteAndSetAttr("reserveStatus");
		String day = req.getString("day");
		if (DataUtil.isEmpty(day)) {
			day = DataUtil.getFormatTimeData(new Date(), "yyyy-MM-dd");
		}
		req.setAttribute("day", day);
		Date beginTime = DataUtil.parseTime(day + " 00:00", "yyyy-MM-dd HH:mm");
		Date endTime = DataUtil.parseTime(day + " 23:59", "yyyy-MM-dd HH:mm");
		// if (beginTime == null) {
		// Calendar calendar = Calendar.getInstance();
		// calendar.set(Calendar.HOUR_OF_DAY, 0);
		// calendar.set(Calendar.MINUTE, 0);
		// calendar.set(Calendar.SECOND, 0);
		// beginTime = calendar.getTime();
		// }
		// if (endTime == null) {
		// Calendar calendar = Calendar.getInstance();
		// calendar.set(Calendar.HOUR_OF_DAY, 23);
		// calendar.set(Calendar.MINUTE, 59);
		// calendar.set(Calendar.SECOND, 59);
		// endTime = calendar.getTime();
		// }
		SimplePage page = req.getSimplePage(20);
		List<CmpReserve> list = this.cmpReserveProcessor
				.getCmpReserveListByCompanyIdEx(companyId, actorId,
						reserveStatus, null, null, beginTime, endTime, true,
						true, page.getBegin(), page.getSize() + 1);
		req.setAttribute("list", list);
		req.setAttribute("beginTime", beginTime);
		req.setAttribute("endTime", endTime);
		return this.getWebPath("admin/reserve/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-9
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
		return this.getWebPath("admin/reserve/view.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-9
	 */
	public String update(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long reserveId = req.getLong("reserveId");
		this.updatereservestatus(companyId, reserveId, req
				.getByte("reserveStatus"));
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	private String updatereservestatus(long companyId, long reserveId,
			byte status) {
		CmpReserve cmpReserve = this.cmpReserveService.getCmpReserve(reserveId);
		if (cmpReserve == null || cmpReserve.getCompanyId() != companyId) {
			return null;
		}
		cmpReserve.setReserveStatus(status);
		this.cmpReserveService.updateCmpReserve(cmpReserve);
		return null;
	}
	// /**
	// * @param req
	// * @param resp
	// * @return
	// * 2010-8-9
	// */
	// public String cancelreserve(HkRequest req, HkResponse resp) {
	// long companyId = req.getLong("companyId");
	// long reserveId = req.getLong("reserveId");
	// this.updatereservestatus(companyId, reserveId,
	// CmpReserve.RESERVESTATUS_CANCEL);
	// this.setOpFuncSuccessMsg(req);
	// return null;
	// }
	//
	// /**
	// * @param req
	// * @param resp
	// * @return
	// * 2010-8-9
	// */
	// public String setwork(HkRequest req, HkResponse resp) {
	// long companyId = req.getLong("companyId");
	// long reserveId = req.getLong("reserveId");
	// this.updatereservestatus(companyId, reserveId,
	// CmpReserve.RESERVESTATUS_DOING);
	// this.setOpFuncSuccessMsg(req);
	// return null;
	// }
	//
	// /**
	// * @param req
	// * @param resp
	// * @return
	// * 2010-8-9
	// */
	// public String setworkok(HkRequest req, HkResponse resp) {
	// long companyId = req.getLong("companyId");
	// long reserveId = req.getLong("reserveId");
	// this.updatereservestatus(companyId, reserveId,
	// CmpReserve.RESERVESTATUS_SUCCESS);
	// this.setOpFuncSuccessMsg(req);
	// return null;
	// }
}